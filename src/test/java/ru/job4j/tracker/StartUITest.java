package ru.job4j.tracker;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version $Id$
 * @since 21.11.2020.
 */
public class StartUITest {
    TrackerNotSingle tracker;
    Item item1, item2, item3, item4, item5;
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setVar() {
        tracker = new TrackerNotSingle();
        item1 = new Item("firstItem", "firstDescription");
        item2 = new Item("secondItem", "secondDescription");
        item3 = new Item("thirdItem", "thirdDescription");
        item4 = new Item("fourthItem", "fourthDescription");
        item5 = new Item("fifthItem", "fifthDescription");
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void loadStdOutput() {
        System.setOut(new PrintStream(this.stdout));
    }

    /**
     * Test showMenu
     */
    @Test
    public void whenPrtMenu() {
        StubInput input = new StubInput(
                new String[]{"0"}
        );
        StubAction action = new StubAction();
        new StartUI().init(input, new TrackerNotSingle(), new UserAction[]{action});
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("MENU")
                .add("0. Stub action")
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
    }

    /**
     * Test ExitAction
     */
    @Test
    public void whenExit() {
        StubInput input = new StubInput(
                new String[]{"0"}
        );
        StubAction action = new StubAction();
        new StartUI().init(input, new TrackerNotSingle(), new UserAction[]{action});
        assertThat(action.isCall(), is(true));
    }

    /**
     * Test getAllAction output
     */
    @Test
    public void whenGetAllActionCheckOutput() {
        StringJoiner expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("------------ All items ------------");
        GetAllAction act = new GetAllAction("Show all items");
        act.execute(new StubInput(new String[]{}), tracker);
        for (Item item : tracker.findAll()) {
            expect.add("Item's ID: " + item.getId()
                    + " Item's name: " + item.getName()
                    + " Description: " + item.getDesc()
                    + " Birth time: " + item.getTime());
        }
        assertThat(new String(out.toByteArray()), is(expect.toString()));
    }

    /**
     * Test findWithNameAction output.
     */
    @Test
    public void whenFindWithNameActionCheckOutput() {
        String key = "Item";
        StringJoiner expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("------------ Find Item By Name ------------")
                .add("Found items: ");
        FindWithNameAction fwna = new FindWithNameAction("Find items by name");
        fwna.execute(new StubInput(new String[]{key}), tracker);
        for (Item item : tracker.findByName(key)) {
            expect.add("Item's ID: " + item.getId()
                    + " Item's name: " + item.getName()
                    + " Description: " + item.getDesc()
                    + " Birth time: " + item.getTime());
        }
        assertThat(new String(out.toByteArray()), is(expect.toString()));
    }

    /**
     * Test createAction output.
     */
    @Test
    public void whenCreateActionCheckOutput() {
        StringJoiner expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("------------ Add new Item ------------")
                .add("------------ New item added ------------");
        CreateAction ca = new CreateAction("Add new item");
        ca.execute(new StubInput(new String[]{"new", "new"}), tracker);
        expect.add("Item's ID: " + tracker.findAll()[tracker.findAll().length - 1].getId()
                + " Item's name: " + tracker.findAll()[tracker.findAll().length - 1].getName()
                + " Description: " + tracker.findAll()[tracker.findAll().length - 1].getDesc()
                + " Birth time: " + tracker.findAll()[tracker.findAll().length - 1].getTime());
        assertThat(new String(out.toByteArray()), is(expect.toString()));
    }

    /**
     * Test findWithIDAction output.
     */
    @Test
    public void whenFindWithIdActionCheckOutput() {
        String id = item3.getId();
        StringJoiner expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("------------ Find Item By ID ------------")
                .add("------------ Found item ------------");
        FindWithIdAction fwia = new FindWithIdAction("Find item by ID");
        fwia.execute(new StubInput(new String[]{id}), tracker);
        expect.add("Item's ID: " + tracker.findById(id).getId()
                + " Item's name: " + tracker.findById(id).getName()
                + " Description: " + tracker.findById(id).getDesc()
                + " Birth time: " + tracker.findById(id).getTime());
        assertThat(new String(out.toByteArray()), is(expect.toString()));
    }

    /**
     * Test replaceItemAction output.
     */
    @Test
    public void whenReplaceItemActionCheckOutput() {
        String id = item3.getId();
        StringJoiner expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("------------ Replace item ------------")
                .add("------------ Found item ------------");
        ReplaceItemAction ria = new ReplaceItemAction("Replace item");
        expect.add("Item's ID: " + tracker.findById(id).getId()
                + " Item's name: " + tracker.findById(id).getName()
                + " Description: " + tracker.findById(id).getDesc()
                + " Birth time: " + tracker.findById(id).getTime());
        ria.execute(new StubInput(new String[]{id, "new", "new"}), tracker);
        expect.add("Item's ID: " + tracker.findById(id).getId()
                + " Item's name: " + tracker.findById(id).getName()
                + " Description: " + tracker.findById(id).getDesc()
                + " Birth time: " + tracker.findById(id).getTime());
        assertThat(new String(out.toByteArray()), is(expect.toString()));
    }

    /**
     * Test DeleteAction output.
     */
    @Test
    public void whenDeleteActionCheckOutput() {
        String id = item3.getId();
        StringJoiner expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("------------ Delete Item ------------");
        DeleteItemAction dia = new DeleteItemAction("Delete item");
        dia.execute(new StubInput(new String[]{id}), tracker);
        expect.add("Item with ID " + id + " deleted!");
        assertThat(new String(out.toByteArray()), is(expect.toString()));
    }
}
