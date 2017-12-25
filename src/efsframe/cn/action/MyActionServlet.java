package efsframe.cn.action;

import org.apache.struts.action.ActionServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyActionServlet extends ActionServlet {

	private static final long serialVersionUID = 929117821112114879L;

	protected void process(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, 
	javax.servlet.ServletException {
	 /**@todo Override this org.apache.struts.action.ActionServlet method*/ 
		request.setCharacterEncoding("UTF-8");//就加着一行一切都解决了 
	  super.process(request, response); 
	} 

}
