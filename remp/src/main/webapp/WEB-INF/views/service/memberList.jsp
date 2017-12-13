<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="item_wrapper">
	<div class="left-w80">
		<input id="keyword" type="text" class="form-control" onkeyup>
	</div>
	<div class="left-w20">
		<button class="btn btn-primary" onclick="getMemberList(this)" value="${target}">조회</button>
	</div>
</div>

<div style="width: 100%; height: 80%; overflow: scroll;">
	<table id="memberList" class="table table-striped table-hover table-bordered table-sm">
		<thead class="thead-dark">
			<tr><th>순서</th><th>아이디</th></tr>
		<thead>
		<tbody></tbody>
	</table>
</div>