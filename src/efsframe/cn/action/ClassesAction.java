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
	 * ����msg��Ϣѡ����ת�����msgΪnullΪ�ɹ���������ת��ʧ��ҳ��
	 * @param url �ɹ���ת·��
	 * @param msg �ɹ�Ϊnull����Ϊnull��Ϊʧ����ʾ��Ϣ
	 * @param mapping
	 * @param request
	 * @return ActionForward
	 */
	private ActionForward forward(String url,String msg,ActionMapping mapping,HttpServletRequest request){
		if(null == msg){
			request.setAttribute("url", url);
			request.setAttribute("msg", "�����ɹ�!");
			return mapping.findForward("goSuHint");
		} else {
			request.setAttribute("url", "back");
			request.setAttribute("msg", msg);
			return mapping.findForward("goErHint");
		}
	}

	// �����༶
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		// ��ȡĬ�ϲ���
		String strXml = request.getParameter("txtXML");
		// �����ɹ�����תҳ��·��
		String strNextUrl = request.getParameter("txtNextUrl");
		
		String strRetXml = service.add(strXml);
		
		return forward(strNextUrl,strRetXml,mapping,request);
	}
	
	// �༶�б�
	public void list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;

		try {
			response.setContentType("text/xml;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			// ��ȡĬ�ϲ���
			String strXml = request.getParameter("txtXML");
			
			out= response.getWriter();
			
			// ҵ�񷵻�
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
	
	// �鿴�༶��ϸ��Ϣ
	public void detail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		
		try {
			response.setContentType("text/xml;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			// ��ȡĬ�ϲ���
			String strXml = request.getParameter("txtXML");
			
			out= response.getWriter();
			
			// ҵ�񷵻�
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
	
	// ѧ���б����༶��ţ�
	public void personList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		
		try {
			response.setContentType("text/xml;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			// ��ȡĬ�ϲ���
			String strXml = request.getParameter("txtXML");
			
			out= response.getWriter();
			
			// ҵ�񷵻�
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
