package com.back.confectionary.products.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    @JsonProperty("type")
    private String productType;
    private Long price;
    @JsonProperty("category_id")
    private Long categoryId;
    /*private List<GalleryItemRequest> gallery;
    private GalleryItemRequest preview;
    private List<ReviewRequest> reviews;*/
    private List<Long> gallery;
    private Long preview;
    private List<Long> reviews;
}
