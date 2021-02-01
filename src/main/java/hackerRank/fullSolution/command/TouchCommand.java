package hackerRank.fullSolution.command;

import hackerRank.fullSolution.entity.FileSystem;

public class TouchCommand implements Command {
    private String argument;
    private static final String ERROR_MESSAGE_INVALID_NAME = "Invalid File or Folder Name";
    private static final String ERROR_MESSAGE_FILE_ALREADY_EXISTS = "File already exists";

    public TouchCommand(String argument) {
        if (argument.length() > 100) {
            throw new RuntimeException(ERROR_MESSAGE_INVALID_NAME);
        }
        this.argument = argument;
    }

    public String execute(FileSystem fs) {
        Boolean success = fs.getCurrent().createFile(argument);
        return (success) ? "" : ERROR_MESSAGE_FILE_ALREADY_EXISTS;
    }
}
