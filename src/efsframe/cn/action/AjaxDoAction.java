package efsframe.cn.action;


import efsframe.cn.base.XmlFunc;
import efsframe.cn.baseManage.Dic;
import efsframe.cn.baseManage.IdentifyBO;
import efsframe.cn.baseManage.IdentifyQueryBO;
import efsframe.cn.baseManage.MaxIDTypeBO;
import efsframe.cn.baseManage.RoleBO;
import efsframe.cn.baseManage.UnitsBO;
import efsframe.cn.baseManage.UserBO;
import efsframe.cn.person.*;
import efsframe.cn.classes.*;
import efsframe.cn.func.*;
import org.dom4j.Document;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import efsframe.cn.cache.*;

public class AjaxDoAction {
  public AjaxDoAction() {
  }
  
  /**
   * ������������б�
   */
  public void getRsQryAffairTypeList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");           //��仰����Ҫ
      PrintWriter out=response.getWriter();
      String strRet=request.getParameter("txtXML");
      
      String strRetXml = IdentifyQueryBO.affairTypeList(strRet);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
 
  /**
   * ����¼������б�
   */
  public void getRsQryEventTypeList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");           //��仰����Ҫ
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.eventTypeList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * ����û������б�
   */
  public void getRsQryUserList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
	    response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");           //��仰����Ҫ
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.userList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void getUserDetail(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.userDetail(request.getParameter("txtUserID"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  /**
   * ��ý�ɫ�����б�
   */
  public void getRsQryRoleList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.roleList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * ��ѯ��ɫȨ����Ϣ�б��б��أ�
   */
  public void getRsQryRoleRightList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.roleRightList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * ��ѯ��ɫȨ����Ϣ�б��б��أ�
   */
  public void getRsQryRoleUserList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.roleUserList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * ��ѯδ�����ڽ�ɫ�е�Ȩ����Ϣ�б��б��أ�
   */
  public void getEventTypeList_AddToRole(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.eventTypeList_AddToRole(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * ��ѯ������־�б��б��أ�
   */
  public void getRsQryErrorLogList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.errorLogList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  
  /**
   * ��ѯϵͳ��־�б��б��أ�
   */
  public void getRsQrySysLogList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.sysLogList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * ����ɫ����û�
   */
  public void backAddUserToRole(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();

      String strRetXml = RoleBO.addUserToRole(request.getParameter("txtOpXml"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * �ӽ�ɫɾ���û�
   */
  public void backDropUserfrmRole(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRetXml = RoleBO.dropUserFromRole(request.getParameter("txtOpXml"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * ����ɫ����¼�
   */
  public void backAddEventToRole(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtOpXml");
      
      String strRetXml = RoleBO.addEventToRole(strXML);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * �ӽ�ɫ��ɾ���¼�
   */
  public void backDropEventfrmRole(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRetXml = RoleBO.dropEventFromRole(request.getParameter("txtOpXml"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * ��ѯ��λ��Ϣ�б��б��أ�
   */
  public void getRsQryMunitList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      
      String strRetXml = IdentifyQueryBO.munitList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  // ��ѯ��λ��ϸ��Ϣ
  public void getMunitDetail(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.munitDetail(request.getParameter("txtUnitID"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * ����ֵ��б�
   */
  public void getRsQryDicList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRet=request.getParameter("txtXML");
      
      String strRetXml = Dic.dicList(strRet);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void getRsQryDicDataList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      Document doc = XmlFunc.CreateNewDoc(strXML);
      String sDicName = XmlFunc.getAttrValue(doc,"EFSFRAME/QUERYCONDITION","dicname");
      doc = null;
      
      String strRetXml = Dic.dicDataList(strXML,sDicName);
      
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  /**
   * ��ú����б�
   */
  public void getRsQrySpellList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.spellList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  
  
  
  /**
   * ��ѯ�����б�
   */
  public void qryTaskList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.qryTaskList(request.getParameter("txtXML"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void userList_AddToRole(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      
      String strRetXml = IdentifyQueryBO.userList_AddToRole(strXML);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }  
  
  
  //��ѯ�û�Ȩ��
  public void getUserRight(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strRetXml = IdentifyBO.getUserRightsByUserID(userSession.getUserID()).getXML();
      //����һ��xml�ַ���
      out.write(strRetXml);
      System.out.println(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  // �����Ա
  public void userAdd(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      String strRetXml = UserBO.addNew(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  // �޸���Ա
  public void userEdit(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      String strRetXml = UserBO.edit(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  // ��λ����
  public void unitDeal(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      String strRetXml = UnitsBO.addOrEdit(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }
  
  // ��ѯ�����б�
  public void getQryMaxTypeList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRet=request.getParameter("txtXML");
      
      String strRetXml = IdentifyQueryBO.maxidtypeList(strRet);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  // ��ѯ��λ��ϸ��Ϣ
  public void getMaxTYpeDetail(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = IdentifyQueryBO.maxidtypeDetail(request.getParameter("txtMaxIDType"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  // ��ѯ�ѷ�������б�
  public void getQryMaxList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      
      String strRetXml = IdentifyQueryBO.maxidList(strQuery);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  // ���봦��
  public void maxidTypeDeal(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtOpXml");
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      String strRetXml = MaxIDTypeBO.dealXml(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }
  
  // ���ѧ������
  public void psnAdd(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      System.out.println(strXML);
      
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      System.out.println(strToXml);
      
      String strRetXml = PersonBO.addNew(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  

  // 
  public void PsnDeal(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      System.out.println(strToXml);
      
      String strRetXml = PersonBO.dealXml(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }  
  // ��ѯѧ���б�
  public void getPersonList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRet=request.getParameter("txtXML");
      
      System.out.println("Con:" + strRet);
      String strRetXml = PersonBO.personList(strRet);
      System.out.println(strRetXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  public void getPersonListSv(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRet=request.getParameter("txtXML");
      
      String strRetXml = PersonBO.personListSv(strRet);
      
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  // ��ѯѧ����ϸ��Ϣ
  public void getPersonDetail(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strRetXml = PersonBO.personDetail(request.getParameter("txtPersonID"));
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void proAdd(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      System.out.println(strXML);
      
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      System.out.println(strToXml);
      
      String strRetXml = ProductBO.AddNew(strToXml);
      System.out.println(strRetXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  //��ѯ��Ʒ�б�
  public void getProList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRet=request.getParameter("txtXML");
      
      String strRetXml = ProductBO.proList(strRet);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  public void getProDetail(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strPId = request.getParameter("txtXML");
      
      String strRetXml = ProductBO.detail(strPId);
      System.out.println(strRetXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  
  public void ordAdd(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      String strRetXml = ProductBO.AddOrd(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  public void ordAdd2(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      String strRetXml = ProductBO.AddOrd2(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  public void ordDel(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strOrdID = request.getParameter("txtXML");
            
      String strRetXml = ProductBO.DelOrd(strOrdID);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void ordEdit(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtXML");
      
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXML,userSession);
      
      String strRetXml = ProductBO.EditOrd(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void getOrdList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strRet=request.getParameter("txtXML");
      
      String strRetXml = ProductBO.ordList(strRet);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  //
  public void getOrdDetail(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strOrdID=request.getParameter("txtXML");
      
      String strRetXml = ProductBO.ordDetail(strOrdID);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  public void getOrdProList(HttpServletRequest request, HttpServletResponse response,String strQuery)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
        
      PrintWriter out=response.getWriter();
      String strOrdID=request.getParameter("txtXML");
      
      String strRetXml = ProductBO.ordproList(strOrdID);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}