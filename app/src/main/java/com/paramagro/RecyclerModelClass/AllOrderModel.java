package com.paramagro.RecyclerModelClass;

public class AllOrderModel {

    private String order_id,order_no,order_amt,order_date,payment_status,order_status;

    public AllOrderModel(String order_id, String order_no, String order_amt, String order_date, String payment_status, String order_status) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.order_amt = order_amt;
        this.order_date = order_date;
        this.payment_status = payment_status;
        this.order_status = order_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_amt() {
        return order_amt;
    }

    public void setOrder_amt(String order_amt) {
        this.order_amt = order_amt;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
