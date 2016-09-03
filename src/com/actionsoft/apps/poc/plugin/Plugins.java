package com.actionsoft.apps.poc.plugin;

import java.util.ArrayList;
import java.util.List;

import com.actionsoft.apps.listener.PluginListener;
import com.actionsoft.apps.poc.plugin.aslp.MyNameASLP;
import com.actionsoft.apps.poc.plugin.at.MyLenExpression;
import com.actionsoft.apps.poc.plugin.cache.BookCache;
import com.actionsoft.apps.poc.plugin.dc.MyFileProcessor;
import com.actionsoft.apps.poc.plugin.dc.StreamFileProcessor;
import com.actionsoft.apps.poc.plugin.dc.TmpFileProcessor;
import com.actionsoft.apps.poc.plugin.process.MyNotification;
import com.actionsoft.apps.poc.plugin.skins.MySkins;
import com.actionsoft.apps.poc.plugin.web.SampleWeb;
import com.actionsoft.apps.poc.plugin.wechat.ProcessorWechatMessage;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.apps.resource.plugin.profile.ACPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.ASLPPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.AWSPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.AddOnsPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.AtFormulaPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.CachePluginProfile;
import com.actionsoft.apps.resource.plugin.profile.DCPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.FullSearchPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.HttpASLP;
import com.actionsoft.apps.resource.plugin.profile.ProcessPublicEventPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.SkinsPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.WechatPluginProfile;
import com.actionsoft.bpms.commons.security.ac.model.ACAccessMode;

/**
 * 注册插件
 */
public class Plugins implements PluginListener {
	public Plugins() {
	}

	public List<AWSPluginProfile> register(AppContext context) {
		// 存放本应用的全部插件扩展点描述
		List<AWSPluginProfile> list = new ArrayList<AWSPluginProfile>();
		// 注册AT公式
		list.add(new AtFormulaPluginProfile("字符串", "@myLen(*str)", MyLenExpression.class.getName(), "我的字符串长度", "返回字符串长度"));
		// 注册DC
		list.add(new DCPluginProfile("myfile", MyFileProcessor.class.getName(), "文件存放到该应用的myfile根目录下", false));
		list.add(new DCPluginProfile("tmp", TmpFileProcessor.class.getName(), "存放用户上传的临时文件，模拟处理过程", false));
		list.add(new DCPluginProfile("!myStream", StreamFileProcessor.class.getName(), "由程序接管写文件流和读文件流，模拟处理过程", false));
		// 注册AC权限
		ACAccessMode[] accessModes = { new ACAccessMode("权限1", 0), new ACAccessMode("权限2", 1) };
		String[] assignmentTypes = { ACPluginProfile.ASSN_TYPE_COMPANY, ACPluginProfile.ASSN_TYPE_DEPARTMENT, ACPluginProfile.ASSN_TYPE_ROLE, ACPluginProfile.ASSN_TYPE_TEAM, ACPluginProfile.ASSN_TYPE_USER };
		list.add(new ACPluginProfile("poc.plugin.myACTest", "我的AC权限测试", assignmentTypes, accessModes, false, true));
		// 注册ASLP
		// 只允许在同一个PaaS内的其他应用调用
		list.add(new ASLPPluginProfile("myName", MyNameASLP.class.getName(), "猜猜我是谁", null));
		// 调用者提供AWS会话，通过http访问
		list.add(new ASLPPluginProfile("myName1", MyNameASLP.class.getName(), "猜猜我是谁", new HttpASLP(HttpASLP.AUTH_AWS_SID)));
		// 调用者提供一个串为hehe的口令，该暗号被定义在MY_KEY变量中，通过http访问
		list.add(new ASLPPluginProfile("myName2", MyNameASLP.class.getName(), "猜猜我是谁", new HttpASLP(HttpASLP.AUTH_KEY, "MY_KEY")));
		// 调用者有应用服务提供方提供的cer公钥文件，通过http访问
		list.add(new ASLPPluginProfile("myName3", MyNameASLP.class.getName(), "猜猜我是谁", new HttpASLP(HttpASLP.AUTH_RSA)));
		// 注册流程全局事件监听器
		list.add(new ProcessPublicEventPluginProfile(MyNotification.class.getName(), "我的全局监听器"));
		// 注册Cache
		list.add(new CachePluginProfile(BookCache.class));
		// 注册全文检索引擎服务
		list.add(new FullSearchPluginProfile("db1", false, "我的全文索引库"));
		// 注册门户主题风格
		list.add(new SkinsPluginProfile(MySkins.class.getName(), false));
		// 注册ADD-ONS
		list.add(new AddOnsPluginProfile(SampleWeb.class.getName()));
		// 注册微信企业号事件处理接口
		list.add(new WechatPluginProfile(ProcessorWechatMessage.class.getName(), "处理各种微信消息"));
		return list;
	}
}
