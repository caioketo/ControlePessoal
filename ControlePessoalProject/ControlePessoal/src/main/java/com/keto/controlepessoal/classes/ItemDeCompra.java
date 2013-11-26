package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by developer on 21/11/13.
 */
public class ItemDeCompra extends ClasseBase {
    public int ItemId;
    public Produto Produto;
    public Preco Preco;

    public ItemDeCompra(JSONObject json) {
        try {
            this.ItemId = json.getInt("ItemId");
            this.Produto = new Produto(json.getJSONObject("Produto"));
            this.Preco = new Preco(json.getJSONObject("Preco"));
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("ItemId")) {
            return ItemId;
        }
        else if (prop.equals("Produto")) {
            return Produto;
        }
        else if (prop.equals("ProdutoDescricao")) {
            return Produto.Descricao;
        }
        else if (prop.equals("Preco")) {
            return Preco;
        }
        else if (prop.equals("PrecoValor")) {
            return Preco.Preco;
        }
        return null;
    }
}