package ru.job4j.tracker;

/**
 * StartUI.
 *
 * @author fourbarman (mailto:maks.java@yandex.ru)
 * @version 1
 * @since 21.11.2020.
 */
public class StartUI {
    /**
     * Get data from user.
     */
    private Input input;
    /**
     * Items storage.
     */
    private Tracker tracker;

    /**
     * Constructor.
     *
     * @param input   Input
     * @param tracker Items storage.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Constructor
     */
    public StartUI() {
    }

    /**
     * Initialises program run while not stopped.
     *
     * @param input   Input.
     * @param tracker Items storage.
     * @param actions User actions.
     */
    public void init(Input input, Tracker tracker, UserAction[] actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ", actions.length);
            UserAction action = actions[select];
            run = action.execute(input, tracker);
        }
    }

    /**
     * Shows menu.
     *
     * @param actions User actions.
     */
    private void showMenu(UserAction[] actions) {
        System.out.println("MENU");
        for (int index = 0; index < actions.length; index++) {
            System.out.println(index + ". " + actions[index].name());
        }
    }

    /**
     * Main method.
     * Program start.
     *
     * @param args Args
     */
    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        Tracker tracker = new TrackerNotSingle();
        UserAction[] actions = {
                new CreateAction("Add new item"),
                new GetAllAction("Show all items"),
                new ReplaceItemAction("Replace item"),
                new DeleteItemAction("Delete item"),
                new FindWithIdAction("Find item by ID"),
                new FindWithNameAction("Find items by name"),
                new ExitAction("Exit")
        };
        new StartUI().init(validate, tracker, actions);
    }
}
