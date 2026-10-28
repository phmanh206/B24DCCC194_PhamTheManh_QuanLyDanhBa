package com.example.quanlydanhba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Contact> list;

    public ContactAdapter(Context context, ArrayList<Contact> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_contact, parent, false);
        }

        Contact c = list.get(position);

        TextView tvAvatar = convertView.findViewById(R.id.tvAvatar);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        TextView tvGroup = convertView.findViewById(R.id.tvGroup);
        TextView tvStar = convertView.findViewById(R.id.tvStar);

        tvAvatar.setText(c.getInitial());
        tvName.setText(c.getName());
        tvPhone.setText(c.getPhone());
        tvGroup.setText(c.getGroup());
        // Chỉ hiện dấu sao nếu là liên hệ yêu thích
        tvStar.setVisibility(c.isFavorite() ? View.VISIBLE : View.INVISIBLE);

        return convertView;
    }
}