package com.foltan.rentalCarTestApp.service;

import com.foltan.rentalCarTestApp.domain.CreditCard;
import com.foltan.rentalCarTestApp.domain.Role;
import com.foltan.rentalCarTestApp.domain.User;
import com.foltan.rentalCarTestApp.dto.CreditCardDto;
import com.foltan.rentalCarTestApp.dto.UserInDto;
import com.foltan.rentalCarTestApp.exception.AssignedRoleException;
import com.foltan.rentalCarTestApp.exception.ExistingEntityException;
import com.foltan.rentalCarTestApp.mapper.UserDtoMapper;
import com.foltan.rentalCarTestApp.repository.CreditCardRepository;
import com.foltan.rentalCarTestApp.repository.RoleRepository;
import com.foltan.rentalCarTestApp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @Mock
        UserRepository userRepository;

        @Mock
        RoleRepository roleRepository;

        @Mock
        CreditCardRepository creditCardRepository;

        @Mock
        PasswordEncoder passwordEncoder;

        @InjectMocks
        UserService userService;


        @Test
        void itShouldSaveUser() {
                UserInDto userInDto = UserInDto.builder()
                        .firstName("Sebastian")
                        .lastName("Hlavny")
                        .username("sebix")
                        .password("123465")
                        .email("apple@gamil.com")
                        .phone(0907111222)
                        .build();

                User user = User.builder()
                        .firstName("Sebastian")
                        .lastName("Hlavny")
                        .username("sebix")
                        .email("apple@gamil.com")
                        .phone(0907111222)
                        .roles(new ArrayList<>())
                        .build();


                when(userRepository.findByUsername("sebix")).thenReturn(Optional.empty());
                when(userRepository.save(user)).thenReturn(user);


                userService.saveUser(userInDto);

                assertThat(user.getFirstName()).isEqualTo(userInDto.getFirstName());
                assertThat(user.getEmail()).isEqualTo(userInDto.getEmail());
        }

        @Test
        void itShouldCheckIfUserIsEdited() {
                Long id = 2L;

                UserInDto userInDto = UserInDto.builder()
                        .username("Peter")
                        .build();

                User user = User.builder()
                        .id(2L)
                        .build();


                when(userRepository.findById(id)).thenReturn(Optional.of(user));
                when(userRepository.save(user)).thenReturn(user);


                Assertions.assertThat(userService.editUser(id, userInDto)).isEqualTo(user);
        }

        @Test
        void itShouldDeleteUser() {
                User user = User.builder()
                        .id(6L)
                        .build();


                when(userRepository.existsById(6L)).thenReturn(true);
                doNothing().when(userRepository).deleteById(6L);


                userService.deleteUser(6L);

                verify(userRepository, times(1)).deleteById(6L);
        }

        @Test
        void itShouldSaveRole() {
                Role role = Role.builder()
                        .name("ROLE_MANAGER")
                        .build();


                when(roleRepository.findByName("ROLE_MANAGER")).thenReturn(Optional.empty());
                when(roleRepository.save(role)).thenReturn(role);


                Assertions.assertThat(userService.saveRole(role)).isEqualTo(role);
        }

        @Test
        void itShouldAddRoleToUser() {
                User user = User.builder()
                        .firstName("Pavol")
                        .lastName("Mohol")
                        .username("pablo")
                        .roles(new ArrayList<>())
                        .build();

                Role role = Role.builder()
                        .name("ROLE_ADMIN")
                        .users(new ArrayList<>())
                        .build();


                when(userRepository.findByUsername("pablo")).thenReturn(Optional.of(user));
                when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
                when(userRepository.save(user)).thenReturn(user);


                userService.addRoleToUser("pablo", "ROLE_ADMIN");

                assertThat(user.getRoles()).hasSize(1);
        }

        @Test
        void itShouldDeleteUserRole() {
                User user = User.builder()
                        .firstName("Kamil")
                        .lastName("Krasny")
                        .username("Kamilek")
                        .roles(new ArrayList<>())
                        .build();

                Role role = Role.builder()
                        .name("ROLE_USER")
                        .users(new ArrayList<>())
                        .build();


                when(userRepository.findByUsername("Kamilek")).thenReturn(Optional.of(user));
                when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
                when(userRepository.save(user)).thenReturn(user);


                userService.addRoleToUser("Kamilek", "ROLE_USER");

                userService.deleteUserRole("Kamilek", "ROLE_USER");

                assertThat(user.getRoles()).hasSize(0);
        }

        @Test
        void itShouldAddCreditCardToUser() {
                User user = User.builder()
                        .firstName("Igor")
                        .lastName("Hniezdo")
                        .username("niceUser45")
                        .build();

                CreditCardDto creditCardDto = CreditCardDto.builder()
                        .cardNumber(7756443322118596L)
                        .build();

                CreditCard creditCard = CreditCard.builder()
                        .cardNumber(7756443322118596L)
                        .accountBalance(0L)
                        .build();


                when(userRepository.findByUsername("niceUser123")).thenReturn(Optional.of(user));
                when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
                when(userRepository.save(user)).thenReturn(user);


                userService.addCreditCardToUser("niceUser123", creditCardDto);
                assertThat(user.getCreditCard().getCardNumber()).isEqualTo(creditCardDto.getCardNumber());
        }

        @Test
        void itShouldDeleteUserCreditCard() {
                User user = User.builder()
                        .firstName("John")
                        .lastName("Orsula")
                        .username("john89")
                        .build();

                CreditCardDto creditCardDto = CreditCardDto.builder()
                        .cardNumber(9999111122223333L)
                        .build();

                CreditCard creditCard = CreditCard.builder()
                        .cardNumber(9999111122223333L)
                        .accountBalance(0L)
                        .build();


                when(userRepository.findByUsername("Shell123")).thenReturn(Optional.of(user));
                when(creditCardRepository.save(creditCard)).thenReturn(creditCard);


                userService.addCreditCardToUser("Shell123", creditCardDto);
                userService.deleteUserCreditCard("Shell123");
                verify(creditCardRepository, times(1)).delete(user.getCreditCard());

        }

        @Test
        void itShouldThrowExistingUserException() {
                UserInDto userInDto = UserInDto.builder()
                        .username("Filip")
                        .build();

                User user = User.builder()
                        .username("Filip")
                        .build();


                when(userRepository.findByUsername("Filip")).thenReturn(Optional.of(user));


                assertThrows(ExistingEntityException.class, () -> userService.saveUser(userInDto));
        }

        @Test
        void itShouldThrowExistingRoleException() {
                Role role = Role.builder()
                        .name("ROLE_VISITOR")
                        .build();

                Role role2 = Role.builder()
                        .name("ROLE_VISITOR")
                        .build();


                when(roleRepository.findByName("ROLE_VISITOR")).thenReturn(Optional.of(role2));


                assertThrows(ExistingEntityException.class, () -> userService.saveRole(role));
        }

        @Test
        void itShouldThrowAssignedRoleException() {
                Role role = Role.builder()
                        .name("ROLE_USER")
                        .build();

                User user = User.builder()
                        .username("Zdenko")
                        .roles(Arrays.asList(role))
                        .build();


                when(userRepository.findByUsername("Zdenko")).thenReturn(Optional.of(user));
                when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));


                assertThrows(AssignedRoleException.class, () -> userService.addRoleToUser("Zdenko", "ROLE_USER"));
        }

        @Test
        void itShouldReturnAllUsers() {
                User user1 = User.builder()
                        .firstName("Adrian")
                        .lastName("Dolny")
                        .username("adri")
                        .password("fsfuhsfdgsf4")
                        .email("Adri7@gmail.com")
                        .phone(0948456235)
                        .build();

                User user2 = User.builder()
                        .firstName("Kamil")
                        .lastName("Bledy")
                        .username("kam123")
                        .password("g8dsh4")
                        .email("Bledy@gmail.com")
                        .phone(945769043)
                        .build();

                User user3 = User.builder()
                        .firstName("Gregor")
                        .lastName("Vysoky")
                        .username("gregl")
                        .password("gtdft47HHs8hu")
                        .email("Vysoky@gmail.com")
                        .phone(0908126585)
                        .build();

                List<User> users = new ArrayList<>();
                users.add(user1);
                users.add(user2);
                users.add(user3);


                when(userRepository.findAll()).thenReturn(users);


                assertThat(userService.getAllUsers()).isEqualTo(UserDtoMapper.mapUserToUserDto(users));
        }

}
