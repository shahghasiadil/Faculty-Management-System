package com.faculty.validator;

import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;

import java.util.function.Function;
import java.util.regex.Pattern;

public class ValidationRule {
    private Control control;
    private Pattern pattern;
    private Function<Control, Boolean> validator;

    public ValidationRule() {
    }

    public ValidationRule(Control control, String pattern) {
        setValidator(this::defaultValidator);
        setControl(control);
        setPattern(pattern);
    }

    public ValidationRule(Control control, Pattern pattern) {
        setValidator(this::defaultValidator);
        this.control = control;
        this.pattern = pattern;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void setPattern(String pattern, int flags) {
        this.pattern = Pattern.compile(pattern, flags);
    }

    public Function<Control, Boolean> getValidator() {
        return validator;
    }

    public void setValidator(Function<Control, Boolean> validator) {
        this.validator = validator;
    }

    private boolean defaultValidator(Control control) {
        if (null == control || null == pattern) {
            return false;
        }

        if (! (control instanceof TextInputControl)) {
            return false;
        }

        TextInputControl hasText = (TextInputControl) control;
        String value = hasText.getText();

        return pattern.matcher(value).matches();
    }

    public boolean validate() {
        return validator.apply(control);
    }
}
