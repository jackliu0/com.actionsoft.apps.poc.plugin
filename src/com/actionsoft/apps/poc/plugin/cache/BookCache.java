package com.actionsoft.apps.poc.plugin.cache;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.actionsoft.apps.resource.plugin.profile.CachePluginProfile;
import com.actionsoft.bpms.commons.cache.Cache;
import com.actionsoft.bpms.commons.cache.CacheManager;

public class BookCache extends Cache<String, BookModel> {

	public BookCache(CachePluginProfile profile) {
		super(profile);
		// 注册索引，为“书名”字段建立缓存唯一索引。要求“书名”字段值不能有重复的
		registeIndex(BookIndex1.class, new BookIndex1());
		// 注册索引，为“出版年份”字段建立缓存多值索引，快速索引到某年份的书
		registeIndex(BookIndex2.class, new BookIndex2());
	}

	/**
	 * 让Cache使用者直接访问到内部封装的操作，如put/remove
	 * 
	 * @return
	 */
	public static BookCache getCache() {
		return CacheManager.getCache(BookCache.class);
	}

	/**
	 * 索引示例。从“书名”获得缓存对象
	 * 
	 * @param name 书名
	 * @return
	 */
	public static BookModel getByName(String name) {
		return getCache().getByIndexSingle(BookIndex1.class, name);
	}

	/**
	 * 索引示例。从“出版年份”获得多个缓存对象
	 * 
	 * @param year
	 * @return
	 */
	public static Iterator<BookModel> listByYear(int year) {
		return getCache().getByIndex(BookIndex2.class, Integer.toString(year));
	}

	/**
	 * 排序示例。按图书名排序索引的结果
	 * 
	 * @param year
	 * @return
	 */
	public static Iterator<BookModel> listSortByName(int year) {
		return getCache().getByIndex(BookIndex2.class, Integer.toString(year), SORT_BOOK_NAME);
	}

	/**
	 * 排序示例。按图书库存量排序全部缓存
	 * 
	 * @return
	 */
	public static Iterator<BookModel> listSortByQuantity() {
		return getCache().iteratorSorted(SORT_BOOK_QUANTITY);
	}

	/**
	 * 初始化缓存。通常这里从DAO或其他持久数据中完成初始化过程
	 */
	protected void load() {
		// 模拟一个持久层数据结构
		List<BookModel> list = new ArrayList<>();
		list.add(new BookModel("001", "北平无战事", 1000, 41.3d, 2014));
		list.add(new BookModel("002", "大江东去", 600, 115.9d, 2013));
		list.add(new BookModel("003", "永恒的终结", 1200, 22.9d, 2014));
		list.add(new BookModel("004", "你总会路过这个世界的美好", 70, 25.9d, 2014));
		list.add(new BookModel("005", "银河系搭车客指南系列", 890, 11.74d, 2012));
		for (BookModel book : list) {
			// 放入本地缓存
			put(book.getId(), book, false);
		}
	}

	// ------------- comparator -------------

	/**
	 * 按书名排序比较器
	 */
	private static Comparator<BookModel> SORT_BOOK_NAME = new Comparator<BookModel>() {
		public int compare(BookModel o1, BookModel o2) {
			if (o1 == null || o2 == null) {
				return 0;
			}
			return o1.getName().compareTo(o2.getName());
		}
	};

	/**
	 * 按图书数量排序比较器
	 */
	private static Comparator<BookModel> SORT_BOOK_QUANTITY = new Comparator<BookModel>() {
		public int compare(BookModel o1, BookModel o2) {
			if (o1 == null || o2 == null) {
				return 0;
			}

			return o1.getQuantity() - o2.getQuantity();
		}
	};

}
