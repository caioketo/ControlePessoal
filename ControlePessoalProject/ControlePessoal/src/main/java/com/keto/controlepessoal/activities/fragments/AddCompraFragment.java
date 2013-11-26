package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Compra;
import com.keto.controlepessoal.classes.ItemDeCompra;
import com.keto.controlepessoal.classes.Local;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by developer on 26/11/13.
 */
public class AddCompraFragment extends Fragment {
    static final int INDEX = 5;
    private View RootView;
    private Spinner spnLocais;
    private ArrayList<Local> Locais;
    private GenericAdapter adapterLocais;
    private ArrayList<ItemDeCompra> Itens;
    private ListView lstItens;
    private GenericAdapter adapterItens;
    private Compra CurrCompra;

    public static Fragment newInstance() {
        AddCompraFragment fragment = new AddCompraFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public AddCompraFragment() {
        CurrCompra = new Compra();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_add_compra, container, false);
        spnLocais = (Spinner)RootView.findViewById(R.id.spn_locais);
        Locais = new ArrayList<Local>();
        try {
            String jsonCompras = new Communicator().execute("Locais", "GET").get();
            JSONArray jarray = new JSONArray(jsonCompras);
            for (int i = 0; i < jarray.length(); i++) {
                Local local = new Local(jarray.getJSONObject(i));
                Locais.add(local);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        adapterLocais = new GenericAdapter(Locais, new int[] { R.id.tvwDescricao },
                new String[] { "Descricao" }, R.layout.local_item);
        spnLocais.setAdapter(adapterLocais);
        spnLocais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CurrCompra.Local = Locais.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CurrCompra.Local = null;
            }
        });
        Itens = new ArrayList<ItemDeCompra>();
        lstItens = (ListView)RootView.findViewById(R.id.lstLista);
        adapterItens = new GenericAdapter(Itens, new int[] { R.id.tvwDescricao, R.id.tvwPreco },
                new String[] { "ProdutoDescricao", "PrecoValor" }, R.layout.item_compra_item);

        lstItens.setAdapter(adapterItens);
        Button btnAddItem = (Button)RootView.findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        return RootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MercadoAct) activity).onSectionAttached(INDEX);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cancelar) {
            ((MercadoAct)getActivity()).setCurrFrag(ComprasListFragment.newInstance());
            return true;
        }
        else if (id == R.id.action_salvar) {
            try {
                String jsonCompra = new Communicator().execute("CreateCompra", "POST", "JSON",
                        CurrCompra.getJSONString())
                        .get();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            ((MercadoAct)getActivity()).setCurrFrag(ComprasListFragment.newInstance());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_default, menu);
    }
}
