package me.vlobanov.mustache;

import static me.vlobanov.mustache.Context.isArray;
import static me.vlobanov.mustache.Context.isFalse;

import java.util.List;
import java.util.Map;

import clojure.lang.Keyword;

import me.vlobanov.mustache.NestedKey;
import me.vlobanov.mustache.ParameterizedKey;


public class KeyFactory {
    public static Object createKey(String value) throws ParserException {
        if(ParameterizedKey.isParameterizedKey(value)) {
            return new ParameterizedKey(value);
        }
        if(NestedKey.isNestedKey(value)) {
            return new NestedKey(value);
        }
        return Keyword.intern(value);
    }
}
