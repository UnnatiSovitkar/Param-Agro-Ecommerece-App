package com.paramagro.param;

import android.content.Context;

import com.paramagro.RecyclerModelClass.ItemCartModel;

import java.util.ArrayList;
import java.util.List;

public class Management {
    private Context context;
    private List<ItemCartModel> itemlist=new ArrayList<>();

    public Management(Context context, List<ItemCartModel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    public double getTotal_p(){
        double fee=0;
        for(int i=0;i<itemlist.size();i++)
            fee = fee+(Double.parseDouble(itemlist.get(i).getPs_sell_price())*(Double.parseDouble(itemlist.get(i).getPro_qty())));
    return fee;

    }

}
