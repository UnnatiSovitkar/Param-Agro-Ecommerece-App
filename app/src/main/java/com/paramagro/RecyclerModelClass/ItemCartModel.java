package com.paramagro.RecyclerModelClass;

public class ItemCartModel {

    String prototal;
    private String pro_id,ps_id,pro_name,pro_desc,ps_mrp_price,ps_sell_price,ps_discount,ps_size,pro_img,pro_qty,cartid;


    public ItemCartModel(String pro_id, String ps_id, String pro_name, String pro_desc, String ps_mrp_price, String ps_sell_price, String ps_discount,
                         String ps_size, String pro_img,String pro_qty,String cartid) {
        this.pro_id = pro_id;
        this.ps_id = ps_id;
        this.pro_name = pro_name;
        this.pro_desc = pro_desc;
        this.ps_mrp_price = ps_mrp_price;
        this.ps_sell_price = ps_sell_price;
        this.ps_discount = ps_discount;
        this.ps_size = ps_size;
        this.pro_img = pro_img;
        this.pro_qty = pro_qty;
        this.cartid = cartid;


    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getPro_qty() {
        return pro_qty;
    }

    public void setPro_qty(String pro_qty) {
        this.pro_qty = pro_qty;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPs_id() {
        return ps_id;
    }

    public void setPs_id(String ps_id) {
        this.ps_id = ps_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_desc() {
        return pro_desc;
    }

    public void setPro_desc(String pro_desc) {
        this.pro_desc = pro_desc;
    }

    public String getPs_mrp_price() {
        return ps_mrp_price;
    }

    public void setPs_mrp_price(String ps_mrp_price) {
        this.ps_mrp_price = ps_mrp_price;
    }

    public String getPs_sell_price() {
        return ps_sell_price;
    }

    public void setPs_sell_price(String ps_sell_price) {
        this.ps_sell_price = ps_sell_price;
    }

    public String getPs_discount() {
        return ps_discount;
    }

    public void setPs_discount(String ps_discount) {
        this.ps_discount = ps_discount;
    }

    public String getPs_size() {
        return ps_size;
    }

    public void setPs_size(String ps_size) {
        this.ps_size = ps_size;
    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }

//    @Override
//    public BigDecimal getItemPrice() {
//        return new BigDecimal(ps_sell_price);
//    }
//
//    @Override
//    public String getItemName() {
//        return pro_name;
//    }
}
