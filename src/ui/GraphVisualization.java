package ui;

import algorithms.MyList;
import algorithms.Vertex;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

public class GraphVisualization extends StackPane {
	public GraphVisualization(MyList<Vertex> list) {

		Group p = new Group();
		double lineLength = 1200 / 10;
		double curr = 0;
		boolean right = true;
		double h = 10;
		for (int i = list.size() - 1; i > 0; i--) {
			Line e;
			Circle v;
			Text label2;
			Text w = null;
			SVGPath arrowHead = new SVGPath();
			if (right) {
				curr = curr + lineLength;
				e = new Line(curr - lineLength, h, curr, h);
				v = new Circle(curr - lineLength, h, 25);
				label2 = new Text(curr - lineLength - 10, h + 5,
						String.format("%s", list.get(i).getId()));
				arrowHead = new SVGPath();
				arrowHead.setContent("M0,0 L10,5 L0,10 Z");
				arrowHead.setFill(Color.web("#F2C94C"));
				arrowHead.setLayoutX(curr - 35);
				arrowHead.setLayoutY(h - 5);
					w = new Text((e.getStartX() + e.getEndX()) / 2 -10, (e.getStartY() + e.getEndY()) / 2,
							String.format("%.2f", list.get(i-1).getDv() - list.get(i-1).getPv().getDv()));
					p.getChildren().add(w);

				
			} else {
				curr = curr - lineLength;
				e = new Line(curr + lineLength, h, curr, h);
				v = new Circle(curr + lineLength, h, 25);
				label2 = new Text(curr + lineLength - 10, h + 5,
						String.format("%s", list.get(i).getId()));
				arrowHead = new SVGPath();
				arrowHead.setContent("M0,0 L10,5 L0,10 Z");
				arrowHead.setFill(Color.web("#F2C94C"));
				arrowHead.setLayoutX(curr + 25);
				arrowHead.setLayoutY(h - 5);
				arrowHead.setRotate(180);
					w = new Text((e.getStartX() + e.getEndX()) / 2 , (e.getStartY() + e.getEndY()) / 2,
							String.format("%.2f", list.get(i-1).getDv() - list.get(i-1).getPv().getDv()));

					p.getChildren().add(w);
				
			}
			v.getStyleClass().add("vertex");
			e.getStyleClass().add("edge-path");
			label2.getStyleClass().add("vertex-label");
			arrowHead.getStyleClass().add("edge-arrow");
			p.getChildren().addAll(e, arrowHead, v, label2);
			if (curr % 1200 == 0) {
				p.getChildren().remove(arrowHead);
				h += 100;
				e = new Line(curr, h - 100, curr, h);
				e.getStyleClass().add("edge-path");
				right = !right;
				arrowHead = new SVGPath();
				arrowHead.setContent("M0,0 L10,5 L0,10 Z");
				arrowHead.setFill(Color.web("#F2C94C"));
				arrowHead.setRotate(90);
				arrowHead.setLayoutX(curr - 5);
				arrowHead.setLayoutY(h - 35);
				p.getChildren().addAll(e, arrowHead);
						}

		}
		Circle last;
		Text label2;
		if (right) {
			curr = curr + lineLength;
			last = new Circle(curr - lineLength, h, 25);
			label2 = new Text(curr - lineLength - 18, h + 5,
					String.format("%4s", list.get(0).getId()));

		} else {
			curr = curr - lineLength;
			last = new Circle(curr + lineLength, h, 25);
			label2 = new Text(curr + lineLength - 18, h + 5,
					String.format("%4s",list.get(0).getId()));
		}
		last.getStyleClass().add("vertex");
		label2.getStyleClass().add("vertex-label");
		p.getChildren().addAll(last, label2);

		this.getChildren().add(p);
		this.setAlignment(Pos.CENTER);

	}
}
