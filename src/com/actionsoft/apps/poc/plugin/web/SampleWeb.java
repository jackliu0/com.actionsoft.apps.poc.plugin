package com.actionsoft.apps.poc.plugin.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.actionsoft.apps.poc.plugin.cache.BookCache;
import com.actionsoft.apps.poc.plugin.cache.BookModel;
import com.actionsoft.apps.resource.plugin.profile.DCPluginProfile;
import com.actionsoft.bpms.commons.addons.AddOnsInterface;
import com.actionsoft.bpms.commons.htmlframework.HtmlPageTemplate;
import com.actionsoft.bpms.commons.mvc.view.ActionWeb;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.server.fs.DCContext;
import com.actionsoft.bpms.util.UUIDGener;
import com.actionsoft.exception.AWSException;
import com.actionsoft.sdk.local.SDK;

public class SampleWeb extends ActionWeb implements AddOnsInterface {

	public SampleWeb(UserContext userContext) {
		super(userContext);
		// TODO Auto-generated constructor stub
	}

	public SampleWeb() {
		// TODO Auto-generated constructor stub
	}

	// add-ons home
	public String mainPage(UserContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b>Hi, This Is A ADD-ONS Application! System.getProperties()</b><hr/>");
		Set keys = System.getProperties().keySet();
		for (Object key : keys) {
			sb.append(key.toString()).append("&nbsp;-&nbsp;").append(System.getProperties().getProperty((String) key)).append("<br/>");
		}
		return sb.toString();
	}

