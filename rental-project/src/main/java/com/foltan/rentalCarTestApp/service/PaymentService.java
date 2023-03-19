package com.foltan.rentalCarTestApp.service;

import com.foltan.rentalCarTestApp.domain.CreditCard;
import com.foltan.rentalCarTestApp.domain.User;
import com.foltan.rentalCarTestApp.dto.CreditCardDto;
import com.foltan.rentalCarTestApp.exception.NoCreditCardException;
import com.foltan.rentalCarTestApp.repository.CreditCardRepository;
import com.foltan.rentalCarTestApp.repository.UserRepository;
import com.foltan.rentalCarTestApp.security.LoggedInUser;
import com.foltan.rentalCarTestApp.mapper.CreditCardDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {

        private final UserRepository userRepository;
        private final CreditCardRepository creditCardRepository;
        private final LoggedInUser loggedInUser;

        public void addCreditCard(CreditCardDto creditCardDto) {

                log.info("Adding credit card to user");
                User user = loggedInUser.getUser();

                if(user.getCreditCard() != null) {

                        throw new IllegalCallerException("You Already Have Credit Card!");
                }
                CreditCard card = creditCardRepository.save(CreditCardDtoMapper.mapToCreditCard(creditCardDto));
                user.setCreditCard(card);
                card.setUser(user);
                userRepository.save(user);
        }

        public void moneyTransfer(Long moneyAmount) {

                User user = loggedInUser.getUser();

                if(user.getCreditCard() == null) {

                        throw new NoCreditCardException("You Do Not Have Credit Card!");

                } else {

                        log.info("Transfer for the amount of {}", moneyAmount);
                        CreditCard creditCard = user.getCreditCard();
                        creditCard.setAccountBalance(creditCard.getAccountBalance() + moneyAmount);
                        userRepository.save(user);

                }
        }

}
