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
import com.keto.controlepessoal.util.Communicator;

/**
 * Created by developer on 21/11/13.
 */
public class AddProdutoFragment extends Fragment {
    static final int INDEX = 4;
    private View RootView;

    public static Fragment newInstance() {
        AddProdutoFragment fragment = new AddProdutoFragment();
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
            ((MercadoAct)getActivity()).setCurrFrag(ProdutoListFragment.newInstance());
            return true;
        }
        else if (id == R.id.action_salvar) {
            try {
                String jsonProd = new Communicator().execute("CreateProd", "POST",
                        "descricao=" + ((EditText)RootView.findViewById(R.id.edtDescricao)).getText().toString() +
                                "&quantidade=" + ((EditText)RootView.findViewById(R.id.edtQuantidade)).getText().toString() +
                                "&quantidadeaviso=" + ((EditText)RootView.findViewById(R.id.edtQtdeMin)).getText().toString())
                        .get();
            }
            catch (Exception ex) {
                Log.e("COM", ex.getMessage());
            }
            ((MercadoAct)getActivity()).setCurrFrag(ProdutoListFragment.newInstance());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_default, menu);
    }
}
