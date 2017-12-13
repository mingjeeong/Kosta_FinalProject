package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 실사결과Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class ExamResult implements Serializable{
	private String id;
	private String itId;
	private String prId;
	private String date;
	private String prState;
	/**
	 * 
	 */
	public ExamResult() {
		super();
	}
	/**
	 * @param id
	 * @param itId
	 * @param prId
	 * @param date
	 * @param prState
	 */
	public ExamResult(String id, String itId, String prId, String date, String prState) {
		super();
		this.id = id;
		this.itId = itId;
		this.prId = prId;
		this.date = date;
		this.prState = prState;
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
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the prState
	 */
	public String getPrState() {
		return prState;
	}
	/**
	 * @param prState the prState to set
	 */
	public void setPrState(String prState) {
		this.prState = prState;
	}
	
	
}
