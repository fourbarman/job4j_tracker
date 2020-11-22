package ru.job4j.tracker;

import java.util.Arrays;
import java.util.Random;
/**
 * TrackerSingleEnum.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020.
 */
public enum TrackerSingleEnum implements Tracker {
    INSTANCE;
    /**
     * Random.
     */

    private static final Random RN = new Random();
    /**
     * Item's storage.
     */
    private final Item[] items = new Item[100];

    /**
     * Cell pointer for a new Item.
     */
    private int position = 0;

    /**
     * Generate ID.
     * Set creation time.
     * Adds Item to storage.
     *
     * @param item New Item.
     * @return Item.
     */
    public Item add(Item item) {
        if (item != null) {
            item.setId(this.generateId());
            item.setTime(System.currentTimeMillis());
            this.items[this.position++] = item;
        }
        return item;
    }

    /**
     * Generate ID.
     *
     * @return ID.
     */
    String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    /**
     * Replace Item by ID.
     *
     * @param id   ID.
     * @param item New Item.
     */
    public boolean replace(String id, Item item) {
        boolean success = false;
        for (int i = 0; i < this.position; i++) {
            if (this.items[i] != null && this.items[i].getId().equals(id)) {
                item.setTime(System.currentTimeMillis());
                item.setId(id);
                this.items[i] = item;
                success = true;
            }
        }
        return success;
    }

    /**
     * Delete Item by ID.
     *
     * @param id ID
     * @return if success
     */
    public boolean delete(String id) {
        boolean success = false;
        for (int i = 0; i < this.position; i++) {
            if (items[i].getId().equals(id)) {
                items[i] = null;
                System.arraycopy(this.items, i + 1, this.items, i, this.position - i - 1);
                this.position--;
                success = true;
            }
        }
        return success;
    }

    /**
     * Shows all Items.
     *
     * @return result[] All Items.
     */
    public Item[] findAll() {
        return Arrays.copyOf(this.items, position);
    }

    /**
     * Find Items by key.
     *
     * @param key Searching key.
     */
    public Item[] findByName(String key) {
        Item[] result = new Item[this.items.length];
        int found = 0;
        for (int i = 0; i < this.position; i++) {
            if (this.items[i] != null && key != null && this.items[i].getName().contains(key)) {
                result[found] = items[i];
                found++;
            }
        }
        return Arrays.copyOf(result, found);
    }

    /**
     * Find Item by ID.
     *
     * @param id ID.
     */
    public Item findById(String id) {
        Item item = null;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getId().equals(id)) {
                item = items[i];
            }
        }
        return item;
    }
}
