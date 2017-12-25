package efsframe.cn.classes;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import efsframe.cn.base.*;
import efsframe.cn.db.DataStorage;
import efsframe.cn.db.SQLAnalyse;
import efsframe.cn.declare.Common;
import efsframe.cn.func.General;


public class ProductBO {

  public static String AddNew(String strXML)
  {
    String strRet = null;
    try {
      // ��ȡ��ڵ�
      Document doc = DocumentHelper.parseText(strXML);

      Element ele = (Element) doc.selectSingleNode("//DATAINFO/PRODUCT");
      
      // ���ñ��
      String strId = NumAssign.assignID_B("100003",General.curYear2() + General.curMonth());
            
      ele.element("PID").setText(strId);
      
      // ����ִ�ж���
      strRet = Operation.dealWithXml(ele.asXML());

    }
    catch (Exception e) {
      
    }
    return strRet;
  }
  
  public static String proList(String strXml) throws Exception {
    // �����׼��ѯXML�ӿڷ��������
    QueryDoc obj_Query = new QueryDoc(strXml);
    int int_PageSize = obj_Query.getIntPageSize();

    int int_CurrentPage = obj_Query.getIntCurrentPage();

    // ��ѯ�ֵ�
    String str_Select = "s.PID PID,s.PTYPE PTYPE,s.PNAME PNAME,s.PRICE PRICE,s.PNUM PNUM";
    // ��ѯ��
    String str_From = "PRODUCT s";
    // ������׼�Ĳ�ѯ����
    String str_Where = obj_Query.getConditions();
    str_Where = General.empty(str_Where) ? str_Where : Common.WHERE
                + str_Where;

    // ��׼�ġ�ͳһ�ķ�ҳ��ѯ�ӿ�
    return CommonQuery.basicListQuery(str_Select, 
                                      str_From, 
                                      str_Where,
                                      "PID", null, 
                                      int_PageSize, 
                                      int_CurrentPage);
  }
  
  ///
  public static String detail(String pid) throws Exception {
    String str_Select = "s.PID PID,s.PTYPE PTYPE,s.PNAME PNAME,s.PRICE PRICE,s.PNUM PNUM";
    // ��ѯ��
    String str_From = "PRODUCT s";
    // ������׼�Ĳ�ѯ����
    String str_Where = Common.WHERE + " PID = '" + pid + "'";
    
    return CommonQuery.basicListQuery(str_Select, 
                                      str_From, 
                                      str_Where,
                                      "PID",
                                      null, 
                                      null, null, 1, 1, 1, 1, 0, true,
                                      "PRODUCT");
  }
  
