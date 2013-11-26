package com.keto.controlepessoal.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.fragments.AddProdutoFragment;
import com.keto.controlepessoal.activities.fragments.ComprasListFragment;
import com.keto.controlepessoal.activities.fragments.LocaisListFragment;
import com.keto.controlepessoal.activities.fragments.ProdutoListFragment;

public class MercadoAct extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
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
                getString(R.string.title_add_compra) };
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
        refreshFragment();
    }

    public void setCurrFrag(Fragment frag) {
        CurrFrag = frag;
        refreshFragment();
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return CurrFrag.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (CurrFrag instanceof AddProdutoFragment) {
            setCurrFrag(ProdutoListFragment.newInstance());
        }
        else {
            super.onBackPressed();
        }
    }

}
