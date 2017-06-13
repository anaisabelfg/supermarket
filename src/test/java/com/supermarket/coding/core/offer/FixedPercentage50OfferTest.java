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

public class FixedPercentage50OfferTest {

    @Test
    public void shouldReturnNoDiscountsWhenItemsAreNotPresent() {

        assertThat(fixedPercentage50Offer.apply(new ArrayList<>()), is(new ArrayList<Discount>()));

        assertThat(fixedPercentage50Offer.apply(null), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnInvalidParameterExceptionWhenItemsPatternAreNotPresent() {

        assertThatThrownBy(() -> new FixedPercentage50Offer(null))
                .isInstanceOf(InvalidParameterException.class);


        assertThatThrownBy(() -> new FixedPercentage50Offer(""))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void shouldReturnNoDiscountsWhenNoMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cheese", new BigDecimal(3.25)));
        }};

        assertThat(fixedPercentage50Offer.apply(items), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnDiscountsListWhenMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cereal", new BigDecimal(1.5)));
        }};

        List<Discount> expectedItems = new ArrayList<Discount>() {{
            add(new Discount(items, new Item("Discount 50% Cereal", new BigDecimal(-0.75))));
        }};

        assertThat(fixedPercentage50Offer.apply(items), is(expectedItems));
    }

    private FixedPercentage50Offer fixedPercentage50Offer
            = new FixedPercentage50Offer("Cereal");
}