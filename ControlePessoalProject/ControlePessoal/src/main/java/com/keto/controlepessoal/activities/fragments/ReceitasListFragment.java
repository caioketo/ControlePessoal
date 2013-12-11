package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.content.Intent;
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
import com.keto.controlepessoal.activities.AddReceitaAct;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Receita;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by developer on 18/11/13.
 */
public class ReceitasListFragment extends Fragment implements ICFragment {
    static final int INDEX = 7;
    static final int VISUALIZAR = 0;
    ListView lstReceitas;
    GenericAdapter adapter;
    ArrayList<Receita> Receitas;

    public static Fragment newInstance() {
        ReceitasListFragment fragment = new ReceitasListFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public ReceitasListFragment() {
        Receitas = new ArrayList<Receita>();
    }

    @Override
    public void refresh() {
        try {
            Receitas.clear();
            String jsonReceitas = new Communicator().execute("Receitas", "GET").get();
            JSONArray jarray = new JSONArray(jsonReceitas);
            for (int i = 0; i < jarray.length(); i++) {
                Receita receita = new Receita(jarray.getJSONObject(i));
                Receitas.add(receita);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        adapter = new GenericAdapter(Receitas, new int[] { R.id.tvwDescricao },
                new String[] { "Descricao" }, R.layout.local_item);
        lstReceitas.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        lstReceitas = (ListView)rootView.findViewById(R.id.lstLista);
        refresh();
        registerForContextMenu(lstReceitas);
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
            startActivity(new Intent(getActivity(), AddReceitaAct.class));
            refresh();
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

