package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.CheckListModel;
import com.example.param.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    Context context;
    List<CheckListModel> itemlist;

    public UserListAdapter(Context context, List<CheckListModel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<CheckListModel> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.checklist_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {

        holder.txtid.setText(itemlist.get(position).getId());
        holder.txtname.setText(itemlist.get(position).getName());
        holder.txtmob.setText(itemlist.get(position).getMobile());
        holder.txtadd.setText(itemlist.get(position).getAddress());

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MMM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        holder.txtdate.setText(currentDateandTime);

//        holder.txtbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent j=new Intent(v.getContext(), DetailProduct.class);
////                j.putExtra("price",n);
//                j.putExtra("description",d);
//                j.putExtra("Title",t);
////                j.putExtra("newsimg",i);
//
//                v.getContext().startActivity(j);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtid,txtname,txtmob,txtadd,txtdate;

//        Button txtbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtid = itemView.findViewById(R.id.item_id);
            txtname = itemView.findViewById(R.id.item_name);
            txtmob = itemView.findViewById(R.id.item_mobile);
            txtadd=itemView.findViewById(R.id.item_addr);
            txtdate=itemView.findViewById(R.id.item_date);


        }
    }
}

