package efsframe.cn.base;

import java.util.*;
import org.dom4j.*;


import efsframe.cn.declare.*;

public class DataDoc implements java.io.Serializable
{
  private static final long serialVersionUID = 5208375922929537616L;
  
  private Document m_doc_Self = null;

  /**
   * ʵ����ʼ��
   * @param objDoc          Document  XML �ĵ�����
   */
  public DataDoc(Document objDoc) throws Exception
  {
    m_doc_Self = objDoc;
  }

  /**
   * ʵ����ʼ��
   * @param strXML          XML �ĵ��ַ���
   */
  public DataDoc(String strXML) throws Exception
  {
    this(DocumentHelper.parseText(strXML));
  }

  /**
   * ʵ����ʼ����Ĭ�ϵĸ��ڵ�
   */
  public DataDoc() throws Exception
  {
    this(Common.XML_HEADINFO + "<" + Common.XDOC_ROOT + "/>");

    //��xml�ļ����н���
    Element ele_root = m_doc_Self.getRootElement();

    ele_root.addAttribute(Common.XML_PROP_SOURCE, Common.PVAL_SOURCE);
    ele_root.addAttribute(Common.XML_PROP_VERSION, Common.PVAL_VERSION);
  }
  
  /**
   * ������ݽڵ���ָ���ڵ����ƵĽڵ�����Ŀ
   * @param strNodeName     �ڵ�����
   * @return int             ���ݽڵ��µ�ָ���ڵ����ƵĽڵ�����Ŀ
   */
  public int getDataNum(String strNodeName)
  {
    List list = m_doc_Self.selectNodes(Common.XDOC_ROOT     + Common.BAR +
                                  Common.XDOC_DATAINFO + Common.BAR + strNodeName);

    return list.size();
  }

  /**
   * ��ò�ѯ���ؽڵ���ָ���ڵ����ƵĽڵ�����Ŀ
   * @param strNodeName     �ڵ�����
   * @return int             ��ѯ�ڵ��µ�ָ���ڵ����ƵĽڵ�����Ŀ
   */
  public int getQueryNum(String strNodeName)
  {
    List list = m_doc_Self.selectNodes(Common.XDOC_ROOT      + Common.BAR +
                                       Common.XDOC_QUERYINFO + Common.BAR + strNodeName);

    return list.size();
  }

  /**
   * ������ݽڵ���ָ���ڵ������
   * @param strNodeName     �ڵ�����
   * @return String          ���ݽڵ���ָ���ڵ������
   */
  public String getDataValue(String strNodeName)
  {
    return XmlFunc.getNodeValue(m_doc_Self,
                                Common.XDOC_ROOT     + Common.BAR +
                                Common.XDOC_DATAINFO + Common.BAR + strNodeName);
  }

  /**
   * ����û���Ϣ�ڵ���ָ���ڵ������
   * @param strNodeName     �ڵ�����
   * @return String          �û���Ϣ�ڵ���ָ���ڵ������
   */
  public String gettUserValue(String strNodeName)
  {
    return XmlFunc.getNodeValue(m_doc_Self,
                                Common.XDOC_ROOT     + Common.BAR +
                                Common.XDOC_USERINFO + Common.BAR + strNodeName);
  }

  /**
   * ���������Ϣ�ڵ���ָ���ڵ������
   * @param strNodeName     �ڵ�����
   * @return String          ������Ϣ�ڵ���ָ���ڵ������
   */
  public String getAffairEventValue(String strNodeName)
  {
    return XmlFunc.getNodeValue(m_doc_Self,
                                Common.XDOC_ROOT        + Common.BAR +
                                Common.XDOC_AFFAIREVENT + Common.BAR + strNodeName);
  }

