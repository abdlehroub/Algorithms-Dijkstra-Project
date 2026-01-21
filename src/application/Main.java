package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import algorithms.Dijkstra;
import algorithms.Edge;
import algorithms.Graph;
import algorithms.MinHeap;
import algorithms.MyList;
import algorithms.Vertex;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.CalculateOptimalPart;
import ui.Card;
import ui.DistanceInfoCard;
import ui.GraphVisualization;
import ui.UploadPart;

public class Main extends Application {
	File file = null;
	Graph g = new Graph(10);
	CalculateOptimalPart calc;
	GraphVisualization gV;

	@Override
	public void start(Stage stage) {

		// Header with a title and small description
		Label titleL = new Label("Shortest Path Application");
		titleL.getStyleClass().add("title");

		Label descriptionL = new Label(
				"Multi-Criteria Shortest Path Optimization Using Graphs and Dijkstra Algorithm with Priority Queue");
		descriptionL.getStyleClass().add("subtitle");

		VBox headerVb = new VBox(5, titleL, descriptionL);
		headerVb.getStyleClass().add("header-box");

		// Content container has all interface components
		VBox contentVb = new VBox(25);
		contentVb.setPadding(new Insets(30));
		contentVb.getStyleClass().add("content-wrapper");

//		----------------------------------------------------
		UploadPart uploadPart = new UploadPart();
		contentVb.getChildren().add(uploadPart);

		calc = new CalculateOptimalPart(g.getVertices().asObservableList());
		contentVb.getChildren().add(calc);

//		----------------------------------------------------

		// Wrap root
		VBox root = new VBox();

		root.getChildren().addAll(headerVb, contentVb);

		// The main pane has all of the parts
		ScrollPane mainSp = new ScrollPane();
		mainSp.setContent(root);
		mainSp.setFitToWidth(true);

		Card res = new Card();
		Label calcOptimalL = new Label("🛠   Find The Shortest Path");
		calcOptimalL.getStyleClass().add("card-title");

		Label calcOptimalDescL = new Label(
				"Enter Source and Destination and click calculate to find the shortest path\r\n");
		calcOptimalDescL.getStyleClass().add("subtitle-dark");

		VBox mainContainer = new VBox(12);
		mainContainer.setPadding(new Insets(20));
		mainContainer.getStyleClass().add("distance-card");
		mainContainer.setAlignment(Pos.CENTER);
		Label Path = new Label("Shortest Path");
		Path.getStyleClass().add("card-title");

		DistanceInfoCard distanceCard = new DistanceInfoCard();

		res.getChildren().addAll(calcOptimalL, calcOptimalDescL, distanceCard, mainContainer);
		contentVb.getChildren().add(res);

		Scene scene = new Scene(mainSp, 1540, 790);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		stage.setTitle("Shortest Path Application");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

		calc.getCalcOptimalBtn().setDisable(true);
		uploadPart.getUploadFileBtn().setOnAction(e2 -> {
			try {
				file = fileChooser();
				g = uploadFromFile(file);
				calc.getData().addAll(g.getVertices().asObservableList());
				showSuccess("File Uploded Successfuly", "Great: File Uploded Successfly!");
				calc.getCalcOptimalBtn().setDisable(false);
			} catch (NullPointerException e1) {
				e1.printStackTrace();
				showError("File Error", "Error: No File Selected!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		calc.getCalcOptimalBtn().setOnAction(e2 -> {

			String mode = (String) calc.getOptimizationGroup().getSelectedToggle().getUserData();
			Dijkstra d = new Dijkstra();
			if (mode.equals("DIST")) {
				mainContainer.getChildren().clear();
				MyList<Vertex> path = d.getShortestPath(g, calc.getSrc().getValue(), calc.getDest().getValue(), false);
				gV = new GraphVisualization(path);
				distanceCard.showOnlyDistance();
				distanceCard.setSource(calc.getSrc().getValue().getId());
				distanceCard.setDestination(calc.getDest().getValue().getId());
				distanceCard.setDistance(calc.getDest().getValue().getDv());
				mainContainer.getChildren().addAll(gV);
			} else if (mode.equals("TIME")) {
				mainContainer.getChildren().clear();
				MyList<Vertex> path = d.getShortestPath(g, calc.getSrc().getValue(), calc.getDest().getValue(), true);
				gV = new GraphVisualization(path);
				distanceCard.showOnlyTime();
				distanceCard.setSource(calc.getSrc().getValue().getId());
				distanceCard.setDestination(calc.getDest().getValue().getId());
				distanceCard.setTime(calc.getDest().getValue().getDv());
				mainContainer.getChildren().addAll(gV);

			} else {
				mainContainer.getChildren().clear();
				Label distanceL = new Label("Distance Based");
				distanceL.getStyleClass().add("card-title");
				MyList<Vertex> path1 = d.getShortestPath(g, calc.getSrc().getValue(), calc.getDest().getValue(), false);
				gV = new GraphVisualization(path1);
				mainContainer.getChildren().addAll(distanceL, gV);
				distanceCard.setDistance(calc.getDest().getValue().getDv());

				MyList<Vertex> path2 = d.getShortestPath(g, calc.getSrc().getValue(), calc.getDest().getValue(), true);
				gV = new GraphVisualization(path2);
				Label timeL = new Label("Time Based");
				timeL.getStyleClass().add("card-title");
				distanceCard.showBoth();
				distanceCard.setSource(calc.getSrc().getValue().getId());
				distanceCard.setDestination(calc.getDest().getValue().getId());
				distanceCard.setTime(calc.getDest().getValue().getDv());
				mainContainer.getChildren().addAll(timeL, gV);
			}
		});

	}

	private void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showSuccess(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

//	------------------FileChooser Function------------------
	public File fileChooser() {
		/*
		 * Function to Open FileChooser Dialog in JavaFX and Accept Only .txt Files The
		 * Function Return the File Selected From the Dialog, If No Selection return
		 * null.
		 */
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File file = fileChooser.showOpenDialog(new Stage());
		return file;
	}

public Graph uploadFromFile(File file) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		int size = 0;
		while ((line = br.readLine()) != null) {
				size++;
		}
		br.close();
		Graph g = new Graph(size);

		br = new BufferedReader(new FileReader(file));
		String[] fLine = br.readLine().split("\\s+");
		int opt = Integer.parseInt(fLine[2]);

		for (Toggle t : calc.getOptimizationGroup().getToggles()) {
		    RadioButton rb = (RadioButton) t;
		    String mode = (String) t.getUserData();   // ← التصحيح هنا

		    if (opt == 1 && mode.equals("DIST")) {
		    	calc.getOptimizationGroup().selectToggle(rb);
		        break;
		    }
		    if (opt == 2 && mode.equals("TIME")) {
		    	calc.getOptimizationGroup().selectToggle(rb);
		        break;
		    }
		    if (opt == 3 && mode.equals("BOTH")) {
		    	calc.getOptimizationGroup().selectToggle(rb);
		        break;
		    }

		}




		while ((line = br.readLine()) != null) {
			String[] p = line.split("\\s+");
			if (!g.getVertices().contains(new Vertex(p[0].trim())))
				g.getVertices().add(new Vertex(p[0].trim()));

		}
		br.close();

		g.getVertices().shrink();
		
		g.getVertices().sort((a, b) -> a.getId().compareTo(b.getId()));

		br = new BufferedReader(new FileReader(file));
		br.readLine();

		while ((line = br.readLine()) != null) {
			String[] p = line.split("\\s+");

			Vertex from = binarySearch(g.getVertices(), p[0]);
			Vertex to = binarySearch(g.getVertices(), p[1]);
			double dist = Double.parseDouble(p[2]);
			double time = Double.parseDouble(p[3]);
			if (to == null && !g.getVertices().contains(new Vertex(p[1].trim()))) {
				to = new Vertex(p[1].trim());
				g.getVertices().add(to);
				g.getVertices().sort((a, b) -> a.getId().compareTo(b.getId()));
			}
			from.getAdjacents().addFirst(new Edge(dist, time, to));
		}
		calc.getSrc().setValue(binarySearch(g.getVertices(), fLine[0]));
		calc.getDest().setValue(binarySearch(g.getVertices(), fLine[1]));

		br.close();
		return g;
	}



	public Vertex binarySearch(MyList<Vertex> vertices, String goal) {

		int l = 0;
		int r = vertices.size() - 1;

		while (l <= r) {
			int mid = (l + r) / 2;
			int cmp = vertices.get(mid).getId().compareTo(goal);

			if (cmp == 0)
				return vertices.get(mid);
			else if (cmp < 0)
				l = mid + 1;
			else
				r = mid - 1;
		}
		return null;
	}

	public static void heapSort(MyList<Vertex> vertices) {
		int n = vertices.size();
		Vertex[] arr = new Vertex[n];
		for (int i = 0; i < n; i++) {
			arr[i] = vertices.get(i);
		}

		MinHeap<Vertex> heap = new MinHeap<>(n);
		heap.heapify(arr);

		for (int i = 0; i < n; i++) {
			vertices.set(i, heap.extractMin());
		}

	}

	public static void main(String[] args) {
		launch();
	}

}
