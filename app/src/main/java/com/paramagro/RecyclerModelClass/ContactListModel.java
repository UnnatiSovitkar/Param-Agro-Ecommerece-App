package com.paramagro.RecyclerModelClass;

public class ContactListModel {

    private String name,mobno;

    public ContactListModel(String name, String mobno) {
        this.name = name;
        this.mobno = mobno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }
}
