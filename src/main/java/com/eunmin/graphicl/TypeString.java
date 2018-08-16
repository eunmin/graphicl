package com.eunmin.graphicl;

public class TypeString {
    public static String toString(Object object) {
        if (object instanceof String) {
            return "\"" + object + "\"";
        }
        else {
            return object.toString();
        }
    }
}
