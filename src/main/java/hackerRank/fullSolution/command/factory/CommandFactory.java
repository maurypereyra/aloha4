package hackerRank.fullSolution.command.factory;

import hackerRank.fullSolution.command.CdCommand;
import hackerRank.fullSolution.command.Command;
import hackerRank.fullSolution.command.LsCommand;
import hackerRank.fullSolution.command.MkdirCommand;
import hackerRank.fullSolution.command.PwdCommand;
import hackerRank.fullSolution.command.TouchCommand;

public class CommandFactory {
    public static final String ERROR_MESSAGE_UNRECOGNIZED_COMMAND = "Unrecognized command";
    public Command buildCommand(String commandAsText) {
        String[] splittedCommand = commandAsText.split(" ");
        String commandName = splittedCommand[0];
        String argument = (splittedCommand.length > 1) ? splittedCommand[1] : null;

        switch (commandName) {
            case "pwd":
                return new PwdCommand();
            case "ls":
                return new LsCommand(argument);
            case "cd":
                return new CdCommand(argument);
            case "mkdir":
                return new MkdirCommand(argument);
            case "touch":
                return new TouchCommand(argument);
            default:
                throw new RuntimeException(ERROR_MESSAGE_UNRECOGNIZED_COMMAND);
        }
    }
}
