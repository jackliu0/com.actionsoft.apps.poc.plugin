package com.actionsoft.apps.poc.plugin.process;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ProcessPubicListener;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;

public class MyNotification extends ProcessPubicListener {

	@Override
	public void call(String eventName, TaskInstance taskInst, ProcessExecutionContext ctx) {
		System.out.println("我监听到[" + eventName + "]" + taskInst);
	}

}
