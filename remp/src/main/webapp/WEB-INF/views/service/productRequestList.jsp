<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<body onload="getProductInsertRequest()">
	<div style="width: 100%; height: 90%; overflow: scroll;">
	   <table id="procutRequestList" class="table table-striped table-hover table-bordered table-sm">
	      <thead class="thead-dark">
	         <tr>
		         <th>자산ID</th>
		         <th>상태</th>
		         <th>입고일자</th>
	         </tr>
	      <thead>
	      <tbody id="productReqList">
	      </tbody>
	   </table>
	</div>
</body>