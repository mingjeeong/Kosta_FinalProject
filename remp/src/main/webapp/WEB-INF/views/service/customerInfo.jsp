<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h3 align="center">고객 정보 변경</h3>
<form id="customerInfoForm" name="customerInfoForm" method="post" action="setMemberChange.do">
<table class="table table-striped" style="margin-top:30px;">
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
			<input type="button" class="btn btn-warning" value="부여" name="bt_chPw" onclick="setNewPw()">
			<input type="button" class="btn btn-warning" value="변경" name="bt_chPw" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>성명</label></td>
		<td>
			<input type="text" class="form-control" id="tb_memName" name="tb_memName" placeholder="성명">
			<input type="hidden" name="column" value="2">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" name="bt_chName" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>전화번호</label></td>
		<td>
			<input type="text" class="form-control" id="tb_memMobile" name="tb_memMobile" placeholder="휴대전화">
			<input type="hidden" name="column" value="3">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" name="bt_chMobile" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>카드번호</label></td>
		<td>
			<select id="sel_card" name="sel_card" class="form-control" style="width: 30%; display: inline; margin-right: 100px;">
	       	  	<option value="">카드를 선택해주세요</option>
                <option value="현대카드">현대카드</option>
                <option value="KB국민카드">KB국민카드</option>
                <option value="신한카드">신한카드</option>
                <option value="삼성카드">삼성카드</option>
                <option value="롯데카드">롯데카드</option>
                <option value="우리카드">우리카드</option>
                <option value="하나카드">하나카드</option>
                <option value="비씨카드">비씨카드</option>
                <option value="NH농협카드">NH농협카드</option>
                <option value="씨티카드">씨티카드</option>
                <option value="카카오뱅크">카카오뱅크</option>
	       	  </select>
	       	<input type="text" id="tb_card1" name="tb_card1" class="form-control" style="width: 13%; display: inline;" placeholder="카" onkeyup="cardNext(this)" maxlength="4">
			<input type="text" id="tb_card2" name="tb_card2" class="form-control" style="width: 13%; display: inline;" placeholder="드" onkeyup="cardNext(this)" maxlength="4">
			<input type="text" id="tb_card3" name="tb_card3" class="form-control" style="width: 13%; display: inline;" placeholder="번" onkeyup="cardNext(this)" maxlength="4">
			<input type="text" id="tb_card4" name="tb_card4" class="form-control" style="width: 13%; display: inline;" placeholder="호" maxlength="4">
			<input type="hidden" name="column" value="4">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" name="bt_chMethod" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>계좌번호</label></td>
		<td>
			<select id="sel_account" name="sel_account" class="form-control" style="width: 30%; display: inline; margin-right: 100px;">
				<option value="">은행을 선택해주세요</option>
				<option value="농협">농협</option>
				<option value="우리은행">우리은행</option>
				<option value="신한은행">신한은행</option>
				<option value="국민은행">국민은행</option>
				<option value="하나은행">하나은행</option>
				<option value="외환은행">외환은행</option>
				<option value="씨티은행">씨티은행</option>
				<option value="기업은행">기업은행</option>
				<option value="우체국">우체국</option>
				<option value="부산은행">부산은행</option>
				<option value="SC은행">SC은행</option>
			</select>
			<input type="text" id="tb_memAccount" name="tb_memAccount" class="form-control" style="width: 40%; display: inline;" placeholder="계좌번호" maxlength="20">
			<input type="hidden" name="column" value="7">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" name="bt_chMethod" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>주소</label></td>
		<td>
			<input type="hidden" name="column" value="5">
			<input type="text" class="form-control" id="tb_post" name="tb_post" style="width: 20%; display: inline;" readonly placeholder="우편번호">
			<input type="text" id="tb_addr" name="tb_addr" class="form-control" style="width: 78%; display: inline; float: right;" readonly placeholder="주소">
			<input type="text" id="tb_addD" name="tb_addD" class="form-control" onfocus="getAddressCheck()" placeholder="상세주소">
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="조회" onclick="getPost()">
			<input type="button" class="btn btn-warning" value="변경" name="bt_chAddress" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td><label>회원상태</label></td>
		<td>
			<input type="hidden" name="column" value="6">
			<input type="hidden" name="hi_memState" id="hi_memState">
			<select class="form-control" id="tb_memState" name="tb_memState">
				<option value="u">사용중</option>
				<option value="s">정지</option>
				<option value="l">탈퇴</option>
			</select>
		</td>
		<td>
			<input type="button" class="btn btn-warning" value="변경" name="bt_chState" onclick="setMemberItemChange(this)">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<label id="la_lastUpdate" style="color: red;"></label>
		</td>
		<td colspan="3" align="center">
			<input type="hidden" id="target" name="target" value="${target}">
			<input type="button" class="btn btn-primary btn-lg" value="일괄변경" onclick="getMemberChange(this)">
		</td>
	</tr>
</table>
</form>