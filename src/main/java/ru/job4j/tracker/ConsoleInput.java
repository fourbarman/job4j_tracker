package ru.job4j.tracker;

import java.util.Scanner;

/**
 * ConsoleInput.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 21.11.2020.
 */
public class ConsoleInput implements Input {
    /**
     * Scanner.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Ask user to enter smthng and return what entered.
     *
     * @param question Ask user to enter smthng.
     * @return String entered in console.
     */
    @Override
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    /**
     * Returns menu option.
     *
     * @param question Ask user to enter smthng.
     * @return Menu option.
     */
    @Override
    public int askInt(String question) {
        return Integer.valueOf(ask(question));
    }

    /**
     * Checks if menu value correct, otherwise throws IllegalStateException.
     *
     * @param question Ask user to enter smthng.
     * @param max      Max value of menu option.
     * @return Menu option.
     */
    @Override
    public int askInt(String question, int max) {
        int select = askInt(question);
        if (select < 0 || select >= max) {
            throw new IllegalStateException(
                    String.format("Out of about %s > [0, %s]", select, max)
            );
        }
        return select;
    }
}
