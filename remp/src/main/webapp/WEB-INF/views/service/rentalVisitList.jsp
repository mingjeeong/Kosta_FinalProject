<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="item_wrapper">
   <div class="left-w80" style="float: left; width: 30%;">
      <select name="sel_visit" id="sel_visit" class="form-control" onchange="selectCheck(this)">
         <option value="all">전체</option>
         <option value="product">품목ID</option>
         <option value="day">요청일</option>
         <option value="status">상태</option>
      </select>
   </div>
   <div style="float: left; width: 50%;">
      <input id="se_all" type="search" class="form-control" readonly>
      <input type="text" id="se_product" class="form-control" style="display: none;">
      <input id="se_date" type="text" class="form-control" style="display: none;" onkeyup="keyLength(this)" maxlength="10">
      <select id="se_complete" class="form-control" style="display: none;">
         <option value="N">미처리</option>
         <option value="Y">처리완료</option>
      </select>
      <input type="hidden" id="keyword">
   </div>
   <div class="left-w20" style="width: 19%">
      <button class="btn btn-primary" onclick="searchCheck(this)">조회</button>
      <input type="hidden" id="pagetype" value="${state}">
   </div>
</div>

<div style="width: 100%; height: 80%; overflow: scroll;">
   <table id="visitRequestResult" class="table table-striped table-hover table-bordered table-sm">
      <thead class="thead-dark">
         <tr><th>고객ID</th><th>제품명</th><th>방문요청일</th><th>상태</th></tr>
      <thead>
      <tbody></tbody>
   </table>
</div>