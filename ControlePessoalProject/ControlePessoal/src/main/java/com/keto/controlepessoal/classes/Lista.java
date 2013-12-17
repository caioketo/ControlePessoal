package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 17/12/13.
 */
public class Lista extends ClasseBase {
    public int ListaId;
    public boolean Aberta;
    public ArrayList<ItemDeLista> Itens;

    public Lista() {
        Itens = new ArrayList<ItemDeLista>();
        Aberta = true;
    }

    public Lista(JSONObject json) {
        Itens = new ArrayList<ItemDeLista>();
        try {
            this.ListaId = json.getInt("ListaId");
            this.Aberta = json.getBoolean("Aberta");
            JSONArray itens = json.getJSONArray("Itens");
            for (int i = 0; i < itens.length(); i++) {
                Itens.add(new ItemDeLista(itens.getJSONObject(i)));
            }
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("ListaId")) {
            return ListaId;
        }
        else if (prop.equals("Itens")) {
            return Itens;
        }
        else if (prop.equals("Aberta")) {
            return Aberta;
        }
        return null;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("ListaId", ListaId);
            objeto.put("Aberta", Aberta);
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
