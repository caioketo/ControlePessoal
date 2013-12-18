package com.keto.controlepessoal.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.keto.controlepessoal.MainActivity;
import com.keto.controlepessoal.R;
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
    private View vi;
    private View.OnClickListener listener;
    private int buttonId;
    private boolean RiscaProp;

    public GenericAdapter(ArrayList<? extends ClasseBase> data, int[] ids, String[] props, int layout) {
        Lista = data;
        inflater = (LayoutInflater) MainActivity.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Ids = ids;
        Props = props;
        LayoutId = layout;
        buttonId = -1;
        listener = null;
        RiscaProp = false;
    }

    public void setRiscaProp(boolean riscaProp) {
        this.RiscaProp = riscaProp;
    }

    public void setButton(int buttonId, View.OnClickListener listener) {
        this.buttonId = buttonId;
        this.listener = listener;
    }

    public View findViewById(int id) {
        if (vi == null) {
            vi = inflater.inflate(LayoutId, null);
        }
        return vi.findViewById(id);
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
        return (Integer)Lista.get(position).get("ID");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean risca = false;
        vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(LayoutId, null);

        assert vi != null;

        if (this.RiscaProp) {
            for (int i = 0; i < Ids.length; i++) {
                Object valor = Lista.get(position).get(Props[i]);
                if (valor instanceof Boolean) {
                    risca = (Boolean)valor;
                }
            }
        }


        for (int i = 0; i < Ids.length; i++) {
            Object valor = Lista.get(position).get(Props[i]);
            if (valor != null) {
                if (vi.findViewById(Ids[i]) instanceof CheckBox) {
                    ((CheckBox)vi.findViewById(Ids[i])).setChecked((Boolean)valor);
                }
                else {
                    if (valor instanceof String) {
                        ((TextView)vi.findViewById(Ids[i])).setText((String)valor);
                    }
                    else {
                        ((TextView)vi.findViewById(Ids[i])).setText(valor.toString());
                    }
                    if (risca) {
                        ((TextView)vi.findViewById(Ids[i])).setTextAppearance(MainActivity.ctx, R.style.strokeText);
                    }
                    else {
                        ((TextView)vi.findViewById(Ids[i])).setTextAppearance(MainActivity.ctx, R.style.normalText);
                    }
                }
            }
        }


        if (buttonId > 0 && listener != null) {
            vi.findViewById(buttonId).setOnClickListener(listener);
        }

        return vi;
    }
}
