package efsframe.cn.base;

/**
 * Document����Ĵ�����
 * ��dom4j��Dom����Ĵ�����м򵥷�װ���������
 * @author enjsky
 */
import java.io.File;
import java.util.Iterator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import efsframe.cn.declare.Common;
import org.dom4j.io.SAXReader;

public class XmlFunc
{
  /**
   * ��XML���õ���Path��ʽ��Ϊ��׼�ĸ�ʽ
   * @param nodePath     ԭʼ��ֵ
   * @return String      ��ʽ��֮���Pathֵ
   */
 public static String formatNodePath(String nodePath)
  {
    String strTruePath;
    String strEnd,strBegin;
    strEnd = nodePath.substring(nodePath.length()-1,nodePath.length());
    strBegin = nodePath.substring(0,1);
    if(!strBegin.equals("/"))
      strBegin = "//";
    else
      strBegin = "";
    
    if(strEnd.equals("/"))
    {
      strTruePath = strBegin + nodePath.substring(0,nodePath.length()-1);
    }
    else
    {
      strTruePath = strBegin + nodePath;
    }
    return strTruePath;
  }
  
  
  /**
   * ����һ��Document����
   * @param              strXml
   * @return             Document ����
   * @throws             Exception
   */
  public static Document CreateNewDoc(String strXml) throws Exception
  {
    try
    {
      Document doc = DocumentHelper.parseText(strXml);
      return doc;
    }
    catch(Exception e)
    {      
      throw(new Exception("����һ��Document����ʧ��"));       
    }
  }
  
  /**
   * ���DOM����ĸ��ڵ�     
   * @param doc            Document����
   * @return               Element����
   */
  public static Element getRootElement(Document doc)
  {
    return doc.getRootElement();
  }
  
  /**
   * ���һ��Dom������ӽڵ���               
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @return             ����һ��Iterator����
   */
  public static Iterator getIterator(Document doc,String nodePath)
  {
    try
    {
      Element el = getNodeElement(doc,nodePath);
      Iterator itList = el.elementIterator();
      return itList;
    }
    catch(Exception e)
    {
      return null;
    }
  }

  /**
   * ���һ��Dom������ӽڵ���           
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @param i            ��i���ڵ�
   * @return             ����һ��Iterator����
   */
  public static Iterator getIterator(Document doc,String nodePath,int i)
  {
    try
    {
      Element el = getNodeElement(doc,nodePath,i);
      Iterator itList = el.elementIterator();
      return itList;
    }
    catch(Exception e)
    {
      return null;
    }
  }

  /**
   * ���һ��Element������ӽڵ���             
   * @param el           Element����
   * @return             ����һ��Iterator����
   */
  public static Iterator getIterator(Element el)
  {
    try
    {
      Iterator itList = el.elementIterator();
      return itList;
    }
    catch(Exception e)
    {
      return null;
    }
  }

  /**
   * ���һ��Dom������ӽڵ���           
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @return             ����һ��Iterator��������
   */
  public static Iterator[] getIteratorArr(Document doc,String nodePath)
  {
    try
    {
      int nodeLen = doc.selectNodes(formatNodePath(nodePath)).size();
      Iterator[] itList = new Iterator[nodeLen];
      Element el;
      for(int i=0;i<nodeLen;i++)
      {
        el = getNodeElement(doc,nodePath,i);
        itList[i] = el.elementIterator();
      }  
      return itList;
    }
    catch(Exception e)
    {
      return null;
    }
  }
  
  
  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��һ��Element��������
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @return             Element��������
   */
  public static Element[] getNodeElementArr(Document doc,String nodePath)
  {
    try
    {
      int nodeLen = doc.selectNodes(formatNodePath(nodePath)).size();
      Element[] elArr = new Element[nodeLen];
      Node node;
      for(int i=0;i<nodeLen;i++)
      {
        node = getNode(doc,nodePath,i);
        elArr[i] = (Element)node;
      }  
      return elArr;
    }
    catch(Exception e)
    {
      return null;
    }
  }


