package com.blueware.agent.aop;

import java.util.Date;

public class Test {
    
public static void main(String[] args) {
        
        SayHello sayHello = new SayHello();
        
        AddLogic logic = new AddLogicImpl(sayHello);
        
        GenSubProxy genSubProxy = new GenSubProxy(logic);
        
        Object obj = genSubProxy.genSubProxy(SayHello.class);
        
        SayHello sh = (SayHello) obj;
        
        sh.hh((byte)1, new byte[]{}, 1, 1f, 's',1, 1, new int[][]{{12}}, "", new String[][]{{"sdf","s"}}, new Date());
        
        sh.sayHello("sg", "srt", 234, new String[]{});
        
    }

}