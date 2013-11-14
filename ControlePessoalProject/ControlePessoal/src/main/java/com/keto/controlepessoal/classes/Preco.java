package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by developer on 13/11/13.
 */
public class Preco {
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
}
