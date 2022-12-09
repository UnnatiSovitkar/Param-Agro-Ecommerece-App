package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.AllOrderSathiModel;
import com.paramagro.param.OrderDetail;
import com.example.param.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllOrderSathiAdapter extends RecyclerView.Adapter<AllOrderSathiAdapter.ViewHolder> {


    Context context;
    List<AllOrderSathiModel> list;

    public AllOrderSathiAdapter(Context context, List<AllOrderSathiModel> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public AllOrderSathiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.allorders_sathi_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrderSathiAdapter.ViewHolder holder, int position) {

        holder.orderno.setText(list.get(position).getOrder_no());
        holder.amt.setText("\u20B9"+list.get(position).getOrder_amt()+"/-");
        String orderid=list.get(position).getOrder_id();

//        holder.date.setText(list.get(position).getOrder_date());

        String dt=list.get(position).getOrder_date();//seting textview with data coming from newsadapter
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//date format of date fetching from api
        SimpleDateFormat formatterOut = new SimpleDateFormat("dd-MM-yyyy");//we wand to diplay date in this format


        //formating date into desire one
        try {

            Date d = formatter.parse(dt);
            System.out.println(d);
            holder.date.setText(formatterOut.format(d));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        String s=list.get(position).getPayment_status();
        if(s.equals("0")){
            holder.status.setTextColor(Color.parseColor("#FF0000"));
            holder.status.setText("Failed");

        }
        if(s.equals("1")){
            holder.status.setTextColor(Color.parseColor("#18D761"));
            holder.status.setText("Success");

        }

        holder.fwrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), OrderDetail.class);

                intent.putExtra("orderid",orderid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this is apparently getting ignored... (ps: i've tried i.setFlags as well)
                v.getContext().startActivity(intent);
            }
        });


/*

        String id=list.get(position).getId();
        String title=list.get(position).getName();
        String desc=list.get(position).getDesc();
        String timedate=list.get(position).getDate_time();
        String image=list.get(position).getImg();

        //removing html tags
        String result = Html.fromHtml(desc).toString();


 */
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderno,amt,date,status;
        LinearLayout fwrd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderno = itemView.findViewById(R.id.order_no_sathi);
            amt=itemView.findViewById(R.id.amt_sathi);
            date = itemView.findViewById(R.id.date_sathi);
            status = itemView.findViewById(R.id.status_sathi);
            fwrd = itemView.findViewById(R.id.fwrdsathi);



        }

    }
}

