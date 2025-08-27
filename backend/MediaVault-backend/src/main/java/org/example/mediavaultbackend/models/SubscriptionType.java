package org.example.mediavaultbackend.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "subscription_type")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_type_id")
    private Long subscriptionTypeId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price_reduction", nullable = false)
    private Double priceReduction;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "extensions", nullable = false)
    private Integer extensions;

}
