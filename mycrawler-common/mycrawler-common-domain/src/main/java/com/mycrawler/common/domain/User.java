package com.mycrawler.common.domain;

import java.io.Serializable;
import java.util.UUID;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@TableName("`mycrawler`.`user`")
public class User extends Domain<User> implements Serializable{
	
	private static final long serialVersionUID = -2231355946934861916L;

	/** 用户ID */
    private String id;

    /** 用户名 */
    private String name;

    /** 用户年龄 */
    private Integer age;

    @TableField(exist = false)
    private String state;

	@Override
	protected Serializable pkVal() {
		return UUID.randomUUID().toString();
	}
}
