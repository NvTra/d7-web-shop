package com.tranv.d7shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "images_url", length = 300)
    private String imageUrl;
}
