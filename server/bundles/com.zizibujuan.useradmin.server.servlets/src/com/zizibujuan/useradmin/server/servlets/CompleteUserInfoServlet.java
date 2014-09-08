package com.zizibujuan.useradmin.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.core.runtime.IPath;

import com.zizibujuan.drip.server.util.servlet.BaseServlet;

public class CompleteUserInfoServlet extends BaseServlet{

	private static final long serialVersionUID = 5649932710022673279L;

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
	
	
}
