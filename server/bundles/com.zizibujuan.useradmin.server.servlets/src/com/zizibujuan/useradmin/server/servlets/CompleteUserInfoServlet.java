package com.zizibujuan.useradmin.server.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;
import org.eclipse.core.runtime.IPath;

import com.zizibujuan.drip.server.util.servlet.BaseServlet;
import com.zizibujuan.drip.server.util.servlet.RequestUtil;
import com.zizibujuan.drip.server.util.servlet.ResponseUtil;
import com.zizibujuan.drip.server.util.servlet.UserSession;
import com.zizibujuan.drip.server.util.servlet.validate.Validator;
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
			Validator validator = new Validator();
			this.validate(validator, newInfo);
			if(validator.hasFieldErrors()){
				ResponseUtil.toJSON(req, resp, validator.getFieldErrors(), HttpServletResponse.SC_PRECONDITION_FAILED);
				return;
			}
			String loginName = newInfo.get("loginName").toString();
			String nickName = newInfo.get("nickName").toString();
			String email = newInfo.get("email").toString();
			userService.completeUserInfo(userId, loginName, nickName, email);
			return;
		}
		super.doPost(req, resp);
	}
	
	private void validate(Validator validator, Map<String, Object> newUserInfo){
		// TODO: 校验邮箱是否被使用
		// TODO: 校验登录名是否被使用
		// TODO: 校验登录名是否只由英文字母与数字组成
		// 校验
		if(newUserInfo == null || newUserInfo.isEmpty()){
			validator.addFieldError("nickName", "用户名不能为空");
			validator.addFieldError("email", "邮箱不能为空");
			return;
		}
		Object loginName = newUserInfo.get("loginName");
		if(loginName == null || loginName.toString().trim().isEmpty()){
			validator.addFieldError("loginName", "用户名不能为空");
		}
		
		Object nickName = newUserInfo.get("nickName");
		if(nickName == null || nickName.toString().trim().isEmpty()){
			validator.addFieldError("nickName", "昵称不能为空");
		}
		
		Object email = newUserInfo.get("email");
		if(email == null || email.toString().trim().isEmpty()){
			validator.addFieldError("email", "邮箱不能为空");
		}else if(!EmailValidator.getInstance().isValid(email.toString().trim())){
			validator.addFieldError("email", "邮箱无效");
		}
		
	}
	
}
