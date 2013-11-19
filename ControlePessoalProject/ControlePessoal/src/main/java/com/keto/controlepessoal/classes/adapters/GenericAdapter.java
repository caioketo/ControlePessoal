package com.keto.controlepessoal.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.keto.controlepessoal.MainActivity;
import com.keto.controlepessoal.classes.ClasseBase;

import java.util.ArrayList;

/**
 * Created by developer on 19/11/13.
 */
public class GenericAdapter extends BaseAdapter {
    private ArrayList<? extends ClasseBase> Lista;
    private static LayoutInflater inflater=null;
    private int[] Ids;
    private String[] Props;
    private int LayoutId;

    public GenericAdapter(ArrayList<? extends ClasseBase> data, int[] ids, String[] props, int layout) {
        Lista = data;
        inflater = (LayoutInflater) MainActivity.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Ids = ids;
        Props = props;
        LayoutId = layout;
    }

    @Override
    public int getCount() {
        return Lista.size();
    }

    @Override
    public Object getItem(int position) {
        return Lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (Long)Lista.get(position).get("ID");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(LayoutId, null);

        assert vi != null;

        for (int i = 0; i < Ids.length; i++) {
            ((TextView)vi.findViewById(Ids[i])).setText((String)Lista.get(position).get(Props[i]));
        }

        return vi;
    }
}