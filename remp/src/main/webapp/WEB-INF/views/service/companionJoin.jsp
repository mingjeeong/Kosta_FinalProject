<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h3 align="center">직원 등록</h3>
<form action="addCompanionJoin.do" method="post">
<table class="table table-striped" style="margin-top:30px">
	<tr>
		<td width="13%"><label>아이디</label></td>
		<td colspan="2" width="75%">
			<input type="text" class="form-control" id="tb_memId" name="tb_memId" placeholder="아이디">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="자동" onclick="setRandomId()">
		</td>
	</tr>
	<tr>
		<td><label>비밀번호</label></td>
		<td colspan="2">
			<input type="password" class="form-control" id="tb_memPw" name="tb_memPw" placeholder="비밀번호">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="자동" onclick="setNewPw()">
		</td>
	</tr>
	<tr>
		<td><label>성명</label></td>
		<td colspan="2">
			<input type="text" class="form-control" id="tb_memName" name="tb_memName" placeholder="성명">
		</td>
		<td>
		</td>
	</tr>
	<tr>
		<td><label>전화번호</label></td>
		<td colspan="2">
			<input type="text" class="form-control" id="tb_memMobile" name="tb_memMobile" placeholder="전화번호">
		</td>
		<td></td>
	</tr>
	<tr>
		<td><label>입사일</label></td>
		<td colspan="2">
			<input type="date" class="form-control" id="tb_memEntry" name="tb_memEntry">
		</td>
		<td></td>
	</tr>
	<tr>
		<td><label>근무지위치</label></td>
		<td colspan="2">
			<input type="text" class="form-control" id="tb_memWork" name="tb_memWork" placeholder="근무지">
		</td>
		<td></td>
	</tr>
	<tr>
		<td><label>회원상태</label></td>
		<td>
			<select class="form-control" id="tb_memState" name="tb_memState">
				<option value="u">업무중</option>
				<option value="o">부재중</option>
				<option value="l">퇴사</option>
			</select>
		</td>
		<td></td><td></td>
	</tr>
	<tr>
		<td><label>권한</label></td>
		<td>
			<select class="form-control" id="tb_auId" name="tb_auId">
				<c:forEach var="item" items="${employeeAuth}">
					<option value="${item.memAuId}">${item.memAuName}</option>
				</c:forEach>
			</select>
		</td>
		<td></td><td></td>
	</tr>
	<tr>
		<td colspan="5" align="right">
			<input type="button" class="btn btn-primary btn-lg" id="bt_insert" value="저장" onclick="getMemberChange(this)">
			<input type="reset" class="btn btn-primary btn-lg" id="bt_onInsert" value="초기화" onclick="insertOn()">
		</td>
	</tr>
</table>
</form>