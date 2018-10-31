package com.thanhpl.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thanhpl.mail.model.SimpleMail;
import com.thanhpl.mail.request.SendMailRequest;
import com.thanhpl.mail.response.SendMailResponse;
import com.thanhpl.mail.response.SendMailResponseData;
import com.thanhpl.mail.service.MailService;
import com.thanhpl.mail.utility.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({ "/v1/mail" })
public class MailController {

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public SendMailResponse send(@RequestBody SendMailRequest request) {
		log.info(JsonUtil.toJson(request));
		SendMailResponse response = new SendMailResponse();
		SendMailResponseData data = new SendMailResponseData();

		try {
			SimpleMail mail = new SimpleMail();
			mail.setFrom(request.getFrom());
			mail.setTo(request.getTo());
			mail.setSubject(request.getSubject());
			mail.setContent(request.getContent());
			
			mailService.sendSimpleMail(mail);

			data.setStatus("Sent");
			response.setData(data);
			response.setCode("0");
			response.setSuccess(true);
			response.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("Server error: " + e.getMessage());
			response.setCode("900");
		}

		log.info(JsonUtil.toJson(response));
		return response;
	}

	@RequestMapping(value = "/send-mime", method = RequestMethod.POST)
	public SendMailResponse sendMime(@RequestBody SendMailRequest request) {
		log.info(JsonUtil.toJson(request));
		SendMailResponse response = new SendMailResponse();
		SendMailResponseData data = new SendMailResponseData();

		try {
			SimpleMail mail = new SimpleMail();
			mail.setFrom(request.getFrom());
			mail.setTo(request.getTo());
			mail.setSubject(request.getSubject());
			mail.setContent(request.getContent());
			mail.setIsHtml(true);
			
			mailService.sendMail(mail);

			data.setStatus("Sent");
			response.setData(data);
			response.setCode("0");
			response.setSuccess(true);
			response.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("Server error: " + e.getMessage());
			response.setCode("900");
		}

		log.info(JsonUtil.toJson(response));
		return response;
	}

	@RequestMapping(value = "/send-velocity", method = RequestMethod.POST)
	public SendMailResponse sendWithVelocity(@RequestBody SendMailRequest request) {
		log.info(JsonUtil.toJson(request));
		SendMailResponse response = new SendMailResponse();
		SendMailResponseData data = new SendMailResponseData();

		try {
			SimpleMail mail = new SimpleMail();
			mail.setFrom(request.getFrom());
			mail.setTo(request.getTo());
			mail.setSubject(request.getSubject());
			mail.setContent(request.getContent());
			mail.setIsHtml(true);
			
			mailService.sendMailInVelocity(mail, "templates/mail.vm");

			data.setStatus("Sent");
			response.setData(data);
			response.setCode("0");
			response.setSuccess(true);
			response.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("Server error: " + e.getMessage());
			response.setCode("900");
		}

		log.info(JsonUtil.toJson(response));
		return response;
	}

	@RequestMapping(value = "/send-freemaker", method = RequestMethod.POST)
	public SendMailResponse sendWithFreeMaker(@RequestBody SendMailRequest request) {
		log.info(JsonUtil.toJson(request));
		SendMailResponse response = new SendMailResponse();
		SendMailResponseData data = new SendMailResponseData();

		try {
			SimpleMail mail = new SimpleMail();
			mail.setFrom(request.getFrom());
			mail.setTo(request.getTo());
			mail.setSubject(request.getSubject());
			mail.setContent(request.getContent());
			mail.setIsHtml(true);
			
			mailService.sendMailInFreemarker(mail, "mail.ftl");

			data.setStatus("Sent");
			response.setData(data);
			response.setCode("0");
			response.setSuccess(true);
			response.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("Server error: " + e.getMessage());
			response.setCode("900");
		}

		log.info(JsonUtil.toJson(response));
		return response;
	}
}
