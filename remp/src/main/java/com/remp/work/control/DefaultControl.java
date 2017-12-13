package com.remp.work.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.remp.work.model.dto.Deprive;
import com.remp.work.model.dto.Employee;
import com.remp.work.model.dto.Item;
import com.remp.work.model.dto.Product;
import com.remp.work.model.service.AssetService;
import com.remp.work.model.service.CustomerService;
import com.remp.work.model.service.MemberService;
import com.remp.work.model.service.RentalService;
import com.remp.work.model.service.SMTPAuthenticatior;
import com.remp.work.model.service.SmsService;
import com.remp.work.util.RempUtility;

/**
 * <p>
 * 기본 UI를 구현한 컨트롤러
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 * @see <a href = "http://spring.io">Spring Framework</a>
 */
@Controller
public class DefaultControl extends ControllerAdapter {

	private CustomerService customerService;
	private MemberService memberService;
	private AssetService assetService;
	private RentalService rentalService;
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	@Autowired
	public void setAssetService(AssetService assetService) {
		this.assetService = assetService;
	}
	@Autowired
	public void setRentalService(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@Override
	public ModelAndView home() {
		return getPlainPage("gistMain.jsp");
	}
	
	/* ============================================== by 이동훈 ============================================ */
	/**
	 * 비밀번호 찾기페이지 이동
	 * @return 해당 페이지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goFindPw.do")
	public ModelAndView goSelectPw() {
		return getPlainRedPage("findPw.jsp");
	}
	
	/**
	 * 상담페이지 이동
	 * @return 해당 페이지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goAdvice.do")
	public ModelAndView goAdvice() {
		return getPlainPage("rentAdvice.jsp");
	}
	
	/**
	 * 자산관리 페이지 이동
	 * @return 해당 페이지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goProductCare.do")
	public ModelAndView goProductCare() {
		return getPlainPage("productCare.jsp");
	}
	
	/**
	 * 자산등록 페이지 이동
	 * @return 해당 페이지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goProductInsert.do")
	public ModelAndView goProductInsert() {
		return getHeadDetailPage("productRequestList.jsp", "productInsert.jsp");
	}

	/**
	 * 회원의 비밀번호를 찾는 메소드. 이메일로 발송
	 * @param tb_id 회원의 id
	 * @param tb_name 회원의 name
	 * @param tb_birth 회원의 birth
	 * @param tb_mobile 회원의 mobile
	 * @return 해당 페이지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("selectPw.do")
	public ModelAndView getPw(String tb_id, String tb_name, String tb_birth, String tb_mobile) {
		ModelAndView mav = new ModelAndView();	
		String tmpPw = "";
		String from = "leedh93@hanmail.net";
		String result = customerService.getPw(tb_id, tb_name, tb_birth, tb_mobile);
		if(result != null) {
			String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		    for( int i=0; i < 8; i++ ) {
		    	tmpPw += possible.charAt((int) Math.floor(Math.random() * possible.length()));
		    }
			customerService.setTmpPw(tb_id, tmpPw);
			mav.addObject("tmpPw", tmpPw);
			mav.addObject("to", result);
			mav.addObject("from", from);
		}else {
			return getErrorPage(mav, "requestError.jsp");
		}
		return getPlainRedPage(mav, "tmpPw.jsp");
	}
	
	/**
	 * 내부 사용자의 정보를 가져오는 메소드
	 * @param id 해당하는 사용자의 id
	 * @param session 현재 세션
	 * @return 해당 페이지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goSetMemberInfo.do")
	public ModelAndView getMemberInfo(String id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		id = (String)session.getAttribute("loginId");
		Employee emp = new Employee();
		emp = memberService.getMemberInfoSub(id);
		if(emp != null) {
			mav.addObject("emp", emp);	
		}else {
			return getErrorPage(mav, "requestError.jsp");
		}
		return getPlainPage(mav, "memberInfo.jsp");
	}
	
	/**
	 * 내부 사용자의 정보를 수정하는 메소드
	 * @param map 요청정보
	 * @param session 해당 세션
	 * @return 내부 사용자의 수정된 정보 폼
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("updateMemberInfo.do")
	public ModelAndView setMemeberInfo(@RequestParam Map<String, String> map, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String id = map.get("tb_id");
		int result = 0;
		result = memberService.setMemberInfoSub(map);
		if(result == 1) {
			return getMemberInfo(id, session);
		}
		return getErrorPage(mav, "requestError.jsp");
	}
	
	/**
	 * as 및 회수요청 목록 조회
	 * @param jsonObjectString 요청정보 json객체
	 * @return as 및 회수요청목록
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getVisitRequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getVisitRequest(@RequestBody String jsonObjectString) {
		Map<String, String> map = jsonToMap(jsonObjectString);
		return rentalService.getVisitSearchList(map.get("pagetype"), map.get("select"), map.get("keyword"));
	}
	
	/**
	 * as결과를 등록 및 수정하는 메소드
	 * @param jsonObjectString 요청정보 json객체
	 * @param session 해당세션
	 * @return 등록 및 수정된 as요청결과
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setFixInfo.do", method=RequestMethod.POST)
	public @ResponseBody String addFixInfo(@RequestBody String jsonObjectString, HttpSession session) {
		String total = null;
		Map<String, String> map = jsonToMap(jsonObjectString);
		String engineerId = (String)session.getAttribute("loginId");
		String engineerName = (String)session.getAttribute("loginName");
		String viDay = "";
		if (map.get("tb_viDay") != null && map.get("tb_viDay").trim().length() > 0) {
			viDay = map.get("tb_viDay").trim().substring(0,10) + map.get("tb_viDay").trim().substring(11, 16);
		}
		map.put("hi_outDay", viDay);
		map.put("engineerId", engineerId);
		map.put("engineerName", engineerName);
		int result = 0;
		if(map.get("hi_outId") == null || map.get("hi_outId").trim().equals("")) {
			result = assetService.addFix(map);
		} else {
			result = assetService.setFix(map);
		}
		if(result != 0) {
			int update = setPrState(map.get("hi_outState"), map.get("hi_buyId"));
			if(update !=0) {
				if(map.get("hi_outState").equals("re_return")) {
					int inUpdate = setInState(map);
					if(inUpdate !=0) total = "success";
				}
			} 
			return total;
		} else {
			return total;
		}
	}
	
	/**
	 * as상태 갱신 시 자산테이블 상태 갱신하는 메소드
	 * @param state 자산의 상태
	 * @param prId 자산ID
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int setPrState(String state, String prId) {
		int result = assetService.setState(state, prId);
		return result;
	}
	
	/**
	 * as상태 갱신 시 입고테이블 갱신하는 메소드
	 * @param map 요청정보
	 * @return int 성공 1, 실패 0
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	public int setInState(Map<String, String> map) {
		int result = assetService.setInState(map);
		return result;
	}
	
	/**
	 * as페이지 이동
	 * @return 해당 페이지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goAsRequest.do")
	public ModelAndView goAsRequest() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("state", "As");
		return getHeadDetailPage(mav, "rentalVisitList.jsp", "rentalAsVisit.jsp");
	}

	/**
	 * as 및 회수요청 리스트 가져오기
	 * @param jsonObjectString 요청정보 json객체
	 * @return as 및 회수요청의 리스트
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getVisitList.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> getVisitList(@RequestBody String jsonObjectString) {
		return rentalService.getBuyList(jsonToMap(jsonObjectString).get("productId"), jsonToMap(jsonObjectString).get("visitId"));
	}

	/**
	 * 자산관리 전체 및 검색조회
	 * @param jsonObjectString 요청정보 json객체
	 * @return 자산관리 전체 및 검색된 결과 조회
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getProductRequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getProductRequest(@RequestBody String jsonObjectString) {
		return assetService.getProduct(jsonToMap(jsonObjectString).get("keyword"));
	}
	
	/**
	 * 자산관리 갱신정보 가져오기
	 * @param jsonObjectString 요청정보 json객체
	 * @return 갱신된 자산의 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getProductUpdate.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> getProductUpdate(@RequestBody String jsonObjectString) {
		return assetService.getProductUpdate(jsonToMap(jsonObjectString).get("prId"), jsonToMap(jsonObjectString).get("itId"));
	}
	
	/**
	 * 자산관리 정보변경(개별)
	 * @param jsonObjectString 요청정보 json객체
	 * @return 확인메시지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setProductUpdate.do", method=RequestMethod.POST)
	public @ResponseBody String setProductUpdate(@RequestBody String jsonObjectString) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
    	String prInDay = "";
		if (map.get("tb_prInDay") != null && map.get("tb_prInDay").trim().length() > 0) {
			prInDay = map.get("tb_prInDay").trim().substring(0,10) + map.get("tb_prInDay").trim().substring(11, 16);
		}
		String prOutDay = "";
		if (map.get("tb_prOutDay") != null && map.get("tb_prOutDay").trim().length() > 0) {
			prOutDay = map.get("tb_prOutDay").trim().substring(0,10) + map.get("tb_prOutDay").trim().substring(11, 16);
		}
		map.put("tb_prInDay", prInDay);
		map.put("tb_prOutDay", prOutDay);
		return assetService.setProductUpdate(map);
	}
	
	/**
	 * 자산관리 정보변경(전체)
	 * @param jsonObjectString 요청정보 json객체
	 * @return 확인메시지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setProductUpdateAll.do", method=RequestMethod.POST)
	public @ResponseBody String setProductUpdateAll(@RequestBody String jsonObjectString) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
		String prInDay = "";
		if (map.get("tb_prInDay") != null && map.get("tb_prInDay").trim().length() > 0) {
			prInDay = map.get("tb_prInDay").trim().substring(0,10) + map.get("tb_prInDay").trim().substring(11, 16);
		}
		String prOutDay = "";
		if (map.get("tb_prOutDay") != null && map.get("tb_prOutDay").trim().length() > 0) {
			prOutDay = map.get("tb_prOutDay").trim().substring(0,10) + map.get("tb_prOutDay").trim().substring(11, 16);
		}
		map.put("tb_prInDay", prInDay);
		map.put("tb_prOutDay", prOutDay);
		return assetService.setProductUpdateAll(map);
	}

	/**
	 * 자산등록 요청조회
	 * @param jsonObjectString 요청정보 json객체
	 * @return 요청상태가 등록대기인 자산리스트
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getProductInsertRequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getProductInsertRequest(@RequestBody String jsonObjectString) {
		return assetService.getProductInsertRequest();
	}
	
	/**
	 * 자산등록 정보폼 가져오기
	 * @param jsonObjectString 요청정보 json객체
	 * @return 해당 자산에 대한 정보를 가진 폼
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getProductInsert.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> getProductInsert(@RequestBody String jsonObjectString) {
		return assetService.getProductInsert(jsonToMap(jsonObjectString).get("productId"));
	}
	
	/**
	 * 자산등록하기
	 * @param jsonObjectString 요청정보 json객체
	 * @return 확인메시지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setProductInsert.do", method=RequestMethod.POST)
	public @ResponseBody String setProductInsert(@RequestBody String jsonObjectString) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
    	String prFirstDay = "";
		if (map.get("tb_prFirstDay") != null && map.get("tb_prFirstDay").trim().length() > 0) {
			prFirstDay = map.get("tb_prFirstDay").trim().substring(0,10) + map.get("tb_prFirstDay").trim().substring(11, 16);
		}
		String prInDay = "";
		if (map.get("tb_prInDay") != null && map.get("tb_prInDay").trim().length() > 0) {
			prInDay = map.get("tb_prInDay").trim().substring(0,10) + map.get("tb_prInDay").trim().substring(11, 16);
		}
		String prOutDay = "";
		if (map.get("tb_prOutDay") != null && map.get("tb_prOutDay").trim().length() > 0) {
			prOutDay = map.get("tb_prOutDay").trim().substring(0,10) + map.get("tb_prOutDay").trim().substring(11, 16);
		}
		map.put("tb_prFirstDay", prFirstDay);
		map.put("tb_prInDay", prInDay);
		map.put("tb_prOutDay", prOutDay);
		return assetService.setProductInsert(map);
	}
	   
    /**
     * select box 변경 시 기간 값 동적할당
     * @param jsonObjectString 요청정보 json객체
     * @return 변경 된 select box요소
	 * @author 이동훈
	 * @since ReMP v 1.0
     */
    @RequestMapping(value="getChangeInfo.do", method=RequestMethod.POST)
    public @ResponseBody Map<String, String> getChangeInfo(@RequestBody String jsonObjectString) {
    	return customerService.getChangeInfo(jsonToMap(jsonObjectString).get("optionValue"));
    }
    
    /**
     * 상담 자산정보 가져오기
     * @param jsonObjectString 요청정보 json객체
     * @return 해당 자산의 정보
	 * @author 이동훈
	 * @since ReMP v 1.0
     */
    @RequestMapping(value="getItemInfo.do", method=RequestMethod.POST)
	 public @ResponseBody List<Map<String, String>> getItemInfo(@RequestBody String jsonObjectString) {
	     return customerService.getItemInfo(jsonToMap(jsonObjectString).get("memberId"));
	 }
	   
    /**
     * as상담 등록하기
     * @param jsonObjectString 요청정보 json객체
     * @param session 해당 세션
     * @return 확인메시지
	 * @author 이동훈
	 * @since ReMP v 1.0
     */
    @RequestMapping(value="setAsAdvice.do", method=RequestMethod.POST)
	 public @ResponseBody String setAsAdvice(@RequestBody String jsonObjectString, HttpSession session) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
    	String empId = (String) session.getAttribute("loginId");
    	String date = map.get("tb_reDay").trim().substring(0,10) + map.get("tb_reDay").trim().substring(12);
    	map.put("tb_reDay", date);
    	map.put("emId", empId);
        map.put("adSort", "c");
        if(map.get("tb_cuResult").equals("비회원입니다.")) {
        	return null;
        } else {
        	return rentalService.setAdvice(map);
        }
	 }
	   
