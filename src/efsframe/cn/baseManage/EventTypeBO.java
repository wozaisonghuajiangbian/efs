package efsframe.cn.baseManage;

import efsframe.cn.base.Operation;
import efsframe.cn.base.ReturnDoc;
import efsframe.cn.cache.DicCache;
import efsframe.cn.db.DataStorage;
import efsframe.cn.declare.Common;
import efsframe.cn.declare.Table;
import efsframe.cn.func.General;

public class EventTypeBO
{
 
  /*********************************************************
   * ����¼�����
   * @param strXml            XML ������Ϣ
   * @return XML              ������Ϣ
  ***********************************************************/
  public static String addOrEdit(String strXml) throws Exception
  {
    String xml = Operation.addOrEdit(strXml,Table.EVENTTYPE);
    DicCache.getInstance().refresh("EVENTTYPE");
    return xml;
  }
  /**
   * ɾ���¼�����
   * @param strEventTypeID
   * @return
   * @throws Exception
   */
  public static String drop(String strEventTypeID) throws Exception
  {
    DataStorage storage = new DataStorage();
    // ɾ����ɫȨ��
    String strSQL = "DELETE FROM ROLEPOWER WHERE EVENTTYPEID='" + strEventTypeID + "'";
    storage.addSQL(strSQL);
    
    // ɾ����Ӧ���¼�
    strSQL = "DELETE FROM EVENTTYPE WHERE EVENTTYPEID='" + strEventTypeID + "'";
    storage.addSQL(strSQL);
    
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
    
    return returndoc.getXML();  
    
  }
}
