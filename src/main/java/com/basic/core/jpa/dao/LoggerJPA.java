package com.basic.core.jpa.dao;

import com.basic.core.jpa.bean.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请求日志数据接口
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/4/10
 * Time：21:06
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Transactional
public interface LoggerJPA
        extends JpaRepository<LoggerEntity,Long>
{

}
