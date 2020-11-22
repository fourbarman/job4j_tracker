package ru.job4j.tracker;

/**
 * Input.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public interface Input {
    /**
     * returns String value
     */
    String ask(String question);

    /**
     * returns int value
     */
    int askInt(String question);

    /**
     * checks menu value
     */
    int askInt(String question, int max);
}
