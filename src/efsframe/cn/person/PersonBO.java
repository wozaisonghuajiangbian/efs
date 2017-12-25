package efsframe.cn.person;

import java.sql.ResultSet;

import org.dom4j.*;
import efsframe.cn.base.CommonQuery;
import efsframe.cn.base.DataDoc;
import efsframe.cn.base.NumAssign;
import efsframe.cn.base.Operation;
import efsframe.cn.base.QueryDoc;
import efsframe.cn.base.ReturnDoc;
import efsframe.cn.db.DBConnection;
import efsframe.cn.db.DataStorage;
import efsframe.cn.db.SQLAnalyse;
import efsframe.cn.declare.*;
import efsframe.cn.func.*;

public class PersonBO {

  /*********************************************************
   * ���ѧ��������Ϣ
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/
  public static String addNew(String strXml) throws Exception
  {
    DataDoc doc = new DataDoc(strXml);
    // �������ݲ�ִ�ж���
    DataStorage storage = new DataStorage();
    // ������׼���ؽṹDom�����
    ReturnDoc returndoc = new ReturnDoc();
    try
    {
      int size = doc.getDataNum(Table.PERSON);
     
      // ����sql���
      for(int i=0;i<size;i++)
      {  
        Element ele = (Element)doc.getDataNode(Table.PERSON,i);
        
        //A.001~~~~
        // ΪPersonID����Ψһ����
        Node node = ele.selectSingleNode(Field.PERSONID);
        String strId = NumAssign.assignID_B("100001",General.curYear2() + General.curMonth());        
        node.setText(strId);
        //end A.001~~~~
        
        storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));
      }
      // ִ��SQL
      String strReturn = storage.runSQL();
      if(!General.empty(strReturn))
      {
        // ִ��ʧ�ܣ������쳣����
        returndoc.addErrorResult(Common.RT_FUNCERROR);
        returndoc.setFuncErrorInfo(strReturn);
      }
      else
        // ִ�гɹ������سɹ��ڵ�
        returndoc.addErrorResult(Common.RT_SUCCESS);
    }
    catch(Exception e)
    {
      // �����쳣�������쳣����
      returndoc.addErrorResult(Common.RT_FUNCERROR);
      returndoc.setFuncErrorInfo(e.getMessage());
    }
    // ��׼�ķ���XML�ṹ�ĵ�
    return returndoc.getXML();
  }
 
  /*********************************************************
   * �޸�\ɾ��������Ϣ
   * @param  strXml           XML ������Ϣ  
   * @return String           XML ������Ϣ   
  ***********************************************************/
  public static String dealXml(String strXml) throws Exception
  {
    return Operation.dealWithXml(strXml);
  }

