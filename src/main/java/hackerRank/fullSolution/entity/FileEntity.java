package hackerRank.fullSolution.entity;

public interface FileEntity {
    String getName();

    Directory getParent();

    FileEntityType getEntityType();
}
