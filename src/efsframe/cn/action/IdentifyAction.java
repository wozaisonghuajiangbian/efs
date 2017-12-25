package efsframe.cn.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.*;
import efsframe.cn.cache.*;
import efsframe.cn.func.*;
import efsframe.cn.base.XmlFunc;
import efsframe.cn.baseManage.AffairTypeBO;
import efsframe.cn.baseManage.Dic;
import efsframe.cn.baseManage.EventTypeBO;
import efsframe.cn.baseManage.IdentifyBO;
import efsframe.cn.baseManage.IdentifyQueryBO;
import efsframe.cn.baseManage.RoleBO;
import efsframe.cn.baseManage.UserBO;
import efsframe.cn.person.PersonBO;

public class IdentifyAction extends DispatchAction
{
  public ActionForward login(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception
  {
    try
    {
      //����xml�ļ�
      String strXml = request.getParameter("txtXML");
      Document doc = DocumentHelper.parseText(strXml);

      //�ӻ����ж�ȡ�û���Ϣ
      UserCache usercache = (UserCache)request.getSession().getServletContext().getAttribute("g_user");

      UserLogonInfo user = IdentifyBO.loginOn(doc,usercache);
      if(user != null)
      {
        request.getSession().setAttribute("user",user);

        return mapping.findForward("success");
      }
      return null;
    }
    catch(Exception e)
    {
      //��������쳣����ת����¼ҳ��
      request.setAttribute("url","default.jsp");
      request.setAttribute("msg",e.getMessage());
      return mapping.findForward("failed");
    }
  }

  // �ɹ���ʾ������
  public ActionForward goSuHint(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response,String url,String msg)
  {
    try
    {
      String strMsg = msg;
      strMsg = strMsg.replaceAll("'","��");
      strMsg = strMsg.replaceAll("\\(","��");
      strMsg = strMsg.replaceAll("\\)","��");
      strMsg = strMsg.replaceAll("\n","<br>");

      request.setAttribute("url",url);
      request.setAttribute("msg",strMsg);
      return mapping.findForward("goSuHint");
    }
    catch(Exception e)
    {
      request.setAttribute("url","back");
      request.setAttribute("msg",e.getMessage());
      return mapping.findForward("goErHint");
    }
  }


  // ������ʾ������
  public ActionForward goErHint(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response,String url,String msg)
  {
    try
    {
      String strMsg = msg;
      strMsg = strMsg.replaceAll("'","��");
      strMsg = strMsg.replaceAll("\\(","��");
      strMsg = strMsg.replaceAll("\\)","��");
      strMsg = strMsg.replaceAll("\n","<br>");

      url = General.empty(url)?"back":url;
      request.setAttribute("url",url);
      request.setAttribute("msg",strMsg);
      return mapping.findForward("goErHint");
    }
    catch(Exception e)
    {
      request.setAttribute("url","back");
      request.setAttribute("msg",e.getMessage());
      return mapping.findForward("goErHint");
    }
  }

  // ������
  public ActionForward affairDeal(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception
  {

    String strXml = request.getParameter("txtXML");

    UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
    String strToXml = PageCommon.setDocXML(strXml,userSession);

    String url="",msg="";
    String strRetXml = AffairTypeBO.addOrEdit(strToXml);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="developer/affairtypelist.jsp";
      msg="ά������ɹ��������������ֵ��ļ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="developer/affairtypelist.jsp";
      msg="ά������ʧ�ܣ�ʧ��ԭ��" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }

  // ɾ������
  public void affairDel(ActionMapping mapping, ActionForm form,
                        HttpServletRequest request, HttpServletResponse response) {
    PrintWriter out = null;
    try {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      String strAffairTypeID = request.getParameter("txtXML");

      out = response.getWriter();

      // ҵ�񷵻�
      String strRetXml = AffairTypeBO.drop(strAffairTypeID);

      out.write(strRetXml);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != out) {
        out.close();
      }
    }
  }



  // ������
  public ActionForward eventDeal(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
  {
    String strXml = request.getParameter("txtXML");
    UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
    String strToXml = PageCommon.setDocXML(strXml,userSession);

    String url="",msg="";
    String strRetXml = EventTypeBO.addOrEdit(strToXml);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="developer/eventtypelist.jsp";
      msg="ά���¼��ɹ��������������ֵ��ļ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="developer/eventtypelist.jsp";
      msg="ά���¼�ʧ�ܣ�ʧ��ԭ��" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }

  //ɾ���¼�����
  public void eventDel(ActionMapping mapping, ActionForm form,
                       HttpServletRequest request, HttpServletResponse response) {
    PrintWriter out = null;
    try {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      String strEventTypeID = request.getParameter("txtXML");

      out = response.getWriter();

      // ҵ�񷵻�
      String strRetXml = EventTypeBO.drop(strEventTypeID);

      out.write(strRetXml);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != out) {
        out.close();
      }
    }
  }

  // ��ɫ����
  public ActionForward roleAddNew(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception
  {
    String strXml = request.getParameter("txtXML");
    UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
    String strToXml = PageCommon.setDocXML(strXml,userSession);

    String url="",msg="";
    String strRetXml = RoleBO.addNew(strToXml);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="sysadmin/rolelist.jsp";
      msg="��ӽ�ɫ�ɹ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="back";
      msg="��ӽ�ɫʧ�ܣ�ʧ��ԭ��" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }

  // ��ɫ����
  public ActionForward roleEdit(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception
  {
    String strXml = request.getParameter("txtXML");
    UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
    String strToXml = PageCommon.setDocXML(strXml,userSession);

    String url="",msg="";
    String strRetXml = RoleBO.edit(strToXml);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="sysadmin/rolelist.jsp";
      msg="�޸Ľ�ɫ�ɹ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="back";
      msg="�޸Ľ�ɫʧ�ܣ�ʧ��ԭ��" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }

  // ��ɫ����
  public ActionForward roleDrop(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception
  {
    String strXml = request.getParameter("txtXML");
    UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
    String strToXml = PageCommon.setDocXML(strXml,userSession);

    String url="",msg="";
    String strRetXml = RoleBO.drop(strToXml);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="sysadmin/rolelist.jsp";
      msg="ɾ����ɫ�ɹ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="sysadmin/rolelist.jsp";
      msg="ɾ����ɫʧ�ܣ�ʧ��ԭ��" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }

  // ɾ���û�
  public void userDrop(ActionMapping mapping, ActionForm form,
                       HttpServletRequest request,
                       HttpServletResponse response)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");

      PrintWriter out=response.getWriter();

      String strXml = request.getParameter("txtOpXml");
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXml,userSession);

      String strRetXml = UserBO.drop(strToXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  // �޸��û�����
  public void userSetPassword(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");

      PrintWriter out=response.getWriter();

      String strXml = request.getParameter("txtOpXml");
      UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
      strXml = PageCommon.setDocXML(strXml,userSession);

      String strRetXml = UserBO.setPassword(strXml);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }



  // �����ֵ��ļ�
  public ActionForward toCreateDicFile(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception
  {
    String strDicName = request.getParameter("txtXML");
    String strUrl = request.getParameter("txtNextUrl");
    try
    {
      General.createDicFile(strDicName);
      return goSuHint(mapping,form,request,response,strUrl,"�����ֵ��ļ��ɹ���");
    }
    catch(Exception e)
    {
      return goErHint(mapping,form,request,response,strUrl,"�����ֵ��ļ�ʧ�ܣ�");
    }
  }


  // �����ֵ��ļ�
  public void CreateDicFile(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  {
    try
    {
      response.setContentType("text/html;charset=utf-8");
      request.setCharacterEncoding("UTF-8");

      PrintWriter out=response.getWriter();

      General.createDicFile(request.getParameter("txtDicName"));
      out.write("");
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }



  // ������ֵ�
  public ActionForward dicAddNew(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
  {
    String sDicName = request.getParameter("txtDicName");
    String sDicDes = request.getParameter("txtDicDes");
    String sTextLen = request.getParameter("txtTextLen");
    String sCodeLen  = request.getParameter("txtCodeLen");
    String sEditable = request.getParameter("txtEditable");

    String url="",msg="";
    String strRetXml = Dic.addNewDic(sDicName, sDicDes, sCodeLen, sTextLen, sEditable);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="sysadmin/diclist.jsp";
      msg="����ֵ�ɹ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="sysadmin/diclist.jsp";
      msg="����ֵ�ʧ�ܣ�ʧ��ԭ��" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }

  // ά���ֵ���Ŀ
  public void dicVindicate(ActionMapping mapping, ActionForm form,
                           HttpServletRequest request,
                           HttpServletResponse response)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strXML = request.getParameter("txtOpXml");
      Document doc = XmlFunc.CreateNewDoc(strXML);

      String strDicName = XmlFunc.getNodeValue(doc,"DICDATA/DICNAME");
      String strDicCode = XmlFunc.getNodeValue(doc,"DICDATA/DIC_CODE");
      String strDicText = XmlFunc.getNodeValue(doc,"DICDATA/DIC_TEXT");
      String strDicValidCode = XmlFunc.getNodeValue(doc,"DICDATA/DICVALID");

      String strRetXml = Dic.vindicate(strDicName, strDicCode, strDicText, strDicValidCode);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  public void dicDelItem(ActionMapping mapping, ActionForm form,
                         HttpServletRequest request,HttpServletResponse response)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strDicName = request.getParameter("txtDicName");
      String strDicCode = request.getParameter("txtDicCode");

      String strRetXml = Dic.deleteItem(strDicName, strDicCode);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void dicDel(ActionMapping mapping, ActionForm form,
                     HttpServletRequest request,HttpServletResponse response)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      PrintWriter out=response.getWriter();
      String strDicName = request.getParameter("txtDicName");

      String strRetXml = Dic.deleteDic(strDicName);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  public ActionForward spellDeal(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
  {
    char sText = (request.getParameter("txtText").toCharArray())[0];
    char sSpell = (request.getParameter("txtSpell").toCharArray())[0];
    String sASpell = request.getParameter("txtASpell");

    String url="",msg="";
    try
    {
      SpellCache.getInstance().update(sText,sSpell,sASpell);
      url="sysadmin/spelllist.jsp";
      msg="ά�����ֳɹ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    catch(Exception e)
    {
      url="back";
      msg="ά������ʧ�ܣ�ʧ��ԭ��" + e.getMessage();
      return goErHint(mapping,form,request,response,url,msg);
    }
  }




  // �����û�����
  public ActionForward SetPassword(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception
  {
    String strXml = request.getParameter("txtXML");
    UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
    String strToXml = PageCommon.setDocXML(strXml,userSession);

    String url="",msg="";
    String strRetXml = UserBO.setPassword(strToXml);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="task.jsp";
      msg="�����û�����ɹ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="back";
      msg="�����û�����ʧ�ܣ�ʧ��ԭ��" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }


  // ��ѯ�ѷ�������б�
  public void getQryMaxList(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  {
    try
    {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");

      PrintWriter out=response.getWriter();

      String strIDType = request.getParameter("txtXML");

      String strRetXml = IdentifyQueryBO.maxidList(strIDType);
      out.write(strRetXml);
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  //������
  public ActionForward psnAdd2(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception
  {

    String strXml = request.getParameter("txtXML");
    // strXml = new String(strXml).getBytes("GBK");

    UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
    String strToXml = PageCommon.setDocXML(strXml,userSession);

    String url="",msg="";
    String strRetXml = PersonBO.addNew(strToXml);

    if(PageCommon.IsSucceed(strRetXml))
    {
      url="task.jsp";
      msg="��ӳɹ���";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    else
    {
      String strErr = PageCommon.getErrInfo(strRetXml);
      url="task.jsp";
      msg="���ʧ�ܣ�" + strErr;
      return goErHint(mapping,form,request,response,url,msg);
    }
  }
}