	 /**
	 * 회수상담 등록하기
	 * @param jsonObjectString 요청정보 json객체
	 * @param session 해당 세션
	 * @return 확인메시지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setRefundAdvice.do", method=RequestMethod.POST)
	 public @ResponseBody String setRefundAdvice(@RequestBody String jsonObjectString, HttpSession session) {
	    Map<String, String> map = jsonToMap(jsonObjectString);
	    String empId = (String) session.getAttribute("loginId");
    	String date = map.get("tb_reDay").trim().substring(0,10) + map.get("tb_reDay").trim().substring(11);
    	map.put("tb_reDay", date);
    	map.put("emId", empId);
    	map.put("adSort", "d");
	   if(map.get("tb_cuResult").equals("비회원입니다.")) {
           	return null;
       } else {
	    return rentalService.setAdvice(map);
       }
	 }
	   
    /**
     * 이미지 업로드
	 * @param request 요청
	 * @param uploadFile 파일
	 * @param obj 객체
	 * @return 확인메시지
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="fileUpload.do", method=RequestMethod.POST)
	   public @ResponseBody String fileUpload(MultipartHttpServletRequest request, MultipartFile uploadFile, Object obj){
		  String path = "";
	      String fileName = "";
	      String prId = request.getParameter("fi_prId");
	       OutputStream out = null;
	       PrintWriter printWriter = null;
	        try {
	           uploadFile = request.getFile("tb_file");
	            fileName = uploadFile.getOriginalFilename();
	            byte[] bytes = uploadFile.getBytes();
	            path = getSaveLocation(request, obj);
	            File file = new File(path);
	            if ((fileName != null) && (!fileName.equals(""))) {
	                if (file.exists()) {
	                    fileName = prId + "_" + fileName;
	                    file = new File(path + fileName);
	                }
	            }
	            out = new FileOutputStream(file);
	            out.write(bytes);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (out != null) {
	                    out.close();
	                }
	                if (printWriter != null) {
	                    printWriter.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	      return path+fileName;
	   }
	   
    /**
     * 이미지 업로드 경로 지정해주는 메소드
	 * @param request 요청
	 * @param obj 객체
	 * @return 실제경로
	 * @author 이동훈
	 * @since ReMP v 1.0
	 */
	private String getSaveLocation(MultipartHttpServletRequest request, Object obj) {
		   String root_path = request.getSession().getServletContext().getRealPath("/");  
	       String attach_path = "resources/images/";
	       String uploadPath = root_path + attach_path;
	        System.out.println("UtilFile getSaveLocation path : " + uploadPath);
	        return uploadPath;
	    }
	
