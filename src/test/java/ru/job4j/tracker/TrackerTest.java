package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
    List<Item> itemList;

    @Before
    public void setVar() {
        tracker = new TrackerNotSingle();
        item1 = new Item("firstItem", "firstDescription");
        item2 = new Item("secondItem", "secondDescription");
        item3 = new Item("thirdItem", "thirdDescription");
        item4 = new Item("fourthItem", "fourthDescription");
        item5 = new Item("fifthItem", "fifthDescription");
        newItem = new Item("newItem", "newDescription");
        itemList = List.of(item1, item2, item3, item4, item5);
    }

    /**
     * Test FindAll.
     * When storage is empty.
     */
    @Test
    public void findAllWhenStorageIsEmpty() {
        assertTrue(tracker.findAll().isEmpty());
    }

    /**
     * Test FindAll.
     * When add 1 item to storage than storage contain that item.
     */
    @Test
    public void whenAddOneItemThenStorageContainsThatItem() {
        tracker.add(item1);
        assertThat(tracker.findAll().size(), is(1));
        assertThat(tracker.findAll().get(0), is(item1));
    }

    /**
     * Test FindAll.
     * When storage contains five items.
     */
    @Test
    public void whenAddOneItemThenStorageContainsOneItem() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        assertThat(tracker.findAll().size(), is(5));
    }
    /////////////////////////////////////////////

    /**
     * Test add().
     * When add 5 items to storage than storage contain that items.
     */
    @Test
    public void whenAddFiveItemThenStorageContainsFiveItems() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        assertThat(tracker.findAll().size(), is(5));
        assertTrue(tracker.findAll().containsAll(itemList));
    }
/////////////////////////////////

    /**
     * Test add.
     * When add null item.
     */
    @Test
    public void whenAddNewNullItemTheStorageIsEmpty() {
        tracker.add(null);
        assertTrue(tracker.findAll().isEmpty());
    }

    /**
     * Test add.
     * When add one item than tracker contain that item.
     */
    @Test
    public void whenAddNewItemTheStorageContainsTheSameItem() {
        tracker.add(item1);
        assertThat(tracker.findAll().size(), is(1));
        assertThat(tracker.findAll().get(0).hashCode(), is(item1.hashCode()));
    }

    /**
     * Test add.
     * When add one item than tracker contain that item.
     */
    @Test
    public void whenAddThreeNewItemsTheStorageContainsSameItems() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findAll(), is(itemList));
    }
///////////////////////////////////

    /**
     * Test findById.
     * When try to find with id = null than return null.
     */
    @Test
    public void whenNotFoundByNullId() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertNull(tracker.findById(null));
    }

    /**
     * Test findById.
     * When found by id.
     */
    @Test
    public void whenFoundById() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findById(item2.getId()), is(item2));
    }

    /**
     * Test findById.
     * When not found by wrong id than return null.
     */
    @Test
    public void whenNotFoundByWrongId() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertNull(tracker.findById("testId"));
    }
//////////////////
    /**
     * Test findByName.
     * When not found by name then return null.
     */
    @Test
    public void whenNotFoundByNullNameReturnsNull() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertNull(tracker.findByName(null));
    }
    /**
     * Test findByName.
     * When found one item by unique name.
     */
    @Test
    public void whenFoundByNameThenReturnOneItem() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findByName(item2.getName()).size(), is(1));
        assertThat(tracker.findByName(item2.getName()).get(0), is(item2));
    }
    /**
     * Test findByName.
     * When found five items by name.
     */
    @Test
    public void whenFoundBySameNameThenReturnSameItems() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        assertThat(tracker.findByName("Item").size(), is(5));
    }
    /**
     * Test findByName.
     * When not found by name then return null.
     */
    @Test
    public void whenNotFoundByWrongNameReturnsNull() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertNull(tracker.findByName("testName"));
    }
///////////////////////////////
    /**
     * Test delete.
     * When delete one item from tracker with 3 items than success and 2 items left.
     */
    @Test
    public void whenDeleteItemWithNullId() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.delete(null), is(false));
        //assertThat(tracker.findAll().size(), is(3));
    }
    /**
     * Test delete.
     * When delete one item from tracker with 3 items than success and 2 items left.
     */
    @Test
    public void whenDeleteItemThenSuccess() {
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.delete(item2.getId()), is(true));
        assertThat(tracker.findAll().size(), is(2));
    }
//
//    @Test
//    public void whenDeleteItemThenStorageDoesNotContainIt() {
//        tracker.add(item1);
//        tracker.add(item2);
//        tracker.add(item3);
//        tracker.delete(item2.getId());
//        assertThat(tracker.findAll().length, is(2));
//    }
//
//    @Test
//    public void whenDeleteItemThenMoveNextItemOnDeletedPlace() {
//        Item[] expected = {item1, item3, item4, item5};
//        tracker.add(item1);
//        tracker.add(item2);
//        tracker.add(item3);
//        tracker.add(item4);
//        tracker.add(item5);
//        tracker.delete(item2.getId());
//        assertThat(tracker.findAll(), is(expected));
//    }
//
//    @Test
//    public void whenDeleteItemWithWrongIdThenReturnFalse() {
//        tracker.add(item1);
//        tracker.add(item2);
//        tracker.add(item3);
//        assertThat(tracker.delete("wrong id"), is(false));
//    }
//
//    @Test
//    public void whenDeleteItemWithWrongIdThenStorageNotChanged() {
//        tracker.add(item1);
//        tracker.add(item2);
//        tracker.add(item3);
//        Item[] expected = tracker.findAll();
//        tracker.delete("wrong id");
//        assertThat(tracker.findAll(), is(expected));
//    }
//
//    /**
//     * Test replace.
//     */
//    @Test
//    public void whenReplaceItemThenStorageHasSameNumberOfItems() {
//        tracker.add(item1);
//        tracker.add(item2);
//        tracker.add(item3);
//        tracker.replace(item2.getId(), newItem);
//        assertThat(tracker.findAll().length, is(3));
//    }
//
//    @Test
//    public void whenReplaceItemThenSuccess() {
//        tracker.add(item1);
//        tracker.add(item2);
//        tracker.add(item3);
//        assertThat(tracker.replace(item2.getId(), newItem), is(true));
//    }
//
//    @Test
//    public void whenReplaceItemThenNewItemOnTheReplacedPosition() {
//        newItem.setId(item2.getId());
//        tracker.add(item1);
//        tracker.add(item2);
//        tracker.add(item3);
//        tracker.replace(item2.getId(), newItem);
//        assertThat(tracker.findById(item2.getId()).getName(), is("newItem"));
//    }
}
