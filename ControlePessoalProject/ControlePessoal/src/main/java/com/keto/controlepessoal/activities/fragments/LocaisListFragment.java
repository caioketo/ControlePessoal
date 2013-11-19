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
import com.keto.controlepessoal.classes.Local;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;

import java.util.ArrayList;

/**
 * Created by developer on 19/11/13.
 */
public class LocaisListFragment extends Fragment {
    static final int EXCLUIR = 0;
    static final int EDITAR = 1;
    ListView lstLocais;
    GenericAdapter adapter;
    ArrayList<Local> Locais;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment newInstance(int sectionNumber) {
        ProdutoListFragment fragment = new ProdutoListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public LocaisListFragment() {
        Locais = new ArrayList<Local>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        /*lstLocais = (ListView)rootView.findViewById(R.id.lstLista);
        try {
            String jsonCompras = new Communicator().execute("http://jangadaserver.no-ip.info/Compra/Compras", "GET").get();
            JSONArray jarray = new JSONArray(jsonCompras);
            for (int i = 0; i < jarray.length(); i++) {
                Compra compra = new Compra(jarray.getJSONObject(i));
                Compras.add(compra);
            }
        }
        catch (Exception ex) {
            Log.e("COM", ex.getMessage());
        }
        adapter = new CompraAdapter(Compras);
        lstLocais.setAdapter(adapter);
        registerForContextMenu(lstLocais);*/
        return rootView;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(getString(R.string.opcoes));
        menu.add(Menu.NONE, EXCLUIR, EXCLUIR, getString(R.string.visualizar));
        menu.add(Menu.NONE, EDITAR, EDITAR, getString(R.string.visualizar));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == EXCLUIR) {
            //
        }
        else if (menuItemIndex == EDITAR) {
            //
        }
        return true;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MercadoAct) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.global, menu);
    }
}