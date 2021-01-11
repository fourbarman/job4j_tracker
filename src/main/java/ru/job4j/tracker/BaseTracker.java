package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * BaseTracker.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 3
 * @since 11.01.2021
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
     * Adds Item to storage.
     *
     * @param item New Item.
     * @return Item.
     */
    public Item add(Item item) {
        int affRows;
        if (item != null) {
            //item.setId(this.generateId());
            String addQuery = "INSERT INTO ITEMS (name, description, created_time) VALUES (?, ? ,?)";
            try (PreparedStatement ps = cn.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS)) {
                //ps.setString(1, item.getId());
                ps.setString(1, item.getName());
                ps.setString(2, item.getDesc());
                ps.setObject(3, Timestamp.from(item.getTime()));

                affRows = ps.executeUpdate();
                if (affRows > 0) {
                    ResultSet keys = ps.getGeneratedKeys();
                    keys.next();
                    item.setId(keys.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    /**
     * Replace Item by ID.
     *
     * @param id   ID.
     * @param item New Item.
     */
    public boolean replace(String id, Item item) {
        int res = 0;
        Integer intId = parseStringToInteger(id);
        //int_id = parseStringToInteger(id);
        if (intId != null && item != null) {
            String replaceQuery = "UPDATE ITEMS SET name = ?, description = ?, created_time = ?  WHERE id = ?";
            try (PreparedStatement ps = cn.prepareStatement(replaceQuery)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getDesc());
                ps.setObject(3, Timestamp.from(item.getTime()));
                ps.setInt(4, intId);
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
        Integer intId = parseStringToInteger(id);
        if (intId != null) {
            String deleteQuery = "DELETE FROM ITEMS WHERE id = ?";
            try (PreparedStatement ps = cn.prepareStatement(deleteQuery)) {
                ps.setInt(1, intId);
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
        Item item = null;
        Integer intId = parseStringToInteger(id);
        if (intId != null) {
            String query = "SELECT * FROM items WHERE id = " + "'" + id + "'";
            List<Item> list = execQuery(query);
            if (!list.isEmpty()) {
                item = list.get(0);
            }
        }
        return item;
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
                String id = rs.getString("ID");
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

    /**
     * Parse String to Integer.
     * Returns null if NFE thrown.
     *
     * @param s String string.
     * @return Integer value.
     */
    private Integer parseStringToInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
