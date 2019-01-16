package com.jasdjf.testinternet.volley;

import java.util.List;

public class VolleyJsonData {

    private List<String> children;
    //@JsonProperty("courseId")
    private int courseid;
    private int id;
    private String name;
    private int order;
    //@JsonProperty("parentChapterId")
    private int parentchapterid;
    //@JsonProperty("userControlSetTop")
    private boolean usercontrolsettop;
    private int visible;


    public void setChildren(List<String> children) {
        this.children = children;
    }
    public List<String> getChildren() {
        return children;
    }


    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }
    public int getCourseid() {
        return courseid;
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


    public void setOrder(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }


    public void setParentchapterid(int parentchapterid) {
        this.parentchapterid = parentchapterid;
    }
    public int getParentchapterid() {
        return parentchapterid;
    }


    public void setUsercontrolsettop(boolean usercontrolsettop) {
        this.usercontrolsettop = usercontrolsettop;
    }
    public boolean getUsercontrolsettop() {
        return usercontrolsettop;
    }


    public void setVisible(int visible) {
        this.visible = visible;
    }
    public int getVisible() {
        return visible;
    }

}