  /**
   * ��һ��Node��������ת��ΪElement��������
   * @param nodeArr         Node��������
   * @return             Element��������
   */
  public static Element[] getNodeElementArr(Node[] nodeArr)
  {
    try
    {
      Element[] elArr = new Element[nodeArr.length];
      for(int i=0;i<nodeArr.length;i++)
      {
        elArr[i] = (Element)nodeArr[i];
      }
      return elArr;
    }
    catch(Exception e)
    {
      return null;
    }
  }
    

  /**
   * ��һ��Node����ת��ΪElement����
   * @param node         Node����
   * @return             Element����
   */
  public static Element getNodeElement(Node node)
  {
    try
    {
      return (Element)node;
    }
    catch(Exception e)
    {
      return null;
    }
  }
  
  
  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��һ��Element����
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @return             Element����
   */
  public static Element getNodeElement(Document doc,String nodePath)
  {
    try
    {
      Node node = getNode(doc,nodePath);
      return (Element)node;
    }
    catch(Exception e)
    {
      return null;
    }
  }
  
  
  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��һ��Element����
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @param i            ��Ӧ�ĵڼ����ڵ�
   * @return             Element����
   */
  public static Element getNodeElement(Document doc,String nodePath,int i)
  {
    try
    {
      Node node = getNode(doc,nodePath,i);
      return (Element)node;
    }
    catch(Exception e)
    {
      return null;
    }
  }

  
  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��һ��Node��������
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @return             Node��������
   */
  public static Node[] getNodeArr(Document doc,String nodePath)
  {
    try
    {
      int nodeLen = doc.selectNodes(formatNodePath(nodePath)).size();
      Node[] nodeArr = new Node[nodeLen];
      for(int i=0;i<nodeLen;i++)
      {
        nodeArr[i] = getNode(doc,nodePath,i);
      }  
      return nodeArr;
    }
    catch(Exception e)
    {
      return null;
    }
  }

  

  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��һ��Node����
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @return             Node����
   */
  public static Node getNode(Document doc,String nodePath)
  {
    try
    {
      String strTruePath = formatNodePath(nodePath);
      Node node = doc.selectSingleNode(strTruePath);
      return node;
    }
    catch(Exception e)
    {
      return null;
    }
  }
  
  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��һ��Node����
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @param i            ��Ӧ�ĵڼ����ڵ�
   * @return             Node����
   */
  public static Node getNode(Document doc,String nodePath,int i)
  {
    try
    {
      Node node = (Node)doc.selectNodes(nodePath).get(i);
      return node;
    }
    catch(Exception e)
    {
      return null;
    }
  }

  /**
   * ���һ��Node�����ֵ
   * @param node             Document����
   * @return                 ����Node��ֵ
   */
  public static String getNodeValue(Node node)
  {
    try
    {
      return node.getText();
    }
    catch(Exception e)
    {
      return "";
    }
  }
  
  /**
   * ���һ��Node���������һ���ӽڵ��ֵ
   * @param node             Document����
   * @param childNodeName    �ӽڵ�����
   * @return                 �����ӽڵ��ֵ
   */
  public static String getNodeValue(Node node,String childNodeName)
  {
    try
    {
      return node.valueOf("@" + childNodeName);
    }
    catch(Exception e)
    {
      return "";
    }
  }
  
  
  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��Valueֵ��ֻ���ҵ������ڵ�
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @return             ���ؽڵ�ֵ
   */
  public static String getNodeValue(Document doc,String nodePath)
  {
    try
    {
      Node node = getNode(doc,nodePath);
      return node.getText();
    }
    catch(Exception e)
    {
      return "";
    }
  }
  
  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��Valueֵ��ֻ���ҵ������ڵ�
   * @param doc          Document����
   * @param nodePath     Node��·��
   * @param i            ��Ӧ�ĵڼ����ڵ�
   * @return             ���ؽڵ�ֵ
   */
  public static String getNodeValue(Document doc,String nodePath,int i)
  {
    try
    {
      Node node = getNode(doc,nodePath,i);
      return node.getText();
    }
    catch(Exception e)
    {
      return "";
    }
    
  }

  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��¶�Ӧ���Ե�ֵ��ֻ���ҵ������ڵ�
   * @param doc           Document����
   * @param nodePath      Node��·��
   * @param attName       ��������
   * @return              ����ֵ
   */
  public static String getAttrValue(Document doc,String nodePath,String attName)
  {
    try
    {
      String strTruePath = formatNodePath(nodePath) + "/@" + attName;
      Node node = doc.selectSingleNode(strTruePath);
      return node.getText();
    }
    catch(Exception e)
    {
      return "";
    }
  }

