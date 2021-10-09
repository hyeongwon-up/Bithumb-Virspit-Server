package com.virspit.virspitproduct.domain.product.service;

import com.virspit.virspitproduct.domain.product.feign.metadata.request.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NftService {
    private final KasService kasService;
    private final IpfsService ipfsService;

    public void deployNftContract(final String contractAlias) {
        kasService.deployNftContract(contractAlias);
    }

    public String uploadMetadata(final String name, final String description, final MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            return null;
        }

        String nftImageUrl = ipfsService.upload(imageFile);
        return ipfsService.upload(new Metadata(name, description, nftImageUrl));
    }
}
