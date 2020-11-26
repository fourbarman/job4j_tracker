package ru.job4j.tracker;

import java.lang.*;
import java.util.List;

/**
 * Tracker.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020.
 */
public class TrackerNotSingle extends BaseTracker {
    /**
     * Generate ID.
     * Set creation time.
     * Adds Item to storage.
     *
     * @param item New Item.
     * @return Item.
     */
    @Override
    public Item add(Item item) {
        return super.add(item);
    }

    /**
     * Generate ID.
     *
     * @return ID.
     */
    String generateId() {
        return super.generateId();
    }

    /**
     * Replace Item by ID.
     *
     * @param id   ID.
     * @param item New Item.
     */
    public boolean replace(String id, Item item) {
        return super.replace(id, item);
    }

    /**
     * Delete Item by ID.
     *
     * @param id ID
     * @return if success
     */
    public boolean delete(String id) {
        return super.delete(id);
    }

    /**
     * Shows all Items.
     *
     * @return List<Item> All Items.
     */
    public List<Item> findAll() {
        return super.findAll();
    }

    /**
     * Find Items by key.
     *
     * @param key Searching key.
     */
    public List<Item> findByName(String key) {
        return super.findByName(key);
    }

    /**
     * Find Item by ID.
     *
     * @param id ID.
     */
    public Item findById(String id) {
        return super.findById(id);
    }
}
