package com.remp.work.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Employee;

/**
 * <p>
 * 직원관리를 위한 Service
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface MemberService {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 내부사용자의 정보를 가져오는 메소드
	 * @param id 해당 회원을 식별하기 위한 id
	 * @return dto 해당 회원의 정보가 담긴 dto객체를 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Employee getMemberInfoSub(String id);
	
	/**
	 * 내부사용자의 정보를 적용하는 메소드
	 * @param map 해당 회원의 정보를 mqp객체에 담아서 전달
	 * @return int 성공 시 1, 실패 시 0값을 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int setMemberInfoSub(Map<String, String> map);
	
	/* ======================================== by 이원호 ================================================= */
	/**
	 * 직원 로그인
	 * @param id 입력한 Id
	 * @param pw 입력한 Pw
	 * @return map 직원정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public HashMap<String, String> getLogin(String id, String pw);
	
	/**
	 * 관리자 직원 목록 조회
	 * @param searchId 검색할 직원 Id
	 * @return list 직원 목록
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getMemberList(String searchId);
	
	/**
	 * 권한 목록 가져오기
	 * @return list 권한 목록
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getAuthorityList();
	
	/**
	 * 관리자 선택한 직원 정보 가져오기
	 * @param memberId 직원 사번
	 * @return map 선택한 직원의 정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getMemberInfo(String memberId);
	
	/**
	 * 직원 회원가입
	 * @param memberJoin 직원 가입 정보
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean addMemberJoin(Map<String, String> memberJoin);
	
	/**
	 * 직원 정보 일괄 변경
	 * @param memberInfo 변경된 직원 정보
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean setMemberInfo(Map<String, String> memberInfo);
	
	/**
	 * 직원 정보 개별 변경
	 * @param memberInfo 변경된 직원 정보
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean setMemberInfoDetail(Map<String, String> memberInfo);
	
	/**
	 * 시퀀스를 통한 사원 번호 발급
	 * @return 성공 문자열, 실패 null
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public String getRandomEmpl();
}
