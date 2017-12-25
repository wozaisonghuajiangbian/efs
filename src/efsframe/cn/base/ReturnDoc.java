package efsframe.cn.base;


import java.util.List;
import java.sql.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import efsframe.cn.declare.Common;
import efsframe.cn.func.General;

/**
 * ����ͳһ�ı�׼ҵ���������XML�ַ����ĵ�
 */
public class ReturnDoc implements java.io.Serializable
{
  private static final long serialVersionUID = 3682545988906963663L;
  private Document m_doc_Self = null;

  /**
   * ʵ����ʼ��
   * @param objDoc          Document  XML �ĵ�����
   */
  public ReturnDoc(Document objDoc) throws Exception
  {
    m_doc_Self = objDoc;
  }

  /**
   * ʵ����ʼ��
   * @param strXML          XML �ĵ��ַ���
   */
  public ReturnDoc(String strXML) throws Exception
  {
    this(DocumentHelper.parseText(strXML));
  }

  /**
   * ʵ����ʼ��
   */
  public ReturnDoc() throws Exception
  {
    this(Common.XML_HEADINFO + "<" + Common.XDOC_ROOT + "/>");

    Element ele_Root = m_doc_Self.getRootElement();

    ele_Root.addAttribute(Common.XML_PROP_SOURCE, Common.PVAL_SOURCE);
    ele_Root.addAttribute(Common.XML_PROP_VERSION, Common.PVAL_VERSION);
  }

  /**
   * ��ò�ѯ�ڵ��½ڵ������Ŀ
   * @param strNodeName     �ڵ�����
   * @return int             ��ѯ�ڵ��½ڵ������Ŀ
   */
  public int getQueryNum(String strNodeName)
  {
    List lst_Temp = m_doc_Self.selectNodes(Common.XDOC_ROOT      + Common.BAR +
                                           Common.XDOC_QUERYINFO + Common.BAR + strNodeName);
    return lst_Temp.size();
  }

  /**
   * ��ô�����Ϣ�ڵ��µĴ��������ڵ��ֵ
   * @param strNodeName      �ڵ�����
   * @return String          �ڵ��ֵ
   */
  public String getErrorValue(String strNodeName)
  {
    return XmlFunc.getNodeValue(m_doc_Self,
                                Common.XDOC_ROOT      + Common.BAR +
                                Common.XDOC_ERRORINFO + Common.BAR + strNodeName);
  }

  /**
   * ������ѯ���ؽڵ�
   *            ����ýڵ㲻���ڣ�����;
   *            ����ýڵ���ڣ�������
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean createQueryInfoNode()
  {
    try
    {
      List lst_QueryInfo = m_doc_Self.selectNodes(Common.XDOC_ROOT + Common.BAR + Common.XDOC_QUERYINFO);

      int int_Size = lst_QueryInfo.size();

      if(int_Size==0)
      {
        Element ele = DocumentHelper.createElement(Common.XDOC_QUERYINFO);
        m_doc_Self.getRootElement().add(ele);
      }

      return true;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    return false;
  }

  public boolean addQueryInfoNode()
  {
    return createQueryInfoNode();
  }

  /**
   * �������󷵻ؽڵ�
   *            ����ýڵ㲻���ڣ�����;
   *            ����ýڵ���ڣ�������
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean createErrorInfoNode()
  {
    try
    {
      List lst_ErrorInfo = m_doc_Self.selectNodes(Common.XDOC_ROOT + Common.BAR + Common.XDOC_ERRORINFO);

      int int_Size = lst_ErrorInfo.size();

      if(int_Size==0)
      {
        Element ele_ErrorInfo = DocumentHelper.createElement(Common.XDOC_ERRORINFO);
        m_doc_Self.getRootElement().add(ele_ErrorInfo);
      }
      return true;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public boolean addErrorInfoNode()
  {
    return createErrorInfoNode();
  }

  /**
   * ��ò�ѯ�ڵ�
   * @return Node            ��ѯ�ڵ�
   */
  public Node getQueryInfoNode()
  {
    return XmlFunc.getNode(m_doc_Self,
                           Common.XDOC_ROOT + Common.BAR + Common.XDOC_QUERYINFO);
  }

