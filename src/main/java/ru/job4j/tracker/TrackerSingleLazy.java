package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TrackerSingleLazy.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 2
 * @since 11.01.2021.
 */
public class TrackerSingleLazy extends BaseTracker {
    private static TrackerSingleLazy instance;

    private TrackerSingleLazy() {
    }

    public static TrackerSingleLazy getInstance() {
        if (instance == null) {
            instance = new TrackerSingleLazy();
        }
        return instance;
    }

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
     * @return result[] All Items.
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
