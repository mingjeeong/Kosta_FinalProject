package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 상담상세Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class AdviceDetail implements Serializable{
	private String id;
	private String buyId;
	private String sort;
	private String content;
	/**
	 * 
	 */
	public AdviceDetail() {
		super();
	}
	/**
	 * @param id
	 * @param buyId
	 * @param sort
	 * @param content
	 */
	public AdviceDetail(String id, String buyId, String sort, String content) {
		super();
		this.id = id;
		this.buyId = buyId;
		this.sort = sort;
		this.content = content;
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
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
