package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 06/12/13.
 */
public class Receita extends ClasseBase {
    public int ReceitaId;
    public String Descricao;
    public ArrayList<ItemDeReceita> Igredientes;
    public ArrayList<PassoDeReceita> Passos;

    public Receita() {
        Igredientes = new ArrayList<ItemDeReceita>();
        Passos = new ArrayList<PassoDeReceita>();
        Descricao = "";
    }

    public Receita(JSONObject json) {
        Igredientes = new ArrayList<ItemDeReceita>();
        Passos = new ArrayList<PassoDeReceita>();
        try {
            this.ReceitaId = json.getInt("ReceitaId");
            this.Descricao = json.getString("Descricao");
            JSONArray itens = json.getJSONArray("Igredientes");
            for (int i = 0; i < itens.length(); i++) {
                Igredientes.add(new ItemDeReceita(itens.getJSONObject(i)));
            }
            JSONArray passos = json.getJSONArray("Passos");
            for (int i = 0; i < passos.length(); i++) {
                Passos.add(new PassoDeReceita(passos.getJSONObject(i)));
            }
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("ReceitaId")) {
            return ReceitaId;
        }
        else if (prop.equals("Passos")) {
            return Passos;
        }
        else if (prop.equals("Descricao")) {
            return Descricao;
        }
        else if (prop.equals("Igredientes")) {
            return Igredientes;
        }
        return null;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("ReceitaId", ReceitaId);
            objeto.put("Descricao", Descricao);
            JSONArray array = new JSONArray();
            for (int i = 0; i < Igredientes.size(); i++) {
                array.put(Igredientes.get(i).getJSON());
            }
            objeto.put("Igredientes", array);
            JSONArray array2 = new JSONArray();
            for (int i = 0; i < Passos.size(); i++) {
                array2.put(Passos.get(i).getJSON());
            }
            objeto.put("Passos", array2);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto;
    }
}
