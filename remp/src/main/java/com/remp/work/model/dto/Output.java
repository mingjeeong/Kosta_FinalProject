package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 출고Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class Output  implements Serializable {
	private String id;
	private String prId;
	private String name;
	private String state;
	private String outDay;
	private int count;
	private String delivery;
	private String day;
	private String complete;
	/**
	 * 
	 */
	public Output() {
		super();
	}
	/**
	 * @param id
	 * @param prId
	 * @param name
	 * @param state
	 * @param outDay
	 * @param count
	 * @param delivery
	 * @param day
	 * @param complete
	 */
	public Output(String id, String prId, String name, String state, String outDay, int count, String delivery,
			String day, String complete) {
		super();
		this.id = id;
		this.prId = prId;
		this.name = name;
		this.state = state;
		this.outDay = outDay;
		this.count = count;
		this.delivery = delivery;
		this.day = day;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the outDay
	 */
	public String getOutDay() {
		return outDay;
	}
	/**
	 * @param outDay the outDay to set
	 */
	public void setOutDay(String outDay) {
		this.outDay = outDay;
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
