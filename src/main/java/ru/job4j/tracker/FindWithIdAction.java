package ru.job4j.tracker;

/**
 * FindWithIdAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
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
        if (tracker.findById(id) != null) {
            System.out.println("------------ Found item ------------");
            System.out.println(String.format(
                    "Item's ID: %s Item's name: %s Description: %s Birth time: %s",
                    tracker.findById(id).getId(),
                    tracker.findById(id).getName(),
                    tracker.findById(id).getDesc(),
                    tracker.findById(id).getTime())
            );
        }
        return true;
    }
}
