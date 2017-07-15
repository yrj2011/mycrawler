package com.mycrawler.tutorial.springboot;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements CommandLineRunner , DisposableBean{
/*
	    @Value("${name}")
	    private String name;*/
	 
	    @Autowired
	    public MyBean(ApplicationArguments args) {
	        boolean debug = args.containsOption("debug");
	        List<String> files = args.getNonOptionArgs();
	        System.out.println(debug);
	        // if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
	    }

		public void run(String... arg0) throws Exception {
			System.out.println("run some code!");
			
		}

		public void destroy() throws Exception {
			System.out.println("spring boot destory!");
			
		}

}
