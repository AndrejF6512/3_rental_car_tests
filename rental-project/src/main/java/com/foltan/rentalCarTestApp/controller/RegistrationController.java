package com.foltan.rentalCarTestApp.controller;

import com.foltan.rentalCarTestApp.dto.UserInDto;
import com.foltan.rentalCarTestApp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

        private final RegistrationService registrationService;

        @PostMapping("/registration")
        public void registerUser(@RequestBody UserInDto userInDto) {
                registrationService.registerUser(userInDto);
        }

}
