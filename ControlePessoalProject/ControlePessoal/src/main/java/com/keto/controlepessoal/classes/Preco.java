package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by developer on 13/11/13.
 */
public class Preco extends ClasseBase {
    public int PrecoId;
    public Date Cadastro;
    public double Preco;

    public Preco(JSONObject json) {
        try {
            this.PrecoId = json.getInt("PrecoId");
            this.Cadastro = (Date)json.get("Cadastro");
            this.Preco = json.getDouble("Preco");
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("PrecoId") || prop.equals("ID")) {
            return PrecoId;
        }
        else if (prop.equals("Cadastro")) {
            return Cadastro;
        }
        else if (prop.equals("Preco")) {
            return Preco;
        }
        return null;
    }

    @Override
    public String getJSONString() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("PrecoId", PrecoId);
            objeto.put("Cadastro", Cadastro);
            objeto.put("Preco", Preco);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto.toString();
    }
}
