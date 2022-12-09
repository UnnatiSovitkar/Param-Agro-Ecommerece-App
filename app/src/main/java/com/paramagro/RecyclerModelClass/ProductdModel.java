package com.paramagro.RecyclerModelClass;

public class ProductdModel {

   private String proid;
   private String proname;
   private String prodesc;
   private String pimg,prodMrp,prodSellp,disc,psid,psize;

    public ProductdModel(String proid,String psid,String proname, String prodesc,String psize,String pimg,String prodMrp, String prodSellp, String disc) {
        this.proid=proid;
        this.psid=psid;
        this.proname=proname;
        this.psize=psize;
        this.prodesc=prodesc;
        this.pimg=pimg;
        this.prodMrp=prodMrp;
        this.prodSellp=prodSellp;
        this.disc=disc;
    }

    public String getPsid() {
        return psid;
    }

    public void setPsid(String psid) {
        this.psid = psid;
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize = psize;
    }

    public String getProdMrp() {
        return prodMrp;
    }

    public void setProdMrp(String prodMrp) {
        this.prodMrp = prodMrp;
    }

    public String getProdSellp() {
        return prodSellp;
    }

    public void setProdSellp(String prodSellp) {
        this.prodSellp = prodSellp;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg.trim();
    }

    public String getProid() {
        return proid;
    }

    public String getProname() {
        return proname;
    }

    public String getProdesc() {
        return prodesc;
    }

    public String getPimg() {
        return pimg;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public void setProdesc(String prodesc) {
        this.prodesc = prodesc;
    }

}