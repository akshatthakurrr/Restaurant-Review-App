package com.akshatapp.restaurant.controllers;


import com.akshatapp.restaurant.domain.dtos.PhotoDto;
import com.akshatapp.restaurant.domain.entities.Photo;
import com.akshatapp.restaurant.mappers.PhotoMappers;
import com.akshatapp.restaurant.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/api/photos")
public class PhotoControllers {
    private final PhotoService photoService;
    private  final PhotoMappers photoMappers;

    @PostMapping
    public PhotoDto uploadPhoto(
            @RequestParam("file")MultipartFile file){
        Photo savedPhoto =photoService.uploadPhoto(file);
        return photoMappers.todto(savedPhoto);
    }
    @GetMapping("/{id:.+}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String id) {
        return photoService.getPhotoResourse(id).map(photo ->
                ResponseEntity.ok()
                        .contentType(
                                MediaTypeFactory.getMediaType(photo)
                                .orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .body(photo)
        ).orElse(ResponseEntity.notFound().build());
    }
}
