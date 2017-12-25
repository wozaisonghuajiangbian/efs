package efsframe.cn.baseManage;

import java.sql.ResultSet;

import org.dom4j.*;

import efsframe.cn.base.ReturnDoc;
import efsframe.cn.base.SystemLog;
import efsframe.cn.base.XmlFunc;
import efsframe.cn.cache.*;
import efsframe.cn.func.*;
import efsframe.cn.db.DBConnection;
import efsframe.cn.declare.*;

public class IdentifyBO
{
  
  /*********************************************************
   * �û���¼
   * @param doc             XML �����ĵ�
   * @param usercache       �û�������Ϣ
   * @return UserLogonInfo  �����û���Ϣ
  ***********************************************************/
  public static UserLogonInfo loginOn(Document doc,UserCache usercache) throws Exception
  {
    //��ȡxml�ļ��е��û���Ϣ
    String strUsertitle = XmlFunc.getNodeValue(doc,Common.XDOC_LOGININFO + Common.BAR + Field.USERTITLE);
    String strPassWord = XmlFunc.getNodeValue(doc,Common.XDOC_LOGININFO + Common.BAR + Field.USERPASSWORD);
    String strIP = XmlFunc.getNodeValue(doc,Common.XDOC_LOGININFO + Common.BAR + Field.LOGINIP);
    String strMac = XmlFunc.getNodeValue(doc,Common.XDOC_LOGININFO + Common.BAR + Field.MAC);
    usercache = UserCache.getInstance();

    //�ӻ����ж�ȡ�û���¼��Ϣ
    UserLogonInfo user = usercache.getUserLogonByLoginName(strUsertitle);
    //��������в����ڸ��û�����Ϣ
    if(user == null)
    {
      //ˢ���û�����
      usercache.refresh_u(strUsertitle);
      //�����ݿ��л�ȡ�û���Ϣ
      user = usercache.getUserLogonByLoginName(strUsertitle);
    }
    
    if(user == null)
    { 
      throw new Exception("�û���������");
    }
    else
    {
      if(!strPassWord.equals(user.getUserPassword()))
      {
        throw new Exception("�û��������벻ƥ�䲻����");
      }
      user.setLoginIP(strIP);
      user.setMAC(strMac);
      
      MUnitCache munit = MUnitCache.getInstance();
      user.setUnitName(munit.getMUnitByID(user.getUnitID()).getMUnitName());
      user.setMUnitType(munit.getMUnitByID(user.getUnitID()).getMType());
      user.setMLevel(munit.getMUnitByID(user.getUnitID()).getMLevel());
      String[] arrSys = {user.getUserID(),user.getUserTitle(),user.getUserName(),"",user.getUnitID(),user.getUnitName(),user.getLoginIP(),user.getMAC()};
      String logid = SystemLog.addSysLog(arrSys);
      user.setLogID(logid);
    }
    return user;
  }
  

