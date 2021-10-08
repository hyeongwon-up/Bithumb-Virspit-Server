package com.virspit.virspitproduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virspit.virspitproduct.domain.product.feign.metadata.request.Metadata;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

//@SpringBootTest
class VirspitProductApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.dir.root-path}")
    String rootPath;

    @Test
    void contextLoads() throws IOException {
        Metadata metadata = new Metadata("test2", "test description", "image url");
        String json = objectMapper.writeValueAsString(metadata);
        logger.info(json);

        IPFS ipfs = new IPFS("localhost", 5001);

        MerkleNode merkleNode = ipfs.add(new NamedStreamable.ByteArrayWrapper(json.getBytes())).get(0);
        logger.info("ipfs result={}", merkleNode);
    }

}
