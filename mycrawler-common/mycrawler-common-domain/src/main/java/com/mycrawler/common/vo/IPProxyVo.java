package com.mycrawler.common.vo;

import java.io.Serializable;

import com.mycrawler.common.domain.IPProxy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class IPProxyVo extends IPProxy implements Serializable{
	private static final long serialVersionUID = -152541856943596316L;
}
