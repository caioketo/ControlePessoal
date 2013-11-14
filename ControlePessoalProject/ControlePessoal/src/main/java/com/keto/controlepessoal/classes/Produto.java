package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 13/11/13.
 */
public class Produto {
    public int ProdutoId;
    public String Descricao;
    public double Quantidade;
    public double QuantidadeAviso;
    public ArrayList<Codigo> Codigos;
    public ArrayList<Preco> Precos;

    public Produto(JSONObject json) {
        Codigos = new ArrayList<Codigo>();
        Precos = new ArrayList<Preco>();
        try {
            this.ProdutoId = json.getInt("ProdutoId");
            this.Descricao = json.getString("Descricao");
            this.Quantidade = json.getDouble("Quantidade");
            this.QuantidadeAviso = json.getDouble("QuantidadeAviso");
            JSONArray precos = json.getJSONArray("Precos");
            JSONArray codigos = json.getJSONArray("Codigos");
            for (int i = 0; i < precos.length(); i++) {
                Precos.add(new Preco(precos.getJSONObject(i)));
            }
            for (int i = 0; i < codigos.length(); i++) {
                Codigos.add(new Codigo(codigos.getJSONObject(i)));
            }
        }
        catch (Exception ex) {
            Log.e("JsonError", ex.getMessage());
        }
    }
}
