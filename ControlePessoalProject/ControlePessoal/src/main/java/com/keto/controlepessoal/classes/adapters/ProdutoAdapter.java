package com.keto.controlepessoal.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.keto.controlepessoal.MainActivity;
import com.keto.controlepessoal.R;
import com.keto.controlepessoal.classes.Produto;

import java.util.ArrayList;

/**
 * Created by developer on 14/11/13.
 */
public class ProdutoAdapter extends BaseAdapter {
    private ArrayList<Produto> Lista;
    private static LayoutInflater inflater=null;

    public ProdutoAdapter(ArrayList<Produto> data) {
        Lista = data;
        inflater = (LayoutInflater) MainActivity.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return Lista.get(position).ProdutoId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.produto_item, null);

        assert vi != null;

        ((TextView)vi.findViewById(R.id.tvwDescricao)).setText(Lista.get(position).Descricao);
        ((TextView)vi.findViewById(R.id.tvwQuantidade)).setText(((Double)Lista.get(position).Quantidade).toString());

        return vi;
    }
}
