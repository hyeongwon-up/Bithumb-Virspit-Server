package com.virspit.virspitproduct.domain.product.repository;

import com.virspit.virspitproduct.domain.product.entity.NftInfo;
import com.virspit.virspitproduct.domain.product.entity.Product;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.sports.repository.SportsRepository;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private SportsRepository sportsRepository;

    private TeamPlayer teamPlayer;

    @BeforeEach
    void setupSportsAndTeamPlayer() {
        Sports sports = sportsRepository.save(new Sports("축구", ""));
        teamPlayer = teamPlayerRepository.save(TeamPlayer.builder().name("손흥민")
                .sports(sports)
                .revenueShareRate(70)
                .type(TeamPlayerType.PLAYER)
                .description("축구 선수 손흥민")
                .build());
    }

    @Test
    @DisplayName("상품 생성 테스트")
    void createProductTest() {
        // given
        String name = "손흥민 프리미어리그 2020-2021";
        String description = "2020-2021 프리미어리그 손흥민 카드";
        int count = 100;
        boolean exhibition = true;
        int price = 100;
        LocalDateTime startDateTime = LocalDateTime.parse("2021-10-10T20:00:00");
        NftInfo nftInfo = NftInfo.builder()
                .contractAlias("son-pl-2020-2021")
                .metadataUri("https://meta.data/ntf")
                .build();

        // when
        Product storedProduct = saveProductsAndGetAll(1).get(0);

        // then
        assertThat(storedProduct.getId()).isNotNull();
        assertThat(storedProduct.getTitle()).isEqualTo(name);
        assertThat(storedProduct.getDescription()).isEqualTo(description);
        assertThat(storedProduct.getTeamPlayer().getId()).isEqualTo(teamPlayer.getId());
        assertThat(storedProduct.getNftInfo()).isEqualTo(nftInfo);
        assertThat(storedProduct.getCount()).isEqualTo(count);
        assertThat(storedProduct.getExhibition()).isEqualTo(exhibition);
        assertThat(storedProduct.getPrice()).isEqualTo(price);
        assertThat(storedProduct.getStartDateTime()).isEqualTo(startDateTime);
    }

    @Test
    @DisplayName("상품 id로 조회 테스트")
    void findProductByIdTest() {
        // given
        Product product = saveProductsAndGetAll(1).get(0);

        // when
        Product storedProduct = productRepository.findById(product.getId()).get();

        // then
        assertThat(storedProduct.getId()).isEqualTo(product.getId());
        assertThat(storedProduct.getTitle()).isEqualTo(product.getTitle());
        assertThat(storedProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(storedProduct.getTeamPlayer()).isEqualTo(product.getTeamPlayer());
        assertThat(storedProduct.getNftInfo()).isEqualTo(product.getNftInfo());
        assertThat(storedProduct.getCount()).isEqualTo(product.getCount());
        assertThat(storedProduct.getExhibition()).isEqualTo(product.getExhibition());
        assertThat(storedProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(storedProduct.getStartDateTime()).isEqualTo(product.getStartDateTime());
    }

    @Test
    @DisplayName("상품 업데이트 테스트")
    void updateProductTest() {
        // given
        Product prevProduct = saveProductsAndGetAll(1).get(0);

        Sports sports = sportsRepository.save(new Sports("배구", ""));
        TeamPlayer volleyballTeamPlayer = teamPlayerRepository.save(TeamPlayer.builder().name("김연경")
                .sports(sports)
                .revenueShareRate(70)
                .type(TeamPlayerType.PLAYER)
                .description("배구 선수 김연경")
                .build());

        String updatedName = "김연경 V-리그 2020-2021";
        String updatedDescription = "2020-2021 V-리그 김연경 카드";
        int updatedCount = 10;
        boolean updatedExhibition = false;
        int updatedPrice = 500;
        LocalDateTime updatedStartDateTime = LocalDateTime.now();
        NftInfo updatedNftInfo = NftInfo.builder()
                .contractAlias("kim-vl-2020-2021")
                .metadataUri("https://meta.data/ntf-2")
                .build();

        prevProduct.update(Product.builder()
                .title(updatedName)
                .description(updatedDescription)
                .teamPlayer(volleyballTeamPlayer)
                .count(updatedCount)
                .exhibition(updatedExhibition)
                .price(updatedPrice)
                .startDateTime(updatedStartDateTime)
                .nftInfo(updatedNftInfo)
                .build());

        // when
        Product updatedProduct = productRepository.save(prevProduct);

        assertThat(updatedProduct.getId()).isEqualTo(prevProduct.getId());
        assertThat(updatedProduct.getTitle()).isEqualTo(updatedName);
        assertThat(updatedProduct.getDescription()).isEqualTo(updatedDescription);
        assertThat(updatedProduct.getTeamPlayer()).isEqualTo(volleyballTeamPlayer);
        assertThat(updatedProduct.getNftInfo()).isEqualTo(updatedNftInfo);
        assertThat(updatedProduct.getCount()).isEqualTo(updatedCount);
        assertThat(updatedProduct.getExhibition()).isEqualTo(updatedExhibition);
        assertThat(updatedProduct.getPrice()).isEqualTo(updatedPrice);
        assertThat(updatedProduct.getStartDateTime()).isEqualTo(updatedStartDateTime);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductTest() {
        // given
        saveProductsAndGetAll(10);
        List<Product> storedProducts = productRepository.findAll();
        int expectedCount = storedProducts.size() - 1;

        // when
        productRepository.deleteById(storedProducts.get(0).getId());
        List<Product> afterDeletionProducts = productRepository.findAll();

        // then
        assertThat(afterDeletionProducts.size()).isEqualTo(expectedCount);
    }

    private List<Product> saveProductsAndGetAll(int productCount) {
        String title = "손흥민 프리미어리그 2020-2021";
        String description = "2020-2021 프리미어리그 손흥민 카드";
        int count = 100;
        boolean exhibition = true;
        int price = 100;
        LocalDateTime startDateTime = LocalDateTime.parse("2021-10-10T20:00:00");
        NftInfo nftInfo = NftInfo.builder()
                .contractAlias("son-pl-2020-2021")
                .metadataUri("https://meta.data/ntf")
                .build();

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < productCount; i++) {
            products.add(productRepository.save(Product.builder()
                    .title(title)
                    .description(description)
                    .teamPlayer(teamPlayer)
                    .count(count)
                    .exhibition(exhibition)
                    .price(price)
                    .startDateTime(startDateTime)
                    .nftInfo(nftInfo)
                    .build()));
        }

        return products;
    }
}