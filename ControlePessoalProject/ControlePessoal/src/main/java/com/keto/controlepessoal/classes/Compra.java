package com.keto.controlepessoal.classes;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by developer on 18/11/13.
 */
public class Compra extends ClasseBase {
    public int CompraId;
    public String Local;
    public ArrayList<Produto> Produtos;

    public Compra(JSONObject json) {
        Produtos = new ArrayList<Produto>();
    }

    @Override
    public Object get(String prop) {
        if (prop.equals("ID") || prop.equals("CompraId")) {
            return CompraId;
        }
        else if (prop.equals("Local")) {
            return Local;
        }
        else if (prop.equals("Produtos")) {
            return Produtos;
        }
        return null;
    }
}
