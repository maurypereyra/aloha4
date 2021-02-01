package hackerRank.fullSolution.entity;

import hackerRank.fullSolution.entity.Directory;

public class FileSystem {
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