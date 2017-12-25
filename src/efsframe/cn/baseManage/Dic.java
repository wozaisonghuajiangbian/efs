package efsframe.cn.baseManage;

import java.util.*;
import org.dom4j.Element;
import efsframe.cn.declare.*;
import efsframe.cn.func.*;
import efsframe.cn.db.*;
import efsframe.cn.base.CommonQuery;
import efsframe.cn.base.QueryDoc;
import efsframe.cn.base.ReturnDoc;
import efsframe.cn.cache.*;

public class Dic
{
  /**
   * ��ѯ�ֵ����ݵļ��б�
   * @param strXML            ��׼ XML ������Ϣ
   * @param strDicName        �ֵ�����
   * @return XML               ��׼ XML ���ؽṹ
   */
  public static String dicDataList(String strXML, String strDicName) throws Exception
  {
    QueryDoc obj_Query = new QueryDoc(strXML);
    Element ele_Condition = obj_Query.getCondition();
    
    /// ���ÿҳ��¼��
    String str_Return = ele_Condition.attributeValue(Common.XML_PROP_RECORDSPERPAGE);
    int int_PageSize = str_Return==null ? 10 : Integer.parseInt(str_Return);

    /// ��õ�ǰ����ѯҳ��
    str_Return = ele_Condition.attributeValue(Common.XML_PROP_CURRENTPAGENUM);
    int int_CurrentPage = str_Return==null ? 1 : Integer.parseInt(str_Return);

    /// ��ü�¼����
    str_Return = ele_Condition.attributeValue(Common.XML_PROP_RECORDS);
    int int_TotalRecords = str_Return==null ? 0 : Integer.parseInt(str_Return);

    int int_CountTotal = int_TotalRecords>0 ? 1 : 0;
    
    int int_TotalPages = 0;
    
    String str_Select = Table.S + Common.DOT  + Field.DIC_CODE  + Common.SPACE + Field.DIC_CODE + Common.COMMA +
                        Table.S + Common.DOT  + Field.DIC_TEXT  + Common.SPACE + Field.DIC_TEXT + Common.COMMA +
                        Table.S + Common.DOT  + Field.DIC_VALID + Common.SPACE + Field.DIC_VALID;
    
    String str_From   = Table.DICDATA + Common.SPACE + Table.S;
    
    String str_Where = obj_Query.getConditions();
    
    str_Where = General.empty(str_Where) ? str_Where : Common.WHERE + str_Where;
    str_Where = !General.empty(str_Where) ?
                str_Where      +
                Common.SPACE   + Field.DICNAME + Common.EQUAL + General.addQuotes(strDicName) :
                Common.S_WHERE + Field.DICNAME + Common.EQUAL + General.addQuotes(strDicName);
    
    // str_Where += Common.S_ORDER + Field.DIC_CODE;
  
    String[] str_DicFieldList = {Field.DIC_VALID};
    
    String[] str_DicNameList  = {Table.DIC_VALID };
    
    return CommonQuery.basicListQuery(str_Select,
                                      str_From,
                                      str_Where,
                                      Field.DIC_CODE,
                                      str_DicFieldList,
                                      str_DicNameList,
                                      null,
                                      int_TotalRecords,
                                      int_TotalPages,
                                      int_PageSize,
                                      int_CurrentPage,
                                      int_CountTotal);
  }

  /**
   * ��ѯ�ֵ��б�
   * @param strXML            ��׼ XML ������Ϣ
   * @return XML               ��׼ XML ���ؽṹ
   */
  public static String dicList(String strXML) throws Exception
  {
    QueryDoc obj_Query = new QueryDoc(strXML);
    Element ele_Condition = obj_Query.getCondition();
    
    /// ���ÿҳ��¼��
    String str_Return = ele_Condition.attributeValue(Common.XML_PROP_RECORDSPERPAGE);
    int int_PageSize = str_Return==null ? 10 : Integer.parseInt(str_Return);

    /// ��õ�ǰ����ѯҳ��
    str_Return = ele_Condition.attributeValue(Common.XML_PROP_CURRENTPAGENUM);
    int int_CurrentPage = str_Return==null ? 1 : Integer.parseInt(str_Return);

    /// ��ü�¼����
    str_Return = ele_Condition.attributeValue(Common.XML_PROP_RECORDS);
    int int_TotalRecords = str_Return==null ? 0 : Integer.parseInt(str_Return);

    int int_CountTotal = int_TotalRecords>0 ? 1 : 0;
    int int_TotalPages = 0;

    String str_Select = Table.S + Common.DOT  + Field.DICNAME  + Common.SPACE + Field.DICNAME + Common.COMMA +
                        Table.S + Common.DOT  + Field.DICDES   + Common.SPACE + Field.DICDES  + Common.COMMA +
                        Table.S + Common.DOT  + Field.CODELEN  + Common.SPACE + Field.CODELEN + Common.COMMA +
                        Table.S + Common.DOT  + Field.TEXTLEN  + Common.SPACE + Field.TEXTLEN + Common.COMMA +
                        Table.S + Common.DOT  + Field.EDITABLE + Common.SPACE + Field.EDITABLE;
    String str_From   = Table.DICLIST + Common.SPACE + Table.S;

    String str_Where = obj_Query.getConditions();

    str_Where = General.empty(str_Where) ? str_Where : Common.WHERE + str_Where;
    // str_Where = !General.empty(str_Where) ? str_Where + Common.S_ORDER + Field.DICNAME : Common.S_ORDER + Field.DICNAME;

    String[] str_DicFieldList = {Field.EDITABLE};
    String[] str_DicNameList  = {Table.DIC_DICEDITABLE};

    return CommonQuery.basicListQuery(str_Select,
                                      str_From,
                                      str_Where,
                                      Field.DICNAME,
                                      str_DicFieldList,
                                      str_DicNameList,
                                      null,
                                      int_TotalRecords,
                                      int_TotalPages,
                                      int_PageSize,
                                      int_CurrentPage,
                                      int_CountTotal);
  }

