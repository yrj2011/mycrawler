package com.mycrawler.tutuorial.activemq;

import java.io.Serializable;

public interface INotifier {
	public void init();
	public void destroy();
	public void notify(Serializable msg,String type);
}
