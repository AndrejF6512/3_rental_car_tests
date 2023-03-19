package com.foltan.rentalCarTestApp.controller;

import com.foltan.rentalCarTestApp.domain.Car;
import com.foltan.rentalCarTestApp.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

        private final DeliveryService deliveryService;

        @PostMapping("/delivery")
        public Car pickUpTheCar(@RequestParam Long carId) {
                return deliveryService.pickUpTheCar(carId);
        }

}
