package ru.job4j.tracker;

/**
 * DeleteItemAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 2.
 * @since 11.01.2021.
 */
public class DeleteItemAction extends BaseAction implements UserAction {

    /**
     * Constructor
     *
     * @param name Action name.
     */
    public DeleteItemAction(String name) {
        super(name);
    }

    /**
     * Deletes item from tracker.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     * @return True.
     */
    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println("------------ Delete Item ------------");
        String id = input.ask("Enter item's ID: ");
        if (tracker.findById(id) != null) {
            tracker.delete(id);
            System.out.println(String.format("Item with ID %s deleted!", id));
        } else {
            System.out.println("------------ Nothing found ------------");
        }
        return true;
    }
}
