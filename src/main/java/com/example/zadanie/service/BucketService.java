package com.example.zadanie.service;

import com.example.zadanie.dto.BucketDTO;
import com.example.zadanie.dto.ProductDTO;
import com.example.zadanie.entity.Bucket;
import com.example.zadanie.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final ProductService productService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BucketDTO getBucket(String bucketJson) {
        Map<ProductDTO, Integer> products = new HashMap<>();
        double price = 0;

        if((bucketJson != null && !bucketJson.isEmpty())) {
            Bucket bucket = createBucketFromString(decodeCookie(bucketJson));

            price = getBucketPrice(bucket);
            products = getBucketProducts(bucket);

        }

        return createBucket(products, price);
    }

    private Map<ProductDTO, Integer> getBucketProducts(Bucket bucket) {
        return bucket.getProducts()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> ProductDTO.of(productService.getProduct(entry.getKey())),
                        Map.Entry::getValue
                ));
    }

    private double getBucketPrice(Bucket bucket) {
        return bucket.getProducts().entrySet().stream().mapToDouble(entry -> {
            Product product = productService.getProduct(entry.getKey());
            return product.getPrice() * entry.getValue();
        }).sum();
    }

    private BucketDTO createBucket(Map<ProductDTO, Integer> products, double price) {
        return BucketDTO.of(products, price);
    }

    public String addProductToBucket(String bucketJson, Long id) {
        Product product = productService.getProduct(id);

        Bucket bucket = (bucketJson == null || bucketJson.isEmpty()) ? new Bucket() : createBucketFromString(decodeCookie(bucketJson));
        bucket.addProduct(product);
        return encodeCookie(createBucketJson(bucket));

    }

    public String deleteProductFromBucket(String bucketJson, Long id) {
        Product product = productService.getProduct(id);
        Bucket bucket = (bucketJson == null || bucketJson.isEmpty()) ? new Bucket() : createBucketFromString(decodeCookie(bucketJson));
        bucket.deleteProduct(product);
        System.out.println(bucket);
        return encodeCookie(createBucketJson(bucket));
    }

    private String createBucketJson(Bucket bucket) {
        try {
            return objectMapper.writeValueAsString(bucket);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Bucket createBucketFromString(String bucketJson) {
        Bucket bucket;
        try {
            bucket = objectMapper.readValue(bucketJson, Bucket.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return bucket;
    }

    private String decodeCookie(String bucketJson) {
        return new String(Base64.getDecoder().decode(bucketJson), StandardCharsets.UTF_8);
    }

    private String encodeCookie(String bucketToCookie) {
        return Base64.getEncoder().encodeToString(bucketToCookie.getBytes(StandardCharsets.UTF_8));
    }
}