  /**
   * �������ݽڵ���ָ���ڵ������
   * @param strNodeName     �ڵ�����
   * @param strValue        �����õĽڵ�����
   * @return boolean         �Ƿ����óɹ�
   */
  public boolean setDataValue(String strNodeName, String strValue)
  {
    try
    {
      if (!strNodeName.startsWith(Common.BAR2))
        strNodeName = Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO + Common.BAR + strNodeName;

      Node nod_Temp = m_doc_Self.selectSingleNode(strNodeName);

      nod_Temp.setText(strValue);

      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   * ������ݽڵ���ָ���ڵ㣨�ڵ��飩������
   * @param strNodeName     �ڵ�����
   * @param intIndex        �ڵ������
   * @return String          ���ݽڵ���ָ���ڵ������
   */
  public String getDataValue(String strNodeName, int intIndex)
  {
     return XmlFunc.getNodeValue(m_doc_Self,
                                 Common.XDOC_ROOT     + Common.BAR +
                                 Common.XDOC_DATAINFO + Common.BAR + strNodeName,
                                 intIndex);
  }

  /**
   * ����û���Ϣ�ڵ�
   * @return Node            �û���Ϣ�ڵ�
   */
  public Node getUserNode()
  {
     return XmlFunc.getNode(m_doc_Self,
                            Common.XDOC_ROOT + Common.BAR + Common.XDOC_USERINFO);
  } 

  /**
   * ���������Ϣ���ڵ�
   * @return Node            ������Ϣ���ڵ�
   */
  public Node getDataRootNode()
  {
     return XmlFunc.getNode(m_doc_Self,
                            Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO);
  } 
  
  /**
   * ���������Ϣ�ڵ�
   * @return Node            ������Ϣ�ڵ�
   */
  public Node getAffairEventNode()
  {
     return XmlFunc.getNode(m_doc_Self,
                            Common.XDOC_ROOT + Common.BAR + Common.XDOC_AFFAIREVENT);
  } 

  /**
   * ������ݽڵ��µĽڵ�
   * @param strNodeName     �ڵ�����
   * @return Node            XML �ڵ�
   */
  public Node getDataNode(String strNodeName)
  {
    return XmlFunc.getNode(m_doc_Self,
                           Common.XDOC_ROOT     + Common.BAR +
                           Common.XDOC_DATAINFO + Common.BAR + strNodeName,
                           0);
  }

  /**
   * ������ݽڵ��µĽڵ�
   * @param strNodeName     �ڵ�����
   * @param intIndex        �ڵ������
   * @return Node            XML �ڵ�
   */
  public Node getDataNode(String strNodeName, int intIndex)
  {
    return XmlFunc.getNode(m_doc_Self,
                           Common.XDOC_ROOT     + Common.BAR +
                           Common.XDOC_DATAINFO + Common.BAR + strNodeName,
                           intIndex);
  }

  /**
   * ��ò�ѯ�ڵ��µĽڵ�
   * @param strNodeName     �ڵ�����
   * @return Node            XML �ڵ�
   */
  public Node getQueryNode(String strNodeName)
  {
    return XmlFunc.getNode(m_doc_Self,
                           Common.XDOC_ROOT      + Common.BAR +
                           Common.XDOC_QUERYINFO + Common.BAR + strNodeName,
                           0);
  }

  /**
   * ��ò�ѯ�ڵ��µĽڵ�
   * @param strNodeName     �ڵ�����
   * @param intIndex        �ڵ������
   * @return Node            XML �ڵ�
   */
  public Node getQueryNode(String strNodeName,int intIndex)
  {
    return XmlFunc.getNode(m_doc_Self,
                           Common.XDOC_ROOT      + Common.BAR +
                           Common.XDOC_QUERYINFO + Common.BAR + strNodeName,
                           intIndex);
  }

  /**
   * ������ݽڵ��µĽڵ���
   * @param strNodeName     �ڵ�����
   * @return List            XML �ڵ���
   */
  public List getDataNodeList(String strNodeName)
  {
    return m_doc_Self.selectNodes(Common.XDOC_ROOT     + Common.BAR +
                                  Common.XDOC_DATAINFO + Common.BAR + strNodeName);
  }

  /**
   * ��ò�ѯ�ڵ��µĽڵ���
   * @param strNodeName     �ڵ�����
   * @return List            XML �ڵ���
   */
  public List getQueryNodeList(String strNodeName)
  {
    return m_doc_Self.selectNodes(Common.XDOC_ROOT      + Common.BAR +
                                  Common.XDOC_QUERYINFO + Common.BAR + strNodeName);
  }

  /**
   * ���ĵ����ڵ������һ���ӽڵ�
   *        �ڱ������У����ԭ���Ѿ�����ͬ���ڵ㣬���ɾ��ԭ�ڵ�
   *        ���磺<EFSFRAME>
   *                 xnodInfo                             * ���ýڵ���ӵ��ĵ����ڵ���
   *              </EFSFRAME>
   * @param xnodInfo         ������ڵ�
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addNode(Node xnodInfo) throws Exception
  {
    try
    {
      Element ele_Root = m_doc_Self.getRootElement();

      if (ele_Root==null) return false;

      String str_NodeName = xnodInfo.getName();

      Node ele_Temp = null;

      /// ���ͬ���ڵ��Ƿ���ڣ�������ڣ���ɾ��֮
      if ((ele_Temp=getDataNode(str_NodeName.toUpperCase()))!=null)
      {
        ele_Root.remove(ele_Temp);
      }

      ele_Root.add(xnodInfo);

      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   * ���ĵ����ݽڵ������һ�� SQL �ű����ӽڵ�
   *        ���磺<EFSFRAME>
   *                <DATAINFO>
   *                  <SQLSCRIPT operation="5"/>          * ���ýڵ���ӵ��ĵ����ڵ���
   *                </DATAINFO>
   *              </EFSFRAME>
   * @param strSQL          ������ڵ�� SQL ���
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addSQLScript(String strSQL)
  {
    try
    {
      Element ele_DataInfo = XmlFunc.getNodeElement(m_doc_Self,
                                                    Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO);

      if(ele_DataInfo==null) return false;

      Element ele_Script = DocumentHelper.createElement(Common.XDOC_SQLSCRIPT);

      ele_Script.setText(strSQL);
      ele_Script.addAttribute(Common.XML_PROP_OPERATION, Common.DT_SQLSCRIPT);

      ele_DataInfo.add(ele_Script);

      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   * ���ĵ����ݽڵ������һ���ӽڵ�
   *        �ڱ������У����ԭ���Ѿ�����ͬ���ڵ㣬���ɾ��ԭ�ڵ�
   *        ���磺<EFSFRAME>
   *                <DATAINFO>
   *                  xnodInfo                            * ���ýڵ���ӵ��ĵ����ڵ���
   *                </DATAINFO>
   *              </EFSFRAME>
   * @param xnodInfo        ������ڵ�
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean addNodeToDataInfo(Node xnodInfo) throws Exception
  {
    try
    {
      Element ele_DataInfo = XmlFunc.getNodeElement(m_doc_Self,
                                                    Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO);

      if (ele_DataInfo==null) return false;

      String str_NodeName = xnodInfo.getName();
          
      Node ele_Temp = null;

      if ((ele_Temp=getDataNode(str_NodeName))!=null)
      {
        ele_DataInfo.remove(ele_Temp);
      }

      ele_DataInfo.add(xnodInfo);

      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   * ���ĵ����ڵ��µ�ָ���ӽڵ������һ���ӽڵ�
   *        �ڱ������У����ԭ���Ѿ�����ͬ���ڵ㣬���ɾ��ԭ�ڵ�
   *        ���磺<EFSFRAME>
   *                <strParentName>
   *                  xnodInfo                            * ���ýڵ���ӵ��ĵ����ڵ���
   *                </strParentName>
   *              </EFSFRAME>
   * @param strParentName   �ϼ��ڵ�����
   * @param xnodInfo        ������ڵ�
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean AddNodeTo(String strParentName, Node xnodInfo) throws Exception
  {
    try
    {
      Element ele_Data = XmlFunc.getNodeElement(m_doc_Self,
                                                Common.XDOC_ROOT + Common.BAR + strParentName.toUpperCase());

      if (ele_Data==null)
        throw new Exception("DataDoc.AddNodeTo.δ�ҵ�ָ���ĸ��ڵ�[" + strParentName.toUpperCase() + "]");

      String str_NodeName = xnodInfo.getName();

      Node ele_Temp = null;

      if ((ele_Temp=XmlFunc.getNode(m_doc_Self,
                                    Common.XDOC_ROOT            + Common.BAR +
                                    strParentName.toUpperCase() + Common.BAR + str_NodeName))!=null)
      {
        ele_Data.remove(ele_Temp);
      }

      ele_Data.add(xnodInfo);

      return true;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * ���ĵ����ڵ��´������ݽڵ�
   *            ������ݽڵ㲻���ڣ�����
   *            ������ݽڵ���ڣ�������
   * @return boolean         �Ƿ�ɹ�
   */
  public boolean createDataInfoNode()
  {
    try{
      List list = m_doc_Self.selectNodes(Common.XDOC_ROOT + Common.BAR + Common.XDOC_DATAINFO);

      int int_Size = list.size();

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
