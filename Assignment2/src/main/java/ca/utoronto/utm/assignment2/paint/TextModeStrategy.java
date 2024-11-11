package ca.utoronto.utm.assignment2.paint;

import ca.utoronto.utm.assignment2.paint.controlPanels.PropertiesPanel;
import ca.utoronto.utm.assignment2.paint.shapes.Point;
import ca.utoronto.utm.assignment2.paint.shapes.Text;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class TextModeStrategy extends DrawModeStrategy implements ModeStrategy {
    private final Pane canvasPane;
    Text text;

    public TextModeStrategy(PaintModel model, String mode, PropertiesPanel pp, Pane canvasPane) {
        super(model, mode, pp);
        this.canvasPane = canvasPane;
        this.text = (Text)model.getCurrentShape();
    }

    @Override
    public void onMousePressed(Point point, boolean isPrimaryButton, boolean isSecondaryButton) {
        super.onMousePressed(point, isPrimaryButton, isSecondaryButton);
        System.out.println("Started text box at: " + point);
    }

    @Override
    public void onMouseReleased(Point point) {
        super.onMouseReleased(point);

        // Finalize the text box with content
        activateTextField();
        System.out.println("Completed text box creation at: " + point);
    }

    private void activateTextField() {
        if (text == null) return;

        TextField textField = text.getTextField();
        canvasPane.getChildren().remove(textField);
        text.setupTextField(text.getStart());
        canvasPane.getChildren().add(textField);
        textField.requestFocus();

        System.out.println("TextField added at: " + textField.getLayoutX() + ", " + textField.getLayoutY());

        textField.setOnAction(e -> saveTextAndRemoveTextField());

        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                if (!textField.getText().trim().isEmpty()) {
                    saveTextAndRemoveTextField();
                } else {
                    canvasPane.getChildren().remove(textField);
                    text.setTextField(null);
                }
            }
        });
    }

    private void saveTextAndRemoveTextField() {
        TextField textField = text.getTextField();

        if (textField != null) {
            text.setTextContent(textField.getText());
            canvasPane.getChildren().remove(textField);
            text.setTextField(null);
            model.addShape(text); // Save text shape in model
            System.out.println("Text saved");
        }
    }
}
