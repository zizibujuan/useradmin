package com.zizibujuan.useradmin.server.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.core.runtime.IPath;

import com.zizibujuan.drip.server.util.servlet.BaseServlet;
import com.zizibujuan.drip.server.util.servlet.RequestUtil;
import com.zizibujuan.drip.server.util.servlet.UserSession;
import com.zizibujuan.useradmin.server.model.UserInfo;
import com.zizibujuan.useradmin.server.service.UserService;

/**
 * 完善用户信息
 * 
 * @author jzw
 * @since 0.0.1
 */
public class CompleteUserInfoServlet extends BaseServlet{

	private static final long serialVersionUID = 5649932710022673279L;
	private UserService userService;
	
	public CompleteUserInfoServlet(){
		userService = UserAdminServiceHolder.getDefault().getUserService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		traceRequest(req);
		IPath path = getPath(req);
		if(path.segmentCount() == 0){
			req.getRequestDispatcher("/useradmin/completeUserInfo.html").forward(req, resp);
			return;
		}
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		traceRequest(req);
		IPath path = getPath(req);
		if(path.segmentCount() == 0){
			long userId = ((UserInfo)UserSession.getUser(req)).getId();
			Map<String, Object> newInfo = RequestUtil.fromJsonObject(req);
			// 校验
			if(newInfo == null || newInfo.isEmpty()){
				
				return;
			}
			String nickName = newInfo.get("nickName").toString();
			String email = newInfo.get("email").toString();
			userService.completeUserInfo(userId, );
			return;
		}
		super.doPost(req, resp);
	}
	
}
