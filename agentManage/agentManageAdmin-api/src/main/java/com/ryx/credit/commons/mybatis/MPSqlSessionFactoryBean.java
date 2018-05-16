package com.ryx.credit.commons.mybatis;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

/**
 * mybatis-plus 2.0.5 全局配置
 * 
 * http://git.oschina.net/baomidou/mybatis-plus/issues/227
 * 
 * @author L.cm
 *
 */
public class MPSqlSessionFactoryBean extends MybatisSqlSessionFactoryBean {
	private IdType idType = IdType.ID_WORKER;

	public void setIdType(int idType) {
		this.idType = IdType.getIdType(idType);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		GlobalConfiguration globalConfig = GlobalConfiguration.defaults();
		globalConfig.setIdType(idType.getKey());
		super.setGlobalConfig(globalConfig);
		super.afterPropertiesSet();
	}
	
}
