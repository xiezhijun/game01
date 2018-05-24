package com.xzj.g01.dispatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xzj.g01.dispatch.bo.DispatchContext;
import com.xzj.g01.dispatch.bo.DispatchType;
import com.xzj.g01.dispatch.bo.DispatcherServer;

@Service
public class DispatchLoginService {
	
	@Autowired
	private DispatchContext dispatchContext;
	
	public void login(String type, DispatcherServer dispatchServer) {
		DispatchType dispatchClientType = DispatchType.valueOf(type);
		dispatchServer.setDispatchType(dispatchClientType);
	}
	
}
