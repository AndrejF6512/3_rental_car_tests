package com.foltan.rentalCarTestApp.mapper;

import com.foltan.rentalCarTestApp.domain.AccessKey;
import com.foltan.rentalCarTestApp.dto.AccessKeyDto;
import org.springframework.stereotype.Service;

@Service
public class AccessKeyDtoMapper {

        public static AccessKeyDto mapToAccessKeyDto(AccessKey accessKey) {
                return AccessKeyDto.builder()
                        .id(accessKey.getId())
                        .carPackage(accessKey.getCarPackage())
                        .hours(accessKey.getHours())
                        .build();
        }

}
