package com.mycrawler.common.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class Domain implements Serializable {

	private static final long serialVersionUID = -5604898939968739067L;

	protected String createdUser;

	protected String createdApp;

	protected Date createdDate;

	protected String lastUpdatedUser;

	protected String lastUpdatedApp;

	protected Date lastUpdatedDate;
	
	protected int version;
}
