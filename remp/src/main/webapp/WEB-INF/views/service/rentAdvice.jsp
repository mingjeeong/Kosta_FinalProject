<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h3 align="center">상담</h3>
<form id="nomalAdviceForm" name="nomalAdviceForm" action="setNomalAdvice" method="post">
<table style="width: 70%; margin: 30px auto;" class="is-line">
   <tr>
      <td width="20%" align="center">
         <label>전화번호</label>
      </td>
      <td>
         <input type="text" id="tb_cuMobile" class="form-control is-valid">
      </td>
      <td align="right">
         <div class="btn-group">
            <label for="cb_nomal" class="btn btn-outline-warning">
               <input type="checkbox" id="cb_group" name="cb_group" value="nomal">일반상담
            </label>
            <label for="cb_rent" class="btn btn-outline-warning">
               <input type="checkbox" id="cb_group" name="cb_group" value="rental">렌탈상담
            </label>
            <label for="cb_as" class="btn btn-outline-warning">
               <input type="checkbox" id="cb_group" name="cb_group" value="as">A/S상담
            </label>
            <label for="cb_back" class="btn btn-outline-warning">
               <input type="checkbox" id="cb_group" name="cb_group" value="refund">회수상담
            </label>
         </div>
      </td>
   </tr>
   <tr>
      <td align="center">
         <span><p>상담내용</p><p>및</p><p>조치사항</p></span>
      </td>
      <td colspan="2">
         <textarea rows="5" cols="90" id="ta_adContent" name="ta_adContent" class="form-control" style="resize: none;"></textarea>
      </td>
   </tr>
</table>
</form>

<div style="clear: both; margin-left:5%; width: 90%">
   <ul class="nav nav-tabs">
      <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#di_rent">렌탈상담</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#di_as">A/S상담</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#di_back">회수상담</a></li>
   </ul>
</div>


<div id="myTabContent" class="tab-content">


