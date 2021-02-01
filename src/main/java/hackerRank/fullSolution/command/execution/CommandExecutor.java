package hackerRank.fullSolution.command.execution;

import hackerRank.fullSolution.entity.FileSystem;
import hackerRank.fullSolution.command.Command;

public class CommandExecutor {
    public String execute(Command command, FileSystem fs) {
        String output = command.execute(fs);
        return output == null || output.isEmpty() ? output : output + "\n";
    }
}
