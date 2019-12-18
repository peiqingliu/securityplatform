package com.security.platform.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/5 18:32
 * @description 公共实体
 */
// 自定义接口 不会创建接口的实例 必须加此注解
@NoRepositoryBean
public interface BaseDao<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
}
