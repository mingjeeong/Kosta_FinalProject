package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 고객Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class Customer implements Serializable{
	private String id;
	private String pw;
	private String name;
	private String birth;
	private String mobile;
	private String post;
	private String addr;
	private String addD;
	private String card;
	private String account;
	private String entryDate;
	private String state;
	private String update;
	/**
	 * 
	 */
	public Customer() {
		super();
	}
	/**
	 * @param id
	 * @param pw
	 * @param name
	 * @param birth
	 * @param mobile
	 * @param post
	 * @param addr
	 * @param addD
	 * @param card
	 * @param account
	 * @param entryDate
	 * @param state
	 * @param update
	 */
	public Customer(String id, String pw, String name, String birth, String mobile, String post, String addr,
			String addD, String card, String account, String entryDate, String state, String update) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.birth = birth;
		this.mobile = mobile;
		this.post = post;
		this.addr = addr;
		this.addD = addD;
		this.card = card;
		this.account = account;
		this.entryDate = entryDate;
		this.state = state;
		this.update = update;
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
	 * @return the pw
	 */
	public String getPw() {
		return pw;
	}
	/**
	 * @param pw the pw to set
	 */
	public void setPw(String pw) {
		this.pw = pw;
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
	 * @return the birth
	 */
	public String getBirth() {
		return birth;
	}
	/**
	 * @param birth the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}
	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}
	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * @return the addD
	 */
	public String getAddD() {
		return addD;
	}
	/**
	 * @param addD the addD to set
	 */
	public void setAddD(String addD) {
		this.addD = addD;
	}
	/**
	 * @return the card
	 */
	public String getCard() {
		return card;
	}
	/**
	 * @param card the card to set
	 */
	public void setCard(String card) {
		this.card = card;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the entryDate
	 */
	public String getEntryDate() {
		return entryDate;
	}
	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
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
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}
	/**
	 * @param update the update to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}
	
}
