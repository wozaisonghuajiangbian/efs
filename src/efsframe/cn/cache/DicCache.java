package efsframe.cn.cache;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import efsframe.cn.db.DBConnection;
import efsframe.cn.declare.*;
import efsframe.cn.func.*;

public class DicCache implements java.io.Serializable
{
  private static final long serialVersionUID = -7589021673801786010L;
  
  private static DicCache m_obj_DicCache;
  private static Hashtable<String, ArrayList<String[]>> m_htb_Dic;

  /// ����һ��Ĭ�ϵ�·������·��ʵ��������Ч��
  /// �ڻ����ʼ����ʱ��ϵͳ�ḳ���Ӧʵ��·��
  private static String m_str_DicPath = "";

  /**
   * ʵ����ʼ��
   */
  private DicCache()
  {
    try
    {
      if(m_obj_DicCache==null)
        init();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      m_obj_DicCache = null;
    }
  }

  /**
   * ����ֵ仺���ʵ��
   * @return �ֵ仺���ʵ��
   */
  public static synchronized DicCache getInstance()
  {
    if (m_obj_DicCache==null)
    {
      m_obj_DicCache = new DicCache();
    }

    return m_obj_DicCache;
  }
  
  /**
   * �����ֵ��ļ������·��
   * @param strPath           �ֵ��ļ������·��
   */
  public static void setDicPath(String strPath)
  {
    m_str_DicPath = strPath;
  }
  
  /**
   * ����ֵ��ļ������·��
   * @return �ֵ��ļ������·��
   */
  public static String getDicPath()
  {
    return m_str_DicPath;
  }

  /**
   * �ֵ仺�����ݵĳ�ʼ��
   */
  private void init() throws Exception
  {
	DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
    {
      m_htb_Dic = new Hashtable<String, ArrayList<String[]>>();

      /// ���Ȳ�ѯ�������ֵ������
      String str_SQL = Common.SELECT  + Field.DICNAME +
                       Common.S_FROM  + Table.DICLIST;
      
      rst = dbc.excuteQuery(str_SQL);

      /// �������ֵ����Ƽ��뵽����� HashTable ��
      while(rst.next())
      {  
        refresh(rst.getString(Field.DICNAME));
      }
      
      rst.close();

      /// ����ϵͳ�����ֵ�
      refresh(Table.MANAGEUNIT);
      refresh(Table.AFFAIRTYPE);
      refresh(Table.EVENTTYPE);
      refresh(Table.USERLIST);

      System.out.println(new String(("������������" + m_htb_Dic.size() + "���ֵ�").getBytes(),"GBK"));

    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }  
    finally
    {
    	rst.close();
    	dbc.freeConnection();
    }
  }

  /**
   * ˢ�µ����ֵ仺�������
   * @param strDicName        �ֵ�����
   */
  public synchronized void refresh(String strDicName) throws Exception
  {
    /// ��֯��ѯ�ֵ�� SQL ���
    String str_SQL = "";
    
    /// ��ϵͳ�����ֵ���д���
    if (strDicName.equalsIgnoreCase(Table.MANAGEUNIT))
    {
      str_SQL = Common.SELECT  + Field.MUNITID               + Common.SPACE   + Field.DIC_CODE  + Common.COMMA   +
                                 Field.MUNITNAME             + Common.SPACE   + Field.DIC_TEXT  + Common.COMMA   +
                                 Field.VALID                 + Common.SPACE   + Field.DIC_VALID +
                Common.S_FROM  + Table.MANAGEUNIT            +
                Common.S_WHERE + Field.MTYPE                 + Common.N_EQUAL + General.addQuotes(Common.USERTYPE_SP) +
                Common.S_ORDER + Field.MUNITID;
    }
    else if (strDicName.equalsIgnoreCase(Table.AFFAIRTYPE))
      str_SQL = Common.SELECT  + Field.AFFAIRTYPEID          + Common.SPACE   + Field.DIC_CODE   + Common.COMMA  +
                                 Field.AFFAIRTYPENAME        + Common.SPACE   + Field.DIC_TEXT   + Common.COMMA  +
                          General.addQuotes(Common.FLG_TRUE) + Common.SPACE   + Field.DIC_VALID  +
                Common.S_FROM  + Table.AFFAIRTYPE            +
                Common.S_ORDER + Field.AFFAIRTYPEID;

    else if (strDicName.equalsIgnoreCase(Table.EVENTTYPE))
      str_SQL = Common.SELECT  + Field.EVENTTYPEID           + Common.SPACE   + Field.DIC_CODE   + Common.COMMA  +
                                 Field.EVENTTYPENAME         + Common.SPACE   + Field.DIC_TEXT   + Common.COMMA  +
                                 General.addQuotes(Common.FLG_TRUE) + Common.SPACE   + Field.DIC_VALID  +
                Common.S_FROM  + Table.EVENTTYPE             +
                Common.S_ORDER + Field.EVENTTYPEID;

    else if (strDicName.equalsIgnoreCase(Table.USERLIST))
      str_SQL = Common.SELECT  + Field.USERID                + Common.SPACE   + Field.DIC_CODE   + Common.COMMA  +
                Field.USERNAME                               + Common.SPACE   + Field.DIC_TEXT   + Common.COMMA  +
                General.addQuotes(Common.FLG_TRUE)           + Common.SPACE   + Field.DIC_VALID  +
                Common.S_FROM  + Table.USERLIST              +
                Common.S_WHERE + Field.USERTYPE              + Common.NOT_IN  + General.addBracket(Common.FLG_TRUE + Common.COMMA + Common.FLG_FALSE) +
                Common.S_ORDER + Field.USERID;
    else
      str_SQL = Common.SELECT  + Field.DIC_CODE              + Common.COMMA   +
                                 Field.DIC_TEXT              + Common.COMMA   +
                                 Field.DIC_VALID             +
                Common.S_FROM  + Table.DICDATA               +
                Common.S_WHERE + Field.DICNAME               + Common.EQUAL   + General.addQuotes(strDicName)    +
                Common.S_ORDER + Field.DIC_CODE;
    DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
    {
      rst = dbc.excuteQuery(str_SQL);
      ArrayList<String[]> arr_DicContent = new ArrayList<String[]>();

      while(rst.next())
      {
        String str_DicCode  = rst.getString(Field.DIC_CODE);
        String str_DicText  = rst.getString(Field.DIC_TEXT);
        String str_DicVaild = rst.getString(Field.DIC_VALID);

        arr_DicContent.add(new String[]{str_DicCode, str_DicText, str_DicVaild});
      
      }
      m_htb_Dic.put(strDicName, arr_DicContent);

      System.out.println("�ѽ�[" + strDicName + "]�ֵ����뵽������");
    }
	catch(Exception e)
	{
		
	}
	finally
    {
    	rst.close();
    	dbc.freeConnection();
    }
  }
 
