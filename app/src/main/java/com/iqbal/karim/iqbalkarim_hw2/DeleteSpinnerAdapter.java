package com.iqbal.karim.iqbalkarim_hw2;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DeleteSpinnerAdapter extends ArrayAdapter<Phone> {
    private Context context;
    private int layoutResourceId;
    private LayoutInflater inflator;
    private ArrayList<Phone> phones;

    private boolean flag=false;

    public DeleteSpinnerAdapter(Context baseContext, int histplacesspinner_layout) {
        super(baseContext, histplacesspinner_layout, Commons.phones);
        this.context = baseContext;
        this.layoutResourceId = histplacesspinner_layout;
        this.phones = Commons.phones;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position,  View convertView,  ViewGroup parent) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        View rowView = convertView;

        if(rowView == null){
            inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflator.inflate(layoutResourceId,parent,false);
        }

        ConstraintLayout layout = rowView.findViewById(R.id.itemLayout);
        if (position % 2 == 0) layout.setBackgroundColor(000000) ;

        ImageView cardImage = rowView.findViewById(R.id.spImagePhone);
        TextView tvName = rowView.findViewById(R.id.spPhoneName);
        TextView tvPrice = rowView.findViewById(R.id.spPrice);
        TextView tvStorage = rowView.findViewById(R.id.spStorage);
        TextView tvMemory = rowView.findViewById(R.id.spMemory);

        cardImage.setImageBitmap(BitmapFactory.decodeByteArray(this.phones.get(position).getImage(), 0, this.phones.get(position).getImage().length));

        tvName.setText(this.phones.get(position).getName());
        tvPrice.setText(formatter.format(this.phones.get(position).getPrice()) + " TL");
        tvStorage.setText("Storage: "+this.phones.get(position).getStorage() + " GB");
        tvMemory.setText("Memory:" +this.phones.get(position).getMemory() + " GB");

        return rowView;
    }
}
