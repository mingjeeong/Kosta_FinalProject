<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 정보 수정(직원)</title>
</head>
<body onload="sbSelect('${emp.state}')">
	<div align="center"> <br>
		<h3>내 정보</h3> <br>
		<form name="MemberInfoForm" action="updateMemberInfo.do" method="post">
			<table border="0" style="margin:auto; width:40%; height:50%">
				<tr>
					<td>
						<label>사번</label>
					</td>
					<td>
						<input type="text" id="tb_id" name="tb_id" title="당신의 사번"  class="form-control " style="background-color:lightgray"
						 readonly value="${emp.id}"> <br>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>
						<label>이름</label>
					</td>
					<td>
						<input type="text" id="tb_name" name="tb_name" title="한글 및 영대소문자 조합" placeholder="이름"  
						class="form-control"  required maxlength="20"  readonly value="${emp.name}" style="background-color:lightgray"> <br>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>
						<label>비밀번호</label>
					</td>
					<td>
						<input type="password" id="tb_pw" name="tb_pw" title="영대소문자 및 숫자, 특수문자 조합 8~30자리" placeholder="비밀번호"  
						class="form-control"  required maxlength="30"  readonly value="${emp.pw}" onfocus="disButton()"> <br>
					</td>
					<td>
						<input type="button" id="bt_updatePw" name="bt_updatePw"  title="수정버튼"  
									value="수정" class="btn btn-primary  editButton" onclick="updatePw()" readonly> <br>
					</td> 
				</tr>
				<tr id="tr_pwCheck">
					<td>
						<label>비밀번호 확인</label>
					</td>
					<td>
						<input type="password" id="tb_pwCheck" name="tb_pwCheck" title="영대소문자 및 숫자, 특수문자 조합 8~30자리" placeholder="비밀번호확인"  
						class="form-control"  maxlength="30"  onkeyup="checkPwRe(tb_pw.value, this.value)">
						<span id="checkPw"></span> <br>
					</td>
					<td>
					</td> 
				</tr>
				<tr>
					<td>
						<label>전화번호</label>
					</td>
					<td>
						<input type="tel" id="tb_mobile" name="tb_mobile" title="숫자 조합" placeholder="-를 제외한 전화번호를 입력하여 주세요"  
						class="form-control"  required maxlength="30"  readonly value="${emp.mobile}"> <br>
					</td>
					<td>
						<input type="button" id="bt_updateMobile" name="bt_updateMobile"  title="수정버튼"  
									value="수정" class="btn btn-primary editButton" onclick="updateMobile()"> <br>
					</td> 
				</tr>
				<tr>
					<td>
						<label>근무지</label>
					</td>
					<td>
						<input type="text" id="tb_work" name="tb_work" title="한글을 입력하세요" placeholder="근무지"  
						class="form-control"  maxlength="255"  readonly value="${emp.work}"> <br>
					</td>
					<td>
						<input type="button" id="bt_updateWork" name="bt_updateWork"  title="수정버튼"  
									value="수정"  class="btn btn-primary editButton" onclick="updateWork()"> <br>
					</td>
				</tr>
				<tr>
					<td>
						<label>입사일자</label>
					</td>
					<td>
						<input type="text" id="tb_entryDate" name="tb_entryDate" title="입사한 날짜입니다" placeholder="입사일자"  
						class="form-control"  maxlength="255"  readonly value="${emp.entryDate}" style="background-color:lightgray"> <br>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>
						<label>최근상태변경일</label>
					</td>
					<td>
						<input type="text" id="tb_update" name="tb_update" title="최근 상태를 변경한 날짜입니다" placeholder="상태변경일"  
						class="form-control"  maxlength="255"  readonly value="${emp.update}" style="background-color:lightgray"> <br>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>
						<label>상태</label>
					</td>
					<td>
						<select id="sb_state" name="sb_state" class="form-control"  required>
							<option value="업무상태">업무상태</option>
							<option value="u">업무중</option>
							<option value="o">부재중</option>
							<option value="i">퇴사</option>
						</select> <br>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td align="right">
						<input type="submit" id="submitMemberInfo" name="submitMemberInfo" value="저장"  class="btn btn-primary" >
					</td>
					<td>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>