package com.remp.work.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 고객관리를 위한 DAO
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface CustomerDao {
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
	public String selectPw(String id, String name, String birth, String mobile);
	
	/**
	 * 회원의 비밀번호를 도출된 임시비밀번호로 바꿔주는 메소드
	 * @param id 비밀번호를 변경 할 회원을 식별하는 아이디
	 * @param tmpPW 비밀번호 찾기를 통해 도출된 임시비밀번호
	 * @return int 변경 성공 시 1, 실패 시 0 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateTmpPw(String id, String tmpPW);
	
	/**
	 * 회원의 비밀번호를 도출된 임시비밀번호로 바꿔주는 메소드
	 * @param customerId 비밀번호를 변경 할 회원을 식별하는 아이디
	 * @return int 변경 성공 시 1, 실패 시 0 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectItemInfo(String customerId);
	
	/**
	 * 상담테이블 자산의 기간 가져오기
	 * @param sbValue 선택된 자산
	 * @return map 검색된 값
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectChangeInfo(String sbValue);
	
	/* ======================================== by 이원호 ================================================= */
	/**
	 * 상담사 고객 정보 가져오기
	 * @param customerId 고객ID
	 * @return 고객ID, Name 리스트
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectCustomerInfo(String customerId);	// 상담테이블 가입여부 가져오기
	
	/**
	 * 고객 로그인
	 * @param id 로그인 시 입력한 ID
	 * @param pw 로그인 시 입력한 PW
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public HashMap<String, String> selectLogin(String id, String pw);
	
	/**
	 * 고객 회원가입
	 * @param customerJoin 입력한 고객 정보
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertJoin(Map<String, String> customerJoin);
	
	/**
	 * 아이디 중복 확인
	 * @param customerId 고객ID
	 * @return 유 true 무 false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean selectIdCheck(String customerId);
	
	/**
	 * 관리자 고객 아이디 검색
	 * @param searchId 검색
	 * @return 고객ID, Name 리스트
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectCustomerList(String searchId);
	
	/**
	 * 관리자 고객 정보 일괄 변경
	 * @param customerInfo 변경된 고객 정보
	 * @return 고객정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int updateCustomerInfo(Map<String, String> customerInfo);
	
	/**
	 * 관리자 고객 정보 개별 변경
	 * @param customerInfo 변경된 고객 정보
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int updateCustomerInfoDetail(Map<String, String> customerInfo);
	
	/**
	 * 임시 회원가입
	 * @param tempInfo 가입할 고객 정보
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertTempCustomer(Map<String, String> tempInfo);
	
	/**
	 * 임시 회원가입한 아이디 가져오기
	 * @param tempInfo 고객 정보
	 * @return 발급된 아이디
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public String selectTempInfo(Map<String, String> tempInfo);
	
	/* ======================================== by 김재림 ================================================= */
	/**
	 * 아이디찾기
	 * @param memberinfo 아이디 찾기 필요정보
	 * @return 아이디
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public String selectCustomerId(Map<String, String> memberinfo);
	
	/**
	 * 회원정보 조회
	 * @param id 아이디
	 * @return 회원정보
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectUserInfo(String id) ;
	
	/**
	 * 회원 계좌변경
	 * @param info 계좌변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int UpdateUserAccount(Map<String, String> info) ;
	
	/**
	 * 회원 카드변경
	 * @param info 카드변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int UpdateUserCard(Map<String, String> info) ;
	
	/**
	 * 회원 주소변경
	 * @param info 주소변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int UpdateUserAddress(Map<String, String> info) ;
	
	/**
	 * 회원 핸드폰 번호변경
	 * @param info 핸드폰 번호 변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int UpdateUserMobile(Map<String, String> info) ;
	
	/**
	 * 회원 비밀번호변경
	 * @param info 비밀번호변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int UpdateUserPassword(Map<String, String> info) ;

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
	public boolean updatePassword(String id, String pw, String newPw);
}
