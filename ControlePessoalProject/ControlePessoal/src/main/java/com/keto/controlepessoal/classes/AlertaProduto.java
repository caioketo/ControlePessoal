package com.keto.controlepessoal.classes;

import com.keto.controlepessoal.util.IdGen;

import org.json.JSONObject;

/**
 * Created by developer on 29/11/13.
 */
public class AlertaProduto {
    public int AlertaId;
    public Produto Produto;

    public AlertaProduto(JSONObject json) {
        try {
            this.Produto = new Produto(json.getJSONObject("Produto"));
            this.AlertaId = IdGen.GenID();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
