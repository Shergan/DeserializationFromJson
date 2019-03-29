package com.divashchenko;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> jsonUser = new LinkedHashMap<>();
        jsonUser.put("name", "Alex");
        jsonUser.put("age", "22");

        User user = (User) fromJsonToUser(jsonUser, User.class);
        System.out.println(user);
    }

    private static Object fromJsonToUser(Map<String, String> json, Class classType) {
        Object obj = null;
        try {
            obj = classType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Field f : classType.getDeclaredFields()) {
            if (json.containsKey(f.getName())) {
                boolean defAccessible = f.isAccessible();
                f.setAccessible(true);

                if ((f.getType()).toString().equals("int")) {
                    try {
                        f.set(obj, Integer.parseInt(json.get(f.getName())));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        f.set(obj, json.get(f.getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                f.setAccessible(defAccessible);
            }
        }

        return obj;
    }
}
