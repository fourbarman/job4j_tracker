package ru.job4j.tracker;

/**
 * GetAllAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public class GetAllAction extends BaseAction implements UserAction {

    /**
     * Constructor
     *
     * @param name Action name.
     */
    public GetAllAction(String name) {
        super(name);
    }

    /**
     * Shows all Items.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     * @return True.
     */
    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println("------------ All items ------------");
        for (Item item : tracker.findAll()) {
            System.out.println(item.toString());
        }
        return true;
    }
}
