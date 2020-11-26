package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * BaseTracker.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020
 */
public abstract class BaseTracker implements Tracker {
    /**
     * Random.
     */

    private static final Random RN = new Random();
    /**
     * Item's storage.
     */
    private final List<Item> items = new ArrayList<>();

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
            this.items.add(item);
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
        int index = this.findIndex(id);
        if (index != -1) {
            item.setId(id);
            this.items.set(index, item);
            return true;
        }
        return false;
    }

    /**
     * Delete Item by ID.
     *
     * @param id ID
     * @return if success
     */
    public boolean delete(String id) {
        int index = this.findIndex(id);
        if (index != -1) {
            this.items.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Shows all Items.
     *
     * @return List<Item> All Items.
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Find Items by key.
     *
     * @param key Searching key.
     * @return List<Item> list.
     */
    public List<Item> findByName(String key) {
        if (key != null) {
            List<Item> result = new ArrayList<>();
            Item item;
            for (int i = 0; i < this.items.size(); i++) {
                item = this.items.get(i);
                if (item.getName().contains(key)) {
                    result.add(item);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * Find Item by ID.
     *
     * @param id ID.
     */
    public Item findById(String id) {
        int index = findIndex(id);
        if (index != -1) {
            return this.items.get(index);
        }
        return null;
    }

    /**
     * Find Item index by ID.
     *
     * @param id ID.
     * @return index.
     */
    private int findIndex(String id) {
        int index = -1;
        if (id != null) {
            for (int i = 0; i < this.items.size(); i++) {
                if (id.equals(this.items.get(i).getId())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
