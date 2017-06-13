package com.supermarket.coding;

import com.supermarket.coding.core.BasicCheckout;
import com.supermarket.coding.core.OfferMatcher;
import com.supermarket.coding.core.model.Item;
import com.supermarket.coding.core.model.Offer;
import com.supermarket.coding.core.offer.AbstractOffer;
import com.supermarket.coding.core.offer.OfferFactory;
import com.supermarket.coding.infrastructure.ListReader;

import java.util.List;

public class CheckoutApplication {

    public static void main(String[] args) {

        if (args.length < 1) {
            throw new RuntimeException("No path to the input list was provided.");
        }

        List<Item> itemList = new ListReader<Item>().read(args[0], Item.class);

        if (args.length > 1) {

            List<Offer> offersList = new ListReader<Offer>().read(args[1], Offer.class);
            List<AbstractOffer> offers = OfferFactory.getOffers(offersList);

            itemList = OfferMatcher.apply(itemList, offers);
        }

        new BasicCheckout().checkout(itemList);
    }
}
