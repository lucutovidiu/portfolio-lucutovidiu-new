package com.lucutovidiu.startup;

import com.lucutovidiu.mongo.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StartupRunner implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        System.out.println("StartupRunner...");
//        System.out.println("StartupRunner...");
//        userService.getAllUsers().stream().findFirst()
//                .ifPresent(user -> {
////                    userService.changePassword(user.getUserEmail(), "MyNewPassword$$$Ovi123");
//
////                    userService.
//                });
//        System.out.println("StartupRunner...");
//        System.out.println("StartupRunner...");
    }
}
