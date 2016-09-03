package com.actionsoft.apps.poc.plugin;

import com.actionsoft.apps.listener.AppListener;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.bpms.server.Quota;
import com.actionsoft.exception.AWSForbiddenException;

public class StartEvent implements AppListener {

	public boolean before(AppContext context) {
		if (Quota.isPRD()) {
			throw new AWSForbiddenException("不允许在PRD环境中启动该应用");
		}
		return true;
	}

	public void after(AppContext context) {
	}

}
