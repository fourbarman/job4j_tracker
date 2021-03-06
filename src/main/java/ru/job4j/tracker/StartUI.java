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
     * Parse number
     * @param s String number.
     * @return int.
     */
    private static int parseNumber(String s) {
        int number = 0;
        try {
            number = Integer.parseInt(s);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return number;
    }

    /**
     * Returns Tracker instance in case of args.
     * @param args Arguments.
     * @return Tracker instance.
     */
    private static Tracker getTracker(String[] args) {
        Tracker tracker = new TrackerNotSingle();
        for (int i = 0; i < args.length; i++) {
            if ("-profile".equals(args[i])) {
                if (args[i + 1].length() > 0 && args[i + 1].matches("\\d+")) {
                    tracker = new MemTracker(parseNumber(args[i + 1]));
                }
            }
        }
        return tracker;
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
        //try (Tracker tracker = new TrackerNotSingle()) {
        try (Tracker tracker = getTracker(args)) {
            tracker.init();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
