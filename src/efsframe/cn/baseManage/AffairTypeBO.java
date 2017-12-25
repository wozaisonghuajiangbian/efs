package efsframe.cn.baseManage;


import efsframe.cn.base.Operation;
import efsframe.cn.base.ReturnDoc;
import efsframe.cn.cache.DicCache;
import efsframe.cn.db.DataStorage;
import efsframe.cn.declare.*;
import efsframe.cn.func.General;

public class AffairTypeBO
{
 
  /*********************************************************
   * 添加\修改事务类型
   * @param strXml XML 数据信息
   * @return String XML 返回信息
  ***********************************************************/
  public static String addOrEdit(String strXml) throws Exception
  {
    
    String xml = Operation.addOrEdit(strXml,Table.AFFAIRTYPE);
    DicCache.getInstance().refresh("AFFAIRTYPE");
    return xml;
  }
  
  public static String drop(String strAffairTypeID) throws Exception
  {
    DataStorage storage = new DataStorage();
    // 删除角色权限
    String strSQL = "DELETE FROM ROLEPOWER WHERE EVENTTYPEID IN (SELECT EVENTTYPEID FROM EVENTTYPE WHERE AFFAIRTYPEID='" + strAffairTypeID + "')";
    storage.addSQL(strSQL);
    
    // 删除对应的事件
    strSQL = "DELETE FROM EVENTTYPE WHERE AFFAIRTYPEID='" + strAffairTypeID + "'";
    storage.addSQL(strSQL);
    
    // 删除对应的事务
    strSQL = "DELETE FROM AFFAIRTYPE WHERE AFFAIRTYPEID='" + strAffairTypeID + "'";
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
