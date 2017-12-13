package com.remp.work.model.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 메일전송을 위한 Java
 * @author 이동훈
 * @since ReMP v 1.0
 *
 */
public class SMTPAuthenticatior extends Authenticator{
	@Override
    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("leedh93","dlatlxptmxm93!");
    }
}