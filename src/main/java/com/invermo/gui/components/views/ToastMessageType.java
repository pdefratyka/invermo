package com.invermo.gui.components.views;

public enum ToastMessageType {
    SUCCESS("Success"), FAILURE("Failure");

    private String text;

    ToastMessageType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