  /**
   * �ڴ�����Ϣ�ڵ�������ӽڵ�
   *            ����ýڵ㲻���ڣ�����Ҫ�����ýڵ㣬����ֵ
   *            ����ýڵ���ڣ�����¸ýڵ��ֵ
   * @param strNodeName      �ڵ�����
   * @param strResult        �ڵ�ֵ
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean setErrorNodeChild(String strNodeName, String strResult) throws Exception
  {
    try
    {
      if (!createErrorInfoNode())
        throw new Exception("ReturnDoc.setErrorNodeChild.�������ؽڵ�ʱ��������");

      Node nod_Temp = XmlFunc.getNode(m_doc_Self,
                                      Common.XDOC_ROOT + Common.BAR + Common.XDOC_ERRORINFO);

      if (nod_Temp==null)
        throw new Exception("ReturnDoc.setErrorNodeChild.��ȡ������ڵ�ʱ��������");

      Node nod_Child = XmlFunc.getNode(m_doc_Self,
                                       Common.XDOC_ROOT      + Common.BAR +
                                       Common.XDOC_ERRORINFO + Common.BAR + strNodeName);

      if (nod_Child==null)
      {
        Node nod_Temp2 = DocumentHelper.createElement(strNodeName);
        nod_Temp2.setText(strResult);
        ((Element)nod_Temp).add(nod_Temp2);
      }
      else
      {
        nod_Child.setText(String.valueOf(strResult));
      }
      return true;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * �ڴ�����Ϣ�ڵ�����Ӵ��󷵻�ֵ
   *            ����ýڵ㲻���ڣ�����Ҫ�����ýڵ㣬����ֵ
   *            ����ýڵ���ڣ�����¸ýڵ��ֵ
   * @param strResult        �ڵ�ֵ
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean setErrorResult(String strResult) throws Exception
  {
    return setErrorNodeChild(Common.XDOC_ERRORRESULT, strResult);
  }

  /**
   * �ڴ�����Ϣ�ڵ�����Ӵ��󷵻�ֵ
   *            ����ýڵ㲻���ڣ�����Ҫ�����ýڵ㣬����ֵ
   *            ����ýڵ���ڣ�����¸ýڵ��ֵ
   * @param strResult       �ڵ�ֵ
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean addErrorResult(String strResult) throws Exception
  {
    return setErrorResult(strResult);
  }

  /**
   * �ڴ�����Ϣ�ڵ�����Ӵ��󷵻�ֵ
   *            ����ýڵ㲻���ڣ�����Ҫ�����ýڵ㣬����ֵ
   *            ����ýڵ���ڣ�����¸ýڵ��ֵ
   * @param intResult       �ڵ�ֵ
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean setErrorResult(int intResult) throws Exception
  {
    return setErrorResult(String.valueOf(intResult));
  }

  /**
   * �ڴ�����Ϣ�ڵ�����Ӵ��󷵻�ֵ
   *            ����ýڵ㲻���ڣ�����Ҫ�����ýڵ㣬����ֵ
   *            ����ýڵ���ڣ�����¸ýڵ��ֵ
   * @param intResult       �ڵ�ֵ
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean addErrorResult(int intResult) throws Exception
  {
    return setErrorResult(intResult);
  }

  /**
   * �ڴ�����Ϣ�ڵ�����Ӵ��󷵻�����
   *            ����ýڵ㲻���ڣ�����Ҫ�����ýڵ㣬����ֵ
   *            ����ýڵ���ڣ�����¸ýڵ��ֵ
   * @param strResult         �ڵ�ֵ
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean setFuncErrorInfo(String strResult) throws Exception
  {
    return setErrorNodeChild(Common.XDOC_FUNCERROR, strResult);
  }

  /**
   * �ڴ�����Ϣ�ڵ�����Ӵ��󷵻�����
   *            ����ýڵ㲻���ڣ�����Ҫ�����ýڵ㣬����ֵ
   *            ����ýڵ���ڣ�����¸ýڵ��ֵ
   * @param strResult        �ڵ�ֵ
   * @return boolean         �Ƿ񴴽��ɹ�
   */
  public boolean addFuncErrorInfo(String strResult) throws Exception
  {
    return setFuncErrorInfo(strResult);
  }

