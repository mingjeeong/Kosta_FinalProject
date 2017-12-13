<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!-- detail -->
<div  id="productCare">
	<div class="item_wrapper" align="center">
		<h3>자산관리</h3>
		<div class="left-w80" align="center">
			<input id="keyword" type="search" class="form-control" style="width: 500px; margin-left:280px">
		</div>
		<div class="left-w20">
		<button class="btn btn-primary" onclick="getProductRequest(this)">조회</button>
		</div>	
	</div>
	<div style="width: 100%; height: 80%; overflow: scroll;" id="spanDiv">
		<table id="productRequestResult" class="table table-striped table-hover table-bordered table-sm is-line">
			<thead class="thead-dark">
				<tr>
					<th>자산ID</th>
					<th>구매ID</th>
					<th>품목ID</th>
					<th>품목명</th>
					<th>최초입고일자</th>
					<th>입고일자</th>
					<th>출고일자</th>
					<th>출고대상</th>
					<th>현재위치</th>
					<th>자산상태</th>
					<th>수량</th>
					<th>QR코드</th>
					<th>인쇄</th>
					<th>수정</th>
				</tr>
			</thead>
			<tbody id="productList">
			</tbody>
		</table>
		<div id="printQR" style="display:none"></div>
	</div>
</div>

<!-- 자산변경부분 -->
<div id="productUpdate" style="display:none">
	<div class="item_wrapper" >
		<h3>자산변경</h3>
	</div>
		<form id="uploadImageForm" name="uploadImageForm" action="fileUploadUp.do" enctype="multipart/form-data" method = "post">
		<div style="float:left; ; width:45%; margin-top: 5%;" align="center">
			<!-- <img id="img_itImage" name="img_itImage" style="width:350; height:280;"> -->
			<input type="file" id="tb_file" name="tb_file"  class="form-control-file" aria-describedby="fileHelp" disabled>
	        <small id="fileHelp" class="form-text text-muted">
	        	등록하실 사진파일을 선택하여 주세요.<br>
	        	전체변경하기를 누르시면 사진변경이 가능합니다.
	        </small>
			<input type="hidden" id="fi_prId" name="fi_prId" >
			<input type="hidden" id="hi_itImage" name="hi_itImage">
		</div>
		</form>
		<div style="width: 55%; height: 85%; overflow: scroll; float:left;" align="center">
		<form id="updateProductForm" name="updateProductForm" >
			<table class="is-line" style="width:80%;" align="center" >
				<tr>
					<td style="width:150px">
						자산ID
					</td>
					<td>
						<input type="text" id="tb_prId" name="tb_prId"  class="prUpdate form-control"  readonly style="background-color:lightgray">
					</td>
				</tr>
				<tr>
					<td>
						품목명
					</td>
					<td>
						<input type="text" id="tb_itName" name="tb_itName"  class="prUpdate form-control" readonly style="background-color:lightgray">
					</td>
				</tr>
				<tr>
					<td>
						품목코드
					</td>
					<td>
						<input type="text" id="tb_itCode" name="tb_itCode"  class="prUpdate form-control" readonly style="background-color:lightgray">
					</td>
				</tr>
				<tr>
					<td>
						제조사
					</td>
					<td>
						<input type="text" id="tb_itManufacturer" name="tb_itManufacturer"  class="prUpdate form-control"readonly style="background-color:lightgray">
					</td>
				</tr>
				<tr>
					<td>
						입고일자
					</td>
					<td>
						<input type="datetime-local" id="tb_prInDay" name="tb_prInDay"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						취득가
					</td>
					<td>
						<input type="text" id="tb_itAcquisition" name="tb_itAcquisition"  class="prUpdate form-control" readonly style="background-color:lightgray">
						<label>원</label>
					</td>
				</tr>
				<tr>
					<td>
						단가(월간)
					</td>
					<td>
						<input type="number" id="tb_itPrice" name="tb_itPrice"  class="prUpdate form-control" readonly style="background-color:lightgray">
						<label>원</label>
					</td>
				</tr>
				<tr>
					<td>
						자산상태
					</td>
					<td>
						<select id="sb_prState" name="sb_prState"  class="prUpdate form-control">
							<option value="none">상태를 선택하세요</option>
							<option value="re_ninput">신규입고요청</option>
							<option value="re_npart">신규수리부속요청</option>
							<option value="re_nother">신규기타자산입고요청</option>
							<option value="re_exinput">외부수리입고요청</option>
							<option value="re_output">렌탈출고요청</option>
							<option value="re_exoutput">외부수리출고요청</option>
							<option value="re_reinput">회수입고요청</option>
							<option value="re_disuse">폐기요청</option>
							<option value="re_repair">수리요청</option>
							<option value="re_return">회수요청</option>
							<option value="wa_ninput">등록대기</option>
							<option value="wa_policy">정책대기</option>
							<option value="wa_product">영업대기</option>
							<option value="wa_check">점검대기</option>
							<option value="wa_repair">수리대기</option>
							<option value="do_product">영업중</option>
							<option value="do_disuse">폐기</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						수량
					</td>
					<td>
						<input type="text" id="tb_prCount" name="tb_prCount"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						출고일자
					</td>
					<td>
						<input type="datetime-local" id="tb_prOutDay" name="tb_prOutDay"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						출고대상
					</td>
					<td>
						<input type="text" id="tb_prOutTarget" name="tb_prOutTarget"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						기본대여기간
					</td>
					<td>
						<input type="text" id="tb_itPeriod" name="tb_itPeriod"  class="prUpdate form-control"  readonly style="background-color:lightgray">
						<label>개월</label>
					</td>
				</tr>
				<tr>
					<td>
						지역
					</td>
					<td>
						<input type="text" id="tb_prLocation" name="tb_prLocation"  class="prUpdate form-control" >
					</td>
				</tr>
				<tr>
					<td>
						제품설명
					</td>
					<td>
						<textarea rows="4"  cols="40"  id=ta_itContent name="ta_itContent"  class="prUpdateTa form-control"  readonly style="background-color:lightgray"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						요구사항
					</td>
					<td>
						<textarea rows="4"  cols="40"  id="ta_prNeeds" name="ta_prNeeds"  class="prUpdateTa form-control" ></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<input type="button" id="bt_edit" name="bt_edit"  value="변경" style="margin-right:10px; width:90px; height:35px" 
								   onclick="setProductUpdate(this)" class="btn btn-primary">
						<input type="button" id="bt_doAllEdit" name="bt_doAllEdit"  value="전체변경하기"  class="btn btn-info"
								   style="margin-right:10px; width:100px; height:35px; align-self: left" onclick="doAllEdit()" >
						<input type="button" id="bt_allEdit" name="bt_allEdit"  value="전체변경"  class="btn btn-primary"
								   style="margin-right:10px; width:90px; height:35px; display: none" onclick="setProductUpdateAll(this)">
						<input type="button" id="bt_backProduct" name="bt_backProduct"  value="뒤로" style="width:90px; height:35px" 
								   onclick="doKeywordBack(hi_keyword.value)" class="btn btn-secondary">
						<input type="hidden" id="hi_keyword" name="hi_keyword">
						<input type="hidden" id="hi_itId" name="hi_itId">
						<input type="hidden" id="tb_itImage" name="tb_itImage" >
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>