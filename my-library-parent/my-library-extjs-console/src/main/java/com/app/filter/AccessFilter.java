package com.app.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.usercenter.domain.SysManager;
import com.sys.core.CurrentUserSession;

/**
 * 访问过滤器<br>
 * 负责访问时的权限验证,对通过浏览器输入地址或外部调用等手段的非法访问进行无条件跳转
 * 
 * @author YangJunping
 * @date 2012-4-13下午1:25:21
 */
public class AccessFilter implements Filter {
    private ServletContext applicationContext = null;

    // @Resource
    // private SysRoleService sysRoleService;
    //
    // @Resource
    // private SysCatalogService sysCatalogService;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;
	String referer = httpRequest.getHeader("referer");
	if (referer != null && referer.startsWith("http://" + request.getServerName())) { // 对正常访问的权限校验
	    CurrentUserSession userSession = (CurrentUserSession) CurrentUserSession.findUserSession(httpRequest);
	    if (userSession == null) {
		PrintWriter out = httpResponse.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open('" + httpRequest.getContextPath() + "/console/index.htm" + "','_top')");
		out.println("</script>");
		out.println("</html>");
		return;
	    }
	    SysManager sysManager = userSession.getCurrentUser();
	    if (sysManager == null) {
		PrintWriter out = httpResponse.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open('" + httpRequest.getContextPath() + "/console/index.htm" + "','_top')");
		out.println("</script>");
		out.println("</html>");
		return;
	    }
	    if (!userSession.isHasRefleshRoleFunction()) {
		// userSession.refreshRoleFunctions(sysRoleService,
		// sysCatalogService);
	    }
	} else { // 对非法访问的跳转,不改变地址栏
	    httpRequest.getRequestDispatcher("/illegal.htm").forward(httpRequest, httpResponse);
	    return;
	}
	chain.doFilter(request, response);
    }

    public void destroy() {
	applicationContext = null;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}