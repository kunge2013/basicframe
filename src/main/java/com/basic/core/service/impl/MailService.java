package com.basic.core.service.impl;

import com.basic.core.service.IMailService;
import com.basic.core.thrid.mail.MailContentTypeEnum;
import com.basic.core.thrid.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService implements IMailService {
    @Autowired
    MailSender mailSender;
    @Override
    public void postMail(String title, String content, MailContentTypeEnum contentType, List<String> targets) throws Exception{
        mailSender.title(title)
                .content(content)
                .contentType(contentType)
                .targets(targets)
                .send();
    }

}
