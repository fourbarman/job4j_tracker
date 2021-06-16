package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemTracker implements Tracker {

    /**
     * Constructor.
     */
    public MemTracker() {
        n = 10000;
        ids = new ArrayList<>();
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
     * This is for profiling.
     */
    public int n;
    public List<String> ids;

    /**
     * Initialise fields.
     */
    @Override
    public void init() {
        ids = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ids.add(this.add(new Item("TEST" + i, "TEST" + i)).getId());
        }
    }

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

    @Override
    public void close() throws Exception {

    }
}
