package com.paramagro.RecyclerModelClass;

public class ProdSizeModel {

   private String pid,mrp,sell,disc,prsize;

    public ProdSizeModel(String pid, String mrp, String sell, String disc, String prsize) {
        this.pid = pid;
        this.mrp = mrp;
        this.sell = sell;
        this.disc = disc;
        this.prsize = prsize;

    }

    public String getPrsize() {
        return prsize;
    }

    public void setPrsize(String prsize) {
        this.prsize = prsize;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }
}

