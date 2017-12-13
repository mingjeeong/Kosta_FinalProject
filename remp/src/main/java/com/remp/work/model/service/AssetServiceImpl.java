package com.remp.work.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remp.work.model.dao.AssetDao;
import com.remp.work.model.dao.BuyDao;
import com.remp.work.model.dao.ExaminationDao;
import com.remp.work.model.dto.Product;

/**
 * <p>
 * 자산관리를 위한 ServiceImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Service("AssetService")
public class AssetServiceImpl implements AssetService {
	private AssetDao assetDao;
	private BuyDao buyDao;
	private ExaminationDao examinationDao;
	
	@Autowired
	public void setAssetDao(AssetDao assetDao) {
		this.assetDao = assetDao;
	}
	@Autowired
	public void setBuyDao(BuyDao buyDao) {
		this.buyDao = buyDao;
	}
	@Autowired
	public void setExaminationDao(ExaminationDao examinationDao) {
		this.examinationDao = examinationDao;
	}

	/* ======================================== by 이동훈 ================================================= */
	@Override
	public int addFix(Map<String, String> map) {
		int result = assetDao.insertFix(map);
		return result;
	}

	@Override
	public int setFix(Map<String, String> map) {
		int result = assetDao.updateFix(map);
		return result;
	}

	@Override
	public int setState(String state, String buyId) {
		int result = assetDao.updateState(state, buyId);
		return result;
	}
	
	@Override
	public int setInState(Map<String, String> map) {
		int result = assetDao.updateInState(map);
		return result;
	}

	@Override
	public Map<String, String> getVisitInfo(String visitId) {
		Map<String, String> map = assetDao.selectVisitInfo(visitId);
		return map;
	}
	
	@Override
	public List<Map<String, String>> getProduct(String keyword) {
		List<Map<String, String>> list = new ArrayList<>();
		String kName = "";
		list = assetDao.selectProduct(keyword);
		for(int i=0; i<list.size(); i++) {
			String state = list.get(i).get("prState");
			switch(state) {
				case "re_ninput" :
					kName = "신규입고요청";
					list.get(i).put("prState", kName);
					break;
				case "re_npart" :
					kName = "신규수리부속요청";
					list.get(i).put("prState", kName);
					break;
				case "re_nother" :
					kName = "신규기타자산입고요청";
					list.get(i).put("prState", kName);
					break;
				case "re_exinput" :
					kName = "외부수리입고요청";
					list.get(i).put("prState", kName);
					break;
				case "re_output" :
					kName = "렌탈출고요청";
					list.get(i).put("prState", kName);
					break;
				case "re_exoutput" :
					kName = "외부수리출고요청";
					list.get(i).put("prState", kName);
					break;
				case "re_reinput" :
					kName = "회수입고요청";
					list.get(i).put("prState", kName);
					break;
				case "re_disuse" :
					kName = "폐기요청";
					list.get(i).put("prState", kName);
					break;
				case "re_repair" :
					kName = "수리요청";
					list.get(i).put("prState", kName);
					break;
				case "re_return" :
					kName = "회수요청";
					list.get(i).put("prState", kName);
					break;
				case "wa_ninput" :
					kName = "등록대기";
					list.get(i).put("prState", kName);
					break;
				case "wa_policy" :
					kName = "정책대기";
					list.get(i).put("prState", kName);
					break;
				case "wa_product" :
					kName = "영업대기";
					list.get(i).put("prState", kName);
					break;
				case "wa_check" :
					kName = "점검대기";
					list.get(i).put("prState", kName);
					break;
				case "wa_repair" :
					kName = "수리대기";
					list.get(i).put("prState", kName);
					break;
				case "do_product" :
					kName = "영업중";
					list.get(i).put("prState", kName);
					break;
				case "do_disuse" :
					kName = "폐기";
					list.get(i).put("prState", kName);
					break;
				default :
					kName = "신규입고요청";
					list.get(i).put("prState", kName);
					break;
			}
		}
		return list;
	}
	
	@Override
	public Map<String, String> getProductUpdate(String prId, String itId) {
		Map<String, String> prMap = new HashMap<>();
		Map<String, String> itMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		prMap = assetDao.selectProductUpdate(prId, itId);
		itMap = assetDao.selectItemUpdate(itId);
		if ((prMap != null) && (!prMap.isEmpty()) && (itMap != null) && (!itMap.isEmpty())) {
			map.put("prId", prMap.get("prId"));
			map.put("prInDay", prMap.get("prInDay"));
			map.put("prOutDay", prMap.get("prOutDay"));
			map.put("prState", prMap.get("prState"));
			map.put("prOutTarget", prMap.get("prOutTarget"));
			map.put("prLocation", prMap.get("prLocation"));
			map.put("prCount", prMap.get("prCount"));
			map.put("prNeeds", prMap.get("prNeeds"));
			map.put("itId", itMap.get("itId"));
			map.put("itName", itMap.get("itName"));
			map.put("itCode", itMap.get("itCode"));
			map.put("itManufacturer", itMap.get("itManufacturer"));
			map.put("itAcquisition", itMap.get("itAcquisition"));
			map.put("itPrice", itMap.get("itPrice"));
			map.put("itContent", itMap.get("itContent"));
			map.put("itImage", itMap.get("itImage"));
			map.put("itPeriod", itMap.get("itPeriod"));
		}
		return map;
	}
	
	@Override
	public String setProductUpdate(Map<String, String>map) {
		int prResult = assetDao.updateProductUpdate(map);
		String result = null;
		if(prResult == 1) {
			result = "1";
		}
		return result;
	}
	
	@Override
	public String setProductUpdateAll(Map<String, String>map) {
		int prResult = assetDao.updateProductUpdate(map);
		int itResult = assetDao.updateProductUpdateAll(map);
		String result = null;
		if(prResult !=0 && itResult !=0) {
			result = "1";
		}
		return result;
	}
	
	@Override
	public List<Map<String, String>> getProductInsertRequest() {
		return assetDao.selectProductInsertRequest();
	}
	
	@Override
	public Map<String, String> getProductInsert(String prId) {
		Map<String, String> map = new HashMap<>();
		Map<String, String> prMap = assetDao.selectProductInsertPr(prId);
		if(!prMap.isEmpty() && prMap != null) {
			map.put("prId", prMap.get("prId"));
			map.put("prFirstDay", prMap.get("prFirstDay"));
			map.put("prInDay", prMap.get("prInDay"));
			map.put("prOutDay", prMap.get("prOutDay"));
			map.put("prOutTarget", prMap.get("prOutTarget"));
			map.put("prLocation", prMap.get("prLocation"));
			map.put("prState", prMap.get("prState"));
			map.put("prCount", prMap.get("prCount"));
			map.put("prQr", prMap.get("prQr"));
			map.put("itId", prMap.get("itId"));
			if(prMap.get("itId") != null && !prMap.get("itId").isEmpty()){
				Map<String, String> itMap = assetDao.selectProductInsertIt(prMap.get("itId"));
				map.put("itName", itMap.get("itName"));
				map.put("itCode", itMap.get("itCode"));
				map.put("itPrice", itMap.get("itPrice"));
				map.put("itAcquisition", itMap.get("itAcquisition"));
				map.put("itPeriod", itMap.get("itPeriod"));
				map.put("itManufacturer", itMap.get("itManufacturer"));
				map.put("itContent", itMap.get("itContent"));
				map.put("itImage", itMap.get("itImage"));
			}
		}
		return map;
	}
	
	@Override
	public String setProductInsert(Map<String, String> map) {
		int setProduct = assetDao.updateProductInsertPr(map);
		int setItem = assetDao.updateProductInsertIt(map);
		String result = null;
		if(setProduct ==1 && setItem == 1) {
			result = "1";
		}
		return result;
	}
	
	/* ======================================== by 김재림 ================================================= */
	@Override
	public int setAssetRentalOut(Map<String, String> info) {
		return assetDao.updateAssetRentalOut(info);
	}

	@Override
	public List<Map<String, String>> getAssetList(String itemId) {
		return assetDao.selectAssetList("wa_product", itemId);
	}

	@Override
	public List<Map<String, String>> getRentalRequestList(String keyword) {
		return buyDao.selectRentalRequestList(keyword);
	}

	@Override
	public List<Map<String, String>> getRequestAssetList(String assetState) {
		return assetDao.selectRequestAssetList(assetState);
	}

	@Override
	public List<Map<String, String>> getRequestSearchAssetList(String assetState, String keyword) {
		return assetDao.selectRequestSearchAssetList(assetState, keyword);
	}

	@Override
	public int setDueDiligencePlan(Map<String, String> info) {
		return examinationDao.updateDueDiligencePlan(info);
	}

	@Override
	public List<Map<String, String>> getDueDiligencePlanList(String keyword) {
		return examinationDao.selectDueDiligencePlanList(keyword);
	}

	@Override
	public int newDueDiligencePlan(Map<String, String> jsonToMap) {
		return examinationDao.insertDueDiligencePlan(jsonToMap);
	}

	@Override
	public int setUnstore(Map<String, String> jsonToMap) {
		return assetDao.updateUnstore(jsonToMap);
	}

	
	/* ======================================== by 이민정 ================================================= */
	@Override
	public List<Map<String, String>> getInputRequest(String assetState){
		return assetDao.selectInputRequest(assetState);
	}
	
	@Override
	public int setInputState(String inputId, String assetState){
		return assetDao.updateInputState(inputId, assetState);
	}
	
	@Override
	public List<Map<String,String>> searchInputRequest(String assetState, String productName){
		return assetDao.selectInputRequest(assetState, productName);
	}
	
	@Override
	public List<Map<String, String>> getInput(String assetState){
		return assetDao.selectInputList(assetState);
	}
	
	@Override
	public List<Map<String,String>> searchInput(String assetState, String productName){
		return assetDao.selectInputSearch(assetState, productName);
	}
	
	@Override
	public ArrayList<Product> getRepairList(){
		ArrayList<Product> list = assetDao.selectRepairList();
		for(int i = 0 ; i < list.size() ; i++) {
			switch (list.get(i).getState()) {
			case "wa_check":
				list.get(i).setState("점검대기");
				break;
			case "wa_product":
				list.get(i).setState("내부수리완료");
				break;
			case "wa_repair":
				list.get(i).setState("수리대기");
				break;
			case "re_exoutput":
				list.get(i).setState("외부수리");
				break;
			case "re_disuse":
				list.get(i).setState("수리불가능");
				break;
			default :
			}
		}
		return list;
	}
	
	@Override
	public List<Map<String,String>> searchRepairList(String keyword, String selectName){
		List<Map<String,String>> list = assetDao.selectRepairList(keyword,selectName);
		for (Map<String,String> map : list) {
			switch (map.get("state")) {
			case "wa_check":
				map.put("state", "점검대기");
				break;
			case "wa_product":
				map.put("state", "내부수리완료");
				break;
			case "wa_repair":
				map.put("state", "수리대기");
				break;
			case "re_exoutput":
				map.put("state", "외부수리");
				break;
			case "re_disuse":
				map.put("state", "수리불가능");
				break;
			default:
			}
		}
		return list;
	}
	
	@Override
	public Map<String, String> getRepairForm(String productId, String productState){
		switch(productState) {
		case "점검대기":
			productState = "wa_check";
			break;
		case "수리대기":
			productState = "wa_repair";
			break;
		default :
		}
		return assetDao.selectRepairForm(productId, productState);
	}
	
	@Override
	public List<Map<String,String>> getRepairPartsList(String itemId){
		return assetDao.selectRepairPartsList(itemId );
	}
	
	@Override
	public int updateProductState(String productId, String repairSort) {
		return assetDao.updateProductState(productId, repairSort);
	}
	@Override
	public List<Map<String, String>> getAllParts() {
		return assetDao.selectAllParts();
	}
	
	@Override
	public List<Map<String, String>> getAllRepairResult(String engineerId) {
		return assetDao.selectAllRepairResult(engineerId);
	}
	
	@Override
	public int insertCustomerBuy(String loginId, Map<String, String> rentalInfo) {
		return assetDao.insertCustomerBuy(loginId, rentalInfo);
	}
	
	@Override
	public List<Map<String, String>> getRepairResult(String type, String keyword){
		return assetDao.selectPartsList(type,keyword);
	}
	
	@Override
	public List<Map<String, String>> getRepairResult(String engineerId,String startDate, String endDate, String repairSort){
		return assetDao.selectRepairResult(engineerId, startDate, endDate, repairSort);
	}
	
	@Override
	public int insertOutput(Map<String, String> map) {
		return assetDao.insertCustomerBuyOutput(map);
	}

	@Override
	public int addRepairResult(Map<String, Object> formInput, Map<String, String> partsInput) {
		return assetDao.insertRepairResult(formInput, partsInput);
	}
	
	/* ======================================== by 이원호 ================================================= */
	@Override
	public int setBuyEnd(String buyId, String date) {
		return assetDao.updateBuyEnd(buyId, date);
	}
	
	@Override
	public Map<String, String> getProductCount(String itId, String itName) {
		return assetDao.selectCount(itId, itName);
	}
}
