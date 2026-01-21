package ui;

import algorithms.Vertex;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.util.StringConverter;

public class CalculateOptimalPart extends Card {
	private ComboBox<Vertex> src;
	private ComboBox<Vertex> dest;
	private Button calcOptimalBtn;
	private ToggleGroup optimizationGroup;
	ObservableList<Vertex> data;

	public CalculateOptimalPart(ObservableList<Vertex> data) {
		this.setVisible(true);

		Label calcOptimalL = new Label("🛠   Find The Shortest Path");
		calcOptimalL.getStyleClass().add("card-title");

		Label calcOptimalDescL = new Label(
				"Enter Source and Destination and click calculate to find the shortest path\r\n");
		calcOptimalDescL.getStyleClass().add("subtitle-dark");

		Label srcL = new Label("Source");
		srcL.getStyleClass().add("input-label");

		Label destL = new Label(" Destenation");
		destL.getStyleClass().add("input-label");

		calcOptimalBtn = new Button("🚀 Shortest Path");
		calcOptimalBtn.getStyleClass().add("modern-button");
		calcOptimalBtn.setPrefHeight(35);
		calcOptimalBtn.setPrefWidth(1800);

		this.data = data;

		src = createCb();
		src.setPrefWidth(900);
		dest = createCb();
		dest.setPrefWidth(900);
		src.getStyleClass().add("combo-box");
		dest.getStyleClass().add("combo-box");

		VBox srcVb = new VBox();
		srcVb.getChildren().addAll(srcL, src);

		VBox destVb = new VBox();
		destVb.getChildren().addAll(destL, dest);

		HBox field = new HBox();
		field.setSpacing(20);
		field.getChildren().addAll(srcVb, destVb);

		VBox mainContainer = new VBox(12);
		mainContainer.setPadding(new Insets(20));
		mainContainer
				.setStyle("-fx-background-color: #f5f7fa;" + "-fx-background-radius: 20;" + "-fx-border-radius: 20;");

		// Toggle Group for Radio Buttons
		optimizationGroup = new ToggleGroup();

		// Create three option cards
		VBox shortestDistanceCard = createOptionCard(createRouteIcon(), "Shortest Distance",
				"Minimize total path distance", optimizationGroup, true, "DIST");

		VBox leastTravelTimeCard = createOptionCard(createClockIcon(), "Least Travel Time",
				"Minimize total travel time", optimizationGroup, false,"TIME");

		VBox bothCombinedCard = createOptionCard(createCombinedIcon(), "Both (Combined)",
				"Optimize for distance and time equally", optimizationGroup, false,"BOTH");

		mainContainer.getChildren().addAll(shortestDistanceCard, leastTravelTimeCard, bothCombinedCard, calcOptimalBtn);

		this.getChildren().addAll(calcOptimalL, calcOptimalDescL, field, mainContainer);
	}

