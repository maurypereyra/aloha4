package hackerRank.fullSolution.entity;

import java.io.Serializable;

public class FileSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Directory root;
    private Directory current;

    public FileSystem(Directory root) {
        this.root = root;
        this.current = root;
    }

    public Directory getRoot() {
        return root;
    }

    public Directory getCurrent() {
        return this.current;
    }

    public void setCurrent(Directory current) {
        this.current = current;
    }
}