  /**
   * ����һ���µ��ֵ�
   * @param strDicName      �ֵ�����
   * @param strDicDes       �ֵ�����
   * @param strCodeLen      �ֵ���볤��
   * @param strEditable     �ֵ��޸�Ȩ��
   * @return XML             ��׼ XML ���ؽṹ
   */
  public static String addNewDic(String strDicName,
                                 String strDicDes,
                                 String strCodeLen,
                                 String strTextLen,
                                 String strEditable) throws Exception
  {
    strDicName = strDicName.toUpperCase();

    String str_SysDics = Table.DICLIST        + Common.COMMA + 
                         Table.DIC_MANAGEUNIT + Common.COMMA +
                         Table.DIC_AFFAIRTYPE + Common.COMMA +
                         Table.DIC_EVENTTYPE  + Common.COMMA + 
                         Table.DIC_USERLIST;

    if (str_SysDics.indexOf(strDicName)>-1)
      throw new Exception("�ֵ��� [" + strDicName + "] Ϊϵͳ�ֵ䣬����ʹ�ø�������Ϊ�ֵ�����");
    
    ArrayList arr_Dic = DicCache.getInstance().getDicByName(strDicName);
    
    if (arr_Dic!=null)
    {
      throw new Exception("ͬ���ֵ� [" + strDicName + "(" + strDicDes +  ")] �Ѿ����ڣ������ظ�����");
    }
    
    /// �������ݿ��е��ֵ�����
    String str_FieldList = General.addBracket(Field.DICNAME + Common.COMMA +
                                              Field.DICDES  + Common.COMMA +
                                              Field.CODELEN + Common.COMMA +
                                              Field.TEXTLEN + Common.COMMA +
                                              Field.EDITABLE);

    String str_ValueList = General.addBracket(General.addQuotes(strDicName) + Common.COMMA +
                                              General.addQuotes(strDicDes)  + Common.COMMA +
                                              strCodeLen                    + Common.COMMA +
                                              strTextLen                    + Common.COMMA +
                                              strEditable);

    String str_SQL = Common.INSERT   + Table.DICLIST +
                     Common.SPACE    + str_FieldList +
                     Common.S_VALUES + str_ValueList;

    DataStorage obj_Storage = new DataStorage();
    obj_Storage.addSQL(str_SQL);

    //  ִ��
    String str_Return = obj_Storage.runSQL();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    if (!General.empty(str_Return))
    {
    	obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
	    obj_ReturnDoc.setFuncErrorInfo(str_Return);
    }
    else
    {
      obj_ReturnDoc.addErrorResult(Common.RT_SUCCESS);
    }

    /// ˢ���ֵ仺������
    DicCache.getInstance().refresh(strDicName);

    /// ����
    return obj_ReturnDoc.getXML();
  }

