package com.supermarket.coding.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.InvalidParameterException;

public class Offer {

    private String name;
    private String product;

    @JsonCreator
    public Offer(@JsonProperty("name") String name,
                 @JsonProperty("product") String product) {

        if (null == product || product.isEmpty())
            throw new InvalidParameterException("Product must be present");
        this.name = name;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }
}
