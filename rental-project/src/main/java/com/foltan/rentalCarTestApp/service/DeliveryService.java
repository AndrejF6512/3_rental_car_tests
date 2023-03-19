package com.foltan.rentalCarTestApp.service;

import com.foltan.rentalCarTestApp.domain.Car;
import com.foltan.rentalCarTestApp.domain.PlacedOrder;
import com.foltan.rentalCarTestApp.domain.User;
import com.foltan.rentalCarTestApp.exception.UnavailableCarException;
import com.foltan.rentalCarTestApp.repository.CarRepository;
import com.foltan.rentalCarTestApp.repository.OrderRepository;
import com.foltan.rentalCarTestApp.security.LoggedInUser;
import com.foltan.rentalCarTestApp.exception.InvalidPackageException;
import com.foltan.rentalCarTestApp.exception.NoAccessKeyException;
import com.foltan.rentalCarTestApp.repository.AccessKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryService {

        public static final Long ID = null;
        private final CarRepository carRepository;
        private final OrderRepository orderRepository;
        private final AccessKeyRepository accessKeyRepository;
        private final LoggedInUser loggedInUser;

        public Car pickUpTheCar(Long carId) {

                Car car = carRepository.findById(carId)
                        .orElseThrow(() -> new EntityNotFoundException("Car With This ID Does Not Exists!"));
                User user = loggedInUser.getUser();
                if(user.getAccessKey() == null) {

                        throw new NoAccessKeyException("You Do Not Have An Access Key!");
                }
                else if(!user.getAccessKey().getCarPackage().equals(car.getCarPackage().getPackageName())) {

                        throw new InvalidPackageException("You Cannot Pick Car From This Package!");
                }
                else if(!car.getIsAvailable()) {

                        throw new UnavailableCarException("This Car Is Not Available!");
                } else {

                        accessKeyRepository.delete(user.getAccessKey());
                        car.setIsAvailable(false);
                        LocalDateTime start = LocalDateTime.now();
                        LocalDateTime end = LocalDateTime.now().plusHours(user.getAccessKey().getHours());
                        PlacedOrder order = new PlacedOrder(ID, user.getId(), car.getId(), car.getBrand(), car.getModel(), start, end);
                        orderRepository.save(order);

                        log.info("You rented a car, have a nice trip!");
                }
                return car;
        }

}
