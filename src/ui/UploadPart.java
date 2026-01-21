package ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class UploadPart extends Card {
	private Button uploadFileBtn;
	public UploadPart() {
		// Upload card to upload file has the tasks

		Label uploadL = new Label("📤   Upload Tasks File");
		uploadL.getStyleClass().add("card-title");
		Label uploadDescL = new Label(
				" Upload a text file containing graph in the format: Vertex, Adjacent, Distance, Time\n\n");
		uploadDescL.getStyleClass().add("subtitle-dark");

		uploadFileBtn = new Button("📤   Choose File (tasks.txt)");
		uploadFileBtn.getStyleClass().add("button");

		TextArea fileFormatTa = new TextArea();
		infoBox(fileFormatTa,
				"📄 File Format:\n\n" + "Vertex, Adjacent, Distance, Time\n\n" + "Example:\n"
						+ "V1, V2, 2.0, 4.0\n" + "V2, V5, 5.7, 4.2\n" + "V5, V7, 2.50, 75.0\n"
						+ "V7, V6, 3.2, 8.7",
				150, 800);
		fileFormatTa.setMinHeight(160);

		this.getChildren().addAll(uploadL, uploadDescL, uploadFileBtn, fileFormatTa);
	}

	public void infoBox(TextArea ta, String content, double height, double width) {
		/*
		 * This is Function to Give the info boxes the style and sizes, built to reduce
		 * the number of lines written.
		 */
		ta.setText(content);
		ta.setEditable(false);
		ta.setWrapText(true);
		ta.getStyleClass().add("info-box");
		ta.setMaxHeight(height);
		ta.setPrefWidth(width);
	}
	public Button getUploadFileBtn() {
		return uploadFileBtn;
	}
}
