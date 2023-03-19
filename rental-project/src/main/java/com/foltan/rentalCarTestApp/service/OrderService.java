package com.foltan.rentalCarTestApp.service;

import com.foltan.rentalCarTestApp.domain.AccessKey;
import com.foltan.rentalCarTestApp.domain.CarPackage;
import com.foltan.rentalCarTestApp.domain.PlacedOrder;
import com.foltan.rentalCarTestApp.domain.User;
import com.foltan.rentalCarTestApp.dto.AccessKeyDto;
import com.foltan.rentalCarTestApp.exception.ExistingOrderException;
import com.foltan.rentalCarTestApp.repository.CarPackageRepository;
import com.foltan.rentalCarTestApp.repository.OrderRepository;
import com.foltan.rentalCarTestApp.security.LoggedInUser;
import com.foltan.rentalCarTestApp.exception.InsufficientFundsException;
import com.foltan.rentalCarTestApp.exception.NoCreditCardException;
import com.foltan.rentalCarTestApp.repository.AccessKeyRepository;
import com.foltan.rentalCarTestApp.mapper.AccessKeyDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

        private final Long ID = null;
        private final CarPackageRepository carPackageRepository;
        private final OrderRepository orderRepository;
        private final AccessKeyRepository accessKeyRepository;
        private final LoggedInUser loggedInUser;

        public List<PlacedOrder> getOrders() {
                log.info("Fetching all orders");
                return orderRepository.findAll();
        }

        public AccessKeyDto submitOrder(String carPackage, Integer hours) {

                User user = loggedInUser.getUser();

                if(user.getCreditCard() == null) {

                        throw new NoCreditCardException("You Do Not Have Credit Card!");
                }
                if(user.getAccessKey() != null) {

                        throw new ExistingOrderException("You Have Already Placed An Order!");
                }
                Long money = user.getCreditCard().getAccountBalance();
                CarPackage carPackageSearch = carPackageRepository.findByPackageName(carPackage)
                        .orElseThrow(() -> new EntityNotFoundException("This Package Does Not Exists!"));
                Integer price = carPackageSearch.getPricePerHour();

                AccessKey accessKey;

                if (money < (long) price * hours) {

                        throw new InsufficientFundsException("You Do Not Have Enough Money!");
                } else {

                        user.getCreditCard().setAccountBalance(money - (long) price * hours);
                        accessKey = new AccessKey(ID, carPackage, hours, null);
                        accessKeyRepository.save(accessKey);
                        user.setAccessKey(accessKey);
                        accessKey.setUser(user);

                        log.info("You managed to rent a car!");

                }
                AccessKeyDto accessKeyDto = AccessKeyDtoMapper.mapToAccessKeyDto(accessKey);
                return accessKeyDto;
        }

}
