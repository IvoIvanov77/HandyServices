package org.softuni.handy.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface CloudinaryService {
    CompletableFuture<String> uploadImage(MultipartFile multipartFile) throws IOException;
}
