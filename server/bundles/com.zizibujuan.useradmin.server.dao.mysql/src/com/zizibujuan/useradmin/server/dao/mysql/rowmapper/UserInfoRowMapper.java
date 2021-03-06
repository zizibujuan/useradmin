package com.zizibujuan.useradmin.server.dao.mysql.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zizibujuan.drip.server.util.dao.RowMapper;
import com.zizibujuan.useradmin.server.model.UserInfo;

/**
 * 将数据库表中的列映射到UserInfo对象上
 * 
 * @author jzw
 * @since 0.0.1
 */
public class UserInfoRowMapper implements RowMapper<UserInfo> {

	@Override
	public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(rs.getLong(1));
		userInfo.setLoginName(rs.getString(2));
		userInfo.setEmail(rs.getString(3));
		userInfo.setSex(rs.getString(4));
		userInfo.setIntroduce(rs.getString(5));
		userInfo.setConfirmKey(rs.getString(6));
		userInfo.setActive(rs.getBoolean(7));
		return userInfo;
	}

}
