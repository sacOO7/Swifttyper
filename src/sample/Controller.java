package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.KeyValue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class Controller implements Initializable{

    private static final Integer STARTTIME = 0;
    private Timeline timeline;
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty(STARTTIME);
    @FXML
    private String paragraph="The Moon is a barren, rocky world without air and water. " +
            "It has dark lava plain on its surface. The Moon is filled with craters." +
            " It has no light of its own. It gets its light from the Sun. " +
            "The Moon keeps changing its shape as it moves round the Earth." ;

    int Cursor=0;
    int Temp;
    int errors=0;
    int words=1;
    int  err=20;

    @FXML
    private Label timer1;
    @FXML
    private Label timer2;
    @FXML
    private Label timer3;
    @FXML
    private TextFlow param;
    @FXML
    public TextField input;
    @FXML
    private AnchorPane anchor;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.setStyle("-fx-font-size: 30px;-fx-text-fill: cornflowerblue;-fx-font-weight:bold");
        final Text t1 = new Text();

        t1.setStyle("-fx-fill:cornflowerblue;-fx-font-weight:bold;-fx-font-size:30px");
        final Text t2 = new Text();
        t2.setStyle("-fx-fill: #4F8A10;-fx-font-weight:bold;-fx-font-size:30px");
        t2.setText("");
        t1.setText(paragraph);
        param.getChildren().addAll(t2, t1);
        timer1.textProperty().bind(timeSeconds.asString());
        timeSeconds.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                timer2.setText(String.valueOf(60*words/newValue.intValue()));
            }
        });

        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (timeline == null) {
                    timeSeconds.set(STARTTIME);
                    timeline = new Timeline();
                    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(100), new KeyValue(timeSeconds, 100)));
                    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            input.setEditable(false);
                        }
                    });
                    timeline.playFromStart();
                }

                Temp = Cursor + newValue.length() - 1;

                if (newValue.length() != 0) {
                    if (paragraph.charAt(Temp) == newValue.charAt(newValue.length() - 1) && (newValue.length() - 1) <= err) {
                        if (err != 20) {
                            err = 20;
                        }
                        anchor.setStyle("-fx-background-color:lightblue");
                        t1.setText(paragraph.substring(Temp + 1, paragraph.length()));
                        t2.setText(paragraph.substring(0, Temp + 1));
                    } else {
                        if (err > (newValue.length() - 1) && (newValue.length() - 1) != -1) {
                            anchor.setStyle("-fx-background-color: red");
                            errors++;
                            timer3.setText(String.valueOf(100 - (errors * 47 / 100)) + "%");
                            err = newValue.length() - 1;
                        }

                    }
                    if (newValue.charAt(newValue.length() - 1) == ' ' && (newValue.length() - 1) < err) {
                        Cursor = Temp + 1;
                        words++;
                        input.setText("");
                    }
                } else {
                    if (Temp == -1) {
                        t2.setText("");
                        t1.setText(paragraph);
                    } else {
                        t1.setText(paragraph.substring(Temp + 1, paragraph.length()));
                        t2.setText(paragraph.substring(0, Temp + 1));
                    }
                }
            }
        });
    }
}
