package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by developer on 13/11/13.
 */
public class Codigo extends ClasseBase {
    public int CodigoId;
    public Date Cadastro;
    public String Codigo;

    public Codigo(JSONObject json) {
        try {
            this.CodigoId = json.getInt("CodigoId");
            this.Cadastro = (Date)json.get("Cadastro");
            this.Codigo = json.getString("Codigo");
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("CodigoId") || prop.equals("ID")) {
            return CodigoId;
        }
        else if (prop.equals("Cadastro")) {
            return Cadastro;
        }
        else if (prop.equals("Codigo")) {
            return Codigo;
        }
        return null;
    }

    @Override
    public String getJSONString() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("CodigoId", CodigoId);
            objeto.put("Cadastro", Cadastro);
            objeto.put("Codigo", Codigo);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto.toString();
    }
}
