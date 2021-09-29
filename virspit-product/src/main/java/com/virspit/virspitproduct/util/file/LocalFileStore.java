package com.virspit.virspitproduct.util.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class LocalFileStore implements FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String storeFilename = createStoreFilename(multipartFile.getOriginalFilename());
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return storeFilename;
    }

    public boolean deleteFile(String filename) {
        File file = new File(getFullPath(filename));
        return file.delete();
    }

    public String getFullPath(final String filename) {
        return fileDir + filename;
    }

    private String createStoreFilename(String originalFilename) {
        String storeFilename = UUID.randomUUID().toString();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (extension != null) {
            storeFilename += "." + extension;
        }

        return storeFilename;
    }
}