  /**
   * ����ָ���ֵ����ƻ���ֵ仺�����
   * @return ArrayList     �ֵ�����
   */
  public ArrayList getDicByName(String strDicName)
  {
    return (ArrayList)m_htb_Dic.get(strDicName);
  }

  /**
   * ���ָ���ֵ��е��ֵ����ݶ�Ӧ���ֵ����
   * @param strDicName   �ֵ�����
   * @param strDicText   �ֵ�����
   * @return String        �ֵ����
   */
  public String getCode(String strDicName, String strDicText) throws Exception
  {
    ArrayList arr_DicContent = (ArrayList)m_htb_Dic.get(strDicName);

    /// ����ָ�����ֵ����Ʋ�������
    if (arr_DicContent==null)
      return null;

    Iterator it = arr_DicContent.iterator();

    while (it.hasNext())
    {
      String[] str_DicItem = (String[])it.next();

      if (strDicText.equalsIgnoreCase(str_DicItem[1]))
      {
        return str_DicItem[0];
      }
    }

    /// δ���ҵ���Ӧ�ֵ�����
    return null;
  }

  /**
   * ���ָ���ֵ��е��ֵ�����Ӧ���ֵ�����
   * @param strDicName   �ֵ�����
   * @param strDicCode   �ֵ����
   * @return String        �ֵ�����
   */
  public String getText(String strDicName, String strDicCode) throws Exception
  {
    ArrayList arr_DicContent = (ArrayList)m_htb_Dic.get(strDicName);
    
    /// ����ָ�����ֵ����Ʋ�������
    if (arr_DicContent==null)
      return null;

    Iterator it = arr_DicContent.iterator();

    while(it.hasNext())
    {
      String[] str_DicItem = (String[])it.next();

      if (strDicCode.equalsIgnoreCase(str_DicItem[0]))
      {
        return str_DicItem[1];
      }
    }

    /// δ���ҵ���Ӧ�ֵ����
    return null;
  }

  /**
   * ���ָ���ֵ��е��ֵ�����Ƿ���Ч��ֵ
   * @param strDicName   �ֵ�����
   * @param strDicCode   �ֵ����
   * @return String        �ֵ���Ŀ�Ƿ���Ч
   */
  public String getValid(String strDicName, String strDicCode) throws Exception
  {
    ArrayList arr_DicContent = (ArrayList)m_htb_Dic.get(strDicName);

    /// ����ָ�����ֵ����Ʋ�������
    if (arr_DicContent==null)
      return null;

    Iterator it = arr_DicContent.iterator();

    while (it.hasNext())
    {
      String[] str_DicItem = (String[])it.next();

      if (strDicCode.equalsIgnoreCase(str_DicItem[0]))
      {
        return str_DicItem[2];
      }
    }

    /// δ���ҵ���Ӧ�ֵ����
    return null;
  }
  

}
