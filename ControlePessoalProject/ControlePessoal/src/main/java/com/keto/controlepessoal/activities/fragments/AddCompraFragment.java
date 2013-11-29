package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Compra;
import com.keto.controlepessoal.classes.ItemDeCompra;
import com.keto.controlepessoal.classes.Local;
import com.keto.controlepessoal.classes.Preco;
import com.keto.controlepessoal.classes.Produto;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by developer on 26/11/13.
 */
public class AddCompraFragment extends Fragment implements ICFragment {
    static final int INDEX = 5;
    private View RootView;
    private Spinner spnLocais;
    private ArrayList<Local> Locais;
    private GenericAdapter adapterLocais;
    public ListView lstItens;
    public GenericAdapter adapterItens;
    private Compra CurrCompra;
    private ComprasListFragment listFragment;
    private ArrayList<Produto> Produtos;
    private GenericAdapter adapterProdutos;



    public static Fragment newInstance(ComprasListFragment listFragment) {
        AddCompraFragment fragment = new AddCompraFragment();
        fragment.listFragment = listFragment;
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

        Produtos = new ArrayList<Produto>();
        try {
            String jsonProdutos = new Communicator().execute("Produtos", "GET").get();
            JSONArray jarray = new JSONArray(jsonProdutos);
            for (int i = 0; i < jarray.length(); i++) {
                Produto produto = new Produto(jarray.getJSONObject(i));
                Produtos.add(produto);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        adapterProdutos = new GenericAdapter(Produtos, new int[] { R.id.tvwDescricao },
                new String[] { "Descricao" }, R.layout.local_item);
        spnLocais = (Spinner)RootView.findViewById(R.id.spn_locais);
        Locais = new ArrayList<Local>();
        try {
            String jsonLocais = new Communicator().execute("Locais", "GET").get();
            JSONArray jarray = new JSONArray(jsonLocais);
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
        CurrCompra.Itens = new ArrayList<ItemDeCompra>();
        lstItens = (ListView)RootView.findViewById(R.id.lstLista);
        adapterItens = new GenericAdapter(CurrCompra.Itens, new int[] { R.id.tvwDescricao, R.id.tvwPreco },
                new String[] { "ProdutoDescricao", "PrecoValor" }, R.layout.item_compra_item);
        adapterItens.setButton(R.id.btnExcluir, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = lstItens.getPositionForView((RelativeLayout)v.getParent());
                if (position >= 0) {
                    CurrCompra.Itens.remove(adapterItens.getItem(position));
                    adapterItens.notifyDataSetChanged();
                }
            }
        });
        lstItens.setAdapter(adapterItens);
        RootView.findViewById(R.id.btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.add_item, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final Spinner spnProdutos = (Spinner)dialogView.findViewById(R.id.spnProduto);
                final EditText edtPreco = (EditText)dialogView.findViewById(R.id.edtPreco);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ItemDeCompra item = new ItemDeCompra();
                        item.Produto = (Produto)spnProdutos.getSelectedItem();
                        item.Preco = new Preco();
                        item.Preco.Cadastro = new Date();
                        item.Preco.Preco = Double.parseDouble(edtPreco.getText().toString());
                        CurrCompra.Itens.add(item);
                        adapterItens.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                spnProdutos.setAdapter(adapterProdutos);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create ();
                dialog.show();
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
            ((MercadoAct)getActivity()).setCurrFrag(listFragment);
            return true;
        }
        else if (id == R.id.action_salvar) {
            if (CurrCompra.Itens.size() <= 0) {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.err_compra_semitem), Toast.LENGTH_SHORT).show();
                return true;
            }
            try {
                String jsonCompra = new Communicator().execute("CreateCompra", "POST", "JSON",
                        CurrCompra.getJSONString())
                        .get();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            ((MercadoAct)getActivity()).setCurrFrag(listFragment, true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_default, menu);
    }

    @Override
    public void onBackPressed() {
        ((MercadoAct)getActivity()).setCurrFrag(listFragment);
    }

    @Override
    public void refresh() {

    }
}
