package com.paramagro.RecyclerModelClass;

public class PikmahitiDetailModel {

    private String id,name,desc,img,date_time;

    public PikmahitiDetailModel(String id, String name, String desc, String img, String date_time) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.img = img;
        this.date_time = date_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
