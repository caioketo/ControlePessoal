package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 17/12/13.
 */
public class ItemDeLista extends ClasseBase {
    public int ItemDeListaId;
    public Produto Produto;

    public ItemDeLista() { }

    public ItemDeLista(JSONObject json) {
        try {
            this.ItemDeListaId = json.getInt("ItemDeListaId");
            this.Produto = new Produto(json.getJSONObject("Produto"));
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("ItemDeListaId")) {
            return ItemDeListaId;
        }
        else if (prop.equals("Produto")) {
            return Produto;
        }
        else if (prop.equals("ProdutoDescricao")) {
            return Produto.Descricao;
        }
        return null;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("ItemDeListaId", ItemDeListaId);
            if (this.Produto != null) {
                objeto.put("Produto", Produto.getJSON());
            }
            else {
                objeto.put("Produto", null);
            }
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto;
    }
}
