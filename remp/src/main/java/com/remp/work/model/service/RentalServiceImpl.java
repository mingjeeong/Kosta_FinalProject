package com.remp.work.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remp.work.model.dao.AssetDao;
import com.remp.work.model.dao.ItemsDao;
import com.remp.work.model.dao.RentalDao;
import com.remp.work.model.dto.Deprive;
import com.remp.work.model.dto.Item;

/**
 * <p>
 * 렌탈관리를 위한 ServiceImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Service("RentalService")
public class RentalServiceImpl implements RentalService {
	private RentalDao rentalDao;
	private AssetDao assetDao;
	private ItemsDao itemDao;
	
	@Autowired
	public void setRentalDao(RentalDao rentalDao) {
		this.rentalDao = rentalDao;
	}
	@Autowired
	public void setAssetDao(AssetDao assetDao) {
		this.assetDao = assetDao;
	}
	@Autowired
	public void setItemDao(ItemsDao itemDao) {
		this.itemDao = itemDao;
	}
	
	/* ======================================== by 이동훈 ================================================= */
	@Override
	public String setAdvice(Map<String, String> map) {
		String result = null;
		if(map.get("adSort").equals("d")) {
			map.put("vi_code", "Refund");
			int asResult = rentalDao.insertAdvice(map);
			int visitResult = rentalDao.insertVisit(map);
			if(asResult != 0 && visitResult !=0) {
				result = "1";
			}
		} else if(map.get("adSort").equals("c")) {
			map.put("vi_code", "As");
			int asResult = rentalDao.insertAdvice(map);
			int visitResult = rentalDao.insertVisit(map);
			if(asResult != 0 && visitResult !=0) {
				result = "1";
			}
		}
		return result;
	}

	/* ======================================== by 이원호 ================================================= */
	@Override
	public List<Map<String, String>> getVisitSearchList(String state, String select, String search) {
		return rentalDao.selectVisitSearchList(state, select, search);
	}
	
	/* DetailFront 전체정보 가져오기 */
	@Override
	public Map<String, String> getBuyList(String buyId, String visitId) {
		Map<String,String> rentMap = rentalDao.selectBuyDay(buyId);		// DetailFront 렌탈기간
		Map<String, String> itemMap = assetDao.selectItemName(buyId);  // DetailFront 자산정보
		Map<String, String> visitMap = assetDao.selectVisitInfo(visitId);		// DetailFront 고객정보
		
		Map<String, String> map = new HashMap<>();
		if (visitMap.get("viComplete").equals("Y") || visitMap.get("viComplete").equals("y")) {
			Map<String,String> outId = assetDao.selectOutderId(visitMap.get("viCode"), visitId);
			if ((outId != null) && (!outId.isEmpty())){
				map.put("outId", outId.get("outId"));
				map.put("outState", outId.get("outState"));
				map.put("outContent", outId.get("outContent"));
				map.put("outSort", outId.get("outSort"));
				map.put("outDay", outId.get("outDay"));
			}
		}
		
		if ((visitMap != null) && (!visitMap.isEmpty()) && (itemMap != null) && (!itemMap.isEmpty()) && (rentMap != null) && (!rentMap.isEmpty())) {
			map.put("buyStart", rentMap.get("buyStart"));
			map.put("buyEnd", rentMap.get("buyEnd"));
			map.put("itName", itemMap.get("itName"));
			map.put("itCode", itemMap.get("itCode"));
			map.put("prId", itemMap.get("prId"));
			map.put("prManufacturer", itemMap.get("prManufacturer"));
			map.put("cuId", visitMap.get("cuId"));
			map.put("viId", visitId);
			map.put("viName", visitMap.get("viName"));
			map.put("viDay", visitMap.get("viDay"));
			map.put("viMobile", visitMap.get("viMobile"));
			map.put("viPost", visitMap.get("viPost"));
			map.put("viAddr", visitMap.get("viAddr"));
			map.put("viAddD", visitMap.get("viAddD"));
			map.put("viComplete", visitMap.get("viComplete"));
		}
		return map;
	}
	
	// 회수결과 등록
	@Override
	public boolean addRefundResult(Deprive depriveDto, String buyId) {
		if (rentalDao.insertRefundResult(depriveDto) == 1) {
			if (assetDao.updateBuyEnd(buyId, depriveDto.getDay()) == 1) {
				if (assetDao.updateState(depriveDto.getState(), buyId) == 1) {
					if (assetDao.updateViComplete(depriveDto.getViId()) == 1) {
						if (rentalDao.insertRefundInput(depriveDto) == 1) {
							System.out.println("입고 등록 완료");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	// 회수결과 변경
	@Override
	public boolean setRefundResult(Deprive depriveDto, String buyId) {
		if (rentalDao.updateRefundResult(depriveDto) == 1) {
			if (assetDao.updateBuyEnd(buyId, depriveDto.getDay()) == 1) {
				if (assetDao.updateState(depriveDto.getState(), buyId) == 1) {
					if (assetDao.updateViComplete(depriveDto.getViId()) == 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	// 상담 아이디 부여 및 입력
	@Override
	public String getAdviceId(String employeeId) {
		String adviceId = rentalDao.selectAdviceId();
		if ((adviceId != null) && (adviceId.length() > 0)) {
			int result = rentalDao.insertAdvice(adviceId, employeeId);
			if (result == 1) {
				return adviceId;
			}
		}
		return null;
	}
	
	// 일반 상담
	@Override
	public int addNomalAdvice(Map<String, String> nomalInfo) {
		return rentalDao.insertAdvice(nomalInfo);
	}
	
	// 렌탈 상담
	@Override
	public int addRentalAdvice(Map<String, String> rentalInfo) {
		String buyId = rentalDao.selectBuyId();
		if(buyId != null) {
			rentalInfo.put("sb_item", buyId);
			int adviceResult = rentalDao.insertAdvice(rentalInfo);
			if (adviceResult == 1) {
				int outputResult = rentalDao.insertOutput(rentalInfo);
				if (outputResult == 1) {
					return rentalDao.insertBuyAdvice(rentalInfo);
				}
			}
		}
		return 0;
	}
	
	/* ======================================== by 이민정 ================================================= */
	public ArrayList<Item> getItemList(){
		return itemDao.getItemList();
	}
	
	public ArrayList<Item> getSearchList(String sb_search, String item){
		return itemDao.getSearchList(sb_search, item);
	}
	
	public Item getItem(String itemId) {
		return itemDao.getItem(itemId);
	}
	
}
