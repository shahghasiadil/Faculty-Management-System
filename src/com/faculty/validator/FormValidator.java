package com.faculty.validator;

import java.util.HashMap;
import java.util.Map;

public class FormValidator {
    private Map<String, ValidationRule> rules = new HashMap<>();

    public void addRule(String key, ValidationRule rule) {
        rules.put(key, rule);
    }

    public void removeRule(String key) {
        if (rules.containsKey(key)) {
            rules.remove(key);
        }
    }

    public void setRule(String key, ValidationRule rule) {
        addRule(key, rule);
    }

    public ValidationRule getRule(String key) {
        if (! rules.containsKey(key)) {
            return null;
        }

        return rules.get(key);
    }

    public boolean isValid(String... keys) {
        boolean valid = keys.length > 0;

        for (String key : keys) {
            valid = (valid && rules.containsKey(key)) ? rules.get(key).validate() : false;
        }

        return valid;
    }

    public boolean isValid() {
        return isValid(rules.keySet().toArray(new String[] {}));
    }
}
