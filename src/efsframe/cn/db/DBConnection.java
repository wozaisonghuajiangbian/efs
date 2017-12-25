package efsframe.cn.db;

import java.sql.*;

import javax.sql.DataSource;

import efsframe.cn.declare.Common;

public class DBConnection implements java.io.Serializable {
	private static final long serialVersionUID = 7951045108660549719L;

	private Connection m_cnn_Self = null;

	private Statement m_stm = null;

	public static DataSource DATA_SOURCE;

	public static String JDBCTYPE;

	/// ������ӹ�����
	static DBConnectionManager connMgr = DBConnectionManager.getInstance();

	/**
	 * ����ʵ�����������ݿ����ӳ��л������
	 */
	public DBConnection() {
		try {
			if (JDBCTYPE.equals("1"))
				m_cnn_Self = DATA_SOURCE.getConnection();
			else
				m_cnn_Self = connMgr.getConnection(Common.SYSTEM_NAME);
			
			m_stm = m_cnn_Self.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ʵ�����������ݿ����ӳ��л������
	 * @param strPoolName ���ӳ�����
	 */
	public DBConnection(String strPoolName) {
		try {
			m_cnn_Self = connMgr.getConnection(strPoolName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������
	 * @return Connection ���ӻ� null
	 */
	public Connection getConnection() {
		try {
			ResultSet rs;
			if (JDBCTYPE.equals("2")) {
				rs = m_stm.executeQuery("SELECT 1 FROM DUAL");
				rs.close();
			}
			else if (JDBCTYPE.equals("3")) {
				rs = m_stm.executeQuery("SELECT 1");
				rs.close();
			}
      else if (JDBCTYPE.equals("4")) {
        rs = m_stm.executeQuery("SELECT 1");
        rs.close();
      }
		}
		catch (Exception ex) // �쳣���´������ӳ�
		{
			System.out.println("���ݿ������쳣�����½������ӡ�");
			connMgr = DBConnectionManager.getInstance();
			connMgr.reset();
			try {
				if (JDBCTYPE.equals("1")) {
					m_cnn_Self = DATA_SOURCE.getConnection();
				}
				else
					m_cnn_Self = connMgr.getConnection(Common.SYSTEM_NAME);
				
				m_stm = m_cnn_Self.createStatement();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return m_cnn_Self;
	}

	/**
	 * ������ӳ�����ʹ�õ�������
	 * @return int ��ʹ�õ�������
	 */
	public int getUsedCounts() {
		return connMgr.getUsedCounts();
	}

	/**
	 * ������ӳ�����ʹ�õ�������
	 * @param strPoolName ���ӳ�����
	 * @return int ��ʹ�õ�������
	 */
	public int getUsedCounts(String strPoolName) {
		return connMgr.getUsedCounts(strPoolName);
	}

	/**
	 * ������ӳ���δ���ӵ�������
	 * @return int δ���ӵ�������
	 */
	public int getFreeCounts() {
		return connMgr.getFreeCounts();
	}

	/**
	 * ������ӳ���δ���ӵ�������
	 * @param strPoolName ���ӳ�����
	 * @return int δ���ӵ�������
	 */
	public int getFreeCounts(String strPoolName) {
		return connMgr.getFreeCounts(strPoolName);
	}

	/**
	 * �ͷ�����
	 */
	public void freeConnection() {
		if (JDBCTYPE.equals("1")) {

			try {
				if (m_stm != null)
					m_stm.close();

				if (m_cnn_Self != null)
					m_cnn_Self.close();
			} catch (Exception e) {
			}
		} else {
			freeConnection(Common.SYSTEM_NAME);
			try {
				if (m_stm != null)
					m_stm.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * �ͷ�����
	 * @param strPoolName ���ӳ�����
	 */
	public void freeConnection(String strPoolName) {
		try {
			if (m_cnn_Self != null) {
				connMgr.freeConnection(strPoolName, m_cnn_Self);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر����ӳ��е��������ӣ��������������ע��
	 */
	public void releaseConnection() {
		connMgr.release();
	}

	/**
	 * strSQL ���
	 * @return String �� ִ�гɹ� ���� ���ش�����Ϣ
	 */
	public String excuteSql(String strSQL) throws Exception {
		String stringRtn = null;

		try {

			PreparedStatement stmt = m_cnn_Self.prepareStatement(strSQL);

			stmt.executeUpdate();

			stmt.close();
			m_cnn_Self.commit();
			//return null;
		} catch (Exception e) {
			e.printStackTrace();
			stringRtn = "��������" + e.getMessage();

			try {
				m_cnn_Self.rollback();
			} catch (SQLException ex) {
				stringRtn += ex.getMessage();
			}
		} finally {
			this.freeConnection();
		}
		return stringRtn;
	}
	
	/**
	 * ִ�в�ѯ
	 * @param strSQL ��ѯ���
	 * @return ����� �� null
	 */
	public ResultSet excuteQuery(String strSQL) throws Exception {
		ResultSet resultSet;

		try {
			resultSet = m_stm.executeQuery(strSQL);
			return resultSet;
		} catch (Exception e) {
			throw e;

		} finally {
			//statement.close();
		}
	}

	/**
	 * �������
	 * @return ��
	 */
	protected void finalize() {
		freeConnection();
	}
}