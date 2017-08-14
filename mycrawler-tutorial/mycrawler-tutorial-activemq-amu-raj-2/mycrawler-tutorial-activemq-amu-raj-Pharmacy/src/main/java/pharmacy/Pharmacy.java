package Pharmacy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by AL on 2015-01-28.
 */
public class Pharmacy {
    public String name;

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
    }


}
