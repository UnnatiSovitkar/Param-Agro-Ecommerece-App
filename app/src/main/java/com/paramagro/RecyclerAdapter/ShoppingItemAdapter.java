package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.ProductdModel;
import com.paramagro.param.DetailProduct;
import com.example.param.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> {

    Context context;
    List<ProductdModel> itemlist;

    public ShoppingItemAdapter(Context context, List<ProductdModel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<ProductdModel> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public ShoppingItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingItemAdapter.ViewHolder holder, int position) {

        holder.txttitle.setText(itemlist.get(position).getProname());

//        holder.total.setText("\u20B9"+itemlist.get(position).getProdMrp());
        holder.offerper.setText(itemlist.get(position).getDisc()+"%");
        holder.txprice.setText("\u20B9"+itemlist.get(position).getProdSellp()+"/-");




        //            /for cross line or stroke on price or text////////////
        SpannableString spannableString=new SpannableString("\u20B9"+itemlist.get(position).getProdMrp()+"/-");

        Log.d("snap",spannableString.toString());

        StrikethroughSpan strikethroughSpan=new StrikethroughSpan();

        spannableString.setSpan(strikethroughSpan,0,spannableString.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(strikethroughSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);///stroke on text from 0 to 4 charecters
        holder.total.setText(spannableString);
        holder.kg.setText(itemlist.get(position).getPsize());

        String id=itemlist.get(position).getProid();
        String name=itemlist.get(position).getProname();
        String desc=itemlist.get(position).getProdesc();
        String psid=itemlist.get(position).getPsid();
        String psize=itemlist.get(position).getPsize();
        String img=itemlist.get(position).getPimg();
        String total=itemlist.get(position).getProdMrp();
        String sellp=itemlist.get(position).getProdSellp();
        String disc=itemlist.get(position).getDisc();



        //load image from the internet and set it into imgview using glide

        Glide.with(context)
                .load(itemlist.get(position).getPimg())
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(holder.txtimg);

        holder.txtcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(v.getContext(), DetailProduct.class);
//                j.putExtra("price",n);
                j.putExtra("psid",psid);//transfer of data through intent
                j.putExtra("id",id);//transfer of data through intent
                j.putExtra("psize",psize);//transfer of data through intent
                j.putExtra("description",desc);//transfer of data through intent
                j.putExtra("Title",name);//transfer of data through intent
                j.putExtra("newsimg",img);//transfer of data through intent
                j.putExtra("mrp",total);//transfer of data through intent
                j.putExtra("sellpr",sellp);//transfer of data through intent
                j.putExtra("discount",disc);//transfer of data through intent

                v.getContext().startActivity(j);

            }
        });

        holder.txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(v.getContext(), DetailProduct.class);
//                j.putExtra("price",n);
                j.putExtra("psid",psid);//transfer of data through intent
                j.putExtra("id",id);//transfer of data through intent
                j.putExtra("psize",psize);//transfer of data through intent
                j.putExtra("description",desc);//transfer of data through intent
                j.putExtra("Title",name);//transfer of data through intent
                j.putExtra("newsimg",img);//transfer of data through intent
                j.putExtra("mrp",total);//transfer of data through intent
                j.putExtra("sellpr",sellp);//transfer of data through intent
                j.putExtra("discount",disc);//transfer of data through intent

                v.getContext().startActivity(j);

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txttitle, txprice,total,offerper,kg;
        ImageView txtimg;
        CardView txtcard;
        Button txtbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtimg = itemView.findViewById(R.id.imgitem);
            txttitle = itemView.findViewById(R.id.titleitem);
            txprice = itemView.findViewById(R.id.pdisc);
            txtcard=itemView.findViewById(R.id.shopbtn);
            total=itemView.findViewById(R.id.ptotal);
            offerper=itemView.findViewById(R.id.poff);
            kg=itemView.findViewById(R.id.kgunitmain);
            txtbtn=itemView.findViewById(R.id.btnclk);



        }
    }
}

