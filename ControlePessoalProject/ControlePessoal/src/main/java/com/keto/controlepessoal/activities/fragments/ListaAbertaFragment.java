package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        adapter = new GenericAdapter(lista.Itens, new int[] { R.id.tvwDescricao },
                new String[] { "ProdutoDescricao" }, R.layout.local_item);
        lstItens.setAdapter(adapter);
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
