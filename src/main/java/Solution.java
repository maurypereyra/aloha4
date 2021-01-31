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
        private Map<String, FileEntity> children = new HashMap<>();

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
            String fullPath = "";
            if (this.getParent() == null) {
                fullPath = SLASH + this.name;
            } else {
                List<String> directories = new ArrayList<>();
                Directory current = this;
                fillDirectoriesList(directories, current);
                fullPath = SLASH + String.join(SLASH, directories);
            }

            return fullPath;
        }

        private void fillDirectoriesList(List<String> directories, Directory current) {
            while (current != null) {
                directories.add(current.getName());
                current = current.getParent();
            }

            Collections.reverse(directories);
        }

        public Boolean createSubdirectory(String dirName) {
            if (this.containsChild(dirName)) {
                return false;
            }

            Directory newDir = new Directory(dirName, this);
            return children.put(dirName, newDir) == null;
        }

        private Boolean containsChild(String childName) {
            return children.containsKey(childName);
        }

        public Boolean createFile(String fileName) {
            if (this.containsChild(fileName)) {
                return false;
            }

            File file = new File(fileName, this);
            return children.put(fileName, file) == null;
        }

    }

    static class File implements FileEntity {
        private static final long serialVersionUID = 1L;

        private String name;
        private Directory parent;

        public File(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
        }

        @Override
        public Directory getParent() {
            return this.parent;
        }

        @Override
        public FileEntityType getEntityType() {
            return FileEntityType.File;
        }

        @Override
        public String getName() {
            return this.name;
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
    static class TouchCommand implements Command {
        private String argument;
        private static final String ERROR_MESSAGE_INVALID_NAME = "Invalid File or Folder Name";
        private static final String ERROR_MESSAGE_FILE_ALREDY_EXISTS = "File already exists";

        public TouchCommand(String argument) {
            if (argument.length() > 100) {
                throw new RuntimeException(ERROR_MESSAGE_INVALID_NAME);
            }
            this.argument = argument;
        }

        public String execute(FileSystem fs) {
            Boolean success = fs.getCurrent().createFile(argument);
            return (success) ? "" : ERROR_MESSAGE_FILE_ALREDY_EXISTS;
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