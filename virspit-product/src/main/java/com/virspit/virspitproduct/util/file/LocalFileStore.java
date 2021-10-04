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

    @Value("${file.dir.root-path}")
    private String fileDir;

    @Override
    public String uploadFile(final MultipartFile multipartFile, final ContentType contentType) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String filename = createFilename(multipartFile.getOriginalFilename());
        multipartFile.transferTo(new File(getFullPath(filename, contentType)));

        return filename;
    }

    @Override
    public boolean deleteFile(final String filename, final ContentType contentType) {
        File file = new File(getFullPath(filename, contentType));
        return file.delete();
    }

    public String getFullPath(final String filename, final ContentType contentType) {
        return fileDir + contentType.getPath() + filename;
    }

    private String createFilename(final String originalFilename) {
        String filename = UUID.randomUUID().toString().replace("-", "");
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (extension != null) {
            filename = filename + "." + extension;
        }

        return filename;
    }
}
