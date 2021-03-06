package com.zizibujuan.useradmin.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.core.runtime.IPath;

import com.zizibujuan.drip.server.util.constant.CookieConstants;
import com.zizibujuan.drip.server.util.servlet.BaseServlet;
import com.zizibujuan.drip.server.util.servlet.CookieUtil;

/**
 * 用户注销，删除会话
 * @author jinzw
 * @since 0.0.1
 */
public class LogoutServlet extends BaseServlet {

	private static final long serialVersionUID = -4415570541175504883L;

	/**
	 * 注销会话，通过ajax方式注销时使用。在客户端写跳转路径
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		traceRequest(req);
		IPath path = getPath(req);
		if(path.segmentCount() == 0){
			req.getSession().invalidate();
			CookieUtil.set(resp, CookieConstants.LOGGED_IN, "0", null, -1);
			CookieUtil.remove(req, resp, CookieConstants.ZZBJ_USER_TOKEN);
			// 注销之后，跳转到首页。
			// FIXME：要是可以停留在当时的页面，但是显示适合该页面的不同内容，会不会更好些呢？
			resp.setStatus(HttpServletResponse.SC_FOUND);
			return;
		}
		super.doPost(req, resp);
	}

	/**
	 * 注销会话，使用超链接的方式注销时使用。在这里写跳转路径。
	 * 因为现在的框架中确定，跳转请求只能请求资源，而注销并没有请求资源，是一个操作，所以不应该走get方法
	 */
	@Deprecated
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		traceRequest(req);
		IPath path = getPath(req);
		if(path.segmentCount() == 0){
			req.getSession().invalidate();
			resp.sendRedirect("/");
			return;
		}
		super.doGet(req, resp);
	}
	
	

}
