package com.keto.controlepessoal.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 13/11/13.
 */
public class Produto extends ClasseBase {
    public int ProdutoId;
    public String Descricao;
    public double Quantidade;
    public double QuantidadeAviso;
    public ArrayList<Codigo> Codigos;
    public ArrayList<Preco> Precos;

    public Produto() {
        Codigos = new ArrayList<Codigo>();
        Precos = new ArrayList<Preco>();
    }


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

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("ProdutoId")) {
            return ProdutoId;
        }
        else if (prop.equals("Descricao")) {
            return Descricao;
        }
        else if (prop.equals("Quantidade")) {
            return Quantidade;
        }
        else if (prop.equals("QuantidadeAviso")) {
            return QuantidadeAviso;
        }
        else if (prop.equals("Codigos")) {
            return Codigos;
        }
        else if (prop.equals("Precos")) {
            return Precos;
        }
        return null;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject objeto = new JSONObject();
        try {
            objeto.put("ProdutoId", ProdutoId);
            objeto.put("Descricao", Descricao);
            objeto.put("Quantidade", Quantidade);
            objeto.put("QuantidadeAviso", QuantidadeAviso);
            JSONArray array = new JSONArray();
            for (int i = 0; i < Codigos.size(); i++) {
                array.put(Codigos.get(i).getJSON());
            }
            objeto.put("Codigos", array);

            JSONArray array2 = new JSONArray();
            for (int i = 0; i < Precos.size(); i++) {
                array2.put(Precos.get(i).getJSON());
            }
            objeto.put("Precos", array2);
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return objeto;
    }
}
