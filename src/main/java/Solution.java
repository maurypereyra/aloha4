import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String args[] ) throws Exception {
        CommandProcessor commandProcessor = new CommandProcessor();
        commandProcessor.start();
    }
    static class CommandProcessor {
        private FileSystem fileSystem;
        private CommandFactory commandFactory;
        private CommandExecutor commandExecutor;

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
    static class FileSystem {
        private Directory root;
        private Directory current;

        public FileSystem(Directory root) {
            this.root = root;
            this.current = root;
        }

        public Directory getRoot() {
            return root;
        }

        public void setCurrent(Directory current) {
            this.current = current;
        }

        public Directory getCurrent() {
            return this.current;
        }
    }

    static class Directory implements FileEntity {
        private String name;
        private Directory parent;
        private Map<String, FileEntity> childs = new HashMap<>();

        private static final String SLASH = "/";

        public Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
        }

        @Override
        public Directory getParent() {
            return this.parent;
        }

        @Override
        public FileEntityType getEntityType() {
            return FileEntityType.Directory;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String getFullPath() {
            if (this.getParent() == null) {
                return SLASH + this.name;
            }
            List<String> directories = new ArrayList<>();
            Directory current = this;
            fillDirectoriesList(directories, current);

            return SLASH + String.join(SLASH, directories);
        }

        private void fillDirectoriesList(List<String> directories, Directory current) {
            while (current != null) {
                directories.add(current.getName());
                current = current.getParent();
            }

            Collections.reverse(directories);
        }
    }

    static interface FileEntity {
        String getName();

        Directory getParent();

        FileEntityType getEntityType();
    }

    enum FileEntityType {
        Directory, File
    }

    static class CommandFactory {
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
                    throw new RuntimeException("Unrecognized command");
            }
        }
    }
    static class CommandExecutor {
        public String execute(Command command, FileSystem fs) {
            return command.execute(fs);
        }
    }

    static class LsCommand implements Command {
        private String argument;

        public LsCommand(String argument) {
        }

        public String execute(FileSystem fs) {
            return "TBD";
        }
    }
    static class CdCommand implements Command {
        private String argument;

        public CdCommand(String argument) {
        }

        public String execute(FileSystem fs) {
            return "TBD";
        }
    }
    static class MkdirCommand implements Command {
        private String argument;

        public MkdirCommand(String argument) {
        }

        public String execute(FileSystem fs) {
            return "TBD";
        }
    }
    static class TouchCommand implements Command {
        private String argument;

        public TouchCommand(String argument) {
        }

        public String execute(FileSystem fs) {
            return "TBD";
        }
    }

    static interface Command {
        String execute(FileSystem fs);
    }

    static class PwdCommand implements Command {

        public PwdCommand() {}

        public String execute(FileSystem fs) {
            return fs.getCurrent().getFullPath();
        }
    }
}
