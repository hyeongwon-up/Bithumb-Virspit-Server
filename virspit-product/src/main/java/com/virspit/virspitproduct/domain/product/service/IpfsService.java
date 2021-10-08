package com.virspit.virspitproduct.domain.product.service;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class IpfsService {
    private static final String BASE_URL = "https://ipfs.io/ipfs/";

    private final IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

    @Value("${file.dir.root-path}")
    private String tmpDir;

    public String uploadJson(final String jsonString) throws IOException {
        List<MerkleNode> merkleNodes = ipfs.add(new NamedStreamable.ByteArrayWrapper(jsonString.getBytes()));
        if (merkleNodes.isEmpty()) {
            // TODO IPFS 파일 업로드 실패
        }

        return BASE_URL + merkleNodes.get(0).hash;
    }

    public String upload(final MultipartFile multipartFile) throws IOException {
        List<MerkleNode> merkleNodes = ipfs.add(new NamedStreamable.InputStreamWrapper(multipartFile.getInputStream()));
        if (merkleNodes.isEmpty()) {
            // TODO IPFS 파일 업로드 실패
        }

        return BASE_URL + merkleNodes.get(0).hash;
    }
}
