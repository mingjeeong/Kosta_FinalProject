<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h3 align="center">회수</h3>

<div style="clear: both; margin-left:5%; margin-bottom:20px; width: 90%">
   <ul class="nav nav-tabs">
      <li class="nav-item"><a class="nav-link" id="firstTab" data-toggle="tab" href="#di_rent">회수 요청 조회</a></li>
      <li class="nav-item"><a class="nav-link" id="secondTab" data-toggle="tab" href="#di_refund">회수 결과 등록</a></li>
   </ul>
</div>

<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade active in" id="di_rent">
	   <h2>제품 정보</h2>
	   <hr width="95%" size="15" color="red">
	   <table align="center" style="margin-bottom: 50px; width:70%">
	      <tr class="tablePadding">
	         <td class="tablePadding" width="30%"><label>자산ID</label></td>
	         <td><label id="la_prId"></label></td>
	      </tr>
	      <tr class="tablePadding"> 
	         <td class="tablePadding"><label>물품코드</label></td>
	         <td><label id="la_itCode"></label></td>
	      </tr>
	      <tr class="tablePadding">
	         <td class="tablePadding"><label>물품명</label></td>
	         <td><label id="la_itName"></label></td>
	      </tr>
	      <tr class="tablePadding">
	         <td class="tablePadding"><label>제조사</label></td>
	         <td><label id="la_prManufacturer"></label></td>
	      </tr>
	   </table>
	   <br>
	   
	   <h3>고객 정보</h3>
	   <hr width="95%" size="15" color="red">
	   <table align="center" style="width:70%">
	      <tr class="tablePadding">
	         <td class="tablePadding" width="30%"><label>고객명</label></td>
	         <td><label id="la_viName"></label></td>
	      </tr>
	      <tr class="tablePadding">
	         <td class="tablePadding"><label>연락처</label></td>
	         <td><label id="la_viMobile"></label></td>
	      </tr>
	      <tr class="tablePadding">
	         <td class="tablePadding"><label>주소</label></td>
	         <td>
	            <span>(</span><label id="la_viPost"></label><span>)</span>
	            <label id="la_viAddr"></label><label id="la_viAddD"></label>
	         </td>
	      </tr>
	      <tr class="tablePadding">
	         <td class="tablePadding"><label>렌탈기간</label></td>
	         <td>
	            <label id="la_buyStart"></label><span>~</span><label id="la_buyEnd"></label>
	         </td>
	      </tr>
	      <tr class="tablePadding">
	         <td class="tablePadding"><label>방문 예정 일시</label></td>
	         <td>
	            <label id="la_viDay"></label>
	         </td>
	      </tr>
	   </table>
	</div>
	
	<!-- ----------------- -->
	
	<div class="tab-pane fade active hide" id="di_refund">
		<form name="refundResultForm" action="setRentalRefundResult.do" method="post">
			<table align="center" style="margin-top: 50px">
				<tr align="right" class="tablePadding">
					<td class="tablePadding"><label>회수 ID</label></td>
					<td>
						<input type="text" id="hi_outId" name="tb_depId" class="form-control" style="width: 300px;" readonly>
					</td>
				</tr>
				<tr align="right" class="tablePadding">
					<td class="tablePadding"><label>고객명</label></td>
					<td>
						<input type="text" id="hi_viName" name="tb_cuName" class="form-control" style="width: 300px;" readonly>
					</td>
				</tr>
				<tr align="right" class="tablePadding">
					<td class="tablePadding"><label>방문일</label></td>
					<td>
						<input type="datetime-local" id="hi_outDay" name="tb_viDay" class="form-control" style="width: 300px;">
					</td>
				</tr>
				<tr align="right" class="tablePadding">
					<td class="tablePadding"><label>자산 ID</label></td>
					<td>
						<input type="text" id="hi_prId" name="tb_prId" class="form-control" style="width: 300px;" readonly>
					</td>
				</tr>
				<tr align="right" class="tablePadding">
					<td class="tablePadding"><label>제품명</label></td>
					<td>
						<input type="text" id="hi_itName" name="tb_prName" class="form-control" style="width: 300px;" readonly>
					</td>
				</tr>
				<tr align="right" class="tablePadding">
					<td class="tablePadding"><label>회수 내역</label></td><td></td>
				</tr>
				<tr align="right" class="tablePadding">
					<td colspan="2" class="tablePadding">
						<textarea rows="5" cols="70" class="form-control" id="hi_outContent" name="te_content" style="resize: none;"></textarea>
					</td>
				</tr>
				<tr style="padding: 5px;">
					<td colspan="2" align="center" style="padding: 5px;">
						<input type="hidden" id="hi_buyId" name="hi_buyId">
			            <input type="hidden" id="hi_outState" name="hi_state">
			            <input type="hidden" id="hi_outSort" name="hi_sort">
			            <input type="hidden" id="hi_viId" name="hi_viId">
			            <input type="hidden" id="pagetype" value="${state}">
						<input type="button" id="aaa" name="aaa" value="저장" onclick="setDateCheck(this)">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
