package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.entity.ImageEntity;
import com.delitech.revealing.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import static com.delitech.revealing.commons.Constants.GENERAL_CREATE_SUCCESS;
import static com.delitech.revealing.commons.Constants.GENERAL_DELETE_SUCCESS;
import static com.delitech.revealing.commons.Constants.GENERAL_SUCCESS;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;
    private final MessageSource messageSource;

    @PostMapping("")
    public ResponseEntity<GeneralBodyResponse<ImageEntity>> save(@RequestParam("file") MultipartFile file, Locale locale) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale),
                        service.save(file, locale)));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<ImageEntity>> getById(@PathVariable("id") UUID id, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_SUCCESS, null, locale),
                        service.getById(id, locale)));
    }

    @PutMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<Void>> delete(@PathVariable("id") UUID id, @RequestParam("file") MultipartFile file, Locale locale) throws IOException {
        service.update(file, id, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_DELETE_SUCCESS, null, locale), null));
    }
}
