package com.remp.work.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Product;

/**
 * <p>
 * 자산관리를 위한 Service
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface AssetService {
	
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * as결과 신규등록
	 * @param map as요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int addFix(Map<String, String> map);
	
	/**
	 * as결과 갱신
	 * @param map as요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int setFix(Map<String, String> map);

	/**
	 * 요청 리스트의 값 가져와서 보여주는 메소드
	 * @param buyId 구매ID
	 * @return map 검색한 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getVisitInfo(String buyId);
	
	/**
	 * as 및 회수에 따른 자산테이블 갱신
	 * @param state 요청상태
	 * @param buyId 구매ID
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int setState(String state, String buyId);
	
	/**
	 * as요청에서 회수처리 시 입고테이블 정보 갱신
	 * @param map 갱신정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int setInState(Map<String, String> map);
	
	/**
	 * 자산관리 전체 및 검색조회
	 * @param keyword 검색키워드
	 * @return list 검색된 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getProduct(String keyword);
	
	/**
	 * 자산관리 상세조회
	 * @param prId 자산ID
	 * @param itId 품목ID
	 * @return map 해당 자산의 상세정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getProductUpdate(String prId, String itId);	

	/**
	 * 자산변경하기(개별)
	 * @param map 요청정보
	 * @return string 성공 문자열, 실패 null
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public String setProductUpdate(Map<String, String>map);
	
	/**
	 * 자산변경하기(전체)
	 * @param map 요청정보
	 * @return string 성공 문자열, 실패 null
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public String setProductUpdateAll(Map<String, String>map);
	
	/**
	 * 자산등록 등록대기자산 조회
	 * @return list 검색된 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getProductInsertRequest();

	/**
	 * 자산등록 정보폼 가져오기
	 * @param prId 자산ID
	 * @return 해당 자산의 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getProductInsert(String prId);

	/**
	 * 자산등록하기
	 * @param map 요청정보
	 * @return string 성공 문자열, 실패 null
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public String setProductInsert(Map<String, String> map);
	
	/* ======================================== by 김재림 ================================================= */
	/**
	 * 렌탈확정기능
	 * @param info 렌탈확정 필요정보
	 * @return 확정 갯수
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	int setAssetRentalOut(Map<String, String> info);
	
	/**
	 * 품목별 자산 리스트 조회
	 * @param itemId 품목아이디
	 * @return 자산리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	List<Map<String, String>> getAssetList(String itemId);
	
	/**
	 * 렌탈 요청 리스트 조회
	 * @param keyword 검색어
	 * @return 렌탈 요청레스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	List<Map<String, String>> getRentalRequestList(String keyword);
	
	/**
	 * 자산상태별 자산리스트 조회
	 * @param assetState 자산상태
	 * @return 자산리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	List<Map<String, String>> getRequestAssetList(String assetState);
	
	/**
	 * 자산 요청별 자산리스트 조회
	 * @param assetState 자산상태
	 * @param keyword 검색어
	 * @return 자산리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	List<Map<String, String>> getRequestSearchAssetList(String assetState, String keyword);
	
	/**
	 * 자산실사계획 변경
	 * @param info 자산실사 변경 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	int setDueDiligencePlan(Map<String, String> info);
	
	/**
	 * 검색어별 자산실사 계획 조회
	 * @param keyword 검색어
	 * @return 자산실사 계획 리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	List<Map<String, String>> getDueDiligencePlanList(String keyword);
	
	/**
	 * 자산 실사계획 등록
	 * @param info 자산 실사계획 등록 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	int newDueDiligencePlan(Map<String, String> info);
	
	/**
	 * 자산 출고
	 * @param info 출고 필요정보
	 * @return 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	int setUnstore(Map<String, String> info);
	
	/* ======================================== by 이민정 ================================================= */
	/**
	 * 입고요청 자산조회
	 * @param assetState 입고요청 자산상태
	 * @return 입고요청조회 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getInputRequest(String assetState);
	
	/**
	 * 입고 상태 변경
	 * @param inputId 입고아이디
	 * @param assetState 입고자산상태
	 * @return 성공 1 실패 0
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int setInputState(String inputId, String assetState);
	
	/**
	 * 입고요청 자산 검색
	 * @param assetState 자산상태
	 * @param productName 검색어
	 * @return 검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String,String>> searchInputRequest(String assetState, String productName);
	
	/**
	 * 입고조회
	 * @param assetState 입고자산상태
	 * @return 입고 조회결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getInput(String assetState);
	
	/**
	 * 입고 검색 조회
	 * @param assetState 입고자산 상태
	 * @param productName 검색어
	 * @return 입고 검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String,String>> searchInput(String assetState, String productName);
	
	/**
	 * 점검대기 조회
	 * @return 점검대기 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public ArrayList<Product> getRepairList();
	
	/**
	 * 점검대기 검색 조회
	 * @param keyword 검색어
	 * @param selectName 검색카테고리
	 * @return 점검대기 검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String,String>> searchRepairList(String keyword, String selectName);
	
	/**
	 * 점검헤드에서 디테일로 가져오기
	 * @param productId 자산아이디
	 * @param productState 자산상태
	 * @return 헤드의 정보 map
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getRepairForm(String productId, String productState);
	
	/**
	 * 내부수리 해당 품목에 부품 조회
	 * @param itemId 품목아이디
	 * @return 부품 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String,String>> getRepairPartsList(String itemId);
	
	/**
	 * 내부수리기사 점검 후 점검테이블에 점검내역 등록
	 * @param formInput 폼내역
	 * @param partsInput 부품폼내역
	 * @return 쿼리반환 여부에 따라 int 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int addRepairResult(Map<String, Object> formInput, Map<String, String> partsInput);
	
	/**
	 * 내부수리기사 점검 후 자산테이블에 자산상태 변경
	 * @param productId 자산아이디
	 * @param repairSort 점검분류
	 * @return 성공 시 1, 실패 시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int updateProductState(String productId, String repairSort);
	
	/**
	 * 고객 렌탈 구매
	 * @param loginId 고객아이디
	 * @param rentalInfo 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int insertCustomerBuy(String loginId, Map<String, String> rentalInfo) ;
	
	/**
	 * 고객 렌탈 구매하여 출고
	 * @param map 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int insertOutput(Map<String, String> map); 
	
	/**
	 * 모든 부품리스트 조회
	 * @return 모든 부품리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getAllParts();
	
	/**
	 * 모든 점검결과 리스트 조회
	 * @param id 내부수리기사 아이디
	 * @return 모든 점검결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getAllRepairResult(String id);
	
	/**
	 * 부품검색
	 * @param type 검색카테고리
	 * @param keyword 검색어
	 * @return 부품검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getRepairResult(String type, String keyword);
	
	/**
	 * 내부수리기사 점검내역 검색 조회
	 * @param engineerId 내부수리기사 아이디
	 * @param startDate 검색기간 시작일
	 * @param endDate 검색기간 마지막일
	 * @param repairSort 점검분류
	 * @return 점검내역 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> getRepairResult(String engineerId,String startDate, String endDate, String repairSort);
	
	/* ======================================== by 이원호 ================================================= */
	/**
	 * 렌탈 회수로 인한 종료일 갱신
	 * @param buyId 구매ID
	 * @param date 회수 일자
	 * @return int 성공 1, 실패 0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int setBuyEnd(String buyId, String date);
	
	/**
	 * 렌탈 상담시 조회한 품목 수량 가져오기
	 * @param itId 품목ID
	 * @param itName 품목Name
	 * @return map 조회한 품목의 수량
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> getProductCount(String itId, String itName);
}
