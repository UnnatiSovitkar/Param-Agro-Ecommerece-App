package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.PikmahitiDetailModel;
import com.example.param.R;
import com.paramagro.param.SubitemDetailPikmahitiActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class PikMahitiDetailListAdapter extends RecyclerView.Adapter<PikMahitiDetailListAdapter.ViewHolder> {

    Context context;
    List<PikmahitiDetailModel> list;

    public PikMahitiDetailListAdapter(Context context, List<PikmahitiDetailModel> list) {
        this.context = context;
        this.list = list;
    }

    //search bar//
    public void setFilteredList(List<PikmahitiDetailModel> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PikMahitiDetailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_pikmahiti_subitem_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PikMahitiDetailListAdapter.ViewHolder holder, int position) {

        holder.title.setText(list.get(position).getName());

        String id=list.get(position).getId();
        String title=list.get(position).getName();
        String desc=list.get(position).getDesc();
        String timedate=list.get(position).getDate_time();
        String image=list.get(position).getImg();

        //removing html tags
        String result = Html.fromHtml(desc).toString();

        Glide.with(context)
                .load(list.get(position).getImg())
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.img);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), SubitemDetailPikmahitiActivity.class);
                i.putExtra("id",id);
                i.putExtra("title",title);
                i.putExtra("desc",result);
                i.putExtra("img",image);
                i.putExtra("date",timedate);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,desc;
        ImageView img;
        Button cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.subitem_title);
            img=itemView.findViewById(R.id.subitem_img);
            cardView=itemView.findViewById(R.id.cardview_subitem);


        }
    }
}

