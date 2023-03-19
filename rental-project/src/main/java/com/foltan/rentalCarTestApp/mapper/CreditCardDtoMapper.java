package com.foltan.rentalCarTestApp.mapper;

import com.foltan.rentalCarTestApp.domain.CreditCard;
import com.foltan.rentalCarTestApp.dto.CreditCardDto;
import org.springframework.stereotype.Service;

@Service
public class CreditCardDtoMapper {

    public static CreditCard mapToCreditCard(CreditCardDto creditCardDto) {

        return CreditCard.builder()
                .cardNumber(creditCardDto.getCardNumber())
                .month(creditCardDto.getMonth())
                .year(creditCardDto.getYear())
                .CVV(creditCardDto.getCVV())
                .accountBalance(0L)
                .build();
    }

}
