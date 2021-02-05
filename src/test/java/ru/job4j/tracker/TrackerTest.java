package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version $Id$
 * @since 05.02.2021.
 */
public class TrackerTest {
    Tracker tracker;
    Item item1, item2, item3, item4, item5, newItem;

    public Connection init() {
        try (InputStream in = TrackerNotSingle.class.getClassLoader().getResourceAsStream("apptest.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Set variables.
     */
    @Before
    public void setVar() {
        tracker = new TrackerNotSingle();
        item1 = new Item("firstItem", "firstDescription");
        item2 = new Item("secondItem", "secondDescription");
        item3 = new Item("thirdItem", "thirdDescription");
        item4 = new Item("fourthItem", "fourthDescription");
        item5 = new Item("fifthItem", "fifthDescription");
        newItem = new Item("newItem", "newDescription");
    }

    /**
     * Test FindAll.
     * When storage is empty.
     */
    @Test
    public void findAllWhenStorageIsEmpty() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            assertTrue(tracker.findAll().isEmpty());
        }
    }

    /**
     * Test FindAll.
     * When add 1 item to storage than storage contain that item.
     */
    @Test
    public void whenAddOneItemThenStorageContainsThatItem() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            assertThat(tracker.findAll().size(), is(1));
        }
    }

    /**
     * Test FindAll.
     * When storage contains five items.
     */
    @Test
    public void whenAddOneItemThenStorageContainsOneItem() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            tracker.add(item4);
            tracker.add(item5);
            assertThat(tracker.findAll().size(), is(5));
        }
    }

    /**
     * Test add.
     * When add null item.
     */
    @Test
    public void whenAddNewNullItemTheStorageIsEmpty() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            assertNull(tracker.add(null));
        }
    }

    /**
     * Test add.
     * When add one item than tracker contain that item.
     */
    @Test
    public void whenAddNewItemTheStorageContainsTheSameItem() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            assertThat(tracker.add(item1), is(item1));
        }
    }

    /**
     * Test add.
     * When add three items than tracker contain that items.
     */
    @Test
    public void whenAddThreeNewItemsTheStorageContainsSameItems() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            assertThat(tracker.add(item1), is(item1));
            assertThat(tracker.add(item2), is(item2));
            assertThat(tracker.add(item3), is(item3));
            assertThat(tracker.findAll().size(), is(3));
        }
    }

    /**
     * Test findById.
     * When try to find with id = null than return null.
     */
    @Test
    public void whenNotFoundByNullId() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertNull(tracker.findById(null));
        }
    }

    /**
     * Test findById.
     * When found by id than return item found.
     */
    @Test
    public void whenFoundById() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertThat(tracker.findById(item2.getId()).getName(), is(item2.getName()));
        }
    }

    /**
     * Test findById.
     * When not found by wrong id than return null.
     */
    @Test
    public void whenNotFoundByWrongId() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertNull(tracker.findById("testId"));
        }
    }

    /**
     * Test findByName.
     * When not found by name then return null.
     */
    @Test
    public void whenNotFoundByNullNameReturnsNull() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertTrue(tracker.findByName(null).isEmpty());
        }
    }

    /**
     * Test findByName.
     * When found one item by unique name.
     */
    @Test
    public void whenFoundByNameThenReturnOneItem() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertThat(tracker.findByName(item2.getName()).size(), is(1));
            assertThat(tracker.findByName(item2.getName()).get(0).getName(), is(item2.getName()));
        }
    }

    /**
     * Test findByName.
     * When found five items by name than tracker contains all 5.
     */
    @Test
    public void whenFoundBySameNameThenReturnSameItems() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            tracker.add(item4);
            tracker.add(item5);
            assertThat(tracker.findByName("Item").size(), is(5));
        }
    }

    /**
     * Test findByName.
     * When not found by name then return null.
     */
    @Test
    public void whenNotFoundByWrongNameReturnsNull() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertTrue(tracker.findByName("testName").isEmpty());
        }
    }

    /**
     * Test delete.
     * When delete null item from tracker with 3 items than return false and nothing deleted.
     */
    @Test
    public void whenDeleteItemWithNullId() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertFalse(tracker.delete(null));
            assertThat(tracker.findAll().size(), is(3));
        }
    }

    /**
     * Test delete.
     * When delete one item from tracker with 3 items than success and 2 items left.
     */
    @Test
    public void whenDeleteItemThenSuccess() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertTrue(tracker.delete(item2.getId()));
            assertFalse(tracker.findAll().contains(item2));
            assertThat(tracker.findAll().size(), is(2));
        }
    }

    /**
     * Test delete.
     * When delete one item from tracker wrong Id than false and all items left.
     */
    @Test
    public void whenDeleteItemWithWrongIdThenReturnFalse() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertFalse(tracker.delete("wrong id"));
            assertThat(tracker.findAll().size(), is(3));
        }
    }

    /**
     * Test replace.
     * When replace item with null Id and null Name than false and storage didn't change.
     */
    @Test
    public void whenReplaceItemWithNullId() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertFalse(tracker.replace(null, null));
            assertThat(tracker.findAll().size(), is(3));
        }
    }

    /**
     * Test replace.
     * When replace item than success and storage capacity didn't change and storage contains new item with old Id.
     */
    @Test
    public void whenReplaceItemThenStorageHasSameNumberOfItems() throws Exception {
        try (Tracker tracker = new TrackerNotSingle(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            assertTrue(tracker.replace(item2.getId(), newItem));
            assertThat(tracker.findAll().size(), is(3));
            assertThat(tracker.findById(item2.getId()).getName(), is(newItem.getName()));
        }
    }
}
