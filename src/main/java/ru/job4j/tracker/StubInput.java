package ru.job4j.tracker;

/**
 * StubInput.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020.
 */
public class StubInput implements Input {
    /**
     * Answers storage.
     */
    private String[] answers;
    /**
     * Start position.
     */
    private int position = 0;

    /**
     * Constructor.
     *
     * @param answers Answers storage.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     * Returns next string.
     *
     * @param question Question.
     * @return Next string
     */
    @Override
    public String ask(String question) {
        return this.answers[this.position++];
    }

    /**
     * Returns int value.
     *
     * @param question Question.
     * @return Int value.
     */
    @Override
    public int askInt(String question) {
        return Integer.valueOf(ask(question));
    }

    /**
     * Checks menu value.
     *
     * @param question Question.
     * @param max      Number of menu options.
     * @return Menu value.
     */
    @Override
    public int askInt(String question, int max) {
        return askInt(question);
    }
}


