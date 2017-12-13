<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div style="clear: both; margin-left:5%; width: 90%">
   <ul class="nav nav-tabs" id="rentalAsTab">
      <li class="nav-item"><a class="nav-link"  id="firstTab"  data-toggle="tab" href="#di_request">요청조회</a></li>
      <li class="nav-item"><a class="nav-link"  id="secondTab"  data-toggle="tab" href="#di_asSubmit">AS등록</a></li>
   </ul>
</div>

<form name="rentalVisitForm" action="go${state}Result.do" method="post">
<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade active in" id="di_request">
	   <h2>제품 정보</h2>
	   <hr width="95%" size="15" color="red">
	   <table align="center">
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>자산ID</label>
	         </td>
	         <td class="rent_td_content">
	         	<label id="la_prId"></label>
	         </td>
	      </tr>
	      <tr class="rent_tr"> 
	         <td class="rent_td_title">
	         	<label>물품코드</label>
	         </td>
	         <td>
	         	<label id="la_itCode"></label>
	         	</td>
	      </tr>
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>물품명</label>
	         </td>
	         <td>
	         	<label id="la_itName"></label>
	         </td>
	      </tr>
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>제조사</label>
	         </td>
	         <td>
	         	<label id="la_prManufacturer"></label>
	         </td>
	      </tr>
	   </table>
	   <br>
	   
	   <h3>고객 정보</h3>
	   <hr width="95%" size="15" color="red">
	   <table align="center">
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>고객명</label>
	         </td>
	         <td class="rent_td_content">
	         	<label id="la_viName"></label>
	         </td>
	      </tr>
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>연락처</label>
	         </td>
	         <td>
	         	<label id="la_viMobile"></label>
	         </td>
	      </tr>
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>주소</label>
	         </td>
	         <td>
	            <span>(</span>
	            <label id="la_viPost"></label>
	            <span>)</span>
	            <label id="la_viAddr"></label>
	            <label id="la_viAddD"></label>
	         </td>
	      </tr>
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>렌탈기간</label>
	         </td>
	         <td>
	            <label id="la_buyStart"></label>
	            <span>~</span>
	            <label id="la_buyEnd"></label>
	         </td>
	      </tr>
	      <tr class="rent_tr">
	         <td class="rent_td_title">
	         	<label>방문 예정 일시</label>
	         </td>
	         <td>
	            <label id="la_viDay"></label>
	         </td>
	      </tr>
	   </table>
	</div>
</form>
	
	<!-- ----------------- -->
	
<div class="tab-pane fade" id="di_asSubmit">
	<form name="asResultForm" id ="asResultForm" method="post">
		<table class="table table-striped table-hover table-bordered table-sm"  style="width:90%; margin-top:10px" align="center">
			<tr>
				<td>
					<label>AS ID</label> 
				</td>
				<td>
					<input type="text" id="hi_outId" name="hi_outId" class="form-control"  readonly style="background-color:lightgray">
				</td>
			</tr>
			<tr>
				<td>
					<label>고객명</label>
				</td>
				<td>
					<input type="text" id="hi_viName" name="hi_viName" class="form-control"  readonly style="background-color:lightgray">
					<input type="hidden" id="hi_cuId" name="hi_cuId" >
				</td>
			</tr>
			<tr>
				<td>
					<label>방문일</label>
				</td>
				<td>
					<input type="datetime-local" id=hi_outDay  name="hi_outDay" class="form-control"  style="background-color: white">
				</td>
			</tr>
			<tr>
				<td>
					<label>자산 ID</label>
				</td>
				<td>
					<input type="text" id="hi_prId" name="hi_prId" class="form-control"  readonly style="background-color:lightgray">
				</td>
			</tr>
			<tr>
				<td>
					<label>제품명</label>
				</td>
				<td>
					<input type="text" id="hi_itName" name="hi_itName" class="form-control"  readonly style="background-color:lightgray">
				</td>
			</tr>
			<tr>
				<td>
					<label>자산 상태</label> 
				</td>
				<td>
					<select class="form-control" id="hi_outState" name="hi_outState" style="background-color: white">
						<option value="do_product">영업중</option>
						<option value="re_return">회수요청</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label>as 분류</label>
				</td>
				<td>
					<select class="form-control" id="hi_outSort" name="hi_outSort" style="background-color: white">
						<option value="일반수리">일반수리</option>
						<option value="기능수리">기능수리</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label>as 내역</label>
				</td>
				<td>
					<textarea rows="4" cols="50"  id="hi_outContent" name="hi_outContent" style="resize: none;" class="form-control"></textarea>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td align="right">
					<input type="hidden" id="hi_buyId"  name="hi_buyId">
		            <input type="hidden" id="hi_viId"  name="hi_viId">
		            <input type="hidden" id="hi_viCode"  name="hi_viCode">
		            <input type="hidden" id="hi_keyValue"  name="hi_keyValue">
					<input type="button" value="저장" class="btn btn-primary" onclick="setFixInfo(this)">
				</td>
			</tr>
		</table>
	</form>
</div>
