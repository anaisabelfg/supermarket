package com.supermarket.coding.core;

import com.supermarket.coding.core.model.Item;

import java.util.List;

public interface Checkout {
    String checkout(List<Item> items);
}
