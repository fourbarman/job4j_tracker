package ru.job4j.tracker;

/**
 * BaseAction.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public abstract class BaseAction implements UserAction {
    /**
     * Action name field.
     */
    private final String name;

    /**
     * Constructor
     *
     * @param name action name.
     */
    protected BaseAction(String name) {
        this.name = name;
    }

    /**
     * Returns action name.
     *
     * @return name.
     */
    @Override
    public String name() {
        return this.name;
    }
}
