package com.actionsoft.apps.poc.plugin.wechat;

import com.actionsoft.bpms.commons.wechat.WechatProcessor;
import com.actionsoft.bpms.commons.wechat.bean.WechatInMessage;
import com.actionsoft.bpms.commons.wechat.bean.WechatOutMessage;
import com.actionsoft.bpms.commons.wechat.bean.WechatOutTextMessage;

public class ProcessorWechatMessage implements WechatProcessor {

	public WechatOutMessage handleMessage(WechatInMessage msg) {
		System.out.println("接收到的信息");
		System.out.println(msg.getEvent());
		System.out.println(msg.getEventKey());
		System.out.println(msg);

		// 回复文本消息
		WechatOutTextMessage txtMsg = new WechatOutTextMessage(msg);
		txtMsg.setContent("Hi，" + msg.getContent());
		return txtMsg;
	}
}
