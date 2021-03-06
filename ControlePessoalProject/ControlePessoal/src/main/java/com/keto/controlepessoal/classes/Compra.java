package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 18/11/13.
 */
public class Compra extends ClasseBase {
    public int CompraId;
    public Local Local;
    public ArrayList<ItemDeCompra> Itens;

    public Compra() {
        Itens = new ArrayList<ItemDeCompra>();
    }

    public Compra(JSONObject json) {
        Itens = new ArrayList<ItemDeCompra>();
        try {
            this.CompraId = json.getInt("CompraId");
            this.Local = new Local(json.getJSONObject("Local"));
            JSONArray itens = json.getJSONArray("Itens");
            for (int i = 0; i < itens.length(); i++) {
                Itens.add(new ItemDeCompra(itens.getJSONObject(i)));
            }
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("CompraId")) {
            return CompraId;
        }
        else if (prop.equals("Local")) {
            return Local;
        }
        else if (prop.equals("LocalDescricao")) {
            return Local.Descricao;
        }
        else if (prop.equals("Itens")) {
            return Itens;
        }
        return null;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("CompraId", CompraId);
            if (this.Local != null) {
                objeto.put("Local", this.Local.getJSON());
            }
            else {
                objeto.put("Local", null);
            }
            JSONArray array = new JSONArray();
            for (int i = 0; i < Itens.size(); i++) {
                array.put(Itens.get(i).getJSON());
            }
            objeto.put("Itens", array);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto;
    }
}
