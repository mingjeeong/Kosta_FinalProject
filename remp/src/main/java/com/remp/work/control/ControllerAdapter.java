package com.remp.work.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remp.work.model.service.AssetService;
import com.remp.work.model.service.CustomerService;
import com.remp.work.model.service.EisService;
import com.remp.work.model.service.MemberService;
import com.remp.work.model.service.RentalService;
/**
 * <p>
 * 모든 컨트롤러의 공통기능을 구현한 어댑터 클래스
 * 컨트롤러 구현 시 어탭터 클래스 상속하여 사용함
 * </p>
 * @author 김재림
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 * @see <a href = "http://spring.io">Spring Framework</a>
 */
public abstract class ControllerAdapter {
	//빌드패스
	public static final String BUILD_PATH = "/WEB-INF/views/";
	public static final String SERVICE_PATH = "/WEB-INF/views/service/";
	public static final String STRUCTURE_PATH = "/WEB-INF/views/structure/";
	public static final String ERROR_PATH = "/WEB-INF/views/error/";
	//탬플릿
	public static final String PLAIN = "plain";
	public static final String PLAIN_RED = "plain_red";
	public static final String HEAD_DETIAL = "head_detail";
	public static final String HEAD_DETIAL_RED = "head_detial_red";
	public static final String TAB = "tab";
	public static final String TAB_RED = "tab_red";
	public static final String ERROR = "error";
	public static final String ERROR_RED = "error_red";
	//기타
	public static final String TITLE = "ReMP : 렌탈관리 통합 플랫폼";
	//서비스
	@Autowired
	protected MemberService memberService;
	@Autowired
	protected CustomerService customerService;
	@Autowired
	protected AssetService assetService;
	@Autowired
	protected RentalService rentalService;
	@Autowired
	protected EisService eisService;
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerAdapter.class);
	
	//---------------------- 화면연결 ------------------------
	/**
	 * 루트 컨택스트 접근 시 페이지
	 * @return 루트컨텐스트 연결 ModelAndView 객체
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public abstract ModelAndView home();
	
	/**
	 * 메인페이지 연결 기능
	 * @return 메인페이지 연결 ModelAndView 객체
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "goMain.do")
	public abstract ModelAndView goMain();
	
	//---------------------- 에러페이지 ------------------------
	/**
	 * 404 에러 페이지 호출
	 * @return 404 에러 페이지 호출 ModelAndView
	 * @author 이원호
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "goFoundError.do")
	public abstract ModelAndView goFoundError();
	
	/**
	 * 500 에러 페이지 호출
	 * @return 500 에러 페이지 호출 ModelAndView
	 * @author 이원호
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "goRequestError.do")
	public abstract ModelAndView goRequestError();
	
	/**
	 * 기타 에러 페이지 호출
	 * @return 기타 에러 페이지 호출 ModelAndView
	 * @author 이원호
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "goEtcError.do")
	public abstract ModelAndView goEtcError();
	
	/**
	 * 고객 404 에러 페이지 호출
	 * @return 고개 404 에러 페이지 호출 ModelAndView
	 * @author 이원호
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "goUserFoundError.do")
	public abstract ModelAndView goUserFoundError();
	
	/**
	 * 고객 500 에러 페이지 호출
	 * @return 고객 500 에러 페이지 호출 ModelAndView
	 * @author 이원호
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "goUserRequestError.do")
	public abstract ModelAndView goUserRequestError();
	
	/**
	 * 고객 기타 에러 페이지 호출
	 * @return 고객 기타 에러 페이지 호출 ModelAndView
	 * @author 이원호
	 * @since ReMP v 1.0 +
	 */
	@RequestMapping(value = "goUserEtcError.do")
	public abstract ModelAndView goUserEtcError();
	
	//---------------------- 유틸리티 --------------------------------
	//업무시스템 템플릿
	/**
	 * PLAIN 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getPlainPage(String content) {
		return getInnerPage(SERVICE_PATH + content);
	}
	
	/**
	 * PLAIN 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param mav 페이지 사용할 ModelAndView 객체
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getPlainPage(ModelAndView mav, String content) {
		return getInnerPage(mav, null, SERVICE_PATH + content, PLAIN);
	}
	
	/**
	 * HEAD_DETAIL 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param headContent head 페이지로 사용할 jsp 파일
	 * @param detailContent detail 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getHeadDetailPage(String headContent, String detailContent) {
		ModelAndView returnValue = getInnerPage(null, "head", SERVICE_PATH + headContent, HEAD_DETIAL);
		returnValue = getInnerPage(returnValue, "detail", SERVICE_PATH + detailContent, HEAD_DETIAL);
		return returnValue;
	}
	
	/**
	 * HEAD_DETAIL_RED 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param headContent head 페이지로 사용할 jsp 파일
	 * @param detailContent detail 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getHeadDetailPage(ModelAndView mav, String headContent, String detailContent) {
		mav = getInnerPage(mav, "head", SERVICE_PATH + headContent, HEAD_DETIAL);
		mav = getInnerPage(mav, "detail", SERVICE_PATH + detailContent, HEAD_DETIAL);
		return mav;
	}
	
	/**
	 * TAB 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getTabPage(String content) {
		return getInnerPage(null, null, SERVICE_PATH + content, TAB);
	}
	
	/**
	 * TAB 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param mav 페이지 사용할 ModelAndView 객체
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getTabPage(ModelAndView mav, String content) {
		return getInnerPage(mav, null, SERVICE_PATH + content, TAB);
	}
	
	/**
	 * PLAIN_RED 탬플릿을 사용하는 페이지 호출, serivce 폴더이용
	 * @param content
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getPlainRedPage(String content) {
		return getInnerPage(null, null, SERVICE_PATH + content, PLAIN_RED);
	}
	
	/**
	 * PLAIN_RED 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param mav 페이지 사용할 ModelAndView 객체
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getPlainRedPage(ModelAndView mav, String content) {
		return getInnerPage(mav, null, SERVICE_PATH + content, PLAIN_RED);
	}
	
	/**
	 * HEAD_DETIAL_RED 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param headContent head 페이지로 사용할 jsp 파일
	 * @param detailContent detail 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getHeadDetailRedPage(String headContent, String detailContent) {
		ModelAndView returnValue = getInnerPage(null, "head", SERVICE_PATH + headContent, HEAD_DETIAL_RED);
		returnValue = getInnerPage(returnValue, "detail", SERVICE_PATH + detailContent, HEAD_DETIAL_RED);
		return returnValue;
	}
	
	/**
	 * HEAD_DETIAL_RED 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param mav 페이지 사용할 ModelAndView 객체
	 * @param headContent head 페이지로 사용할 jsp 파일
	 * @param detailContent detail 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getHeadDetailRedPage(ModelAndView mav, String headContent, String detailContent) {
		mav = getInnerPage(mav, "head", SERVICE_PATH + headContent, HEAD_DETIAL_RED);
		mav = getInnerPage(mav, "detail", SERVICE_PATH + detailContent, HEAD_DETIAL_RED);
		return mav;
	}
	
	/**
	 * TAB_RED 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getTabRedPage(String content) {
		return getInnerPage(null, null, SERVICE_PATH + content, TAB);
	}
	
	/**
	 * TAB_RED 탬플릿을 사용하는 페이지 호출, service 폴더이용
	 * @param mav 페이지 사용할 ModelAndView 객체
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getTabRedPage(ModelAndView mav, String content) {
		return getInnerPage(mav, null, SERVICE_PATH + content, TAB);
	}
	
	/**
	 * ERROR 탬플릿을 사용하는 페이지 호출, error 폴더이용
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getErrorPage(String content) {
		return getInnerPage(null, null, ERROR_PATH + content, ERROR);
	}
	
	/**
	 * ERROR 탬플릿을 사용하는 페이지 호출, error 폴더이용
	 * @param mav 페이지 사용할 ModelAndView 객체
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getErrorPage(ModelAndView mav, String content) {
		return getInnerPage(mav, null, ERROR_PATH + content, ERROR);
	}

	/**
	 * ERROR_RED 탬플릿을 사용하는 페이지 호출, error 폴더이용
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getErrorRedPage(String content) {
		return getInnerPage(null, null, ERROR_PATH + content, ERROR_RED);
	}
	
	/**
	 * ERROR_RED 탬플릿을 사용하는 페이지 호출, error 폴더이용
	 * @param mav 페이지 사용할 ModelAndView 객체
	 * @param content 컨텐츠 페이지로 사용할 jsp 파일
	 * @return 화면 구성 완료된 ModelAndView 객체
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getErrorRedPage(ModelAndView mav, String content) {
		return getInnerPage(mav, null, ERROR_PATH + content, ERROR_RED);
	}
	
	/**
	 * HTML 구성 시 탬플릿과 조립체 컨텐츠를 페이지로 구성하여 ModelAndView 객체로 리턴함
	 * @param fileName 조립체 컨텐츠로 사용될 대상 파일
	 * @return PLAIN 탬플릿으로 구성완료된 페이지
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getInnerPage(String fileName) {
		return getInnerPage(null, null, fileName, null);
	}
	
	/**
	 * HTML 구성 시 탬플릿과 조립체 컨텐츠를 페이지로 구성하여 ModelAndView 객체로 리턴함
	 * @param fileName 조립체 컨텐츠로 사용될 대상 파일
	 * @param templeteId 사용할 탬플릿
	 * @return templeteId 탬플릿으로 구성완료된 페이지
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getInnerPage(String fileName, String templeteId) {
		return getInnerPage(null, null, fileName, templeteId);
	}
	
	/**
	 * HTML 구성 시 탬플릿과 조립체 컨텐츠를 페이지로 구성하여 ModelAndView 객체로 리턴함
	 * @param viewName 탬플릿에서 호출할 변수이름
	 * @param fileName 조립체 컨텐츠로 사용될 대상 파일
	 * @param templeteId 사용할 탬플릿
	 * @return templeteId 탬플릿으로 구성완료된 페이지
	 * @see #getInnerPage(ModelAndView, String, String, String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getInnerPage(String viewName, String fileName, String templeteId) {
		return getInnerPage(null, viewName, fileName, templeteId);
	}
	
	/**
	 * HTML 구성 시 탬플릿과 조립체 컨텐츠를 페이지로 구성하여 ModelAndView 객체로 리턴함
	 * @param mav 외부 ModelAndView 객체
	 * @param viewName 탬플릿에서 호출할 변수이름
	 * @param fileName 조립체 컨텐츠로 사용될 대상 파일
	 * @param templeteId 사용할 탬플릿
	 * @return 구성완료된 페이지
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected ModelAndView getInnerPage(ModelAndView mav, String viewName, String fileName, String templeteId) {
		if (mav == null) {
			mav = new ModelAndView();
		}
		if (viewName == null || viewName.trim().length() == 0) {
			viewName = "servicePage";
		}
		if (templeteId == null || templeteId.trim().length() == 0) {
			templeteId = PLAIN;
		}
		mav.addObject(viewName, fileName);
		mav.setViewName(templeteId);
		return mav;
	}
	
	//--------------- DBMS transaction Message controller -------------
	/**
	 * AJAX 통신 시 int형 데이터를 받아 단일라인를 수행결과를 Map으로 전달
	 * @param result update 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #areUpdatedToMap(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected Map<String, String> isUpdatedToMap(int result) {
		Map<String, String> returnValue = new HashMap<>();
		StringBuilder value = new StringBuilder();
		if (result == 1) {
			value.append("success");
		} else if (result == 0) {
			value.append("invalid");
		} else if (result > 1) {
			value.append("violated");
		} else {
			value.append("network");
		}
		returnValue.put("result", value.toString());
		return returnValue;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 다중라인를 수행결과를 Map으로 전달
	 * @param result update 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #isUpdatedToMap(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected Map<String, String> areUpdatedToMap(int result) {
		Map<String, String> returnValue = new HashMap<>();
		StringBuilder value = new StringBuilder();
		if (result >= 1) {
			value.append("success");
		} else if (result == 0) {
			value.append("invalid");
		} else {
			value.append("network");
		}
		returnValue.put("result", value.toString());
		return returnValue;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 단일라인를 수행결과를 boolean 으로 전달
	 * @param result update 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  true - 정합한 DBMS 트랜젝션 수행, False - 정합하지 못한 DBMS 트랜젝션 수행
	 * @see #areUpdated(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected boolean isUpdated(int result) {
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 다중라인를 수행결과를 boolean 으로 전달
	 * @param result update 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  true - 정합한 DBMS 트랜젝션 수행, False - 정합하지 못한 DBMS 트랜젝션 수행
	 * @see #isUpdated(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected boolean areUpdated(int result) {
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 단일라인를 수행결과를 Map으로 전달
	 * @param result insert 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #areInsertedToMap(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected Map<String, String> isInsertedToMap(int result) {
		Map<String, String> returnValue = new HashMap<>();
		StringBuilder value = new StringBuilder();
		if (result == 1) {
			value.append("success");
		} else if (result == 0) {
			value.append("invalid");
		} else if (result > 1) {
			value.append("violated");
		} else {
			value.append("network");
		}
		returnValue.put("result", value.toString());
		return returnValue;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 다중라인를 수행결과를 Map으로 전달
	 * @param result insert 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #isInsertedToMap(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected Map<String, String> areInsertedToMap(int result) {
		Map<String, String> returnValue = new HashMap<>();
		StringBuilder value = new StringBuilder();
		if (result >= 1) {
			value.append("success");
		} else if (result == 0) {
			value.append("invalid");
		} else {
			value.append("network");
		}
		returnValue.put("result", value.toString());
		return returnValue;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 단일라인를 수행결과를 boolean으로 전달
	 * @param result insert 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #areInserted(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected boolean isInserted(int result) {
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 다중라인를 수행결과를 boolean으로 전달
	 * @param result insert 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #isInserted(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected boolean areInserted(int result) {
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 단일라인를 수행결과를 Map으로 전달
	 * @param result delete 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #areDeletedToMap(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected Map<String, String> isDeletedToMap(int result) {
		Map<String, String> returnValue = new HashMap<>();
		StringBuilder value = new StringBuilder();
		if (result == 1) {
			value.append("success");
		} else if (result == 0) {
			value.append("invalid");
		} else if (result > 1) {
			value.append("violated");
		} else {
			value.append("network");
		}
		returnValue.put("result", value.toString());
		return returnValue;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 다중라인를 수행결과를 Map으로 전달
	 * @param result delete 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #isDeletedToMap(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected Map<String, String> areDeletedToMap(int result) {
		Map<String, String> returnValue = new HashMap<>();
		StringBuilder value = new StringBuilder();
		if (result >= 1) {
			value.append("success");
		} else if (result == 0) {
			value.append("invalid");
		} else {
			value.append("network");
		}
		returnValue.put("result", value.toString());
		return returnValue;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 단일라인를 수행결과를 boolean 으로 전달
	 * @param result delete 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #areDeleted(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected boolean isDeleted(int result) {
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * AJAX 통신 시 int형 데이터를 받아 다중라인를 수행결과를 boolean으로 전달
	 * @param result delete 진행후 현재 DBMS 트랜젝션 수행 라인 수
	 * @return  ResponseBody로 전달될 Map
	 * @see #isDeleted(int)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	protected boolean areDeleted(int result) {
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * JSON 문자열을 Map으로 변환하여 전달, value는 String 타입
	 * @param jsonObjectString JSON 형태의 문자열
	 * @return Map으로 파싱된 JSON 문자열
	 * @see #jsonToOMap(String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	public Map<String, String> jsonToMap(String jsonObjectString) {
		Map<String, String> returnValue = new HashMap<>();
		ObjectMapper om = new ObjectMapper();
		try {
			returnValue = om.readValue(jsonObjectString.getBytes(), HashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * JSON 문자열을 Map으로 변환하여 전달, value는 Object 타입
	 * @param jsonObjectString JSON 형태의 문자열
	 * @return Map으로 파싱된 JSON 문자열
	 * @see #jsonToOMap(String)
	 * @author 김재림
	 * @since ReMP v 1.0 +
	 */
	public Map<String, Object> jsonToOMap(String jsonObjectString) {
		Map<String, Object> returnValue = new HashMap<>();
		ObjectMapper om = new ObjectMapper();
		try {
			returnValue = om.readValue(jsonObjectString.getBytes(), HashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}