  /**
   * ���һ��Dom�����ж�Ӧ�ڵ��¶�Ӧ���Ե�ֵ
   * @param doc           Document����
   * @param nodePath      Node��·��
   * @param attName       ��������
   * @param i             �ڼ����ڵ�
   * @return              ����ֵ
   */
  public static String getAttrValue(Document doc,String nodePath,String attName,int i)
  {
    try
    {
      String strTruePath = formatNodePath(nodePath) + "/@" + attName;
      Node node = (Node)doc.selectNodes(strTruePath).get(i);
      return node.getText();
    }
    catch(Exception e)
    {
      return "";
    }
  }
  
  /**
   * ���һ��Element�����ж�Ӧ���Ե�ֵ
   * @param el            Element����
   * @param attName       ��������
   * @return              ����ֵ
   */
  public static String getAttrValue(Element el,String attName)
  {
    try
    {
      return el.attributeValue(attName);
    }
    catch(Exception e)
    {
      return "";
    }
  }
  
  /**
   * ����һ��Dom�����ж�Ӧ�ڵ��¶�Ӧ���Ե�ֵ��ֻ���ҵ������ڵ㣬û�оʹ���
   * @param doc           Document����
   * @param nodePath      Node��·��
   * @param attName       ��������
   * @param attValue      ����ֵ
   * @return              boolean �Ƿ�ɹ�
   */
  public static boolean setAttrValue(Document doc,String nodePath,String attName,String attValue)
  {
    try
    {
        Element el = getNodeElement(doc,nodePath);
        if(el==null)
          return false;
        if(setAttrValue(el,attName,attValue))
          return true;
        else
          return false;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ����һ��Dom�����ж�Ӧ�ڵ��¶�Ӧ���Ե�ֵ��û�оʹ���
   * @param doc           Document����
   * @param nodePath      Node��·��
   * @param attName       ��������
   * @param attValue      ����ֵ
   * @param index         �ڵ�����
   * @return              boolean �Ƿ�ɹ�
   */
  public static boolean setAttrValue(Document doc,String nodePath,String attName,String attValue,int index)
  {
    try
    {
        Element el = getNodeElement(doc,nodePath,index);
        if(el==null)
          return false;
        if(setAttrValue(el,attName,attValue))
          return true;
        else
          return false;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ����һ��Element�����ж�Ӧ���Ե�ֵ
   * @param el            Element����
   * @param attName       ��������
   * @return              boolean �Ƿ�ɹ�
   */
  public static boolean setAttrValue(Element el,String attName,String attValue)
  {
    try
    {
      // ����Element��û�ж�Ӧ�����Զ����������������о͸�ֵ��û�оʹ���
      el.addAttribute(attName,attValue);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   * ɾ��һ��Element�����ж�Ӧ����
   * @param ele           Element����
   * @param attributeName ������
   * @return  boolean     �Ƿ�ɹ�
   */
  public static boolean  removeAttr(Element ele, String attributeName)
  {
    try
    {
      Attribute att = ele.attribute(attributeName);
      ele.remove(att);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ɾ��һ��Document������ָ���ڵ��µĶ�Ӧ����
   * @param doc           Dom����
   * @param nodePath      �ڵ�·��
   * @param attributeName ������
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean  removeAttr(Document doc,String nodePath, String attributeName)
  {
    try
    {
      Element ele = getNodeElement(doc,nodePath);
      return removeAttr(ele, attributeName);
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ɾ��һ��Document������ָ���ڵ��µĶ�Ӧ����
   * @param doc           Dom����
   * @param nodePath      �ڵ�·��
   * @param index         �ڵ�����
   * @param attributeName ������
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean  removeAttr(Document doc,String nodePath,int index, String attributeName)
  {
    try
    {
      Element ele = getNodeElement(doc,nodePath,index);
      return removeAttr(ele, attributeName);
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ����xPath ·�����������¹���Document����û�еĽڵ��Զ�����
   * @param doc           Document����
   * @param nodePath      xPath ·��
   * @return              boolean  �Ƿ�ɹ�
   */
  public static boolean createNodePath(Document doc,String nodePath)
  {
    try
    {
      String strTmpNodePath = nodePath.toUpperCase();
      Node node = getNode(doc,strTmpNodePath);
      if(node != null)
        return true;
      else
      {
        String strNodeArr[];
        strNodeArr = strTmpNodePath.split(Common.BAR);
        Element rootEle = getRootElement(doc);
        //A ~~~
        // ���Ȳ������ϲ�ڵ㣬���û�У����ڸ��ڵ����洴��
        Element el = getNodeElement(doc,strNodeArr[0].toUpperCase()); 
        if(el==null)
        {
          Element ele = DocumentHelper.createElement(strNodeArr[0].toUpperCase());
          rootEle.add(ele);
        }
        // A ~~~
        
        // B~~~
        // �������е��ӽڵ㣬û�оʹ���
        for(int i=1;i<strNodeArr.length;i++)
        {
          if(strNodeArr[i] != null)
          {
            String strNodePathTmp = "";
            
            for(int j=0;j<i;j++)
            {
              strNodePathTmp = strNodePathTmp + strNodeArr[j].toUpperCase() + Common.BAR; 
            }
            
            // �õ�ָ���ڵ�
            el = getNodeElement(doc,strNodePathTmp + strNodeArr[i].toUpperCase());
            
            if(el==null)
            {
              el = getNodeElement(doc,strNodePathTmp);
              Element ele = DocumentHelper.createElement(strNodeArr[i].toUpperCase());
              el.add(ele);
            }
          }         
        }
        //B~~
      }
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ����Node�����ֵ
   * @param node          Node�ڵ�         
   * @param strValue      �趨�Ľڵ�ֵ
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean setNodeValue(Node node,String strValue)
  {
    try
    {
      node.setText(strValue);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ����Document������ָ��xPath·���ڵ��ֵ
   * @param doc           Dom����
   * @param nodePath      �ڵ�·��
   * @param strValue      �ڵ�ֵ
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean setNodeValue(Document doc,String nodePath,String strValue)
  {
    try
    {
      createNodePath(doc,nodePath);
      Node node = getNode(doc,nodePath);
      node.setText(strValue);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ����Document������ָ��xPath·���ڵ��ֵ
   * @param doc           Dom����
   * @param nodePath      �ڵ�·��
   * @param index         �ڵ�����
   * @param strValue      �ڵ�ֵ
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean setNodeValue(Document doc,String nodePath,int index,String strValue)
  {
    try
    {
      createNodePath(doc,nodePath);
      Node node = getNode(doc,nodePath,index);
      node.setText(strValue);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ɾ��Element�����е�һ����Element����
   * @param parentEle     ��Element����
   * @param childEle      ��Element����
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean removeNode(Element parentEle, Element childEle)
  {
    try
    {
      parentEle.remove(childEle);
      return true;
    }
    catch(Exception e)
    {
      return false;
    } 
  }
  
  /**
   * ɾ��Document�����е�ָ��·�����ӽڵ�
   * @param doc           Dom����
   * @param nodePath      �ڵ�·��
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean removeNode(Document doc, String nodePath)
  {
    try
    {
      Element childEle = getNodeElement(doc,nodePath);
      Element parentEle = childEle.getParent();
      return removeNode(parentEle,childEle);
    }
    catch(Exception e)
    {
      return false;
    } 
  }
  
  /**
   * ɾ��Document�����е�ָ��·�����ӽڵ�
   * @param doc           Dom����
   * @param nodePath      �ڵ�·��
   * @param index         �ڵ�����
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean removeNode(Document doc, String nodePath,int index)
  {
    try
    {
      Element childEle = getNodeElement(doc,nodePath,index);
      Element parentEle = childEle.getParent();
      return removeNode(parentEle,childEle);
    }
    catch(Exception e)
    {
      return false;
    } 
  }
  
  
  /**
   * ��һ�η��Ϲ淶��XML�ַ�����ӵ�һ��Element������ȥ
   * @param nodeEle       Element�ڵ����
   * @param strXml        �������XML�ַ���
   * @return              boolean �Ƿ�ɹ�
   */
  public static boolean xmlAddToElement(Element nodeEle,String strXml)
  {
    try
    {
      Document doc = CreateNewDoc(strXml);
      Element rootEle = getRootElement(doc);
      nodeEle.add(rootEle);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * ��һ�η��Ϲ淶��XML�ַ���,����ָ����·����ӵ�һ��Document������ȥ
   * @param doc           Document����
   * @param nodePath      �ڵ�·��
   * @param strXML        XML�ַ���
   * @return boolean      �Ƿ�ɹ�
   */
  public static boolean setNodeDOM(Document doc,String nodePath,String strXML)
  {
    try
    {
      Element nodeEle = getNodeElement(doc,nodePath);
      if(nodeEle == null)
      {
        if(createNodePath(doc,nodePath))
          nodeEle = getNodeElement(doc,nodePath);
      }
      xmlAddToElement(nodeEle,strXML);
      
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
 
  /**
   * �����ӽڵ�
   * @param eleRootExp  ���ڵ�
   * @param nodename    �ڵ�����
   * @param nodevalue   �ڵ�ֵ
   * @return            Element����
   */
  public static Element createSubNode(Element eleRootExp,String nodename,String nodevalue)
  {
    Element ele = DocumentHelper.createElement(nodename);
    ele.setText(nodevalue);
    eleRootExp.add(ele);
    return ele;
  }
  
  /**
   * ���ӽڵ㸳ֵ
   * @param eleRootExp  ���ڵ�
   * @param nodename    �ڵ�����
   * @param nodevalue   �ڵ�ֵ
   */
  public static void setSubNodeValue(Element eleRootExp,String nodename,String nodevalue)
  {
    try
    {
      Element ele = eleRootExp.element(nodename);
      if (ele==null) return;
      ele.setText(nodevalue);
    }
    catch(Exception e)
    {
      
    }
  }  
  
  /**
  * ��ýڵ���������ӽڵ�����
  * @param eleRootExp     ���ڵ�
  * @param propName    ��������
  * @param propValue   ����ֵ
  * @return boolean       �Ƿ�ɹ�
  */
  public static boolean setAllSubNodeProperty(Element eleRootExp,String propName,String propValue)
  {
    try
    {
      if(eleRootExp == null) return false;
      Iterator it = eleRootExp.elementIterator();
      while(it.hasNext())
      {
        Element ele = (Element)it.next();
        ele.addAttribute(propName,propValue);
      }
      return true;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return false;
      
    }
  }
  
  // 
  public static void main(String[] args) throws Exception
  {
    Document doc = null;
    SAXReader saxReader = new SAXReader();
    doc = saxReader.read(new File("c:/aaa.xml"));
    System.out.println(doc.asXML());

    // Document doc = XmlFunc.CreateNewDoc("<lexelt item='����' snum='2'><instance id='0001'>2005����ŷԪ�����õ��������ĵ�10����ͷ�����ڵľ��õ�������ʹʧҵ�ʸ߾Ӳ��£����¸���Ա���Ľṹ�ĸ�ٻ���Ҳ�������ص���ᶯ����ͬʱ����������<head>����</head>Ҳǰ��δ�еؼ���ŷ���ڲ��Է�չģʽ֮����</instance><instance id='0002'>ŷ��ͳ�ƾ֣����չ����ĳ���������ʾ��ŷԪ������������Ⱦ�ҵ��������<head>����</head>����ҵ������ǰһ�����½���0.1%��������ʾ������������ȣ�ŷԪ����������ҵ�����������ˣ��򡣵���ȥ��ͬ����ȣ���ҵ�����������ˣ���������</instance></lexelt>");
    for(int i = 0;i<doc.selectNodes("//instance").size();i++)
    {
      System.out.println(XmlFunc.removeNode(doc, "//head", 0));
    }
    System.out.println(doc.asXML());
  }
}