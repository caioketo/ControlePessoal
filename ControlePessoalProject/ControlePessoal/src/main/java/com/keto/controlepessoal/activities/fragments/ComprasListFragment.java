package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Compra;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by developer on 18/11/13.
 */
public class ComprasListFragment extends Fragment implements ICFragment {
    static final int INDEX = 2;
    static final int VISUALIZAR = 0;
    ListView lstCompras;
    GenericAdapter adapter;
    ArrayList<Compra> Compras;

    public static Fragment newInstance() {
        ComprasListFragment fragment = new ComprasListFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public ComprasListFragment() {
        Compras = new ArrayList<Compra>();
    }

    @Override
    public void refresh() {
        try {
            Compras.clear();
            String jsonCompras = new Communicator().execute("Compras", "GET").get();
            JSONArray jarray = new JSONArray(jsonCompras);
            for (int i = 0; i < jarray.length(); i++) {
                Compra compra = new Compra(jarray.getJSONObject(i));
                Compras.add(compra);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        adapter = new GenericAdapter(Compras, new int[] { R.id.tvwCompraId, R.id.tvwLocal },
                new String[] { "CompraId", "LocalDescricao" }, R.layout.compra_item);
        lstCompras.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        lstCompras = (ListView)rootView.findViewById(R.id.lstLista);
        refresh();
        registerForContextMenu(lstCompras);
        return rootView;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(getString(R.string.opcoes));
        menu.add(Menu.NONE, VISUALIZAR, VISUALIZAR, getString(R.string.visualizar));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == VISUALIZAR) {
            //
        }
        return true;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MercadoAct) activity).onSectionAttached(INDEX);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            ((MercadoAct)getActivity()).setCurrFrag(AddCompraFragment.newInstance(this));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.compras, menu);
    }

    @Override
    public void onBackPressed() {
        ((MercadoAct)getActivity()).close();
    }
}

