package ru.job4j.tracker;

/**
 * FindWithNameAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 2.
 * @since 11.01.2021.
 */
public class FindWithNameAction extends BaseAction implements UserAction {

    /**
     * Constructor
     *
     * @param name Action name.
     */
    public FindWithNameAction(String name) {
        super(name);
    }

    /**
     * Finds Items by name.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     * @return True.
     */
    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println("------------ Find Item By Name ------------");
        String name = input.ask("Enter item's name: ");
        //if (tracker.findByName(name) != null) {
        if (!tracker.findByName(name).isEmpty()) {
            System.out.println("Found items: ");
            for (Item item : tracker.findByName(name)) {
                System.out.println(item.toString());
            }
        } else {
            System.out.println("------------ Nothing found ------------");
        }
        return true;
    }
}
