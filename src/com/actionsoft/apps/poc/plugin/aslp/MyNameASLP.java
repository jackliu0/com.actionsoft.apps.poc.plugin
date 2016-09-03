package com.actionsoft.apps.poc.plugin.aslp;

import java.util.Map;

import com.actionsoft.apps.resource.interop.aslp.ASLP;
import com.actionsoft.apps.resource.interop.aslp.Meta;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;

public class MyNameASLP implements ASLP {

	public MyNameASLP() {
	}

	@Meta(parameter = { "name:'yourName',required:true,desc:'你的名字'" })
	public ResponseObject call(Map<String, Object> params) {
		ResponseObject ro = ResponseObject.newOkResponse("Hi " + params.get("yourName") + " , My name is AWS!");
		return ro;
	}

}
