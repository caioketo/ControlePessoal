package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Lista;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONObject;

/**
 * Created by developer on 17/12/13.
 */
public class ListaAbertaFragment extends Fragment implements ICFragment {
    static final int INDEX = 8;
    ListView lstItens;
    GenericAdapter adapter;
    Lista lista;

    public static Fragment newInstance() {
        ListaAbertaFragment fragment = new ListaAbertaFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    @Override
    public void refresh() {
        try {
            String jsonLista = new Communicator().execute("ListaAberta", "GET").get();
            lista = new Lista(new JSONObject(jsonLista));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        adapter = new GenericAdapter(lista.Itens, new int[] { R.id.tvwDescricao, R.id.cbxMarcado },
                new String[] { "ProdutoDescricao", "Marcado" }, R.layout.item_lista_item);
        adapter.setRiscaProp(true);
        adapter.setButton(R.id.btnExcluir, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = lstItens.getPositionForView((RelativeLayout)v.getParent());
                if (position >= 0) {
                    lista.Itens.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        lstItens.setAdapter(adapter);
        lstItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lista.Itens.get(position).Marcado = !lista.Itens.get(position).Marcado;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        lstItens = (ListView)rootView.findViewById(R.id.lstLista);
        refresh();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MercadoAct) activity).onSectionAttached(INDEX);
    }

    @Override
    public void onBackPressed() {
        ((MercadoAct)getActivity()).close();
    }
}
