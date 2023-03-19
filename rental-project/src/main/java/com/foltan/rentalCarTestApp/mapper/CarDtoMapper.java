package com.foltan.rentalCarTestApp.mapper;

import com.foltan.rentalCarTestApp.domain.Car;
import com.foltan.rentalCarTestApp.dto.CarDto;
import org.springframework.stereotype.Service;

@Service
public class CarDtoMapper {

        public static Car mapToCar(CarDto carDto) {

                return Car.builder()
                        .registrationNr(carDto.getRegistrationNr())
                        .brand(carDto.getBrand())
                        .model(carDto.getModel())
                        .isAvailable(carDto.getIsAvailable())
                        .build();

        }

}
