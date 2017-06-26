package com.mycrawler.collector.zhihu.downloadIP;

public class Main {
	private static String url = "http://www.goubanjia.com/free/index%d.shtml";
	public Main() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		for (int i=1;i<=100;i++) {
			System.out.println(String.format(url, i));
		}
	}
}