	public String getDCSample1Home() {
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "dc-sample1.htm", macroLibraries);
	}

	public String getDCSample2Home() {
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "dc-sample2.htm", macroLibraries);
	}

	public String getDCSample3Home() {
		long time = System.currentTimeMillis();
		// 生成一个临时文件，供用户下载
		String appId = "com.actionsoft.apps.poc.plugin";
		String repositoryName = "tmp";
		String groupValue = "crm";
		String fileValue = "auto-report";
		String fileName = time + ".txt";
		DCPluginProfile dcProfile = SDK.getDCAPI().getDCProfile(appId, repositoryName);
		DCContext dcContext = new DCContext(getContext(), dcProfile, appId, groupValue, fileValue, fileName);
		File dirFile = new File(dcContext.getPath());
		File tmpFile = new File(dcContext.getFilePath());
		// 模拟处理，返回一段文字
		String data = "Hello AWS PaaS! currentTimeMillis:" + time;
		InputStream in = null;
		try {
			if (tmpFile.exists()) {
				tmpFile.delete();
				tmpFile.createNewFile();
			} else {
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				tmpFile.createNewFile();
			}
			in = new ByteArrayInputStream(data.getBytes());
			boolean isWrited = SDK.getDCAPI().write(in, dcContext);
			if (!isWrited) {
				throw new AWSException("Write Error!");
			}
		} catch (Exception e) {
			throw new AWSException(e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
			}
		}
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("sid", getContext().getSessionId());
		macroLibraries.put("tip", time);
		macroLibraries.put("url", dcContext.getDownloadURL());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "dc-sample3.htm", macroLibraries);
	}

	public String getDCSample4Home() {
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "dc-sample4.htm", macroLibraries);
	}

	public String getACSampleHome() {
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "ac-sample.htm", macroLibraries);
	}

	public String checkAC(int accessMode) {
		boolean isAccess = SDK.getPermAPI().havingACPermission(getContext().getUID(), "poc.plugin.myACTest", "123", accessMode);
		return ResponseObject.newOkResponse(Boolean.toString(isAccess)).toString();
	}

	public String getCacheSampleHome() {
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "cache-sample.htm", macroLibraries);
	}

	public String getCacheSampleHome1() {
		StringBuilder data = new StringBuilder();
		data.append("<b>BookCache</b><hr>\n");
		data.append("<b>.size()</b>=" + BookCache.getCache().size()).append("<br/>");
		long time = System.currentTimeMillis();
		BookModel book = new BookModel(SDK.getPlatformAPI().getAWSServer().getInstanceName() + "-" + Long.toString(time), "书(" + time + ")", 1, 1d, 2015);
		data.append("<b>.put()</b><br/>");
		BookCache.getCache().put(book.getId(), book);
		data.append("<b>.size()</b>=" + BookCache.getCache().size()).append("<br/>");
		Iterator<BookModel> iterator = BookCache.getCache().iterator();
		data.append("<b>.iterator()</b><br/>");
		while (iterator.hasNext()) {
			BookModel bookModel = iterator.next();
			data.append(bookModel.toJson()).append("<br/>");
		}
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("data", data.toString());
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "cache-sample-dialog.htm", macroLibraries);
	}

	public String getCacheSampleHome2() {
		StringBuilder data = new StringBuilder();
		data.append("<b>BookCache ==> BookIndex1/BookIndex2</b><hr>\n");
		data.append("<b>BookCache.getByName(\"你总会路过这个世界的美好\")</b><br/>" + BookCache.getByName("你总会路过这个世界的美好").toJson()).append("<br/>");
		Iterator<BookModel> iterator = BookCache.listByYear(2014);
		data.append("<b>BookCache.getByYear(2014)</b><br/>");
		while (iterator.hasNext()) {
			BookModel bookModel = iterator.next();
			data.append(bookModel.toJson()).append("<br/>");
		}
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("data", data.toString());
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "cache-sample-dialog.htm", macroLibraries);
	}

	public String getCacheSampleHome3() {
		StringBuilder data = new StringBuilder();
		data.append("<b>BookCache ==> SORT_BOOK_NAME/SORT_BOOK_QUANTITY</b><hr>\n");
		Iterator<BookModel> iterator = BookCache.listSortByName(2014);
		data.append("<b>SORT_BOOK_NAME/BookCache.listSortByName(2014)</b><br/>");
		while (iterator.hasNext()) {
			BookModel bookModel = iterator.next();
			data.append(bookModel.getName()).append(" ==> ").append(bookModel.toJson()).append("<br/>");
		}
		iterator = BookCache.listSortByQuantity();
		data.append("<b>SORT_BOOK_QUANTITY/BookCache.listSortByQuantity()</b><br/>");
		while (iterator.hasNext()) {
			BookModel bookModel = iterator.next();
			data.append(bookModel.getQuantity()).append(" ==> ").append(bookModel.toJson()).append("<br/>");
		}
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("data", data.toString());
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "cache-sample-dialog.htm", macroLibraries);
	}

	public String getFSSampleHome() {
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("sid", getContext().getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "fs-sample.htm", macroLibraries);
	}

	public String createFSIndex(String myContent, String myCustomerName, String myCustomerId) {
		// 调用方
		String sourceAppId = "com.actionsoft.apps.poc.plugin";
		// 服务地址
		String aslp = "aslp://com.actionsoft.apps.fullsearch/createIndexByContent";
		Map<String, Object> params = new HashMap<String, Object>();
		// 给定必填参数
		params.put("repositoryName", "db1");
		params.put("documentId", UUIDGener.getUUID());
		params.put("content", myContent);
		JSONArray extendFields = new JSONArray();
		// 扩展客户名称字段
		JSONObject customerNameField = new JSONObject();
		customerNameField.put("fieldName", "customerName");
		customerNameField.put("fieldType", "text");
		customerNameField.put("fieldContent", myCustomerName);
		customerNameField.put("fieldBoost", 1);
		customerNameField.put("fieldStore", true);// 可以返回到查询结果
		// 扩展客户ID字段
		JSONObject customerIdField = new JSONObject();
		customerIdField.put("fieldName", "customerId");
		customerIdField.put("fieldType", "text");
		customerIdField.put("fieldContent", myCustomerId);
		customerIdField.put("fieldBoost", 1);
		customerIdField.put("fieldStore", true);// 可以返回到查询结果

		extendFields.add(customerNameField);
		extendFields.add(customerIdField);
		params.put("otherFields", extendFields.toString());
		ResponseObject ro = SDK.getAppAPI().callASLP(SDK.getAppAPI().getAppContext(sourceAppId), aslp, params);
		return ro.toString();
	}

	public String searchFS(String searchText) {
		// 调用方
		String sourceAppId = "com.actionsoft.apps.poc.plugin";
		// 服务地址
		String aslp = "aslp://com.actionsoft.apps.fullsearch/search";
		Map<String, Object> params = new HashMap<String, Object>();
		// 给定必填参数
		params.put("repositoryName", "db1");
		params.put("searchText", searchText);
		ResponseObject ro = SDK.getAppAPI().callASLP(SDK.getAppAPI().getAppContext(sourceAppId), aslp, params);
		return ro.toString();
	}

	public String advanceSearchFS(String searchText) {
		// 调用方
		String sourceAppId = "com.actionsoft.apps.poc.plugin";
		// 服务地址
		String aslp = "aslp://com.actionsoft.apps.fullsearch/advancedSearch";
		Map<String, Object> params = new HashMap<String, Object>();
		// 给定必填参数
		params.put("repositoryName", "db1");
		params.put("searchText", searchText);
		ResponseObject ro = SDK.getAppAPI().callASLP(SDK.getAppAPI().getAppContext(sourceAppId), aslp, params);
		return ro.toString();
	}

}
