package com.remp.work.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Product;

/**
 * <p>
 * 자산관리를 위한 Dao
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
public interface AssetDao {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * as결과 신규등록
	 * @param map as요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int insertFix(Map<String, String> map);
	
	/**
	 * as결과 갱신
	 * @param map as요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateFix(Map<String, String> map);
	
	/**
	 * 조회된 리스트에서 해당 항목의 값 가져와 상세 페이지에 뿌려줌
	 * @param buyId 구매ID
	 * @return map 검색된 값(자산정보)
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectItemName(String buyId);
	
	/**
	 * 조회된 리스트에서 해당 항목의 값 가져와 상세 페이지에 뿌려줌
	 * @param visitId 방문ID
	 * @return map 검색된 값(고객정보)
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectVisitInfo(String visitId);
	
	/**
	 * as 및 회수에 따른 자산테이블 갱신
	 * @param state 요청상태
	 * @param buyId 구매ID
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateState(String state, String buyId);
	
	/**
	 * as요청에서 회수처리 시 입고테이블 정보 갱신
	 * @param map 갱신정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateInState(Map<String, String> map);
	
	/**
	 * asID or 회수ID 가져오긴
	 * @param viCode 방문코드
	 * @param visitId 방문ID
	 * @return map 해당 정보 값
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectOutderId(String viCode, String visitId);

	/**
	 * 신규삽입 시 방문테이블 완료여부 갱신
	 * @param viId 방문ID
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateViComplete(String viId);
	
	/**
	 * 자산관리 전체 및 검색조회
	 * @param keyword 검색키워드
	 * @return list 검색된 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectProduct(String keyword);

	/**
	 * 자산관리 상세조회
	 * @param prId 자산ID
	 * @param itId 품목ID
	 * @return map 해당 자산의 상세정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectProductUpdate(String prId, String itId);
	
	/**
	 * 자산 상세조회 (품목테이블)
	 * @param itId 품목ID
	 * @return map 해당 자산의 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectItemUpdate(String itId);
	
	/**
	 * 자산변경하기(개별)
	 * @param map 요청정보
	 * @return int 성공 1,  실패  0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateProductUpdate(Map<String, String>map);
	
	/**
	 * 자산변경하기(전체)
	 * @param map 요청정보
	 * @return int 성공 1,  실패  0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateProductUpdateAll(Map<String, String>map);
	
	/**
	 * 자산등록할 리스트 조회
	 * @return list 자산의 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectProductInsertRequest();
	
	/**
	 * 자산등록 정보폼 가져오기(자산)
	 * @param prId 자산ID
	 * @return map 검색된 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectProductInsertPr(String prId);
	
	/**
	 * 자산등록 정보폼 가져오기(품목)
	 * @param itId 품목ID
	 * @return map 검색된 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectProductInsertIt(String itId);
	
	/**
	 * 자산등록하기(자산)
	 * @param map 요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateProductInsertPr(Map<String, String> map);
	
	/**
	 * 자산등록하기(품목)
	 * @param map 요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int updateProductInsertIt(Map<String, String> map);
	
	/* ======================================== by 김재림 ================================================= */
	
	/** 자산상태 변경 공통
	 * @param assetState 변경할 자산상태
	 * @param assetId 변경할 자산
	 * @return 수행 라인수
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int updateAssetState(String assetState, String assetId);
	
	/**
	 * 품목별 자산 리스트 조회
	 * @param assetState 자산상태
	 * @param itemId 품목아이디
	 * @return 자산리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectAssetList(String assetState, String itemId);
	
	/**
	 * 요청별 자산리스트 조회
	 * @param assetState 자산상태
	 * @return 자산리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectRequestAssetList(String assetState);
	
	/**
	 * 자산검색기능
	 * @param assetState 자산상태
	 * @param keyword 검색어
	 * @return 자산리스트
	 * @deprecated
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectRequestSearchAssetList(String assetState, String keyword);
	
	/**
	 * 출고기능
	 * @param info 출고필요정보
	 * @return 출고한 갯수
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int updateUnstore(Map<String, String> info);
	
	/**
	 * 렌탈확정기능
	 * @param info 렌탈확정 필요정보
	 * @return 확정 갯수
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	public int updateAssetRentalOut(Map<String, String> info);
	
	/* ======================================== by 이민정 ================================================= */
	/**
	 * 입고요청 자산조회
	 * @param assetState 입고요청 자산상태
	 * @return 입고요청조회 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectInputRequest(String assetState); 
	
	/**
	 * 입고요청 등록
	 * @param inputId 입고아이디
	 * @param assetState 입고자산상태
	 * @return 성공 1 실패 0
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int updateInputState(String inputId, String assetState); 
	
	/**
	 * 입고요청 자산 검색
	 * @param assetState 자산상태
	 * @param productName 검색어
	 * @return 검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectInputRequest(String assetState, String productName); 
	
	/**
	 * 입고조회
	 * @param assetState 입고자산상태
	 * @return 입고 조회결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectInputList(String assetState); 
	
	/**
	 * 입고 검색 조회
	 * @param assetState 입고자산 상태
	 * @param productName 검색어
	 * @return 입고 검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectInputSearch(String assetState, String productName);
	
	/**
	 * 점검대기 조회
	 * @return 점검대기 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public ArrayList<Product> selectRepairList();
	
	/**
	 * 점검대기 검색 조회
	 * @param keyword 검색어
	 * @param selectName 검색카테고리
	 * @return 점검대기 검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectRepairList(String keyword, String selectName) ; 
	
	/**
	 * 점검헤드에서 디테일로 가져오기
	 * @param productId 자산아이디
	 * @param productState 자산상태
	 * @return 헤드의 정보 map
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectRepairForm(String productId, String productState) ;
	
	/**
	 * 내부수리 해당 품목에 부품 조회
	 * @param itemId 품목아이디
	 * @return 부품 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectRepairPartsList(String itemId) ; 
	
	/**
	 * 내부수리기사 점검 후 점검테이블에 점검내역 등록
	 * @param formInput 폼내역
	 * @param partsInput 부품폼내역
	 * @return 쿼리반환 여부에 따라 int 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int insertRepairResult(Map<String, Object> formInput, Map<String, String> partsInput) ;
	
	/**
	 * 내부수리기사 점검 후 자산테이블에 자산상태 변경
	 * @param productId 자산아이디
	 * @param repairSort 점검분류
	 * @return 성공 시 1, 실패 시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int updateProductState(String productId, String repairSort) ;
	
	/*부속품*/
	/**
	 * 모든 부품리스트 조회
	 * @return 모든 부품리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectAllParts() ;
	
	/**
	 * 부품검색
	 * @param type 검색카테고리
	 * @param keyword 검색어
	 * @return 부품검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String, String>> selectPartsList(String type, String keyword);
	
	/*내부수리 결과*/
	/**
	 * 모든 점검결과 리스트 조회
	 * @param id 내부수리기사 아이디
	 * @return 모든 점검결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public List<Map<String,String>> selectAllRepairResult(String id) ;
	
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
	public List<Map<String, String>> selectRepairResult(String engineerId, String startDate, String endDate, String repairSort);
	
	/*렌탈구매*/
	/**
	 * 고객 렌탈 구매
	 * @param loginId 고객아이디
	 * @param rentalInfo 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int insertCustomerBuy(String loginId, Map<String,String> rentalInfo) ;
	
	/**
	 * 고객 렌탈 구매하여 출고
	 * @param adviceInfo 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int insertCustomerBuyOutput(Map<String, String> adviceInfo) ;
	
	/**
	 * 자산 등록
	 * @return 성공 시 1, 실패시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int insertProduct();
	/**
	 * 자산상태 변경
	 * @param inputId 입고아이디
	 * @param assetState 자산상태
	 * @return 성공 시 1, 실패시 0 반환
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public int updateProduct(String inputId, String assetState);		
	
	/* ======================================== by 이원호 ================================================= */
	/**
	 * 렌탈 회수로 인한 종료일 갱신
	 * @param buyId 구매Id
	 * @param date 회수 일자
	 * @return 성공1 실패0
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public int updateBuyEnd(String buyId, String date);
	
	/**
	 * 렌탈 상담시 조회한 품목 수량 가져오기
	 * @param itId 품목ID
	 * @param itName 품목Name
	 * @return 해당 물품의 상태별 수량
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public Map<String, String> selectCount(String itId, String itName);
}
 
