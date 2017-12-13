package com.remp.work.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Employee;

/**
 * <p>
 * 직원관리를 위한 Dao
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface MemberDao {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 내부사용자의 정보를 가져오는 메소드
	 * @param id 해당 회원을 식별하기 위한 id
	 * @return dto 해당 회원의 정보가 담긴 dto객체를 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Employee selectMemberInfoSub(String id);
	
	/**
	 * 내부사용자의 정보를 적용하는 메소드
	 * @param map 해당 회원의 정보를 mqp객체에 담아서 전달
	 * @return int 성공 시 1, 실패 시 0값을 반환
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateMemberInfoSub(Map<String, String> map);
	
	/* ======================================== by 이원호 ================================================= */
	/**
	 * 시퀀스를 통한 사원 번호 발급
	 * @return 발급된 사원 번호
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public String selectRandomEmpl();
	
	/**
	 * 직원 로그인
	 * @param id 입력한 Id
	 * @param pw 입력한 Pw
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public HashMap<String, String> selectLogin(String id, String pw);
	
	/**
	 * 관리자 직원 목록 조회
	 * @param searchId 검색할 직원 Id
	 * @return 직원 목록 리스트
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectMemberList(String searchId);
	
	/**
	 * 권한 목록 가져오기
	 * @return 권한 ID, 권한 Name
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectAuthorityList();
	
	/**
	 * 관리자 선택한 직원 정보 가져오기
	 * @param memberId 직원 사번
	 * @return 선택된 직원 정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectMemberInfo(String memberId);
	
	/**
	 * 직원 회원가입
	 * @param memberJoin 직원 가입 정보
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertMemberJoin(Map<String, String> memberJoin);
	
	/**
	 * 직원 정보 일괄 변경
	 * @param memberInfo 변경된 직원 정보
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int updateMemberInfo(Map<String, String> memberInfo);
	
	/**
	 * 직원 정보 개별 변경
	 * @param memberInfo 변경된 직원 정보
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int updateMemberInfoDetail(Map<String, String> memberInfo);
}
