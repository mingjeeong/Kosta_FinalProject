<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h3 align="center">직원 정보 변경</h3>
<form id="conpainInfoForm" name="conpainInfoForm" method="post" action="setMemberChange.do">
<table class="table table-striped" style="margin-top:30px">
	<tr id="tr_memId">
		<td width="13%"><label>아이디</label></td>
		<td width="75%">
			<input type="text" class="form-control" id="tb_memId" name="tb_memId" placeholder="아이디">
			<input type="hidden" id="hi_target" name="hi_target" value="${target}">
		</td>
		<td></td>
	</tr>
	<tr>
		<td><label>비밀번호</label></td>
		<td>
			<input type="password" class="form-control" id="tb_memPw" name="tb_memPw" placeholder="비밀번호">
			<input type="hidden" name="column" value="1">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>성명</label></td>
		<td>
			<input type="text" class="form-control" id="tb_memName" name="tb_memName" placeholder="성명">
			<input type="hidden" name="column" value="2">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>입사일</label></td>
		<td>
			<input type="date" class="form-control" id="tb_memEntry" name="tb_memEntry">
			<input type="hidden" name="column" value="7">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>전화번호</label></td>
		<td>
			<input type="text" class="form-control" id="tb_memMobile" name="tb_memMobile" placeholder="전화번호">
			<input type="hidden" name="column" value="3">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>근무지위치</label></td>
		<td>
			<input type="text" class="form-control" id="tb_memWork" name="tb_memWork" placeholder="근무지">
			<input type="hidden" name="column" value="4">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>회원상태</label></td>
		<td>
			<input type="hidden" name="column" value="5">
			<select class="form-control" id="tb_memState" name="tb_memState">
				<option value="u">업무중</option>
				<option value="o">부재중</option>
				<option value="l">퇴사</option>
			</select>
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>권한</label></td>
		<td>
			<input type="hidden" name="column" value="6">
			<select class="form-control" id="tb_auId" name="tb_auId">
				<c:forEach var="item" items="${employeeAuth}">
					<option value="${item.memAuId}">${item.memAuName}</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<label id="la_lastUpdate" style="color: red;"></label>
		</td>
		<td colspan="3" align="center">
			<input type="hidden" name="target" value="${target}">
			<input type="button" class="btn btn-primary btn-lg" value="저장" onclick="getMemberChange(this)">
		</td>
	</tr>
</table>
</form>