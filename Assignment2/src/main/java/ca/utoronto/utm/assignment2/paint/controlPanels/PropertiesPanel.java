package ca.utoronto.utm.assignment2.paint.controlPanels;

import ca.utoronto.utm.assignment2.paint.PaintProperties;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * This is a class defining PropertiesPanel
 *
 * @author tianji61 / mengale1
 */
public class PropertiesPanel extends GridPane implements EventHandler<MouseEvent> {

    private final CheckBox fill = new CheckBox("");
    private final ArrayList<Slider> sliders = new ArrayList<>();
    private final ArrayList<Text> texts = new ArrayList<>();

    public PropertiesPanel() {
        // adding gap between elements
        this.setVgap(5.0);
        this.setPadding(new Insets(10.0));
        // templates
        int[] columns = new int[]{3, 4, 5, 7, 8, 9, 11, 13};
        String[] textTemplate = new String[]{"R : ", "G : ", "B : ",
                "R : ", "G : ", "B : ",
                "px : ", "px : "};

        this.add(new Text("Fill"), 0, 1);
        this.add(fill, 1, 1);
        this.add(new Text("Fill Color"), 0, 2);
        this.add(new Text("Border Color"), 0, 6);
        this.add(new Text("Border Width"), 0, 10);
        this.add(new Text("Stroke Size"), 0, 12);
        for (int i = 0; i < columns.length; i++) {
            Slider slider = new Slider();
            if (i == 7) {  // Stroke Slider Case
                slider.setMin(1);
                slider.setMax(50);
            } else {
                slider.setMax(255);
            }
            slider.setMaxWidth(100);
            slider.setOnMouseDragged(this);
            slider.setOnMousePressed(this);
            Text text = new Text();
            text.setText(textTemplate[i] + (int)slider.getValue());
            this.add(slider, 0, columns[i]);
            this.add(text, 1, columns[i]);
            this.sliders.add(slider);
            this.texts.add(text);
        }
        this.sliders.get(6).setMax(20);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int index = 0;
        for (Slider slider : sliders) {
            if (mouseEvent.getSource() == slider) {
                texts.get(index).setText(texts.get(index).getText().substring(0, 4) + (int)slider.getValue());
            }
            index ++;
        }
    }

    public PaintProperties getPaintProperties() {

        Color fillColor = Color.rgb(
                (int)sliders.get(0).getValue(),
                (int)sliders.get(1).getValue(),
                (int)sliders.get(2).getValue());
        Color borderColor = Color.rgb(
                (int)sliders.get(3).getValue(),
                (int)sliders.get(4).getValue(),
                (int)sliders.get(5).getValue());
        double borderWidth = sliders.get(6).getValue();
        double strokeSize = sliders.get(7).getValue();
        return new PaintProperties(fill.isSelected(), fillColor, borderColor, borderWidth, strokeSize);
    }
}
