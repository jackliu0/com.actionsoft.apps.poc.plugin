package com.actionsoft.apps.poc.plugin.dc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import com.actionsoft.bpms.server.fs.AbstFileProcessor;
import com.actionsoft.bpms.server.fs.DCContext;
import com.actionsoft.bpms.server.fs.FileProcessorListener;
import com.actionsoft.bpms.server.fs.dc.DCMessage;
import com.actionsoft.bpms.util.UtilIO;

public class StreamFileProcessor extends AbstFileProcessor implements FileProcessorListener {

	public boolean uploadReady(Map<String, Object> param) {
		DCContext context = (DCContext) param.get("DCContext");
		InputStream in = (InputStream) param.get("data");
		// 如果你的应用需要多类自定义处理的场景，可通过groupValue/fileValue变量做识别
		if (context.getGroupValue().equals("hr") && context.getFileValue().equals("action1")) {
			try {
				// 模拟处理，读来自用户上传的文件，什么都不做
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				UtilIO.copy(in, bytes);
				context.setDCMessage(DCMessage.OK, "fileName:" + context.getFileName() + ",byte size:" + bytes.size());
				return true;
			} catch (Exception e) {
				context.setDCMessage(DCMessage.ERROR, e.toString());
				return false;
			}
		} else {
			context.setDCMessage(DCMessage.WARNING, "处理器无法识别的类型。groupValue:" + context.getGroupValue() + ",fileValue:" + context.getFileValue());
			return false;
		}
	}

	public InputStream downloadContent(Map<String, Object> param) throws Exception {
		DCContext context = (DCContext) param.get("DCContext");
		// 如果你的应用需要多类自定义处理的场景，可通过groupValue/fileValue变量做识别
		if (context.getGroupValue().equals("hr") && context.getFileValue().equals("action1")) {
			// 模拟读文件，将流返回给客户端
			ByteArrayInputStream bytes = new ByteArrayInputStream("hi, AWS PaaS!".getBytes());
			return bytes;
		} else {
			context.setDCMessage(DCMessage.WARNING, "处理器无法识别的类型。groupValue:" + context.getGroupValue() + ",fileValue:" + context.getFileValue());
			return null;
		}
	}
}
