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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.ProdutosAct;
import com.keto.controlepessoal.classes.Produto;
import com.keto.controlepessoal.classes.adapters.ProdutoAdapter;
import com.keto.controlepessoal.util.Communicator;
import com.keto.controlepessoal.util.IntentIntegrator;
import com.keto.controlepessoal.util.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 14/11/13.
 */
public class ProdutoListFragment extends Fragment {
    static final int ADD_CODIGO = 0;
    ListView lstProdutos;
    ProdutoAdapter adapter;
    ArrayList<Produto> Produtos;
    private static final String ARG_SECTION_NUMBER = "section_number";
    int SelProdId;

    public static Fragment newInstance(int sectionNumber) {
        ProdutoListFragment fragment = new ProdutoListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProdutoListFragment() {
        Produtos = new ArrayList<Produto>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos_lista, container, false);
        lstProdutos = (ListView)rootView.findViewById(R.id.lstLista);
        try {
            String jsonProds = new Communicator().execute("http://jangadaserver.no-ip.info/Produto/Produtos", "GET").get();
            JSONArray jarray = new JSONArray(jsonProds);
            for (int i = 0; i < jarray.length(); i++) {
                Produto prod = new Produto(jarray.getJSONObject(i));
                Produtos.add(prod);
            }
        }
        catch (Exception ex) { }
        adapter = new ProdutoAdapter(Produtos);
        lstProdutos.setAdapter(adapter);
        registerForContextMenu(lstProdutos);
        return rootView;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(getString(R.string.opcoes));
        menu.add(Menu.NONE, ADD_CODIGO, ADD_CODIGO, getString(R.string.add_codigo));
    }

    @Override
    public void onActivityResult(int request, int result, Intent i) {
        IntentResult scan= IntentIntegrator.parseActivityResult(request,
                result,
                i);

        if (scan!=null) {
            try {
                String jsonProd = new Communicator().execute("http://jangadaserver.no-ip.info/Produto/AddCodigo", "POST",
                        "produtoId=" + SelProdId + "&codigo=" + scan.getContents())
                        .get();
                Produto prod = new Produto(new JSONObject(jsonProd));
            }
            catch (Exception ex) {

            }
            //edtCodigo.setText(scan.getContents());
            //dialog.show();
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
                    (new IntentIntegrator(getActivity())).initiateScan();
                }
            });
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!edtCodigo.getText().toString().equals("")) {
                        try {
                            String jsonProd = new Communicator().execute("http://jangadaserver.no-ip.info/Produto/AddCodigo", "POST",
                                    "produtoId=" + Produtos.get(info.position).ProdutoId + "&codigo=" + edtCodigo.getText().toString())
                                    .get();
                            Produto prod = new Produto(new JSONObject(jsonProd));
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
            final AlertDialog dialog = builder.create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
        }

        return true;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ProdutosAct) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
