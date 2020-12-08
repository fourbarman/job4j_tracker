package ru.job4j.tracker;

/**
 * FindWithNameAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
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
        if (tracker.findByName(name) != null) {
            System.out.println("Found items: ");
            for (Item items : tracker.findByName(name)) {
                System.out.println(String.format(
                        "Item's ID: %s Item's name: %s Description: %s Birth time: %s",
                        items.getId(),
                        items.getName(),
                        items.getDesc(),
                        items.getTime())
                );
            }
        }
        return true;
    }
}
