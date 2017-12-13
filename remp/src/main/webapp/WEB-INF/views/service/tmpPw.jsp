<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.Address"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Session"%>
<%@page import="com.remp.work.model.service.SMTPAuthenticatior"%>
<%@page import="javax.mail.Authenticator"%>
<%@page import="java.util.Properties"%>
    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>임시 비밀번호 발송</title>
</head>
<body>
<% /*이메일 보내는 부분*/
	request.setCharacterEncoding("utf-8");
	  
	String from = (String)request.getAttribute("from");
	String to = (String)request.getAttribute("to");
	String result = (String)request.getAttribute("tmpPw");
	String title = "임시비밀번호입니다.";
	  
	Properties p = new Properties();
	  
	p.put("mail.smtp.host","smtp.daum.net");
	p.put("mail.smtp.port", "465");
	p.put("mail.smtp.starttls.enable", "true");
	p.put("mail.smtp.auth", "true");
	p.put("mail.smtp.debug", "true");
	p.put("mail.smtp.socketFactory.port", "465");
	p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	p.put("mail.smtp.socketFactory.fallback", "false");
	 
    try{
    	Authenticator auth = new SMTPAuthenticatior();
        Session ses = Session.getInstance(p, auth);
        /* ses.setDebug(true); */
        MimeMessage msg = new MimeMessage(ses); // 메일의 내용을 담을 객체 
     
        msg.setSubject(title); //  제목
     
        StringBuffer buffer = new StringBuffer();
        buffer.append("회원님의 임시비밀번호는 <font color='red' style='font-weigth: bold; font-size: 20px;'>" + result +"</font> 입니다.<p>");
        buffer.append("로그인 후 변경하여주세요.");
        Address fromAddr = new InternetAddress(from);
        msg.setFrom(fromAddr); 
     
        Address toAddr = new InternetAddress(to);
        msg.addRecipient(Message.RecipientType.TO, toAddr); // 받는 사람
         
        msg.setContent(buffer.toString(), "text/html;charset=UTF-8"); // 내용
        Transport.send(msg); // 전송  
     
    } catch(Exception e){
        e.printStackTrace();
        return;
    }
%>
	<div align="center"> <br>
		<h3>비밀번호 찾기</h3> <br>
			<form name="backForm" action="goLogin.do" method="post">
				<h6>회원님의 이메일로 임시비밀번호를 전송하였습니다.</h6>
				<h5>로그인 후 변경하여 주세요.</h5>
				<input type="submit" id="submit" name="submit" value="로그인하러가기"  class="btn btn-primary" > <br>
			</form>
	</div>
</body>
</html>