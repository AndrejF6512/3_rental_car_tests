package com.foltan.rentalCarTestApp.repository;

import com.foltan.rentalCarTestApp.domain.User;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

        @Autowired
        private UserRepository userRepository;

        @BeforeEach
        void setUp() {
                User user = new User(null, "Andrej", "Foltán", "Foltan123", "foltan", "foltana@gmail.com", 888777621, null, null, new ArrayList<>());
                User user2 = new User(null, "Peter", "Pevný", "Pevny456", "pevny", "pevnyp@gmail.com", 465999222, null, null, new ArrayList<>());
                User user3 = new User(null, "Karol", "Malý", "Maly567", "maly", "malyk@gmail.com", 765555444, null, null, new ArrayList<>());
                User user4 = new User(null, "Petra", "Veselá", "Petra963", "vesela", "veselap@gmail.com", 908764220, null, null, new ArrayList<>());
                userRepository.save(user);
                userRepository.save(user2);
                userRepository.save(user3);
                userRepository.save(user4);
        }

        @AfterEach
        void tearDown() {
                userRepository.deleteAll();
        }

        @Test
        void itShouldReturnTrueIfUsernameExists() {
                AssertionsForClassTypes.assertThat(userRepository.findByUsername("Foltan123")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(userRepository.findByUsername("Gregor56")).isInstanceOf(Optional.class).isEmpty();
                AssertionsForClassTypes.assertThat(userRepository.findByUsername("Maly567")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(userRepository.findByUsername("Pevny456")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(userRepository.findByUsername("Jozef777")).isInstanceOf(Optional.class).isEmpty();
                AssertionsForClassTypes.assertThat(userRepository.findByUsername("Petra963")).isInstanceOf(Optional.class).isPresent();

        }

}
