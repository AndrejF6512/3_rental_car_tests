package com.foltan.rentalCarTestApp.service;

import com.foltan.rentalCarTestApp.domain.User;
import com.foltan.rentalCarTestApp.dto.UserInDto;
import com.foltan.rentalCarTestApp.mapper.UserInDtoMapper;
import com.foltan.rentalCarTestApp.exception.ExistingEntityException;
import com.foltan.rentalCarTestApp.exception.WeakPasswordException;
import com.foltan.rentalCarTestApp.repository.UserRepository;
import com.foltan.rentalCarTestApp.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RegistrationService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final UserService userService;

        public void registerUser(UserInDto userInDto) {

                if (userRepository.findByUsername(userInDto.getUsername()).isPresent()) {

                        throw new ExistingEntityException("User With Given Username Already Exists!");

                } else if (!PasswordValidator.matcher(userInDto.getPassword()).matches()) {

                        throw new WeakPasswordException("Password Must Contains Minimum Eight Characters," +
                                " At Least One Uppercase Letter, One Lowercase Letter And One Number!");

                } else {

                        log.info("Registration of new user");
                        User user = UserInDtoMapper.mapToUser(userInDto);
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                        userRepository.save(user);
                        userService.addRoleToUser(user.getUsername(), "ROLE_USER");

                }
        }

}