  /**
   * ��Ӷ���
   * @param strXML
   * @return
   */
  public static String AddOrd(String strXML)
  {
    String strRet = null;
    try {
      // ���ñ��
      String strId = NumAssign.assignID_B("100004",General.curYear4() + 
                     General.curMonth() + General.curDay());

      Document doc = DocumentHelper.parseText(strXML);
      Integer len = doc.selectNodes("//ORDERID").size();
      
      for(int i=0;i<len;i++)
      {
        Element ele = (Element) doc.selectNodes("//ORDERID").get(i);
        ele.setText(strId);
      }
      
      // ����ִ�ж���
      strRet = Operation.dealWithXml(doc.asXML());

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return strRet;
  }
  
  
  /**
   * ��Ӷ���
   * @param strXML
   * @return
   */
  public static String AddOrd2(String strXML) throws Exception
  {
    DataStorage obj_Storage = new DataStorage();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    String str_SQL = "";
    try {
      // ���ñ��
      String strId = NumAssign.assignID_B("100004",General.curYear4() + 
                     General.curMonth() + General.curDay());

      Document doc = DocumentHelper.parseText(strXML);
      
      // ���� ORDERLIST insert
      Element ele1 = (Element) doc.selectSingleNode("//DATAINFO/ORDERLIST");
      ele1.element("ORDERID").setText(strId);
      
      str_SQL = SQLAnalyse.analyseXMLSQL(ele1);
      
      obj_Storage.addSQL(str_SQL);

      
      // ����˹������sql��乹��
      Integer len = doc.selectNodes("//ORDPRO").size();
      for(int i=0;i<len;i++)
      {
        Element ele = (Element) doc.selectNodes("//ORDPRO").get(i);
        
        str_SQL = "INSERT INTO ORDPRO(ORDERID,PID)VALUES ('" + strId + "','" + ele.element("PID").getText() + "')";
        obj_Storage.addSQL(str_SQL);
      }
       
      String str_Return = obj_Storage.runSQL();
      
      if (!General.empty(str_Return))
      {
        obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
        obj_ReturnDoc.setFuncErrorInfo(str_Return);     
      }
      else
      {
        obj_ReturnDoc.addErrorResult(Common.RT_SUCCESS);
      }

    }
    catch (Exception e) {
      obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
      obj_ReturnDoc.setFuncErrorInfo(e.getMessage());     
    }
    return obj_ReturnDoc.getXML();
  }
  
  /**
   * �޸Ķ���
   * @param strXML
   * @return
   * @throws Exception
   */
  public static String EditOrd(String strXML) throws Exception
  {
    DataStorage obj_Storage = new DataStorage();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    String str_SQL = "";
    try {
      // ���ñ��
      
      Document doc = DocumentHelper.parseText(strXML);
      
      // ���� ORDERLIST insert
      Element ele1 = (Element) doc.selectSingleNode("//DATAINFO/ORDERLIST");
      String strOrdID = ele1.element("ORDERID").getText();
      
      str_SQL = SQLAnalyse.analyseXMLSQL(ele1);
      
      obj_Storage.addSQL(str_SQL);

      
      // ����˹������sql��乹��
      // ɾ��ԭ���Ķ�����Ʒ
      obj_Storage.addSQL("DELETE FROM ORDPRO WHERE ORDERID='" + strOrdID + "'");
      
      // ѭ������µ���Ʒ
      Integer len = doc.selectNodes("//ORDPRO").size();
      for(int i=0;i<len;i++)
      {
        Element ele = (Element) doc.selectNodes("//ORDPRO").get(i);
        
        str_SQL = "INSERT INTO ORDPRO(ORDERID,PID)VALUES ('" + strOrdID + "','" + ele.element("PID").getText() + "')";
        obj_Storage.addSQL(str_SQL);
      }
       
      String str_Return = obj_Storage.runSQL();
      
      if (!General.empty(str_Return))
      {
        obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
        obj_ReturnDoc.setFuncErrorInfo(str_Return);     
      }
      else
      {
        obj_ReturnDoc.addErrorResult(Common.RT_SUCCESS);
      }

    }
    catch (Exception e) {
      obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
      obj_ReturnDoc.setFuncErrorInfo(e.getMessage());     
    }
    return obj_ReturnDoc.getXML();
  }
  
  /**
   * ɾ������
   * @param strOrdID
   * @return
   * @throws Exception
   */
  public static String DelOrd(String strOrdID) throws Exception
  {
    DataStorage obj_Storage = new DataStorage();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    String str_SQL = "";
    try {
      // ɾ��ԭ���Ķ�����Ʒ

      obj_Storage.addSQL("DELETE FROM ORDPRO WHERE ORDERID='" + strOrdID + "'");
      
      str_SQL = "DELETE FROM ORDERLIST WHERE ORDERID='" + strOrdID + "'";
      obj_Storage.addSQL(str_SQL);
       
      String str_Return = obj_Storage.runSQL();
      
      if (!General.empty(str_Return))
      {
        obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
        obj_ReturnDoc.setFuncErrorInfo(str_Return);     
      }
      else
      {
        obj_ReturnDoc.addErrorResult(Common.RT_SUCCESS);
      }

    }
    catch (Exception e) {
      obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
      obj_ReturnDoc.setFuncErrorInfo(e.getMessage());     
    }
    return obj_ReturnDoc.getXML();
  }
  
  
  /**
   * �����б��ѯ
   * @param strXml
   * @return
   * @throws Exception
   */
  public static String ordList(String strXml) throws Exception {
    // �����׼��ѯXML�ӿڷ��������
    QueryDoc obj_Query = new QueryDoc(strXml);
    int int_PageSize = obj_Query.getIntPageSize();

    int int_CurrentPage = obj_Query.getIntCurrentPage();

    // ��ѯ�ֵ�
    String str_Select = "s.ORDERID ORDERID,s.ORDERPSN ORDERPSN,s.ORDERDATE ORDERDATE,s.ORDERTITLE ORDERTITLE,s.CUSNAME CUSNAME,s.CUSUNIT CUSUNIT";
    // ��ѯ��
    String str_From = "ORDERLIST s";
    // ������׼�Ĳ�ѯ����
    String str_Where = obj_Query.getConditions();
    str_Where = General.empty(str_Where) ? str_Where : Common.WHERE
                + str_Where;
    
    System.out.println(str_Where);
    
    // �������ֶ��б�
    String[] str_DateList  = {"ORDERDATE"};
    
    // ��׼�ġ�ͳһ�ķ�ҳ��ѯ�ӿ�
    return CommonQuery.basicListQuery(str_Select,
                                      str_From,
                                      str_Where,
                                      "ORDERID",
                                      str_DateList,
                                      int_PageSize,
                                      int_CurrentPage);
  }
  
  // ������������Ʒ�б�
  public static String ordproList(String ordid) throws Exception {
    String str_Select = "s.ORDERID ORDERID,s.PID PID";
    // ��ѯ��
    String str_From = "ORDPRO s";
    // ������׼�Ĳ�ѯ����
    String str_Where = Common.WHERE + " ORDERID = '" + ordid + "'";
    
    return CommonQuery.basicListQuery(str_Select, 
                                      str_From, 
                                      str_Where,
                                      "PID",
                                      null, 
                                      null, null, 1, 1, 99999, 1, 0, true,
                                      "ROW");
  }

  
  //������ϸ��Ϣ��ѯ
  public static String ordDetail(String ordid) throws Exception {
    String str_Select = "s.ORDERID ORDERID,s.ORDERPSN ORDERPSN,s.ORDERDATE ORDERDATE,s.ORDERTITLE ORDERTITLE,s.CUSNAME CUSNAME,s.CUSUNIT CUSUNIT";
    // ��ѯ��
    String str_From = "ORDERLIST s";
    // ������׼�Ĳ�ѯ����
    String str_Where = Common.WHERE + " ORDERID = '" + ordid + "'";
    
    return CommonQuery.basicListQuery(str_Select, 
                                      str_From, 
                                      str_Where,
                                      "ORDERID",
                                      null, 
                                      null, null, 1, 1, 1, 1, 0, true,
                                      "ORDERLIST");
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
