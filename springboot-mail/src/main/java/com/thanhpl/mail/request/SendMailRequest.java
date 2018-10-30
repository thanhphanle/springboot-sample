package com.thanhpl.mail.request;

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
public class SendMailRequest {
	private String from;
	private String to;
	private String subject;
	private String content;
}
