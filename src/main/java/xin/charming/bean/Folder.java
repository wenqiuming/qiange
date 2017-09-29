package xin.charming.bean;

public class Folder {
    private Integer id;

    private String fname;

    private Integer uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Folder(String fname, Integer uid) {
        this.fname = fname;
        this.uid = uid;
    }

    public Folder() {
    }
}