package efsframe.cn.action;

import java.io.PrintWriter;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import efsframe.cn.base.Operation;
import efsframe.cn.cache.UserLogonInfo;
import efsframe.cn.func.PageCommon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseAction extends DispatchAction {
	public BaseAction() {
	}
	
    
    public ActionForward add(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
      String url="developer/affairtypelist.jsp";
      String msg="添加事务成功！";
      return goSuHint(mapping,form,request,response,url,msg);
    }
    
    
    public ActionForward goSuHint(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,String url,String msg)
    {
      try
      {
        request.setAttribute("url",url);
        request.setAttribute("msg",msg);
        return mapping.findForward("goSuHint");
      }
      catch(Exception e)
      {
        request.setAttribute("url","back");
        request.setAttribute("msg",e.getMessage());
        return mapping.findForward("goErHint");
      }
    }
    

    public ActionForward test(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
      return null;
    }
    
    /**
     * 处理通用XML
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public void dealWithXml(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) {
    PrintWriter out = null;
    try {
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      // 获取默认参数
      String strXml = request.getParameter("txtXML");

      UserLogonInfo userSession = (UserLogonInfo) request.getSession()
          .getAttribute("user");
      String strToXml = PageCommon.setDocXML(strXml, userSession);

      out = response.getWriter();

      // 业务返回
      String strRetXml = Operation.dealWithXml(strToXml);

      out.write(strRetXml);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != out) {
        out.close();
      }
    }
  }
    
  
}
