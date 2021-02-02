package hackerRank.fullSolution.entity;

import java.io.Serializable;

public class File implements FileEntity, Serializable {
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
