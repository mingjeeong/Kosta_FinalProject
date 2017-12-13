package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 사용부품Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class UseParts implements Serializable{
	private String reId;
	private String paId;
	private int count;
	
	/**
	 * 
	 */
	public UseParts() {
		super();
	}

	/**
	 * @param reId
	 * @param paId
	 * @param count
	 */
	public UseParts(String reId, String paId, int count) {
		super();
		this.reId = reId;
		this.paId = paId;
		this.count = count;
	}

	/**
	 * @return the reId
	 */
	public String getReId() {
		return reId;
	}

	/**
	 * @param reId the reId to set
	 */
	public void setReId(String reId) {
		this.reId = reId;
	}

	/**
	 * @return the paId
	 */
	public String getPaId() {
		return paId;
	}

	/**
	 * @param paId the paId to set
	 */
	public void setPaId(String paId) {
		this.paId = paId;
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
}
