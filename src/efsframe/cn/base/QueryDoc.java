package efsframe.cn.base;

import org.dom4j.*;

import java.util.*;

import efsframe.cn.declare.*;
import efsframe.cn.func.General;

/**
 * ���ڶԱ�׼��ѯ�ṹXML�ĵ����з�������
 */
public class QueryDoc implements java.io.Serializable
{
  private static final long serialVersionUID = -1981973515297855263L;
  
  private Document m_doc_Self = null;
  private int intPageSize;
  private int intCurrentPage;

  /**
   * ʵ����ʼ��
   * @param objDoc          Document  XML �ĵ�����
   */
  public QueryDoc(Document objDoc) throws Exception
  {
    this.m_doc_Self = objDoc;
  }

  /**
   * ʵ����ʼ��
   */
  public QueryDoc(String strXML) throws Exception
  {
    this(DocumentHelper.parseText(strXML));

    Element ele_Condition = this.getCondition();
    
    ////  ���ÿҳ��¼��
    String str_Return = ele_Condition.attributeValue(Common.XML_PROP_RECORDSPERPAGE);
    this.setIntPageSize(str_Return==null ? 10 : Integer.parseInt(str_Return));
    
    ///  ��õ�ǰ����ѯҳ��
    str_Return = ele_Condition.attributeValue(Common.XML_PROP_CURRENTPAGENUM);
    this.setIntCurrentPage(str_Return==null ? 1 : Integer.parseInt(str_Return));
    
  }

  /**
   * ��ý����õ�T-SQL��ѯWhere���
   * @return String          �����õ�T-SQL��ѯWhere���
   */
  public String getConditions() throws Exception
  {
    Element ele_Root = m_doc_Self.getRootElement();
    Element ele_Conditions = ele_Root.element(Common.XDOC_QUERYCONDITION);
    if(ele_Conditions==null)
    {
      return null;
    }
    return parseConditions(ele_Conditions);
  }

  /**
   * ���� XML �ĵ������õ�T-SQL��ѯWhere���
   * @return String          �����õ�T-SQL��ѯWhere���
   */
  private String parseConditions(Element ele) throws Exception
  {
    Iterator it_Condition = ele.elementIterator(Common.XDOC_CONDITION);
    Iterator it_Conditions = ele.elementIterator(Common.XDOC_CONDITIONS);
    
    String str_Where = "";
    String str_Type = "";

    str_Type = ele.elementText(Common.XDOC_TYPE);
    str_Type = General.empty(str_Type) ? "" : str_Type.trim().toUpperCase();

    int int_Count = 0;

    while (it_Condition.hasNext())
    {
      /// ��ò�ѯ�ڵ�
      if (!General.empty(str_Where))
      {
        if (General.empty(str_Type)) throw new Exception("QueryDoc.parseConditions.77.δ�ṩ����");

        str_Where += Common.SPACE + str_Type + Common.SPACE;
      }

      int_Count++;

      Element ele_Condition = (Element)it_Condition.next();

      String str_Alias     = ele_Condition.attributeValue(Common.XML_PROP_ALIAS);
      String str_DataType  = ele_Condition.attributeValue(Common.XML_PROP_DATATYPE);
      int int_DataType     = General.empty(str_DataType) ? 0 : Integer.parseInt(str_DataType);

      String str_FieldName = ele_Condition.elementText(Common.XDOC_FIELDNAME);
      str_FieldName = General.empty(str_Alias) ? str_FieldName : str_Alias + Common.DOT +str_FieldName;

      /// +
      /// ����ǲ�ѯ�������ڣ����ø��ֵ�� sv ���ԣ�����ȷ���Ƿ��ǰ�����������ѯ��
      /// ����� AGERANGE ��˵���ǰ�����������ѯ
      /// ����� �գ���˵���ǰ��ճ�����������ѯ
      String str_Sv        = ele_Condition.attributeValue(Common.XDOC_FIELDNAME, Common.XML_PROP_SV);
      String str_Operation = ele_Condition.elementText(Common.XDOC_OPERATION);
      String str_DataValue = ele_Condition.elementText(Common.XDOC_VALUE);
      
      str_Operation = str_Operation.trim().toUpperCase();

      /// ���������������͵��ֶ�
      if (int_DataType==3)
      {
        /// �����������в�ѯ
        if (str_Sv.equals(Common.XML_AGERANGE))
        {
          str_Where += General.opYearDate(str_FieldName, str_DataValue, str_Operation);
        }
        else
        {
          str_Where += General.opDate(str_FieldName, str_DataValue, str_Operation);
        }
      }
      else
      {
        if (int_DataType==Common.IDT_BINARY)
          throw new Exception("QueryDoc.parseConditions.�������ܴ�����������͵�����");
        
        if(str_Operation.toUpperCase().equals("NOT IN") || str_Operation.toUpperCase().equals("IS") || str_Operation.toUpperCase().equals("IS NOT"))
        {
        	str_Where += str_FieldName + Common.SPACE +
			  			 str_Operation + Common.SPACE +
			  			str_DataValue ;	
        }
        else
        {
        	str_Where += str_FieldName + Common.SPACE +
            			 str_Operation + Common.SPACE +
            			 General.converType(int_DataType, str_DataValue, str_FieldName, null);	
        }
        
      }
    }

    while (it_Conditions.hasNext())
    {
      int_Count++;

      Element ele_Conditions = (Element)it_Conditions.next();

      /// ��ò�ѯ�ڵ�
      if (!General.empty(str_Where))
      {
        if (General.empty(str_Type))
          throw new Exception("QueryDoc.parseConditions.137.δ�ṩ����");

        str_Where += Common.SPACE + str_Type + Common.SPACE;
      }

      str_Where += parseConditions(ele_Conditions);
    }

    return int_Count>1 ? General.addBracket(str_Where) : str_Where;
  }

  /**
   * �����ĵ��� XML �ַ���
   * @return String          ��ǰDocument�ĵ���XML �ַ���
   */
  public String getXML() throws Exception
  {
    return m_doc_Self.asXML();
  }
  
  /**
   * �����ĵ���XML�ĵ�����
   * @return Document        ��ǰ�ĵ���XML�ĵ�����
   */
  public Document getDocument()
  {
    return this.m_doc_Self;
  }

  /**
   * ���ĵ��л�ò�ѯ�ڵ�
   * @return Element         ��ѯ�ڵ�
   */
  public Element getCondition()
  {
    Element ele_Condition = (Element)this.m_doc_Self.selectSingleNode(Common.XDOC_ROOT + Common.BAR +
                                                                      Common.XDOC_QUERYCONDITION);
    return ele_Condition;
  }

  public int getIntCurrentPage() {
    return intCurrentPage;
  }

  public void setIntCurrentPage(int intCurrentPage) {    
    this.intCurrentPage = intCurrentPage;
  }

  public int getIntPageSize() {
    return intPageSize;
  }

  public void setIntPageSize(int intPageSize) {
    this.intPageSize = intPageSize;
  }
  

}
