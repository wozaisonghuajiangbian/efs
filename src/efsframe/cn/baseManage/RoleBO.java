package efsframe.cn.baseManage;

import org.dom4j.*;
import efsframe.cn.db.DataStorage;
import efsframe.cn.db.SQLAnalyse;
import efsframe.cn.declare.*;
import efsframe.cn.func.*;
import java.sql.*;
import java.util.*;
import efsframe.cn.base.DataDoc;
import efsframe.cn.base.NumAssign;
import efsframe.cn.base.Operation;
import efsframe.cn.base.ReturnDoc;
import efsframe.cn.cache.*;
import efsframe.cn.db.DBConnection;


public class RoleBO
{

  /*********************************************************
   * ��ӽ�ɫ
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/
  public static String addNew(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // ����ִ�ж���
    DataStorage storage = new DataStorage();
    ReturnDoc returndoc = new ReturnDoc();
    try
    {
	    int size = doc.getDataNum(Table.ROLEBASIC);
	   
	    // ����sql���
	    for(int i=0;i<size;i++)
	    {  
	      Element ele = (Element)doc.getDataNode(Table.ROLEBASIC,i);
	      Node node = ele.selectSingleNode(Field.ROLEID);
	      String strId = NumAssign.assignID_A("000001");
	      
	      node.setText(strId);
	      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
	    }
	    // ִ��
	    String strReturn = storage.runSQL();
	    if(!General.empty(strReturn))
	    {
		    returndoc.addErrorResult(Common.RT_FUNCERROR);
		    returndoc.setFuncErrorInfo(strReturn);
	    }
	    else
	    {  
	      returndoc.addErrorResult(Common.RT_SUCCESS);
	    }
    }
    catch(Exception e)
    {
	    returndoc.addErrorResult(Common.RT_FUNCERROR);
	    returndoc.setFuncErrorInfo(e.getMessage());
    }
    
    return returndoc.getXML();  
  }
 
  /*********************************************************
   * �޸Ľ�ɫ
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/

  public static String edit(String strXml) throws Exception
  {
    return Operation.addOrEdit(strXml,Table.ROLEBASIC);
  }
  
  /*********************************************************
   * ɾ����ɫ��Ϣ
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/

  public static String drop(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // ����ִ�ж���
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.ROLEBASIC);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // ����sql���
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.ROLEBASIC,i);
      Node ndRole = ele.selectSingleNode(Field.ROLEID);
      String strRoleId = ndRole==null?null:ndRole.getText();
      String sql = "select userid from roleuser where roleid = '" + strRoleId + "'";

    	DBConnection dbc = new DBConnection();
    	ResultSet rst = null;
      try
      {
       rst = dbc.excuteQuery(sql);
        while(rst.next())
        {
          hashId.put(rst.getString("userid"),rst.getString("userid"));
        }
        //if(conn!=null)
          //conn.close();
      }  
      catch(Exception e)
      {
        //
      }
    	finally
    	{
    	rst.close();
    	dbc.freeConnection();
    	}
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // ִ��
    String strReturn = storage.runSQL();
    ReturnDoc returndoc = new ReturnDoc();
    if(!General.empty(strReturn))
    {
	    returndoc.addErrorResult(Common.RT_FUNCERROR);
	    returndoc.setFuncErrorInfo(strReturn);
    }
    else
    {  
      returndoc.addErrorResult(Common.RT_SUCCESS);
    }
    
    Enumeration enums = hashId.elements();
    while(enums.hasMoreElements())
    {
      UserCache.getInstance().refresh((String)enums.nextElement());
    }
    return returndoc.getXML();  
    
  }
  
  /*********************************************************
   * ���ɫ����û�
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/

  public static String addUserToRole(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // ����ִ�ж���
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.ROLEUSER);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // ����sql���
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.ROLEUSER,i);
      Node node = ele.selectSingleNode(Field.USERID);
      String strId = node==null?null:node.getText();
      hashId.put(strId,strId);
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // ִ��
    String strReturn = storage.runSQL();
    ReturnDoc returndoc = new ReturnDoc();
    if(!General.empty(strReturn))
    {
	    returndoc.addErrorResult(Common.RT_FUNCERROR);
	    returndoc.setFuncErrorInfo(strReturn);
    }
    else
    {  
      returndoc.addErrorResult(Common.RT_SUCCESS);
    }
    
    Enumeration enums = hashId.elements();
    while(enums.hasMoreElements())
    {
      UserCache.getInstance().refresh((String)enums.nextElement());
    }
    return returndoc.getXML();    
  }
  
  /*********************************************************
   * �ӽ�ɫ��ɾ���¼�����Ȩ��
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/

  public static String dropEventFromRole(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // ����ִ�ж���
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.ROLEPOWER);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // ����sql���
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.ROLEPOWER,i);
      Node node = ele.selectSingleNode(Field.ROLEID);
      String strId = node==null?null:node.getText();
      String sql = "select userid from roleuser where roleid = '" + strId + "'";

   	  DBConnection dbc = new DBConnection();
  	  ResultSet rst = null;
      try
      {
        rst = dbc.excuteQuery(sql);
        while(rst.next())
        {
          hashId.put(rst.getString("userid"),rst.getString("userid"));
        }
        
      }
      catch(Exception e)
      {
      }
    	finally
    	{
    	rst.close();
    	dbc.freeConnection();
    	}
      
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // ִ��
    String strReturn = storage.runSQL();
    ReturnDoc returndoc = new ReturnDoc();
    if(!General.empty(strReturn))
    {
	    returndoc.addErrorResult(Common.RT_FUNCERROR);
	    returndoc.setFuncErrorInfo(strReturn);
    }
    else
    {  
      returndoc.addErrorResult(Common.RT_SUCCESS);
    }
    
    Enumeration enums = hashId.elements();
    while(enums.hasMoreElements())
    {
      UserCache.getInstance().refresh((String)enums.nextElement());
    }
    return returndoc.getXML();    
  } 
  
  /*********************************************************
   * �ӽ�ɫ��ɾ���û�
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/

  public static String dropUserFromRole(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // ����ִ�ж���
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.ROLEUSER);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // ����sql���
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.ROLEUSER,i);
      Node node = ele.selectSingleNode(Field.USERID);
      String strId = node==null?null:node.getText();
      hashId.put(strId,strId);
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // ִ��
    String strReturn = storage.runSQL();
    ReturnDoc returndoc = new ReturnDoc();
    if(!General.empty(strReturn))
    {
	    returndoc.addErrorResult(Common.RT_FUNCERROR);
	    returndoc.setFuncErrorInfo(strReturn);
    }
    else
    {  
      returndoc.addErrorResult(Common.RT_SUCCESS);
    }
    
    Enumeration enums = hashId.elements();
    while(enums.hasMoreElements())
    {
      UserCache.getInstance().refresh((String)enums.nextElement());
    }
    return returndoc.getXML();    
  } 
  
  /*********************************************************
   * ���ɫ����¼�����
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/

  public static String addEventToRole(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // ����ִ�ж���
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.ROLEPOWER);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // ����sql���
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.ROLEPOWER,i);
      Node node = ele.selectSingleNode(Field.ROLEID);
      String strId = node==null?null:node.getText();
      String sql = "select userid from roleuser where roleid = '" + strId + "'";

    	DBConnection dbc = new DBConnection();
    	ResultSet rst = null;
      try
      {
        rst = dbc.excuteQuery(sql);
        while(rst.next())
        {
          hashId.put(rst.getString("userid"),rst.getString("userid"));
        }
        
      }
      catch(Exception e)
      {
        // ��������e.printStackTrace();
      }
    	finally
    	{
    	rst.close();
    	dbc.freeConnection();
    	}
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // ִ��
    String strReturn = storage.runSQL();
    ReturnDoc returndoc = new ReturnDoc();
    if(!General.empty(strReturn))
    {
	    returndoc.addErrorResult(Common.RT_FUNCERROR);
	    returndoc.setFuncErrorInfo(strReturn);
    }
    else
    {  
      returndoc.addErrorResult(Common.RT_SUCCESS);
    }
    
    Enumeration enums = hashId.elements();
    while(enums.hasMoreElements())
    {
      UserCache.getInstance().refresh((String)enums.nextElement());
    }
    return returndoc.getXML();    
  } 
  
}
