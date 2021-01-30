package com.lucutovidiu.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.lucutovidiu"})
@ComponentScan(basePackages = {"com.lucutovidiu"})
@EnableMongoRepositories(basePackages = {"com.lucutovidiu"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//        User user = new User();
//        user.setUserName("");
//        user.setUserEmail("");
//        user.setPassword(");
//        user.setRoles(Arrays.asList(UserRole.ADMIN, UserRole.USER, UserRole.BUSINESS_ADMIN));
//        user.setGender("male");
//        user.setIsAccountNonExpired(true);
//        user.setIsAccountNonLocked(true);
//        user.setIsCredentialsNonExpired(true);
//        user.setIsEnabled(true);
//        userRepository.save(user);
//    }

}
