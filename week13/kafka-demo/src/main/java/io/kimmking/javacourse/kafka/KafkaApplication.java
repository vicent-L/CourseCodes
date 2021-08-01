package io.kimmking.javacourse.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author lzg
 * @Date 2021-08-01 21:33
 */

@ComponentScan
@SpringBootApplication(scanBasePackages = "io.kimmking.javacourse.kafka")
public class KafkaApplication {

    public static void main(String[] args) {


        SpringApplication.run(KafkaApplication.class, args);

    }

}
