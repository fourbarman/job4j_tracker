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
     * Adds Item to storage.
     *
     * @param item New Item.
     * @return Item.
     */
    public Item add(Item item) {
        if (item != null) {
            item.setId(this.generateId());
            String addQuery = "INSERT INTO ITEMS (id_item, name, description, created_time) VALUES (?, ?, ? ,?)";
            try (PreparedStatement ps = cn.prepareStatement(addQuery)) {
                ps.setString(1, item.getId());
                ps.setString(2, item.getName());
                ps.setString(3, item.getDesc());
                ps.setObject(4, Timestamp.from(item.getTime()));
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        int res = 0;
        if (id != null && item != null) {
            String replaceQuery = "UPDATE ITEMS SET name = ?, description = ?, created_time = ?  WHERE id_item = ?";
            try (PreparedStatement ps = cn.prepareStatement(replaceQuery)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getDesc());
                ps.setObject(3, Timestamp.from(item.getTime()));
                ps.setString(4, id);
                res = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res == 1;
    }

    /**
     * Delete Item by ID.
     *
     * @param id ID
     * @return if success
     */
    public boolean delete(String id) {
        int res = 0;
        if (id != null) {
            String deleteQuery = "DELETE FROM ITEMS WHERE id_item LIKE ?";
            try (PreparedStatement ps = cn.prepareStatement(deleteQuery)) {
                ps.setString(1, id);
                res = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res == 1;
    }

    /**
     * Shows all Items.
     *
     * @return List<Item> All Items.
     */
    public List<Item> findAll() {
        String findAllQuery = "SELECT * FROM ITEMS";
        return execQuery(findAllQuery);
    }

    /**
     * Find Items by key.
     *
     * @param key Searching key.
     * @return List<Item> list.
     */
    public List<Item> findByName(String key) {
        String findByNameQuery = "SELECT * FROM items WHERE name LIKE " + "'%" + key + "%'";
        return execQuery(findByNameQuery);
    }

    /**
     * Find Item by ID.
     *
     * @param id ID.
     */
    public Item findById(String id) {
        String query = "SELECT * FROM items WHERE id_item LIKE " + "'" + id + "';";
        List<Item> list = execQuery(query);
        return list.get(0);
    }

    /**
     * Returns List of Item objects found by query.
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
                Timestamp timestamp = rs.getObject("CREATED_TIME", Timestamp.class);
                Item item = new Item(name, desc);
                item.setId(id);
                item.setTime(timestamp.toInstant());
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
