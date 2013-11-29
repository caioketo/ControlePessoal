package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Local;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by developer on 19/11/13.
 */
public class LocaisListFragment extends Fragment implements ICFragment {
    static final int INDEX = 3;
    static final int EXCLUIR = 0;
    static final int EDITAR = 1;
    ListView lstLocais;
    GenericAdapter adapter;
    ArrayList<Local> Locais;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment newInstance() {
        LocaisListFragment fragment = new LocaisListFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public LocaisListFragment() {
        Locais = new ArrayList<Local>();
    }

    @Override
    public void refresh() {
        try {
            String jsonCompras = new Communicator().execute("Locais", "GET").get();
            JSONArray jarray = new JSONArray(jsonCompras);
            for (int i = 0; i < jarray.length(); i++) {
                Local local = new Local(jarray.getJSONObject(i));
                Locais.add(local);
            }
        }
        catch (Exception ex) { }
        adapter = new GenericAdapter(Locais, new int[] { R.id.tvwDescricao },
                new String[] { "Descricao" }, R.layout.local_item);
        lstLocais.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        lstLocais = (ListView)rootView.findViewById(R.id.lstLista);
        refresh();
        registerForContextMenu(lstLocais);
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
        ((MercadoAct) activity).onSectionAttached(INDEX);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            View dialogView = getActivity().getLayoutInflater().inflate(R.layout.add_local, null);
            AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
            final EditText edtDescricao = (EditText)dialogView.findViewById(R.id.edtDescricao);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (!edtDescricao.getText().toString().isEmpty()) {
                        Local nLocal = new Local();
                        nLocal.Descricao = edtDescricao.getText().toString();
                        try {
                            String jsonLocal = new Communicator().execute("CreateLocal", "POST", "JSON",
                                    nLocal.getJSONString())
                                    .get();
                            refresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create ();
            dialog.show();
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