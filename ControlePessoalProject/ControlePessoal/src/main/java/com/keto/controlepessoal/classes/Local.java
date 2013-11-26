package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 19/11/13.
 */
public class Local extends ClasseBase {
    public int LocalId;
    public String Descricao;

    public Local (JSONObject json) {
        try {
            this.LocalId = json.getInt("LocalId");
            this.Descricao = json.getString("Descricao");
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("LocalId") || prop.equals("ID")) {
            return LocalId;
        }
        else if (prop.equals("Descricao")) {
            return Descricao;
        }
        return null;
    }

    @Override
    public String getJSONString() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("LocalId", LocalId);
            objeto.put("Descricao", Descricao);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto.toString();
    }
}
