package efsframe.cn.cache;

import java.sql.ResultSet;
import java.util.Hashtable;

import efsframe.cn.db.DBConnection;
import efsframe.cn.declare.*;
import efsframe.cn.func.*;

public class MUnitCache implements java.io.Serializable
{
  private static final long serialVersionUID = 2915697131106064790L;
  private static MUnitCache m_obj_MUnitCache;
  private static Hashtable<String, MUnitInfo> m_htb_MUnit;
  
  /**
   * ����ʵ����
   */
  private MUnitCache()
  {
    try
    {
      if(m_obj_MUnitCache==null)
        init();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      m_obj_MUnitCache = null;
    }
  }
  
  /**
   * ��øö���Ļ���ʵ��
   * @return MUnitCache
   */
  public static synchronized MUnitCache getInstance()
  {
    if(m_obj_MUnitCache == null)
    {
      m_obj_MUnitCache = new MUnitCache();
    }
    return m_obj_MUnitCache;
  }
  
  /**
   * ��ʼ���ö���Ļ���ʵ��
   */
  private void init() throws Exception
  {
	DBConnection dbc = new DBConnection();
    ResultSet rst = null;
	try
	{
      /// ��ѯ���й���λ��Ϣ
      String str_SQL = Common.SELECT + Common.ALL +
                       Common.S_FROM + Table.MANAGEUNIT;
      
      rst = dbc.excuteQuery(str_SQL);

      m_htb_MUnit = new Hashtable<String, MUnitInfo>();

      while(rst.next())
      {
        MUnitInfo obj_MUnitInfo = new MUnitInfo();
        obj_MUnitInfo.setValueByResult(rst);
        m_htb_MUnit.put(obj_MUnitInfo.getMUnitID(), obj_MUnitInfo);
      }
      
      rst.close();
      
      System.out.println("������������" + m_htb_MUnit.size() + "������λ��Ϣ");
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
   * ����һ����λ�Ļ�����Ϣ
   */
  public synchronized void refresh(String strUnitID) throws Exception
  {
	DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
	{
      /// ��ѯ�ù���λ����Ϣ
      String str_SQL = Common.SELECT  + Common.ALL       +
                       Common.S_FROM  + Table.MANAGEUNIT +
                       Common.S_WHERE + Field.MUNITID    + Common.EQUAL + General.addQuotes(strUnitID) +
                       Common.S_ORDER + Field.MUNITID;
      // ��ѯ
      rst = dbc.excuteQuery(str_SQL);

      if (m_htb_MUnit==null)
      {
        m_htb_MUnit = new Hashtable<String, MUnitInfo>();
      } /// if (m_htb_MUnit==null)

      /// ������λ����Ϣ���µ�������
      while (rst.next())
      {
        MUnitInfo obj_MUnit = (MUnitInfo)m_htb_MUnit.get(strUnitID);

        if (obj_MUnit==null) obj_MUnit = new MUnitInfo();

        obj_MUnit.setValueByResult(rst);

        m_htb_MUnit.put(strUnitID, obj_MUnit);
      } /// while (rst.next())
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
   * ���ָ������λ�Ļ������
   * @param strUnitID         ��λ���
   * @return MUnitInfo         ���浥λ����
   */
  public MUnitInfo getMUnitByID(String strUnitID) throws Exception
  {
    MUnitInfo obj_MUnit = null;

    if(m_htb_MUnit!=null)
    {
      obj_MUnit = (MUnitInfo)m_htb_MUnit.get(strUnitID);
    }

    return obj_MUnit;
  }

  /**
   * ��ù���λ����
   * @return int               ����λ����
   */
  public synchronized int getCount() throws Exception
  {
    return m_htb_MUnit.size();
  }
  
  /// ���ڲ���
  public static void main(String[] args)
  {
    try
    {
      MUnitCache obj_MUnitCache = new MUnitCache();
      
      System.out.println(obj_MUnitCache.getCount());
      System.out.println(obj_MUnitCache.getMUnitByID("441900000000").getMUnitName());
    }
    catch(Exception e)
    {
    }
  }  
}
