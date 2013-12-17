package com.keto.controlepessoal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.SettingsActivity;
import com.keto.controlepessoal.activities.fragments.ComprasListFragment;
import com.keto.controlepessoal.activities.fragments.ICFragment;
import com.keto.controlepessoal.activities.fragments.LocaisListFragment;
import com.keto.controlepessoal.activities.fragments.ProdutoListFragment;
import com.keto.controlepessoal.activities.fragments.ReceitasListFragment;

public class MercadoAct extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public static Context ctx;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private int SelectedItem = 1;
    private Fragment CurrFrag;

    private String[] Titulos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        String[] tmp = { getString(R.string.title_produtos), getString(R.string.title_compras),
                getString(R.string.title_locais), getString(R.string.title_add_produto),
                getString(R.string.title_add_compra), getString(R.string.title_codigos), getString(R.string.title_receitas),
                getString(R.string.title_lista_aberta)};
        Titulos = tmp;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        SelectedItem = position + 1;
        if (position == 0) {
            CurrFrag = ProdutoListFragment.newInstance();
        }
        else if (position == 1) {
            CurrFrag = ComprasListFragment.newInstance();
        }
        else if (position == 2) {
            CurrFrag = LocaisListFragment.newInstance();
        }
        else if (position == 3) {
            CurrFrag = ReceitasListFragment.newInstance();
        }
        refreshFragment();
    }

    public void setCurrFrag(Fragment frag, boolean refresh) {
        CurrFrag = frag;
        refreshFragment();
        if (refresh) {
            ((ICFragment)CurrFrag).refresh();
        }
    }

    public void setCurrFrag(Fragment frag) {
        setCurrFrag(frag, false);
    }

    public void refreshFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, CurrFrag)
                .commit();
    }

    public void onSectionAttached(int number) {
        mTitle = Titulos[number - 1];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            menu.clear();
            restoreActionBar();
            return super.onCreateOptionsMenu(menu);
        }
        menu.clear();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        else {
            return CurrFrag.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        ((ICFragment)CurrFrag).onBackPressed();
    }

    public void close() {
        super.onBackPressed();
    }
}