  /*********************************************************
   * ��ѯѧ�������б�
   * @param      strXML            ��׼��ѯ�����ṹ
   * @return       XML               ��׼��ѯ���ؽṹ
   */
    public static String personList(String strXML) throws Exception
    {
      // �����׼��ѯXML�ӿڷ��������
      QueryDoc obj_Query = new QueryDoc(strXML);
      int int_PageSize = obj_Query.getIntPageSize();

      int int_CurrentPage = obj_Query.getIntCurrentPage();

      String str_Where = obj_Query.getConditions();
      

      // ��ѯ�ֵ�
      String str_Select = "s.PERSONID PERSONID,s.NAME NAME,s.IDCARD IDCARD,s.SEX SEX,s.PLACECODE PLACECODE,s.BIRTHDAY BIRTHDAY,s.TEL TEL";
      // ��ѯ��
      String str_From   = Table.PERSON + Common.SPACE + Table.S;
      // ������׼�Ĳ�ѯ����
      
      str_Where = General.empty(str_Where) ? str_Where : Common.WHERE + str_Where;
      
      // �������ֶ��б�
      String[] str_DateList  = {Field.BIRTHDAY};
      
      // ��׼�ġ�ͳһ�ķ�ҳ��ѯ�ӿ�
      return CommonQuery.basicListQuery(str_Select,
                                        str_From,
                                        str_Where,
                                        Field.PERSONID,
                                        str_DateList,
                                        int_PageSize,
                                        int_CurrentPage);
    }

    
    public static String personListSv(String strXML) throws Exception
    {
      // �����׼��ѯXML�ӿڷ��������
      QueryDoc obj_Query = new QueryDoc(strXML);
      int int_PageSize = obj_Query.getIntPageSize();

      int int_CurrentPage = obj_Query.getIntCurrentPage();

      String str_Where = obj_Query.getConditions();

      // ��ѯ�ֵ�
      String str_Select = "s.PERSONID PERSONID,s.NAME NAME,s.IDCARD IDCARD,s.SEX SEX,s.PLACECODE PLACECODE,s.BIRTHDAY BIRTHDAY,s.TEL TEL";
      // ��ѯ��
      String str_From   = Table.PERSON + Common.SPACE + Table.S;
      // ������׼�Ĳ�ѯ����
      
      str_Where = General.empty(str_Where) ? str_Where : Common.WHERE + str_Where;
      
      // �������ֶ��б�
      String[] str_DateList  = {Field.BIRTHDAY};
      String[] str_DicFieldList  = {"SEX","PLACECODE"};
      String[] str_DicNameList  = {"DIC_GENDER","DIC_CODE"};
      
      // ��׼�ġ�ͳһ�ķ�ҳ��ѯ�ӿ�
      return CommonQuery.basicListQuery(str_Select,
                                        str_From,
                                        str_Where,
                                        Field.PERSONID,
                                        str_DicFieldList,
                                        str_DicNameList,
                                        str_DateList,
                                        0,
                                        0,
                                        int_PageSize,
                                        int_CurrentPage,
                                        0);

    }
    
    
    /**
     * ��ѯѧ����ϸ������Ϣ
     * @param      sPersonID         ѧ�����
     * @return     XML               ��׼��ѯ���ؽṹ
     */
    public static String personDetail(String sPersonID) throws Exception
    {
      String str_Select = "s.PERSONID PERSONID,s.NAME NAME,s.IDCARD IDCARD,s.SEX SEX,s.BIRTHDAY BIRTHDAY,s.PLACECODE PLACECODE,s.YEAROLD YEAROLD,s.EMAIL EMAIL,s.TEL TEL,s.BAK BAK";

      String str_From   = "PERSON s";
    
      String str_Where = Common.WHERE + Common.SPACE + Field.PERSONID + Common.EQUAL + General.addQuotes(sPersonID);
      
      String[] str_DateList  = {Field.BIRTHDAY};
      return CommonQuery.basicListQuery(str_Select, 
                                        str_From, 
                                        str_Where,
                                        Field.PERSONID,
                                        null, 
                                        null, str_DateList, 1, 1, 1, 1, 0, true,
                                        Table.PERSON);
    }   
    
    
    /**
     * ���ֵ䷭�����Ϣ��ѯ
     * @param sPersonID
     * @return
     * @throws Exception
     */
    public static String personDetail2(String sPersonID) throws Exception
    {
      String str_Select = "s.PERSONID PERSONID,s.NAME NAME,s.IDCARD IDCARD,s.SEX SEX,s.BIRTHDAY BIRTHDAY,s.PLACECODE PLACECODE,s.YEAROLD YEAROLD,s.EMAIL EMAIL,s.TEL TEL,s.BAK BAK";

      String str_From   = "PERSON s";
    
      String str_Where = Common.WHERE + Common.SPACE + Field.PERSONID + Common.EQUAL + General.addQuotes(sPersonID);
      
      String[] str_DateList  = {"BIRTHDAY"};
      String[] str_DicFieldList  = {"SEX","PLACECODE"};
      String[] str_DicNameList  = {"DIC_GENDER","DIC_CODE"};
      
      return CommonQuery.basicListQuery(str_Select, 
                                        str_From, 
                                        str_Where,
                                        Field.PERSONID,
                                        str_DicFieldList, 
                                        str_DicNameList, 
                                        str_DateList, 
                                        1, 1, 1, 1, 0, false,"PERSON");
    }
    
    
    // ͨ��sql���ֱ�ӻ��rst,ͨ��rstֱ��ת��Ϊxml
    public static String personDetail3(String sPersonID) throws Exception
    {
      String str_SQL = "SELECT * FROM PERSON WHERE PERSONID=" + General.addQuotes(sPersonID);
      DBConnection dbc = new DBConnection();
      ReturnDoc docReturn = new ReturnDoc();
      
      ResultSet rst = null;
      
      rst = dbc.excuteQuery(str_SQL);

      docReturn.getQueryInfo(rst, "PERSON");
      
      rst.close();
      dbc.freeConnection();

      
      return docReturn.getXML();
      
    }

}
