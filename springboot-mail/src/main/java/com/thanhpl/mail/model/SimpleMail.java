package com.thanhpl.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMail {
	private String from;
	private String fromDisplayName;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String content;
	private Boolean isHtml;
}
