package com.zizibujuan.useradmin.server.configurator;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zizibujuan.drip.server.util.constant.CookieConstants;
import com.zizibujuan.drip.server.util.servlet.CookieUtil;
import com.zizibujuan.drip.server.util.servlet.UserSession;
import com.zizibujuan.useradmin.server.model.UserInfo;
import com.zizibujuan.useradmin.server.service.UserService;
import com.zizibujuan.useradmin.server.servlets.UserAdminServiceHolder;

/**
 * 用户自动登录
 * 
 * @author jzw
 * @since 0.0.1
 */
public class AutoLoginFilter implements Filter{

	private static final Logger logger = LoggerFactory.getLogger(AutoLoginFilter.class);
	
	private UserService userService;
	
	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		
		if(!UserSession.isLogged(httpRequest)){
			tryAutoLogin(httpRequest);
		}
		filterChain.doFilter(servletRequest, servletResponse);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		userService = UserAdminServiceHolder.getDefault().getUserService();
	}
	
	private void tryAutoLogin(final HttpServletRequest httpRequest) {
		// 如果cookie中有logged_in和token，则执行登录操作
		Cookie loggedInCookie = CookieUtil.get(httpRequest, CookieConstants.LOGGED_IN);
		// TODO:用户每次登录，都自动分配一个token
		if(loggedInCookie != null && ("1").equals(loggedInCookie.getValue())){
			Cookie tokenCookie = CookieUtil.get(httpRequest, CookieConstants.ZZBJ_USER_TOKEN);
			if(tokenCookie != null){
				String localAccessToken = tokenCookie.getValue();
				// 自动登录
				UserInfo userInfo = userService.getByToken(localAccessToken);
				if(userInfo == null){
					logger.error("自动登录失败，这个时候页面上会显示LoggedInHeader，出现不一致的情况");
				}else{
					UserSession.setUser(httpRequest, userInfo);
				}
			}
		}
	}


}
