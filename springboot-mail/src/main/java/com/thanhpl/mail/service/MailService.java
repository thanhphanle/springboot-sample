package com.thanhpl.mail.service;

import com.thanhpl.mail.model.SimpleMail;

public interface MailService {
	void sendSimpleMail(SimpleMail mail) throws Exception;
	
	void sendMail(SimpleMail mail) throws Exception;
	
	void sendMailInVelocity(SimpleMail mail, String templateName) throws Exception;
	
	void sendMailInFreemarker(SimpleMail mail, String templateName) throws Exception;
}
