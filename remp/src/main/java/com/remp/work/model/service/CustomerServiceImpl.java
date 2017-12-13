package com.remp.work.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remp.work.model.dao.CustomerDao;

/**
 * <p>
 * 고객관리를 위한 ServiceImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService{
	private CustomerDao customerDao;
	@Autowired
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/* ======================================== by 이동훈 ================================================= */
	@Override
	public String getPw(String id, String name, String birth, String mobile) {
		return customerDao.selectPw(id, name, birth, mobile);
	}

	@Override
	public int setTmpPw(String id, String tmpPW) {
		return customerDao.updateTmpPw(id, tmpPW);
	}
	
	@Override
	public List<Map<String, String>> getItemInfo(String customerId) {
		return customerDao.selectItemInfo(customerId);
	}
	
	@Override
	public Map<String, String> getChangeInfo(String sbValue) {
		return customerDao.selectChangeInfo(sbValue);
	}
	
	/* ======================================== by 이원호 ================================================= */
	@Override
	public HashMap<String, String> getLogin(String id, String pw) {
		return customerDao.selectLogin(id, pw);
	}

	@Override
	public boolean addJoin(Map<String, String> customerJoin) {
		if (customerDao.insertJoin(customerJoin) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getIdCheck(String customerId) {
		return customerDao.selectIdCheck(customerId);
	}

	@Override
	public List<Map<String, String>> getCustomerList(String searchId) {
		return customerDao.selectCustomerList(searchId);
	}
	
	@Override
	public Map<String, String> getCustomerInfo(String customerId) {
		return customerDao.selectCustomerInfo(customerId);
	}

	@Override
	public boolean setCustomerInfo(Map<String, String> customerInfo) {
		if (customerDao.updateCustomerInfo(customerInfo) == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean setCustomerInfoDetail(Map<String, String> customerInfo) {
		if (customerDao.updateCustomerInfoDetail(customerInfo) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String addTempCustomer(Map<String, String> tempInfo) {
		if (customerDao.insertTempCustomer(tempInfo) == 1) {
			return customerDao.selectTempInfo(tempInfo);
		}
		return "";
	}
	
	/* ======================================== by 이민정 ================================================= */
	@Override
	public Boolean setPassword(String id, String pw, String newPw) {
		return null;
		//return customerDao.updatePassword(id, pw, newPw);
	}
	
	/* ======================================== by 김재림 ================================================= */
	@Override
	public String getCustomerId(Map<String, String> memberinfo) {
		return customerDao.selectCustomerId(memberinfo);
	}

	@Override
	public int setNewPassword(Map<String, String> jsonToMap) {
		return customerDao.UpdateUserPassword(jsonToMap);
	}

	@Override
	public int setNewMobile(Map<String, String> jsonToMap) {
		return customerDao.UpdateUserMobile(jsonToMap);
	}

	@Override
	public int setNewAddress(Map<String, String> jsonToMap) {
		return customerDao.UpdateUserAddress(jsonToMap);
	}
	
	@Override
	public int setNewCard(Map<String, String> jsonToMap) {
		return customerDao.UpdateUserCard(jsonToMap);
	}
	
	@Override
	public int setNewAccount(Map<String, String> jsonToMap) {
		return customerDao.UpdateUserAccount(jsonToMap);
	}

	@Override
	public Map<String, String> getUserInfo(String id) {
		return customerDao.selectUserInfo(id);
	}
}
