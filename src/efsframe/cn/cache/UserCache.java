package efsframe.cn.cache;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import efsframe.cn.db.DBConnection;
import efsframe.cn.declare.*;
import efsframe.cn.func.*;

public class UserCache implements java.io.Serializable
{
  private static final long serialVersionUID = -4411484701131093447L;

  private static UserCache m_obj_UserCache;
  private static Hashtable<String, UserLogonInfo> m_htb_Users;
  
  /**
   * ʵ����ʼ��
   */
  private UserCache()
  {
    try
      {
         if(m_obj_UserCache==null) init();
      }
    catch(Exception e)
    {
      e.printStackTrace();
      m_obj_UserCache = null;
    }
  }

  /**
   * �����û����Ʒ����û�����
   * @param strUserTitle          �û���¼��
   * @return �û��������
   */
  public UserInfo getUserByLoginName(String strUserTitle)
  {
    Enumeration enr_Users = m_htb_Users.elements();

    UserInfo obj_UserInfo;

    while (enr_Users.hasMoreElements())
    {
      obj_UserInfo = (UserInfo)enr_Users.nextElement();

      String str_UserTitle = obj_UserInfo.getUserTitle();

      if (str_UserTitle.equalsIgnoreCase(strUserTitle))
      {
        return obj_UserInfo;
      }
    }

    return null;
  }
  
  public UserInfo getUserByUserId(String strUserId)
  {
    Enumeration enr_Users = m_htb_Users.elements();

    UserInfo obj_UserInfo = null;

    while (enr_Users.hasMoreElements())
    {
      obj_UserInfo = (UserInfo)enr_Users.nextElement();

      String str_UserId = obj_UserInfo.getUserID();

      if (str_UserId.equalsIgnoreCase(strUserId))
      {
        return obj_UserInfo;
      }
    }

    return null;
  }
 
  /**
   * �����û����Ʒ����û���¼����
   * @param strUserTitle          �û���¼��
   * @return �û���¼�������
   */
  public UserLogonInfo getUserLogonByLoginName(String strUserTitle)
  {
    Enumeration enr_Users = m_htb_Users.elements();

    UserLogonInfo obj_UserLogonInfo;

    //���������еĵ��û���
    while (enr_Users.hasMoreElements())
    {
      //��ȡ�����е��û���
      obj_UserLogonInfo = (UserLogonInfo)enr_Users.nextElement();

      String str_UserTitle = obj_UserLogonInfo.getUserTitle();
      //���¼���û������бȶ�
      if (str_UserTitle.equalsIgnoreCase(strUserTitle))
      {
        return obj_UserLogonInfo;
      }
    }
    //��������в������û���Ϣ�����ؿ�
    return null;
  }
 

 
  
  /**
   * ����û���������ʵ��
   * @return �û������ʵ��
   * synchronized�߳���
   */
  public static synchronized UserCache getInstance()
  {
    if(m_obj_UserCache == null)
    {
      m_obj_UserCache = new UserCache();
    }

    return m_obj_UserCache;
  }
  
  /**
   * �û�����ʵ���ĳ�ʼ��
   */
  private void init() throws Exception
  {
    try
    {
      m_htb_Users = new Hashtable<String, UserLogonInfo>();

      /*
       * ���⣺ ����û���������ܵ������û������ʱ��������ݿ��쳣
       * ���ã� ��������������˸��죬��ԭ������ʱ�ͼ��������û������޸�Ϊ��һ���û��ڵ�һ�ε�½��ʱ��ȥ���������û���Ϣ
       *        ���Ժ��ٵ�½��ֱ�Ӵӻ���ȡ��½��Ϣ
       * 
      /// ��ѯ�����û�����Ϣ
      String str_SQL = Common.SELECT  + Common.ALL     +
                       Common.S_FROM  + Table.USERLIST +
                       Common.S_ORDER + Field.USERID;
  	  rst = dbc.excuteQuery(str_SQL);
      


      
      while (rst.next())
      {  
        /// ����û���Ϣ
        UserLogonInfo obj_UserLogonInfo = new UserLogonInfo();
        obj_UserLogonInfo.setValueByResult(rst);

        m_htb_Users.put(obj_UserLogonInfo.getUserID(), obj_UserLogonInfo);

        m_htb_UserRights.put(obj_UserLogonInfo.getUserID(), setUserRightsByUserID(obj_UserLogonInfo.getUserID()));
      }
      
      System.out.println(new String(("�û�������������" + m_htb_Users.size() + "���û���Ϣ").getBytes(),"GBK"));
      
      rst.close();
      */
    }
    catch(Exception e)
    {
      throw e;
    }
    
  }

