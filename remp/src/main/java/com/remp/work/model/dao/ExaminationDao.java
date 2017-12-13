package com.remp.work.model.dao;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 자산 실사를 위한 Dao
 * </p>
 * @author 김재림
 * @since JDK 1.8 이후
 * @version ReMP v 1.0
 */
public interface ExaminationDao {
	
	/**
	 * 실사계획 변경
	 * @param info 변경정보
	 * @return 변경 결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	int updateDueDiligencePlan(Map<String, String> info);

	/**
	 * 실사계획 검색
	 * @param keyword 검색어
	 * @return 실사계획 리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	List<Map<String, String>> selectDueDiligencePlanList(String keyword);
	
	/**
	 * 실사계획 등록
	 * @param info 실사계획 정보
	 * @return 등록 수행결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	int insertDueDiligencePlan(Map<String, String> info);
	
}