package com.example.notesproject;

import javafx.stage.Stage;

import java.util.Optional;
import java.util.prefs.Preferences;

public class AppSize {

    private static final String HEIGHT = "Height";
    private static final String WIDTH = "Width";
    private static final String Y = "Y";
    private static final String X = "X";

    private final static Preferences prefs =  Preferences.userNodeForPackage(AppSize.class);

    public static void setSize(Stage stage) {

        Optional.ofNullable(prefs.getDouble(X, -1)).filter(x->x>=0).ifPresent(stage::setX);
        Optional.ofNullable(prefs.getDouble(Y, -1)).filter(y->y>=0).ifPresent(stage::setY);
        Optional.ofNullable(prefs.getDouble(WIDTH, -1)).filter(width->width>=0).ifPresent(stage::setWidth);
        Optional.ofNullable(prefs.getDouble(HEIGHT, -1)).filter(height->height>=0).ifPresent(stage::setHeight);

        stage.setOnCloseRequest(
                e->{

                    if(stage.isFullScreen()) {
                        return;
                    }

                    prefs.putDouble(X, stage.getX());
                    prefs.putDouble(Y, stage.getY());
                    prefs.putDouble(WIDTH, stage.getWidth());
                    prefs.putDouble(HEIGHT, stage.getHeight());
                });
    }
}
