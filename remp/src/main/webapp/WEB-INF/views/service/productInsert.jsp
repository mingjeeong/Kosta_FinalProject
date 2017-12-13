<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!-- detail -->
<div id="productInsert" >
	<div class="item_wrapper" align="center" style="border-bottom: solid; margin-bottom: 10px">
		<h3>자산등록</h3>
	</div>
	<form id="uploadImageForm" name="uploadImageForm" action="fileUpload.do" enctype="multipart/form-data" method = "post">
	<div style="float:left; ; width:45%; margin-top: 10%" align="center">
		<img id="img_itImage" name="img_itImage" style="width:350; height:280;">
		<input type="file" id="tb_file" name="tb_file" class="form-control-file" aria-describedby="fileHelp">
        <small id="fileHelp" class="form-text text-muted">
        	등록하실 사진파일을 선택하여 주세요.<br>
        	사진파일 미 선택시 변경이 불가능하게 만들어야지
        </small>
		<input type="hidden" id="fi_itId" name="fi_itId" >
	</div>
	</form>
	<form id="insertProductForm" name="insertProductForm">
		<div style="width: 55%; height: 75%; overflow: scroll; float:left;" align="center">
			<table class="is-line" style="width:100%;" align="center" >
				<tr>
					<td>
						<label style="width: 170px">자산ID</label>
					</td>
					<td>
						<input type="text" id="tb_prId" name="tb_prId"  class="prUpdate form-control" readonly>
					</td>
				</tr>
				<tr>
					<td>
						<label>품목ID</label>
					</td>
					<td>
						<input type="text" id="tb_itId" name="tb_itId"  class="prUpdate form-control" >
					</td>
				</tr>
				<tr>
					<td>
						<label>제조사</label>
					</td>
					<td>
						<input type="text" id="tb_itManufacturer" name="tb_itManufacturer"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>품목명</label>
					</td>
					<td>
						<input type="text" id="tb_itName" name="tb_itName"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>품목코드</label>
					</td>
					<td>
						<input type="text" id="tb_itCode" name="tb_itCode"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>최초입고일자</label>
					</td>
					<td>
						<input type="datetime-local" id="tb_prFirstDay" name="tb_prFirstDay"  class="prUpdate form-control" readonly>
					</td>
				</tr>
				<tr>
					<td>
						<label>입고일자</label>
					</td>
					<td>
						<input type="datetime-local" id="tb_prInDay" name="tb_prInDay"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>자산취득가</label>
					</td>
					<td>
						<input type="text" id="tb_itAcquisition" name="tb_itAcquisition"  class="prUpdate form-control">
						<label>원</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>단가(월별)</label>
					</td>
					<td>
						<input type="text" id="tb_itPrice" name="tb_itPrice"  class="prUpdate form-control">
						<label>원</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>상태</label>
					</td>
					<td>
						<select id="sb_prState" name="sb_prState"  class="prUpdate prUpdate form-control">
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
						<label>수량</label>
					</td>
					<td>
						<input type="text" id="tb_prCount" name="tb_prCount"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>출고일자</label>
					</td>
					<td>
						<input type="datetime-local" id="tb_prOutDay" name="tb_prOutDay"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>출고대상</label>
					</td>
					<td>
						<input type="text" id="tb_prOutTarget" name="tb_prOutTarget"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>지역</label>
					</td>
					<td>
						<input type="text" id="tb_prLocation" name="tb_prLocation"  class="prUpdate form-control">
					</td>
				</tr>
				<tr>
					<td>
						<label>기본대여기간</label>
					</td>
					<td>
						<input type="text" id="tb_itPeriod" name="tb_itPeriod"  class="prUpdate form-control">
						<label>개월</label>
					</td>
				</tr>
				<tr>
				<tr>
					<td>
						<label>제품설명</label>
					</td>
					<td>
						<textarea rows="4" cols="40" id="tb_itContent" name="tb_itContent" class="prUpdate form-control" style="resize: none;"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div style="clear:both;" align="right">
			<input type="button" id="bt_submit" name="bt_submit"  value="등록" style="margin-top:10px; width:90px; height:35px"
					   class="btn btn-primary" onclick="setProductInsert(this)"> 
			<input type="hidden" id="tb_itImage" name="tb_itImage" >
		</div>
	</form>
</div>
