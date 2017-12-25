package efsframe.cn.baseManage;

import java.util.Enumeration;
import java.util.Hashtable;
import org.dom4j.Element;
import org.dom4j.Node;
import efsframe.cn.base.DataDoc;
import efsframe.cn.base.NumAssign;
import efsframe.cn.base.ReturnDoc;
import efsframe.cn.cache.DicCache;
import efsframe.cn.cache.UserCache;
import efsframe.cn.db.DataStorage;
import efsframe.cn.db.SQLAnalyse;
import efsframe.cn.declare.Common;
import efsframe.cn.declare.Field;
import efsframe.cn.declare.Table;
import efsframe.cn.func.General;

public class UserBO
{
  
  /*********************************************************
   * 添加用户信息
   * @param strXml            XML 数据信息
   * @return XML              返回信息
  ***********************************************************/

  public static String addNew(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // 创建执行对象
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.USERLIST);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // 解析sql语句
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.USERLIST,i);
      Node node = ele.selectSingleNode(Field.USERID);
      String strId = NumAssign.assignID_A("000002");
      hashId.put(strId,strId);
      node.setText(strId);
      
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // 执行
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
    DicCache.getInstance().refresh("USERLIST");
    return returndoc.getXML();      
  }
  
  /*********************************************************
   * 修改用户信息
   * @param strXml            XML 数据信息
   * @return XML              返回信息
  ***********************************************************/

  public static String edit(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // 创建执行对象
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.USERLIST);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // 解析sql语句
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.USERLIST,i);
      Node node = ele.selectSingleNode(Field.USERID);
      String strId = node==null?null:node.getText();
      hashId.put(strId,strId);
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // 执行
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
    DicCache.getInstance().refresh(Table.USERLIST);
    return returndoc.getXML();      
  }
  
  /*********************************************************
   * 删除用户信息，同步删除掉用户对应的权限文件
   * @param strXml            XML 数据信息
   * @return XML              返回信息
  ***********************************************************/

  public static String drop(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // 创建执行对象
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.USERLIST);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // 解析sql语句
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.USERLIST,i);
      Node node = ele.selectSingleNode(Field.USERID);
      String strId = node==null?null:node.getText();
      hashId.put(strId,strId);
      storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    }
    // 执行
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
    
    Enumeration enumkeys = hashId.keys();
    while(enumkeys.hasMoreElements())
    {
      UserCache.getInstance().remove((String)enumkeys.nextElement());
    }
    DicCache.getInstance().refresh("USERLIST");
    return returndoc.getXML();  
    
  }
  
  /*********************************************************
   * 设置用户口令
   * @param strXml            XML 数据信息
   * @return XML              返回信息
  ***********************************************************/
  public static String setPassword(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // 创建执行对象
    DataStorage storage = new DataStorage();
    String str_UserID = "";
    // 解析sql语句
    Element ele = (Element)doc.getDataNode(Table.USERLIST,0);
    str_UserID = ele.selectSingleNode(Field.USERID).getText();
    storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
    // 执行
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
    
    UserCache.getInstance().refresh(str_UserID);
    
    return returndoc.getXML();      
  }
}
