package com.example.usermanagementservice.controllers.user;

import com.example.usermanagementservice.services.user.UserInformation;
import com.example.usermanagementservice.services.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/getMyInfo")
    public UserInformation getMyInfo() {
        return userService.getMyInfo();
    }
}
