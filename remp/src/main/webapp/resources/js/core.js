$(document).ready(function() { // body onload와 겹쳐서 .ready로 변경
	$("#rental").hide(0);
	$("#asset").hide(0);
	$("#EIS").hide(0);
	$("#member").hide(0);
	$("#tr_pwCheck").hide(0);
})

/* ======================================== by 김재림 ================================================= */
/*
 * ReMP에서 사용될 기본 자바스크립트 태그
 * 작성자 : 김재림
 * 버전 v 1.0
 */

/* 서브메뉴 열기 */
/* 로그처리 */
var operationMod = "operation";

function debug(log) {
	if (operationMod == "debug") {
		console.log("[Debug] Message : " + log);
	}
}
/* ------------------------- 네비게이션 컨트롤 ------------------------- */
function getNavSubMenu(targetElement) {
	var targetId = "#" + $(targetElement).data("module");
	
	if ($(targetId).css("display") == "block") {
		$(targetId).slideUp('fast');
		return;
	}
	
	$("#rental").slideUp('fast');	
	$("#asset").slideUp('fast');
	$("#EIS").slideUp('fast');
	$("#member").slideUp('fast');
	$(targetId).slideDown('fast');
}

function setNavSubMenu(takeElement) {
	$(takeElement).slideUp('fast')
}

/* ------------------------- UI 관련 ------------------------- */
/* 메시지창
 * 작성자 : 김재림
 * 버  전 : v 1.2
 */
const MsgWindow = class MsgWindow {
	/* 전역변수
	 * windowType - 창타입
	 * msgWindow - 메시지창 객체
	 * msgWindowHead - 헤드영역
	 * msgWindowDetail - 디테일영역
	 * msgWindowAction - 액션영역
	 */
	//생성자
	constructor(windowType) {
		if (windowType == null) {
			this.windowType == 'plain';
		}
		this.windowType = windowType.toLowerCase();
	}
	//창속성 기본 메시지창, violated : 정책위반창, Error : 에러창, Invalid : 무효창
	setWindowType(windowType) {
		if (windowType == null) {
			this.windowType == 'plain';
		}
		this.windowType = windowType.toLowerCase();
		this.msgWindowHead.removeAttr('class');
		this.msgWindowHead.attr('class', 'msgWindowHead '+this.windowType);
	}
	//현재설정된 메시지창 타입
	getWindowType() {
		return this.windowType;
	}
	//타이틀 및 메시지내용 설정
	setMessage(msgWindowHeadText, msgWindowDetialText) {
		if (msgWindowHeadText == null) {
			msgWindowHeadText == "정보"
		}
		if (this.windowType == null) {
			this.windowType = "plain";
		} else {
			switch (this.windowType) {
			case "violated":
				msgWindowHeadText = "[정책위반] "+msgWindowHeadText;
				break;
			case "error":
				msgWindowHeadText = "[오류] "+msgWindowHeadText;
				break;
			case "invalid":
				msgWindowHeadText = "[무효] "+msgWindowHeadText;
				break;
			default:
				msgWindowHeadText = "[메세지] "+msgWindowHeadText;
				break;
			}
		}
		if (msgWindowDetialText == null || msgWindowDetialText == "") {
			this.msgWindowDetialText = "---------------------------------------메시지 영역---------------------------------------";
		}
		this.msgWindowHead = $('<div />', {class: 'msgWindowHead '+this.windowType, onclick: 'MsgWindow.onTop(this)'});
		this.msgWindowDetail = $('<div />', {class: 'msgWindowDetail'});
		$(this.msgWindowHead).append($('<span />', {class: 'msgWindowHead-text', text: msgWindowHeadText}));
		$(this.msgWindowDetail).append($('<div />', {class: 'msgWindowDetail-text', text: msgWindowDetialText}));
	}
	//타이틀 및 메시지내용 출력
	getMessage() {
		return "제목 : "+this.msgWindowHeadText+", 내용 : "+this.msgWindowDetialText;
	}
	//버튼 설정
	setButton(buttonText, callbackFn) {
		if (this.msgWindowAction == null) {
			this.msgWindowAction = $('<div />', {class: 'msgWindowAction'});
		}
		if (buttonText == null) {
			buttonText == '확인';
		}
		if (callbackFn == null) {
			callbackFn = this.remove();
		}
		$(this.msgWindowAction).append($('<input />', {class: 'btn btn-primary btn-sm msgBtn', type: 'button', value: buttonText, onclick: callbackFn}));
	}
	//화면에 창 설정하기
	show() {
		if (this.msgWindowHead == null || this.msgWindowDetail == null) {
			this.setMessage(null, null);
		}
		$("body").append(this.createMsgWindow(this.windowType, this.msgWindowHead, this.msgWindowDetail));
		$(this.msgWindowAction).children()[0].focus();
	}
	//화면에서 숨기기
	static hide(takeElement) {
		debug(takeElement);
		$(takeElement).parents('div.msgWindow').fadeOut('fast');
		setTimeout(function() {
			$(takeElement).parents('div.msgWindow').remove();
		}, '3000', takeElement);
	}
	//객체삭제
	static remove(takeElement) {
		$(takeElement).parents('div.msgWindow').remove();
	}
	//상단으로 가져오기
	static onTop(takeElement) {
		$('.msgWindow').css('z-index', 'auto');
		$(takeElement).parents('.msgWindow').css('z-index', 1);
	}
	//메시지 객체 생성
	createMsgWindow() {
		this.msgWindow = $('<div />', {class: 'msgWindow'});
		$(this.msgWindow).append(this.msgWindowHead);
		$(this.msgWindow).append(this.msgWindowDetail);
		$(this.msgWindow).append(this.msgWindowAction);
		$(this.msgWindow).draggable();
		return this.msgWindow;
	}
	
	toString() {
		return 'windowType : '+ this.windowType +
			', msgWindowHead : '+ this.msgWindowHead +
			', msgWindowDetail : '+ this.msgWindowDetail +
			', msgWindowAction : '+ this.msgWindowAction;
	}
	//객체초기화
	initialize() {
		 this.windowType = null;
		 this.msgWindow = null;
		 this.msgWindowHead = null;
		 this.msgWindowDetail = null;
		 this.msgWindowAction = null;
	}
}

const PwWindow = class PwWindow {
	/* 전역변수
	 * setting - 콜백함수
	 * passwordWindow - 모달윈도우
	 * pwWindowBody - 모달 내용
	 */
	//생성자
	constructor(setting) {
		this.setting = setting;
		this.passwordWindow = $('<div />', {class: 'passwordWindow'});
			$(this.passwordWindow).data('target', setting);
		this.pwWindowBody = $('<div />', {class: 'pwWindowBody'});
		var title = $('<div />', {});
			$(title).append($('<h3 />', {text: '변경 전 비밀번호 확인'}));
		var form = $('<div />', {});
			$(form).append($('<input />', {name: 'check', class: 'form-control form-control-lg', type: 'password', placeholder: '비밀번호 입력'}))
		var action = $('<div />', {class: 'pwAction'});
			$(action).append($('<input />', {class: 'btn btn-primary btn-lg', type: 'button', style:'float: right;', onclick: 'PwWindow.hide(this)', value:'취소하기'}));
			$(action).append($('<input />', {class: 'btn btn-primary btn-lg', type: 'button', style:'float: right;', onclick: 'PwWindow.confirm(this)', value:'확인하기'}));
			
		$(this.pwWindowBody).append(title);
		$(this.pwWindowBody).append($('<hr>',{}));
		$(this.pwWindowBody).append(form);
		$(this.pwWindowBody).append($('<hr>',{}));
		$(this.pwWindowBody).append(action);
		$(this.passwordWindow).append(this.pwWindowBody);
	}
	
	show() {
		$('body').append(this.passwordWindow);
	}
	
	static hide(takeElement) {
		$(takeElement).parents('.passwordWindow').remove();
	}
	
	static confirm(takeElement) {
		var target = $(takeElement).parents('.passwordWindow');
		var setting = $(target).data('target');
		setting.sData.password = $('input[name=check]').val();
		$(takeElement).parents('.passwordWindow').remove();
		if (setting.password != '') {
			userChangeInvokeAJAX(setting.requestURL, setting.sData, setting.href);
		}
	}
}
/* ------------------------- 일반기능 ------------------------- */
function setToDate(fromDateValue) {
	$('#toDate').attr('min', fromDateValue);
}

function addToDate(takeElement) {
	var fromDate = $('#fromDate').val().split('-');
	debug(fromDate);
	if (fromDate.length <= 1) {
		var today = new Date();
		var todayYear = today.getFullYear();
		var todayMonth = today.getMonth() + 1;
		todayMonth = (todayMonth < 10)? '0' + todayMonth: todayMonth;
		var todayDay = today.getDate();
		todayDay = (todayDay < 10)? '0' + todayDay: todayDay;
		$('#fromDate').val(todayYear+'-'+todayMonth+'-'+todayDay);
		fromDate = $('#fromDate').val().split('-');
	}
	
	var addDate = $(takeElement).data('add-date').split('-');
	var year = parseInt(fromDate[0]) + parseInt(addDate[0]);
	var month = parseInt(fromDate[1]) + parseInt(addDate[1]) - 1;
	var day = parseInt(fromDate[2]) + parseInt(addDate[2]);
	var toDate = new Date(year, month, day, 0, 0, 0, 0);
	year = toDate.getFullYear();
	month = toDate.getMonth() + 1;
	month = (month < 10)? '0' + month : month;
	day = toDate.getDate();
	day = (day < 10)? '0' + day : day;
	debug(year+'-'+month+'-'+day);
	$('#toDate').val(year+'-'+month+'-'+day);
}

function initDate() {
	$('#toDate').val('');
	$('#fromDate').val('');
}

function setDueDiligenceForm() {
	$('input[name=planId]').val($(headElement).data('planId'))
	$('input[name=fromDate]').val($(headElement).data('fromDate'));
	$('input[name=toDate]').val($(headElement).data('toDate'));
	$('input[name=misc]').val($(headElement).data('misc'));
}

/* 우편번호 */
function getPost(takeElement, takeElement2) {
	new daum.Postcode({
        oncomplete: function(data) {
            var fullAddr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                fullAddr = data.roadAddress;
            } else {
                fullAddr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                if(data.bname !== '') {
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }
            $(takeElement).val(data.zonecode);
            $(takeElement2).val(fullAddr);
        }
    }).open();
}

function userChangeInvoke(form) {
	var sData = {};
	var a = '';
	$.each($(form).serializeArray(), function(i, item){
		a += (i != 0)? ',' : '';
		a += item.name+':"'+item.value+'"';
	})
	eval("sData = {"+a+"}");
	var setting = {
			requestURL:$(form).attr('action'),
			sData:sData,
			href:'gouserchange.do'
	}
	new PwWindow(setting).show();
	return false;
}

/* ------------------------- 비동기 통신 ------------------------- */
/* ajax 공통 */
var loading = {
	on: $('<div />', {class: 'loading'}),
}

