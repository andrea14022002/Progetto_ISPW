package com.plantnursery.model;

import java.time.LocalDateTime;


public class Notification {

	public Notification(TypeNotif type, LocalDateTime dateTime, String setName, String orderCode) {
		this.type = type;
		this.dateAndTime = dateTime;
		this.setName = setName;
		this.orderCode = orderCode;
	}

	private final LocalDateTime dateAndTime;

	private final String orderCode;

	private final String setName;

	private final TypeNotif type;

	public TypeNotif getType() {
		return this.type;
	}

	public LocalDateTime getDateAndTime() {
		return this.dateAndTime;
	}

	public String getOrderCode() {
		return this.orderCode;
	}

	public String getSetName() {
		return this.setName;
	}

}