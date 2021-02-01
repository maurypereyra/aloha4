package hackerRank.fullSolution.command;

import hackerRank.fullSolution.entity.Directory;
import hackerRank.fullSolution.entity.FileSystem;

public class CdCommand implements Command {
    public static final String ERROR_MESSAGE_DIRECTORY_NOT_FOUND = "Directory not found";
    public static final String GO_UP = "..";
    public static final String ROOT = "root";
    private String[] dirNames;

    public CdCommand(String argument) {
        this.dirNames = argument.split("/");
    }

    public String execute(FileSystem fs) {
        if (GO_UP.equals(this.dirNames[0])) {
            if (!ROOT.equals(fs.getCurrent().getName()) && fs.getCurrent().getParent() != null) {
                fs.setCurrent(fs.getCurrent().getParent());
            }
        } else {
            Directory dir = (Directory) fs.getCurrent().getSubDir(dirNames);
            if (dir != null) {
                fs.setCurrent(dir);
            } else {
                return ERROR_MESSAGE_DIRECTORY_NOT_FOUND;
            }
        }
        return "";
    }
}
