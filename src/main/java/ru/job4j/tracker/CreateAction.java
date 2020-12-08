package ru.job4j.tracker;

/**
 * CreateAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public class CreateAction extends BaseAction implements UserAction {

    /**
     * Constructor
     *
     * @param name Action name.
     */
    public CreateAction(String name) {
        super(name);
    }

    /**
     * Adds item to tracker.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     * @return True.
     */
    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println("------------ Add new Item ------------");
        String name = input.ask("Item's name: ");
        String desc = input.ask("Item's description: ");
        Item item = new Item(name, desc);
        tracker.add(item);
        System.out.println("------------ New item added ------------");
        System.out.println(String.format(
                "Item's ID: %s Item's name: %s Description: %s Birth time: %s",
                item.getId(),
                item.getName(),
                item.getDesc(),
                item.getTime())
        );
        return true;
    }
}
