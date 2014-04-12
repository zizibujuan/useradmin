package com.zizibujuan.useradmin.server.model.constant;

/**
 * 用户相关属性名
 * 
 * @author jinzw
 * @since 0.0.1
 */
public class ApplicationPropertyKey {
	/**
	 * 最近登录时间
	 */
	public static final String LOGIN_LAST_LOGIN_MILLIS = "login.lastLoginMillis";
	
	/**
	 * 用户登录次数
	 */
	public static final String LOGIN_COUNT = "login.count";
	
	/**
	 * 用户连续输错密码的次数
	 */
	public static final String INVALID_PASSWORD_ATTEMPTS = "invalidPasswordAttempts";
	
	/**
	 * 记录临时用户标识的当前值
	 */
	public static final String DRIP_COOKIE_MAX_USER_ID = "drip.cookie.max.userId";

}
