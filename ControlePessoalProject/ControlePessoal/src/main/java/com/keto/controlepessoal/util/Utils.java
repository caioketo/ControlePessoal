package com.keto.controlepessoal.util;

import java.util.Date;

/**
 * Created by developer on 05/12/13.
 */
public class Utils {

    public static Date JsonDateToDate(String jsonDate) {
        //  "/Date(1321867151710)/"
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf(")");
        String s = jsonDate.substring(idx1+1, idx2);
        long l = Long.valueOf(s);
        return new Date(l);
    }
}
