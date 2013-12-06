package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 06/12/13.
 */
public class PassoDeReceita extends ClasseBase {
    public int PassoDeReceitaId;
    public int Ordem;
    public String Descricao;

    public PassoDeReceita() { }

    public PassoDeReceita(JSONObject json) {
        try {
            this.PassoDeReceitaId = json.getInt("PassoDeReceitaId");
            this.Ordem = json.getInt("Ordem");
            this.Descricao = json.getString("Descricao");
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("PassoDeReceitaId")) {
            return PassoDeReceitaId;
        }
        else if (prop.equals("Ordem")) {
            return Ordem;
        }
        else if (prop.equals("Descricao")) {
            return Descricao;
        }
        return null;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("PassoDeReceitaId", PassoDeReceitaId);
            objeto.put("Ordem", Ordem);
            objeto.put("Descricao", Descricao);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto;
    }
}