  /**
   * ά���ֵ�����
   *        ��������е��ֵ���Ŀ����£�������µ��ֵ���Ŀ������
   * @param strDicName      �ֵ�����
   * @param strDicCode      �ֵ����
   * @param strDicText      �ֵ��ı�����
   * @param strDicValid     �ֵ���Ŀ�Ƿ���Ч
   * @return XML             ��׼ XML ���ؽṹ
   */
  public static String vindicate(String strDicName,
                                 String strDicCode,
                                 String strDicText,
                                 String strDicValid) throws Exception
  {
    strDicName = strDicName.toUpperCase();
    ArrayList arr_Dic = DicCache.getInstance().getDicByName(strDicName);
    if (arr_Dic==null) throw new Exception("ָ�����ֵ䲢���������ֵ仺����");

    boolean bln_New = true;
    Iterator it_Dic = arr_Dic.iterator();
    while (it_Dic.hasNext())
    {
      String[] str_DicItem = (String[])it_Dic.next();
      if (str_DicItem[0].equals(strDicCode))
      {
        bln_New = false;
        break;
      }
    }

    String str_SQL = "";

    DataStorage obj_Storage = new DataStorage();

    if (bln_New)
    {
      /// �������ݿ��е��ֵ�����
      String str_FieldList = General.addBracket(Field.DICNAME  + Common.COMMA +
                                                Field.DIC_CODE + Common.COMMA +
                                                Field.DIC_TEXT + Common.COMMA +
                                                Field.DIC_VALID);

      String str_ValueList = General.addBracket(General.addQuotes(strDicName) + Common.COMMA +
                                                General.addQuotes(strDicCode) + Common.COMMA +
                                                General.addQuotes(strDicText) + Common.COMMA +
                                                General.addQuotes(strDicValid));

      str_SQL = Common.INSERT   + Table.DICDATA +
                Common.SPACE    + str_FieldList +
                Common.S_VALUES + str_ValueList;
    }
    else
    {
      /// �������ݿ��е��ֵ�����
      str_SQL = Common.UPDATE  + Table.DICDATA   +
                Common.S_SET   + Field.DIC_TEXT  + Common.EQUAL + General.addQuotes(strDicText)  + Common.COMMA +
                                 Field.DIC_VALID + Common.EQUAL + General.addQuotes(strDicValid) +
                Common.S_WHERE + Field.DIC_CODE  + Common.EQUAL + General.addQuotes(strDicCode)  +
                Common.S_AND   + Field.DICNAME   + Common.EQUAL + General.addQuotes(strDicName);
    }

    obj_Storage.addSQL(str_SQL);

    String str_Return = obj_Storage.runSQL();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    if (!General.empty(str_Return))
    {
    	obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
	    obj_ReturnDoc.setFuncErrorInfo(str_Return);
    }
    else
    {
      obj_ReturnDoc.addErrorResult(Common.RT_SUCCESS);
    }

    /// ˢ���ֵ仺����Ϣ
    DicCache.getInstance().refresh(strDicName);

    /// ����
    return obj_ReturnDoc.getXML();
  }

  /**
   * ɾ���ֵ���Ŀ
   * @param strDicName      �ֵ�����
   * @param strDicCode      �ֵ����
   * @return XML             ��׼ XML ���ؽṹ
   */
  public static String deleteItem(String strDicName, String strDicCode) throws Exception
  {
    strDicName = strDicName.toUpperCase();
    ArrayList arr_Dic = DicCache.getInstance().getDicByName(strDicName);
    if (arr_Dic==null) throw new Exception("ָ�����ֵ䲢���������ֵ仺����");

    String str_SQL = "";
    DataStorage obj_Storage = new DataStorage();

    /// ɾ�����ݿ��е��ֵ�����
    str_SQL = Common.DELETE  + Table.DICDATA  +
              Common.S_WHERE + Field.DICNAME  + Common.EQUAL + General.addQuotes(strDicName) +
              Common.S_AND   + Field.DIC_CODE + Common.EQUAL + General.addQuotes(strDicCode);

    obj_Storage.addSQL(str_SQL);

    String str_Return = obj_Storage.runSQL();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    if(!General.empty(str_Return))
    {
    	obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
	    obj_ReturnDoc.setFuncErrorInfo(str_Return);
    }
    else
    {
      obj_ReturnDoc.addErrorResult(Common.RT_SUCCESS);
    }

    /// ˢ���ֵ仺����Ϣ
    DicCache.getInstance().refresh(strDicName);

    /// ����
    return obj_ReturnDoc.getXML();
  }
  
  /**
   * ɾ���ֵ�
   * @param strDicName      �ֵ�����
   * @return XML             ��׼ XML ���ؽṹ
   */
  public static String deleteDic(String strDicName) throws Exception
  {
    strDicName = strDicName.toUpperCase();
    
    String str_SQL = "";
    DataStorage obj_Storage = new DataStorage();

    /// ɾ�����ݿ��е��ֵ�
    str_SQL = Common.DELETE  + Table.DICLIST  +
              Common.S_WHERE + Field.DICNAME  + Common.EQUAL + General.addQuotes(strDicName);

    obj_Storage.addSQL(str_SQL);

    /// ɾ�����ݿ��е��ֵ�����
    str_SQL = Common.DELETE  + Table.DICDATA  +
              Common.S_WHERE + Field.DICNAME  + Common.EQUAL + General.addQuotes(strDicName);

    obj_Storage.addSQL(str_SQL);

    String str_Return = obj_Storage.runSQL();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    if(!General.empty(str_Return))
    {
      obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
      obj_ReturnDoc.setFuncErrorInfo(str_Return);
    }
    else
    {
      obj_ReturnDoc.addErrorResult(Common.RT_SUCCESS);
    }

    /// ����
    return obj_ReturnDoc.getXML();
  }  
}
