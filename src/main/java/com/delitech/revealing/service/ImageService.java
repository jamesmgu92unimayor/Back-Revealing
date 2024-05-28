package com.delitech.revealing.service;


import com.delitech.revealing.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public interface ImageService {

    ImageEntity save(MultipartFile file, Locale locale) throws IOException;

    ImageEntity update(MultipartFile file, UUID id, Locale locale) throws IOException;

    ImageEntity getById(UUID id, Locale locale);


}
