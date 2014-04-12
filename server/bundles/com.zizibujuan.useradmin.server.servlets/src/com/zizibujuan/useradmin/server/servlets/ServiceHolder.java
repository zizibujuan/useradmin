package com.zizibujuan.useradmin.server.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zizibujuan.cm.server.service.ApplicationPropertyService;
import com.zizibujuan.useradmin.server.service.UserBindService;
import com.zizibujuan.useradmin.server.service.UserRelationService;
import com.zizibujuan.useradmin.server.service.UserService;



/**
 * 服务容器，所有的服务实例都注入在这里，在servlet中需要引用服务时，统一通过该类调用。
 * FIXME：这是一个折中的方式，本想通过ds的方式为所有servlet添加服务，
 * 可是通过“org.eclipse.equinox.http.registry.servlets”
 * 扩展点方式调用的servlet并不是ds中声明的组件。所以总是报NullPointerException。 
 * 等找到解决方法之后，把这个替换掉。
 * 
 * @author 金正伟
 * @since 0.0.1
 */
public class ServiceHolder {
	private static final Logger logger = LoggerFactory
			.getLogger(ServiceHolder.class);

	private static ServiceHolder singleton;

	public static ServiceHolder getDefault() {
		return singleton;
	}

	public void activate() {
		singleton = this;
	}

	public void deactivate() {
		singleton = null;
	}
	
	

	
	private UserService userService;

	public void setUserService(UserService userService) {
		logger.info("注入UserService");
		this.userService = userService;
	}

	public void unsetUserService(UserService userService) {
		logger.info("注销UserService");
		if (this.userService == userService) {
			this.userService = null;
		}
	}
	public UserService getUserService() {
		return this.userService;
	}
	

	// 经测试，这里注入的applicationPropertyService与Activator中的applicationPropertyService是同一个对象实例
	private ApplicationPropertyService applicationPropertyService;
	public ApplicationPropertyService getApplicationPropertyService() {
		return applicationPropertyService;
	}
	public void setApplicationPropertyService(ApplicationPropertyService applicationPropertyService) {
		logger.info("注入ApplicationPropertyService");
		logger.info("applicationPropertyService In ServiceHolder:"+applicationPropertyService);
		this.applicationPropertyService = applicationPropertyService;
	}

	public void unsetApplicationPropertyService(ApplicationPropertyService applicationPropertyService) {
		logger.info("注销ApplicationPropertyService");
		if (this.applicationPropertyService == applicationPropertyService) {
			this.applicationPropertyService = null;
		}
	}

	private UserBindService userBindService;

	public void setUserBindService(UserBindService userBindService) {
		logger.info("注入userBindService");
		this.userBindService = userBindService;
	}

	public void unsetUserBindService(UserBindService userBindService) {
		logger.info("注销userBindService");
		if (this.userBindService == userBindService) {
			this.userBindService = null;
		}
	}
	public UserBindService getUserBindService() {
		return userBindService;
	}

	private UserRelationService userRelationService;
	public void setUserRelationService(UserRelationService userRelationService) {
		logger.info("注入userRelationService");
		this.userRelationService = userRelationService;
	}

	public void unsetUserRelationService(UserRelationService userRelationService) {
		logger.info("注销userRelationService");
		if (this.userRelationService == userRelationService) {
			this.userRelationService = null;
		}
	}
	public UserRelationService getUserRelationService() {
		return userRelationService;
	}
}
