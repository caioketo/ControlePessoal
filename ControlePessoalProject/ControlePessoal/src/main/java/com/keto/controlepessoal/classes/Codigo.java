package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by developer on 13/11/13.
 */
public class Codigo {
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
}
