package efsframe.cn.func;

import java.util.*;
import java.io.FileInputStream;
import java.lang.Math;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import efsframe.cn.declare.*;
import efsframe.cn.base.XmlFunc;
import efsframe.cn.cache.*;

/**
 * ���ڶ����ݽ��������еı�׼XML�ĵ��ַ������н�һ���ķ������� 
 */
public class PageCommon
{

  /**
   * �жϵ������ִ���Ƿ�ɹ�
   * @param strXML       XML�ı�����
   * @return             True �ɹ���False ʧ��
   */
  public static boolean IsSucceed(String strXML)
  {
    try
    {
      Document doc = XmlFunc.CreateNewDoc(strXML);
      return IsSucceed(doc);
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * �жϵ������ִ���Ƿ�ɹ�
   * @param doc   Document����
   * @return    True �ɹ���False ʧ��
   */
  public static boolean IsSucceed(Document doc)
  {
	  try
	    {
	      String strRet = XmlFunc.getNodeValue(doc, Common.XDOC_ERRORINFO + Common.BAR + Common.XDOC_ERRORRESULT);
	      if(strRet != null)
	      {
	        if(strRet.equals(Common.SRT_SUCCESS) || strRet.equals(Common.RT_QUERY_SUCCESS))
	          return true;
	        else
	          return false;
	      }
	      else
	      {
	        return false;
	      }
	    }
	    catch(Exception e)
	    {
	      return false;
	    }
  }
  
  /**
   * ��õ��ò�ѯ����ķ��ؽ��
   * @param strXML       XML�ı�����
   * @return String      ����String
   *                     00 - ��ʾ��ѯ�ɹ�
   *                     01 - ��ʾ��ѯ�ɹ�,����¼Ϊ��
   */
  public static String getQueryResult(String strXML)
  {
    try
    {
      String strRet;
      Document doc = XmlFunc.CreateNewDoc(strXML);

      strRet = XmlFunc.getNodeValue(doc, Common.XDOC_ERRORINFO + Common.BAR + Common.XDOC_ERRORRESULT);

      if(strRet == null) strRet = "99";

      return strRet;
    }
    catch(Exception e)
    {
      return "99";
    }
  }
  
  /**
   * ���ò�ѯҳ������
   * @param strXML              XML�ı�����
   * @param intPageSize         ҳ�Ĵ�С
   * @param intCurrentPage      ��ǰҳ��
   * @return                    String
   */
  public static String setQueryPageInfo(String strXML,String intPageSize,String intCurrentPage)
  {
    try
    {
      Document doc = XmlFunc.CreateNewDoc(strXML);

      XmlFunc.setAttrValue(doc,Common.XDOC_QUERYCONDITION, Common.XML_PROP_RECORDSPERPAGE, intPageSize);
      XmlFunc.setAttrValue(doc,Common.XDOC_QUERYCONDITION, Common.XML_PROP_CURRENTPAGENUM, intCurrentPage);

      return doc.asXML();
    }
    catch(Exception e)
    {
      return strXML;
    }
  }
 
  /**
   * ���ò�ѯҳ������
   * @param strXML              XML�ı�����
   * @param intPageSize         ҳ�Ĵ�С
   * @param intCurrentPage      ��ǰҳ��
   * @return                    String
   */
  public static String setQueryPageInfo(String strXML,int intPageSize,int intCurrentPage)
  {
    try
    {
      Document doc = XmlFunc.CreateNewDoc(strXML);
      XmlFunc.setAttrValue(doc,Common.XDOC_QUERYCONDITION, Common.XML_PROP_RECORDSPERPAGE, String.valueOf(intPageSize));
      XmlFunc.setAttrValue(doc,Common.XDOC_QUERYCONDITION, Common.XML_PROP_CURRENTPAGENUM, String.valueOf(intCurrentPage));
      return doc.asXML();
    }
    catch(Exception e)
    {
      return strXML;
    }
  }  
  
  /**
   * ���һ��Ĭ�ϵ��ύ����Document����
   * @return Document    Document����
   */
  public static Document getDefaultXML()
  {
    try
    {
      String strXML = Common.XML_HEADINFO +
                    "<"  + Common.XDOC_ROOT + " efsframe='" + Common.PVAL_SOURCE + "' version='" + Common.PVAL_VERSION + "'>" +
                    "</" + Common.XDOC_ROOT + ">";
      return XmlFunc.CreateNewDoc(strXML);
    }
    catch(Exception e)
    {
      return null;
    }
  }
  
  /**
   * �����ύ������� XML �ַ���
   * @param strDataInfo         �����ַ���
   * @param userSession         ��¼�û�Session
   * @return                    ƴ�����֮���XML �ĵ��ַ���
   */
  public static String setDocXML(String strDataInfo,
                                 UserLogonInfo userSession)
  {
    try
    {
      Document doc = getDefaultXML();
      String nodePath = Common.XDOC_ROOT;
      XmlFunc.setNodeDOM(doc,nodePath,strDataInfo);
      
      // �����û��ڵ�
      String strNodeData = "<USERINFO><USERID>"     + userSession.getUserID() + 
                           "</USERID><USERTITLE>"   + userSession.getUserTitle() + 
                           "</USERTITLE><USERNAME>" + userSession.getUserName() + 
                           "</USERNAME><UNITID>"    + userSession.getUnitID() + 
                           "</UNITID><UNITNAME>"    + userSession.getUnitName() + 
                           "</UNITNAME><MTYPE>"     + userSession.getMUnitType() +
                           "</MTYPE><LOGID>"        + userSession.getLogID() +
                           "</LOGID><USERTYPE>"     + userSession.getUserType() + 
                           "</USERTYPE></USERINFO>";
      nodePath = Common.XDOC_ROOT;

      XmlFunc.setNodeDOM(doc,nodePath,strNodeData);
            
      return doc.asXML();
    }
    catch(Exception e)
    {
      return null;
    }
  }
  
  
  /**
   * ��õ������ʧ�ܵ�����
   * @param strXML   ��׼����XML�ĵ��ַ���
   * @return String ����������Ϣ
   */
  public static String getErrInfo(String strXML)
  {
    try
    {
      Document doc = XmlFunc.CreateNewDoc(strXML);
      return getErrInfo(doc);
    }
    catch(Exception e)
    {
      return "";
    }
  }
 
  /**
   * ��õ������ʧ�ܵ�����
   * @param doc     ��׼����XML�ĵ�����
   * @return String ����������Ϣ
   */  
  public static String getErrInfo(Document doc)
  {
    try
    {
      String nodePath = Common.XDOC_ROOT + Common.BAR + Common.XDOC_ERRORINFO + Common.BAR + Common.XDOC_FUNCERROR;
      return XmlFunc.getNodeValue(doc, nodePath);
    }
    catch(Exception e)
    {
      return "";
    }
  }
  
  /**
   * ��ò�ѯ���ص� XML �ṹ�е���ҳ��
   * @param strXML  ��׼��ѯ����XML�ĵ��ַ���
   * @return int ҳ����
   */
  public static int getTotalPages(String strXML)
  {
    try
    {
      Document doc = XmlFunc.CreateNewDoc(strXML);
      return getTotalPages(doc);
    }
    catch(Exception e)
    {
      return 0;
    }
  }

  /**
   * ��ò�ѯ���ص� XML �ṹ�е���ҳ��
   * @param doc  ��׼��ѯ����XML�ĵ�����
   * @return int ҳ����
   */  
  public static int getTotalPages(Document doc)
  {
    try
    {
      String nodePath = Common.XDOC_ROOT + Common.BAR + Common.XDOC_QUERYINFO;
      String str_Ret = XmlFunc.getAttrValue(doc,nodePath, Common.XML_PROP_TOTALPAGES);
      if(General.empty(str_Ret))
    	  return 0;
      else
        return Integer.parseInt(str_Ret);
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * ��ò�ѯ���ص� XML �ṹ�е��ܼ�¼�� 
   * @param strXML ��׼��ѯ����XML�ĵ��ַ���
   * @return int �ܼ�¼��
   */
  public static int getRecords(String strXML)
  {
    try
    {
      Document doc = XmlFunc.CreateNewDoc(strXML);
      return getRecords(doc);
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * ��ò�ѯ���ص� XML �ṹ�е��ܼ�¼�� 
   * @param doc  ��׼��ѯ����XML�ĵ�����
   * @return int �ܼ�¼��
   */  
  public static int getRecords(Document doc)
  {
    try
    {
      String nodePath = Common.XDOC_ROOT + Common.BAR + Common.XDOC_QUERYINFO;
      String str_Ret = XmlFunc.getAttrValue(doc,nodePath, Common.XML_PROP_RECORDS);
      if(General.empty(str_Ret))
    	  return 0;
      else
        return Integer.parseInt(str_Ret);
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * ���һ�������
   * @return String �����
   */
  public static String getRandom()
  {
    String str_Month = String.valueOf(DateTimeUtil.getCurrentMonth());
    String str_Day = String.valueOf(DateTimeUtil.getCurrentDay());
    String str_Hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
    String str_Minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
    String str_Second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
    String str_RandNum =String.valueOf((int)(Math.random() * 10000));
    String str_Rand = str_Month + str_Day + str_Hour + str_Minute + str_Second + str_RandNum;
    return str_Rand;
  }
  
  
  
  /**
   * ʹ��XSLT��ʽ��ת��XML����ΪHTML�ַ���
   * @param strXML              ����ʽ����xml�ĵ��ַ�
   * @param strXSLTPath         ���õ�xslt�ĵ�·��
   * @return String             ��ʽ�����֮���HTML�ַ���
   */
  public static String strXML2Html(String strXML, String strXSLTPath)
  {
    try
    {
    	Document doc = XmlFunc.CreateNewDoc(strXML);

      return strXML2Html(doc,strXSLTPath);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }
  /**
   * ʹ��XSLT��ʽ��ת��XML����ΪHTML�ַ���
   * @param doc                 ����ʽ����xml�ĵ�����
   * @param strXSLTPath         ���õ�xslt�ĵ�·��
   * @return String             ��ʽ�����֮���HTML�ַ���
   */
  public static String strXML2Html(Document doc, String strXSLTPath)
  {
    try
    {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer(new StreamSource(strXSLTPath));
        
      // now lets style the given document
      DocumentSource source = new DocumentSource(doc);
      DocumentResult result = new DocumentResult();

      transformer.transform(source,result);
      // return the transformed document
      Document transformedDoc = result.getDocument();

      String strXML = transformedDoc.asXML();
      int iIndex = 0;
      while((iIndex = strXML.indexOf("<TEXTAREA",iIndex))!=-1)
      {
         
        int i = strXML.indexOf("/>",iIndex);
        iIndex = i;
        if(i==-1) break;
        String sTemp1 = strXML.substring(0,i);
        String sTemp2 = strXML.substring(i+2);
        strXML = sTemp1 + "></TEXTAREA>" + sTemp2;
      }
      iIndex = 0;
      while((iIndex = strXML.indexOf("<SCRIPT",iIndex))!=-1)
      {
         
        int i = strXML.indexOf("/>",iIndex);
        iIndex = i;
        if(i==-1) break;
        String sTemp1 = strXML.substring(0,i);
        String sTemp2 = strXML.substring(i+2);
        strXML = sTemp1 + "></SCRIPT>" + sTemp2;
      }
      return strXML;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }
  

  /**
   * ������unicode������ļ����أ�ת��Ϊ�ַ���
   * @param strLocalPath        �ļ�·��
   * @return                    String�ַ���
   */
  public static String loadLocalFileToStr(String strLocalPath)
  {
    try
    {
      String strRet = "";
      FileInputStream in = new FileInputStream(strLocalPath);
      
      int size = in.available();
      byte bt[] = new byte[size];
      while((in.read(bt,0,size)!=-1)&&(size>0))
      {
        strRet = new String(bt, "Unicode");
      }
      return strRet;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return "";
    }
  }
  
  
/*
 * ����һ����ѯ������
 * @param strFiledName  �ֶ���
 * @param strOperation  ������
 * @param strValue      ֵ
 * @return Document     Document����
 */  
  public static Document makeCondition(String strFiledName,
                                       String strOperation,
                                       String strValue)
  {
    
    String strXML = "<CONDITION alias='' datatype=''>" +
                    "<FIELDNAME>" + strFiledName + "</FIELDNAME>" +
                    "<OPERATION>" + strOperation + "</OPERATION>" +
                    "<VALUE>" + strValue + "</VALUE>" +
                    "</CONDITION>";
    Document doc = null;
    try
    {
       doc = DocumentHelper.parseText(strXML);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return doc;
  }
  
 /*
 * ����һ����ѯ������
 * @param strType
 * @param xmlCon1
 * @param xmlcon2
 * @return Document
 */  
  public static Document addConditionItem(String strType,
                                          Document xmlCon1,
                                          Document xmlcon2)
  {
    String strXML = "<CONDITIONS>" +
                    "<TYPE>" + strType + "</TYPE>" +
                    "</CONDITIONS>";
    Document doc = null;
    try
    {
       doc = DocumentHelper.parseText(strXML);
       if(xmlCon1!=null)
       {
         doc.getRootElement().add(xmlCon1.getRootElement());
       }
       if(xmlcon2!=null)
       {
         doc.getRootElement().add(xmlcon2.getRootElement());
       }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return doc;
  }
  
  /*
   * �����׼��ѯXML�ĵ��ַ���
   */
  public static Document makeQueryCondition(String strPredicate,Document xmlCon)
  {
    String strXML = "<?xml version='1.0'?>" +
             "<" + Common.XDOC_ROOT + " efsframe='" + Common.PVAL_SOURCE + "' version='" + Common.PVAL_VERSION + "'>" +
             "<QUERYCONDITION>" +
             "<PREDICATE>" + strPredicate + "</PREDICATE>" +
             "</QUERYCONDITION>" +
             "</" + Common.XDOC_ROOT + ">";
    Document doc = null;
    try
    {
       doc = DocumentHelper.parseText(strXML);
       if(xmlCon!=null)
       {
         doc.getRootElement().element("QUERYCONDITION").add(xmlCon.getRootElement());
       }
       
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return doc;
  }
  

}
