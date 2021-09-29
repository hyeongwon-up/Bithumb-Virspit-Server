package com.virspit.virspitproduct;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {
    private final TypeResolver typeResolver;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("종목, 팀/플레이어, NFT 상품 API")
                .description("종목, 팀/플레이어, NFT 상품 API")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.virspit.virspitproduct"))
                .paths(PathSelectors.any())
                .build();
    }

    @Getter
    @Setter
    @ApiModel
    static class Page {
        @ApiModelProperty("페이지 번호(0..N)")
        private Integer page;

        @ApiModelProperty(value = "페이지 당 아이템 개수(1..100)")
        private Integer size;
    }
}
