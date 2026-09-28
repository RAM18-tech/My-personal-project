package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.ClassTypeDto;

import java.util.List;

public interface ClassTypeService {
    List<ClassTypeDto> getAllClassTypes();
    ClassTypeDto getClassTypeById(int id);
    ClassTypeDto addClassType(ClassTypeDto dto);
    ClassTypeDto updateClassType(int id, ClassTypeDto dto);
    void deleteClassType(int id);

}
