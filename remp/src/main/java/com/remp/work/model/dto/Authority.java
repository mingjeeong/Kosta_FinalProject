package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 권한Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class Authority implements Serializable{
	private String id;
	private String name;
	private String content;
	/**
	 * 
	 */
	public Authority() {
		super();
	}
	/**
	 * @param id
	 * @param name
	 * @param content
	 */
	public Authority(String id, String name, String content) {
		super();
		this.id = id;
		this.name = name;
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
