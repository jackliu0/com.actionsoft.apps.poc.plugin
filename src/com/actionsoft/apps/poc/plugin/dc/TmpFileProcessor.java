package com.actionsoft.apps.poc.plugin.dc;

import java.util.Map;

import com.actionsoft.bpms.server.fs.AbstFileProcessor;
import com.actionsoft.bpms.server.fs.DCContext;
import com.actionsoft.bpms.server.fs.FileProcessorListener;
import com.actionsoft.bpms.server.fs.dc.DCMessage;
import com.actionsoft.bpms.util.UtilFile;

public class TmpFileProcessor extends AbstFileProcessor implements FileProcessorListener {

	// 处理上传的临时文件示例
	public void uploadSuccess(Map<String, Object> param) {
		DCContext context = (DCContext) param.get("DCContext");
		if (context.getGroupValue().equals("crm") && context.getFileValue().equals("customer")) {
			UtilFile tmpFile = new UtilFile(context.getFilePath());
			// 模拟处理过程，比如用POI处理Excel文件
			byte[] data = tmpFile.readBytes();
			context.setDCMessage(DCMessage.OK, "This file size : " + data.length + " bytes.");
			// 删除
			tmpFile.delete();
		}
	}

	// 下载生成的临时文件示例
	public void downloadComplete(Map<String, Object> param) {
		DCContext context = (DCContext) param.get("DCContext");
		if (context.getGroupValue().equals("crm") && context.getFileValue().equals("auto-report")) {
			// 删除这个临时文件
			new UtilFile(context.getFilePath()).delete();
		}
	}
}