  /**
   * �����û����ˢ�¸��û���Ȩ�޹������ĵ�����
   * @param strUserID           �û����
   * @return ReturnDoc           ���û���Ȩ�޹������ĵ�����
   */
  public static ReturnDoc getUserRightsByUserID(String strUserID) throws Exception
  {
    /// ��ѯ�����û���Ȩ����Ϣ
    String str_SQL = Common.SELECT  + Common.DISTINCT        + Common.ALL   +
                     Common.S_FROM  + Table.VW_USERRIGHTTREE +
                     Common.S_WHERE + Field.USERID           + Common.EQUAL + General.addQuotes(strUserID) + 
                     Common.S_ORDER + Field.AFFAIRTYPEID     + Common.COMMA + Field.EVENTTYPEID;

    
    DBConnection dbc = new DBConnection();
    ResultSet rst_UserRight = null;
    try
    {
      rst_UserRight = dbc.excuteQuery(str_SQL);
    
      if(rst_UserRight == null)
      {
        throw new Exception("����û�Ȩ��ʧ��");
      }
  
      String str_PreAffairTypeID = "";
      String str_PreEventTypeID = "";
  
      ReturnDoc doc_RightTree = new ReturnDoc();
  
      Element ele_Root = null;
      Element ele_Query = null;
      Element ele_AffairType = null;
  
      /// �Խ�������б������������ɹ�����
      while (rst_UserRight.next())
      {
        /// ������ѯ���ؽڵ�
        if (!doc_RightTree.createQueryInfoNode())
        {  
          throw new Exception("UserCache.setUserRightsByUserID.������ѯ���ؽڵ�ʱ��������");
        } /// if (!doc_RightTree.createQueryInfoNode())
  
        ele_Root = ele_Root==null? (Element)doc_RightTree.getQueryInfoNode() : ele_Root;
  
        String str_AffairTypeID   = rst_UserRight.getString(Field.AFFAIRTYPEID);
        String str_AffairTypeName = rst_UserRight.getString(Field.AFFAIRTYPENAME);
        String str_EventTypeID    = rst_UserRight.getString(Field.EVENTTYPEID);
        String str_EventTypeName  = rst_UserRight.getString(Field.EVENTTYPENAME);
        String str_OpURL          = rst_UserRight.getString(Field.OPURL);
  
        int int_AffairTypeID = Integer.parseInt(str_AffairTypeID);
  
        Element ele_EventType = null;
  
        /// ��ѯ����
        if (int_AffairTypeID==4)
        {  
          ele_AffairType = DocumentHelper.createElement(Common.XDOC_OPERATION);
          ele_AffairType.addAttribute(Common.XML_PROP_AFFAIRTYPEID, str_AffairTypeID);
          ele_AffairType.addAttribute(Common.XML_PROP_NAME, str_AffairTypeName);
          ele_Query = ele_AffairType;
          ele_Root.add(ele_AffairType);
        } /// if (int_AffairTypeID==4)
        else
        {
          if (!str_PreAffairTypeID.endsWith(str_AffairTypeID))
          {
            ele_AffairType = DocumentHelper.createElement(Table.AFFAIRTYPE);
            ele_AffairType.addAttribute(Common.XML_PROP_AFFAIRTYPEID, str_AffairTypeID);
            ele_AffairType.addAttribute(Common.XML_PROP_TEXT, str_AffairTypeName);
            // ele_AffairType.addAttribute("expanded", "true");
            str_PreAffairTypeID = str_AffairTypeID;
            ele_Root.add(ele_AffairType);
          } /// if (!str_PreAffairTypeID.endsWith(str_AffairTypeID))
        } /// else if(int_AffairTypeID<100000 || (int_AffairTypeID>=111000 && int_AffairTypeID<=111999) || int_AffairTypeID>300000)
  
        /// ��ͬ���¼����ͣ������ظ�����
        if (!str_PreEventTypeID.equals(str_EventTypeID))
        {
          ele_EventType = DocumentHelper.createElement(Table.EVENTTYPE);
          ele_EventType.addAttribute(Common.XML_PROP_EVENTTYPEID, str_EventTypeID);
          ele_EventType.addAttribute(Common.XML_PROP_TEXT, str_EventTypeName);
          ele_EventType.addAttribute(Common.XML_PROP_OPURL, str_OpURL);
          ele_AffairType.add(ele_EventType);
          str_PreEventTypeID = str_EventTypeID;
        } /// if (!str_PreEventTypeID.equals(str_EventTypeID))
      } /// while (rst_UserRight.next())
      
      
  
      /// ����ѯ����ڵ㣬׷�ӵ�Ȩ�޹����������
      if (ele_Query!=null)
      {
        Element ele_TempQuery = (Element)ele_Query.clone();
        Element ele_QueryInfo = (Element)doc_RightTree.getQueryInfoNode();
        ele_QueryInfo.remove(ele_Query);
        ele_QueryInfo.add(ele_TempQuery);
      } /// if (ele_Query!=null)
      
      if (!doc_RightTree.addErrorResult(Common.RT_QUERY_SUCCESS))
      {
        throw new Exception("��Ӻ������ؽ��ʧ��");
      } /// if (!doc_RightTree.addErrorResult(Common.RT_QUERY_SUCCESS))
      
      return doc_RightTree;
    }
    catch(Exception e)
    {
      throw e;
    }
    finally
    {
      rst_UserRight.close();
      dbc.freeConnection();
    }
  }
  
}
