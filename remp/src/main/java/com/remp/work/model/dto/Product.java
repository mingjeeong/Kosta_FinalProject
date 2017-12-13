package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 자산Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class Product implements Serializable{
	private String id;
	private String buyId;
	private String itId;
	private String itName;
	private String firstDay;
	private String inDay;
	private String outDay;
	private String outTarget;
	private String location;
	private String needs;
	private String state;
	private int count;
	private String qr;
	
	/**
	 * 
	 */
	public Product() {
		super();
	}

	/**
	 * @param id
	 * @param buyId
	 * @param itId
	 * @param itName
	 * @param firstDay
	 * @param inDay
	 * @param outDay
	 * @param outTarget
	 * @param location
	 * @param needs
	 * @param state
	 * @param count
	 * @param qr
	 */
	public Product(String id, String buyId, String itId, String itName, String firstDay, String inDay, String outDay,
			String outTarget, String location, String needs, String state, int count, String qr) {
		super();
		this.id = id;
		this.buyId = buyId;
		this.itId = itId;
		this.itName = itName;
		this.firstDay = firstDay;
		this.inDay = inDay;
		this.outDay = outDay;
		this.outTarget = outTarget;
		this.location = location;
		this.needs = needs;
		this.state = state;
		this.count = count;
		this.qr = qr;
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
	 * @return the buyId
	 */
	public String getBuyId() {
		return buyId;
	}

	/**
	 * @param buyId the buyId to set
	 */
	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}

	/**
	 * @return the itId
	 */
	public String getItId() {
		return itId;
	}

	/**
	 * @param itId the itId to set
	 */
	public void setItId(String itId) {
		this.itId = itId;
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
	 * @return the firstDay
	 */
	public String getFirstDay() {
		return firstDay;
	}

	/**
	 * @param firstDay the firstDay to set
	 */
	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}

	/**
	 * @return the inDay
	 */
	public String getInDay() {
		return inDay;
	}

	/**
	 * @param inDay the inDay to set
	 */
	public void setInDay(String inDay) {
		this.inDay = inDay;
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
	 * @return the outTarget
	 */
	public String getOutTarget() {
		return outTarget;
	}

	/**
	 * @param outTarget the outTarget to set
	 */
	public void setOutTarget(String outTarget) {
		this.outTarget = outTarget;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the needs
	 */
	public String getNeeds() {
		return needs;
	}

	/**
	 * @param needs the needs to set
	 */
	public void setNeeds(String needs) {
		this.needs = needs;
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
	 * @return the qr
	 */
	public String getQr() {
		return qr;
	}

	/**
	 * @param qr the qr to set
	 */
	public void setQr(String qr) {
		this.qr = qr;
	}
}