  /**
   * ���µ����û�������������
   * @param strUserID           �û����
   */
  public synchronized void refresh(String strUserID) throws Exception
  {
	DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
	{
      /// ��ѯ�����û�����Ϣ
      String str_SQL = Common.SELECT  + Common.ALL     +
                       Common.S_FROM  + Table.USERLIST +
                       Common.S_WHERE + Field.USERID   + Common.EQUAL + General.addQuotes(strUserID);

      rst = dbc.excuteQuery(str_SQL);

      if(m_htb_Users==null)
      {
        m_htb_Users = new Hashtable<String, UserLogonInfo>();
      }


      while (rst.next())
      {  
        /// ����û���Ϣ
        UserLogonInfo obj_UserLogonInfo = (UserLogonInfo)m_htb_Users.get(rst.getString(Field.USERTITLE));

        if(obj_UserLogonInfo==null) obj_UserLogonInfo = new UserLogonInfo();

        obj_UserLogonInfo.setValueByResult(rst);

        m_htb_Users.put(obj_UserLogonInfo.getUserID(), obj_UserLogonInfo);

      }
      
    }
    catch(Exception e)
    {
      throw e;
    }
    finally
    {
    	rst.close();
    	dbc.freeConnection();
    }
  }


  /**
   * ���µ����û�������������
   * @param strUserTitle           �û���
   */
  public synchronized void refresh_u(String strUserTitle) throws Exception
  {
	DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
	{
      /// ��ѯ�����û�����Ϣ
      String str_SQL = Common.SELECT  + Common.ALL     +
                       Common.S_FROM  + Table.USERLIST +
                       Common.S_WHERE + Field.USERTITLE   + Common.EQUAL + General.addQuotes(strUserTitle);

      rst = dbc.excuteQuery(str_SQL);

      if(m_htb_Users==null)
      {
        m_htb_Users = new Hashtable<String, UserLogonInfo>();
      }

      //�û����������޷������Ϣ
      while (rst.next())
      {  
        /// ����û���Ϣ
        UserLogonInfo obj_UserLogonInfo = (UserLogonInfo)m_htb_Users.get(rst.getString(Field.USERTITLE));

        if(obj_UserLogonInfo==null) obj_UserLogonInfo = new UserLogonInfo();

        obj_UserLogonInfo.setValueByResult(rst);

        m_htb_Users.put(obj_UserLogonInfo.getUserID(), obj_UserLogonInfo);
        
      }
      
    }
    catch(Exception e)
    {
      throw e;
    }
    finally
    {
    	rst.close();
    	dbc.freeConnection();
    }
  }
  /**
   * ���û�������ɾ��һ���û��������
   * @param strUserID           �û����
   */
  public synchronized void remove(String strUserID) throws Exception
  {
    try
    {
      m_htb_Users.remove(strUserID);
    }
    catch(Exception e)
    {
      throw e;
    }
  }

  // ����û��Ƿ�ӵ��һ���¼���Ȩ��
  public static boolean getUserRightByEvent(String strUserID,String strEventtypeID) throws SQLException
  {
    DBConnection dbc = new DBConnection();
    ResultSet rst = null;
    boolean rsBln = false;
    boolean bln = false;
    //�鿴Ȩ�ޱ�(VW_USERRIGHT)�У����û��Ƿ�ӵ�и��¼� Ȩ��
    try
      {
        rst = dbc.excuteQuery("SELECT USERID FROM VW_USERRIGHT WHERE USERID='" + strUserID + "' AND EVENTTYPEID='" + strEventtypeID + "'");
        rsBln = true;
        while(rst.next())
        {
          bln = true;
        }
      }
    catch(Exception e)
    {
      
    }
    finally
    {
      if(rsBln)
        rst.close();
      dbc.freeConnection();
    }
    return bln;
  }
  
}
