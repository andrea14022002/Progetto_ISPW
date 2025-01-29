package com.plantnursery.bean;

import java.time.ZonedDateTime;

public class NotificationBean {

	private String type;

	private String setName;

	private String orderCode;

	private ZonedDateTime dateAndTime;

	public String getType() {
		return type;
	}

	public String getSetName() {
		return this.setName;
	}

	public String getOrderCode() {
		return this.orderCode;
	}

	public ZonedDateTime getDateAndTime() {
		return this.dateAndTime;
	}

	public void setDateAndTime(ZonedDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String setName) {
		this.setName = setName;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}