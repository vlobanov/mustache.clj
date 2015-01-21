package me.vlobanov.mustache;

import static me.vlobanov.mustache.Context.isArray;
import static me.vlobanov.mustache.Context.isFalse;

import java.util.List;
import java.util.Map;

import clojure.lang.Keyword;

import me.vlobanov.mustache.NestedKey;

public class ParameterizedKey {
    NestedKey key;
    NestedKey[] argsKeys;

    public ParameterizedKey(String value) throws ParserException {
        // very very silly arguments parsing
        String[] openParensParts = value.split("\\(");
        if(openParensParts.length != 2) {
            throw new ParserException("Expected to find exactly 1 open parenthesis in " + value);
        }
        if(!value.endsWith(")")) {
            throw new ParserException("Expected to find close parenthesis in " + value);
        }

        String formattedKey = openParensParts[0];
        String argumentsStr = openParensParts[1].substring(0, openParensParts[1].length() - 1);

        if(argumentsStr.indexOf(")") >= 0) {
            throw new ParserException("Expected to find exactly 1 close parenthesis in " + value);
        }

        String[] arguments = argumentsStr.split("\\W*,\\W*");
        argsKeys = new NestedKey[arguments.length];
        for(int i = 0; i < arguments.length; i++) {
            argsKeys[i] = new NestedKey(arguments[i]);
        }
        key = new NestedKey(formattedKey);
    }

    public Object getIn(Map m, Object notFound) {
        Object[] argsResults = new Object[argsKeys.length];
        Object notFoundArg = "";
        for(int i = 0; i < argsKeys.length; i++) {
            argsResults[i] = argsKeys[i].getIn(m, notFoundArg);
        }
        Object keyValue = (key.getIn(m, notFound));
        if(keyValue == notFound) {
            return notFound;
        }

        return String.format((String)keyValue, argsResults);
    }

    public static boolean isParameterizedKey(String value) {
        return (value.indexOf("(") >= 0);
    }
}
