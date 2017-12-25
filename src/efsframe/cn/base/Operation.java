package efsframe.cn.base;

import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import efsframe.cn.declare.Common;
import efsframe.cn.db.DataStorage;
import efsframe.cn.db.SQLAnalyse;
import efsframe.cn.func.General;

/**
 * ��׼ҵ�������XML����ͳһ�ӿ���
 * �ܷ����ݵĽ���׼��ҵ�������XML�ĵ����������T-SQL����װ����ִ��
 * �����ձ�׼�ķ���XML�ṹ������ش�������Ϣ
 */
public class Operation
{
  /**
   * ����ҵ����������Դ���ͨ���͵ģ�����Ҫ�κ�ҵ�������޸ĵ�[���]��[�޸�]��ҵ��
   * @param objDataDoc      Document  XML �ĵ�����
   * @param strTbName       �������ҵ�������
   * @return String         ��׼ XML �����ĵ��ַ���
   */
  public static String addOrEdit(DataDoc objDataDoc, String strTbName) throws Exception
  {
     
    /// ����ִ�ж���
    DataStorage obj_Storage = new DataStorage();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    try
    {

	    int int_Size = objDataDoc.getDataNum(strTbName);
	
	    /// ���� SQL ���
	    for (int i=0; i<int_Size; i++)
	    {
	      Element ele_Temp = (Element)objDataDoc.getDataNode(strTbName,i);
	      obj_Storage.addSQL(SQLAnalyse.analyseXMLSQL(ele_Temp));
	    }
	
	    /// ִ��
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
    catch(Exception e)
    {
    	obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
    	obj_ReturnDoc.setFuncErrorInfo(e.getMessage());    	
    }

    return obj_ReturnDoc.getXML();
  }

  /**
   * ����ҵ����������Դ���ͨ���͵ģ�����Ҫ�κ�ҵ�������޸ĵ�[���]��[�޸�]��ҵ��
   * @param strXML          Document  XML �ĵ��ַ���
   * @param strTbName       �������ҵ�������
   * @return String         ��׼ XML �����ĵ��ַ���
   */
  public static String addOrEdit(String strXML, String strTbName) throws Exception
  {
    DataDoc obj_DataDoc = new DataDoc(strXML);

    return addOrEdit(obj_DataDoc,strTbName);
  }
  
  
  /**
   * ͳһ�����׼��ҵ�������XML�ĵ������ĵ�������T-SQL��װ��DataStorage������Ϊһ������ִ��
   * @param strXML          ��׼ҵ�������XML�ĵ�
   * @return String         ��׼ XML �����ĵ��ַ���
   */
  public static String dealWithXml(String strXML) throws Exception
  {
    /// ����ִ�ж���
    DataStorage obj_Storage = new DataStorage();
    ReturnDoc obj_ReturnDoc = new ReturnDoc();
    try
    {
      Document obj_Doc = DocumentHelper.parseText(strXML);
      List lst_Temp = obj_Doc.getRootElement().selectNodes("//*[@operation][@operation!='']");
      Iterator it_Temp = lst_Temp.iterator();
          
      while (it_Temp.hasNext())
      {
        Element ele_Temp = (Element)it_Temp.next();
        //      �����������ݵ�XML
        String str_SQL = SQLAnalyse.analyseXMLSQL(ele_Temp);
        
        obj_Storage.addSQL(str_SQL);
      }
  
      /// ִ��
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
    catch(Exception e)
    {
      obj_ReturnDoc.addErrorResult(Common.RT_FUNCERROR);
      obj_ReturnDoc.setFuncErrorInfo(e.getMessage());     
    }

    return obj_ReturnDoc.getXML();
    
  }
  
  
}