<!-- 렌탈상담 -->


   <div class="tab-pane fade active in active show" id="di_rent">
   <form id="rentalAdviceForm" name="rentalAdviceForm" action="setRentalAdvice" method="post">
     <table class="table is-line" style="width: 90%; margin: 30px auto">
       <tr>
          <td>
             <label>식별정보</label>
          </td>
          <td colspan="4">
             <input type="text" id="tb_cuId" name="tb_cuId" class="form-control" placeholder="아이디" style="width: 52%; display: inline;" onclick="setAddCustomerCheckF()" onkeyup="getVisitCustomer(this)">
             <input type="text" id="tb_cuName" name="tb_cuName" class="form-control" placeholder="이름" style="width: 46%; display: inline;">
          </td>
          <td></td>
          <td width="100px">
             <label>생년월일</label>
          </td>
          <td>
             <input type="date" id="tb_cuBirth" name="tb_cuBirth" class="form-control">
          </td>
          <td colspan="2">
             <input type="text" id="tb_cuResult" class="form-control" disabled style="width: 49%; display: inline;">
             <input type="button" id="bt_temporary" class="btn btn-danger" value="임시 회원등록" onclick="temporary(this)" style="width: 49%; display: inline;">
          </td>
       </tr>
       <tr>
          <td>
             <label>렌탈정보</label>
          </td>
          <td colspan="4">
             <input type="text" class="form-control" placeholder="품목ID" id="tb_itId" name="tb_itId" style="width: 35%; display: inline;">
             <input type="button" class="btn btn-success" value="조회" onclick="getProductCount()" style="width: 18%; display: inline;">
             <input type="text" class="form-control" placeholder="품목명" id="tb_itName" name="tb_itName" readonly style="width: 35%; display: inline; float: right;">
          </td>
          <td></td>
          <td>
             <label>렌탈기간</label>
          </td>
          <td>
             <input type="date" id="tb_start" name="tb_start" class="form-control" placeholder="시작일" onchange="defaultDate()">
          </td>
          <td>
             <input type="date" id="tb_end" name="tb_end" class="form-control" placeholder="종료일">
          </td>
          <td>
             <input type="text" id="tb_itPeriod" name="tb_itPeriod" class="form-control" placeholder="개월수">
          </td>
       </tr>
       <tr>
          <td>
             <label>수량 & 가격</label>
          </td>
          <td colspan="2">
             <input type="number" id="tb_itNumber" name="tb_itNumber" class="form-control" placeholder="렌탈수량" min="0" style="width: 49%; display: inline;">
             <input type="number" id="tb_itPrice" name="tb_itPrice" class="form-control" placeholder="렌탈가격" min="0" style="width: 49%; display: inline; float: right;" readonly>
          </td>
          <td colspan="3" width="120px">
             <label>재고상태</label>
          </td>
          <td colspan="4">
             <input type="text" id="tb_waitCount" class="form-control" placeholder="재고수량" readonly style="width: 33%; display: inline; float: right;">
             <input type="text" id="tb_doCount" class="form-control" placeholder="회수대기" readonly style="width: 33%; display: inline;">
             <input type="text" id="tb_refundCount" class="form-control" placeholder="영업중" readonly style="width: 33%; display: inline; float: right;">
          </td>
       </tr>
       <tr>
          <td rowspan="2">
             <label>인도지</label>
          </td>
          <td colspan="2">
             <input type="text" class="form-control" id="tb_post" name="tb_post" placeholder="우편번호" style="width: 70%; display: inline;">
             <input type="button" class="btn btn-success" value="우편번호" onclick="getPost()" style="width: 28%; display: inline;  float: right;">
          </td>
          <td colspan="7">
             <input type="text" class="form-control" id="tb_addr" name="tb_addr" placeholder="도로명 주소">
          </td>
       </tr>
       <tr>
          <td colspan="9">
             <input type="text" class="form-control" id="tb_addD" name="tb_addD" placeholder="상세주소">
          </td>
       </tr>
       <tr>
          <td>
             <label>결제방법</label>
          </td>
          <td colspan="4">
         <div class="btn-group" data-toggle="buttons">
         <label class="btn btn-outline-info active">
            <input type="radio" name="rb_cashMethod" checked onchange="buyMethod(this)" value="account">계좌이체
         </label>
         <label class="btn btn-outline-info">
            <input type="radio" name="rb_cashMethod" onchange="buyMethod(this)" value="card">카드결제
         </label>
         <label class="btn btn-outline-info">
            <input type="radio" name="rb_cashMethod" onchange="buyMethod(this)" value="money">현금결제
         </label>
         </div>
          </td>
          <td></td>
          <td>
             <label>납부방법</label>
          </td>
          <td colspan="3">
             <div class="btn-group" data-toggle="buttons">
             <label class="btn btn-outline-info active">
                <input type="radio" name="rb_timeMethod" value="매월납">매월납
             </label>
             </div>
          </td>
       </tr>
       <tr>
          <td>
             <label>결제정보</label>
          </td>
          <td colspan="7">
               <select id="sel_account" name="sel_account"  class="form-control" style="width: 20%;">
                  <option value="">은행을 선택해주세요</option>
                <option value="농협">농협</option>
                <option value="우리은행">우리은행</option>
                <option value="신한은행">신한은행</option>
                <option value="국민은행">국민은행</option>
                <option value="하나은행">하나은행</option>
                <option value="외환은행">외환은행</option>
                <option value="씨티은행">씨티은행</option>
                <option value="기업은행">기업은행</option>
                <option value="우체국">우체국</option>
                <option value="부산은행">부산은행</option>
                <option value="SC은행">SC은행</option>
               </select>
               <select id="sel_card" name="sel_card" class="form-control" style="width: 20%; display: none;">
                  <option value="">카드를 선택해주세요</option>
                <option value="현대카드">현대카드</option>
                <option value="KB국민카드">KB국민카드</option>
                <option value="신한카드">신한카드</option>
                <option value="삼성카드">삼성카드</option>
                <option value="롯데카드">롯데카드</option>
                <option value="우리카드">우리카드</option>
                <option value="하나카드">하나카드</option>
                <option value="비씨카드">비씨카드</option>
                <option value="NH농협카드">NH농협카드</option>
                <option value="씨티카드">씨티카드</option>
                <option value="카카오뱅크">카카오뱅크</option>
               </select>
               <input type="text" class="form-control" id="tb_payNum" name="tb_payNum" style="width: 40%;">
          </td>
          <td colspan="2"></td>
       </tr>
    </table>
   </form>
   </div>
  
  <!-- as상담 -->
  <div class="tab-pane fade" id="di_as">
  	<form id="asAdviceForm" name="asAdviceForm">
	    <table class="table table-striped" style="width: 90%; margin: 30px auto">
	    	<tr>
	    		<td style="width:110px">
	    			<label>식별정보</label>
	    		</td>
	    		<td colspan="2">
	    			<input type="text" class="form-control"  id="tb_cuId" name="tb_cuId" placeholder="아이디" onkeyup="getVisitCustomer(this), getVisitItem(this)">
	    		</td>
	    		<td>
	    			<input type="text" class="form-control"  id="tb_cuName" name="tb_cuName" placeholder="이름" readonly  >
	    		</td>
	    		<td style="width: 100px">
	    			<label>생년월일</label>
	    		</td>
	    		<td>
	    			<input type="text" class="form-control"  id="tb_cuBirth" name="tb_cuBirth"  placeholder="생년월일" readonly  >
	    		</td>
	    		<td>
	    			<input type="text" class="form-control"  id="tb_cuResult" name="tb_cuResult"  placeholder="가입상태" readonly  >
	    		</td>
	    		<td>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td>
	    			<label>렌탈정보</label>
	    		</td>
	    		<td colspan="3">
	    			<span>구매ID</span>
	    			<select id="sb_item" name="sb_item" style="width: 150px" onchange="changeValue(this)">
	    				<option id="list">-- 자산목록 --</option>
	    			</select>
	    			<input type="hidden" id="hi_itId" name="hi_itId">
	    		<td>
	    			<label>렌탈기간</label>
	    		</td>
	    		<td>
	    			<input type="text" class="form-control"  id="tb_buyStart" name="tb_buyStart"  placeholder="시작일" readonly>
	    		</td>
	    		<td>	
	    			<input type="text" class="form-control"  id="tb_buyEnd" name="tb_buyEnd"  placeholder="종료일" readonly>
	    		</td>
	    		<td>
	    			<input type="text" class="form-control"  id="tb_buyMonth" name="tb_buyMonth"  placeholder="개월 수" readonly>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td rowspan="2">
	    			<label>주소지</label>
	    		</td>
	    		<td colspan="2">
	    			<input type="text" class="form-control"  id="tb_post" name="tb_post"  placeholder="우편번호">
	    		</td>
	    		<td>
					<input type="button" class="btn btn-success" value="우편번호찾기" onclick="getPost(this)" style="display: inline;  float: right;">
	    		</td>
	    		<td colspan="4">
	    			<input type="text" class="form-control"  id="tb_addr" name="tb_addr"  placeholder="주소">
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="7">
	    			<input type="text" class="form-control"  id="tb_addD" name="tb_addD"  placeholder="상세주소">
	    		</td>
	    	</tr>
	    	<tr>
   	    		<td>
    			<label>As요청일</label>
		   		</td>
		   		<td colspan="3">
		   			<input type="datetime-local" class="form-control"  id="tb_reDay" name="tb_reDay" >
		   		</td>
	    		<td rowspan="2">
	    			<label>이상내용</label>
	    		</td>
	    		<td rowspan="2"  colspan="7">
	    			<textarea rows="4" cols="80" class="form-control"  id="ta_adContent" name="ta_adContent"  placeholder="이상내용"></textarea>
	    		</td>
	    	</tr>
	    	<tr>
	    	</tr>
	    </table>
	 </form>
  </div>
  
  <!-- 회수상담 -->
  <div class="tab-pane fade" id="di_back">
  	<form id="refundAdviceForm" name="refundAdviceForm">
        <table class="table table-striped" style="width: 90%; margin: 30px auto">
    	<tr>
    		<td style="width: 110px">
    			<label>식별정보</label>
    		</td>
    		<td colspan="2">
    			<input type="text" class="form-control"  id="tb_cuId" name="tb_cuId"  placeholder="아이디" onkeyup="getVisitCustomer(this), getVisitItem(this)">
    		</td>
    		<td>
    			<input type="text" class="form-control"  id="tb_cuName" name="tb_cuName"  placeholder="이름" readonly  >
    		</td>
    		<td style="width: 100px">
    			<label>생년월일</label>
    		</td>
    		<td>
    			<input type="text" class="form-control"  id="tb_cuBirth" name="tb_cuBirth"  placeholder="생년월일" readonly  >
    		</td>
    		<td>
    			<input type="text" class="form-control"  id="tb_cuResult" name="tb_cuResult"  placeholder="가입상태" readonly >
    		</td>
    		<td>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<label>렌탈정보</label>
    		</td>
    		<td colspan="3">
    			<span>구매ID</span>
	    			<select id="sb_item" name="sb_item" style="width: 150px" onchange="changeValue(this)">
	    				<option id="list">-- 자산목록 --</option>
	    			</select>
	    			<input type="hidden" id="hi_itId" name="hi_itId">
    		</td>
    		<td>
    			<label>렌탈기간</label>
    		</td>
    		<td>
    			<input type="text" class="form-control"  id="tb_buyStart" name="tb_buyStart"  placeholder="시작일" readonly  >
    		</td>
    		<td>	
    			<input type="text" class="form-control"  id="tb_buyEnd" name="tb_buyEnd"  placeholder="종료일" readonly  >
    		</td>
    		<td>
    			<input type="text" class="form-control"  id="tb_buyMonth" name="tb_buyMonth"  placeholder="개월 수" readonly  >
    		</td>
    	</tr>
    	<tr>
    		<td rowspan="2">
    			<label>주소지</label>
    		</td>
    		<td colspan="2">
    			<input type="text" class="form-control"  id="tb_post" name="tb_post"  placeholder="우편번호">
    		</td>
    		<td>
    			<input type="button" class="btn btn-success" value="우편번호찾기" onclick="getPost(this)" style="display: inline;  float: right;">
    		</td>
    		<td colspan="4">
    			<input type="text" class="form-control"  id="tb_addr" name="tb_addr"  placeholder="주소">
    		</td>
    	</tr>
    	<tr>
    		<td colspan="7">
    			<input type="text" class="form-control"  id="tb_addD" name="tb_addD"  placeholder="상세주소">
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<label>회수요청일</label>
    		</td>
    		<td colspan="3">
    			<input type="datetime-local" class="form-control"  id="tb_reDay" name="tb_reDay" >
    		</td>
    		<td>
    			<label>회수사유</label>
    		</td>
    		<td colspan="3">
    		<div class="btn-group" data-toggle="buttons" >
				<label class="btn btn-outline-info active btn-lg">
					<input type="radio"  id="rb_maxRefund"  name="rb_expiration"  value="만기회수"  checked> 만기회수
				</label>
				<label class="btn btn-outline-info btn-lg">
					<input type="radio"   id="rb_stopRefund"  name="rb_expiration"  value="중도해지" > 중도해지
				</label>
				<label class="btn btn-outline-info btn-lg">
					<input type="radio"  id="rb_etc" name="rb_expiration"  value="기타" > 기타
				</label>
			</div>
    	</tr>
    	<tr>
    		<td>
    			<label>추가사항</label>
    		</td>
    		<td colspan="7">
    			<textarea rows="2" cols="80" class="form-control"  id="ta_adContent" name="ta_adContent"  placeholder="추가사항"></textarea>
    		</td>
    	</tr>
      </table>
    </form>
  </div>
</div>
   
<div align="center">
   <input type="button" class="btn btn-primary btn-lg" value="등록" onclick="checkAdvice()">
</div>
</body>
</html>