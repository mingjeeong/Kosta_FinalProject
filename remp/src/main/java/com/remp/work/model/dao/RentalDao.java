package com.remp.work.model.dao;

import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Advice;
import com.remp.work.model.dto.Deprive;

/**
 * <p>
 * 렌탈관리를 위한 Dao
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface RentalDao {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 회수상담 등록 시 방문테이블 삽입
	 * @param map 요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int insertVisit(Map<String, String> map);
	
	/* ======================================== by 이원호 ================================================= */
	/**
	 * 방문 테이블 조회
	 * @param state AS 또는 Refund
	 * @param select 검색 탭
	 * @param Search 검색 내용
	 * @return AS/회수 요청 리스트
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectVisitSearchList(String state, String select, String Search); // 방문테이블 검색결과 가져오기
	
	/**
	 * 구매, 자산관리테이블, 품목테이블 정보 가져오기
	 * @param buyId 구매 ID
	 * @return 품목 시작일 종료일
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectBuyDay(String buyId); // 구매테이블 및 자산관리자테이블, 품목테이블 가져오기
	
	/**
	 * 외부수리기사 회수결과 등록
	 * @param depriveDto 회수결과 내용
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertRefundResult(Deprive depriveDto); // 회수관련 
	
	/**
	 * 외부수리기사 회수결과 변경
	 * @param depriveDto 회수결과 내용
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int updateRefundResult(Deprive depriveDto);
	
	/**
	 * 회수 결과 조회
	 * @param prId 자산ID
	 * @param cuId 고객ID
	 * @param viDay 결과 등록일
	 * @return 회수 결과와 관련된 정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectRefundResult(String prId, String cuId, String viDay);
	
	/**
	 * 상담 아이디 생성
	 * @return 발급된 상담ID
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public String selectAdviceId();
	
	/**
	 * 상담 등록
	 * @param adviceId 상담Id
	 * @param employeeId 직원ID
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertAdvice(String adviceId, String employeeId); // 상담등록
	
	/**
	 * 렌탈 상담 시 구매정보 등록 
	 * @param adviceInfo 상담 내용
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertBuyAdvice(Map<String, String> adviceInfo);
	
	/**
	 * 렌탈 상담 시 출고 요청 등록
	 * @param adviceInfo 상담 내용
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertOutput(Map<String, String> adviceInfo);
	
	/**
	 * 상담 세부내용 등록
	 * @param map 상담 내용
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertAdvice(Map<String, String> map);
	
	/**
	 * 구매ID 가져오기
	 * @return 새로 발급된 구매ID
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public String selectBuyId();
	
	/**
	 * 회수 처리시 품목 입고 요청
	 * @param depriveDto
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int insertRefundInput(Deprive depriveDto);
}
