package duke;

import duke.command.Command;
import duke.exception.DukeIndexInputException;
import duke.exception.DukeTaskDetailsException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.nio.file.Paths;
import java.util.Scanner;


public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    Duke() {
        this.storage = new Storage(Paths.get(".", "data"),
                                   Paths.get(".", "data", "tasks.txt"));
        this.tasks = new TaskList(this.storage.getTasks());
        this.ui = new Ui();
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }

    private void run() {
        this.ui.greet();
        Scanner sc = new Scanner(System.in);
        Boolean run = true;
        while (run && sc.hasNextLine()) {
            String input = sc.nextLine();
            System.out.println(Ui.DIVIDER_LINE);
            input = input.strip();
            String[] splitInput = input.split(" ", 2);
            Command command = Parser.parse(splitInput);
            try {
                switch (command) {
                case EXIT:
                    this.ui.exit();
                    run = false;
                    break;
                case LIST:
                    this.ui.list(this.tasks);
                    break;
                case INDEXCOMMAND:
                    Task task = this.tasks.indexCommand(splitInput);
                    this.ui.indexCommand(this.tasks.size(), task, splitInput);
                    break;
                case ADDCOMMAND:
                    Task addedTask = this.tasks.addTask(splitInput);
                    this.ui.addTask(addedTask, this.tasks.size());
                    break;
                default:
                    this.ui.unknownCommand();
                }
            } catch (DukeTaskDetailsException | DukeIndexInputException e) {
                System.out.println("\t" + e.toString());
            }
            System.out.println(Ui.DIVIDER_LINE);
            this.storage.saveTasks(this.tasks.getTasks());
        }
        sc.close();
    }

}
