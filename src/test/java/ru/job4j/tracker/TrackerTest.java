package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020.
 */
public class TrackerTest {
    Tracker tracker;
    Item item1, item2, item3, item4, item5, newItem;

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
     */
    @Test
    public void findAllWhenStorageIsEmpty() {
        assertThat(tracker.findAll(), emptyArray());
        //assertTrue((tracker.findAll()).isEmpty());
        //assertTrue(tracker.findAll(), empty());
    }

    @Test
    public void whenAddOneItemThenStorageContainsOneItem() {
        tracker.add(item1);
        assertThat(tracker.findAll().length, is(1));
    }

    @Test
    public void whenAddFiveItemThenStorageContainsFiveItems() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        assertThat(tracker.findAll().length, is(5));
    }

    /**
     * Test add.
     */
    @Test
    public void whenAddNewNullItemTheStorageIsEmpty() {
        tracker.add(null);
        assertThat("arr size should be equal to 0", tracker.findAll(), emptyArray());
    }

    @Test
    public void whenAddNewItemTheStorageContainsTheSameItem() {
        Item item = new Item("firstItem", "firstDescription");
        tracker.add(item);
        assertThat(tracker.findAll()[0].hashCode(), is(item.hashCode()));
    }

    @Test
    public void whenAddThreeNewItemsTheStorageContainsSameItems() {
        Item[] expected = {item1, item2, item3};
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findAll(), is(expected));
    }

    /**
     * Test findById.
     */
    @Test
    public void whenFoundById() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findById(item2.getId()), is(item2));
    }

    @Test
    public void whenNotFoundByNullId() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertNull(tracker.findById(null));
    }

    @Test
    public void whenNotFoundByWrongId() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertNull(tracker.findById("testId"));
    }

    /**
     * Test findByName.
     */
    @Test
    public void whenFoundByNameThenReturnOneItem() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findByName(item2.getName()).length, is(1));
    }

    @Test
    public void whenFoundBySameNameThenReturnSameItems() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        assertThat(tracker.findByName("Item").length, is(5));
    }

    @Test
    public void whenFoundByNameReturnTheSameItem() {
        Item[] expected = {item2};
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findByName(item2.getName()), is(expected));
    }

    @Test
    public void whenNotFoundByWrongNameReturnsEmptyArray() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findByName("testName"), emptyArray());
    }

    /**
     * Test delete.
     */
    @Test
    public void whenDeleteItemThenSuccess() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.delete(item2.getId()), is(true));
    }

    @Test
    public void whenDeleteItemThenStorageDoesNotContainIt() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.delete(item2.getId());
        assertThat(tracker.findAll().length, is(2));
    }

    @Test
    public void whenDeleteItemThenMoveNextItemOnDeletedPlace() {
        Item[] expected = {item1, item3, item4, item5};
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        tracker.delete(item2.getId());
        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void whenDeleteItemWithWrongIdThenReturnFalse() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.delete("wrong id"), is(false));
    }

    @Test
    public void whenDeleteItemWithWrongIdThenStorageNotChanged() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        Item[] expected = tracker.findAll();
        tracker.delete("wrong id");
        assertThat(tracker.findAll(), is(expected));
    }

    /**
     * Test replace.
     */
    @Test
    public void whenReplaceItemThenStorageHasSameNumberOfItems() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.replace(item2.getId(), newItem);
        assertThat(tracker.findAll().length, is(3));
    }

    @Test
    public void whenReplaceItemThenSuccess() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.replace(item2.getId(), newItem), is(true));
    }

    @Test
    public void whenReplaceItemThenNewItemOnTheReplacedPosition() {
        newItem.setId(item2.getId());
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.replace(item2.getId(), newItem);
        assertThat(tracker.findById(item2.getId()).getName(), is("newItem"));
    }
}
