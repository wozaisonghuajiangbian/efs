package efsframe.cn.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import efsframe.cn.classes.ClassesBO;

public class ClassesAction extends BaseAction {
	private ClassesBO service;

	public ClassesAction() {
		service = new ClassesBO();
	}
	
	/**
	 * 根据msg信息选择跳转，如果msg为null为成功，否则跳转到失败页面
	 * @param url 成功跳转路径
	 * @param msg 成功为null，不为null则为失败提示信息
	 * @param mapping
	 * @param request
	 * @return ActionForward
	 */
	private ActionForward forward(String url,String msg,ActionMapping mapping,HttpServletRequest request){
		if(null == msg){
			request.setAttribute("url", url);
			request.setAttribute("msg", "操作成功!");
			return mapping.findForward("goSuHint");
		} else {
			request.setAttribute("url", "back");
			request.setAttribute("msg", msg);
			return mapping.findForward("goErHint");
		}
	}

	// 新增班级
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		// 获取默认参数
		String strXml = request.getParameter("txtXML");
		// 操作成功后跳转页面路径
		String strNextUrl = request.getParameter("txtNextUrl");
		
		String strRetXml = service.add(strXml);
		
		return forward(strNextUrl,strRetXml,mapping,request);
	}
	
	// 班级列表
	public void list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;

		try {
			response.setContentType("text/xml;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			// 获取默认参数
			String strXml = request.getParameter("txtXML");
			
			out= response.getWriter();
			
			// 业务返回
			String strRetXml = service.list(strXml);
			
			out.write(strRetXml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.close();
			}
		}
	}
	
	// 查看班级详细信息
	public void detail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		
		try {
			response.setContentType("text/xml;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			// 获取默认参数
			String strXml = request.getParameter("txtXML");
			
			out= response.getWriter();
			
			// 业务返回
			String strRetXml = service.detail(strXml);
			
			out.write(strRetXml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.close();
			}
		}
	}
	
	// 学生列表（含班级编号）
	public void personList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		
		try {
			response.setContentType("text/xml;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			// 获取默认参数
			String strXml = request.getParameter("txtXML");
			
			out= response.getWriter();
			
			// 业务返回
			String strRetXml = service.personList(strXml);
			
			out.write(strRetXml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.close();
			}
		}
	}
}
