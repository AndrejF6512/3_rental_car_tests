package com.foltan.rentalCarTestApp.mapper;

import com.foltan.rentalCarTestApp.domain.CarPackage;
import com.foltan.rentalCarTestApp.dto.CarPackageDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarPackageDtoMapper {

        public static CarPackage mapToCarPackage(CarPackageDto carPackageDto) {

                return CarPackage.builder()
                        .packageName(carPackageDto.getPackageName())
                        .pricePerHour(carPackageDto.getPricePerHour())
                        .cars(new ArrayList<>())
                        .build();

        }

}
