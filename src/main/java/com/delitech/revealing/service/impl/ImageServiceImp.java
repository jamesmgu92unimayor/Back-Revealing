package com.delitech.revealing.service.impl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.delitech.revealing.entity.ImageEntity;
import com.delitech.revealing.repository.ImageRepository;
import com.delitech.revealing.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public ImageEntity save(MultipartFile file, Locale locale) throws IOException {
        return imageRepository.save(ImageEntity.builder()
                .name(file.getOriginalFilename())
                .data(Base64.getEncoder().encodeToString(file.getBytes()))
                .build());
    }

    @Override
    @Transactional
    public ImageEntity update(MultipartFile file, UUID id, Locale locale) throws IOException {
        ImageEntity image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found"));

        image.setData(Base64.getEncoder().encodeToString(file.getBytes()));
        return imageRepository.save(image);
    }

    @Override
    public ImageEntity getById(UUID id, Locale locale) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found"));
    }
}
