package efsframe.cn.db;

/**
 * ���ݴ���������������T-SQL����ͳһ�����װ����
 * @author enjsky
 * {@link www.efsframe.cn}
 */
import java.util.*;
import java.sql.*;
import efsframe.cn.base.NumAssign;
import efsframe.cn.declare.Common;
import efsframe.cn.func.General;

public class DataStorage
{
  private LinkedList<String> m_lst_SQL;    /// �洢 SQL ���
  
  /**
   * ����ʵ����
   */
  public DataStorage()
  {
    this.m_lst_SQL = new LinkedList<String>();
  }
  
  /**
   * ��һ�� SQL �����ӵ��洢������
   * @param strSQL          �����뼯�ϵ� SQL ���
   * @return int             �ڴ洢�����е�����
   *                        ������ʧ���򷵻� -1
   */
  public int addSQL(String strSQL)
  {
    if (this.m_lst_SQL.add(strSQL))
    {
      return this.m_lst_SQL.size() - 1;
    }
    return -1;
  }
  
  /**
   * ��ô洢�е� SQL ���
   * @param intIndex        �洢�����е�������
   * @return String          SQL ���
   *                        ��������Ų���ȷ���򷵻� null
   */
  public String getSQL(int intIndex)
  {
    if (!valIndex(intIndex))
    {
      return null;
    }

    return this.m_lst_SQL.get(intIndex);
  }
 
  /**
   * ��ô洢�е� SQL ��������
   * @param strSQL        SQL���
   * @return int           SQL �����ڴ洢�����е�������
   */
  public int getSQLIndex(String strSQL)
  {
    return this.m_lst_SQL.indexOf(strSQL);
  }
  
  /**
   * ���´洢�� SQL ���
   * @param strSQL       SQL ���
   * @param intIndex     �ڴ洢�����е�������
   */
  public void setSQL(String strSQL, int intIndex)
  {
    if (!valIndex(intIndex))
    {
      return;
    }

    this.m_lst_SQL.set(intIndex, strSQL);
  }

  /**
   * ɾ���洢�� SQL ���
   * @param intIndex     �ڴ洢�����е�������
   */
  public void deleteSQL(int intIndex)
  {
    if (!valIndex(intIndex))
    {
      return;
    }

    this.m_lst_SQL.remove(intIndex);
  }
  
  /**
   * ��մ洢�� SQL ���
   */
  public void clear()
  {
    this.m_lst_SQL.clear();
  }
  
  /**
   * ���鴫����������Ƿ��ڴ洢���ϵ���Ч��Χ��
   * @param intIndex
   * @return boolean     �Ϸ�Ϊtrue;�Ƿ�Ϊfalse
   */
  public boolean valIndex(int intIndex)
  {
    boolean b1 = (0 - intIndex) > 0 ? false : true;
    boolean b2 = (intIndex - this.m_lst_SQL.size())>=0 ? false : true;
    return b1 & b2;
  }
 
  /**
   * ִ�д洢�е� SQL ���
   * @return String        ��    ����ִ�гɹ�
   *                       ����  ���ش�����Ϣ
   */
  public String runSQL()
  {

    /// �������
    DBConnection dbc = new DBConnection();
    Connection conn = dbc.getConnection();
    
    try
    { 
      conn.setAutoCommit(false);
      Iterator<String> it = this.m_lst_SQL.iterator();
      
      while(it.hasNext())
      { 
        String str_SQL = it.next();
        
        PreparedStatement stmt = conn.prepareStatement(str_SQL);
        
        stmt.executeUpdate();
        
        stmt.close();
      }
      
      conn.commit();
      
      return null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        conn.rollback();
        
        // �������SQL��װ�� SQLSTORAGE ���У����ڲ�ѯ����
        conn.setAutoCommit(false);
        Iterator<String> it = this.m_lst_SQL.iterator();
        String strObjID = NumAssign.assignID_B("000011",General.curYear2() + General.curMonth() + General.curDay());

        while(it.hasNext())
        { 
          String str_SQL = it.next();
          if(DBConnection.JDBCTYPE.equals("3")) // Ms Sql
        	  str_SQL = "INSERT INTO SQLSTORAGE(OBJID,SQLSCRIPT) VALUES ('" + strObjID + "','" + str_SQL.replaceAll(Common.QUOTE, Common.MARK) + "')";
          else if(DBConnection.JDBCTYPE.equals("2")) // Oralce
        	  str_SQL = "INSERT INTO SQLSTORAGE(SQLID,OBJID,SQLSCRIPT) VALUES (SEQ_SQLID.NEXTVAL,'" + strObjID + "','" + str_SQL.replaceAll(Common.QUOTE, Common.MARK) + "')";
          else if(DBConnection.JDBCTYPE.equals("4")) // MySql
            str_SQL = "INSERT INTO SQLSTORAGE(OBJID,SQLSCRIPT) VALUES ('" + strObjID + "','" + str_SQL.replaceAll(Common.QUOTE, Common.MARK) + "')";

          PreparedStatement stmt = conn.prepareStatement(str_SQL);
          
          stmt.executeUpdate();
          
          stmt.close();
        }
        
        conn.commit();
        
      }
      catch (SQLException ex)
      {
        return ex.getMessage();
      }
      return e.getMessage();
    }
    finally
    {
      dbc.freeConnection();
    }
    
  }
  
}