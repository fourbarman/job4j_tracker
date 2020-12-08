package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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

    private Connection cn;

    /**
     * Connect to DB.
     */
    public void init() {
        try (InputStream in = Tracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Close connection.
     *
     * @throws Exception e.
     */
    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

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
            //item.setTime(System.currentTimeMillis());
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
     * @return result[] All Items.
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Find Items by key.
     *
     * @param key Searching key.
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (int i = 0; i < this.items.size(); i++) {
            if (key.equals(this.items.get(i).getName())) {
                result.add(this.items.get(i));
            }
        }
        return result;
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
        for (int i = 0; i < this.items.size(); i++) {
            if (id.equals(this.items.get(i).getId())) {
                index = i;
                break;
            }
        }
        return index;
    }
}
