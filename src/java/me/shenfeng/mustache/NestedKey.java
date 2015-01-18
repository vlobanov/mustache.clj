package me.vlobanov.mustache;

import static me.vlobanov.mustache.Context.isArray;
import static me.vlobanov.mustache.Context.isFalse;

import java.util.List;
import java.util.Map;

import clojure.lang.Keyword;

public class NestedKey {
    Keyword[] parts;
    public String strValue;
    public static final char SEPARATOR = '.';

    public boolean equals(Object obj) {
        return ((obj instanceof NestedKey) &&
                ((NestedKey)obj).strValue.equals(this.strValue));
    }

    public NestedKey(String value) {
        strValue = value;
        String[] strParts = value.split("\\" + SEPARATOR);
        parts = new Keyword[strParts.length];
        for(int i = 0; i < strParts.length; i++) {
            parts[i] = Keyword.intern(strParts[i]);
        }
    }

    public Object getIn(Map m, Object notFound) {
        Object currVal = m;
        Map currMap;

        for(Keyword key : parts) {
            if(currVal instanceof Map) {
                currMap = (Map)currVal;

                if(currMap.containsKey(key)) {
                    currVal = currMap.get(key);
                } else {
                    return notFound;
                }
            } else {
                return notFound;
            }
        }

        return currVal;
    }

    public static boolean isNestedKey(String value) {
        return (!(value.equals(".")) && value.indexOf(SEPARATOR) >= 0);
    }
}
