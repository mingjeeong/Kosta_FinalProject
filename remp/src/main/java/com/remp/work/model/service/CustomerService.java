package com.remp.work.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 고객관리를 위한 Service
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface CustomerService {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 회원의 비밀번호를 찾는 메소드
	 * @param id 회원의 아이디
	 * @param name 회원의 이름
	 * @param birth 회원의 생년월일
	 * @param mobile 회원의 휴대전화번호
	 * @return String 임시비밀번호를 전송하기 위해 회원의 아이디(이메일)를 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public String getPw(String id, String name, String birth, String mobile);
	
	/**
	 * 회원의 비밀번호를 도출된 임시비밀번호로 바꿔주는 메소드
	 * @param id 비밀번호를 변경 할 회원을 식별하는 아이디
	 * @param tmpPW 비밀번호 찾기를 통해 도출된 임시비밀번호
	 * @return int 변경 성공 시 1, 실패 시 0 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int setTmpPw(String id, String tmpPW);
	
	/**
	 * 상담테이블 자산가져오기
	 * @param customerId 고객ID
	 * @return list 검색된 값
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public List<Map<String,String>> getItemInfo(String customerId);	// 상담테이블 자산가져오기
	
	/**
	 * 상담테이블 자산의 기간 가져오기
	 * @param sbValue 선택된 자산
	 * @return map 검색된 값
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String,String> getChangeInfo(String sbValue);	// 상담테이블 자산의 기간가져오기
	
	/* ======================================== by 이원호 ================================================= */
    /**
	 * 상담사 고객 정보 가져오기
	 * @param customerId 고객ID
	 * @return map 해당 고객의 정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getCustomerInfo(String customerId);	// 상담테이블 가입여부 확인하기
	
	 /**
	 * 고객 로그인
	 * @param id 로그인 시 입력한 ID
	 * @param pw 로그인 시 입력한 PW
	 * @return map 고객 정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public HashMap<String, String> getLogin(String id, String pw);
	
	 /**
	 * 고객 회원가입
	 * @param customerJoin 입력한 고객 정보
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean addJoin(Map<String, String> customerJoin);
	
	 /**
	 * @param customerId
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean getIdCheck(String customerId);
	
	 /**
	 * 아이디 중복 확인
	 * @param searchId 고객ID
	 * @return list 아이디 목록
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getCustomerList(String searchId);
	
	 /**
	 * 관리자 고객 정보 일괄 변경
	 * @param customerInfo 변경된 고객 정보
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean setCustomerInfo(Map<String, String> customerInfo);
	
	 /**
	 * 관리자 고객 정보 개별 변경
	 * @param customerInfo 변경된 고객 정보
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean setCustomerInfoDetail(Map<String, String> customerInfo);
	
	/**
	 * 임시 회원가입 및 아이디 가져오기
	 * @param tempInfo 가입할 고객 정보
	 * @return 성공 문자열, 실패 null
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public String addTempCustomer(Map<String, String> tempInfo);
	
	/* ======================================== by 이민정 ================================================= */
	/**
	 * 고객 비밀번호 변경
	 * @param id 고객아이디
	 * @param pw 현재비밀번호
	 * @param newPw 변경할 새 비밀번호
	 * @return true, false
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public Boolean setPassword(String id, String pw, String newPw);
	
	/* ======================================== by 김재림 ================================================= */
	/**
	 * 아이디 찾기
	 * @param memberinfo 아이디찾기 필요정보
	 * @return 아이디
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public String getCustomerId(Map<String, String> memberinfo);
	
	/**
	 * 비밀번호 변경
	 * @param info 비밀번호 변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int setNewPassword(Map<String, String> info);
	
	/**
	 * 휴대폰 번호 변경
	 * @param info 휴대폰 번호 변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int setNewMobile(Map<String, String> info);
	
	/**
	 * 주소 변경
	 * @param info 주소 변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int setNewAddress(Map<String, String> info);
	
	/**
	 * 카드 변경
	 * @param info 카드 변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int setNewCard(Map<String, String> info);
	
	/**
	 * 계좌 변경
	 * @param info 계좌 변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int setNewAccount(Map<String, String> info);
	
	/**
	 * 고객정보 조회
	 * @param id 사용자 아이디
	 * @return 고객정보
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getUserInfo(String id);
}
