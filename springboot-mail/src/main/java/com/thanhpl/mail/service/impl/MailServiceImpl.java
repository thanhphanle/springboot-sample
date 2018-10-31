package com.thanhpl.mail.service.impl;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.thanhpl.mail.model.SimpleMail;
import com.thanhpl.mail.service.MailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Configuration freemarkerConfig;

	@Override
	public void sendSimpleMail(SimpleMail mail) throws Exception {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(mail.getFrom());
			message.setTo(mail.getTo());
			message.setSubject(mail.getSubject());
			message.setText(mail.getContent());
			javaMailSender.send(message);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void sendMail(SimpleMail mail) throws Exception {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			if (!StringUtils.isEmpty(mail.getFrom())) {
				if (!StringUtils.isEmpty(mail.getFromDisplayName())) {
					helper.setFrom(mail.getFrom(), mail.getFromDisplayName());
				} else {
					helper.setFrom(mail.getFrom());
				}
			}
			helper.setTo(mail.getTo().split(","));
			if (!StringUtils.isEmpty(mail.getCc())) {
				helper.setCc(mail.getCc().split(","));
			}
			if (!StringUtils.isEmpty(mail.getBcc())) {
				helper.setBcc(mail.getBcc().split(","));
			}

			helper.setSubject(mail.getSubject());
			helper.setText(mail.getContent(), mail.getIsHtml());
			helper.setText("<html><body><img src=\"cid:banner\" >" + mail.getContent() + "</body></html>", true);

			ClassLoader classLoader = getClass().getClassLoader();
			FileSystemResource file = new FileSystemResource(
					new File(classLoader.getResource("media/banner.jpg").getFile()));
			helper.addInline("banner", file);

			FileSystemResource fileSystemResource = new FileSystemResource(
					new File(classLoader.getResource("media/sale.txt").getFile()));
			helper.addAttachment("sale.txt", fileSystemResource);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			throw e;
		}
	}

	// https://www.baeldung.com/spring-email
	// https://www.baeldung.com/apache-velocity
	@Override
	public void sendMailInVelocity(SimpleMail mail, String templateName) throws Exception {
		try {
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			velocityEngine.init();

			org.apache.velocity.Template template = velocityEngine.getTemplate("templates/mail.vm");

			VelocityContext context = new VelocityContext();
			context.put("title", mail.getSubject());
			context.put("body", mail.getContent());

			StringWriter writer = new StringWriter();
			template.merge(context, writer);

			String content = writer.getBuffer().toString();
			System.out.println(content);

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
			if (!StringUtils.isEmpty(mail.getFrom())) {
				if (!StringUtils.isEmpty(mail.getFromDisplayName())) {
					helper.setFrom(mail.getFrom(), mail.getFromDisplayName());
				} else {
					helper.setFrom(mail.getFrom());
				}
			}
			helper.setTo(mail.getTo().split(","));
			if (!StringUtils.isEmpty(mail.getCc())) {
				helper.setCc(mail.getCc().split(","));
			}
			if (!StringUtils.isEmpty(mail.getBcc())) {
				helper.setBcc(mail.getBcc().split(","));
			}

			helper.setSubject(mail.getSubject());
			helper.setText(content, true);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void sendMailInFreemarker(SimpleMail mail, String templateName) throws Exception {
		try {
			Template template = freemarkerConfig.getTemplate(templateName);
			Map<String, String> model = new HashMap<>();
			model.put("title", mail.getSubject());
			model.put("body", mail.getContent());

			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			System.out.println(content);

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
			if (!StringUtils.isEmpty(mail.getFrom())) {
				if (!StringUtils.isEmpty(mail.getFromDisplayName())) {
					helper.setFrom(mail.getFrom(), mail.getFromDisplayName());
				} else {
					helper.setFrom(mail.getFrom());
				}
			}
			helper.setTo(mail.getTo().split(","));
			if (!StringUtils.isEmpty(mail.getCc())) {
				helper.setCc(mail.getCc().split(","));
			}
			if (!StringUtils.isEmpty(mail.getBcc())) {
				helper.setBcc(mail.getBcc().split(","));
			}

			helper.setSubject(mail.getSubject());
			helper.setText(content, true);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			throw e;
		}
	}

}
