package com.shu.votetool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.shu.votetool.dao")
public class QuickVote
{
    public static void main( String[] args )
    {
        SpringApplication.run(QuickVote.class, args);
//        System.out.println( "Hello World!" );
    }
}
