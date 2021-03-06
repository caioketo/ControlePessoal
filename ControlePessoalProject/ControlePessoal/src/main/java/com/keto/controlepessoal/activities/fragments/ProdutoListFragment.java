package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Produto;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;
import com.keto.controlepessoal.util.IntentIntegrator;
import com.keto.controlepessoal.util.IntentResult;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by developer on 14/11/13.
 */
public class ProdutoListFragment extends Fragment implements ICFragment {
    static final int INDEX = 1;
    static final int ADD_CODIGO = 0;
    static final int CODIGOS = 1;
    ListView lstProdutos;
    GenericAdapter adapter;
    ArrayList<Produto> Produtos;
    int SelProdId;
    AlertDialog dialog;

    public static Fragment newInstance() {
        ProdutoListFragment fragment = new ProdutoListFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public ProdutoListFragment() {
        Produtos = new ArrayList<Produto>();
    }

    @Override
    public void refresh() {
        try {
            Produtos.clear();
            String jsonProds = new Communicator().execute("Produtos", "GET").get();
            JSONArray jarray = new JSONArray(jsonProds);
            for (int i = 0; i < jarray.length(); i++) {
                Produto prod = new Produto(jarray.getJSONObject(i));
                Produtos.add(prod);
            }
        }
        catch (Exception ex) { }
        adapter = new GenericAdapter(Produtos, new int[] { R.id.tvwDescricao, R.id.tvwQuantidade },
                new String[] { "Descricao", "Quantidade" }, R.layout.produto_item);
        lstProdutos.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        lstProdutos = (ListView)rootView.findViewById(R.id.lstLista);
        refresh();
        registerForContextMenu(lstProdutos);
        return rootView;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(getString(R.string.opcoes));
        menu.add(Menu.NONE, ADD_CODIGO, ADD_CODIGO, getString(R.string.add_codigo));
        menu.add(Menu.NONE, CODIGOS, CODIGOS, getString(R.string.codigos));
    }

    @Override
    public void onActivityResult(int request, int result, Intent i) {
        IntentResult scan= IntentIntegrator.parseActivityResult(request,
                result,
                i);

        if (scan!=null) {
            Toast.makeText(MercadoAct.ctx, "Adicionando código: " + scan.getContents(), Toast.LENGTH_SHORT);
            try {
                new Communicator().execute("AddCodigo", "POST",
                        "produtoId=" + SelProdId + "&codigo=" + scan.getContents())
                        .get();
                //Produto prod = new Produto(new JSONObject(jsonProd));
                Toast.makeText(MercadoAct.ctx, "Adicionado código: " + scan.getContents(), Toast.LENGTH_SHORT);
            }
            catch (Exception ex) {
                Toast.makeText(MercadoAct.ctx, ex.getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        if (menuItemIndex == ADD_CODIGO) {
            SelProdId = Produtos.get(info.position).ProdutoId;
            View dialogView = getActivity().getLayoutInflater().inflate(R.layout.add_codigo, null);
            AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
            assert dialogView != null;
            final EditText edtCodigo = (EditText)dialogView.findViewById(R.id.edt_codigo);
            final Button btnScan = (Button)dialogView.findViewById(R.id.btnScan);
            btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    (new IntentIntegrator(getActivity())).initiateScan();
                }
            });
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!edtCodigo.getText().toString().equals("")) {
                        try {
                            new Communicator().execute("AddCodigo", "POST",
                                    "produtoId=" + Produtos.get(info.position).ProdutoId + "&codigo=" + edtCodigo.getText().toString())
                                    .get();
                        }
                        catch (Exception ex) {

                        }
                    }
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setView(dialogView);
            dialog = builder.create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
        }
        else if (menuItemIndex == CODIGOS) {
            SelProdId = Produtos.get(info.position).ProdutoId;
            ((MercadoAct)getActivity()).setCurrFrag(CodigosListFragment.newInstance(SelProdId, this));
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
            ((MercadoAct)getActivity()).setCurrFrag(AddProdutoFragment.newInstance(this));
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
