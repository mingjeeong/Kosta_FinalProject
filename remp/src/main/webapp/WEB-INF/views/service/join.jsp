<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form name="joinForm" action="join.do" method="post" autocomplete="off">
<h3 align="center">회원가입</h3>
<table class="tableLoc table-hover" cellpadding="10px" align="center">
	<tr>
		<td width="120px"><label>아이디</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" id="tb_id" name="tb_id" type="email" pattern="^[a-zA-Z0-9]{4,20}@([a-z0-9]+\.)+[a-z0-9]{2,4}$" placeholder="아이디" required style="width: 80%; display: inline;">
			<input class="btn btn-success" type="button" value="중복확인" onclick="setJoinId()" style="width: 15%; display: inline; float: right;">
		</td>
	</tr>
	<tr>
		<td colspan="3" class="hint" align="center">
			<label id="la_idCheck"></label>
		</td>
	</tr>
	<tr>
		<td><label>비밀번호</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" id="tb_pw"  name="tb_pw" type="password" onfocus="setPw()" pattern="[0-9a-zA-z!@#$%^&*]{8,30}$" title="영대소문자 및 숫자, 특수문자 조합 8~30자리" placeholder="비밀번호" maxlength="30" required>
		</td>
	</tr><!-- [!@#$%^&*]+[0-9a-zA-Z]+ -->
	<tr>
		<td><label>비밀번호 확인</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" id="tb_rePw" name="tb_rePw" type="password" onkeyup="setPwCheck()" pattern="[0-9a-zA-z!@#$%^&*]{8,30}$" placeholder="비밀번호 확인" maxlength="30"required>
		</td>
	</tr>
	<tr>
		<td class="hint" colspan="3" align="center">
			<label id="la_pwCheck"></label>
		</td>
	</tr>
	<tr>
		<td><label>성 명</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" name="tb_name" type="text" pattern="[가-힣]{2,}" title="한글을 입력하세요." placeholder="이름" required>
		</td>
	</tr>
	<tr>
		<td><label>생년월일</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" type="date" name="tb_birth" required>
		</td>
	</tr>
	<tr>
		<td><label>전화번호</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" name="tb_mobile" type="tel" pattern="[0-9]{3}-[0-9]{3,4}-[0-9]{4}" title="'-'를 포함해주세요." placeholder="'-'를 포함해주세요.">
		</td>
	</tr>
	<tr>
		<td><label>우편번호</label></td>
		<td>
			<input class="form-control form-control-lg" id="tb_post" name="tb_post" type="number" placeholder="우편번호" readonly="readonly">
		</td>
		<td align="left">
			<input class="btn btn-success" type="button" value="우편번호" onclick="getPost()">
		</td>
	</tr>
	<tr>
		<td><label>주소</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" id="tb_addr" name="tb_addr" type="text" placeholder="주소" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td><label>상세주소</label></td>
		<td colspan="2">
			<input class="form-control form-control-lg" id="tb_addD" name="tb_addD" type="text" placeholder="상세주소" onfocus="getAddressCheck()">
		</td>
	</tr>
	<tr>
		<td><label>카드</label></td>
		<td>
			<select id="sel_card" name="sel_card" class="form-control" style="width: 90%;">
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
		</td>
		<td align="center">
			<input class="form-control form-control-sm" name="tb_card1" placeholder="카" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
			<input class="form-control form-control-sm" name="tb_card2" placeholder="드" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
			<input class="form-control form-control-sm" name="tb_card3" placeholder="번" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
			<input class="form-control form-control-sm" name="tb_card4" placeholder="호" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
		</td>		
	</tr>
	<tr>
		<td><label>계좌번호</label></td>
		<td>
			<select id="sel_account" name="sel_account" class="form-control" style="width: 90%;">
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
		</td>
		<td>
			<input class="form-control form-control-lg" name="tb_account" type="text" placeholder="계좌번호" pattern="[0-9-]{11,15}">
		</td>
	</tr>
	<tr>
		<td align="center" colspan="3">
			<input class="btn btn-primary btn-lg" type="submit" value="가입하기" style="width: 100px; margin-right: 100px;">
			<input class="btn btn-danger btn-lg" type="reset" value="초기화" style="width: 100px;">
		</td>
	</tr>
</table>
</form>