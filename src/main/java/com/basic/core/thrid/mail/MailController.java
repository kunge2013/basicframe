package com.basic.core.thrid.mail;

import com.basic.core.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class MailController {
	@Autowired
	IMailService mailService;
	@RequestMapping(value = "/mail/postMail", method = RequestMethod.GET)
	public void postMail() throws Exception {
		mailService.postMail("测试SpringBoot发送邮件","简单文本内容发送", MailContentTypeEnum.TEXT, Arrays.asList("543976974@qq.com"));
	}

}
