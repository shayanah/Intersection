package sample;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller extends Application
{
    public Label lbl;
    private TraficFlow flow1,flow2;

    private StringProperty timer ;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        flow1 = new TraficFlow();
        flow1.getClock();
        timer.setValue(flow1.currentTime);

        lbl.textProperty().bind(timer);
        timer.setValue("sdf");

    }
}