	   /* ======================================== by 김재림 ================================================= */
	/**
	 * 아이디 찾기 페이지 이동
	 * @return 아이디 찾기 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gofindid.do")
	public ModelAndView gofindId() {
		return  getPlainRedPage("findid.jsp");
	}
	
	/**
	 * 아이디 찾기 기능
	 * @param memberinfo 아이디 찾기 데이터 JSON
	 * @return 결과 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="findid.do", method=RequestMethod.POST)
	public ModelAndView findId(@RequestParam HashMap<String, String> memberinfo) {
		ModelAndView returnValue = new ModelAndView();
		String customerId = customerService.getCustomerId(memberinfo);
		returnValue.addObject("customerId", customerId);
		return getPlainRedPage(returnValue, "findidresult.jsp");
	}
	
	/**
	 * 고객 개인정보 변경 페이지 연결
	 * @param session 고객 세션
	 * @return 고객 개인정보 변경 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gouserchange.do")
	public ModelAndView goUserChange(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String id = session.getAttribute("loginId").toString();
		Map<String, String> userinfo = customerService.getUserInfo(id);
		mav.addObject("userInfo", userinfo);
		return getPlainRedPage(mav, "user_change.jsp");
	}
	
	/**
	 * 렌탈 확정 페이지 연결
	 * @return 렌탈 확정 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gorentalasset.do")
	public ModelAndView goRentalAsset() {
		return getHeadDetailPage("rental_request.jsp", "rental_asset_info.jsp");
	}
	
	/**
	 * AJAX 렌탈 요청 조회
	 * @param jsonObjectString 렌탈 정보
	 * @return 렌탈 요청 리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getrentalrequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getRentalRequestList(@RequestBody String jsonObjectString) {
		return assetService.getRentalRequestList(jsonToMap(jsonObjectString).get("keyword"));
	}
	
	/**
	 * AJAX 자산조회 공통
	 * @param jsonObjectString 자산 정보
	 * @return 자산 리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getassetlist.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getAssetList(@RequestBody String jsonObjectString) {
		return assetService.getAssetList(jsonToMap(jsonObjectString).get("productId"));
	}
	
	/**
	 * 출고관리 페이지 연결
	 * @return 출고관리 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gounstoreconfirm.do")
	public ModelAndView goUnstoreConfirm() {
		return getHeadDetailPage("out_request.jsp", "out_asset_info.jsp");
	}
	
	/**
	 * AJAX 출고요청 상태별 자산리스트 조회
	 * @param jsonObjectString 출고상태정보
	 * @return 출고요청 상태별 자산리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getrequestassetlist.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getRequestAssetList(@RequestBody String jsonObjectString) {
		return assetService.getRequestAssetList(jsonToMap(jsonObjectString).get("assetState"));
	}
	
	/**
	 * AJAX 렌탈자산 확정
	 * @param jsonObjectString 자산정보, 요청정보
	 * @return 확정된 렌탈 자산 id
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="rentalassetconfirm.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> rentalAssetConfirm(@RequestBody String jsonObjectString) {
		int result = assetService.setAssetRentalOut(jsonToMap(jsonObjectString));
		Map<String, String> returnValue = isUpdatedToMap(result);
		returnValue.put("id", jsonToMap(jsonObjectString).get("id"));
		return returnValue;
	}
	
	/**
	 * AJAX 자산출고 공통
	 * @param jsonObjectString 출고자산 및 요청정보
	 * @return 출고된 자산 id
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setunstore.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> setUnstore(@RequestBody String jsonObjectString) {
		String id = jsonToMap(jsonObjectString).get("productId");
		Map<String, String> returnValue = isUpdatedToMap(assetService.setUnstore(jsonToMap(jsonObjectString)));
		returnValue.put("id", id);
		return returnValue;
	}
	
	/**
	 * AJAX 자산 정보 검색기능
	 * @param jsonObjectString 검색어
	 * @return 자산 리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getrequestsearchassetlist.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getRequestSearchAssetList(@RequestBody String jsonObjectString) {
		Map<String, String> json = jsonToMap(jsonObjectString);
		return assetService.getRequestSearchAssetList(json.get("assetState"), json.get("keyword"));
	}
	
	/**
	 * 자산 실사계획 페이지 연결
	 * @return 자산 실사계획 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goduediligence.do")
	public ModelAndView goDueDiligence() {
		return getPlainPage("due_diligence_inputform.jsp");
	}
	
	/**
	 * 자산 실사조회 및 변경 페이지 연결
	 * @return 자산 실사조회 및 변경 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gochangeduediligence.do")
	public ModelAndView goChangeDueDiligence() {
		return getHeadDetailPage("due_diligence_list.jsp", "due_diligence_form.jsp");
	}
	
	/**
	 * 자산실사결과 등록 페이지 연결
	 * @return 자산실사결과 등록 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goduediligenceresult.do")
	public ModelAndView goDueDiligenceResult() {
		return getHeadDetailPage("due_diligence_list.jsp", "detail.jsp");
	}
	
	/**
	 * 자산실사 결과조회 및 변경 페이지 연결
	 * @return 자산실사 조회 및 변경 페이지
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gochangeduediligenceresult.do")
	public ModelAndView goChangeDueDiligenceResult() {
		return getHeadDetailPage("due_diligence_list.jsp", "detail.jsp");
	}
	
	/**
	 * AJAX 자산 실사 일정등록
	 * @param jsonObjectString 자산 실사 계획 내용
	 * @return 결과판단내용
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("newduediligenceplan.do")
	public @ResponseBody Map<String, String> newDueDiligencePlan(@RequestBody String jsonObjectString) {
		int result = assetService.newDueDiligencePlan(jsonToMap(jsonObjectString));
		return isInsertedToMap(result);
	}
	
	/**
	 * AJAX 자산 실사 일정변경
	 * @param jsonObjectString 자산 실사 계획 내용
	 * @return 결과판단내용
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("setduediligenceplan.do")
	public @ResponseBody Map<String, String> setDueDiligencePlan(@RequestBody String jsonObjectString) {
		int result = assetService.setDueDiligencePlan(jsonToMap(jsonObjectString));
		return isUpdatedToMap(result);
	}
	
	/**
	 * AJAX 실사계획 조회
	 * @param jsonObjectString 검색어
	 * @return 실사계획 리스트
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping("getduediligenceplanlist.do")
	public @ResponseBody List<Map<String, String>> getDueDiligencePlanList(@RequestBody String jsonObjectString) {
		return assetService.getDueDiligencePlanList(jsonToMap(jsonObjectString).get("keyword").replaceAll(" ", "|"));
	}
	
	/**
	 * AJAX 고객 새 비밀번호 설정
	 * @param jsonObjectString 변경정보
	 * @param session HttpSession - 사용자 검증용
	 * @return 설정 결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setNewPassword.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> setNewPassword(@RequestBody String jsonObjectString, HttpSession session) {
		Map<String, String> map = jsonToMap(jsonObjectString);
		System.out.println(session.getAttribute("loginId").toString());
		map.put("id", session.getAttribute("loginId").toString());
		String password = map.get("password");
		String newPass = map.get("new_pw");
		if (password == null || password.trim().isEmpty() || newPass == null || newPass.trim().isEmpty()) {
			return isUpdatedToMap(0);
		}
		int result = customerService.setNewPassword(map);
		return isUpdatedToMap(result);
	}
	
	/**
	 * AJAX 고객 새 핸드폰 번호 설정
	 * @param jsonObjectString 변경정보
	 * @param session HttpSession - 사용자 검증용
	 * @return 설정 결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setNewMobile.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> setNewMobile(@RequestBody String jsonObjectString, HttpSession session) {
		Map<String, String> map = jsonToMap(jsonObjectString);
		map.put("id", session.getAttribute("loginId").toString());
		String password = map.get("password");
		String mobile = map.get("new_mobile");
		if (password == null || password.trim().isEmpty() || mobile == null || mobile.trim().isEmpty()) {
			return isUpdatedToMap(0);
		}
		int result = customerService.setNewMobile(map);
		return isUpdatedToMap(result);
	}
	
	/**
	 * AJAX 고객 새 주소 설정
	 * @param jsonObjectString 변경정보
	 * @param session HttpSession - 사용자 검증용
	 * @return 설정 결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setNewAddress.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> setNewAddress(@RequestBody String jsonObjectString, HttpSession session) {
		Map<String, String> map = jsonToMap(jsonObjectString);
		map.put("id", session.getAttribute("loginId").toString());
		String password = map.get("password");
		String post = map.get("new_post");
		String addr = map.get("new_addr");
		String addrd = map.get("new_addr_d");
		if (password == null || password.trim().isEmpty() ||
				post == null || post.trim().isEmpty() ||
				addr == null || addr.trim().isEmpty() ||
				addrd == null || addrd.trim().isEmpty()) {
			return isUpdatedToMap(0);
		}
		int result = customerService.setNewAddress(map);
		return isUpdatedToMap(result);
	}
	
	/**
	 * AJAX 고객 새 카드 설정
	 * @param jsonObjectString 변경정보
	 * @param session HttpSession - 사용자 검증용
	 * @return 설정 결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setNewCard.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> setNewCard(@RequestBody String jsonObjectString, HttpSession session) {
		Map<String, String> map = jsonToMap(jsonObjectString);
		map.put("id", session.getAttribute("loginId").toString());
		String password = map.get("password");
		String company = map.get("new_c_company");
		String card = map.get("new_card1")+map.get("new_card2")+map.get("new_card3")+map.get("new_card4");
		if (password == null || password.trim().isEmpty() || company == null || company.trim().isEmpty() || card.trim().length() != 19 || !card.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")) {
			return isUpdatedToMap(0);
		}
		map.put("new_card", card);
		int result = customerService.setNewCard(map);
		return isUpdatedToMap(result);
	}
	
	/**
	 * AJAX 고객 새 계좌 설정
	 * @param jsonObjectString 변경정보
	 * @param session HttpSession - 사용자 검증용
	 * @return 설정 결과
	 * @author 김재림
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setNewAccount.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> setNewAccount(@RequestBody String jsonObjectString, HttpSession session) {
		Map<String, String> map = jsonToMap(jsonObjectString);
		map.put("id", session.getAttribute("loginId").toString());
		String password = map.get("password");
		String company = map.get("new_a_company");
		String account = map.get("new_account");
		if (password == null || password.trim().isEmpty() || company == null || company.trim().isEmpty() || account == null || account.trim().isEmpty()) {
			return isUpdatedToMap(0);
		}
		int result = customerService.setNewAccount(map);
		return isUpdatedToMap(result);
	}
	
	/* ======================================== by 이민정 ================================================= */
	/**
	 * 비밀번호 변경
	 * @param session 세션
	 * @param presentPw 현재 비밀번호
	 * @param newPw 새 비밀번호
	 * @return 성공시 메인화면으로 이동
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("changepw.do")
	public ModelAndView setPassword(HttpSession session, String presentPw, String newPw) {
		ModelAndView mv = new ModelAndView();
		String id = (String) session.getAttribute("loginId");
		boolean result = customerService.setPassword(id, presentPw, newPw);
		if(result) {
			return getPlainPage("gistMain.jsp");
		}else {
			//일치 하지 않는 정보 입니다 알림
		}
		return mv;
	}
	

	/**
	 * 비밀번호 변경 페이지로 가기
	 * @return 비밀번호 변경 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gochangepw.do")
	public ModelAndView goSetPassword() {
		return getPlainRedPage("changepw.jsp");
	}
	
	/**
	 * 렌탈 메인 페이지로 가기
	 * @return 렌탈 메인 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("rentalmain.do")
	public ModelAndView rentalMain() {
		ModelAndView mv = getPlainRedPage("rentalmain.jsp");
		ArrayList<Item> list = rentalService.getItemList();
		if(list != null) {
			mv.addObject("list", list);
		}
		return mv;
	}
	
	/**
	 * 렌탈 제품 검색하기
	 * @param sb_search 셀렉트 박스의 검색 카테고리
	 * @param item 검색어
	 * @return 검색 내역 출력
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("searchitem.do")
	public ModelAndView getSearchList(String sb_search, String item) {
		ModelAndView mv = getPlainRedPage("rentalmain.jsp");
		ArrayList<Item> list = rentalService.getSearchList(sb_search, item);
		if((item.trim().length() == 0) || (item.equals("")) || (list.size() == 0)) {
			StringBuffer message = new  StringBuffer();
			message.append("'").append(item);
			message.append("'에 대한 검색 결과가 없습니다.");
			mv.addObject("message", message);
		}
		else if((list != null) && (!list.isEmpty())) {
			StringBuffer message = new  StringBuffer();
			message.append("'").append(item);
			message.append("'에 대한 검색 결과가 ").append(list.size());
			message.append("건이 있습니다.");
			mv.addObject("list", list);
			mv.addObject("message", message);
			
		}
		return mv;
	}
	
	
	/**
	 * 렌탈 상세보기 페이지로 가기
	 * @param itemId 품목 아이디
	 * @return 렌탈 상세보기 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("rentaldetail.do")
	public ModelAndView rentalDetail(@RequestParam String itemId) {
		ModelAndView mv = getPlainRedPage("rentaldetail.jsp");
		Item dto = rentalService.getItem(itemId);
		if(dto != null) {
			mv.addObject("dto", dto);
			RempUtility ru = new RempUtility();
			mv.addObject("price", ru.numMoney(dto.getPrice()));
		}
		return mv;
	}
	
	/**
	 * 렌탈 구매하기
	 * @param itemId 품목아이디
	 * @return 렌탈 구매 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("rental.do")
	public ModelAndView rental(@RequestParam String itemId) {
		ModelAndView mv = getPlainRedPage("rental.jsp");;
		Item dto = rentalService.getItem(itemId);
		if(dto != null) {
			mv.addObject("dto", dto);
			RempUtility ru = new RempUtility();
			mv.addObject("price", ru.numMoney(dto.getPrice()));
		}
		return mv;
	}
	
	/**
	 * 입고 요청자산 조회로 이동
	 * @return 입고요청 자산 조회 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goinputrequest.do")
	public ModelAndView goInputRequest() {
		return getHeadDetailPage("searchinputrequesthead.jsp", "searchinputrequestdetail.jsp");
	}
	
	/**
	 * 입고 요청자산 조회
	 * @param jsonObjectString 입고 요청자산 상태 (inputState)
	 * @return 입고 요청자산 상태에 맞는 입고 요청 자산 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getinputrequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getInputRequeat(@RequestBody String jsonObjectString) {
		return assetService.getInputRequest(jsonToMap(jsonObjectString).get("inputState"));
	}
	
	/**
	 * 입고요청자산 입고 등록
	 * @param jsonObjectString 입고아이디(id), 입고자산상태(state)
	 * @return 입고등록 성공,실패시 메시지 map
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="setinputstate.do", method=RequestMethod.POST)
	public  @ResponseBody Map<String, String> setInputState(@RequestBody String jsonObjectString) {
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		int result = assetService.setInputState(jsonToMap(jsonObjectString).get("id"),jsonToMap(jsonObjectString).get("state"));
		if(result == 1) {
			map.put("result", "성공적으로 입고 처리되었습니다.");
		}else {
			map.put("result", "입고처리를 실패하였습니다.");
		}
		return map;
	}
	
	/**
	 * 요청자산 검색 조회
	 * @param jsonObjectString 요청자산 상태(state), 검색어(name)
	 * @return 요청자산 상태,검색어에 맞는 검색결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="searchinputrequest.do", method=RequestMethod.POST) 
	public @ResponseBody List<Map<String, String>> searchInputRequest(@RequestBody String jsonObjectString) {
		return assetService.searchInputRequest(jsonToMap(jsonObjectString).get("state"),jsonToMap(jsonObjectString).get("name").trim());
	}
	
	/**
	 * 입고 조회로 이동
	 * @return 입고 조회 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("goinput.do")
	public ModelAndView goInput() {
		return getHeadDetailPage("searchinputhead.jsp", "searchinputdetail.jsp");
	}
	
	/**
	 * 입고조회
	 * @param jsonObjectString 입고자산상태(state)
	 * @return 입고 자산상태를 조회한 입고 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("getinput.do") 
	public @ResponseBody List<Map<String, String>> getInput(@RequestBody String jsonObjectString) {
		System.out.println(jsonToMap(jsonObjectString).get("state"));
		List<Map<String, String>> result = assetService.getInput(jsonToMap(jsonObjectString).get("state"));
		System.out.println(result);
		return result;
	}
	
	/**
	 * 입고조회 검색
	 * @param jsonObjectString 입고상태(state),검색어(name)
	 * @return 입고자산 검색 후 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="searchinput.do", method=RequestMethod.POST) 
	public @ResponseBody List<Map<String, String>> searchInput(@RequestBody String jsonObjectString) {
		return assetService.searchInput(jsonToMap(jsonObjectString).get("state"),jsonToMap(jsonObjectString).get("name").trim());
	}

	/**
	 * 내부수리기사 점검등록 페이지로 이동
	 * @param session 세션
	 * @return 점검대기인 자산과 세션에 저장되어있는 아이디와 이름을 출력한 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gorepairlist.do")
	public ModelAndView goRepairList(HttpSession session) {
		ModelAndView mv = getHeadDetailPage("addrepairresulthead.jsp", "addrepairresultdetail.jsp");
		ArrayList<Product> list = assetService.getRepairList();
		
		if(list != null) {
			mv.addObject("list", list);
		}
		String id = (String) session.getAttribute("loginId");
		String name = (String) session.getAttribute("loginName");
		mv.addObject("id", id);
		mv.addObject("name", name);
		return mv;
	}
	
	/**
	 * 내부수리기사 점검대기 데이터 검색조회
	 * @param jsonObjectString 검색어(keyword), 검색카테고리(select)
	 * @return 점검대기인 상태인 자산을 검색어와 검색카테고리로 검색한 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getrepairlist.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getRepairList(@RequestBody String jsonObjectString) {
		return assetService.searchRepairList(jsonToMap(jsonObjectString).get("keyword"), jsonToMap(jsonObjectString).get("select"));
	}
	
	/**
	 * 헤드에서 항목 클릭하면 디테일영역에 데이터 가져오기
	 * @param jsonObjectString 자산아이디(id),자산상태(state)
	 * @return 헤드에서 선택한 정보 map
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getrepairform.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> getRepairForm(@RequestBody String jsonObjectString ) {
		return assetService.getRepairForm(jsonToMap(jsonObjectString).get("id"), jsonToMap(jsonObjectString).get("state"));
	}
	
	/**
	 * 내부수리기사 점검내역 등록할 때 내부수리시 수리하는 품목에 맞는 부품리스트 보여주기
	 * @param jsonObjectString 품목아이디(id)
	 * @return 품목아이디에 사용되는 부품리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getpartslist.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getPartsList(@RequestBody String jsonObjectString ) {
		return assetService.getRepairPartsList(jsonToMap(jsonObjectString).get("id"));
	}
	
	   /**
	   * 내부수리기사 점검내역 등록
	   * @param jsonObjectString
	   * @return 점검내역 등록결과 map
	   * @author 이민정
	   * @since ReMP v 1.0
       */
      @RequestMapping(value="addrepairresult.do", method=RequestMethod.POST)
      public  @ResponseBody Map<String, String> addRepairResult(@RequestBody String jsonObjectString) {
         Map<String, Object> map = jsonToOMap(jsonObjectString);
         Map<String, String> parts = (Map<String, String>) map.get("list");
         int result = 0;
         if(map.get("repairSort").toString().length() != 0 && map.get("repairContent").toString().trim().length() != 0) {
            result = assetService.addRepairResult(map, parts);
         }
         return areUpdatedToMap(result);
      }
	
