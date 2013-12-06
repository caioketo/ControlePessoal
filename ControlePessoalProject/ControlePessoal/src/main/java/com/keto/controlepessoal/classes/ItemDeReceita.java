package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 06/12/13.
 */
public class ItemDeReceita extends ClasseBase {
    public int ItemDeReceitaId;
    public Produto Produto;
    public double QuantidadeUtilizada;

    public ItemDeReceita() { }

    public ItemDeReceita(JSONObject json) {
        try {
            this.ItemDeReceitaId = json.getInt("ItemDeReceitaId");
            this.Produto = new Produto(json.getJSONObject("Produto"));
            this.QuantidadeUtilizada = json.getDouble("QuantidadeUtilizada");
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("ItemDeReceitaId")) {
            return ItemDeReceitaId;
        }
        else if (prop.equals("Produto")) {
            return Produto;
        }
        else if (prop.equals("ProdutoDescricao")) {
            return Produto.Descricao;
        }
        else if (prop.equals("QuantidadeUtilizada")) {
            return QuantidadeUtilizada;
        }
        return null;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("ItemDeReceitaId", ItemDeReceitaId);
            if (this.Produto != null) {
                objeto.put("Produto", Produto.getJSON());
            }
            else {
                objeto.put("Produto", null);
            }
            objeto.put("QuantidadeUtilizada", QuantidadeUtilizada);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto;
    }
}
