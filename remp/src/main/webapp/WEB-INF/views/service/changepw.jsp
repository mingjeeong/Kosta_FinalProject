<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<!-- this area is changing Password -->
<div align="center">
	<h3>비밀번호 변경</h3>
	<form action="changepw.do" method="post" name="changepwform" id="changepwform">
		<div style="width:30%; height:40%; margin-top: 20px">
			<input id="presentPw" name="presentPw" type="password" placeholder="현재 비밀번호" class="form-control" required autofocus>
			<div style="margin-top : 20px; margin-bottom: 10px">
				<input id="newPw" name="newPw" type="password" placeholder="새 비밀번호" class="form-control"  required>
				<input id="checkPw" name="checkPw" type="password" placeholder="새 비밀번호 확인" class="form-control"  required>
			</div>
			<input type="button" class="btn btn-primary" value="비밀번호 번경" onclick="checkPw()">
		</div>
	</form>
</div>