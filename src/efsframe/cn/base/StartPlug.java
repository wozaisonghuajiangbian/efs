package efsframe.cn.base;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import efsframe.cn.cache.*;

public class StartPlug implements PlugIn, java.io.Serializable
{
  private static final long serialVersionUID = -4994972851611141206L;

  public void destroy()
  {
  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException
  {
    ServletContext obj_Context = servlet.getServletContext();

    DicCache obj_DicCache = DicCache.getInstance();
    obj_Context.setAttribute("g_dic", obj_DicCache);
    System.out.println("EfsFrame 字典缓存加载完成……");

    SpellCache obj_SpellCache = SpellCache.getInstance();
    obj_Context.setAttribute("g_spell", obj_SpellCache);
    System.out.println("EfsFrame 拼音缓存加载完成……");

    MUnitCache obj_MUnitCache = MUnitCache.getInstance();
    obj_Context.setAttribute("g_munit",obj_MUnitCache);
    System.out.println("EfsFrame 管理单位缓存加载完成……");

    UserCache obj_UserCache = UserCache.getInstance();
    obj_Context.setAttribute("g_user",obj_UserCache);
    System.out.println("EfsFrame 用户信息缓存加载完成……");
    
    // 设置字典路径
    String str_DicPath = obj_Context.getRealPath("dic");
    DicCache.setDicPath(str_DicPath);
    
  }
}
