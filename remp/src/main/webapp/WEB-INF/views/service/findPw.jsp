<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>
	<div align="center"> <br>
		<h3>비밀번호 찾기</h3> <br>
		<form name="selectPw" action="selectPw.do" method="post">
			<table style="width:30%; height:40%; margin:auto">
				<tr>
					<td>
						<label>아이디</label>
					</td>
					<td>
						<input type="text" id="tb_id" name="tb_id" title="영대소문자 및 숫자조합 이메일형식" placeholder="이메일 형식의 아이디를 적어주세요"  
						class="form-control " autofocus required maxlength="40" >
					</td>
				</tr>
				<tr>
					<td>
						<label>이름</label>
					</td>
					<td>
						<input type="text" id="tb_name" name="tb_name" title="한글 및 영대소문자 조합" placeholder="회원님의 이름을 적어주세요"  
						class="form-control"  required maxlength="20" >
					</td>
				</tr>
				<tr>
					<td>
						<label>생년월일</label>
					</td>
					<td>
						<input type="date"  id="tb_birth" name="tb_birth" title="날짜를 선택해주세요" placeholder="회원님의 생년월일을 적어주세요"  
						class="form-control"  required >
					</td>
				</tr>
				<tr>
					<td>
						<label>전화번호</label>
					</td>
					<td>
						<input type="tel" id="tb_mobile" name="tb_mobile" title="숫자 조합" placeholder="-를 포함한 전화번호를 입력하여 주세요"  
						class="form-control"  required maxlength="30" >
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" id="submit" name="submit" value="비밀번호 찾기"  class="btn btn-primary" > <br>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>