	public ComboBox<Vertex> createCb() {
		ComboBox<Vertex> comboBox = new ComboBox<>();
		comboBox.setEditable(true);

		// Filtered list
		FilteredList<Vertex> filtered = new FilteredList<>(data, v -> true);

		comboBox.setItems(filtered);

		// Display Vertex ID instead of object
		comboBox.setConverter(new StringConverter<>() {
			@Override
			public String toString(Vertex v) {
				return v == null ? "" : v.getId();
			}

			@Override
			public Vertex fromString(String s) {
				return comboBox.getItems().stream().filter(v -> v.getId().equals(s)).findFirst().orElse(null);
			}
		});

		comboBox.getEditor().setStyle("-fx-background-color: transparent;");

		// Search logic
		comboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
			final String text = newVal.toLowerCase();

			filtered.setPredicate(v -> v.getId().toLowerCase().startsWith(text));

			if (!comboBox.isShowing()) {
				comboBox.show();
			}
		});

		// When user selects an item, show it in editor
		comboBox.valueProperty().addListener((obs, old, val) -> {
			if (val != null) {
				comboBox.getEditor().setText(val.getId());
			}
		});
		return comboBox;
	}

	public Button getCalcOptimalBtn() {
		return calcOptimalBtn;
	}

	public void setCalcOptimalBtn(Button calcOptimalBtn) {
		this.calcOptimalBtn = calcOptimalBtn;
	}

	public ObservableList<Vertex> getData() {
		return data;
	}

	public void setData(ObservableList<Vertex> data) {
		this.data = data;
	}

	public ComboBox<Vertex> getSrc() {
		return src;
	}

	public void setSrc(ComboBox<Vertex> src) {
		this.src = src;
	}

	public ComboBox<Vertex> getDest() {
		return dest;
	}

	public void setDest(ComboBox<Vertex> dest) {
		this.dest = dest;
	}

	private VBox createOptionCard(Region icon, String title, String description, ToggleGroup group,
			boolean isSelected, String rbName) {
		VBox card = new VBox(0);
		card.setAlignment(Pos.CENTER_LEFT);
		card.getStyleClass().add("option-card");
		if (isSelected) {
			card.getStyleClass().add("selected");
		}

		HBox contentBox = new HBox(16);
		contentBox.setAlignment(Pos.CENTER_LEFT);
		contentBox.setPadding(new Insets(20, 24, 20, 24));

// Icon container
		VBox iconContainer = new VBox();
		iconContainer.setAlignment(Pos.CENTER);
		iconContainer.setPrefSize(56, 56);
		iconContainer.setMinSize(56, 56);
		iconContainer.setMaxSize(56, 56);
		iconContainer.setStyle("-fx-background-color: #e3f2fd; -fx-background-radius: 12;");
		iconContainer.getChildren().add(icon);

// Text container
		VBox textContainer = new VBox(4);
		textContainer.setAlignment(Pos.CENTER_LEFT);

		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("option-title");

		Label descLabel = new Label(description);
		descLabel.getStyleClass().add("option-description");

		textContainer.getChildren().addAll(titleLabel, descLabel);

// Spacer
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

// Custom checkmark circle
		Circle checkCircle = new Circle(14);
		checkCircle.getStyleClass().add("check-circle");
		if (isSelected) {
			checkCircle.getStyleClass().add("selected");
		}

// Hidden radio button for functionality
		RadioButton radioButton = new RadioButton();
		radioButton.setText(title);
		radioButton.setToggleGroup(group);
		radioButton.setSelected(isSelected);
		radioButton.setVisible(false);
		radioButton.setManaged(false);
		radioButton.setUserData(rbName);
		


		contentBox.getChildren().addAll(iconContainer, textContainer, spacer, checkCircle);
		card.getChildren().add(contentBox);

// Click handler
		card.setOnMouseClicked(e -> {
			radioButton.setSelected(true);
			updateCardSelection(card, checkCircle, true);
// Remove selection from siblings
			VBox parent = (VBox) card.getParent();
			for (var node : parent.getChildren()) {
				if (node != card && node instanceof VBox) {
					VBox siblingCard = (VBox) node;
					HBox siblingContent = (HBox) siblingCard.getChildren().get(0);
					Circle siblingCheck = (Circle) siblingContent.getChildren().get(3);
					updateCardSelection(siblingCard, siblingCheck, false);
				}
			}
		});

		return card;
	}

	private void updateCardSelection(VBox card, Circle checkCircle, boolean selected) {
		if (selected) {
			if (!card.getStyleClass().contains("selected")) {
				card.getStyleClass().add("selected");
			}
			if (!checkCircle.getStyleClass().contains("selected")) {
				checkCircle.getStyleClass().add("selected");
			}
		} else {
			card.getStyleClass().remove("selected");
			checkCircle.getStyleClass().remove("selected");
		}
	}

	private Region createRouteIcon() {
		SVGPath path = new SVGPath();
		path.setContent(
				"M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z");
		path.setFill(Color.web("#5a6c7d"));
		path.setScaleX(1.3);
		path.setScaleY(1.3);

		VBox container = new VBox();
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(path);
		return container;
	}

	private Region createClockIcon() {
		SVGPath path = new SVGPath();
		path.setContent(
				"M12 2C6.5 2 2 6.5 2 12s4.5 10 10 10 10-4.5 10-10S17.5 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm.5-13H11v6l5.2 3.2.8-1.3-4.5-2.7V7z");
		path.setFill(Color.web("#5a6c7d"));
		path.setScaleX(1.3);
		path.setScaleY(1.3);

		VBox container = new VBox();
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(path);
		return container;
	}

	private Region createCombinedIcon() {
		SVGPath path = new SVGPath();
		path.setContent(
				"M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z");
		path.setFill(Color.web("#5a6c7d"));
		path.setScaleX(1.3);
		path.setScaleY(1.3);

		VBox container = new VBox();
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(path);
		return container;
	}

	public ToggleGroup getOptimizationGroup() {
		return optimizationGroup;
	}

	public void setOptimizationGroup(ToggleGroup optimizationGroup) {
		this.optimizationGroup = optimizationGroup;
	}
	
	

}
