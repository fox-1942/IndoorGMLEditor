package hu.iit.uni.miskolc.gml.editor.gui;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.util.Duration;

public class RotateAction implements EventHandler<ActionEvent> {

    RotateTransition trans;

    RotateAction(Point3D a, Node group) {
        trans = new RotateTransition(new Duration(5000), group);
        trans.setAxis(a);
        trans.setCycleCount(2);
        trans.setAutoReverse(true);
        trans.setByAngle(360);
    }

    @Override
    public void handle(ActionEvent event) {
        trans.play();
    }
}