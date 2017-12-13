package com.remp.work.model.service;

import java.util.HashMap;

import org.json.simple.JSONObject;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

/**
 * @author 이원호
 *
 */
public class SmsService {
	
	/**
	 * coolSMS를 이용한 sms 서비스
	 * @param mobile 전화번호
	 * @param emId 직원ID
	 * @param emPw 직원PW
	 * @return
	 * @author 이원호
	 * @since ReMP v 1.0
	 */
	public boolean sendSms(String mobile, String emId, String emPw) {
		System.out.println("시작");
	    String api_key = "NCSDRVCBUATDBU01";
	    String api_secret = "MOY0APKM3A1DXMA6E3SDIYFIUZOKX2QG";
	    Message coolsms = new Message(api_key, api_secret);

	    StringBuilder msg = new StringBuilder();
	    msg.append("[GIST]\n");
	    msg.append("개인정보 변경 안내\n");
	    msg.append("회원의 아이디 : "+emId+"\n");
	    msg.append("회원의 비밀번호 : "+emPw+"\n");
	    
	    String[] spMobile = mobile.split("-");
	    String phone = "";
	    for(int i=0; i<spMobile.length; i++) {
	    	phone += spMobile[i].trim();
	    }
	    
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", phone);
	    params.put("from", "01036113608");
	    params.put("type", "SMS");
	    params.put("text", msg.toString());
	    params.put("app_version", "test app 1.2"); // application name and version

	    try {
	      JSONObject obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	      return true;
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	      return false;
	    }
	 }
}
