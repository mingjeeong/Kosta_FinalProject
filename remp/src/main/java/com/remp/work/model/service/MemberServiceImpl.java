package com.remp.work.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remp.work.model.dao.MemberDao;
import com.remp.work.model.dto.Employee;

/**
 * <p>
 * 직원관리를 위한 ServiceImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Service("MemberService")
public class MemberServiceImpl implements MemberService{
	MemberDao memberDao;
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	/* ======================================== by 이동훈 ================================================= */
	@Override
	public Employee getMemberInfoSub(String id) {
		Employee emp = new Employee();
		emp = memberDao.selectMemberInfoSub(id);
		return emp;
	}

	@Override
	public int setMemberInfoSub(Map<String, String> map) {
		int result;
		result = memberDao.updateMemberInfoSub(map);
		return result;
	}
	
	/* ======================================== by 이원호 ================================================= */
	@Override
	public HashMap<String, String> getLogin(String id, String pw) {
		return memberDao.selectLogin(id, pw);
	}
	
	@Override
	public List<Map<String, String>> getMemberList(String searchId) {
		return memberDao.selectMemberList(searchId);
	}
	
	@Override
	public List<Map<String, String>> getAuthorityList() {
		return memberDao.selectAuthorityList();
	}
	
	@Override
	public Map<String, String> getMemberInfo(String memberId) {
		return memberDao.selectMemberInfo(memberId);
	}

	@Override
	public boolean addMemberJoin(Map<String, String> memberJoin) {
		if (memberDao.insertMemberJoin(memberJoin) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean setMemberInfo(Map<String, String> memberInfo) {
		if (memberDao.updateMemberInfo(memberInfo) == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean setMemberInfoDetail(Map<String, String> memberInfo) {
		if (memberDao.updateMemberInfoDetail(memberInfo) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getRandomEmpl() {
		return memberDao.selectRandomEmpl();
	}
}
