package com.actionsoft.apps.poc.plugin.skins;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.actionsoft.bpms.commons.htmlframework.HtmlPageTemplate;
import com.actionsoft.bpms.commons.portal.skins.AbstPortalSkins;
import com.actionsoft.bpms.commons.portal.skins.PortalSkinsInterface;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.util.UtilString;
import com.actionsoft.exception.AWSException;
import com.actionsoft.sdk.local.SDK;
import com.actionsoft.sdk.local.api.PortalAPI;

public class MySkins extends AbstPortalSkins implements PortalSkinsInterface {

	/**
	 * 登录成功后首页面
	 */
	public String getHomePage(UserContext me) {
		StringBuilder sb = new StringBuilder();
		// 取当前用户可访问的全部功能。实际场景建议用getNavList逐级读取
		JSONArray navTree = SDK.getPortalAPI().getNavTree(me);
		for (int i = 0; i < navTree.size(); i++) {
			JSONObject system = navTree.getJSONObject(i);
			String systemName = system.getString("name");
			String systemUrl = system.getString("url");
			// 目标。mainFrame关键字表示打开到你指定的门户工作区，_blank表示浏览器新窗口
			String systemTarget = system.getString("target");
			if (!UtilString.isEmpty(systemUrl) && !systemUrl.equals("/")) {
				systemName = "*" + systemName;
			} else {
				systemUrl = "";
			}
			sb.append("<option value=\"").append(systemUrl).append("\">").append(systemName).append("</option>\n");
			// 该子系统的所有目录
			JSONArray directories = system.getJSONArray("directory");
			for (int ii = 0; ii < directories.size(); ii++) {
				JSONObject directory = directories.getJSONObject(ii);
				String directoryName = directory.getString("name");
				String directoryUrl = directory.getString("url");
				// 目标。mainFrame关键字表示打开到你指定的门户工作区，_blank表示浏览器新窗口
				String directoryTarget = directory.getString("target");
				if (!UtilString.isEmpty(directoryUrl) && !directoryUrl.equals("/")) {
					directoryName += "*";
				} else {
					directoryUrl = "";
				}
				sb.append("<option value=\"").append(directoryUrl).append("\">").append(systemName).append("/").append(directoryName).append("</option>\n");
				// 该目录的所有功能
				JSONArray functions = directory.getJSONArray("function");
				for (int iii = 0; iii < functions.size(); iii++) {
					JSONObject function = functions.getJSONObject(iii);
					String functionName = function.getString("name");
					String functionUrl = function.getString("url");
					// 目标。mainFrame关键字表示打开到你指定的门户工作区，_blank表示浏览器新窗口
					String functionTarget = function.getString("target");
					if (!UtilString.isEmpty(functionUrl) && !functionUrl.equals("/")) {
						functionName += "*";
					} else {
						functionUrl = "";
					}
					sb.append("<option value=\"").append(functionUrl).append("\">").append(systemName).append("/").append(directoryName).append("/").append(functionName).append("</option>\n");
				}
			}
		}
		// user info
		String userInfo = me.getUserName();
		// template merge
		Map<String, Object> macroLibraries = new LinkedHashMap<String, Object>();
		macroLibraries.put("userInfo", userInfo);
		macroLibraries.put("nav-list", sb.toString());
		macroLibraries.put("sid", me.getSessionId());
		return HtmlPageTemplate.merge("com.actionsoft.apps.poc.plugin", "portal-index.htm", macroLibraries);
	}

	/**
	 * 退出提示页面
	 */
	public String getLogoutPage(UserContext me) {
		PortalAPI portalApi = SDK.getPortalAPI();
		// 关闭session
		boolean isClosed = portalApi.closeSession(me.getSessionId());
		if (!isClosed) {
			throw new AWSException("Session关闭异常");
		}
		// 调转到你的登出页面
		return "logout sucess!";
	}
}
