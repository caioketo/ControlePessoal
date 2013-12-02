package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Codigo;
import com.keto.controlepessoal.classes.Produto;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;
import com.keto.controlepessoal.util.IntentIntegrator;
import com.keto.controlepessoal.util.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 02/12/13.
 */
public class CodigosListFragment extends Fragment implements ICFragment {
    static final int INDEX = 6;
    ListView lstCodigos;
    GenericAdapter adapter;
    ArrayList<Codigo> Codigos;
    int SelProdId;
    private ProdutoListFragment listFragment;


    public static Fragment newInstance(int produtoId, ProdutoListFragment listFragment) {
        CodigosListFragment fragment = new CodigosListFragment();
        fragment.setHasOptionsMenu(true);
        fragment.SelProdId = produtoId;
        fragment.listFragment = listFragment;
        return fragment;
    }

    public CodigosListFragment() {
        Codigos = new ArrayList<Codigo>();
    }

    @Override
    public void refresh() {
        try {
            String jsonCodigos = new Communicator().execute("Codigos", "GET", "produtoId=" + SelProdId).get();
            JSONArray jarray = new JSONArray(jsonCodigos);
            for (int i = 0; i < jarray.length(); i++) {
                Codigo cod = new Codigo(jarray.getJSONObject(i));
                Codigos.add(cod);
            }
        }
        catch (Exception ex) { }
        adapter = new GenericAdapter(Codigos, new int[] { R.id.tvwDescricao },
                new String[] { "Codigo" }, R.layout.produto_item);
        lstCodigos.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        lstCodigos = (ListView)rootView.findViewById(R.id.lstLista);
        refresh();
        return rootView;
    }


    @Override
    public void onActivityResult(int request, int result, Intent i) {
        IntentResult scan= IntentIntegrator.parseActivityResult(request,
                result,
                i);

        if (scan!=null) {
            try {
                String jsonProd = new Communicator().execute("AddCodigo", "POST",
                        "produtoId=" + SelProdId + "&codigo=" + scan.getContents())
                        .get();
                Produto prod = new Produto(new JSONObject(jsonProd));
            }
            catch (Exception ex) {

            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MercadoAct) activity).onSectionAttached(INDEX);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.compras, menu);
    }

    @Override
    public void onBackPressed() {
        ((MercadoAct)getActivity()).setCurrFrag(listFragment);
    }
}
