package efsframe.cn.db;

/**
 * �������ӳصĹ���
 * @author enjsky
 */
import java.util.Date;
import java.util.Vector;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import efsframe.cn.db.DBConnection;
import efsframe.cn.declare.*;
import java.util.List;
import java.util.Iterator;

public class DBConnectionManager {

	static private DBConnectionManager instance;

	static private int clients;

	private static Vector<Driver> drivers = new Vector<Driver>();

	//���ݿ����ӳ�
	private static Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();

	private static Hashtable ctxTable = new Hashtable();

	/**
	 * ������ӳع������ʵ��
	 * @return DBConnectionManager
	 */
	static synchronized DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		clients++;
		return instance;
	}

	private DBConnectionManager() {
		init();
	}

	/**
	 * ���ӳع�����ĳ�ʼ��
	 */
	private void init() {
		InputStream isConfig = getClass().getResourceAsStream("connection.ini");
		Properties prop = new Properties();

		try {
			prop.load(isConfig);
		} catch (Exception e) {
			isConfig = getClass().getResourceAsStream(
					"/" + Common.SYSTEM_NAME
							+ "/WEB-INF/classes/efsframe/cn/db/connection.ini");
			try {
				prop.load(isConfig);
			} catch (IOException ex) {
				System.err.println("���ܶ�ȡ�����ļ�. " + "��ȷ�������ļ���ָ����·����");
				return;
			}
		}

		String isJdbctype = prop.getProperty("JdbcType");
		DBConnection.JDBCTYPE = isJdbctype;
		System.out.println("JDBC���ͣ�" + isJdbctype);
		if (isJdbctype.equals("1")) { //װ��WebLogic��WebSphere��Ӧ�÷�����������Դ
			loadJdbc(prop);
		} 
		else {                     //װ���Զ��������Դ
			loadDrivers(prop);
			createPools(prop);
		}

	}
	
	 /**
	   * �������ӳ�
	   */
	  public void reset()
	  {
		  init();
	  }

	/**
	 * ��ʼ�����ݿ������������
	 * @param props�������б�
	 */
	private void loadDrivers(Properties props) {
		String driverClasses = props.getProperty("drivers");
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				Driver driver = (Driver) Class.forName(driverClassName)
						.newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				System.out.println("�ɹ�ע��JDBC��������" + driverClassName);
			} catch (Exception e) {
				System.out.println("�޷�ע��JDBC��������: " + driverClassName + ", ����: " + e);
			}
		}
	}

	
	/**
	 * ��ʼ��Weblogic��WebSphere�ȶ��������Դ
	 * @param props ����Դ�����ļ�
	 */
	private void loadJdbc(Properties props) {

		try {

			// String applicationSerName = props.getProperty("AppSerName");

			String jdname = props.getProperty("DataSource");
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(jdname);

			DBConnection.DATA_SOURCE = ds;
            
			System.out.println("");
			System.out.println("��ȡ����Դ"+jdname+"�ɹ���");
			System.out.println("");

		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ȡ����Դʧ�ܣ�");
		}

	}


	/**
	 * ��ʼ�����ӳ�
	 * @param props �����б�
	 * @return ��
	 */
	private void createPools(Properties props) {
		Enumeration propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			
			if (name.endsWith(".url")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String strUrl = props.getProperty(poolName + ".url");
				if (strUrl == null) {
					System.out.println("û��Ϊ���ӳ�" + poolName + "ָ��URL");
					continue;
				}
				String strUser = props.getProperty(poolName + ".user");
				String strPassword = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");
				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					System.out.println("������������������: " + maxconn + " .���ӳ�: "
							+ poolName);
					max = 0;
				}
				DBConnectionPool pool = new DBConnectionPool(poolName, strUrl,
						strUser, strPassword, max);
				pools.put(poolName, pool);
				System.out.println("�ɹ��������ӳ�" + poolName + "����" + pool.maxcon + "��");
			}
		}
	}


	public Context getContext() {
		return getContext("default");
	}

	public Context getContext(String name) {
		Context ctx = (Context) DBConnectionManager.ctxTable.get(name);
		return ctx;
	}

	/**
	 * ��ù��ж�����ʹ�õ�����
	 * @return int ��ʹ�õ�������
	 */
	public int getUsedCounts() {
		return getUsedCounts(Common.SYSTEM_NAME);
	}

	/**
	 *  ��ù��ж�����ʹ�õ�����
	 * @param strPoolName ���ӳ�����
	 * @return int ��ʹ�õ�������
	 */
	public int getUsedCounts(String strPoolName) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(strPoolName);

		if (pool != null) {
			return pool.getUsedCounts();
		}
		return 0;
	}

	/**
	 * ��ù��ж���δʹ�õ�����
	 * @return int δʹ�õ�������
	 */
	public int getFreeCounts() {
		return getFreeCounts(Common.SYSTEM_NAME);
	}

	/**
	 * ��ù��ж���δʹ�õ�����
	 * @param strPoolName ���ӳ�����
	 * @return int δʹ�õ�������
	 */
	public int getFreeCounts(String strPoolName) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(strPoolName);

		if (pool != null) {
			return pool.getFreeCounts();
		}
		return 0;
	}

	/**
	 * �������ӵ� url
	 * @param name  ���ӳ�����
	 * @return ��
	 */
	public String getUrl(String name) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.URL;
		}
		return null;
	}

	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
			return;
		} else {
			if (con != null) {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {

				}
			}
		}
		con = null;
	}

	/**
	 * �ر���������,�������������ע��
	 */
	public synchronized void release() {
		if (--clients != 0) {
			return;
		}
		Enumeration allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			if (pool != null) {
				pool.release();
			}
		}
		Enumeration allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				System.out.println("����JDBC�������� " + driver.getClass().getName()
						+ "��ע��");
			} catch (SQLException e) {
				System.out.println("�޷���������JDBC���������ע��: "
						+ driver.getClass().getName());
			}
		}
	}

	/**
	 * ���һ����������.��û�п�������,������������С���������������, �򴴽�������������.
	 * @param name ���ӳ�����
	 * @return Connection �������ӻ�null
	 */
	public Connection getConnection(String name) {

		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection();
		}
		return null;

	}

	/**
	 * ���һ����������.��û�п�������,������������С���������������, �򴴽�������������.����,��ָ����ʱ���ڵȴ������߳��ͷ�����.
	 * @param name ���ӳ�����
	 * @param time �Ժ���Ƶĵȴ�ʱ��
	 * @return Connection �������ӻ�null
	 */
	public Connection getConnectionFromPool(String name, long time) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}

	class DBConnectionPool {
		private String name;

		private String URL;

		private String user;

		private String password;

		private int maxcon;

		private List<Connection> freeConnections = new java.util.LinkedList<Connection>();

		private List<Connection> usedConnections = new java.util.LinkedList<Connection>();

		private int checkout;

		public DBConnectionPool(String name, String URL, String user,
				String password, int maxcon) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxcon = maxcon;
			if (maxcon > 0)
				for (int i = 0; i < maxcon; i++) {
					Connection con = newConnection();
					freeConnections.add(con);
				}
		}

		public synchronized void freeConnection(Connection con) {
			if (con != null) {
				if (usedConnections.remove(con)) {
					freeConnections.add(con);
					this.checkout--;
					notifyAll();
				}
			}
		}

		public synchronized Connection getConnection(long timeout) {
			Connection con = getConnectionex(timeout);
			if (con != null) {
				usedConnections.add(con);
			}
			return con;
		}

		/***********************************************************************
		 * �������
		 * @return Connetion ����
		 **********************************************************************/
		public synchronized Connection getConnection() {
			Connection con = getConnectionex();
			if (con == null) {
				con = getConnectionex(3000);
			}
			if (con != null) {
				usedConnections.add(con);
				this.checkout++;
			}
			return con;
		}

		/**
		 * ��ù��ж�����ʹ�õ�����
		 * @return int ��ʹ�õ�������
		 */
		public synchronized int getUsedCounts() {
			return usedConnections.size();
		}

		/**
		 * ��ù��ж���δʹ�õ�����
		 * @return int δʹ�õ�������
		 */
		public synchronized int getFreeCounts() {
			return freeConnections.size();
		}

		/**
		 * �����ӳػ��һ����������.��û�п��е������ҵ�ǰ������С��������� ������,�򴴽�������.��ԭ���Ǽ�Ϊ���õ����Ӳ�����Ч,�������ɾ��֮,
		 * Ȼ��ݹ�����Լ��Գ����µĿ�������.
		 */
		private synchronized Connection getConnectionex() {

			Connection con = null;

			if (freeConnections.size() > 0) {
				con = (Connection) freeConnections.get(0);
				freeConnections.remove(0);

				try {
					if (con != null) {
						if (con.isClosed()) {
							System.out.println("�����ӳ�" + name + "�½�һ������");
							con = newConnection();
						}
					}
				} catch (SQLException e) {
					//System.out.println("�����ӳ�" + name + "ɾ��һ����Ч����");
					con = getConnectionex();
				}

			}
			return con;
		}

		/***********************************************************************
		 * �������
		 * @param timeout ��ʱ��
		 * @return Connetion ����
		 **********************************************************************/
		private synchronized Connection getConnectionex(long timeout) {
			Connection con;
			long startTime = new Date().getTime();

			while (((con = getConnectionex()) == null)) {
				try {
					wait(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (new Date().getTime() - startTime > timeout) {
					return null;
				}
			}
			return con;
		}

		/***********************************************************************
		 * �ͷ���������
		 **********************************************************************/
		public synchronized void release() {
			Iterator allconnections = freeConnections.iterator();

			while (allconnections.hasNext()) {

				Connection con = (Connection) allconnections.next();
				try {
					con.close();
					System.out.println("�ر����ӳ�" + name + "�е�һ������");
				} catch (SQLException e) {
					System.out.println("�޷��ر����ӳ�" + name + "�е�����,����Ϊ��"
							+ e.getMessage());
				}
			}
			freeConnections.clear();
			usedConnections.clear();
		}

		/***********************************************************************
		 * �����µ�����
		 * @return Connection �½�����
		 **********************************************************************/
		private Connection newConnection() {
			Connection con = null;

			try {
				if (user == null) {
					con = DriverManager.getConnection(this.URL);
				} else {
					con = DriverManager.getConnection(this.URL, this.user,
							this.password);
				}
			} catch (Exception e) {
				System.out.println("�޷���������URL������" + URL + "��ԭ�����£�"
						+ e.getMessage());
				return null;
			}
			return con;
		}
	}
}