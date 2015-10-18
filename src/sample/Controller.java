package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    public Label lbl;
    private TraficFlow flow1 = new TraficFlow(),flow2;

    private StringProperty timer ;


    public void btnClick(ActionEvent actionEvent)
    {
        //flow1.getClock();
        //lbl.setText(flow1.currentTime);
        timer.setValue("OK");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        flow1.getClock();
        timer = new SimpleStringProperty();
        timer.bind(flow1.CurrentTime);
        //timer.setValue(flow1.currentTime);

        lbl.textProperty().bind(timer);


    }
}
