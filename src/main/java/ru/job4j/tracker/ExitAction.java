package ru.job4j.tracker;

/**
 * ExitAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public class ExitAction extends BaseAction implements UserAction {

    /**
     * Constructor
     *
     * @param name Action name.
     */
    public ExitAction(String name) {
        super(name);
    }

    /**
     * Stops program run.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     * @return False.
     */
    @Override
    public boolean execute(Input input, Tracker tracker) {
        return false;
    }
}
