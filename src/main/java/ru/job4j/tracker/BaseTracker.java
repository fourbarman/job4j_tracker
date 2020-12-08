package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * BaseTracker.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 2
 * @since 21.11.2020
 */
public abstract class BaseTracker implements Tracker {
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
    //private final List<Item> items = new ArrayList<>();

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
            String query = "insert into items(id_item, name, description, created_time)" +
                    "values("
                    + "'" + item.getId() + "',"
                    + "'" + item.getName() + "',"
                    + "'" + item.getDesc() + "',"
                    + "'" + item.getTime() + "'"
                    + ");"
                    ;
            this.updQuery(query);
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
        String query = "UPDATE items SET (name, description, created_time)" +
                "values("
                + "'" + item.getName() + "',"
                + "'" + item.getDesc() + "',"
                + "'" + item.getTime() + "'"
                + ") where id_item = " + id + ";"
                ;
        int res = updQuery(query);
        return res == 1;

    }

    /**
     * Delete Item by ID.
     *
     * @param id ID
     * @return if success
     */
    public boolean delete(String id) {
        String query = "DELETE FROM items WHERE id_item LIKE " + "'" + id + "';";
        int res = updQuery(query);
        return res == 1;
    }

    /**
     * Shows all Items.
     *
     * @return List<Item> All Items.
     */
    public List<Item> findAll() {
        String query = "select * from items;";
        return execQuery(query);
    }

    /**
     * Find Items by key.
     *
     * @param key Searching key.
     * @return List<Item> list.
     */
    public List<Item> findByName(String key) {
        String query = "SELECT * FROM items WHERE name LIKE " + "'%" + key + "%';";
        return execQuery(query);
    }

    /**
     * Find Item by ID.
     *
     * @param id ID.
     */
    public Item findById(String id) {
        String query = "SELECT * FROM items WHERE id_item LIKE " + "'" + id + "';";
        List<Item> list = execQuery(query);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Returns List of found by query.
     *
     * @param query Query.
     * @return List.
     */
    private List<Item> execQuery(String query) {
        List<Item> list = new ArrayList<>();
        try (Statement stmt = cn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("ID_ITEM");
                String name = rs.getString("NAME");
                String desc = rs.getString("DESCRIPTION");
                String time = rs.getString("CREATED_TIME");
                Item item = new Item(name, desc);
                item.setId(id);
                item.setTime(time);
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Update DB using query.
     *
     * @param query Query.
     * @return number of rows updated.
     */
    private int updQuery(String query) {
        int res = 0;
        try (Statement stmt = cn.createStatement()) {
           res = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
