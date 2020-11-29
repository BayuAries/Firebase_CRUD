package com.example.firebase_crud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firebase_crud.R;
import com.example.firebase_crud.model.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();

    public void setMahasiswaList(ArrayList<Mahasiswa> mahasiswaList){
        this.mahasiswaList = mahasiswaList;
    }

    public MahasiswaAdapter(Context context){
        this.context =  context;
    }
    @Override
    public int getCount() {
        return mahasiswaList.size();
    }

    @Override
    public Object getItem(int i) {
        return mahasiswaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if(itemView == null){
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_mahasiswa, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        Mahasiswa mahasiswa = (Mahasiswa) getItem(i);
        viewHolder.bind(mahasiswa);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtName, txtMail;

        public ViewHolder(View view) {
            txtName = view.findViewById(R.id.txtName);
            txtMail = view.findViewById(R.id.txtMail);
        }

        public void bind(Mahasiswa mahasiswa) {
            txtName.setText(mahasiswa.getName());
            txtMail.setText(mahasiswa.getMail());
        }
    }
}
