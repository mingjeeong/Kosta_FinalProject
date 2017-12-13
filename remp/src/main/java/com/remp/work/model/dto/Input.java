package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 입고Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class Input implements Serializable {
	private String id;
	private String prId;
	private String itName;
	private int count;
	private String state;
	private String day;
	private String delivery;
	private String fDay;
	private String complete;
	/**
	 * 
	 */
	public Input() {
		super();
	}
	/**
	 * @param id
	 * @param prId
	 * @param itName
	 * @param count
	 * @param state
	 * @param day
	 * @param delivery
	 * @param fDay
	 * @param complete
	 */
	public Input(String id, String prId, String itName, int count, String state, String day, String delivery,
			String fDay, String complete) {
		super();
		this.id = id;
		this.prId = prId;
		this.itName = itName;
		this.count = count;
		this.state = state;
		this.day = day;
		this.delivery = delivery;
		this.fDay = fDay;
		this.complete = complete;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the prId
	 */
	public String getPrId() {
		return prId;
	}
	/**
	 * @param prId the prId to set
	 */
	public void setPrId(String prId) {
		this.prId = prId;
	}
	/**
	 * @return the itName
	 */
	public String getItName() {
		return itName;
	}
	/**
	 * @param itName the itName to set
	 */
	public void setItName(String itName) {
		this.itName = itName;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return the delivery
	 */
	public String getDelivery() {
		return delivery;
	}
	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	/**
	 * @return the fDay
	 */
	public String getfDay() {
		return fDay;
	}
	/**
	 * @param fDay the fDay to set
	 */
	public void setfDay(String fDay) {
		this.fDay = fDay;
	}
	/**
	 * @return the complete
	 */
	public String getComplete() {
		return complete;
	}
	/**
	 * @param complete the complete to set
	 */
	public void setComplete(String complete) {
		this.complete = complete;
	}
	
	
}
