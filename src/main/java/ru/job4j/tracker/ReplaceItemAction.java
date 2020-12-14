package ru.job4j.tracker;

/**
 * ReplaceItemAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public class ReplaceItemAction extends BaseAction implements UserAction {

    /**
     * Constructor
     *
     * @param name Action name.
     */
    public ReplaceItemAction(String name) {
        super(name);
    }

    /**
     * Replaces Item's name, description.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     * @return True.
     */
    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println("------------ Replace item ------------");
        String id = input.ask("Enter Item's ID: ");
        Item oldItem = tracker.findById(id);
        if (oldItem != null) {
            System.out.println("------------ Found item ------------");
            System.out.println(oldItem.toString());
            String name = input.ask("Enter new item's name: ");
            String desc = input.ask("Enter new item's description: ");
            Item newItem = new Item(name, desc);
            tracker.replace(id, newItem);
            System.out.println(tracker.findById(id).toString());
        }
        return true;
    }
}
