package com.ryx.credit.pojo.admin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ryx.credit.commons.utils.JsonUtils;

import java.io.Serializable;

/**
 *
 * 角色资源
 *
 */
@TableName("c_role_resource")
public class CRoleResource implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 角色id */
	@TableField(value = "role_id")
	private Long roleId;

	/** 资源id */
	@TableField(value = "resource_id")
	private Long resourceId;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
