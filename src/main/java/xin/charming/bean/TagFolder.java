package xin.charming.bean;

public class TagFolder extends Tag {
    private int folderId;
    private int tagId;
    private int orderby;

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public TagFolder() {

    }

    public TagFolder(int folderId, int tagId, int orderby) {
        this.folderId = folderId;
        this.tagId = tagId;
        this.orderby = orderby;
    }

    @Override
    public String toString() {
        return "TagFolder{" +
                "folderId=" + folderId +
                ", tagId=" + tagId +
                ", orderby=" + orderby +
                '}';
    }
}
