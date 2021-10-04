package com.virspit.virspitproduct.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStore {
    String uploadFile(MultipartFile multipartFile, ContentType contentType) throws IOException;

    boolean deleteFile(String fileUrl, ContentType contentType);
}
