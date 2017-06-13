package com.supermarket.coding.core.offer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.supermarket.coding.core.model.Discount;
import com.supermarket.coding.core.model.Item;

import java.security.InvalidParameterException;
import java.util.List;

public abstract class AbstractOffer {
    private String product;

    @JsonCreator
    public AbstractOffer(String product) {

        if (null == product || product.isEmpty())
            throw new InvalidParameterException("Offer must be present");
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public abstract List<Discount> apply(List<Item> items);
}
