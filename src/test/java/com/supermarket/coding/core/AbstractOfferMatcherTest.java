package com.supermarket.coding.core;

import com.supermarket.coding.core.model.Discount;
import com.supermarket.coding.core.model.Item;
import com.supermarket.coding.core.offer.AbstractOffer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


public class AbstractOfferMatcherTest {

    @Test
    public void shouldReturnTheItemListWhenItemOrOffersAreNotPresent() {

        List<Item> emptyItemList = new ArrayList<>();
        List<AbstractOffer> emptyAbstractOfferList = new ArrayList<>();

        assertThat(OfferMatcher.apply(oneItemList, null), is(oneItemList));
        assertNull(OfferMatcher.apply(null, oneAbstractOfferList));

        assertNull(OfferMatcher.apply(null, emptyAbstractOfferList));
        assertThat(OfferMatcher.apply(emptyItemList, null), is(emptyItemList));

        assertThat(OfferMatcher.apply(oneItemList, emptyAbstractOfferList), is(oneItemList));
        assertThat(OfferMatcher.apply(emptyItemList, oneAbstractOfferList), is(emptyItemList));

        assertNull(OfferMatcher.apply(null, null));
        assertThat(OfferMatcher.apply(emptyItemList, emptyAbstractOfferList), is(emptyItemList));

    }

    @Test
    public void shouldApplyOneOffer() throws Exception {

        List<Item> itemList = new ArrayList<Item>() {{
            add(cereal);
            add(milk);
            add(milk);
        }};

        List<AbstractOffer> abstractOfferList = new ArrayList<AbstractOffer>() {{
            add(fixedDiscountCerealX1Mock);
        }};

        List<Item> expectedItems = new ArrayList<Item>() {{
            addAll(itemList);
            add(new Item("Discount 50% Cereal", new BigDecimal(-0.75)));
        }};

        assertThat(OfferMatcher.apply(itemList, abstractOfferList), is(expectedItems));
    }

    @Test
    public void shouldApplySeveralOffers() throws Exception {

        List<Item> itemList = new ArrayList<Item>() {{
            add(cereal);
            add(milk);
            add(milk);
        }};
        List<AbstractOffer> abstractOfferList = new ArrayList<AbstractOffer>() {{
            add(fixedDiscountCerealX1Mock);
            add(twoForOneMilkMock);
        }};
        List<Item> expectedItems = new ArrayList<Item>() {{
            add(cereal);
            add(milk);
            add(milk);
            add(new Item("Discount 50% Cereal", new BigDecimal(-0.75)));
            add(new Item("2 for 1 in milk", new BigDecimal(-1.8)));
        }};

        List<Item> itemsWithAppliedOffers = OfferMatcher.apply(itemList, abstractOfferList);

        assertThat(itemsWithAppliedOffers, is(expectedItems));
    }

    @Test
    public void shouldApplyBestOfferWhenMoreThanOneMatches() throws Exception {

        List<Item> itemList = new ArrayList<Item>() {{
            add(cereal);
            add(cereal);
            add(cereal);
        }};
        List<AbstractOffer> abstractOfferList = new ArrayList<AbstractOffer>() {{
            add(fixedDiscountCerealX3Mock);
            add(threeForTwoCerealMock);
        }};
        List<Item> expectedItems = new ArrayList<Item>() {{
            add(cereal);
            add(cereal);
            add(cereal);
            add(halfPriceCereal);
            add(halfPriceCereal);
            add(halfPriceCereal);
        }};

        assertThat(OfferMatcher.apply(itemList, abstractOfferList), is(expectedItems));
    }

    private Item cereal = new Item("Cereal", new BigDecimal(1.5));
    private Item milk = new Item("Milk", new BigDecimal(1.8));

    private Item halfPriceCereal = new Item("Discount 50% Cereal", new BigDecimal(-0.75));

    private List<Item> oneItemList = new ArrayList<Item>() {{
        add(cereal);
    }};

    private List<Item> milkMatchItemList = new ArrayList<Item>() {{
        add(milk);
        add(milk);
    }};

    private List<Item> cerealMatchItemList = new ArrayList<Item>() {{
        add(cereal);
        add(cereal);
        add(cereal);
    }};

    private Item cerealDiscount3for1 = new Item("3 for 1 in Cereal", new BigDecimal(-1.50));

    private Item milkDiscount = new Item("2 for 1 in milk", new BigDecimal(-1.8));

    private AbstractOffer fixedDiscountCerealX1Mock = new AbstractOffer("Cereal") {
        @Override
        public List<Discount> apply(List<Item> items) {
            return new ArrayList<Discount>() {{
                add(new Discount(oneItemList, halfPriceCereal));
            }};
        }
    };

    private AbstractOffer fixedDiscountCerealX3Mock = new AbstractOffer("Cereal") {
        @Override
        public List<Discount> apply(List<Item> items) {
            return new ArrayList<Discount>() {{
                add(new Discount(oneItemList, halfPriceCereal));
                add(new Discount(oneItemList, halfPriceCereal));
                add(new Discount(oneItemList, halfPriceCereal));
            }};
        }
    };

    private AbstractOffer twoForOneMilkMock = new AbstractOffer("Milk") {
        @Override
        public List<Discount> apply(List<Item> items) {
            return new ArrayList<Discount>() {{
                add(new Discount(milkMatchItemList, milkDiscount));
            }};
        }
    };

    private AbstractOffer threeForTwoCerealMock = new AbstractOffer("Cereal") {
        @Override
        public List<Discount> apply(List<Item> items) {
            return new ArrayList<Discount>() {{
                add(new Discount(cerealMatchItemList, cerealDiscount3for1));
            }};
        }
    };

    private List<AbstractOffer> oneAbstractOfferList = new ArrayList<AbstractOffer>() {{
        add(fixedDiscountCerealX1Mock);
    }};


}