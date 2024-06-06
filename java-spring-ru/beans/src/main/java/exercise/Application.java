package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// BEGIN

// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    LocalDateTime ldt = LocalDateTime.now();

    @Bean
    public Daytime daytime() {
        var time = LocalDateTime.now().getHour();
        if (time >= 6 && time < 22) {
            return new Day();
        }
        return new Night();
    }

    // END
}
