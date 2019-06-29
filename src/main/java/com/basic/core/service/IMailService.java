package com.basic.core.service;

import com.basic.core.thrid.mail.MailContentTypeEnum;

import java.util.List;

public interface IMailService {
    /**
     * 发送邮件
     * @param title 标题
     * @param content 内容
     * @param contentType 格式
     * @param targets 接收人邮箱
     */
    void postMail(String title, String content, MailContentTypeEnum contentType, List<String> targets) throws Exception;
}
