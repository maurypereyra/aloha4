package hackerRank.fullSolution.command;

import hackerRank.fullSolution.entity.FileSystem;

public class PwdCommand implements Command {

    public PwdCommand() {}

    public String execute(FileSystem fs) {
        return fs.getCurrent().getFullPath();
    }
}