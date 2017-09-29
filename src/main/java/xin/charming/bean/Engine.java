package xin.charming.bean;

import org.springframework.stereotype.Component;

@Component
public class Engine {
    private int id;
    private String name;
    private String url;
    private String SearchLogo;
    private int orderby;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSearchLogo() {
        return SearchLogo;
    }

    public void setSearchLogo(String searchLogo) {
        SearchLogo = searchLogo;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }
}
