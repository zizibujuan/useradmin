package com.zizibujuan.useradmin.server.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用户统计属性 数据访问接口
 * @author jzw
 * @since 0.0.1
 */
public interface UserAttributesDao {

	/**
	 * 更新登录状态。<br/>
	 * 支持的属性有：1、更新用户最近登录时间；2、更新用户登录次数。
	 * @param userId 本网站用户标识
	 * @param localAccessToken 本网站生成的访问标识
	 * @param oauthAccessToken 标识用户登录信息
	 * @param oauthTokenExpireIn token过期时长
	 */
	void updateLoginState(Long userId, String localAccessToken, String oauthAccessToken, long oauthTokenExpireIn);
	
	/**
	 * 在创建一个用户时，一次性初始化这些属性。但是这样做的一个问题时，后面新增的属性就没有办法插入了，
	 * 没加一个属性，就调用一个sql语句，批量更新一下。
	 * @param con 数据库链接
	 * @param userId 本网站用户标识
	 * @throws SQLException 
	 */
	void initUserState(Connection con, Long userId) throws SQLException;

}
