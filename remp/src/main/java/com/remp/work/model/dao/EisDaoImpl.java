package com.remp.work.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Eis를 위한 DaoImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Repository("eisDao")
public class EisDaoImpl implements EisDao {
	private static EisDaoImpl instance;
	private  EisDaoImpl() {
		
	}
	public static EisDaoImpl getInstance() {
		if(instance == null) {
			instance = new EisDaoImpl();
		}
		return instance;
	}
	
	private FactoryDao factory;
	@Autowired
	public void setFactoryDao(FactoryDao factory) {
		this.factory = factory;
	}
}
