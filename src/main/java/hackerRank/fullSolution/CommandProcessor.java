package hackerRank.fullSolution;

import hackerRank.fullSolution.command.Command;
import hackerRank.fullSolution.command.execution.CommandExecutor;
import hackerRank.fullSolution.command.factory.CommandFactory;
import hackerRank.fullSolution.entity.Directory;
import hackerRank.fullSolution.entity.FileSystem;
import hackerRank.fullSolution.utils.FilePersistor;

import java.util.Optional;
import java.util.Scanner;

public class CommandProcessor {
    private static final String INITIAL_STATE_FILENAME = "initialState.cpr";
    public static final String PERSISTING_THE_FILE_SYSTEM_ERROR_MESSAGE = "An issue has occurred while persisting the File System";
    private FileSystem fileSystem;
    private final CommandFactory commandFactory;
    private final CommandExecutor commandExecutor;
    private final FilePersistor filePersistor;

    public CommandProcessor() {
        this.filePersistor = FilePersistor.getInstance();
        this.commandFactory = new CommandFactory();
        this.commandExecutor = new CommandExecutor();
        restoreOrCreateFileSystem();
    }

    private void restoreOrCreateFileSystem() {
        Optional<Object> optionalFile = FilePersistor.getInstance().readObjectFromDisk(INITIAL_STATE_FILENAME);

        if (optionalFile.isPresent()) {
            this.fileSystem = (FileSystem) optionalFile.get();
        } else {
            Directory root = new Directory("root", null);
            this.fileSystem = new FileSystem(root);
        }
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
        persistFileSystem();
    }

    private void persistFileSystem() {
        try {
            this.filePersistor.saveObjectToDisk(INITIAL_STATE_FILENAME, this.fileSystem);
        } catch (Exception e) {
            System.out.println(PERSISTING_THE_FILE_SYSTEM_ERROR_MESSAGE + "\n" + e.getMessage());
        }
    }
}
