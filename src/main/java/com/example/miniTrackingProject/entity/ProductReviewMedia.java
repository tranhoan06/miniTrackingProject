package com.example.miniTrackingProject.entity;

import com.example.miniTrackingProject.common.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_review_media")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewMedia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private ProductReviewsEntity reviews;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "media_type", nullable = false)
    private MediaType mediaType;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_delete", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDelete;
}
