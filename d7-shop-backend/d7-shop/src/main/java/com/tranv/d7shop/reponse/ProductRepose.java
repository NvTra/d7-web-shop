package com.tranv.d7shop.reponse;


import com.tranv.d7shop.models.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRepose extends BaseRepose {

    private String name;

    private Float price;

    private String thumbnail;

    private String description;

    private Long categoryId;

    public static ProductRepose fromProduct(Product product) {
        ProductRepose productRepose = ProductRepose.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productRepose.setCreatedAt(product.getCreatedAt());
        productRepose.setUpdateAt(product.getUpdateAt());
        return productRepose;
    }
}
