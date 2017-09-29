package xin.charming.bean;

public class Tag {

    private  int id;
    private String tname;
    private String icon;
    private String url;
    private String type;
    private  String bgColor;
    private  String topName;
    private int auth;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public Tag() {

    }

    public Tag(String tname, String icon, String url, String type, String bgColor, String topName,int auth) {
        this.tname = tname;
        this.icon = icon;
        this.url = url;
        this.type = type;
        this.bgColor = bgColor;
        this.topName = topName;
        this.auth=auth;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tname='" + tname + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", topName='" + topName + '\'' +
                ", auth=" + auth +
                '}';
    }
}
