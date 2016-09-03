package com.actionsoft.apps.poc.plugin.cache;

import com.actionsoft.bpms.commons.cache.ListValueIndex;

/**
 * 索引示例。为“出版年份”字段建立缓存多值索引，快速索引到某年份的书
 */
public class BookIndex2 extends ListValueIndex<String, BookModel> {

	/**
	 * 返回该索引的键值
	 */
	public String key(BookModel model) {
		return Integer.toString(model.getYear());
	}

}
