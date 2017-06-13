package com.supermarket.coding.infrastructure;

import com.supermarket.coding.core.model.Item;
import com.supermarket.coding.core.model.Offer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListReaderTest {
    @Test
    public void shouldReadEmptyFile() {
        List<Item> items = new ListReader<Item>().read("src/test/resources/empty.json", Item.class);
        assertThat(items).isEmpty();
    }

    @Test
    public void shouldReadSingleItemList() {
        List<Item> items = new ListReader<Item>().read("src/test/resources/single.json", Item.class);
        assertThat(items).hasSize(1);
        Item item = items.get(0);
        assertThat(item.getName()).isEqualTo("beans");
        assertThat(item.getPrice()).isEqualTo(new BigDecimal("1.50"));
    }

    @Test
    public void shouldReadMultipleItemList() {
        List<Item> items = new ListReader<Item>().read("src/test/resources/multiple.json", Item.class);
        assertThat(items).hasSize(2);
    }

    @Test
    public void shouldReadMultipleOfferList() {
        List<Offer> offers = new ListReader<Offer>().read("src/test/resources/offersList.json", Offer.class);
        assertThat(offers).hasSize(3);
        Offer offer = offers.get(0);
        assertThat(offer.getName()).isEqualTo("2 for 2£");
        assertThat(offer.getProduct()).isEqualTo("cereal");
    }

    @Test(expected = MalformedListException.class)
    public void shouldThrowErrorForMalformedInput() {
        List<Item> items = new ListReader<Item>().read("src/test/resources/malformed.json", Item.class);
        assertThat(items).hasSize(1);
    }

    @Test(expected = MalformedListException.class)
    public void shouldThrowErrorIfFileDoesNotExist() {
        List<Item> items = new ListReader<Item>().read("nonexistent.json", Item.class);
        assertThat(items).hasSize(1);
    }
}
