package com.example.java_ifortex_test_task.util;

import com.example.java_ifortex_test_task.entity.DeviceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DeviceTypeConverter implements AttributeConverter<DeviceType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DeviceType deviceType) {
        return deviceType != null ? deviceType.getCode() : null;
    }

    @Override
    public DeviceType convertToEntityAttribute(Integer integer) {
        return integer != null ? DeviceType.fromCode(integer) : null;
    }
}
