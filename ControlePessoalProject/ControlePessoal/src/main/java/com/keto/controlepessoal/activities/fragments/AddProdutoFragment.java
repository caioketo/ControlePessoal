package com.keto.controlepessoal.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.classes.Produto;
import com.keto.controlepessoal.util.Communicator;

/**
 * Created by developer on 21/11/13.
 */
public class AddProdutoFragment extends Fragment implements ICFragment {
    static final int INDEX = 4;
    private View RootView;
    private ProdutoListFragment listFragment;

    public static Fragment newInstance(ProdutoListFragment listFragment) {
        AddProdutoFragment fragment = new AddProdutoFragment();
        fragment.listFragment = listFragment;
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public AddProdutoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_add_produto, container, false);
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
            try {
                Produto nProduto = new Produto();
                nProduto.Descricao = ((EditText)RootView.findViewById(R.id.edtDescricao)).getText().toString();
                nProduto.Quantidade = Double.parseDouble(((EditText)RootView.findViewById(R.id.edtQuantidade)).getText().toString());
                nProduto.QuantidadeAviso = Double.parseDouble(((EditText)RootView.findViewById(R.id.edtQtdeMin)).getText().toString());
                String jsonProd = new Communicator().execute("CreateProd", "POST", "JSON",
                        nProduto.getJSONString()).get();
            }
            catch (Exception ex) {
                Log.e("COM", ex.getMessage());
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
