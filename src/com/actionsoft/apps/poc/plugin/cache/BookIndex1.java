package com.actionsoft.apps.poc.plugin.cache;

import com.actionsoft.bpms.commons.cache.SingleValueIndex;

/**
 * 索引示例。为“书名”字段建立缓存唯一索引。要求“书名”字段值不能有重复的
 */
public class BookIndex1 extends SingleValueIndex<String, BookModel> {

	/**
	 * 返回该索引的键值
	 */
	public String key(BookModel model) {
		return model.getName();
	}

}
