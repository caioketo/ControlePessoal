package com.keto.controlepessoal.classes;

import org.json.JSONObject;

/**
 * Created by developer on 19/11/13.
 */
public abstract class ClasseBase {
    public abstract Object get(String prop);
    public String getJSONString() {
        return getJSON().toString();
    }
    public abstract JSONObject getJSON();
}
