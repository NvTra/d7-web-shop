package com.tranv.d7shop.reponse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListRepose {
    private List<ProductRepose> productList;
    private int totalPages;
}
