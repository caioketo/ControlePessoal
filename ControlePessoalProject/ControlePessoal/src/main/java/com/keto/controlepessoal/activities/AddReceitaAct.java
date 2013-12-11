package com.keto.controlepessoal.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.classes.ItemDeReceita;
import com.keto.controlepessoal.classes.PassoDeReceita;
import com.keto.controlepessoal.classes.Produto;
import com.keto.controlepessoal.classes.Receita;
import com.keto.controlepessoal.classes.adapters.GenericAdapter;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Locale;

public class AddReceitaAct extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    Receita cReceita;

    ArrayList<Produto> Produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receita);
        cReceita = new Receita();

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

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_salvar) {
            if (cReceita.Igredientes.size() <= 0) {
                Toast.makeText(getApplicationContext(), getString(R.string.err_receita_semigrediente), Toast.LENGTH_SHORT).show();
                return true;
            }
            if (cReceita.Passos.size() <= 0) {
                Toast.makeText(getApplicationContext(), getString(R.string.err_receita_sempasso), Toast.LENGTH_SHORT).show();
                return true;
            }
            try {
                new Communicator().execute("CreateReceita", "POST", "JSON",cReceita.getJSONString()).get();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            finish();
            return true;
        }
        else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        AddReceitaAct act;

        public SectionsPagerAdapter(FragmentManager fm, AddReceitaAct _act) {
            super(fm);
            act = _act;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1, act);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_receita).toUpperCase(l);
                case 1:
                    return getString(R.string.title_igredientes).toUpperCase(l);
                case 2:
                    return getString(R.string.title_passos).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int Section;
        private View rootView;
        AddReceitaAct act;
        GenericAdapter igredientesAdapter;
        GenericAdapter passosAdapter;
        ListView lstIgredientes;
        ListView lstPassos;
        private GenericAdapter adapterProdutos;


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, AddReceitaAct _act) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            fragment.act = _act;
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Section = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (Section) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_add_receita, container, false);
                    mapReceita();
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_add_receita_igredientes, container, false);
                    mapIgredientes();
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_add_receita_passos, container, false);
                    mapPassos();
                    break;
            }
            return rootView;
        }

        public void mapReceita() {
            if (!act.cReceita.Descricao.isEmpty()) {
                ((EditText)rootView.findViewById(R.id.edtDescricao)).setText(act.cReceita.Descricao);
            }
        }

        public void mapIgredientes() {
            adapterProdutos = new GenericAdapter(act.Produtos, new int[] { R.id.tvwDescricao },
                    new String[] { "Descricao" }, R.layout.local_item);
            final Spinner spnProdutos = (Spinner)rootView.findViewById(R.id.spnProduto);
            spnProdutos.setAdapter(adapterProdutos);
            final EditText edtQtde = (EditText)rootView.findViewById(R.id.edtQuantidade);


            lstIgredientes = (ListView)rootView.findViewById(R.id.lstLista);
            igredientesAdapter = new GenericAdapter(act.cReceita.Igredientes, new int[] { R.id.tvwDescricao, R.id.tvwPreco },
                    new String[] { "ProdutoDescricao", "QuantidadeUtilizada" }, R.layout.item_compra_item);
            igredientesAdapter.setButton(R.id.btnExcluir, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = lstIgredientes.getPositionForView((RelativeLayout)v.getParent());
                    if (position >= 0) {
                        act.cReceita.Igredientes.remove(igredientesAdapter.getItem(position));
                        igredientesAdapter.notifyDataSetChanged();
                    }
                }
            });

            rootView.findViewById(R.id.btnAddItem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemDeReceita item = new ItemDeReceita();
                    item.Produto = (Produto)spnProdutos.getSelectedItem();
                    item.QuantidadeUtilizada = Double.parseDouble(edtQtde.getText().toString());
                    act.cReceita.Igredientes.add(item);
                    igredientesAdapter.notifyDataSetChanged();
                }
            });
        }

        public void mapPassos() {
            final EditText edtPasso = (EditText)rootView.findViewById(R.id.edtPasso);
            final EditText edtDescricao = (EditText)rootView.findViewById(R.id.edtDescricao);

            lstPassos = (ListView)rootView.findViewById(R.id.lstLista);
            passosAdapter = new GenericAdapter(act.cReceita.Passos, new int[] { R.id.tvwDescricao, R.id.tvwPreco },
                    new String[] { "Passo", "Descricao" }, R.layout.item_compra_item);
            passosAdapter.setButton(R.id.btnExcluir, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = lstPassos.getPositionForView((RelativeLayout) v.getParent());
                    if (position >= 0) {
                        act.cReceita.Passos.remove(passosAdapter.getItem(position));
                        passosAdapter.notifyDataSetChanged();
                    }
                }
            });

            rootView.findViewById(R.id.btnAddItem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PassoDeReceita passo = new PassoDeReceita();
                    passo.Ordem = Integer.parseInt(edtPasso.getText().toString());
                    passo.Descricao = edtDescricao.getText().toString();
                    act.cReceita.Passos.add(passo);
                    passosAdapter.notifyDataSetChanged();
                    edtDescricao.setText("");
                    edtPasso.setText("");
                }
            });
        }
    }
}
