package ru.job4j.tracker;

/**
 * ValidateInput.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020.
 */
public class ValidateInput implements Input {
    /**
     * Input.
     */
    private final Input input;

    /**
     * Constructor.
     *
     * @param input Input.
     */

    public ValidateInput(Input input) {
        this.input = input;
    }

    /**
     * Ask user to enter smthng and return what entered.
     *
     * @param question Ask user to enter smthng.
     * @return String entered in console.
     */
    @Override
    public String ask(String question) {
        return input.ask(question);
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
     * Checks menu value.
     * Throws IllegalStateException if not in range max.
     * Throws NumberFormatException if not a number.
     *
     * @param question Question.
     * @param max      Number of menu options.
     * @return Menu value.
     */
    @Override
    public int askInt(String question, int max) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = input.askInt(question, max);
                invalid = false;
            } catch (IllegalStateException moe) {
                System.out.println("Please select key from menu ");
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }
}
