package ru.job4j.tracker;

/**
 * FindWithIdAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 2.
 * @since 11.01.2021.
 */
public class FindWithIdAction extends BaseAction implements UserAction {

    /**
     * Constructor
     *
     * @param name Action name.
     */
    public FindWithIdAction(String name) {
        super(name);
    }

    /**
     * Finds Items by ID.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     * @return True.
     */
    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println("------------ Find Item By ID ------------");
        String id = input.ask("Enter item's ID: ");
        Item item = tracker.findById(id);
        if (item != null) {
            System.out.println("------------ Found item ------------");
            System.out.println(item.toString());
        } else {
            System.out.println("------------ Nothing found ------------");
        }
        return true;
    }
}
