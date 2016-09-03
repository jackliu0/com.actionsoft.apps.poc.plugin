package com.actionsoft.apps.poc.plugin;

import com.actionsoft.apps.poc.plugin.skins.MySkins;
import com.actionsoft.apps.poc.plugin.web.SampleWeb;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.server.bind.annotation.Controller;
import com.actionsoft.bpms.server.bind.annotation.Mapping;

import net.sf.json.JSONObject;

@Controller
public class AppController {

	@Mapping("com.actionsoft.apps.poc.plugin_mobileExtend")
	public String mobileExtend(UserContext me, String action, String error) {
		boolean iserror = Boolean.valueOf(error);
		JSONObject json = new JSONObject();
		if (iserror) {
			json.put("result", "error");
			json.put("msg", "未知错误，请联系系统管理员");
			return json.toString();
		}

		if (action.equals("openUrl")) {
			json.put("result", "ok");
			json.put("msg", "");
			json.put("action", "openUrl");
			json.put("url", "http://www.baidu.com");
		} else if (action.equals("refreshTodoTask")) {
			json.put("result", "ok");
			json.put("msg", "");
			json.put("action", "refreshTodoTask");
		}

		return json.toString();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_sample1")
	public String dcSample1(UserContext me) {
		return new SampleWeb(me).getDCSample1Home();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_sample2")
	public String dcSample2(UserContext me) {
		return new SampleWeb(me).getDCSample2Home();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_sample3")
	public String dcSample3(UserContext me) {
		return new SampleWeb(me).getDCSample3Home();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_sample4")
	public String dcSample4(UserContext me) {
		return new SampleWeb(me).getDCSample4Home();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_ac_sample")
	public String acSample(UserContext me) {
		return new SampleWeb(me).getACSampleHome();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_ac_check")
	public String checkAC(UserContext me, int accessMode) {
		return new SampleWeb(me).checkAC(accessMode);
	}

	@Mapping("com.actionsoft.apps.poc.plugin_cache_sample")
	public String cacheSample(UserContext me) {
		return new SampleWeb(me).getCacheSampleHome();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_cache_sample1")
	public String cacheSample1(UserContext me) {
		return new SampleWeb(me).getCacheSampleHome1();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_cache_sample2")
	public String cacheSample2(UserContext me) {
		return new SampleWeb(me).getCacheSampleHome2();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_cache_sample3")
	public String cacheSample3(UserContext me) {
		return new SampleWeb(me).getCacheSampleHome3();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_fs_sample")
	public String fsSample(UserContext me) {
		return new SampleWeb(me).getFSSampleHome();
	}

	@Mapping("com.actionsoft.apps.poc.plugin_fs_sample1")
	public String fsSample(UserContext me, String myContent, String myCustomerName, String myCustomerId) {
		return new SampleWeb(me).createFSIndex(myContent, myCustomerName, myCustomerId);
	}

	@Mapping("com.actionsoft.apps.poc.plugin_fs_sample2")
	public String searchFSSample(UserContext me, String searchText) {
		return new SampleWeb(me).searchFS(searchText);
	}

	@Mapping("com.actionsoft.apps.poc.plugin_fs_sample3")
	public String advanceSearchFSSample(UserContext me, String searchText) {
		return new SampleWeb(me).advanceSearchFS(searchText);
	}

	@Mapping("com.actionsoft.apps.poc.plugin_portal_logout")
	public String portalLogout(UserContext me) {
		return new MySkins().getLogoutPage(me);
	}
}