  /**
   * ����ѯ���صĽ����ƴװ����Ҫ�� XML �Ľṹ
   * @param rst             ���صĽ����
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean getQueryInfo(ResultSet rst) throws Exception
  {
    return getQueryInfo(rst, Common.XDOC_ROW);
  }

  /**
   * ����ѯ���صĽ����ƴװ����Ҫ�� XML �Ľṹ
   * @param rst             ���صĽ����
   * @param strNodeName     QUERY �ӽڵ��µĽڵ�����
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean getQueryInfo(ResultSet rst, String strNodeName) throws Exception
  {
    try
    {
      strNodeName = General.empty(strNodeName) ? Common.XDOC_ROW : strNodeName;

      if (!createQueryInfoNode())
        throw new Exception("ReturnDoc.getQueryInfo.������ѯ���ؽڵ�ʱ��������");

      Element ele_Query = XmlFunc.getNodeElement(m_doc_Self, Common.XDOC_ROOT + Common.BAR + Common.XDOC_QUERYINFO);

      if (ele_Query==null)
        throw new Exception("ReturnDoc.getQueryInfo.��ò�ѯ���ؽڵ�ʱ��������");

      boolean bln_HasData = false;

      while (rst.next())
      {
        Element ele_Row = DocumentHelper.createElement(strNodeName);

        for(int i=1; i<rst.getMetaData().getColumnCount()+1; i++)
        {
          int int_Type = rst.getMetaData().getColumnType(i);
          String str_File = "";
          Element ele_Node = DocumentHelper.createElement(rst.getMetaData().getColumnName(i).toUpperCase());

          switch(int_Type)
          {
            case Types.BLOB:
            case Types.BINARY:
              Blob blob = rst.getBlob(i);

              /// ������������ת��Ϊ 16 ���Ʊ������
              if (blob!=null)
              {
                byte[] byt_Temp = new byte[(int)(blob.length())];
                byt_Temp = blob.getBytes(1, byt_Temp.length);
                str_File = General.byte2hex(byt_Temp);
              } /// if (blob!=null)

              ele_Node.setText(str_File);
              break;
            case Types.DATE:
              str_File =rst.getString(i);
           	  if(!General.empty(str_File))
           	  {
   	        	 String sv = General.cDateStr(str_File);
   	        	 ele_Node.setText(str_File);
   	        	 ele_Node.addAttribute("sv",sv);
           	  }
           	  else
           	  {
           		 ele_Node.setText(""); 
           	  }
            default:
              ele_Node.setText(rst.getString(i)==null ? "" : rst.getString(i));

          } /// switch(int_Type)

          ele_Row.add(ele_Node);
        }

        ele_Query.add(ele_Row);
        bln_HasData = true;
      }

      return bln_HasData;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * �ڲ�ѯ���������У����ĳ��������У�����һ���ڵ�
   *        ���磺<EFSFRAME>
   *                <QUERYINFO>
   *                  <ROW>
   *                    <FLD1>FLD1</FLD1>
   *                    <FLD2>FLD2</FLD2>
   *                    <FLD3>FLD3</FLD3>
   *                  </ROW>
   *                </QUERYINFO>
   *              </EFSFRAME>
   * @param strNodeName     QUERY �ӽڵ��µĽڵ�����
   * @param strValue        �ڵ�ֵ
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addRowToQuery(String strNodeName, String strValue) throws Exception
  {
    return addRowToQuery(strNodeName, strValue, 0);
  }

  /**
   * �ڲ�ѯ���������У����ĳ��������У�����һ���ڵ�
   *        ���磺<EFSFRAME>
   *                <QUERYINFO>
   *                  <ROW>
   *                    <FLD1>FLD1</FLD1>
   *                    <FLD2>FLD2</FLD2>
   *                    <FLD3>FLD3</FLD3>
   *                  </ROW>
   *                </QUERYINFO>
   *              </EFSFRAME>
   * @param strNodeName     QUERY �ӽڵ��µĽڵ�����
   * @param strValue        �ڵ�ֵ
   * @param intIndex        ����˳���
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addRowToQuery(String strNodeName, String strValue, int intIndex) throws Exception
  {
    try
    {
      Element ele_Row = XmlFunc.getNodeElement(m_doc_Self, Common.XDOC_ROOT + Common.BAR +
                                                           Common.XDOC_QUERYINFO + Common.BAR + Common.XDOC_ROW, intIndex);

      Element ele_Node = DocumentHelper.createElement(strNodeName);

      ele_Node.setText(strValue);
      ele_Row.add(ele_Node);

      return true;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * �ڷ����ĵ����ڵ��£�����һ���ڵ�
   *        ���磺<EFSFRAME>
   *                xnodInfo                              * ���ýڵ���ӵ������ĵ����ڵ���
   *              </EFSFRAME>
   * @param xnodInfo        ������Ľڵ�
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addNode(Node xnodInfo) throws Exception
  {
    try
    {
      Element ele_Root = XmlFunc.getNodeElement(m_doc_Self, Common.XDOC_ROOT);

      if (ele_Root==null)
        throw new Exception("ReturnDoc.addNode.��ȡ��ѯ���ظ��ڵ�ʱ��������");

      ele_Root.add(xnodInfo);

      return true;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * ���ĵ����ڵ��е���һ���ڵ����һ���ڵ�
   *        �ڱ������У����ԭ���Ѿ�����ͬ���ڵ㣬���ɾ��ԭ�ڵ�
   *        ���磺<EFSFRAME>
   *                <parentName>
   *                  xnodInfo                            * ���ýڵ���ӵ������ĵ����ڵ���
   *                </parentName> 
   *              </EFSFRAME>
   * @param strParentName   ������ĸ��ڵ������
   * @param xnodInfo        ������Ľڵ�
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addNodeTo(String strParentName, Node xnodInfo) throws Exception
  {
    try
    {
      Element ele_Node = XmlFunc.getNodeElement(m_doc_Self,Common.XDOC_ROOT + Common.BAR + strParentName.toUpperCase());

      if (ele_Node==null)
        throw new Exception("ReturnDoc.addNodeTo.����ӵĸ��ڵ�Ϊ��");

      String str_NodeName = xnodInfo.getName();

      Node tempnode = null;

      if ((tempnode=XmlFunc.getNode(m_doc_Self, Common.XDOC_ROOT + Common.BAR + strParentName.toUpperCase() + Common.BAR + str_NodeName))!=null)
      {
        ele_Node.remove(tempnode);
      }

      ele_Node.add(xnodInfo);
      return true;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * ���ѯ�������ݽڵ��У����һ���ڵ�
   *        �ڱ������У����ԭ���Ѿ�����ͬ���ڵ㣬���ɾ��ԭ�ڵ�
   *        ���磺<EFSFRAME>
   *                <QUERYINFO>
   *                  xnodInfo                            * ���ýڵ���ӵ������ĵ����ڵ���
   *                 </QUERYINFO> 
   *              </EFSFRAME>
   * @param xnodInfo        ������Ľڵ�
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addNodeToQueryInfo(Node xnodInfo) throws Exception
  {
    try
    {
      Element ele_Query = XmlFunc.getNodeElement(m_doc_Self, Common.XDOC_ROOT + Common.BAR + Common.XDOC_QUERYINFO);

      if(ele_Query==null)
        throw new Exception("ReturnDoc.addNodeToQueryInfo.��ȡ��ѯ���ظ��ڵ�ʱ��������");

      ele_Query.add(xnodInfo);

      return true;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * ���ѯ�������ݽڵ��У��������
   *        �ڱ������У����ԭ���Ѿ�����ͬ���ڵ㣬���ɾ��ԭ�ڵ�
   *        ���磺<EFSFRAME>
   *                <QUERYINFO> <--  PropInfo
   *                 </QUERYINFO> 
   *              </EFSFRAME>
   * @param strPropName     ��������
   * @param strPropValue    ����ֵ
   * @return boolean        �Ƿ�ɹ�
   */
  public boolean addPropToQueryInfo(String strPropName, String strPropValue) throws Exception
  {
    try
    {
      Element ele_Query = XmlFunc.getNodeElement(m_doc_Self, Common.XDOC_ROOT + Common.BAR + Common.XDOC_QUERYINFO);

      if (ele_Query==null)
        throw new Exception("ReturnDoc.addPropToQueryInfo.��ȡ��ѯ���ظ��ڵ�ʱ��������");

      ele_Query.addAttribute(strPropName,strPropValue);

      return true;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * �������ݽڵ�
   *            ����ýڵ㲻���ڣ�����
   *            ����ýڵ���ڣ�������
   *        ���磺<EFSFRAME>
   *                <DATAINFO>                            * ���ݽڵ�
   *                 </DATAINFO> 
   *              </EFSFRAME>
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean createDataInfoNode()
  {
    try
    {
      List lst_DataInfo = m_doc_Self.selectNodes(Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO);

      int int_Size = lst_DataInfo.size();

      if (int_Size==0)
      {
        Element ele_DataInfo = DocumentHelper.createElement(Common.XDOC_DATAINFO);
        m_doc_Self.getRootElement().add(ele_DataInfo);
      }
      return true;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * �����ݽڵ�������½ڵ�
   *        ���磺<EFSFRAME>
   *                <DATAINFO>
   *                  <--  xnodInfo                       * ���ýڵ���ӵ������ĵ����ڵ���
   *                 </DATAINFO> 
   *              </EFSFRAME>
   * @param xnodInfo        ������Ľڵ�
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addNodeToDataInfo(Node xnodInfo) throws Exception
  {
    try
    {
      Element ele_DataInfo = XmlFunc.getNodeElement(m_doc_Self,Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO);

      if (ele_DataInfo==null) return false;

      ele_DataInfo.add(xnodInfo);

      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   * �����ݽڵ���������
   *        ���磺<EFSFRAME>
   *                <DATAINFO>
   *                  <--  xnodInfo                       * ���ýڵ���ӵ������ĵ����ڵ���
   *                 </DATAINFO> 
   *              </EFSFRAME>
   * @param strPropName       ��������
   * @param strPropValue       ����ֵ
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addPropToDataInfo(String strPropName, String strPropValue) throws Exception
  {
    try
    {
      Element ele_DataInfo = XmlFunc.getNodeElement(m_doc_Self, Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO);

      if (ele_DataInfo==null)
        throw new Exception("ReturnDoc.addPropToDataInfo.��ȡ��ѯ���ظ��ڵ�ʱ��������");

     ele_DataInfo.addAttribute(strPropName, strPropValue);

     return true;
   }
   catch(Exception e)
   {
     throw new Exception(e.getMessage());
   }
  }

  /**
   * �����ĵ��� XML �ַ���
   * @return String          �ĵ��� XML �ַ���
   */
  public String getXML() throws Exception
  {
    return m_doc_Self.asXML();
  }

  /**
   * �����ĵ���XML�ĵ�����
   * @return Document        �ĵ���XML�ĵ�����
   */
  public Document getDocument() throws Exception
  {
    return m_doc_Self;
  }
}