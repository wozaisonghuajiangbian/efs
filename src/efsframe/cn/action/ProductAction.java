package efsframe.cn.action;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import efsframe.cn.classes.ProductBO;

public class ProductAction extends BaseAction {

  // 新增班级
  public void AddNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
    // 获取默认参数
    PrintWriter out = null;
    try
    {
      String strXml = request.getParameter("txtXML");
      String strRet = ProductBO.AddNew(strXml);
  
      response.setContentType("text/xml;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      // 获取默认参数
      
      out= response.getWriter();
          
      out.write(strRet);
  
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally{
      if(null != out){
        out.close();
      }
    }
  }
  
}
