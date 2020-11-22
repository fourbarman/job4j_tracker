package ru.job4j.tracker;

/**
 * UserAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public interface UserAction {
    /**
     * User action name.
     *
     * @return Name.
     */
    String name();

    /**
     * Executes user actions.
     *
     * @param input   Input.
     * @param tracker Item's storage.
     */
    boolean execute(Input input, Tracker tracker);
}
