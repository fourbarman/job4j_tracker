package ru.job4j.tracker;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Tracker.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 2
 * @since 11.01.2021.
 */
public interface Tracker extends AutoCloseable {

    /**
     * Init DB connection.
     */
    void init();

    /**
     * Generate ID.
     * Set creation time.
     * Adds Item to storage.
     *
     * @param item New Item.
     * @return Item.
     */
    Item add(Item item);

    /**
     * Replace Item by ID.
     *
     * @param id   ID.
     * @param item New Item.
     */
    boolean replace(String id, Item item);

    /**
     * Delete Item by ID.
     *
     * @param id ID
     * @return if success
     */
    boolean delete(String id);

    /**
     * Shows all Items.
     *
     * @return List<Item> All Items.
     */
    List<Item> findAll();

    /**
     * Find Items by key.
     *
     * @param key Searching key.
     * @return List<Item> List of found items.
     */
    List<Item> findByName(String key);

    /**
     * Find Item by ID.
     *
     * @param id ID.
     */
    Item findById(String id);
}
