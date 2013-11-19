package com.keto.controlepessoal.classes;

import org.json.JSONObject;

/**
 * Created by developer on 19/11/13.
 */
public class Local extends ClasseBase {
    public int LocalId;
    public String Descricao;

    public Local (JSONObject json) {

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
}
