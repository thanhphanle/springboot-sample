package com.thanhpl.mail.controller;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thanhpl.mail.request.SendMailRequest;
import com.thanhpl.mail.response.SendMailResponse;
import com.thanhpl.mail.response.SendMailResponseData;
import com.thanhpl.mail.utility.JsonUtil;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({ "/v1/mail" })
//https://www.baeldung.com/spring-email
//https://www.baeldung.com/apache-velocity
public class MailController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Configuration freemarkerConfig;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public SendMailResponse send(@RequestBody SendMailRequest request) {
		log.info(JsonUtil.toJson(request));
		SendMailResponse response = new SendMailResponse();
		SendMailResponseData data = new SendMailResponseData();

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(request.getFrom());
			message.setTo(request.getTo());
			message.setSubject(request.getSubject());
			message.setText(request.getContent());
			javaMailSender.send(message);

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
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(request.getFrom());
			mimeMessageHelper.setTo(request.getTo());
			mimeMessageHelper.setSubject(request.getSubject());
			mimeMessageHelper.setText("<html><body><img src=\"cid:banner\" >" + request.getContent() + "</body></html>",
					true);

			ClassLoader classLoader = getClass().getClassLoader();
			FileSystemResource file = new FileSystemResource(
					new File(classLoader.getResource("media/banner.jpg").getFile()));
			mimeMessageHelper.addInline("banner", file);

			FileSystemResource fileSystemResource = new FileSystemResource(
					new File(classLoader.getResource("media/sale.txt").getFile()));
			mimeMessageHelper.addAttachment("sale.txt", fileSystemResource);

			javaMailSender.send(mimeMessage);

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
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			velocityEngine.init();

			Template template = velocityEngine.getTemplate("templates/mail.vm");

			VelocityContext context = new VelocityContext();
			context.put("title", request.getSubject());
			context.put("body", request.getContent());

			StringWriter writer = new StringWriter();
			template.merge(context, writer);

			String text = writer.getBuffer().toString();

			System.out.println(text);

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(request.getFrom());
			mimeMessageHelper.setTo(request.getTo());
			mimeMessageHelper.setSubject(request.getSubject());
			mimeMessageHelper.setText(text, true);

			javaMailSender.send(mimeMessage);

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
			//freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
			freemarker.template.Template template = freemarkerConfig.getTemplate("mail.ftl");

			Map<String, String> model = new HashMap<>();
			model.put("title", request.getSubject());
			model.put("body", request.getContent());

			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			System.out.println(text);

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(request.getFrom());
			mimeMessageHelper.setTo(request.getTo());
			mimeMessageHelper.setSubject(request.getSubject());
			mimeMessageHelper.setText(text, true);

			javaMailSender.send(mimeMessage);

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
