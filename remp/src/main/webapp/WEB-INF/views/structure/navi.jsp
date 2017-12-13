<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="#">ReMP</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor02" aria-controls="navbarColor02"
		aria-expanded="false" aria-label="Toggle navigation" style="">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarColor02">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active">
				<a class="nav-link" href="#">Home
					<span class="sr-only">(current)</span>
				</a>
			</li>
			<li class="left"><a class="sub-navi-link" data-module="member" href="javascript:void(0)" onclick="getNavSubMenu(this)">회원관리</a></li>
			<li class="left"><a class="sub-navi-link" data-module="rental" href="javascript:void(0)" onclick="getNavSubMenu(this)">렌탈관리</a></li>
			<li class="left"><a class="sub-navi-link" data-module="asset" href="javascript:void(0)" onclick="getNavSubMenu(this)">자산관리</a></li>
			<li class="left"><a class="sub-navi-link" data-module="EIS" href="javascript:void(0)" onclick="getNavSubMenu(this)">EIS</a></li>
		</ul>
	</div>
	<!-- by 이원호 -->
	<div style="text-align: right">
		<a href="goLogin.do" class="navbar-brand nav-item nav-link">로그인</a>
		<span class="navbar-brand nav-item nav-link">|</span>
		<a href="goJoin.do" class="navbar-brand nav-item nav-link">회원가입</a>
	</div>
</nav>
<!-- 회원관리 -->
<div id="member" class="sub-navi" style="display: none;" onmouseleave="setNavSubMenu(this)">
	<ul class="navi-type">
		<li class="left"><a class="sub-navi-link" href="gouserchange.do">내정보 변경</a></li>
		<li class="left"><a class="sub-navi-link" href="goSetMemberInfo.do">내부사용자 정보수정</a></li>
		<li class="left"><a class="sub-navi-link" href="goCompanionJoin.do">직원등록</a></li>
		<li class="left"><a class="sub-navi-link" href="goCompanionInfo.do">직원수정</a></li>
		<li class="left"><a class="sub-navi-link" href="goCusInfo.do">회원수정</a></li>
		<li class="left"><a class="sub-navi-link" href="goMain.do">메인</a></li>
		<li class="left"><a class="sub-navi-link" href="goFindPw.do">비밀번호 찾기</a></li>
		<li class="left"><a class="sub-navi-link" href="gochangepw.do">비밀번호변경</a></li>
	</ul>
</div>

<!-- 렌탈관리 -->
<div id="rental" class="sub-navi" style="display: none;" onmouseleave="setNavSubMenu(this)">
	<ul class="navi-type">
		<li class="left"><a class="sub-navi-link" href="goAdvice.do">상담페이지</a></li>
		<li class="left"><a class="sub-navi-link" href="goRefundRequest.do">회수조회</a></li>
		<li class="left"><a class="sub-navi-link" href="goAsRequest.do">as 요청조회</a></li>
		<li class="left"><a class="sub-navi-link" href="rentalmain.do">렌탈검색</a></li>
	</ul>
</div>

<!-- 자산관리 -->
<div id="asset" class="sub-navi" style="display: none;" onmouseleave="setNavSubMenu(this)">
	<ul class="navi-type">
		<li class="left"><a class="sub-navi-link" href="goProductCare.do">자산관리</a></li>
		<li class="left"><a class="sub-navi-link" href="goProductInsert.do">자산등록</a></li>
		<li class="left"><a class="sub-navi-link" href="goinputrequest.do">입고요청조회</a></li>
		<li class="left"><a class="sub-navi-link" href="goinput.do">입고조회</a></li>
		<li class="left"><a class="sub-navi-link" href="gorepairlist.do">점검내역등록</a></li>
		<li class="left"><a class="sub-navi-link" href="gosearchparts.do">수리부속조회</a></li>
		<li class="left"><a class="sub-navi-link" href="gorepairresult.do">점검결과조회</a></li>
		<li class="left"><a class="sub-navi-link" href="gorentalasset.do">렌탈자산확정</a></li>
		<li class="left"><a class="sub-navi-link" href="gounstoreconfirm.do">출고관리</a></li>
		<li class="left"><a class="sub-navi-link" href="goduediligence.do">실사계획등록</a></li>
		<li class="left"><a class="sub-navi-link" href="gochangeduediligence.do">실사계획조회·변경</a></li>
		<li class="left"><a class="sub-navi-link" href="goRequestError.do">실사계획결과등록</a></li>
		<li class="left"><a class="sub-navi-link" href="goRequestError.do">실사계획결과조회·변경</a></li>
	</ul>
</div>

<!-- EIS -->
<div id="EIS" class="sub-navi" style="display: none;" onmouseleave="setNavSubMenu(this)">
		<ul class="navi-type">
		<li class="left"><a class="sub-navi-link" href="#">item1</a></li>
		<li class="left"><a class="sub-navi-link" href="#">item2</a></li>
		<li class="left"><a class="sub-navi-link" href="#">item3</a></li>
		<li class="left"><a class="sub-navi-link" href="#">item4</a></li>
		<li class="left"><a class="sub-navi-link" href="#">item5</a></li>
		<li class="left"><a class="sub-navi-link" href="#">item6</a></li>
	</ul>
</div>

<!-- 고객 메뉴 -->
<div id="EIS" class="sub-navi" style="display: none;" onmouseleave="setNavSubMenu(this)">
	<ul class="navi-type">
		<li class="left"><a class="sub-navi-link" href="gofindid.do">아이디 찾기</a></li>
		<li class="left"><a class="sub-navi-link" href="gouserchange.do">회원정보변경</a></li>
	</ul>
</div>