package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class DistanceInfoCard extends HBox {

    private Label sourceLabel;
    private Label destinationLabel;
    private Label distanceValue;
    private Label timeValue;
    private VBox distanceSection;
    private VBox timeSection;

    public DistanceInfoCard() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("distance-card");
        this.setPadding(new Insets(24, 32, 24, 32));
        this.setMaxWidth(Double.MAX_VALUE);
        this.setSpacing(20);

        // Source Section
        VBox sourceSection = createLocationSection("SOURCE", "City A", true);
        sourceLabel = (Label) ((HBox) sourceSection.getChildren().get(1)).getChildren().get(1);

        // Arrow Icon
        Region arrowIcon = createArrowIcon();
        VBox arrowContainer = new VBox(arrowIcon);
        arrowContainer.setAlignment(Pos.CENTER);
        arrowContainer.setPadding(new Insets(0, 20, 0, 20));

        // Destination Section
        VBox destinationSection = createLocationSection("DESTINATION", "City B", false);
        destinationLabel = (Label) ((HBox) destinationSection.getChildren().get(1)).getChildren().get(1);

        // Spacer to push info to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Distance Section
        distanceSection = createInfoSection("DISTANCE", 0, createDistanceIcon(), "#3b82f6");
        distanceValue = (Label) distanceSection.getChildren().get(1);

        // Time Section
        timeSection = createInfoSection("TIME", 0, createClockIcon(), "#f59e0b");
        timeValue = (Label) timeSection.getChildren().get(1);

        this.getChildren().addAll(
            sourceSection,
            arrowContainer,
            destinationSection,
            spacer,
            distanceSection,
            timeSection
        );
    }

    public void setSource(String source) {
        sourceLabel.setText(source);
    }

    public void setDestination(String destination) {
        destinationLabel.setText(destination);
    }

    public void setDistance(double distance) {
        distanceValue.setText(String.format("%.2f", distance));
    }

    public void setTime(double time) {
        timeValue.setText(String.format("%.2f", time));
    }

    public void showOnlyDistance() {
        if (!this.getChildren().contains(distanceSection)) {
            this.getChildren().add(distanceSection);
        }
        this.getChildren().remove(timeSection);
    }

    public void showOnlyTime() {
        if (!this.getChildren().contains(timeSection)) {
            this.getChildren().add(timeSection);
        }
        this.getChildren().remove(distanceSection);
    }

    public void showBoth() {
        if (!this.getChildren().contains(distanceSection)) {
            this.getChildren().add(5, distanceSection);
        }
        if (!this.getChildren().contains(timeSection)) {
            this.getChildren().add(timeSection);
        }
    }

    private VBox createLocationSection(String label, String location, boolean isSource) {
        VBox section = new VBox(8);
        section.setAlignment(Pos.CENTER_LEFT);

        // Label (SOURCE/DESTINATION)
        Label labelText = new Label(label);
        labelText.getStyleClass().add("location-label");

        // Location name with icon
        HBox locationBox = new HBox(10);
        locationBox.setAlignment(Pos.CENTER_LEFT);

        Region icon = createLocationIcon(isSource);

        Label locationText = new Label(location);
        locationText.getStyleClass().add("location-name");

        locationBox.getChildren().addAll(icon, locationText);
        section.getChildren().addAll(labelText, locationBox);

        return section;
    }

    private VBox createInfoSection(String labelText, double value, Region icon, String color) {
        VBox section = new VBox(6);
        section.setAlignment(Pos.CENTER);
        section.getStyleClass().add("info-section");
        section.setPadding(new Insets(8, 24, 8, 24));
        section.setStyle("-fx-background-color: " + color + "1a; -fx-border-color: " + color + "40;");

        // Icon and Label row
        HBox headerBox = new HBox(8);
        headerBox.setAlignment(Pos.CENTER);

        Label label = new Label(labelText);
        label.getStyleClass().add("info-label");
        label.setStyle("-fx-text-fill: " + color + ";");

        headerBox.getChildren().addAll(icon, label);

        // Value
        Label valueLabel = new Label(String.format("%.2f", value));
        valueLabel.getStyleClass().add("info-value");
        valueLabel.setStyle("-fx-text-fill: " + color + ";");

        section.getChildren().addAll(headerBox, valueLabel);

        return section;
    }

    private Region createLocationIcon(boolean isSource) {
        SVGPath path = new SVGPath();
        path.setContent("M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z");

        if (isSource) {
            path.setFill(Color.web("#10b981")); // Green for source
        } else {
            path.setFill(Color.web("#ef4444")); // Red for destination
        }

        path.setScaleX(1.0);
        path.setScaleY(1.0);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(path);
        return container;
    }

    private Region createArrowIcon() {
        SVGPath path = new SVGPath();
        path.setContent("M8.59 16.59L13.17 12 8.59 7.41 10 6l6 6-6 6-1.41-1.41z");
        path.setFill(Color.web("#9ca3af"));
        path.setScaleX(1.5);
        path.setScaleY(1.5);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(path);
        return container;
    }

    private Region createDistanceIcon() {
        SVGPath path = new SVGPath();
        path.setContent("M12 2L4.5 20.29l.71.71L12 18l6.79 3 .71-.71z");
        path.setFill(Color.web("#3b82f6"));
        path.setScaleX(0.8);
        path.setScaleY(0.8);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(path);
        return container;
    }

    private Region createClockIcon() {
        SVGPath path = new SVGPath();
        path.setContent("M12 2C6.5 2 2 6.5 2 12s4.5 10 10 10 10-4.5 10-10S17.5 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm.5-13H11v6l5.2 3.2.8-1.3-4.5-2.7V7z");
        path.setFill(Color.web("#f59e0b"));
        path.setScaleX(0.8);
        path.setScaleY(0.8);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(path);
        return container;
    }
}