package com.virspit.virspitproduct.domain.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final IPFS ipfs;
    private final ObjectMapper objectMapper;

    @Value("${file.dir.root-path}")
    private String tmpDir;

    public IpfsService(@Value("${ipfs.server}") String serverUrl, @Value("${ipfs.port}") String port, ObjectMapper objectMapper) {
        ipfs = new IPFS("/ip4/" + serverUrl + "/tcp/" + port);
        this.objectMapper = objectMapper;
    }

    public String upload(final Object object) throws IOException {
        String objectJson = objectMapper.writeValueAsString(object);
        List<MerkleNode> merkleNodes = ipfs.add(new NamedStreamable.ByteArrayWrapper(objectJson.getBytes()));
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
