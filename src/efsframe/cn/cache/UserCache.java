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
   * 实例初始化
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
   * 根据用户名称返回用户对象
   * @param strUserTitle          用户登录名
   * @return 用户缓存对象
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
   * 根据用户名称返回用户登录对象
   * @param strUserTitle          用户登录名
   * @return 用户登录缓存对象
   */
  public UserLogonInfo getUserLogonByLoginName(String strUserTitle)
  {
    Enumeration enr_Users = m_htb_Users.elements();

    UserLogonInfo obj_UserLogonInfo;

    //遍历缓存中的的用户名
    while (enr_Users.hasMoreElements())
    {
      //获取缓存中的用户名
      obj_UserLogonInfo = (UserLogonInfo)enr_Users.nextElement();

      String str_UserTitle = obj_UserLogonInfo.getUserTitle();
      //与登录的用户名进行比对
      if (str_UserTitle.equalsIgnoreCase(strUserTitle))
      {
        return obj_UserLogonInfo;
      }
    }
    //如果缓存中不存在用户信息，返回空
    return null;
  }
 

 
  
  /**
   * 获得用户缓存对象的实例
   * @return 用户缓存的实例
   * synchronized线程锁
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
   * 用户缓存实例的初始化
   */
  private void init() throws Exception
  {
    try
    {
      m_htb_Users = new Hashtable<String, UserLogonInfo>();

      /*
       * 问题： 如果用户量大，则可能导致在用户缓存的时候出现数据库异常
       * 作用： 对这个方法进行了改造，将原来启动时就加载所有用户对象修改为，一个用户在第一次登陆的时候去缓存他的用户信息
       *        而以后再登陆就直接从缓存取登陆信息
       * 
      /// 查询所有用户的信息
      String str_SQL = Common.SELECT  + Common.ALL     +
                       Common.S_FROM  + Table.USERLIST +
                       Common.S_ORDER + Field.USERID;
  	  rst = dbc.excuteQuery(str_SQL);
      


      
      while (rst.next())
      {  
        /// 添加用户信息
        UserLogonInfo obj_UserLogonInfo = new UserLogonInfo();
        obj_UserLogonInfo.setValueByResult(rst);

        m_htb_Users.put(obj_UserLogonInfo.getUserID(), obj_UserLogonInfo);

        m_htb_UserRights.put(obj_UserLogonInfo.getUserID(), setUserRightsByUserID(obj_UserLogonInfo.getUserID()));
      }
      
      System.out.println(new String(("用户缓存中已载入" + m_htb_Users.size() + "个用户信息").getBytes(),"GBK"));
      
      rst.close();
      */
    }
    catch(Exception e)
    {
      throw e;
    }
    
  }

  /**
   * 更新单个用户缓存对象的内容
   * @param strUserID           用户编号
   */
  public synchronized void refresh(String strUserID) throws Exception
  {
	DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
	{
      /// 查询出该用户的信息
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
        /// 添加用户信息
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
   * 更新单个用户缓存对象的内容
   * @param strUserTitle           用户名
   */
  public synchronized void refresh_u(String strUserTitle) throws Exception
  {
	DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
	{
      /// 查询出该用户的信息
      String str_SQL = Common.SELECT  + Common.ALL     +
                       Common.S_FROM  + Table.USERLIST +
                       Common.S_WHERE + Field.USERTITLE   + Common.EQUAL + General.addQuotes(strUserTitle);

      rst = dbc.excuteQuery(str_SQL);

      if(m_htb_Users==null)
      {
        m_htb_Users = new Hashtable<String, UserLogonInfo>();
      }

      //用户不存在则无法添加信息
      while (rst.next())
      {  
        /// 添加用户信息
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
   * 从用户缓存中删除一个用户缓存对象
   * @param strUserID           用户编号
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

  // 获得用户是否拥有一个事件的权限
  public static boolean getUserRightByEvent(String strUserID,String strEventtypeID) throws SQLException
  {
    DBConnection dbc = new DBConnection();
    ResultSet rst = null;
    boolean rsBln = false;
    boolean bln = false;
    //查看权限表(VW_USERRIGHT)中，该用户是否拥有该事件 权限
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
