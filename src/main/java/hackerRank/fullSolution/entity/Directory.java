package hackerRank.fullSolution.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Directory implements FileEntity, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String ERROR_MESSAGE_INVALID_PATH = "Invalid path";
    private static final String SLASH = "/";
    private final String name;
    private final Directory parent;
    private final Map<String, FileEntity> children = new HashMap<>();

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

    public FileEntity getSubDir(String[] dirNames) {
        FileEntity current = this;

        for (String dirName : dirNames) {
            FileEntity child = ((Directory) current).getSubDir(dirName);

            if (child == null || child.getEntityType() == FileEntityType.File) {
                throw new RuntimeException(ERROR_MESSAGE_INVALID_PATH);
            } else {
                current = child;
            }
        }
        return current;
    }

    public FileEntity getSubDir(String dirName) {
        return this.children.getOrDefault(dirName, null);
    }

    public String printChildren(boolean recursive) {
        StringBuilder sb = this.printRecursive(recursive, new StringBuilder());

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    private StringBuilder printRecursive(boolean recursive, StringBuilder sb) {
        List<Directory> childrenDirectoriesToPrint = new ArrayList<>();
        if (recursive && !children.isEmpty()) {
            sb.append(this.getFullPath() + "\n");
        }

        for (Map.Entry<String, FileEntity> child : children.entrySet()) {
            sb.append(child.getKey() + "\n");

            if (recursive && child.getValue().getEntityType() == FileEntityType.Directory) {
                childrenDirectoriesToPrint.add((Directory) child.getValue());
            }
        }

        for (Directory dir : childrenDirectoriesToPrint) {
            dir.printRecursive(true, sb);
        }

        return sb;
    }
}