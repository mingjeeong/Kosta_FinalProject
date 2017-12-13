<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!-- detail -->
<div class="item_wrapper">
	<div class="left-w20">
		<span class="btn btn-secondary">렌탈출고자산</span>
	</div>
	<div class="left-w70">
		<input id="keyword" type="text" class="form-control">
	</div>
	<div class="left-w10">
		<button class="btn btn-primary" onclick="getrequestsearchassetlist()">조회</button>
	</div>
</div>
<div style="width: 100%; height: 80%; overflow: scroll;">
	<table class="table table-striped table-hover table-bordered table-sm cursor is-line">
		<thead class="thead-dark headfix">
			<tr>
				<th>요청일자</th>
				<th>요청ID</th>
				<th>자산ID</th>
				<th>품목ID</th>
				<th>출고수량</th>
				<th>출고</th>
			</tr>
		</thead>
		<tbody id="productList">
		</tbody>
	</table>
</div>