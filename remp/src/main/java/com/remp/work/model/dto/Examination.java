package com.remp.work.model.dto;

import java.io.Serializable;

/**
 * 실사Dto
 * @author 이동훈, 김재림, 이원호, 이민정
 *
 */
public class Examination implements Serializable{
	private String id;
	private String start;
	private String end;
	private String name;
	private String code;
	private int probe;
	private int repair;
	private int rental;
	private int total;
	private int product;
	private String content;
	/**
	 * 
	 */
	public Examination() {
		super();
	}
	/**
	 * @param id
	 * @param start
	 * @param end
	 * @param name
	 * @param code
	 * @param probe
	 * @param repair
	 * @param rental
	 * @param total
	 * @param product
	 * @param content
	 */
	public Examination(String id, String start, String end, String name, String code, int probe, int repair, int rental,
			int total, int product, String content) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.name = name;
		this.code = code;
		this.probe = probe;
		this.repair = repair;
		this.rental = rental;
		this.total = total;
		this.product = product;
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
	 * @return the start
	 */
	public String getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the probe
	 */
	public int getProbe() {
		return probe;
	}
	/**
	 * @param probe the probe to set
	 */
	public void setProbe(int probe) {
		this.probe = probe;
	}
	/**
	 * @return the repair
	 */
	public int getRepair() {
		return repair;
	}
	/**
	 * @param repair the repair to set
	 */
	public void setRepair(int repair) {
		this.repair = repair;
	}
	/**
	 * @return the rental
	 */
	public int getRental() {
		return rental;
	}
	/**
	 * @param rental the rental to set
	 */
	public void setRental(int rental) {
		this.rental = rental;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the product
	 */
	public int getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(int product) {
		this.product = product;
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