/* 회원정보 변경 공통 AJAX */
function userChangeInvokeAJAX(requestURL, sData, href) {
	debug(requestURL);
	debug(sData);
	debug(href);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : JSON.stringify(sData),
		dataType : "JSON",
		async: false,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			switch (rData.result) {
			case "success":
				var msg = new MsgWindow('plain');
				location.href = href;
				break;
			case "invalid":
				var msg = new MsgWindow('invalid');
				msg.setMessage('변경오류','변경할 항목이 존재하지 않거나, 변경할 수 없는 상태입니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "violated":
				var msg = new MsgWindow('violated');
				msg.setMessage('정책위반','정책위반이 발생하였습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "network":
				var msg = new MsgWindow('error');
				msg.setMessage('네트워크 오류','네트워크에 문제가 있습니다. 관리자에게 문의하십시오.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
	
}

/* head_detail에서 head 일련번호 세팅하기 */
var headSeq = null;
var selectedHeadSeq = null;
var headElement = null;

function setHeadSeqRequest(takeElement) {
	headElement = takeElement;
	var callbackfunction = $(takeElement).data("fn-name"); //data-fn-name=?
	if (callbackfunction.substr(callbackfunction.length - 1) != ')') {
		callbackfunction += "(headSeq)";
	}
	headSeq = $(takeElement).data("key"); // data-key=?
	debug(headSeq);
	$(takeElement).parents("table")
		.children()
		.children(".table-success")
		.removeClass("table-success");
	$(takeElement).addClass("table-success");
	debug(callbackfunction);
	eval(callbackfunction);
}

/* head_detail에서 head 일련번호 가져오기 */
function getHeadSeqRequest() {
	return headSeq;
}

/* 렌탈요청헤드 조회 
 * success - 헤드부분 테이블 생성
 * error - 에러 메시지 알림창
 */
function getRentalRequest(takeElement){
	var requestURL = "getrentalrequest.do";
	var keyword = $("#keyword").val();
	if (keyword == null || keyword == "") {
		keyword = "OR";
	}
	var sData = JSON.stringify({
		keyword:keyword
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			//테이블데이터 생성부분
			for (var i = 0; i < rData.length; i++) {
				ihtml += "<tr data-fn-name='getassetlist' data-key='"+rData[i].itemId+"' data-request-id='"+rData[i].reqId+"' onclick='setHeadSeqRequest(this)'>";
				ihtml += "<td>"+rData[i].reqId+"</td><td>"+rData[i].reqTransDate+"</td>";
				ihtml += "</tr>";
			}
			debug(ihtml);
			$("#rentalRequestResult tbody").html(ihtml);
			$('.loading').remove();
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			setTimeout(3000);
			$(".loading").remove();
		}
	});
}

/* 품목조회(콜백함수)
 * success - 품목별 테이블 생성
 * error - 에러 알림창
 */
function getassetlist(productId) {
	if (productId == selectedHeadSeq) {
		debug("already chosen")
		return;
	}
	selectedHeadSeq = productId;
	var requestURL = "getassetlist.do";
	var sData = JSON.stringify({
		productId:productId
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			if (rData.length == 0) {
				var msg = new MsgWindow('plain');
				msg.setMessage('조회내용 없음','조회된 결과가 없습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
			}
			//테이블데이터 생성부분
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				ihtml += "<tr data-item-id='"+ row.id
					+"' data-item-name='"+ row.name
					+"' data-item-entrydate='"+ row.entrydate
					+"' data-item-recentdate='"+ row.recentdate
					+"' data-item-unstorecount='"+ row.unstorecount
					+"' data-item-price='"+ row.price+"'>";
				ihtml += "<td>"+row.id+
					"</td><td>"+row.name+
					"</td><td>"+row.entrydate+
					"</td><td>"+row.recentdate+
					"</td><td>"+row.unstorecount+
					"</td><td>"+row.price+
					"</td><td><button class='btn btn-primary btn-sm' onclick='setUnstoreRequest(this)'>출고요청</button></td></tr>";
			}
			debug(ihtml);
			$("#productList").html(ihtml);
			if ($(".table-success").data("flag") == "true") {
				$("table button").attr("disabled", true);
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 자산관리자 출고처리
 * success - 해당자산 영업 출고
 * error - 에러 메시지 알림창
 */
function setUnstoreRequest(takeElement) {
	var requestURL = "rentalassetconfirm.do";
	var sData = JSON.stringify({
		id:$(takeElement).parents("tr").data("item-id"),
		requestId:$(headElement).data("request-id")
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			switch (rData.result) {
			case "success":
				msgBox = new MsgWindow('plain');
				msgBox.setMessage('출고성공!','['+rData.id+']가 정상적으로 렌트출고요청 되었습니다.');
				msgBox.setButton('확인','MsgWindow.hide(this)');
				msgBox.show();
				$("table button").attr("disabled", true);
				$(".table-success").data("flag", "true");
				$(".table-success").addClass("table-danger");
				break;
			case "invalid":
				var msg = new MsgWindow('invalid');
				msg.setMessage('자산상태오류',rData.id+' 가 존재하지 않거나, 변경할 수 없는 상태입니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "violated":
				var msg = new MsgWindow('violated');
				msg.setMessage('정책위반','정책위반이 발생하였습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "network":
				var msg = new MsgWindow('error');
				msg.setMessage('네트워크 오류','네트워크에 문제가 있습니다. 관리자에게 문의하십시오.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 요청별 자산조회
 * success - 요청내용별 자산리스트 생성
 * error - 에러 메시지 알림창
 */
function getRequestAssetList(takeData) {
	if (takeData == selectedHeadSeq) {
		debug("already chosen")
		return;
	}
	selectedHeadSeq = takeData;
	var requestURL = "getrequestassetlist.do";
	var sData = JSON.stringify({
		assetState:takeData
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			//테이블데이터 생성부분
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				ihtml += "<tr data-request-day='"+ row.requestDay
					+"' data-request-id='"+ row.requestId
					+"' data-product-id='"+ row.productId
					+"' data-item-id='"+ row.itemId
					+"' data-asset-state='"+ row.assetState
					+"' data-asset-amount='"+ row.amount +"'>";
				ihtml += "<td>"+row.requestDay+
					"</td><td>"+row.requestId+
					"</td><td>"+row.productId+
					"</td><td>"+row.itemId+
					"</td><td>"+row.amount+
					"</td><td><button class='btn btn-primary btn-sm' onclick='setUnstore(this)'>출고</button></td></tr>"
			}
			debug(ihtml);
			$("#productList").html(ihtml);
			if ($(".table-success").data("flag") == "true") {
				$("table button").attr("disabled", true);
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 요청별 검색 자산조회
 * success - 요청내용별 자산리스트 생성
 * error - 에러 메시지 알림창
 */
function getrequestsearchassetlist() {
	var requestURL = "getrequestsearchassetlist.do";
	var sData = JSON.stringify({
		assetState:headSeq,
		keyword:$("#keyword").val()
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			//테이블데이터 생성부분
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				ihtml += "<tr data-item-id='"+ row.id
					+"' data-item-name='"+ row.name
					+"' data-item-entrydate='"+ row.entrydate
					+"' data-item-recentdate='"+ row.recentdate
					+"' data-item-unstorecount='"+ row.unstorecount
					+"' data-item-price='"+ row.price+"'>";
				ihtml += "<td>"+row.id+
					"</td><td>"+row.name+
					"</td><td>"+row.entrydate+
					"</td><td>"+row.recentdate+
					"</td><td>"+row.unstorecount+
					"</td><td>"+row.price+
					"</td><td><button class='btn btn-primary btn-sm' onclick='setUnstore(this)'>출고</button></td></tr>"
			}
			debug(ihtml);
			$("#productList").html(ihtml);
			if ($(".table-success").data("flag") == "true") {
				$("table button").attr("disabled", true);
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 출고관리자 출고처리
 * success - 해당자산 출고처리
 * error - 에러 메시지 알림창
 */
function setUnstore(takeElement) {
	var requestURL = "setunstore.do";
	var sData = JSON.stringify({
		productId:$(takeElement).parents("tr").data("product-id"),
		requestId:$(takeElement).parents("tr").data("request-id"),
		assetState:$(takeElement).parents("tr").data("asset-state")
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			switch (rData.result) {
			case "success":
				var msg = new MsgWindow('plain');
				msg.setMessage('출고성공',rData.id+' 가 정상적으로 출고 되었습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				$(takeElement).attr("disabled", true);
				$(".table-success").data("flag", "true");
				break;
			case "invalid":
				var msg = new MsgWindow('invalid');
				msg.setMessage('자산상태오류',rData.id+' 가 존재하지 않거나, 변경할 수 없는 상태입니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "violated":
				var msg = new MsgWindow('violated');
				msg.setMessage('정책위반','정책위반이 발생하였습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "network":
				var msg = new MsgWindow('error');
				msg.setMessage('네트워크 오류','네트워크에 문제가 있습니다. 관리자에게 문의하십시오.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 실사계획 불러오기
 * success - 요청 리스트 조회
 * error - 에러 메시지
 */
function getDueDiligencePlanList() {
	var requestURL = "getduediligenceplanlist.do";
	var keyword = $("#keyword").val();
	if (keyword == null || keyword == "") {
		var msg = new MsgWindow('violated');
		msg.setMessage('검색어 정책위반','검색어를 입력하지 않아 조회할 수 없습니다. 확인 후 다시 시도해주십시요.');
		msg.setButton('확인','MsgWindow.hide(this)');
		msg.show();
		return;
	}
	$('#dueDiligenceplanListResult tbody tr').remove();
	var sData = JSON.stringify({
		keyword:keyword
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			for (var index = 0; index < rData.length; index++) {
				var tr = $('<tr />', {onclick: 'setHeadSeqRequest(this)'});
				$(tr).data('key', rData[index].planId);
				$(tr).data('plan-id', rData[index].planId);
				$(tr).data('from-date', rData[index].fromDate);
				$(tr).data('to-date', rData[index].toDate);
				$(tr).data('misc', rData[index].misc);
				$(tr).data('fn-name', 'setDueDiligenceForm()');
				$(tr).append($('<td />', {text: rData[index].planId}));
				$(tr).append($('<td />', {text: rData[index].fromDate}));
				$('#dueDiligenceplanListResult tbody').append(tr);
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 실사계획 등록하기
 * success - 실사계획 등록 후 결과 메시지
 * error - 에러메시지
 */
function newDueDiligencePlan(takeElement) {
	var requestURL = "newduediligenceplan.do";
	var sData = "{";
	$($('form[name=due_diligence]').serializeArray()).each(function(i){
		sData += (i != 0)? ',' : '';
		sData += '"'+this.name+'":"'+this.value+'"';
	})
	sData += "}";
	var errorField = "";
	var jsonObj = JSON.parse(sData);
	
	if (jsonObj.planId == '') {
		errorField = "실사아이디";
	} else if (jsonObj.fromDate == '') {
		errorField = "시작날짜";
	} else if (jsonObj.toDate == '') {
		errorField = "종료날짜";
	} else if (jsonObj.fromDate > jsonObj.toDate) {
		errorField = "올바른 날짜정보"
	}
	
	if (errorField != '') {
		var msg = new MsgWindow('invalid');
		msg.setMessage('데이터 오류',errorField+'가 입력되지 않았습니다. 확인해주세요.');
		msg.setButton('확인','MsgWindow.hide(this)');
		msg.show();
		return;
	}
	
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			switch (rData.result) {
			case "success":
				var msg = new MsgWindow('plain');
				msg.setMessage('실사계획 저장완료','실사계획이 저장 되었습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				$('form[name=due_diligence]').each(function(){
					this.reset();
				})
				break;
			case "invalid":
				var msg = new MsgWindow('invalid');
				msg.setMessage('실사계획 상태오류','실사계획이 존재하지 않거나, 변경할 수 없는 상태입니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "violated":
				var msg = new MsgWindow('violated');
				msg.setMessage('정책위반','정책위반이 발생하였습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "network":
				var msg = new MsgWindow('error');
				msg.setMessage('네트워크 오류','네트워크에 문제가 있습니다. 관리자에게 문의하십시오.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 실사계획 변경하기
 * success - 실사계획 변경 후 결과 메시지
 * error - 에러메시지
 */
function setDueDiligencePlan(takeElement) {
	var requestURL = ".do";
	var sData = "{";
	$($('form[name=due_diligence]').serializeArray()).each(function(i){
		sData += (i != 0)? ',' : '';
		sData += '"'+this.name+'":"'+this.value+'"';
	})
	sData += "}";
	var errorField = "";
	var jsonObj = JSON.parse(sData);
	
	if (jsonObj.planId == '') {
		errorField = "실사아이디";
		$('input[name=planId]').focus();
	} else if (jsonObj.fromDate == '') {
		errorField = "시작날짜";
		$('input[name=fromDate]').focus();
	} else if (jsonObj.toDate == '') {
		errorField = "종료날짜";
		$('input[name=toDate]').focus();
	} else if (jsonObj.fromDate > jsonObj.toDate) {
		errorField = "올바른 날짜정보"
		$('input[name=toDate]').focus();
	}
	
	if (errorField != '') {
		var msg = new MsgWindow('invalid');
		msg.setMessage('데이터 오류',errorField+'가 입력되지 않았습니다. 확인해주세요.');
		msg.setButton('확인','MsgWindow.hide(this)');
		msg.show();
		return;
	}
	
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			switch (rData.result) {
			case "success":
				var msg = new MsgWindow('plain');
				msg.setMessage('실사계획 저장완료','실사계획이 저장 되었습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				$(takeElement).attr('disabled', true);
				$('form[name=due_diligence] input').attr('disabled', true);
				break;
			case "invalid":
				var msg = new MsgWindow('invalid');
				msg.setMessage('실사계획 상태오류','실사계획이 존재하지 않거나, 변경할 수 없는 상태입니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "violated":
				var msg = new MsgWindow('violated');
				msg.setMessage('정책위반','정책위반이 발생하였습니다.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			case "network":
				var msg = new MsgWindow('error');
				msg.setMessage('네트워크 오류','네트워크에 문제가 있습니다. 관리자에게 문의하십시오.');
				msg.setButton('확인','MsgWindow.hide(this)');
				msg.show();
				break;
			}
		},
		error : function() {
			var msg = new MsgWindow('error');
			msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
			msg.setButton('확인','MsgWindow.hide(this)');
			msg.show();
		},
		complete : function() {
			debug('comlete');
			$(".loading").remove();
		}
	});
}

/* ======================================== by 이동훈 ================================================= */

/**
 * @param takeElement
 * @returns 
 */
function setOutsideHeadSeqRequest(takeElement) {
	var callbackfunction = $(takeElement).data("fn-name");
	headSeq = $(takeElement).data("key");//data-key=?
	var headSeq2 = $(takeElement).data("key2");//data-key=?
	$(takeElement).parents("table")
		.children()
		.children(".table-success")
		.removeClass("table-success");
	$(takeElement).addClass("table-success");
	debug(callbackfunction+"(headSeq, headSeq2)");
	eval(callbackfunction+"(headSeq, headSeq2)");
	$("#di_request").attr("class", "tab-pane fade active in active show")
    $("#firstTab").attr("class", "nav-link active")
    $("#di_asSubmit").attr("class", "tab-pane fade")
    $("#secondTab").attr("class", "nav-link")
}

/**
 * as,회수요청 리스트 조회
 * @param takeElement
 * @returns
 */
function getVisitRequest(takeElement){
   var requestURL = "getVisitRequest.do";
   var key = $("#keyword").val();
   var sData = JSON.stringify({
	  select:$("#sel_visit").val(),
      keyword:$("#keyword").val(),
      pagetype:$("#pagetype").val()
   });
   debug(sData);
   debug(requestURL);
   $.ajax({
      type : "POST",
      url : requestURL,
      data : sData,
      dataType : "JSON",
      async: true,
      contentType:'application/json;charset=UTF-8',
      beforeSend: function() {
         $("#content").append(loading.on);
      },
      success : function(rData) {
         var ihtml = "";
         for (var i = 0; i < rData.length; i++) {
            ihtml += "<tr data-fn-name='getVisitList' data-key='"+rData[i].buyId+"' data-key2='"+rData[i].viId+"' onclick='setOutsideHeadSeqRequest(this)'>";
            ihtml += "<td>"+rData[i].cuId+"</td><td>"+rData[i].itName+"</td><td>"+rData[i].viDay+"</td><td>"+rData[i].viComplete+"" +
            		"<input type='hidden' id='hi_state' name='hi_state' value='As'><input type='hidden' id='hi_key'' name='hi_key' value="+key+"></td>";
            ihtml += "</tr>";
         }
         $("#visitRequestResult tbody").html(ihtml);
         $('.loading').remove();
      },
      error : function() {
    	  document.location = "goRequestError.do";
      },
      complete : function() {
         $(".loading").remove();
      }
   });
}

/**
 * as요청품목조회
 * @param productId
 * @param visitId
 * @returns
 */
function getVisitList(productId, visitId) {
	if (productId == selectedHeadSeq) {
		return;
	}
	selectedHeadSeq = productId;
	var requestURL = "getVisitList.do";
	var sData = JSON.stringify({
		productId:""+productId,
		visitId:""+visitId
	});
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = rData.prId;
			$("#la_prId").html(rData.prId);
	        $("#la_itCode").html(rData.itCode);
	        $("#la_itName").html(rData.itName);
	        $("#la_prManufacturer").html(rData.prManufacturer);
	        $("#la_viName").html(rData.viName);
	        $("#la_viMobile").html(rData.viMobile);
	        $("#la_viPost").html(rData.viPost);
	        $("#la_viAddr").html(rData.viAddr);
	        $("#la_viAddD").html(rData.viAddD);
	        $("#la_buyStart").html(rData.buyStart);
	        $("#la_buyEnd").html(rData.buyEnd);
	        $("#la_viDay").html(rData.viDay);
	         
	        $("#hi_prId").val(rData.prId);
	        $("#hi_itName").val(rData.itName);
	        $("#hi_viId").val(rData.viId);
	        $("#hi_viName").val(rData.viName);
	        if((typeof(rData.outDay) != 'undefined') && rData.outDay != null){
	            var datee = rData.outDay;
	            var chDate = datee.substring(0,10)+"T"+datee.substring(11);
	            $("#hi_outDay").val(chDate);
	         }
	        $("#hi_cuId").val(rData.cuId);
	        $("#hi_buyId").val(productId);
	        $("#hi_outId").val(rData.outId);
	        $("#hi_outSort").val(rData.outSort);
	        $("#hi_outContent").val(rData.outContent);
	        $("#hi_outState").val(rData.outState);
	        $("#hi_viCode").val($("#hi_state").val());
	        $("#hi_keyValue").val($("#hi_key").val());
		},
		error : function() {
			var a = new MsgWindow("error");
			a.setMessage("검색에러", "검색에 실패하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * as요청 저장하기
 * @param targetEelement
 * @returns
 */
function setFixInfo(targetEelement) {
	var requestURL = "setFixInfo.do";
	var sData = formChange($(targetEelement).parents("form"))
	var key = $("#hi_keyValue").val();
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "text",	// json하면 오류
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var a = new MsgWindow("plain");
			a.setMessage("등록완료", "결과가 등록되었습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
			getVisitRequest(key);
		},
		error : function(request,status,error) {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 자산관리자 자산리스트 가져오기
 * @param takeElement
 * @returns
 */
function getProductRequest(takeElement){
	var requestURL = "getProductRequest.do";
	var keyValue = $("#keyword").val();
	debug("keyValue" + keyValue);
	if(keyValue == ""){
		keyValue = " ";
	}
	var sData = JSON.stringify({
		keyword:keyValue
	});
	debug("sData : " + sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			var qhtml = "";
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				debug("123123" + row.itId)
				ihtml += "<tr data-item-prid='"+ row.prId
					+"' data-item-buyid='"+ row.buyId
					+"' data-item-itid='"+ row.itId
					+"' data-item-itname='"+ row.itName
					+"' data-item-prfirstday='"+ row.prFirstDay
					+"' data-item-prinday='"+ row.prInDay
					+"' data-item-proutday='"+ row.prOutDay
					+"' data-item-prouttarget='"+ row.prOutTarget
					+"' data-item-prlocation='"+ row.prLocation
					+"' data-item-prstate='"+ row.prState
					+"' data-item-prcount='"+ row.prCount
					+"' data-item-prqr='"+ row.prQr
					+"' data-item-keyvalue='"+ keyValue+"'>";
				ihtml += "<td>"+row.prId+
					"</td><td>"+row.buyId+
					"</td><td>"+row.itId+
					"</td><td>"+row.itName+
					"</td><td>"+row.prFirstDay+
					"</td><td>"+row.prInDay+
					"</td><td>"+row.prOutDay+
					"</td><td>"+row.prOutTarget+
					"</td><td>"+row.prLocation+
					"</td><td>"+row.prState+
					"</td><td>"+row.prCount+
					"</td><td>" +
					"</td><td><div style='display: none' id='donghun'>"+row.prQr+
					"</div><button class='btn btn-primary btn-sm' onclick='doQrPrint(this)'>QR인쇄</button>"+
					"</td><td><button class='btn btn-primary btn-sm' onclick='doProductUpdate(this)''>수정</button></td></tr>"
			}
			debug(ihtml);
			$("#productRequestResult tbody").html(ihtml);
			$("#printQR").html(qhtml);
			$('.loading').remove();
		},
		error : function() {
			var a = new MsgWindow("error");
			a.setMessage("검색결과없음", keyValue + "에 대한 검색결과가 없습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 자산관리 QR코드 인쇄하기
 * @param takeElement
 * @returns
 */
function doQrPrint(takeElement){
	var QR = $(takeElement).parents("tr").find("div").html();
	var objWin = window.open('', 'print', 'height=600,width=800');
	objWin.document.write("<div><img src='resources/images/");
	objWin.document.write(QR);
	objWin.document.write("'/></div>");
	objWin.print();
	objWin.close();
}

/**
 * 자산관리자 자산변경폼 가져오기
 * @param takeElement
 * @returns
 */
function doProductUpdate(takeElement) {
	var requestURL = "getProductUpdate.do";
	var sData = JSON.stringify({
		prId:$(takeElement).parents("tr").data("item-prid"),
		itId:""+$(takeElement).parents("tr").data("item-itid"),
		keyvalue:$(takeElement).parents("tr").data("item-keyvalue")
	});
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			$("#productCare").hide();
			$("#productUpdate").fadeIn(100);
			$("#img_itImage").attr("src", "\resources\images" + rData.itImage)
			$("#fi_prId").val(rData.prId);
			$("#hi_itImage").val(rData.itImage)
			$("#tb_prId").val(rData.prId)
			$("#tb_itName").val(rData.itName)
			$("#tb_itCode").val(rData.itCode)
			$("#tb_itManufacturer").val(rData.itManufacturer)
			if((typeof(rData.prInDay) != 'undefined') && (rData.prInDay != null)) {
				if((rData.prInDay.length > 0)){
					var datee = rData.prInDay;
					var chDate = datee.substring(0,10)+"T"+datee.substring(11);
					$("#tb_prInDay").val(chDate);
				}
			} else {
				$("#tb_prInDay").val("");
			}
			$("#tb_itAcquisition").val(rData.itAcquisition)
			$("#tb_itPrice").val(rData.itPrice)
			$("#sb_prState").val(rData.prState)
			debug("rData.prState : " + rData.prState)
			$("#tb_prCount").val(rData.prCount)
			
			if((typeof(rData.prOutDay) != 'undefined') && (rData.prOutDay != null)) {
				if((rData.prOutDay.length > 0)){
					var datee = rData.prOutDay;
			        var chDate = datee.substring(0,10)+"T"+datee.substring(11);
			        $("#tb_prOutDay").val(chDate);
				}
			} else {
				$("#tb_prOutDay").val("");
			}
			$("#tb_prOutTarget").val(rData.prOutTarget)
			$("#tb_itPeriod").val(rData.itPeriod)
			$("#tb_prLocation").val(rData.prLocation)
			$("#ta_itContent").val(rData.itContent)
			$("#ta_prNeeds").val(rData.prNeeds)
			$("#hi_keyword").val($(takeElement).parents("tr").data("item-keyvalue"))
			$("#hi_itId").val($(takeElement).parents("tr").data("item-itid"))
	        $("#img_itImage").attr("src", rData.itImage);
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * a자산관리자 자산변경하기(개별)
 * @param targetEelement
 * @returns
 */
function setProductUpdate(targetEelement) {
	var requestURL = "setProductUpdate.do";
	var sData = formChange($(targetEelement).parents("form"))
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var key = $("#hi_keyword").val();
			$("#productCare").fadeIn(100);
			$("#productUpdate").hide();
			var a = new MsgWindow("plain");
			a.setMessage("변경성공", "변경되었습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
			getProductRequest(key)
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 자산관리자 자산변경하기(전체)
 * @param targetEelement
 * @returns
 */
function setProductUpdateAll(targetEelement) {
	var tmpImage = $("#tb_file").val()
	var image = tmpImage.substring(12)
	var tmpItId = $("#tb_itId").val();
	var itId = tmpItId + "_" + image;
	$("#tb_itImage").val(itId)
	$("#hi_itImage").val(tmpImage)
	var requestURL = "setProductUpdateAll.do";
	var sData =  formChange($(targetEelement).parents("form"));
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var key = $("#hi_keyword").val();
			$("#productCare").fadeIn(100);
			$("#productUpdate").hide();
			$("#ta_itContent").attr("readonly", true);
			$("#tb_itPeriod").attr("readonly", true);
			$("#tb_itPrice").attr("readonly", true);
			$("#tb_itAcquisition").attr("readonly", true);
			$("#fi_itImage").attr("disabled", true);
			$("#ta_itContent").css("background-color", "lightgray");
			$("#tb_itPeriod").css("background-color", "lightgray");
			$("#tb_itPrice").css("background-color", "lightgray");
			$("#tb_itAcquisition").css("background-color", "lightgray");
			$("#tb_itManufacturer").css("background-color", "lightgray");
			$("#tb_itCode").css("background-color", "lightgray");
			$("#tb_itName").css("background-color", "lightgray");
			$("#bt_doAllEdit").show();
			$("#bt_edit").show();
			$("#bt_allEdit").hide();
			fileUpload();
			var a = new MsgWindow("plain");
			a.setMessage("변경성공", "변경되었습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
			getProductRequest(key)
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 자산등록 요청 리스트 조회
 * @returns
 */
function getProductInsertRequest(){
   var requestURL = "getProductInsertRequest.do";
   $.ajax({
      type : "POST",
      url : requestURL,
      dataType : "JSON",
      async: true,
      contentType:'application/json;charset=UTF-8',
      beforeSend: function() {
         $("#content").append(loading.on);
      },
      success : function(rData) {
         var ihtml = "";
         for (var i = 0; i < rData.length; i++) {
            ihtml += "<tr data-fn-name='getProductInsertList' data-key='"+rData[i].prId+"' onclick='setHeadSeqRequest(this)'>";
            ihtml += "<td>"+rData[i].prId+"</td><td>등록대기</td><td>"+rData[i].prFDay+"</td>";
            ihtml += "</tr>";
         }
         $("#procutRequestList tbody").html(ihtml);
         $('.loading').remove();
      },
      error : function() {
    	  document.location = "goRequestError.do";
      },
      complete : function() {
         $(".loading").remove();
      }
   });
}

/**
 * 자산등록 등록폼 가져오기
 * @param productId
 * @returns
 */
function getProductInsertList(productId) {
	if (productId == selectedHeadSeq) {
		debug("already chosen")
		return;
	}
	selectedHeadSeq = productId;
	var requestURL = "getProductInsert.do";
	var sData = JSON.stringify({
		productId:productId,
	});
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = rData.prId;
	        $("#tb_prId").val(rData.prId);
	        $("#fi_prId").val(rData.prId);
	        $("#tb_itId").val(rData.itId);
	        $("#tb_itManufacturer").val(rData.itManufacturer);
	        $("#tb_itName").val(rData.itName);
	        $("#tb_itCode").val(rData.itCode);
	        if((typeof(rData.prFirstDay) != 'undefined') && (rData.prFirstDay != null)) {
	        	if((rData.prFirstDay.length > 0)){
	        		var datee = rData.prFirstDay;
	        		var chDate = datee.substring(0,10)+"T"+datee.substring(11);
	        		$("#tb_prFirstDay").val(chDate);
	        	}
			} else {
				$("#tb_prFirstDay").val("");
			}
			if((typeof(rData.prInDay) != 'undefined') && (rData.prInDay != null)) {
				if((rData.prInDay.length > 0)){
					var datee = rData.prInDay;
					var chDate = datee.substring(0,10)+"T"+datee.substring(11);
					$("#tb_prInDay").val(chDate);
				}
			} else {
				$("#tb_prInDay").val("");
			}
			$("#tb_itAcquisition").val(rData.itAcquisition)
			$("#tb_itPrice").val(rData.itPrice)
			$("#sb_prState").val(rData.prState)
			$("#tb_prCount").val(rData.prCount)
			
			if((typeof(rData.prOutDay) != 'undefined') && (rData.prOutDay != null)) {
				if((rData.prOutDay.length > 0)){
					var datee = rData.prOutDay;
			        var chDate = datee.substring(0,10)+"T"+datee.substring(11);
			        $("#tb_prOutDay").val(chDate);
				}
			} else {
				$("#tb_prOutDay").val("");
			}
	        $("#tb_prOutTarget").val(rData.prOutTarget);
	        $("#tb_prLocation").val(rData.prLocation);
	        $("#tb_itPeriod").val(rData.itPeriod);
	        $("#tb_itContent").val(rData.itContent);
	        $("#tb_prQr").val(rData.prQr);
	        $("#img_itImage").attr("src", rData.itImage);
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 자산등록하기
 * @param targetEelement
 * @returns
 */
function setProductInsert(targetEelement) {
	var requestURL = "setProductInsert.do";
	var tmpImage = $("#tb_file").val()
	var image = tmpImage.substring(12)
	var tmpItId = $("#tb_itId").val();
	var itId = tmpItId + "_" + image;
	$("#tb_itImage").val(itId)
	var sData = formChange($(targetEelement).parents("form"))
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var a = new MsgWindow("plain");
			a.setMessage("등록완료", "등록에 성공하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
			fileUpload();
			getProductInsertRequest();
		},
		error : function() {
			var a = new MsgWindow("error");
			a.setMessage("자산등록실패", "입력값을 한번 더 확인하여주세요.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 이미지 업로드하기
 * @returns
 */
function fileUpload() {
	var requestURL = "fileUpload.do";
	$('#uploadImageForm').ajaxForm({
		type : "POST",
		url : requestURL,
		dataType : "text",
		async: false,
		method: "post",
		enctype: "multipart/form-data",
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var a = new MsgWindow("plain");
			a.setMessage("성공", "이미지 등록에 성공하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		error:function(){
			var a = new MsgWindow("error");
			a.setMessage("실패", "이미지 등록에 실패하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
	$("#uploadImageForm").submit();
}

/**
 * 자산관리 검색유지시키며 뒤로가기
 * @param takeElement
 * @returns
 */
function doKeywordBack(takeElement){
	$("#productCare").fadeIn(100);
	$("#productUpdate").hide();
	getProductRequest(takeElement)
}

/**
 * 자산관리 전체변경 시 disabled 및 readonly 풀어주기
 * @returns
 */
function doAllEdit(){
	$("#bt_doAllEdit").hide();
	$("#bt_edit").hide();
	$("#bt_allEdit").show();
	var a = new MsgWindow("plain");
	a.setMessage("전체변경", "이제 품목관련 항목도 변경이 가능합니다.");
	a.setButton("확인", "MsgWindow.hide(this)");
	a.show();
	$("#ta_itContent").attr("readonly", false);
	$("#tb_itPeriod").attr("readonly", false);
	$("#tb_itPrice").attr("readonly", false);
	$("#tb_itAcquisition").attr("readonly", false);
	$("#tb_itManufacturer").attr("readonly", false);
	$("#tb_itCode").attr("readonly", false);
	$("#tb_itName").attr("readonly", false);
	
	$("#ta_itContent").css("background-color", "white");
	$("#tb_itPeriod").css("background-color", "white");
	$("#tb_itPrice").css("background-color", "white");
	$("#tb_itAcquisition").css("background-color", "white");
	$("#tb_itManufacturer").css("background-color", "white");
	$("#tb_itCode").css("background-color", "white");
	$("#tb_itName").css("background-color", "white");
	$("#tb_file").attr("disabled", false);
}

/* 상담 고객 정보 조회 */
/**
 * 상담 고객정보조회
 * @param takeElement
 * @returns
 */
function getVisitCustomer(takeElement) {
      var sData = JSON.stringify({
         memberId:$(takeElement).val()
      });
      $.ajax({
         type : "POST",
         url : "getCustomerInfo.do",
         data : sData,
         dataType : "JSON",
         async: true,
         contentType:'application/json;charset=UTF-8',
         beforeSend: function() {
            $("#content").append(loading.on);
         },
         success : function(rData) {
            if(!rData.isEmpty){
               $(takeElement).parents("form").children().find("#tb_cuName").val(rData.memName);
               $(takeElement).parents("form").children().find("#tb_cuBirth").val(rData.memBirth);
               $(takeElement).parents("form").children().find("#tb_post").val(rData.memPost);
               $(takeElement).parents("form").children().find("#tb_addr").val(rData.memAddr);
               $(takeElement).parents("form").children().find("#tb_addD").val(rData.memAddD);
               $(takeElement).parents("form").children().find("#tb_cuResult").val("회원입니다.");
               $(takeElement).parents("form").children().find("#tb_cuName").prop("readonly", true);
               $(takeElement).parents("form").children().find("#tb_cuBirth").prop("readonly", true);
               $(takeElement).parents("form").children().find("#bt_temporary").prop("disabled", true);
               if($(takeElement).parents("form").attr("name") != "rentalAdviceForm"){
                  getVisitItem(takeElement);
               }
            } else {
               $(takeElement).parents("form").children().find("#tb_cuName").val("");
               $(takeElement).parents("form").children().find("#tb_cuBirth").val("");
               $(takeElement).parents("form").children().find("#tb_post").val("");
               $(takeElement).parents("form").children().find("#tb_addr").val("");
               $(takeElement).parents("form").children().find("#tb_addD").val("");
               $(takeElement).parents("form").children().find("#tb_cuResult").val("비회원입니다.");
               if($(takeElement).parents("form").attr("name") == "rentalAdviceForm"){
                  $(takeElement).parents("form").children().find("#tb_cuName").prop("readonly", false);
                  $(takeElement).parents("form").children().find("#tb_cuBirth").prop("readonly", false);
                  $(takeElement).parents("form").children().find("#bt_temporary").prop("disabled", false);
               }
            }
         },
         error : function() {
            var a = new MsgWindow("error");
            a.setMessage("실패", "검색결과가 없습니다.");
            a.setButton("확인", "MsgWindow.hide(this)");
            a.show();
         },
         complete : function() {
            $(".loading").remove();
       }
   });
}

/**
 * 상담 자산정보조회
 * @param takeElement
 * @returns
 */
function getVisitItem(takeElement) {
	   var sData = JSON.stringify({
	      memberId:$(takeElement).val()
	   });
	   var ihtml="";
	   $.ajax({
	      type : "POST",
	      url : "getItemInfo.do",
	      data : sData,
	      dataType : "JSON",
	      async: true,
	      contentType:'application/json;charset=UTF-8',
	      beforeSend: function() {
	         $("#content").append(loading.on);
	      },
	      success : function(rData) {
	    	  var memName = $(takeElement).parents("form").children().find("#tb_cuName").val();
	    	  var select = $(takeElement).parents("form").children().find("#sb_item")
	    	  if(typeof(memName) != 'undefined'){
	        	 debug("select : " + select.val());
	        	 debug("rData : " + rData.length);
        		 select.find("option").each(function(){
        			 $(this).remove();
        		 })
        		 select.prepend("<option value='list'>-- 자산목록 --</option>");
		        	 if(rData.length>1){
		        		 for(var i=0; i<rData.length; i++){
		        			 select.append("<option value='" + rData[i].buyId + "'>" + rData[i].buyId + "</option>");
		        			 $(takeElement).parents("form").children().find("#hi_itId").val(rData[i].itId);
		        		 }
		        	 } else if(rData.length == 1){
		        		 for(var i=0; i<rData.length; i++){
		        			 select.append("<option value='" + rData[i].buyId + "'>" + rData[i].buyId + "</option>");
		        			 $(takeElement).parents("form").children().find("#hi_itId").val(rData[i].itId);
		        		 } 
		        	 }
	    	  } else {
	        	 $(takeElement).parents("form").children().find("#sb_item").val("");
	        	 $(takeElement).parents("form").children().find("#tb_buyStart").val("");
	        	 $(takeElement).parents("form").children().find("#tb_buyEnd").val("");
	        	 select.find("option").each(function(){
	        		 $(this).remove();
	        		 select.append("<option value=' '></option>");
	        	 })
	         }
	      },
	      error : function() {
				var a = new MsgWindow("error");
				a.setMessage("실패", "검색결과가 없습니다.");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
	      },
	      complete : function() {
	         $(".loading").remove();
	    }
   });
}

/**
 * 상담 자산기간조회
 * @param takeElement
 * @returns
 */
function changeValue(takeElement) {
	   var sData = JSON.stringify({
	      optionValue:$(takeElement).val()
	   });
	   var ihtml="";
	   $.ajax({
	      type : "POST",
	      url : "getChangeInfo.do",
	      data : sData,
	      dataType : "json",
	      async: true,
	      contentType:'application/json;charset=UTF-8',
	      beforeSend: function() {
	         $("#content").append(loading.on);
	      },
	      success : function(rData) {
	    	  var memName = $(takeElement).parents("form").children().find("#tb_cuName").val();
	    	  var endDay = new Date(rData.buyEnd);
	    	  var startDay = new Date(rData.buyStart);
	    	  var reuslt = new Date(endDay-startDay);
	    	  if(typeof(memName) != 'undefined'){
		    	  $(takeElement).parents("form").children().find("#tb_buyStart").val(rData.buyStart);
		    	  $(takeElement).parents("form").children().find("#tb_buyEnd").val(rData.buyEnd);
	    	  } else if(memName == null){
	    		  $(takeElement).parents("form").children().find("#tb_buyStart").val(" ");
		    	  $(takeElement).parents("form").children().find("#tb_buyEnd").val(" ");
		    	  $(takeElement).parents("form").children().find("#tb_buyMonth").val(" ");
	    	  }
	      },
	      error : function() {
				var a = new MsgWindow("error");
				a.setMessage("실패", "검색결과가 없습니다.");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
	      },
	      complete : function() {
	         $(".loading").remove();
	    }
   });
}

/**
 * as상담 등록하기
 * @param adviceId
 * @returns
 */
function setAsAdvice(adviceId) {
	var sData = $.extend({}, {
		adviceId:adviceId,
		tb_mobile:$("#tb_mobile").val()		
	}, JSON.parse(formChange($("#asAdviceForm"))));
	var result =" ";
	var requestURL = "setAsAdvice.do";
	$.ajax({
		type : "POST",
		url : requestURL,
		data : JSON.stringify(sData),
		dataType : "JSON",
		async: false,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var a = new MsgWindow("plain");
			a.setMessage("성공", "등록에 성공하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
			result = rData;
			document.location = "goAdvice.do"
		},
		error : function() {
			var a = new MsgWindow("error");
			a.setMessage("실패", "등록에 실패하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
	return result;
}

/**
 * 회수상담 등록하기
 * @param adviceId
 * @returns
 */
function setRefundAdvice(adviceId) {
	var sData = $.extend({}, {
		adviceId:adviceId,
		tb_mobile:$("#tb_mobile").val(),
		reason:$("input[type=radio][name=rb_expiration]:checked").val()
	}, JSON.parse(formChange($("#refundAdviceForm"))));
	var result =" ";
	var requestURL = "setRefundAdvice.do";
	$.ajax({
		type : "POST",
		url : requestURL,
		data : JSON.stringify(sData),
		dataType : "JSON",
		async: false,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var a = new MsgWindow("plain");
			a.setMessage("성공", "등록에 성공하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
			result = rData;
			document.location = "goAdvice.do";
		},
		error : function() {
			var a = new MsgWindow("error");
			a.setMessage("실패", "등록에 실패하였습니다.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		complete : function() {
			$(".loading").remove();
		}
	});
	return result;
}

/**
 * 우편번호API (상담에서 사용)
 * @param takeElement
 * @returns
 */
function getPost(takeElement) {
	new daum.Postcode({
        oncomplete: function(data) {
            var fullAddr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                fullAddr = data.roadAddress;
            } else {
                fullAddr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                if(data.bname !== '') {
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }
            $(takeElement).parents("form").children().find("#tb_post").val(data.zonecode);
            $(takeElement).parents("form").children().find("#tb_addr").val(fullAddr);
            document.getElementById('tb_addD').focus();
        }
    }).open();
}

/**
 * 내부 사용자 비밀번호 수정 시 속성 바꾸어주는 메소드
 * @returns
 */
function updatePw(){
	$("#tb_pw").attr("readonly", false);
	$("#tb_pw").css("background-color", "white")
	$("#tr_pwCheck").toggle(300);
}

/**
 * 내부 사용자 전화번호 수정 시 속성을 바꾸어주는 메소드
 * @returns
 */
function updateMobile(){
	$("#tb_mobile").attr("readonly", false);
	$("#tb_mobile").css("background-color", "white")
}

/**
 * 내부 사용자 주소 수정 시 속성을 바꾸어주는 메소드
 * @returns
 */
function updateWork(){
	$("#tb_work").attr("readonly", false);
	$("#tb_work").css("background-color", "white")
}

/**
 * 내부 사용자 내 정보 수정 불러올 시 select box값 지정해주는 메소드
 * @param selectData
 * @returns
 */
function sbSelect(selectData){
	switch(selectData){
		case "u" :
			$("#sb_state").val("u").attr("selected", "selected");
			break;
		case "o" :
			$("#sb_state").val("o").attr("selected", "selected");
			break;
		case "i" :
			$("#sb_state").val("i").attr("selected", "selected");
			break;
		default :
			$("#sb_state").val("업무상태").attr("selected", "selected");
			break;
	}
}

/**
 * 비밀번호 확인 시 span에 글짜 뿌려주는 메소드
 * @param pw1
 * @param pw2
 * @returns
 */
function checkPwRe(pw1, pw2){
    if(pw1 != pw2){
    	$("#checkPw").html("<font size='3px' color='red'> 비밀번호가 일치하지 않습니다. </font>");
    	$("#submitMemberInfo").attr("disabled", true);
    }else{
    	$("#checkPw").html( "<font size='3px' color='blue'> 비밀번호가 일치합니다. </font>");
    	$("#submitMemberInfo").attr("disabled", false);
    }
 }

/**
 * 비밀번호 확인 후 다시 비밀번호 접근 시 저장버튼 속성 바꾸어주는 메소드
 * @returns
 */
function disButton(){
	$("#checkPw").html("<font> </font>");
	$("#submitMemberInfo").attr("disabled", true);
}


/* ======================================== by 이원호 ================================================= */

/**
 * 객체 정렬
 * @param form 객체
 * @returns
 */
function formChange(form){
   var jsonstr = "{";
      $.each($(form).serializeArray(), function(index, item){
         jsonstr += (index != 0) ? ',' : '';
         jsonstr += '"'+item.name+'":"'+item.value+'"'
      });
      jsonstr += "}";
      return jsonstr;
}

/**
 * 로그인 고객, 직원 선택 시 숨기기
 * @param user
 * @returns
 */
function getLoginTab(user) {
	if(user=="customer"){
		$("#employee").show(0);
	} else{
		$("#employee").hide(0);
	}
}

/**
 * radio버튼을 통한 로그인 
 * @returns
 */
function getLogin() {
	var st = $(":input:radio[name=rb_user]:checked").val();
	document.loginForm.action = st+"Login.do";
	document.loginForm.submit();
}

/**
 * 회원 가입 시 상세 주소만 입력 방지
 * @returns
 */
function getAddressCheck() {
	var post = $("#tb_post").val();
	if (post.length == 0){
		alert("주소를 입력하세요.");
		$("#tb_post").focus();
	}
}

/**
 * 회원 가입 시 아이디 중복 확인
 * @returns
 */
function setJoinId() {
	var customerId = $("#tb_id").val();
	if (customerId.trim().length > 0) {
		var requestURL = "getIdCheck.do";
		var sData = JSON.stringify({
	      customerId:$("#tb_id").val()
		});
		$.ajax({
		      type : "POST",
		      url : requestURL,
		      data : sData,
		      dataType : "JSON",
		      async: true,
		      contentType:'application/json;charset=UTF-8',
		      beforeSend: function() {
		         $("#content").append(loading.on);
		      },
		      success : function(rData) {
		    	  if(rData){
		    		  $("#la_idCheck").html("<font color='red'>이미 사용중 또는 탈퇴한 아이디 입니다.</font>");
		    	  } else {
		    		  $("#la_idCheck").html("<font color='red'>사용가능한 아이디 입니다.</font>");
		    	  }
		      },
		      error : function() {
		    	  document.location = "goUserRequestError.do";
		      },
		      complete : function() {
		         $(".loading").remove();
		      }
		   });
	} else {
		var a = new MsgWindow("error");
		a.setMessage("", "아이디를 입력하세요.");
		a.setButton("확인", "MsgWindow.hide(this)");
		a.show();
	}
}

/**
 * 회원가입 시 비밀번호 입력 상태 문구
 * @returns
 */
function setPw() {
	$("#la_pwCheck").html("<font color='red'>비밀번호를 확인해주세요.</font>");
}

/**
 * 회원가입 시 비밀번호 확인
 * @returns
 */
function setPwCheck() {
	if ($("#tb_pw").val() == $("#tb_rePw").val()){
		$("#la_pwCheck").html("<font color='blue'>비밀번호가 일치합니다.</font>");
	} else {
		$("#la_pwCheck").html("<font color='red'>비밀번호가 일치하지 않습니다.</font>");
	}
}

/**
 * AS, 회수 요청 검색 시 select에 따른 검색 창
 * @param takeElement 검색 시 select 값
 * @returns
 */
function selectCheck(takeElement){
	var choice = $(takeElement).val();
	if (choice == "all"){
		$("#se_all").show();
		$("#se_product").hide();
		$("#se_date").hide();
		$("#se_complete").hide();
	} else if (choice == "product") {
		$("#se_all").hide();
		$("#se_product").show();
		$("#se_date").hide();
		$("#se_complete").hide();
	} else if (choice == "day") {
		$("#se_all").hide();
		$("#se_product").hide();
		$("#se_date").show();
		$("#se_complete").hide();
	} else if (choice == "status") {
		$("#se_all").hide();
		$("#se_product").hide();
		$("#se_date").hide();
		$("#se_complete").show();
	}
}

/**
 * AS, 회수 요청 검색 시 날짜를 선택한 경우 text 입력창 설정
 * @param takeElement 검색내용
 * @returns
 */
function keyLength(takeElement){
	var length = $(takeElement).val().length;
	var value = $(takeElement).val();
	var reg = /^[0-9]+$/;
	debug($(takeElement).val()+" : value")
	if (reg.test(value.charAt(length-1))?true:false) {
		if ((length == 4)||(length == 7)){
			$(takeElement).val(value+"-");
		}
	} else {
		$(takeElement).val(value.slice(0,-1));
	}
}

/**
 * AS, 회수 요청 검색 시 검색 값 길이
 * @param takeElement 검색 시 select 값
 * @returns
 */
function searchCheck(takeElement){
	var choice = $("#sel_visit").val();
	var a = new MsgWindow("error");
	if (choice == "all"){
		$("#keyword").val($("#se_all").val())
		getVisitRequest(takeElement);
	} else if (choice == "product") {
		if ($("#se_product").val().length > 0){
			$("#keyword").val($("#se_product").val())
			getVisitRequest(takeElement);
		} else {
			a.setMessage("", "아이디를 입력하세요.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		}
	} else if (choice == "day") {
		debug("date : "+$("#se_date").val())
		if ($("#se_date").val().length > 0){
			$("#keyword").val($("#se_date").val())
			getVisitRequest(takeElement);
		} else {
			a.setMessage("", "아이디를 입력하세요.");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		}
	} else if (choice == "status") {
		$("#keyword").val($("#se_complete").val())
		getVisitRequest(takeElement);
	}
}

/**
 * 관리자가 고객, 직원 리스트 조회
 * @param takeElement
 * @returns
 */
function getMemberList(takeElement){
	var requestURL = "get"+$(takeElement).val()+"List.do";
	var sData = JSON.stringify({
		keyword:$("#keyword").val()
	});
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				ihtml += "<tr data-fn-name='getMemberInfo' data-key2='"+$(takeElement).val()+"' data-key='"+rData[i].memId+"' onclick='setOutsideHeadSeqRequest(this)'>";
				ihtml += "<td>"+(i+1)+"</td><td>"+rData[i].memId+"</td>";
				ihtml += "</tr>";
			}
			$("#memberList tbody").html(ihtml);
			$('.loading').remove();
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 관리자가 선택한 고객 및 직원 정보 가져오기
 * @param memberId
 * @param target
 * @returns
 */
function getMemberInfo(memberId, target) {
	if (memberId == selectedHeadSeq) {
		debug("already chosen")
		return;
	}
	selectedHeadSeq = memberId;
	var requestURL = "get"+target+"Info.do";
	var sData = JSON.stringify({
		memberId:""+memberId
	});
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			$("#tb_memId").val(rData.memId);
			$("#tb_memPw").val(rData.memPw);
			$("#tb_memName").val(rData.memName);
			$("#tb_memMobile").val(rData.memMobile);
			$("#tb_memEntry").val(rData.memEntry);
			$("#tb_memState").val(rData.memState);
			$("#tb_memUpdate").val(rData.memUpdate);
			$("#tb_memWork").val(rData.memWork);
			$("#tb_post").val(rData.memPost);
			$("#tb_addr").val(rData.memAddr);
			$("#tb_addD").val(rData.memAddD);
			$("#sel_card").val(rData.memCardSel); //카드사
			if((typeof(rData.memCard) != 'undefined') && (rData.memCard != null)){
				$("#tb_card1").val(rData.memCard.substring(0,4));
				$("#tb_card2").val(rData.memCard.substring(4,8));
				$("#tb_card3").val(rData.memCard.substring(8,12));
				$("#tb_card4").val(rData.memCard.substring(12));
			}
			$("#sel_account").val(rData.memAccountSel);  //은행
			$("#tb_memAccount").val(rData.memAccount);
			$("#la_lastUpdate").html("최근 상태 변경 일자   "+rData.memUpdate);
			$("#tb_auId").val(rData.memAuId);
			$("#bt_insert").hide();
			var a = new MsgWindow("");
			a.setMessage("", "불러오기 성공!");
			a.setButton("확인", "MsgWindow.hide(this)");
			a.show();
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 회수 결과 등록 시 날짜 입력창 내용 확인
 * @param takeElement
 * @returns
 */
function setDateCheck(takeElement) {
	if ($("#hi_outDay").val().length > 0){
		getMemberChange(takeElement);
	} else {
		var a = new MsgWindow("error");
		a.setMessage("", "날짜 입력");
		a.setButton("확인", "MsgWindow.hide(this)");
		a.show();
	}
}

/**
 * 관리자의 고객, 직원 정보 일괄 등록 및 변경, 외부수리기사 회수처리결과 등록 및 변경
 * @param takeElement
 * @returns
 */
function getMemberChange(takeElement) {
	var requestURL = $(takeElement).parents("form").attr("action");
	var sData = ""+formChange($(takeElement).parents("form"));
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			if(rData){
				var a = new MsgWindow("");
				a.setMessage("", "성공");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			}else{
				var a = new MsgWindow("error");
				a.setMessage("", "실패");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			}
			if(requestURL == "addCompanionJoin.do"){
				$("#tb_memId").val("");
				$("#tb_memPw").val("");
				$("#tb_memName").val("");
				$("#tb_memMobile").val("");
				$("#tb_memEntry").val("");
				$("#tb_memWork").val("");
				$("#tb_memState").val("");
				$("#tb_memId").val("");
				a.show();
			} else if(requestURL == "setRentalRefundResult.do"){
				getVisitRequest(takeElement);
			}
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 관리자가 직원 및 고객 정보 개별 변경
 * @param takeElement
 * @returns
 */
function setMemberItemChange(takeElement) {
	var requestURL = "setMemberItemChange.do";
	if (formChange($(takeElement).parents("tr").find("select")).length > 0){
		var sData = $.extend({},JSON.parse(formChange($(takeElement).parents("tr").find("input"))),JSON.parse(formChange($("#tr_memId").find("input"))));
	} else {
		var sData = $.extend({},JSON.parse(formChange($(takeElement).parents("tr").find("input"))),$.extend({},JSON.parse(formChange($("#tr_memId").find("input"))),JSON.parse(formChange($(takeElement).parents("tr").find("select")))));
	}
	debug("aa : "+sData)
	$.ajax({
		type : "POST",
		url : requestURL,
		data : JSON.stringify(sData),
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			if(rData){
				var a = new MsgWindow("");
				a.setMessage("", "정보 변경 성공!");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			}else{
				var a = new MsgWindow("error");
				a.setMessage("", "정보 변경 실패!");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			}
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 관리자가 직원 등록 시 비밀번호 랜덤 부여
 * @returns
 */
function setNewPw(){
	var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    for( var i=0; i < 8; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    $("#tb_memPw").val(text);
}

/**
 * 직원 등록 페이지에서 기존 입력 정보 초기화 시 등록 버튼 활성화
 * @returns
 */
function insertOn(){
	$("#bt_insert").show();
}

/**
 * 관리자가 직원아이디 부여하는 부분
 * @returns
 */
function setRandomId(){
	$.ajax({
		type : "POST",
		url : "getNewId.do",
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			$("#tb_memId").val(rData);
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 렌탈 상담 시 제품 상태별 수량 가져오기
 * @returns
 */
function getProductCount(){
	var sData = JSON.stringify({
		itId:$("#tb_itId").val(),
		itName:$("#tb_itName").val()
	});
	$.ajax({
		type : "POST",
		url : "getProductCount.do",
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			debug(rData + " : item")
			if (!rData.isEmpty){
				$("#tb_itName").val(rData.itName);
				$("#tb_waitCount").val(rData.waitCount);
				$("#tb_doCount").val(rData.doCount);
				$("#tb_refundCount").val(rData.refundCount);
				$("#tb_itPeriod").val(rData.itPeriod);
				$("#tb_itPrice").val(rData.itPrice);
			} else {
				$("#tb_itId").val("");
				$("#tb_itName").val("");
				$("#tb_waitCount").val("");
				$("#tb_doCount").val("");
				$("#tb_refundCount").val("");
				$("#tb_itPeriod").val("");
				$("#tb_itPrice").val("");
				var a = new MsgWindow("error");
				a.setMessage("", "검색 결과가 없습니다.");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			}
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 렌탈 상담시 임시회원등록 버튼 활성화
 * @returns
 */
function setAddCustomerCheckF() {
	$("#bt_temporary").prop("disabled", false);
}

/**
 * 렌탈 상담시 임시회원등록 버튼 비활성화
 * @returns
 */
function setAddCustomerCheckT() {
	$("#bt_temporary").prop("disabled", true);
}

/**
 * 렌탈 상담시 임시회원 등록
 * @param takeElement
 * @returns
 */
function temporary(takeElement){
	var sData = JSON.stringify({
		memberName:$(takeElement).parents("form").children().find("#tb_cuName").val(),
		memberMobile:$("#tb_cuMobile").val(),
		memberBirth:$(takeElement).parents("form").children().find("#tb_cuBirth").val()
	});
	debug(sData)
	$.ajax({
		type : "POST",
		url : "addTempCustomer.do",
		data : sData,
		dataType : "text",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			if(rData.length > 0){
				$(takeElement).parents("form").children().find("#tb_cuId").val(rData);
				$(takeElement).parents("form").children().find("#tb_cuResult").val("가입 성공");
			} else {
				$(takeElement).parents("form").children().find("#tb_cuResult").val("가입 실패");
			}
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 렌탈 상담 시 구매 시작일에 따른 기본 렌탈 종료일 표시
 * @returns
 */
function defaultDate() {
	var date1 = new Date($("#tb_start").val());
	var date2 = new Date($("#tb_start").val());
	date2.setMonth(date1.getMonth() + 36)
	$("#tb_end").val(date2.toISOString().slice(0,10));
}

/**
 * 렌탈 상담 결제 방법에 따른 입력창
 * @param takeElement
 * @returns
 */
function buyMethod(takeElement) {
	var method = $(takeElement).val();
	if (method == "account"){
		$("#sel_card").hide();
		$("#sel_account").show();
		$("#tb_methodNum").prop("readonly", false);
	} else if (method == "card") {
		$("#sel_card").show();
		$("#sel_account").hide();
		$("#tb_methodNum").prop("readonly", false);
	} else {
		$("#sel_card").hide();
		$("#sel_account").hide();
		$("#tb_methodNum").prop("readonly", true);
	}
}

/**
 * 상담 등록
 * @returns
 */
function checkAdvice() {
	var adviceId="";
	var count = 0;
	if ($("#cb_group:checked").length > 0){
		adviceId = addAdviceId();
		debug(adviceId+" : ad ID")
		if (adviceId.length > 0) {
			$("#cb_group:checked").each(function(index, item){
				if (item.value == "nomal") {
					setNomalAdvice(adviceId);
				}
				if(item.value == "rental") {
					setRentalAdvice(adviceId);
				}
				if(item.value == "as") {
					setAsAdvice(adviceId);
				} 
				if(item.value == "refund") {
					setRefundAdvice(adviceId);
				}
				});
		} else {
			document.location = "goRequestError.do";
		}
	} else {
		alert("라디오 선택!!")
	}
}

/**
 * 상담 등록 시 상담 아이디 가져오기
 * @returns
 */
function addAdviceId() {
	var result="";
	$.ajax({
		type : "POST",
		url : "addAdviceId.do",
		dataType : "text",
		async: false,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			if (rData != null){
				debug(rData + " : advice Id")
				result = rData;
			}
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
	return result;
}

/**
 * 일반 상담 등록
 * @param adviceId
 * @returns
 */
function setNomalAdvice(adviceId) {
	var sData = $.extend({}, {
		adviceId:adviceId
		}, JSON.parse(formChange($("#nomalAdviceForm")))
		);
	$.ajax({
		type : "POST",
		url : "addNomalAdvice.do",
		data : JSON.stringify(sData),
		dataType : "json",
		async: false,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			if(rData != 0) {
				var a = new MsgWindow("");
				a.setMessage("", "상담 등록 성공");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			} else {
				var a = new MsgWindow("error");
				a.setMessage("", "상담 등록 실패");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			}
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * 렌탈 상담 등록
 * @param adviceId
 * @returns
 */
function setRentalAdvice(adviceId) {
	var sData = $.extend({}, JSON.parse(formChange($("#rentalAdviceForm"))), {
		adviceId:adviceId,
		tb_mobile:$("#tb_mobile").val()
	});
	$.ajax({
		type : "POST",
		url : "addRentalAdvice.do",
		data : JSON.stringify(sData),
		dataType : "json",
		async: false,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			debug(rData + "aaa")
			if(rData != 0) {
				var a = new MsgWindow("");
				a.setMessage("", "렌탈 상담 등록 성공");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			} else {
				var a = new MsgWindow("error");
				a.setMessage("", "렌탈 상담 등록 실패");
				a.setButton("확인", "MsgWindow.hide(this)");
				a.show();
			}
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/**
 * QR 코드 생성
 * @returns
 */
function qrCreate(){
	$.ajax({
		type : "POST",
		url : "addQrCode.do",
		dataType : "json",
		async: false,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			debug(rData + " : Rental Advice")
		},
		error : function() {
			document.location = "goRequestError.do";
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

///* ======================================== by 이민정 ================================================= */

/*비밀번호변경 확인*/
function checkPw(){
	if($("#newpw").val() != $("#checkpw").val()){
		alert("입력하신 새 비밀번호 불일치");
		return false;
	} else {
		document.changepwform.submit();
	}
}

/*rentalDetail 품목 아이디 가져오기*/
function getItemId(element){
	var itemId = $(element).data("code");
	location.href = "rentaldetail.do?itemId="+itemId;
}

/*rental품목 아이디 가져오기*/
function getId(element){
	var itemId = $(element).data("code");
	location.href = "rental.do?itemId="+itemId;
}

/*결제수단  카드선택*/
function showCard(){
	$("#paymentCard").css("display","block");
	$("#paymentAccount").css("display","none");
	$("#paymentCash").css("display","none");
	$("#paymentCard input").attr("required",true);
	$("#paymentCard select").attr("required",true);
	$("#paymentCash input").attr("required",false);
	$("#paymentCash select").attr("required",false);
	$("#paymentCash select").val('');
	$("#paymentCash input").val('');
}

/*결제수단 계좌이체선택*/
function showAccount(){
	$("#paymentCard").css("display","none");
	$("#paymentAccount").css("display","block");
	$("#paymentCash").css("display","none");
	$("#paymentCash input").attr("required",true);
	$("#paymentCash select").attr("required",true);
	$("#paymentCard input").attr("required",false);
	$("#paymentCard select").attr("required",false);
	$("#paymentCard select").val('');
	$("#paymentCard input").val('');
}

function showCash(){
	$("#paymentCard").css("display","none");
	$("#paymentAccount").css("display","none");
	$("#paymentCash").css("display","block");
	$("#paymentCash input").attr("required",false);
	$("#paymentCash select").attr("required",false);
	$("#paymentCard input").attr("required",false);
	$("#paymentCard select").attr("required",false);
	$("#paymentCard select").val('');
	$("#paymentCard input").val('');
	$("#paymentCash select").val('');
	$("#paymentCash input").val('');
}

/*렌탈 기간 설정*/
function setDate(element){
	var startDate = $(element).val().split("-");
	var year = parseInt(startDate[0])+($('#period').val()/12);
	var month = startDate[1];
	var date = startDate[2];
	$("#end").val(year+'-'+month+'-'+date);
	var today = new Date();
	year = today.getFullYear();
	month = today.getMonth()+1;
	date = today.getDate();
	if(month < 10) {
		month = '0' + month;
	}
	if(date < 10) {
		date = '0' + date;
	}
	$("#start").attr('min', year+'-'+month+'-'+date);
}
/* 점검결과조회 날짜 선택*/
function dateLock(element){
	var startDate = $(element).val().split("-");
	var year = parseInt(startDate[0]);
	var month = parseInt(startDate[1]);
	var date = parseInt(startDate[2]);
	console.log(year);
	console.log(month);
	console.log(date);
	if(month < 10) {
		month = '0' + month;
	}
	if(date < 10) {
		date = '0' + date;
	}
	$("#end").attr('min', year+'-'+month+'-'+date);
}

/* 입고요청조회 */
function getInputRequest(key) {
	var takeData = key;
	var requestURL = "getinputrequest.do";
	var sData = JSON.stringify({
		inputState:takeData
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				ihtml += "<tr data-input-id='"+ row.id
					+"' data-item-name='"+ row.name
					+"' data-item-count='"+ row.count
					+"' data-item-state='"+ row.state
					+"' data-item-date='"+ row.date
					+"' data-item-delivery='"+ row.delivery
					+"'>";
				ihtml += "<td>"+row.id+
					"</td><td>"+row.name+
					"</td><td>"+row.count+
					"</td><td>"+row.date+
					"</td><td>"+row.delivery+
					"</td><td><button class='btn btn-primary btn-sm' onclick='inputRequest(this)'>입고</button></td></tr>";
			}
			debug(ihtml);
			$("#inputList").html(ihtml);
			if ($(".table-success").data("flag") == "true") {
				$("table button").attr("disabled", true);
			}
		},
		error : function() {
			alert("검색실패!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}
/* 요청입고 등록 */
function inputRequest(takeElement) {
	var requestURL = "setinputstate.do";
	var sData = JSON.stringify({
		id:$(takeElement).parents("tr").data("input-id"),
		state:$(takeElement).parents("tr").data("item-state")
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			alert(rData.result);
			$(takeElement).attr("disabled", true);
		},
		error : function() {
			alert("입고실패!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 요청 입고 검색 */
function searchInputRequest() {
	var requestURL = "searchinputrequest.do";
	var sData = JSON.stringify({
		state:getHeadSeqRequest(),
		name:$("#searchInput").val()
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				ihtml += "<tr data-input-id='"+ row.id
					+"' data-item-name='"+ row.name
					+"' data-item-count='"+ row.count
					+"' data-item-state='"+ row.state
					+"' data-item-date='"+ row.date
					+"' data-item-delivery='"+ row.delivery
					+"'>";
				ihtml += "<td>"+row.id+
					"</td><td>"+row.name+
					"</td><td>"+row.count+
					"</td><td>"+row.date+
					"</td><td>"+row.delivery+
					"</td><td><button class='btn btn-primary btn-sm' onclick='inputRequest(this)'>입고</button></td></tr>";
			}
			debug(ihtml);
			$("#inputList").html(ihtml);
			if ($(".table-success").data("flag") == "true") {
				$("table button").attr("disabled", true);
			}
		},
		error : function() {
			alert("검색실패!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}
/* 입고 자산상태별 조회*/
function getInput(key){
	var takeData = key;
	var requestURL = "getinput.do";
	var sData = JSON.stringify({
		state:takeData
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				
				ihtml += "<tr data-input-id='"+ row.id
					+"' data-item-name='"+ row.name
					+"' data-item-count='"+ row.count
					+"' data-item-state='"+ row.state
					+"' data-item-date='"+ row.date
					+"' data-item-delivery='"+ row.delivery
					+"' data-item-qr='"+ row.qr+"'>";
				ihtml += "<td>"+row.id+
					"</td><td>"+row.name+
					"</td><td>"+row.count+
					"</td><td>"+row.date+
					"</td><td>"+row.delivery+
					"</td><td></td></tr>";
			}
			debug(ihtml);
			$("#inputList").html(ihtml);
			if ($(".table-success").data("flag") == "true") {
				$("table button").attr("disabled", true);
			}
		},
		error : function() {
			alert("검색실패!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}
/* 입고 제품명 검색 조회*/
function searchInput(){
	var requestURL = "searchinput.do";
	var sData = JSON.stringify({
		state:getHeadSeqRequest(),
		name:$("#searchInput").val()
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				ihtml += "<tr data-input-id='"+ row.id
					+"' data-item-name='"+ row.name
					+"' data-item-count='"+ row.count
					+"' data-item-state='"+ row.state
					+"' data-item-date='"+ row.date
					+"' data-item-delivery='"+ row.delivery
					+"' data-item-qr='"+ row.qr+"'>";
				ihtml += "<td>"+row.id+
					"</td><td>"+row.name+
					"</td><td>"+row.count+
					"</td><td>"+row.date+
					"</td><td>"+row.delivery+
					"</td><td></td></tr>";
			}
			debug(ihtml);
			$("#inputList").html(ihtml);
		},
		error : function() {
			alert("검색실패!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 점검대기 정보 검색 조회 */
function getRepairList(){
	var requestURL = "getrepairlist.do";
	var sData = JSON.stringify({
		keyword:$("#keyword").val(),
		select:$("#type").val()
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				ihtml += "<tr class='is-line' data-fn-name='getrepairform(headElement)' data-key='"+rData[i].id+ "'data-itid='"+rData[i].itemId+"'data-productstate='"+rData[i].state+"'data-itname='"+rData[i].itemName+"' onclick='setHeadSeqRequest(this)'>";
				ihtml += "<td>"+rData[i].id+"</td><td>"+rData[i].itemName+"</td>"+"<td>"+rData[i].state+"</td><td>"+rData[i].date+"</td>";
				ihtml += "</tr>";
			}
			debug(ihtml);
			$("#repairList tbody").html(ihtml);
			$('.loading').remove();
		},
		error : function() {
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}
/* 점검내역 폼 가져오기*/
function getrepairform(takeElement){
	var takeData = $(takeElement).data('key');
	var requestURL = "getrepairform.do";
	var sData = JSON.stringify({
		id:takeData,
		state:$(takeElement).data('productstate')
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			$("#productId").val(rData.productId);
			$("#itemId").val(rData.itemId);
			$("#itemName").val(rData.itemName);
			$("#repairDate").val(rData.todayDate);
			$("#repairContents").val(rData.repairContent);
		},
		error : function() {
			alert("폼으로 가져오기 실패!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

function useParts(){
	document.getElementById("useParts").value += $("#parts").val()+" ";
}

function resetParts(){
	document.getElementById("useParts").value = "";
}

function resetForm(){
	$("#repairContents").val('');
	$("#useParts").val('');
	$("#repairSort").val('');
	$("#parts").val('');
}

/* 점검내역 폼에 부품 가져오기*/
function getPartsList(key){ 
	var takeData = $(key).data('itid');
	var requestURL = "getpartslist.do";
	var sData = JSON.stringify({
		id:takeData
		
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
			$("#parts tbody").remove();
			$('#parts').parents('td').children('div').remove();
			$('#parts').css('display', 'table');
		},
		success : function(rData) {
			debug(rData.length);
			debug(rData);
			if (rData.length > 0) {
				var tbody = $('<tbody />', {});
				for (var i = 0; i < rData.length; i++) {
					var tr = $('<tr />',{});
					$(tr).data('part-id', rData[i].partId);
					$(tr).data('part-name', rData[i].partName);
					$(tr).data('part-count', rData[i].partCount);
					$(tr).append($('<td />', {text: rData[i].partId}));
					$(tr).append($('<td />', {text: rData[i].partName}));
					$(tr).append($('<td />', {text: rData[i].partCount}));
					$(tr).append($('<td />', {}).append(
							$('<input />', {
								name : rData[i].partId,
								type: 'number',
								max: rData[i].partCount,
								min: '0',
								step: '1',
								value:'0',
								class: 'from-control'
							}))
					)
					$(tbody).append(tr);
				}
				$('#parts').append(tbody);
			} else {
				$('#parts').parents('td').append($('<div />', {text: '조회된 결과가 없습니다.', class: 'card border-warning'}))
				$('#parts').css('display', 'none');
			}
		},
		error : function() {
			alert("부품가져오기 실패!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

function redirectPage(url) {
	   window.location.href = url;
}

/* 수리내역 등록 */
function addRepairResult(takeElement) {
   debug(getUsePartsList("form[name=partsInputForm]"));
   var requestURL = "addrepairresult.do";
   /*var sData = getUsePartsList("#repairResultForm");*/
   var sData = JSON.stringify({
      repairId:$("#productId").val(),
      itName:$("#itemName").val(),
      productId:$("#productId").val(),
      engineerId:$("#engineerId").val(),
      engineerName:$("#engineerName").val(),
      repairSort:$("#repairSort").val(),
      state:$("#productId").val(),
      repairDate:$("#repairDate").val(),
      repairContent:$("#repairContents").val(),
      list:JSON.parse(getUsePartsList("form[name=partsInputForm]"))
   });
   debug(sData);
   debug(requestURL);
   $.ajax({
      type : "POST",
      url : requestURL,
      data : sData,
      dataType : "JSON",
      async: true,
      contentType:'application/json;charset=UTF-8',
      beforeSend: function() {
         $("#content").append(loading.on);
      },
      success : function(rData) {
      
         switch (rData.result) {
         case "success":
            msgBox = new MsgWindow('plain');
            msgBox.setMessage('점검등록성공!',' 점검결과가 정상적으로 등록되었습니다.');
            msgBox.setButton('확인', 'redirectPage(gorepairlist.do)');
            msgBox.show();
            break;
         case "invalid":
            var msg = new MsgWindow('invalid');
            msg.setMessage('점검등록오류','입력한 점검결과가 존재하지 않거나, 변경할 수 없는 상태입니다.');
            msg.setButton('확인','MsgWindow.hide(this)');
            msg.show();
            break;
         case "violated":
            var msg = new MsgWindow('violated');
            msg.setMessage('정책위반','정책위반이 발생하였습니다.');
            msg.setButton('확인','MsgWindow.hide(this)');
            msg.show();
            break;
         case "network":
            var msg = new MsgWindow('error');
            msg.setMessage('네트워크 오류','네트워크에 문제가 있습니다. 관리자에게 문의하십시오.');
            msg.setButton('확인','MsgWindow.hide(this)');
            msg.show();
            break;
         }
         
         document.location="gorepairlist.do";
         
      },
      error : function() {
         var msg = new MsgWindow('error');
         msg.setMessage('AJax 통신 오류','현재 서버와 연결이 원활하지 않아, 요청한 기능을 수행할 수 없습니다. 잠시후 다시 시도하여 주십시요.');
         msg.setButton('확인','MsgWindow.hide(this)');
         msg.show();
      },
      complete : function() {
         $(".loading").remove();
      }
   });
}

function repairFormCheck(){
	if(document.getElementById("repairSort") == null){
		alert('분류미선택');
		return;
	}else if(document.getElementById("repairContents") == null){
		alert('점검내역미입력');
		return;
	}
}
/* 우편번호 */
function getPost() {
   new daum.Postcode({
        oncomplete: function(data) {
            var fullAddr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                fullAddr = data.roadAddress;
            } else {
                fullAddr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                if(data.bname !== '') {
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }
            $("#tb_post").val(data.zonecode);
            $("#tb_addr").val(fullAddr);
        }
    }).open();
}

/*json으로 가져오기*/
function getUsePartsList(form){
	var jsonstr = "{";
	$.each($(form).serializeArray(), function(index, item){ 
		if(item.value != 0) { 
			jsonstr += '"'+item.name+'":"'+item.value+'",'; 
			}});
	jsonstr = (jsonstr.length == 1)? null : jsonstr.substr(0, jsonstr.length - 1) + "}";
	return jsonstr;
	
}

/* 모든 부속품 리스트 가져오기*/
function getAllPartsList(){
	var requestURL = "getallpartslist.do";
	var sData = JSON.stringify({
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				var message = "";
				if(row.partSafety != null && parseInt(row.partTotal) <= parseInt(row.partSafety)){
					message = "부속품 주문요청";
				}
				ihtml += "<tr data-parts-id='"+ row.partId
					+"' data-parts-model='"+ row.partModel
					+"' data-parts-search='"+ row.partSearch
					+"' data-item-id='"+ row.itemId
					+"' data-parts-manufacturer='"+ row.partManufacturer
					+"' data-parts-total='"+ row.partTotal
					+"' data-parts-safety='"+ row.partSafety+"'>";
				ihtml += "<td>"+row.partId+
					"</td><td>"+row.partModel+
					"</td><td>"+row.partSearch+
					"</td><td>"+row.itemId+
					"</td><td>"+row.partManufacturer+
					"</td><td>"+row.partTotal+
					"</td><td>"+row.partSafety+
					"</td><td  style='color: red'>"+message+"</td></tr>";
			}
			debug(ihtml);
			$("#partsList").html(ihtml);
		},
		error : function() {
			alert("부속품가져오기실패!!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}

/* 모든 수리결과 리스트 가져오기*/
function getAllRepairResultList(){
	var requestURL = "getallrepairresultlist.do";
	var sData = JSON.stringify({
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				
				ihtml += "<tr data-repair-id='"+ row.repairId
					+"' data-item-name='"+ row.itemName
					+"' data-product-id='"+ row.productId
					+"' data-repair-date='"+ row.repairDate
					+"' data-repair-state='"+ row.repairState
					+"' data-engineer-id='"+ row.engineerId
					+"' data-engineer-name='"+ row.engineerName
					+"' data-repair-sort='"+ row.repairSort
					+"' data-repair-content='"+ row.repairContent
					+"' id='"+ row.engineerId+"'>";
				ihtml += "<td>"+row.repairId+
					"</td><td>"+row.itemName+
					"</td><td>"+row.productId+
					"</td><td>"+row.repairContent+
					"</td><td>"+row.repairDate+
					"</td><td>"+row.repairSort+"</td></tr>";
			}
			debug(ihtml);
			$("#repairResultList").html(ihtml);
		},
		error : function() {
			alert("수리결과초기리스트가져오기 실패!!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});
}
/* 부품 검색*/
function getSearchPartsList(){
	var requestURL = "getsearchpartslist.do";
	var sData = JSON.stringify({
		searchType : $("#searchType").val(),
		searchKeyword : $("#searchParts").val()
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				var message = "";
				if(row.partSafety != null && parseInt(row.partTotal) <= parseInt(row.partSafety)){
					message = "부속품 주문요청";
				}
				ihtml += "<tr data-parts-id='"+ row.partId
					+"' data-parts-model='"+ row.partModel
					+"' data-parts-search='"+ row.partSearch
					+"' data-item-id='"+ row.itemId
					+"' data-parts-manufacturer='"+ row.partManufacturer
					+"' data-parts-total='"+ row.partTotal
					+"' data-parts-safety='"+ row.partSafety+"'>";
				ihtml += "<td>"+row.partId+
					"</td><td>"+row.partModel+
					"</td><td>"+row.partSearch+
					"</td><td>"+row.itemId+
					"</td><td>"+row.partManufacturer+
					"</td><td>"+row.partTotal+
					"</td><td>"+row.partSafety+
					"</td><td  style='color: red'>"+message+"</td></tr>";
			}
			debug(ihtml);
			$("#partsList").html(ihtml);
		},
		error : function() {
			alert("부품 검색 실패!!");
		},
		complete : function() {
			$(".loading").remove();
		}

		
	});
}
/* 점검결과 조회*/
function getRepairResultList(){
	var requestURL = "getrepairresultlist.do";
	var sData = JSON.stringify({
		startDate : $("#start").val(),
		endDate : $("#end").val(),
		repairSort : $("#repairResultSort").val(),
	});
	debug(sData);
	debug(requestURL);
	$.ajax({
		type : "POST",
		url : requestURL,
		data : sData,
		dataType : "JSON",
		async: true,
		contentType:'application/json;charset=UTF-8',
		beforeSend: function() {
			$("#content").append(loading.on);
		},
		success : function(rData) {
			var ihtml = "";
			debug(rData.length);
			debug(rData);
			for (var i = 0; i < rData.length; i++) {
				var row = rData[i];
				
				ihtml += "<tr data-repair-id='"+ row.repairId
					+"' data-item-name='"+ row.itemName
					+"' data-product-id='"+ row.productId
					+"' data-repair-date='"+ row.repairDate
					+"' data-repair-state='"+ row.repairState
					+"' data-engineer-id='"+ row.engineerId
					+"' data-engineer-name='"+ row.engineerName
					+"' data-repair-sort='"+ row.repairSort
					+"' data-repair-content='"+ row.repairContent
					+"' id='"+ row.engineerId
					+"'>";
				ihtml += "<td>"+row.repairId+
					"</td><td>"+row.itemName+
					"</td><td>"+row.productId+
					"</td><td>"+row.repairContent+
					"</td><td>"+row.repairDate+
					"</td><td>"+row.repairSort+"</td></tr>";
			}
			debug(ihtml);
			$("#repairResultList").html(ihtml);
		},
		error : function() {
			alert("점검결과 조회 실패!!");
		},
		complete : function() {
			$(".loading").remove();
		}
	});

}


