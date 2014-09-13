package com.zizibujuan.useradmin.server.servlets.connect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.Avatar;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.zizibujuan.drip.server.util.Gender;
import com.zizibujuan.useradmin.server.model.UserBindInfo;
import com.zizibujuan.useradmin.server.model.constant.OAuthConstants;
import com.zizibujuan.useradmin.server.service.UserBindService;
import com.zizibujuan.useradmin.server.servlets.UserAdminServiceHolder;

/**
 * qq用户登录
 * 
 * @author jzw
 * @since 0.0.1
 */
public class QQUserConnect extends UserConnect {
	
	private static final Logger logger = LoggerFactory.getLogger(QQUserConnect.class);
	private UserBindService userBindService = UserAdminServiceHolder.getDefault().getUserBindService();

	// 如何避免多次显示授权信息？
	@Override
	protected void toLoginPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
        	resp.sendRedirect(new Oauth().getAuthorizeURL(req));
        } catch (QQConnectException e) {
            logger.error("获取qq登录页面失败", e);
        }
	}

	@Override
	protected void login(HttpServletRequest req, HttpServletResponse resp,
			String code) throws IOException {
		boolean needComplete = false;
		try {
			// 从本地获取访问标识与失效时间，如果访问标识有效，则直接使用token访问，否则重新请求token
			AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(req);
			String accessToken = null;
			String openID = null;
			long tokenExpireIn = 0L;
			
			accessToken = accessTokenObj.getAccessToken();
			if(accessToken.equals("")){
				logger.error("没有获取到响应参数");
				return;
			}
		
			tokenExpireIn = accessTokenObj.getExpireIn();
			
			req.getSession().setAttribute("qq_access_token", accessToken);
			req.getSession().setAttribute("qq_token_expirein", tokenExpireIn);
			
			OpenID openIDObj = new OpenID(accessToken);
			openID = openIDObj.getUserOpenID();
			UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
			UserInfoBean qzoneUserInfoBean = qzoneUserInfo.getUserInfo();
			
			if(qzoneUserInfoBean.getRet() != 0){
				logger.error(qzoneUserInfoBean.getMsg());
				return;
			}
			
			Long dripUserId = null;
			// 判断第三方帐号是否与本网站帐号建立关联，如果没有，则先建立关联
			UserBindInfo userBindInfo = userBindService.get(OAuthConstants.QQ, openID);
			
			if(userBindInfo == null){
				userBindInfo = new UserBindInfo();
				userBindInfo.setOpenId(openID);
				userBindInfo.setSiteId(OAuthConstants.QQ);
				com.zizibujuan.useradmin.server.model.UserInfo dripUser = new com.zizibujuan.useradmin.server.model.UserInfo();
				dripUser.setNickName(qzoneUserInfoBean.getNickname());
				dripUser.setAccessToken(accessToken);
				Calendar now = Calendar.getInstance();
				now.add(Calendar.SECOND, (int)tokenExpireIn);
				dripUser.setExpiresTime(now.getTime());
				
				String sex = "";
				String qqGender = qzoneUserInfoBean.getGender();
				if(qqGender.equals("男")){
					sex = Gender.MALE;
				}else if(qqGender.equals("女")){
					sex = Gender.FEMALE;
				}else{
					sex = Gender.UNKNOWN;
				}
				
				dripUser.setSex(sex);
				
				Avatar qqAvatar = qzoneUserInfoBean.getAvatar();
				List<com.zizibujuan.useradmin.server.model.Avatar> avatars = new ArrayList<com.zizibujuan.useradmin.server.model.Avatar>();
				addAvatar(avatars, "figureurl", qqAvatar.getAvatarURL30(), 30, 30);
				addAvatar(avatars, "figureurl_1", qqAvatar.getAvatarURL50(), 50, 50);
				addAvatar(avatars, "figureurl_2", qqAvatar.getAvatarURL100(), 100, 100);
				dripUserId = userService.importUser(dripUser, userBindInfo, avatars);
			}else{
				dripUserId = userBindInfo.getUserId();
			}
			
			needComplete = internLogin(req, resp, dripUserId, qzoneUserInfoBean.getNickname(), accessToken, tokenExpireIn);
			
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		resp.sendRedirect(needComplete ? "/completeUserInfo" : "/files/new" /*跳转到新建文档页面*/);
	}
	
}
