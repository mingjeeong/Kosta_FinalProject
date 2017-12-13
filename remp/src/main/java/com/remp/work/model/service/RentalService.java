package com.remp.work.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Deprive;
import com.remp.work.model.dto.Item;

/**
 * <p>
 * 렌탈관리를 위한 Service
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface RentalService {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * as 및 회수상담 등록
	 * @param map 요청정보
	 * @return string 성공 문자열, 실패 null
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public String setAdvice(Map<String, String> map);
	
	/* ======================================== by 이원호 ================================================= */
	/**
	 * 방문 테이블 조회
	 * @param state AS 또는 Refund
	 * @param select 검색 탭
	 * @param search 검색 내용
	 * @return list 방문 테이블 내용 조회
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getVisitSearchList(String state, String select, String search);
	
	/**
	 * 구매, 자산관리테이블, 품목테이블 정보 가져오기
	 * @param buyId 구매 ID
	 * @param visitId 방문 ID
	 * @return map 각각 가져온 정보
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getBuyList(String buyId, String visitId);
	
	/**
	 * 외부수리기사 회수결과 등록
	 * @param depriveDto 회수결과 내용
	 * @param buyId 구매ID
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean addRefundResult(Deprive depriveDto, String buyId);
	
	/**
	 * 외부수리기사 결과 입고테이블 변경
	 * @param depriveDto 회수결과 내용
	 * @param buyId 구매ID
	 * @return true, false
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean setRefundResult(Deprive depriveDto, String buyId);
	/**
	 * 상담 등록
	 * @param employeeId
	 * @return 성공 문자열, 실패 null
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public String getAdviceId(String employeeId);
	
	/**
	 * 일반 상담 세부내용 등록
	 * @param nomalInfo 일반 상담 내용
	 * @return int 성공 1, 실패 0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int addNomalAdvice(Map<String, String> nomalInfo);
	
	/**
	 * 렌탈 상담 세부내용 등록
	 * @param rentalInfo 렌탈 상담 내용
	 * @return int 성공 1, 실패 0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int addRentalAdvice(Map<String, String> rentalInfo);
	
	/* ======================================== by 이민정 ================================================= */
	   /**
	    * 고객렌탈 메인 화면 품목리스트 보여주기
	    * @return 고객렌탈 메인 화면 품목리스트
		 * @author 이민정
		 * @since ReMP v 1.0
	    */
	   public ArrayList<Item> getItemList();
	   
	   /**
	    * 제품 검색
	    * @param sb_search 검색카테고리
	    * @param item 검색어
	    * @return 제품검색 결과 리스트
		 * @author 이민정
		 * @since ReMP v 1.0
	    */
	   public ArrayList<Item> getSearchList(String sb_search, String item);
	   
	   /**
	    * 렌탈 제품 상세보기
	    * @param itemId 품목아이디
	    * @return 품목dto
		 * @author 이민정
		 * @since ReMP v 1.0
	    */
	   public Item getItem(String itemId);
}
