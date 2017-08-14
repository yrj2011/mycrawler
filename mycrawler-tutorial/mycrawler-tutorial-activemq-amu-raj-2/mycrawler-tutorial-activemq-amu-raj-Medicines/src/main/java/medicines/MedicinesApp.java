package medicines;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dominik on 2015-01-27.
 */
@ComponentScan
@EnableAutoConfiguration
@RestController
public class MedicinesApp{

    public static void main(String[] args) {
        SpringApplication.run(MedicinesApp.class, args);
    }
}
