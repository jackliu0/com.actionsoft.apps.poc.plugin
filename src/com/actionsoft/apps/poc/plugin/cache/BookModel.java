package com.actionsoft.apps.poc.plugin.cache;

import com.actionsoft.bpms.commons.mvc.model.IModelBean;
import com.actionsoft.bpms.commons.mvc.model.ModelBean;

public class BookModel extends ModelBean implements IModelBean {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private int year;
	private int quantity;
	private double price;

	public BookModel() {
	}

	public BookModel(String id, String name, int quantity, double price, int year) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
