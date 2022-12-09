package com.paramagro.RecyclerModelClass;

public class PikmahitiModel {

    private String id,name,desc,img;

    public PikmahitiModel(String id, String name, String desc, String img) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.img = img;
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
}
