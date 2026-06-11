package com.unhas.h071241052.tugaspraktikum5;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class BahanAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listData;

    public BahanAdapter(Context context, ArrayList<String> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() { return listData.size(); }

    @Override
    public Object getItem(int position) { return listData.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bahan, parent, false);

        TextView txtNama = view.findViewById(R.id.txtNamaBahan);
        TextView txtDetail = view.findViewById(R.id.txtDetailBahan);
        LinearLayout layout = view.findViewById(R.id.layoutItem);

        String rawData = listData.get(position);
        String[] parts = rawData.split("\\|");

        if (parts.length == 4) {
            txtNama.setText(parts[0]);
            txtDetail.setText(parts[1] + " • " + parts[2]);

            if (parts[3].equalsIgnoreCase("Segera")) {
                layout.setBackgroundColor(Color.parseColor("#FFEBEE"));
                txtNama.setTextColor(Color.parseColor("#C62828"));
            } else {
                layout.setBackgroundColor(Color.parseColor("#E8F5E9"));
            }
        }
        return view;
    }
}