	/**
	 * 부품조회 페이지로 이동
	 * @return 부품조회 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gosearchparts.do")
	public ModelAndView goSearchPart() {
		return getPlainPage("searchparts.jsp");
	}
	
	/**
	 * 모든 부품 리스트 보여주기(부품조회 초기페이지의 데이터)
	 * @return 모든 부품 조회한 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getallpartslist.do", method=RequestMethod.POST) 
	public @ResponseBody List<Map<String, String>> getAllParts() {
		return assetService.getAllParts();
	}
	
	/**
	 * 내부수리기사 점검결과보기 페이지로 이동
	 * @return 내부수리결과 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping("gorepairresult.do")
	public ModelAndView goRepairResult() {
		return getPlainPage("searchrepairresult.jsp");
	}
	
	/**
	 * 내부수리기사 모든 점검결과  보여주기 
	 * @param session 세션
	 * @param jsonObjectString 
	 * @return 모든 점검결과 list
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getallrepairresultlist.do", method=RequestMethod.POST) 
	public @ResponseBody List<Map<String, String>> getAllRepairResult(HttpSession session, @RequestBody String jsonObjectString) {
		String id = (String) session.getAttribute("loginId");
		return assetService.getAllRepairResult(id);
	}
	
	/**
	 * 렌탈 구매, 구매내역확인으로 이동
	 * @param session 세션
	 * @param map 
	 * @return 렌탈 구매 후 구매내역 확인 페이지
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="rentalpayment.do", method=RequestMethod.POST) 
	public ModelAndView rentalPayment(HttpSession session, @RequestParam Map<String, String> map) {
		ModelAndView mv = getPlainRedPage("complitedpayment.jsp");
		String customerId = (String) session.getAttribute("loginId");
		int buyResult = assetService.insertCustomerBuy(customerId, map);
		if(buyResult != 0) {
			map.put("tb_itNumber", "1");
			int outputResult = assetService.insertOutput(map); 
			mv.addObject("map", map);
			RempUtility ru = new RempUtility();
			mv.addObject("price", ru.numMoney(Integer.parseInt(map.get("price"))));
		}
		return mv;
	}
	
	/**
	 * 부속품 검색조회
	 * @param jsonObjectString 검색 카테고리(searchType), 검색어(searchKeyword)
	 * @return 부속품 검색한 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getsearchpartslist.do", method=RequestMethod.POST) 
	public @ResponseBody List<Map<String, String>> getSearchPartsList(@RequestBody String jsonObjectString) {
		return assetService.getRepairResult(jsonToMap(jsonObjectString).get("searchType"),jsonToMap(jsonObjectString).get("searchKeyword"));
	}
	
	/**
	 * 수리기사 점검내역 결과 검색조회
	 * @param session 세션
	 * @param jsonObjectString 검색기간 시작일(startDate), 검색기간 마지막일(endDate), 점검분류(repairSort)
	 * @return 내부수리결과 검색한 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	@RequestMapping(value="getrepairresultlist.do", method=RequestMethod.POST) 
	public @ResponseBody List<Map<String, String>> getRepairResult(HttpSession session,@RequestBody String jsonObjectString) {
		String id = (String) session.getAttribute("loginId");
		List<Map<String, String>> result = assetService.getRepairResult(id, jsonToMap(jsonObjectString).get("startDate"), jsonToMap(jsonObjectString).get("endDate"), jsonToMap(jsonObjectString).get("repairSort"));
		return result;
	}
		
	/* ======================================== by 이원호 ================================================= */
		/**
		 * 메인페이지 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goMain.do")
		public ModelAndView goMain() {
			return getPlainPage("gistMain.jsp");
		}
		
		/**
		 * 404ErrorPage 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goFoundError.do")
		public ModelAndView goFoundError() {
			return getErrorPage("foundError.jsp");
		}
		
		/**
		 * 500ErrorPage 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goRequestError.do")
		public ModelAndView goRequestError() {
			return getErrorPage("requestError.jsp");
		}
		
		/**
		 * 기타 ErrorPage 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goEtcError.do")
		public ModelAndView goEtcError() {
			return getErrorPage("etcError.jsp");
		}
		
		/**
		 * 사용자 404ErrorPage 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goUserFoundError.do")
		public ModelAndView goUserFoundError() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("employeeAuth", "aa");
			return getErrorRedPage(mv, "foundError.jsp");
		}
		
		/**
		 * 사용자 500ErrorPage 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goUserRequestError.do")
		public ModelAndView goUserRequestError() {
			return getErrorRedPage("requestError.jsp");
		}
		
		/**
		 * 사용자 기타 ErrorPage 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goUserEtcError.do")
		public ModelAndView goUserEtcError() {
			return getErrorRedPage("etcError.jsp");
		}
		
		/**
		 * 로그인 페이지 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goLogin.do")
		public ModelAndView goLogin() {
			return getPlainPage("login.jsp");
		}
		
		/**
		 * 회원가입  페이지 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goJoin.do")
		public ModelAndView goJoin() {
			return getPlainPage("join.jsp");
		}

		/**
		 * 관리자 직원 등록 페이지
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goCompanionJoin.do")
		public ModelAndView goCompanionJoin() {
			List<Map<String, String>> auList = memberService.getAuthorityList();
			ModelAndView mv = new ModelAndView();
			mv.addObject("target", "Employee");
			if ((auList != null) && (!auList.isEmpty())) {
				mv.addObject("employeeAuth", auList);
			}
			return getHeadDetailPage(mv, "memberList.jsp", "companionJoin.jsp");
		}
		
		/**
		 * 관리자 직원 정보 변경 페이지
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goCompanionInfo.do")
		public ModelAndView goCompanionInfo() {
			List<Map<String, String>> auList = memberService.getAuthorityList();
			ModelAndView mv = new ModelAndView();
			mv.addObject("target", "Employee");
			if ((auList != null) && (!auList.isEmpty())) {
				mv.addObject("employeeAuth", auList);
			}
			return getHeadDetailPage(mv, "memberList.jsp", "companionInfo.jsp");
		}

		/**
		 * 관리자 고객 정보 변경 페이지
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goCusInfo.do")
		public ModelAndView goCusInfo() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("target", "Customer");
			return getHeadDetailPage(mv, "memberList.jsp", "customerInfo.jsp");
		}
		
		/**
		 * 회수 결과 처리 페이지 이동
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value = "goRefundRequest.do")
		public ModelAndView goRefundRequest() {
			ModelAndView mav = new ModelAndView();
			mav.addObject("state", "Refund");
			return getHeadDetailPage(mav, "rentalVisitList.jsp", "rentalVisit.jsp");
		}
		
		/**
		 * 고객 회원 가입
		 * @param customerJoin
		 * @return 성공 1 실패 0
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping("join.do")
		public ModelAndView join(@RequestParam Map<String, String> customerJoin) {
			if (customerService.addJoin(customerJoin)) {
				return getPlainPage("login.jsp");
			}
			return getPlainPage("join.jsp");
		}
		
		/**
		 * 고객 로그인
		 * @param tb_inputId 입력한 ID
		 * @param tb_inputPw 입력한 PW
		 * @param session 아이디, 이름, 권한ID
		 * @return 성공 ID,이름,권한
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping("customerLogin.do")
		public ModelAndView customerLogin(String tb_inputId, String tb_inputPw, HttpSession session) {
			HashMap<String, String> hashmap = customerService.getLogin(tb_inputId, tb_inputPw);
			if ((hashmap!=null) && (!hashmap.isEmpty())) {
				session.setAttribute("loginId", hashmap.get("loginId"));
				session.setAttribute("loginName", hashmap.get("loginName"));
				session.setAttribute("authority", "customer");
				return getPlainRedPage("rentalmain.jsp");
			}
			return getPlainPage("login.jsp");
		}
		
		/**
		 * 직원 로그인
		 * @param tb_inputId 입력한 ID
		 * @param tb_inputPw 입력한 PW
		 * @param session 아이디, 이름, 권한ID
		 * @return 성공 ID,이름,권한
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping("employeeLogin.do")
		public ModelAndView employeeLogin(String tb_inputId, String tb_inputPw, HttpSession session) {
			HashMap<String, String> hashmap = memberService.getLogin(tb_inputId, tb_inputPw);
			if ((hashmap!=null) && (!hashmap.isEmpty())) {
				session.setAttribute("loginId", hashmap.get("loginId"));
				session.setAttribute("loginName", hashmap.get("loginName"));
				session.setAttribute("authority", hashmap.get("authority"));
				return getPlainPage("gistMain.jsp");
			}
			return getPlainPage("login.jsp");
		}
		
		/**
		 * 회원가입 시 아이디 중복 확인
		 * @param jsonObjectString 고객ID
		 * @return 유 1 무 0
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="getIdCheck.do", method=RequestMethod.POST)
		public @ResponseBody boolean idCheck(@RequestBody String jsonObjectString) {
			return customerService.getIdCheck(jsonToMap(jsonObjectString).get("customerId"));
		}
		
		/**
		 * 외부수리기사 회수 요청 결과 등록 및 수정
		 * @param jsonObjectString 회수 결과 정보
		 * @param session ID, 이름
		 * @return 성공 1 실패0
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="setRentalRefundResult.do", method=RequestMethod.POST)
		public @ResponseBody Boolean refundResult(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			Deprive depriveDto = new Deprive();
			String date = "";
			if (map.get("tb_viDay").trim().length() > 0) {
				date = map.get("tb_viDay").trim().substring(0,10) + map.get("tb_viDay").trim().substring(11);
			}
			depriveDto.setId(map.get("tb_depId"));
			depriveDto.setViId(map.get("hi_viId"));
			depriveDto.setDay(date);
			depriveDto.setCuId(map.get("hi_cuId"));
			depriveDto.setCuName(map.get("tb_cuName"));
			depriveDto.setPrId(map.get("tb_prId"));
			depriveDto.setState("re_return");
			depriveDto.setEngineerId((String)session.getAttribute("loginId"));
			depriveDto.setEngineerName((String)session.getAttribute("loginName"));
			depriveDto.setContent(map.get("te_content"));
			
			if (map.get("tb_depId").trim().length() > 0) {
				return rentalService.setRefundResult(depriveDto, map.get("hi_buyId"));
			} else {
				return rentalService.addRefundResult(depriveDto, map.get("hi_buyId"));
			}
		}
		
		/**
		 * 관리자가 고객 리스트 조회
		 * @param jsonObjectString 검색어
		 * @return 검색어를 만족하는 고객 리스트 조회
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="getCustomerList.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getCustomereList(@RequestBody String jsonObjectString) {
			return customerService.getCustomerList(jsonToMap(jsonObjectString).get("keyword"));
		}
		
		/**
		 * 관리자가 직원 등록
		 * @param jsonObjectString 직원 정보
		 * @return 성공 1 실패 0
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="addCompanionJoin.do", method=RequestMethod.POST)
		public @ResponseBody Boolean addCompanionJoin(@RequestBody String jsonObjectString) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			SmsService sms = new SmsService();
			sms.sendSms(map.get("tb_memMobile"), map.get("tb_memId"), map.get("tb_memPw"));
			return memberService.addMemberJoin(map);
		}
		
		/**
		 * 관리자가 직원 목록 조회
		 * @param jsonObjectString 검색어
		 * @return 검색어를 만족하는 직원 리스트
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="getEmployeeList.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getEmployeeList(@RequestBody String jsonObjectString) {
			return memberService.getMemberList(jsonToMap(jsonObjectString).get("keyword"));
		}
		
		/**
		 * 관리자가 고객 정보 조회
		 * @param jsonObjectString 고객  정보
		 * @return 고객정보
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="getCustomerInfo.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> getCustomereInfo(@RequestBody String jsonObjectString) {
			return customerService.getCustomerInfo(jsonToMap(jsonObjectString).get("memberId"));
		}
		
		/**
		 * 관리자가 직원 정보 조회
		 * @param jsonObjectString 직원 정보
		 * @return 성공 true 실패 false
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="getEmployeeInfo.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> getEmployeeInfo(@RequestBody String jsonObjectString) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			return memberService.getMemberInfo(map.get("memberId"));
		}
		
		/**
		 * 관리자가 고객, 직원 정보 일괄 변경
		 * @param jsonObjectString 고객 및 직원 정보
		 * @return 성공 true 실패 false
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="setMemberChange.do", method=RequestMethod.POST)
		public @ResponseBody boolean setMemberChange(@RequestBody String jsonObjectString) {
			Map<String, String> memberInfo = jsonToMap(jsonObjectString);
			if (memberInfo.get("target").trim().equals("Customer")) {
				return customerService.setCustomerInfo(memberInfo);
			} else if(memberInfo.get("target").trim().equals("Employee")) {
				if(memberService.setMemberInfo(memberInfo)) {
					SmsService sms = new SmsService();
					return sms.sendSms(memberInfo.get("tb_memMobile"), memberInfo.get("tb_memId"), memberInfo.get("tb_memPw"));
				}
			}
			return false;
		}
		
		/**
		 * 관리자가 고객 및 직원 정보 개별 변경
		 * @param jsonObjectString 고객 및 직원 정보
		 * @return 성공 true 실패 false
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="setMemberItemChange.do", method=RequestMethod.POST)
		public @ResponseBody boolean setMemberItemChange(@RequestBody String jsonObjectString) {
			Map<String, String> memberInfo = jsonToMap(jsonObjectString);
			if (memberInfo.get("hi_target").trim().equals("Customer")) {
				if(memberInfo.get("column").equals("1")) {
					email(memberInfo.get("tb_memId"), memberInfo.get("tb_memPw"));
				}
				return customerService.setCustomerInfoDetail(memberInfo);
			} else if(memberInfo.get("hi_target").trim().equals("Employee")) {
				return memberService.setMemberInfoDetail(memberInfo);
			}
			return false;
		}
		
		/**
		 * 관리자가 직원 등록 시 새로운 아이디 가져오기
		 * @param jsonObjectString 새로운 직원 ID값
		 * @return 발급된 아이디
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="getNewId.do", method=RequestMethod.POST)
		public @ResponseBody String getNewId(@RequestBody String jsonObjectString) {
			return memberService.getRandomEmpl();
		}
		
		/**
		 * 상담사가 렌탈 상담시 조회한 물품 수량 가져오기
		 * @param jsonObjectString 검색 itId, itName
		 * @return 해당 물품의 상태별 수량
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="getProductCount.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> getProductCount(@RequestBody String jsonObjectString) {
			Map<String, String> item = jsonToMap(jsonObjectString);
			return assetService.getProductCount(item.get("itId"), item.get("itName"));
		}
		
		/**
		 * 상담사가 렌탈 상담시 임시 회원 등록
		 * @param jsonObjectString 고객 정보
		 * @return 성공 1 실패 0
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="addTempCustomer.do", method=RequestMethod.POST)
		public @ResponseBody String addTempCustomer(@RequestBody String jsonObjectString) {
			return customerService.addTempCustomer(jsonToMap(jsonObjectString));
		}
		
		/**
		 * 상담사가 상담 내용 등록 시 새로운 상담 아이디 가져오기
		 * @param session 직원ID
		 * @return 상담 ID
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="addAdviceId.do", method=RequestMethod.POST)
		public @ResponseBody String addAdviceId(HttpSession session) {
			return rentalService.getAdviceId((String)session.getAttribute("loginId"));
		}
		
		/**
		 * 상담사가 일반 상담 등록하는 부분
		 * @param jsonObjectString 일반 상담 내용
		 * @param session 직원 ID
		 * @return 등록 1 실패 0
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="addNomalAdvice.do", method=RequestMethod.POST)
		public @ResponseBody String addNomalAdvice(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			map.put("adSort", "a");
			map.put("sb_item", "");
			map.put("emId", (String)session.getAttribute("loginId"));
			int result = rentalService.addNomalAdvice(map);
			if (result == 1) {
				return "1";
			}
			return "0";
		}
		
		/**
		 * 상담사가 렌탈 상담 등록하는 부분
		 * @param jsonObjectString 렌탈 상담 내용
		 * @param session 직원ID
		 * @return 등록 1 실패 0
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="addRentalAdvice.do", method=RequestMethod.POST)
		public @ResponseBody String addRentalAdvice(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			map.put("adSort", "b");
			map.put("emId", (String)session.getAttribute("loginId"));
			int result = rentalService.addRentalAdvice(map);
			if (result == 1) {
				return "1";
			}
			return "0";
		}
		
		/**
		 * 관리자가 고객 비밀번호 변경 시 메일 전송
		 * @param customerId 고객ID
		 * @param newPw 변경된 PW
		 * @return 성공 true 실패 false
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		public boolean email(String customerId, String newPw) {
			String to = customerId;
			String from = "leedh93@hanmail.net";
			
			Properties p = new Properties();
			p.put("mail.smtp.host","smtp.daum.net");
			p.put("mail.smtp.port", "465");
			p.put("mail.smtp.starttls.enable", "true");
			p.put("mail.smtp.auth", "true");
			p.put("mail.smtp.debug", "true");
			p.put("mail.smtp.socketFactory.port", "465");
			p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			p.put("mail.smtp.socketFactory.fallback", "false");
			
			try{
		    	Authenticator auth = new SMTPAuthenticatior();
		        Session ses = Session.getInstance(p, auth);
		        MimeMessage msg = new MimeMessage(ses);
		     
		        msg.setSubject("[GIST] 개인정보 이용 안내");
		        StringBuilder buffer = new StringBuilder();
		        buffer.append("고객님의 요청으로 인하여 개인 정보가 변경되었습니다.<br><br>");
		        buffer.append("변경된 회원님의 임시비밀번호는   <font color=\"red\"  style=\"font-size: 20px; font-weight: bold;\">"+ newPw +"</font>  입니다.<br><br>");
		        buffer.append("변경한 내역이 존재하지 않으실 경우 고객센터에 문의하여 주세요.");
		        Address fromAddr = new InternetAddress(from);
		        msg.setFrom(fromAddr); 
		     
		        Address toAddr = new InternetAddress(to);
		        msg.addRecipient(Message.RecipientType.TO, toAddr);
		        msg.setContent(buffer.toString(), "text/html;charset=UTF-8");
		        Transport.send(msg);
		    } catch(Exception e){
		        e.printStackTrace();
		        return false;
		    }
			return true;
		}
		
		/**
		 * ZXING 을 이용한 Qr Code 발급
		 * @author 이원호
		 * @since ReMP v 1.0
		 */
		@RequestMapping(value="addQrCode.do", method=RequestMethod.POST)
		public @ResponseBody void qrCode() {
			try {
	            File file = null;
	            file = new File("I:\\ho\\eclipseall\\ReMPP\\remp\\src\\main\\webapp\\resources\\images");
	            if(!file.exists()) {
	                file.mkdirs();
	            }
	            String codeurl = new String("http://www.naver.com/".getBytes("UTF-8"), "ISO-8859-1");
	            int qrcodeColor = 0xFF00FF00;
	            int backgroundColor = 0x00FF00FF;
	             
	            QRCodeWriter qrCodeWriter = new QRCodeWriter();
	            BitMatrix bitMatrix = qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE,200, 200);
	            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);
	            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
	            ImageIO.write(bufferedImage, "png", new File("I:\\ho\\eclipseall\\ReMPP\\remp\\src\\main\\webapp\\resources\\images\\qrcode.png"));
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}