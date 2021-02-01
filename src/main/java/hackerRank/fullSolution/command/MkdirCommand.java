package hackerRank.fullSolution.command;

import hackerRank.fullSolution.entity.FileSystem;

public class MkdirCommand implements Command {
    private String argument;
    private static final String ERROR_MESSAGE_INVALID_NAME = "Invalid File or Folder Name";
    private static final String ERROR_MESSAGE_DIRECTORY_ALREADY_EXISTS = "Directory already exists";

    public MkdirCommand(String argument) {
        if (argument.length() > 100) {
            throw new RuntimeException(ERROR_MESSAGE_INVALID_NAME);
        }
        this.argument = argument;
    }

    public String execute(FileSystem fs) {
        Boolean success = fs.getCurrent().createSubdirectory(argument);
        return (success) ? "" : ERROR_MESSAGE_DIRECTORY_ALREADY_EXISTS;
    }
}