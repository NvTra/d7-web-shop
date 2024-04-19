package com.tranv.d7shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SocialAcount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "provider", length = 20)
    private String provider;
    @Column(name = "provider_id", length = 50)
    private String providerId;

    @Column(name = "email", length = 20)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
