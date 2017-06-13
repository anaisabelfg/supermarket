package com.supermarket.coding.core.offer;

import com.supermarket.coding.core.model.Discount;
import com.supermarket.coding.core.model.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Buy2IdenticalItemsFor2PoundsOfferTest {

    @Test
    public void shouldReturnNoDiscountsWhenItemsAreNotPresent() {

        assertThat(buy2IdenticalItemsFor2PoundsOffer.apply(new ArrayList<>()), is(new ArrayList<Discount>()));

        assertThat(buy2IdenticalItemsFor2PoundsOffer.apply(null), is(new ArrayList<Discount>()));
    }

    @Test
    public void shouldReturnInvalidParameterExceptionWhenItemsPatternAreNotPresent() {

        assertThatThrownBy(() -> new Buy2IdenticalItemsFor2PoundsOffer(null))
                .isInstanceOf(InvalidParameterException.class);


        assertThatThrownBy(() -> new Buy2IdenticalItemsFor2PoundsOffer(""))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void shouldReturnNoDiscountsWhenNoMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cheese", new BigDecimal(3.25)));
            add(new Item("Cereal", new BigDecimal(1.50)));
        }};

        assertThat(buy2IdenticalItemsFor2PoundsOffer.apply(items), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnDiscountsListWhenMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cereal", new BigDecimal(1.5)));
            add(new Item("Cereal", new BigDecimal(1.5)));
        }};

        List<Discount> expectedItems = new ArrayList<Discount>() {{
            add(new Discount(items, new Item("2 for 2£ Cereal", new BigDecimal(-1))));
        }};

        assertThat(buy2IdenticalItemsFor2PoundsOffer.apply(items), is(expectedItems));
    }

    private Buy2IdenticalItemsFor2PoundsOffer buy2IdenticalItemsFor2PoundsOffer
            = new Buy2IdenticalItemsFor2PoundsOffer("Cereal");

}