package efsframe.cn.baseManage;

import java.util.Enumeration;
import java.util.Hashtable;
import org.dom4j.Element;
import org.dom4j.Node;
import efsframe.cn.db.DataStorage;
import efsframe.cn.db.SQLAnalyse;
import efsframe.cn.declare.Common;
import efsframe.cn.declare.Field;
import efsframe.cn.declare.Table;
import efsframe.cn.func.General;
import efsframe.cn.base.DataDoc;
import efsframe.cn.base.ReturnDoc;
import efsframe.cn.cache.MUnitCache;
import efsframe.cn.cache.DicCache;

public class UnitsBO
{
  /*********************************************************
   * 添加管理单位
   * @param strXml            XML 数据信息
   * @return XML              返回信息
  ***********************************************************/

  public static String addOrEdit(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // 创建执行对象
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.MANAGEUNIT);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // 解析sql语句
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.MANAGEUNIT,i);
      Node node = ele.selectSingleNode(Field.MUNITID);
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
      MUnitCache.getInstance().refresh((String)enums.nextElement());
    }
    DicCache.getInstance().refresh("MANAGEUNIT");
    return returndoc.getXML();  
  }
 
  
}
