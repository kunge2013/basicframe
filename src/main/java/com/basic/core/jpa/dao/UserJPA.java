package com.basic.core.jpa.dao;

import com.basic.core.jpa.bean.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/4/4
 * Time：15:43
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Transactional
public interface UserJPA extends
        JpaRepository<UserEntity, Long>,
        JpaSpecificationExecutor<UserEntity>,
        Serializable{
    public UserEntity findByUsername(String username);
}
