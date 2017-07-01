package com.mycrawler.common.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class IPProxy extends Domain<IPProxy> implements Serializable {
	private static final long serialVersionUID = -152541856943596316L;
	private String id;
	private String ip;
	private Integer port;
	private String type;
	private boolean available;
	
	@Override
	protected Serializable pkVal() {
		return id;
	}
}
