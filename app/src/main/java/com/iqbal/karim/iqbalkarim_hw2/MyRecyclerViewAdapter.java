package com.iqbal.karim.iqbalkarim_hw2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    Context context;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
    Intent intent;

    public MyRecyclerViewAdapter(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position % 2 == 0) holder.layout.setBackgroundColor(0000000);
        final Phone pho = Commons.phones.get(position);
        holder.phoneName.setText(pho.getName());
        holder.phonePrice.setText(formatter.format(pho.getPrice()) + " TL");
        holder.phoneStorage.setText("Storage: " + pho.getStorage() + " GB");
        holder.phoneMemory.setText("Memory: " + pho.getMemory() + " GB");
        Bitmap bitmap = BitmapFactory.decodeByteArray(pho.getImage(), 0, pho.getImage().length);
        holder.phoneImage.setImageBitmap(bitmap);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("phone", pho);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Commons.phones.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView phoneName, phonePrice, phoneStorage, phoneMemory;
        ImageView phoneImage;
        ConstraintLayout layout;

        MyViewHolder(View viewItem){
            super(viewItem);
            layout = viewItem.findViewById(R.id.constLayout);
            phoneName = viewItem.findViewById(R.id.phoneName);
            phoneImage = viewItem.findViewById(R.id.phoneImg);
            phonePrice = viewItem.findViewById(R.id.phonePrice);
            phoneMemory = viewItem.findViewById(R.id.phoneMemory);
            phoneStorage = viewItem.findViewById(R.id.phoneStorage);
        }
    }

}
