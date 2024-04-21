package com.tranv.d7shop.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranv.d7shop.models.OrderDetail;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRepose {
    private Long id;
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private int totalMoney;

    @JsonProperty("color")
    private String color;

    public static OrderDetailRepose fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailRepose.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(builder().price)
                .numberOfProducts(builder().numberOfProducts)
                .totalMoney(builder().totalMoney)
                .color(builder().color)
                .build();
    }
}
