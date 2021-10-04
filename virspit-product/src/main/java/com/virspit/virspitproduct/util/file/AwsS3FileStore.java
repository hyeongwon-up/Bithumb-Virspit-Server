package com.virspit.virspitproduct.util.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwsS3FileStore implements FileStore {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${file.dir.root-path}")
    private String fileDir;

    @Override
    public String uploadFile(MultipartFile multipartFile, ContentType contentType) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String filename = createFilename(multipartFile.getOriginalFilename());

        File tmpFile = new File(fileDir, filename);
        multipartFile.transferTo(tmpFile);

        String uploadPath = contentType.getPath() + filename;
        amazonS3Client.putObject(new PutObjectRequest(bucket, uploadPath, tmpFile).withCannedAcl(CannedAccessControlList.PublicRead));

        if (!tmpFile.delete()) {
            // TODO 임시 파일 삭제 실패 시 처리할 작업 구현
        }

        return amazonS3Client.getUrl(bucket, uploadPath).toString();
    }

    private String createFilename(final String originalFilename) {
        String filename = UUID.randomUUID().toString().replace("-", "");
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (extension != null) {
            filename = filename + "." + extension;
        }

        return filename;
    }

    @Override
    public boolean deleteFile(String fileUrl, ContentType contentType) {
        String filename = StringUtils.getFilename(fileUrl);
        if (filename == null) {
            return false;
        }

        amazonS3Client.deleteObject(bucket, contentType.getPath() + filename);

        return true;
    }
}
