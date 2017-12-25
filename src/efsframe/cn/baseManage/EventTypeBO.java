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
   * 添加事件类型
   * @param strXml            XML 数据信息
   * @return XML              返回信息
  ***********************************************************/
  public static String addOrEdit(String strXml) throws Exception
  {
    String xml = Operation.addOrEdit(strXml,Table.EVENTTYPE);
    DicCache.getInstance().refresh("EVENTTYPE");
    return xml;
  }
  /**
   * 删除事件类型
   * @param strEventTypeID
   * @return
   * @throws Exception
   */
  public static String drop(String strEventTypeID) throws Exception
  {
    DataStorage storage = new DataStorage();
    // 删除角色权限
    String strSQL = "DELETE FROM ROLEPOWER WHERE EVENTTYPEID='" + strEventTypeID + "'";
    storage.addSQL(strSQL);
    
    // 删除对应的事件
    strSQL = "DELETE FROM EVENTTYPE WHERE EVENTTYPEID='" + strEventTypeID + "'";
    storage.addSQL(strSQL);
    
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
    
    return returndoc.getXML();  
    
  }
}
