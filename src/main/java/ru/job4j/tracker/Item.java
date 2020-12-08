package ru.job4j.tracker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Item.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020.
 */
public class Item {
    /**
     * Item's ID.
     */
    private String id;
    /**
     * Item's name.
     */
    private String name;
    /**
     * Item's description.
     */
    private String desc;
    /**
     * Item's create time in millis.
     */
    private String time;

    /**
     * Constructor
     *
     * @param name Item's name.
     * @param desc Item's description.
     */
    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.time = currentDateTime.format(formatter);
    }

    /**
     * Returns ID.
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns description.
     *
     * @return desc.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Set description.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Returns creation time.
     *
     * @return time.
     */
    public String getTime() {
        return time;
    }

    /**
     * Set time.
     */
    public void setTime(String time) {
        this.time = time;
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        this.time = currentDateTime.format(formatter);

//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        Date resultDate = new Date();
//        this.time = sdf.format(resultDate);
    }

    /**
     * Overrides method equals.
     *
     * @return if equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return time == item.time
                && Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(desc, item.desc);
    }

    /**
     * Overrides method hashCode.
     *
     * @return hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, time);
    }
}
