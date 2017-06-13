package com.supermarket.coding.core.offer;

import com.supermarket.coding.core.model.Discount;
import com.supermarket.coding.core.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FixedPercentage50Offer extends AbstractOffer {

    public FixedPercentage50Offer(String product) {
        super(product);
    }

    @Override
    public List<Discount> apply(List<Item> items) {

        if (null == items || items.isEmpty())
            return new ArrayList<>();

        return items.stream()
                .filter(item -> getProduct().contains(item.getName()))
                .map(toDiscount()).collect(Collectors.toList());

    }

    private Function<Item, Discount> toDiscount() {
        return item -> {
            List<Item> itemsInvolved = new ArrayList<>();
            itemsInvolved.add(item);
            Item discountItem = new Item("Discount 50% " + item.getName(),
                    item.getPrice().multiply(new BigDecimal(0.5)).negate());

            return new Discount(itemsInvolved, discountItem);
        };
    }
}
