package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.ClassTypeDto;
import com.cts.flightBookingService.entity.ClassType;
import com.cts.flightBookingService.repository.ClassTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassTypeServiceImpl implements ClassTypeService {

    @Autowired
    private ClassTypeRepository classTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ClassTypeDto> getAllClassTypes() {
        return classTypeRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ClassTypeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClassTypeDto getClassTypeById(int id) {
        ClassType classType = classTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassType not found"));
        return modelMapper.map(classType, ClassTypeDto.class);
    }


    @Override
    public ClassTypeDto addClassType(ClassTypeDto dto) {
        ClassType classType = modelMapper.map(dto, ClassType.class);
        ClassType saved = classTypeRepository.save(classType);
        return modelMapper.map(saved, ClassTypeDto.class);
    }

    @Override
    public ClassTypeDto updateClassType(int id, ClassTypeDto dto) {
        ClassType classType = classTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassType not found"));
        classType.setType(dto.getType());
        ClassType updated = classTypeRepository.save(classType);
        return modelMapper.map(updated, ClassTypeDto.class);
    }

    @Override
    public void deleteClassType(int id) {
        classTypeRepository.deleteById(id);
    }


}
