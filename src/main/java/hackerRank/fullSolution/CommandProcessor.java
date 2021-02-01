package hackerRank.fullSolution;

import hackerRank.fullSolution.command.Command;
import hackerRank.fullSolution.command.execution.CommandExecutor;
import hackerRank.fullSolution.command.factory.CommandFactory;
import hackerRank.fullSolution.entity.Directory;
import hackerRank.fullSolution.entity.FileSystem;

import java.util.Scanner;

public class CommandProcessor {
    private final FileSystem fileSystem;
    private final CommandFactory commandFactory;
    private final CommandExecutor commandExecutor;

    public CommandProcessor() {
        this.fileSystem = new FileSystem(new Directory("root", null));
        this.commandFactory = new CommandFactory();
        this.commandExecutor = new CommandExecutor();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        String commandAsText = sc.nextLine();

        while (!commandAsText.equalsIgnoreCase("quit")) {
            try {
                Command command = this.commandFactory.buildCommand(commandAsText);
                String output = this.commandExecutor.execute(command, this.fileSystem);
                System.out.print(output);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            commandAsText = sc.nextLine();
        }
    }
}
