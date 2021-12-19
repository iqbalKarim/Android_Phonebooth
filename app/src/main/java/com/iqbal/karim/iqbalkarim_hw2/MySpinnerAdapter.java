package com.iqbal.karim.iqbalkarim_hw2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;


public class MySpinnerAdapter extends ArrayAdapter<CreditCard> {
    private Context context;
    private int layoutResourceId;
    private LayoutInflater inflator;
    private ArrayList<CreditCard>  cards;

    private boolean flag=false;

    public MySpinnerAdapter(Context baseContext, int histplacesspinner_layout, ArrayList<CreditCard> cards) {
        super(baseContext, histplacesspinner_layout, cards);
        this.context = baseContext;
        this.layoutResourceId = histplacesspinner_layout;
        this.cards = cards;
    }

    @Override
    public View getDropDownView(int position,  View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position,  View convertView,  ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflator.inflate(layoutResourceId,parent,false);
        }

        ConstraintLayout layout = rowView.findViewById(R.id.itemLayout);
        if (position % 2 == 0) layout.setBackgroundColor(000000) ;

        ImageView cardImage = rowView.findViewById(R.id.spImagePhone);
        TextView tvCardholder = rowView.findViewById(R.id.spPhoneName);
        TextView tvCardNum = rowView.findViewById(R.id.spPrice);
        TextView tvCVC = rowView.findViewById(R.id.spStorage);
        TextView tvExpiry = rowView.findViewById(R.id.spMemory);

        cardImage.setImageResource(R.drawable.creditcard);
        tvCardholder.setText(this.cards.get(position).getCardholderName());
        tvCardNum.setText(this.cards.get(position).getCardNumber());
        tvCVC.setText(this.cards.get(position).getCvc());
        tvExpiry.setText(this.cards.get(position).getCardExpiry());

        return rowView;
    }

}
