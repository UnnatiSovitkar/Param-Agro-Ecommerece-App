package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.ItemCartModel;
import com.example.param.R;
import com.paramagro.param.interfaces.TotalListnerInterface;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ViewHolder> {


    Context context;
    List<ItemCartModel> list;
    private TotalListnerInterface totalListnerInterface;
    TextView totalp;
    int total=0;
    public ItemCartAdapter(Context context, List<ItemCartModel> list1,TotalListnerInterface totalListnerInterface,TextView totalp) {
        list = new ArrayList<ItemCartModel>();
        this.context = context;
        this.list = list1;
        this.totalListnerInterface = totalListnerInterface;
        this.totalp=totalp;
    }

    //search bar//
    public void setFilteredList(List<ItemCartModel> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_cart_items_layout,parent,false);
        return new ViewHolder(view,totalListnerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartAdapter.ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getPro_name());
        holder.prc.setText("\u20B9"+list.get(position).getPs_sell_price()+"/-");
        holder.qty.setText("qty :"+list.get(position).getPro_qty());
        holder.size.setText(list.get(position).getPs_size());


        total= total+Integer.parseInt(list.get(position).getPs_sell_price());
        Log.d("total", String.valueOf(total));
        String cart_id= list.get(position).getCartid();


/*

        String id=list.get(position).getId();
        String title=list.get(position).getName();
        String desc=list.get(position).getDesc();
        String timedate=list.get(position).getDate_time();
        String image=list.get(position).getImg();

        //removing html tags
        String result = Html.fromHtml(desc).toString();


 */
        Glide.with(context)
                .load(list.get(position).getPro_img())
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.img);

    }
//    public void setData(List<ItemCartModel> items) {
//        this.list = items;
//        notifyDataSetChanged();
//    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,qty,prc,size;
        ImageView img,delete;


        public ViewHolder(@NonNull View itemView,TotalListnerInterface totalListnerInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.pro_name_crt);
            img=itemView.findViewById(R.id.cart_img);
            qty = itemView.findViewById(R.id.pro_qty_crt);
            prc = itemView.findViewById(R.id.pro_pr_crt);
            delete=itemView.findViewById(R.id.delete_crt);
            size=itemView.findViewById(R.id.pro_sz_crt);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    totalListnerInterface.onItemClick(getAdapterPosition());

                }
            });

        }

    }
}

