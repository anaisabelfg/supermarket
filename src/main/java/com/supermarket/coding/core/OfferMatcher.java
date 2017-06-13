package com.supermarket.coding.core;

import com.supermarket.coding.core.model.Discount;
import com.supermarket.coding.core.model.Item;
import com.supermarket.coding.core.offer.AbstractOffer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OfferMatcher {

    public static List<Item> apply(List<Item> items, List<AbstractOffer> abstractOffers) {

        if (null == items || items.isEmpty() || null == abstractOffers || abstractOffers.isEmpty()) return items;

        List<Item> bestDiscountItems = abstractOffers.stream()
                .collect(Collectors.groupingBy(AbstractOffer::getProduct)).entrySet().stream()
                .map(productOffers -> toDiscountsByOffer(items, productOffers))
                .map(OfferMatcher::chooseBestOffer)
                .flatMap(d -> d.orElse(new ArrayList<>()).stream())
                .map(Discount::getDiscountItem)
                .collect(Collectors.toList());

        return Stream.concat(items.stream(), bestDiscountItems.stream()).collect(Collectors.toList());
    }

    private static Optional<List<Discount>> chooseBestOffer(Stream<List<Discount>> discountsLists) {
        return discountsLists
                .sorted(Comparator.comparing(OfferMatcher::sumDiscounts))
                .findFirst();
    }

    private static Stream<List<Discount>> toDiscountsByOffer(List<Item> items, Map.Entry<String, List<AbstractOffer>> productOffers) {
        return productOffers.getValue().stream().map(offer -> offer.apply(items));
    }

    private static BigDecimal sumDiscounts(List<Discount> discounts) {
        return discounts.stream()
                .map(d -> d.getDiscountItem().getPrice())
                .reduce(new BigDecimal(0), BigDecimal::add);
    }
}