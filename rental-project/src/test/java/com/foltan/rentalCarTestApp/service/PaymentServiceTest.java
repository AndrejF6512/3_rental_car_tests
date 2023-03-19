package com.foltan.rentalCarTestApp.service;

import com.foltan.rentalCarTestApp.domain.CreditCard;
import com.foltan.rentalCarTestApp.domain.User;
import com.foltan.rentalCarTestApp.dto.CreditCardDto;
import com.foltan.rentalCarTestApp.exception.NoCreditCardException;
import com.foltan.rentalCarTestApp.repository.CreditCardRepository;
import com.foltan.rentalCarTestApp.repository.UserRepository;
import com.foltan.rentalCarTestApp.security.LoggedInUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

        @Mock
        UserRepository userRepository;

        @Mock
        CreditCardRepository creditCardRepository;

        @Mock
        LoggedInUser loggedInUser;

        @InjectMocks
        PaymentService paymentService;

        @Test
        void itShouldAddCreditCardToUser() {
                User user = User.builder()
                        .firstName("Mickey")
                        .lastName("Mouse")
                        .build();

                CreditCardDto creditCardDto = CreditCardDto.builder()
                        .cardNumber(8888943300781111L)
                        .build();

                CreditCard creditCard = CreditCard.builder()
                        .cardNumber(8888943300781111L)
                        .accountBalance(0L)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);
                when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
                when(userRepository.save(user)).thenReturn(user);


                paymentService.addCreditCard(creditCardDto);

                assertThat(user.getCreditCard()).isEqualTo(creditCard);
        }

        @Test
        void itShouldMakeMoneyTransfer() {
                CreditCard creditCard = CreditCard.builder()
                        .accountBalance(0L)
                        .build();

                User user = User.builder()
                        .firstName("Richard")
                        .lastName("Maly")
                        .creditCard(creditCard)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);
                when(userRepository.save(user)).thenReturn(user);


                paymentService.moneyTransfer(700L);

                assertThat(user.getCreditCard().getAccountBalance()).isEqualTo(700L);
        }

        @Test
        void itShouldThrowNoCreditCardException() {
                User user = User.builder()
                        .username("Michal765")
                        .creditCard(null)
                        .build();

                when(loggedInUser.getUser()).thenReturn(user);

                assertThrows(NoCreditCardException.class, () -> paymentService.moneyTransfer(400L));
        }

}
