package com.github.yrj.activemq.tourial;

import java.io.Serializable;

public interface INotifier {
	public void init();
	public void destroy();
	public void notify(Serializable msg,String type);
}
