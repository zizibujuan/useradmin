package com.zizibujuan.useradmin.server.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.zizibujuan.useradmin.server.model.UserBindInfo;

/**
 * 用户帐号绑定 数据访问接口
 * 
 * @author jzw
 * @since 0.0.1
 */
public interface UserBindDao {

	/**
	 * 从帐号关联表中获取关联信息
	 * 
	 * @param siteId 授权站点标识 {@link OAuthConstants}
	 * @param openId 第三方网站的用户标识，因为第三方网站用户标识类型不确定，
	 * 所以这里一律使用字符串类型。
	 * @return 本网站用户与第三方网站用户映射信息
	 */
	UserBindInfo get(int siteId, String openId);
	
	/**
	 * 将第三方帐号与本网站的用户进行绑定
	 * 
	 * @param conn 数据库链接
	 * @param userBindInfo 关联信息
	 * @return 返回映射记录的标识，该标识不是第三方网站的用户标识，而是与本网站用户关绑定后生成的一个关系标识。
	 * @throws SQLException 
	 */
	Long bind(Connection conn, UserBindInfo userBindInfo) throws SQLException;
	
	
	
	
	
	
	/**
	 * 获取本地用户引用，一个本地用户会关联多个第三方网站或者本地网站用户，但是只有一个标明了引用该用户的信息，作为本地用户的基本信息。
	 * @param localUserId 本地用户标识
	 * @return 用户关联信息
	 * <pre>
	 * map结构：
	 * 		localUserId：本地用户标识
	 * 		connectUserId：本网站为第三方网站用户生成的代理主键
	 * </pre>
	 */
	Map<String, Object> getRefUserMapperInfo(Long localUserId);
	
	/**
	 * 获取引用的用户标识，这个用户标识是本网站为第三方网站用户生成的全局用户标识。
	 * 注意，这里的引用是指使用第三方网站用户的基本信息
	 * @param localUserId 本网站用户标识
	 * @return 引用的为第三方网站用户生成的全局用户标识
	 */
	Long getRefUserId(Long localUserId);
	
	/**
	 * 获取映射用户标识对应的本地用户标识
	 * @param connectUserId 本网站为第三方网站用户生成的全局用户标识
	 * @return 本地用户标识
	 */
	Long getLocalUserId(Long connectUserId);

}
