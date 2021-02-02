package hackerRank.fullSolution.command;

import hackerRank.fullSolution.entity.FileSystem;

import java.util.Optional;

public class LsCommand implements Command {
    private Optional<String> argument;

    public LsCommand(String argument) {
        this.argument = Optional.ofNullable(argument);
    }

    public String execute(FileSystem fs) {
        String output = "";

        if (this.argument.isPresent()) {

            if (!"-r".equals(this.argument.get())) {
                throw new RuntimeException("Invalid Command");
            }
            output = fs.getCurrent().printChildren(true);
        } else {
            output = fs.getCurrent().printChildren(false);
        }

        return output;
    }
}