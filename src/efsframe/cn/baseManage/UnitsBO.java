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
   * ��ӹ���λ
   * @param strXml            XML ������Ϣ
   * @return XML              ������Ϣ
  ***********************************************************/

  public static String addOrEdit(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // ����ִ�ж���
    DataStorage storage = new DataStorage();
    int size = doc.getDataNum(Table.MANAGEUNIT);
    Hashtable<String, String> hashId = new Hashtable<String, String>();
    // ����sql���
    for(int i=0;i<size;i++)
    {  
      Element ele = (Element)doc.getDataNode(Table.MANAGEUNIT,i);
      Node node = ele.selectSingleNode(Field.MUNITID);
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
      MUnitCache.getInstance().refresh((String)enums.nextElement());
    }
    DicCache.getInstance().refresh("MANAGEUNIT");
    return returndoc.getXML();  
  }
 
  
}
