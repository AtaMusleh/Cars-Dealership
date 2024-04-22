import java.io.*;
import java.io.FileNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.text.Text;
import javafx.stage.*;
//import javafx.stage.Stage;

public class InterFace extends Application {
	brandLinkedList carInventory;
	Queue queue;
	Stack stack;
	Stage mainStage;
	Scene scene;
	brandNode a;
	Label joker = new Label();
	Button customer = new Button("Customer");
	private Button orderFile = new Button();
	private Button carsB = new Button();
	private Button orderB = new Button();
	private Button saveB = new Button();
	private Button searchB = new Button();
	private Button report = new Button();
	private Order firstOrder;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		customer.setDisable(true);
		orderFile.setDisable(true);
		carsB.setDisable(true);
		orderB.setDisable(true);
		saveB.setDisable(true);
		searchB.setDisable(true);
		report.setDisable(true);
		customer.setText("Sorry, there is \nno cars right now");
		mainStage.setTitle("Car Agency  System");
		this.mainStage = mainStage;

		mainScreen();

	}

	/*
	 * The showCarsScreen() method sets up a screen interface for modifying cars in
	 * the car inventory. It creates a graphical user interface with buttons for
	 * inserting, deleting, and updating cars. The method displays a title label,
	 * buttons, and a background image. Users can click the buttons to navigate to
	 * different screens for performing the respective car modification operations.
	 * The method also handles the actions when the buttons are clicked and
	 * redirects the user to the corresponding screens.
	 */
	private void showCarsScreen() {
		// Load and display the main image
		Image image = new Image("a.jpg");
		ImageView imageView = new ImageView(image);

		// Load and display the back button image
		Image image2 = new Image("back.png");
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(50);
		imageView2.setFitHeight(50);

		// Create the back button
		Button esc = new Button();
		esc.setGraphic(imageView2);
		esc.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

		// Create the title label
		Label top = new Label("Modify The cars");
		top.setFont(new Font(24));

		// Create the buttons for insert, delete, and update
		Button insert = new Button("Insert");
		Button delete = new Button("Delete");
		Button update = new Button("Update");
		insert.setPrefWidth(200);
		insert.setPrefHeight(60);
		insert.setFont(new Font(20));
		delete.setPrefWidth(200);
		delete.setPrefHeight(60);
		delete.setFont(new Font(20));
		update.setPrefWidth(200);
		update.setPrefHeight(60);
		update.setFont(new Font(20));
		insert.setStyle("-fx-background-radius: 50;");
		delete.setStyle("-fx-background-radius: 50;");
		update.setStyle("-fx-background-radius: 50;");

		// Create a vertical box to hold the buttons
		VBox vb = new VBox(10);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(insert, delete, update);

		// Create a horizontal box to hold the back button
		HBox hp = new HBox(10);
		hp.setAlignment(Pos.TOP_LEFT);
		hp.getChildren().add(esc);

		// Create a stack pane to stack the title label and the back button horizontally
		StackPane topStackPane = new StackPane();
		topStackPane.getChildren().addAll(top, hp);
		StackPane.setAlignment(hp, Pos.TOP_LEFT);
		StackPane.setAlignment(top, Pos.TOP_CENTER);

		// Handle the action when the back button is clicked
		esc.setOnAction(e1 -> adminMainScreen());

		// Create a border pane and set its components
		BorderPane bp = new BorderPane();
		bp.getChildren().add(imageView);
		bp.setTop(topStackPane);
		bp.setCenter(vb);

		// Create a scene with the border pane and set it on the main stage
		Scene scene = new Scene(bp, 500, 400);
		mainStage.setScene(scene);
		mainStage.show();

		// Handle the actions when the insert, update, and delete buttons are clicked
		insert.setOnAction(e1 -> {
			showInsertScreen();
		});

		update.setOnAction(e2 -> {
			showUpdateScreen();
		});

		delete.setOnAction(e3 -> {
			showDeleteScreen();
		});

	}

	/*
	 * The showInsertScreen() method creates a user interface for inserting new
	 * brands or models into a car inventory. It allows users to enter brand or
	 * model information and validates the input before adding it to the inventory.
	 * The method provides an intuitive interface with input fields and buttons for
	 * a seamless insertion process.
	 */
	private void showInsertScreen() {
		Image image = new Image("a.jpg");
		ImageView imageView = new ImageView(image);
		Image image2 = new Image("back.png");
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(50);
		imageView2.setFitHeight(50);
		Button esc = new Button();
		esc.setGraphic(imageView2);
		esc.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		Label top1 = new Label("Insert new Brand or Model");
		top1.setFont(new Font(24));
		Button insertB = new Button("Brand");
		Button insertM = new Button("Model");
		insertB.setPrefWidth(200);
		insertB.setPrefHeight(60);
		insertB.setFont(new Font(20));
		insertM.setPrefWidth(200);
		insertM.setPrefHeight(60);
		insertM.setFont(new Font(20));
		insertB.setOnAction(e -> {
			Label top = new Label("Insert a new Brand");
			top.setFont(new Font(26));
			TextField ta = new TextField();
			ta.setPromptText("New Brand");
			Button insert = new Button("Insert");
			insert.setPrefWidth(120);
			insert.setPrefHeight(40);
			insert.setFont(new Font(16));
			insert.setOnAction(e1 -> {
				String newBrand = ta.getText();

				if (newBrand != null && !newBrand.isEmpty()) {
					brandNode existingBrand = carInventory.search(newBrand);

					if (existingBrand == null) {
						// Brand doesn't exist, add it to the linked list
						carInventory.insert(new brandNode(new Brand(newBrand)));
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setTitle("Success");
						successAlert.setHeaderText("Brand Inserted");
						successAlert.setContentText("The brand '" + newBrand + "' has been inserted successfully.");
						successAlert.showAndWait();
						ta.setText(null);
					} else {
						// Brand already exists
						Alert errorAlert = new Alert(Alert.AlertType.ERROR);
						errorAlert.setTitle("Error");
						errorAlert.setHeaderText("Brand Already Exists");
						errorAlert.setContentText("The brand '" + newBrand + "' already exists.");
						errorAlert.showAndWait();
						ta.setText(null);
					}
				}

			});
			VBox v = new VBox(10);
			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(top, ta, insert);
			HBox hp1 = new HBox(10);
			hp1.setAlignment(Pos.TOP_LEFT);
			hp1.getChildren().add(esc);
			StackPane topStackPane1 = new StackPane();
			topStackPane1.getChildren().addAll(hp1);
			StackPane.setAlignment(hp1, Pos.TOP_LEFT);
			// StackPane.setAlignment(top, Pos.TOP_CENTER);
			esc.setOnAction(e12 -> showCarsScreen());
			BorderPane bp = new BorderPane();
			bp.getChildren().add(imageView);
			bp.setCenter(v);
			bp.setTop(topStackPane1);
			scene = new Scene(bp, 500, 400);
			mainStage.setScene(scene);
			mainStage.show();
		});
		insertM.setOnAction(e -> {
			Label top = new Label("Insert a new Model");
			top.setFont(new Font(26));

			TextField brandField = new TextField();
			brandField.setPromptText("Brand");

			TextField modelField = new TextField();
			modelField.setPromptText("Model");

			TextField yearField = new TextField();
			yearField.setPromptText("Year");

			TextField colorField = new TextField();
			colorField.setPromptText("Color");

			TextField priceField = new TextField();
			priceField.setPromptText("Price");

			Button insert = new Button("Insert");
			insert.setPrefWidth(120);
			insert.setPrefHeight(40);
			insert.setFont(new Font(16));

			insert.setOnAction(e1 -> {
				String brand = brandField.getText();
				String model = modelField.getText();
				int year = 0;
				String yearText = yearField.getText();
				String color = colorField.getText();
				String price = priceField.getText();

				// Validate year input
				try {
					year = Integer.parseInt(yearText);
				} catch (NumberFormatException ex) {
					Alert errorAlert = new Alert(Alert.AlertType.ERROR);
					errorAlert.setTitle("Error");
					errorAlert.setHeaderText("Invalid Year");
					errorAlert.setContentText("Please enter a valid year as an integer.");
					errorAlert.showAndWait();
					return;
				}

				if (brand != null && !brand.isEmpty() && model != null && !model.isEmpty() && color != null
						&& !color.isEmpty() && price != null && !price.isEmpty()) {

					// Search for the brand node
					brandNode existingBrand = carInventory.search(brand);

					if (existingBrand == null) {
						// Brand doesn't exist, add it to the linked list
						existingBrand = new brandNode(new Brand(brand));
						carInventory.insert(existingBrand);
					}
					// double price = parsePrice(priceText);
					// Create the new car model
					Car newCar = new Car(model, year, color, price);

					// Add the car to the brand's cars list
					existingBrand.getBrand().getCars().insert(new carNode(newCar));

					Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
					successAlert.setTitle("Success");
					successAlert.setHeaderText("Model Inserted");
					successAlert.setContentText("The model '" + model + "' has been inserted successfully.");
					successAlert.showAndWait();

					brandField.setText(null);
					modelField.setText(null);
					yearField.setText(null);
					colorField.setText(null);
					priceField.setText(null);
				}

			});

			VBox v = new VBox(10);
			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(top, brandField, modelField, yearField, colorField, priceField, insert);

			HBox hp1 = new HBox(10);
			hp1.setAlignment(Pos.TOP_LEFT);
			hp1.getChildren().add(esc);

			StackPane topStackPane1 = new StackPane();
			topStackPane1.getChildren().addAll(hp1);
			StackPane.setAlignment(hp1, Pos.TOP_LEFT);

			esc.setOnAction(e12 -> showCarsScreen());

			BorderPane bp = new BorderPane();
			bp.getChildren().add(imageView);
			bp.setCenter(v);
			bp.setTop(topStackPane1);

			scene = new Scene(bp, 500, 400);
			mainStage.setScene(scene);
			mainStage.show();

		});

		VBox v = new VBox(10);
		v.getChildren().addAll(insertB, insertM);
		v.setAlignment(Pos.CENTER);
		HBox hp1 = new HBox(10);
		hp1.setAlignment(Pos.TOP_LEFT);
		hp1.getChildren().add(esc);
		StackPane topStackPane1 = new StackPane();
		topStackPane1.getChildren().addAll(top1, hp1);
		StackPane.setAlignment(hp1, Pos.TOP_LEFT);
		StackPane.setAlignment(top1, Pos.TOP_CENTER);
		esc.setOnAction(e12 -> showCarsScreen());
		BorderPane bp1 = new BorderPane();
		bp1.getChildren().add(imageView);
		bp1.setTop(topStackPane1);
		bp1.setCenter(v);
		scene = new Scene(bp1, 500, 400);
		mainStage.setScene(scene);
		mainStage.show();
	}

	/*
	 * The showUpdateScreen() method displays a screen where the user can update
	 * brand or model information in a car inventory system. It provides UI elements
	 * such as combo boxes and text fields for selecting and entering new
	 * information. The method handles the update process and updates the inventory
	 * accordingly.
	 */
	private void showUpdateScreen() {
		Image image = new Image("a.jpg");
		ImageView imageView = new ImageView(image);
		Image image2 = new Image("back.png");
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(50);
		imageView2.setFitHeight(50);
		Button esc = new Button();
		esc.setGraphic(imageView2);
		esc.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		Label top2 = new Label("Update a Brand or a Model");
		top2.setFont(new Font(24));
		Button updateB = new Button("Brand");
		Button updateM = new Button("Model");
		updateB.setOnAction(e -> {
			Label top = new Label("Update any brand you want");
			TextField tf = new TextField();
			tf.setPromptText("New Brand Name");
			top.setFont(new Font(20));
			tf.setDisable(true);
			ComboBox<String> brandC = new ComboBox<>();
			brandC.setPromptText("Select a brand");
			brandC.setEditable(true);
			ObservableList<String> allItems = FXCollections.observableArrayList();
			try {

				a = carInventory.getFront();
				while (a != null) {
					String brand = a.getBrand().getBrand();
					if (brand != null && !brand.isEmpty()) {
						allItems.add(brand);
					}
					a = a.getNext();
				}
				FXCollections.sort(allItems);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			brandC.setItems(allItems);
			brandC.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue == null || newValue.isEmpty()) {
					tf.setDisable(true);
				} else {
					tf.setDisable(false);
				}
			});

			brandC.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
				String enteredText = newValue.toLowerCase();

			});

			Button updateButton = new Button("Update");
			updateButton.setOnAction(e1 -> {
				if (brandC.getValue() != null && tf.getText() != null) {
					String brandS = brandC.getValue();
					String brandNew = tf.getText();
					Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");
					confirmationDialog.setHeaderText("Update Brand");
					confirmationDialog.setContentText("Are you sure you want to Update the brand?");

					Optional<ButtonType> result = confirmationDialog.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						if (brandC.getValue() != null && tf.getText() != null) {
							carInventory.update(brandS, brandNew);
							allItems.remove(brandS);
							allItems.add(brandNew.toUpperCase());
							FXCollections.sort(allItems);
							brandC.setValue(null);
							tf.setText(null);
						}
					} else {

					}

				} else {

				}
			});
			updateButton.setPrefWidth(100);
			updateButton.setPrefHeight(30);
			updateButton.setFont(new Font(17));
			VBox v1 = new VBox(10);
			v1.getChildren().addAll(brandC, tf);
			v1.setAlignment(Pos.CENTER);
			VBox vb = new VBox(10);
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(v1, updateButton);
			HBox hp1 = new HBox(10);
			hp1.setAlignment(Pos.TOP_LEFT);
			hp1.getChildren().add(esc);
			StackPane topStackPane1 = new StackPane();
			topStackPane1.getChildren().addAll(top2, hp1);
			StackPane.setAlignment(hp1, Pos.TOP_LEFT);
			StackPane.setAlignment(top2, Pos.TOP_CENTER);
			esc.setOnAction(e12 -> showCarsScreen());
			BorderPane bp = new BorderPane();
			bp.getChildren().add(imageView);
			bp.setTop(topStackPane1);
			bp.setCenter(vb);
			scene = new Scene(bp, 500, 400);
			mainStage.setScene(scene);
			mainStage.show();
		});
		updateM.setOnAction(e1 -> {
			Label top = new Label("Update any model you want");
			top.setFont(new Font(20));
			TextField modelTF = new TextField();
			modelTF.setPromptText("New Model");
			modelTF.setDisable(true);

			TextField yearTF = new TextField();
			yearTF.setPromptText("New Year");
			yearTF.setDisable(true);

			TextField colorTF = new TextField();
			colorTF.setPromptText("New Color");
			colorTF.setDisable(true);

			TextField priceTF = new TextField();
			priceTF.setPromptText("New Price");
			priceTF.setDisable(true);

			Button updateButton = new Button("Update");
			ComboBox<String> brandC = new ComboBox<>();
			brandC.setPromptText("Select a brand");

			ComboBox<String> modelC = new ComboBox<>();
			modelC.setPromptText("Select a model");

			// Set up the ComboBoxes based on the existing data
			try {
				brandNode brandNode = carInventory.getFront();
				while (brandNode != null) {
					String brand = brandNode.getBrand().getBrand();
					if (brand != null && !brand.isEmpty()) {
						brandC.getItems().add(brand);
					}
					brandNode = brandNode.getNext();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			brandC.valueProperty().addListener((observable, oldValue, newValue) -> {
				modelC.getItems().clear();
				if (newValue != null) {
					brandNode brandNode = carInventory.search(newValue);
					if (brandNode != null) {
						// LinkedList cars = brandNode.getBrand().getCars();
						carNode carN = brandNode.getBrand().getCars().getFront();
						while (carN != null) {
							modelC.getItems().add(carN.getCar().getModel() + "," + carN.getCar().getYear() + ","
									+ carN.getCar().getColor() + "," + carN.getCar().getPrice());
							carN = carN.getNext();
						}
					}
				}
			});

			modelC.valueProperty().addListener((observable, oldValue, newValue) -> {
				boolean disableFields = brandC.getValue() == null || modelC.getValue() == null;
				modelTF.setDisable(disableFields);
				yearTF.setDisable(disableFields);
				colorTF.setDisable(disableFields);
				priceTF.setDisable(disableFields);
			});

			updateButton.setOnAction(e2 -> {
				String brand = brandC.getValue();
				String newModel = modelTF.getText();
				String newYear = yearTF.getText();
				String newColor = colorTF.getText();
				String newPrice = priceTF.getText();
				String[] data = modelC.getValue().split(",");
				String oldM = data[0];
				String oldY = data[1];
				String oldC = data[2];
				String oldP = data[3];

				if (brand != null && brandC.getValue() != null && newModel != null && newYear != null
						&& newColor != null && newPrice != null) {
					brandNode existingBrandNode = carInventory.search(brand);
					if (existingBrandNode != null) {
						carNode existingCar = existingBrandNode.getBrand().getCars()
								.search(new Car(oldM, Integer.parseInt(oldY), oldC, oldP));
						if (existingCar != null) {
							// Update the car model with the new specs
							existingCar.getCar().setModel(newModel);
							existingCar.getCar().setYear(Integer.parseInt(newYear));
							existingCar.getCar().setColor(newColor);
							existingCar.getCar().setPrice(newPrice);

							Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
							successAlert.setTitle("Success");
							successAlert.setHeaderText("Model Updated");
							successAlert.setContentText("The model has been updated successfully.");
							successAlert.showAndWait();

							// Clear the input fields
							brandC.setValue(null);
							modelC.setValue(null);
							modelTF.setText(null);
							yearTF.setText(null);
							colorTF.setText(null);
							priceTF.setText(null);
						}
					}
				}
			});

			VBox v1 = new VBox(10);
			v1.getChildren().addAll(brandC, modelC, modelTF, yearTF, colorTF, priceTF);
			v1.setAlignment(Pos.CENTER);

			VBox vb = new VBox(10);
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(top, v1, updateButton);

			HBox hp1 = new HBox(10);
			hp1.setAlignment(Pos.TOP_LEFT);
			hp1.getChildren().add(esc);

			StackPane topStackPane1 = new StackPane();
			topStackPane1.getChildren().addAll(top, hp1);
			StackPane.setAlignment(hp1, Pos.TOP_LEFT);
			StackPane.setAlignment(top, Pos.TOP_CENTER);

			esc.setOnAction(e12 -> showCarsScreen());

			BorderPane bp = new BorderPane();
			bp.getChildren().add(imageView);
			bp.setTop(topStackPane1);
			bp.setCenter(vb);

			scene = new Scene(bp, 500, 400);
			mainStage.setScene(scene);
			mainStage.show();
		});
		updateB.setPrefWidth(200);
		updateB.setPrefHeight(60);
		updateB.setFont(new Font(20));
		updateM.setPrefWidth(200);
		updateM.setPrefHeight(60);
		updateM.setFont(new Font(20));
		VBox v = new VBox(10);
		v.getChildren().addAll(updateB, updateM);
		v.setAlignment(Pos.CENTER);
		HBox hp1 = new HBox(10);
		hp1.setAlignment(Pos.TOP_LEFT);
		hp1.getChildren().add(esc);
		StackPane topStackPane1 = new StackPane();
		topStackPane1.getChildren().addAll(top2, hp1);
		StackPane.setAlignment(hp1, Pos.TOP_LEFT);
		StackPane.setAlignment(top2, Pos.TOP_CENTER);
		esc.setOnAction(e12 -> showCarsScreen());
		BorderPane bp1 = new BorderPane();
		bp1.getChildren().add(imageView);
		bp1.setTop(topStackPane1);
		bp1.setCenter(v);
		scene = new Scene(bp1, 500, 400);
		mainStage.setScene(scene);
		mainStage.show();

	}

	/*
	 * The showDeleteScreen() method creates a user interface for deleting brands or
	 * models from a car inventory. It allows users to select the brand or model
	 * they want to delete and confirms the deletion with a dialog box. The method
	 * provides a smooth deletion process and includes a back button to return to
	 * the previous screen.
	 */
	private void showDeleteScreen() {
		Image image = new Image("a.jpg");
		ImageView imageView = new ImageView(image);
		Image image2 = new Image("back.png");
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(50);
		imageView2.setFitHeight(50);
		Button esc = new Button();
		esc.setGraphic(imageView2);
		esc.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		Label top3 = new Label("Delete a Brand or a Model");
		top3.setFont(new Font(24));
		Button deleteB = new Button("Brand");
		Button deleteM = new Button("Model");
		deleteB.setOnAction(e -> {
			Label top = new Label("Delete any brand you want");
			top.setFont(new Font(20));
			ComboBox<String> brandC = new ComboBox<>();
			brandC.setPromptText("Select a brand");
			brandC.setEditable(true);
			ObservableList<String> allItems = FXCollections.observableArrayList();
			try {

				a = carInventory.getFront();
				while (a != null) {
					String brand = a.getBrand().getBrand();
					if (brand != null && !brand.isEmpty()) {
						allItems.add(brand);
					}
					a = a.getNext();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			brandC.setItems(allItems);

			brandC.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
				String enteredText = newValue.toLowerCase();

			});

			Button deleteButton = new Button("Delete");
			deleteButton.setOnAction(e1 -> {
				if (brandC.getValue() != null) {
					String brandS = brandC.getValue();
					Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");
					confirmationDialog.setHeaderText("Delete Brand");
					confirmationDialog.setContentText("Are you sure you want to delete the brand?");

					Optional<ButtonType> result = confirmationDialog.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						brandNode deleteNode = carInventory.search(brandS);
						if (deleteNode != null) {
							carInventory.delete(deleteNode);
							allItems.remove(brandS);
							brandC.setValue(null);
						}
					} else {

					}

				}
			});
			deleteButton.setPrefWidth(100);
			deleteButton.setPrefHeight(30);
			deleteButton.setFont(new Font(17));
			VBox vb = new VBox(10);
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(brandC, deleteButton);
			HBox hp1 = new HBox(10);
			hp1.setAlignment(Pos.TOP_LEFT);
			hp1.getChildren().add(esc);
			StackPane topStackPane1 = new StackPane();
			topStackPane1.getChildren().addAll(top3, hp1);
			StackPane.setAlignment(hp1, Pos.TOP_LEFT);
			StackPane.setAlignment(top3, Pos.TOP_CENTER);
			esc.setOnAction(e12 -> showCarsScreen());
			BorderPane bp = new BorderPane();
			bp.getChildren().add(imageView);
			bp.setTop(topStackPane1);
			bp.setCenter(vb);
			scene = new Scene(bp, 500, 400);
			mainStage.setScene(scene);
			mainStage.show();
		});

		deleteM.setOnAction(e2 -> {
			Label top = new Label("Delete any Model you want");
			top.setFont(new Font(20));
			ComboBox<String> brandC = new ComboBox<>();
			ComboBox<String> modelC = new ComboBox<>();
			brandC.setPromptText("Select a brand");
			brandC.setEditable(true);
			modelC.setPromptText("Select the model");
			modelC.setEditable(true);
			ObservableList<String> allItems = FXCollections.observableArrayList();

			try {
				a = carInventory.getFront();
				while (a != null) {
					String brand = a.getBrand().getBrand();
					brandC.getItems().add(brand);
					allItems.add(brand);
					a = a.getNext();
				}

				brandC.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
					String enteredText = newValue.toLowerCase();

				});

				brandC.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
					modelC.getItems().clear();
					if (newvalue != null) {
						a = carInventory.search(newvalue);
						if (a != null) {
							carNode curr = a.getBrand().getCars().getFront();
							while (curr != null) {
								modelC.getItems().add(curr.getCar().getModel() + "," + curr.getCar().getYear() + ","
										+ curr.getCar().getColor() + "," + curr.getCar().getPrice());
								curr = curr.getNext();
							}
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

			Button deleteButton = new Button("Delete");
			deleteButton.setOnAction(e1 -> {
				if (brandC.getValue() != null && modelC.getValue() != null) {
					String brand = brandC.getValue();
					String[] data = modelC.getValue().split(",");
					String model = data[0];
					String year = data[1];
					String color = data[2];
					String price = data[3];
					Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");
					confirmationDialog.setHeaderText("Delete Brand");
					confirmationDialog.setContentText("Are you sure you want to delete the brand?");

					Optional<ButtonType> result = confirmationDialog.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {

						brandC.setValue(null);
						try {
							Car car = new Car(model, Integer.parseInt(year), color, price);
							brandNode b = carInventory.search(brand);
							carNode ca = b.getBrand().getCars().search(car);

							if (ca != null) {
//								if (b.getBrand().getCars().search(car) != null) {
								// b.getBrand().getCars().delete(car);
								b.getBrand().getCars().delete(car);
								modelC.getItems().clear();
								// }
							}

						} catch (Exception e3) {
						}

					}
				}
			});
			deleteButton.setPrefWidth(100);
			deleteButton.setPrefHeight(30);
			deleteButton.setFont(new Font(17));
			VBox v = new VBox(10);
			v.getChildren().addAll(brandC, modelC);
			v.setAlignment(Pos.CENTER);
			VBox vb = new VBox(10);
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(v, deleteButton);
			HBox hp1 = new HBox(10);
			hp1.setAlignment(Pos.TOP_LEFT);
			hp1.getChildren().add(esc);
			StackPane topStackPane1 = new StackPane();
			topStackPane1.getChildren().addAll(top3, hp1);
			StackPane.setAlignment(hp1, Pos.TOP_LEFT);
			StackPane.setAlignment(top3, Pos.TOP_CENTER);
			esc.setOnAction(e12 -> showCarsScreen());
			BorderPane bp = new BorderPane();
			bp.getChildren().add(imageView);
			bp.setTop(topStackPane1);
			bp.setCenter(vb);
			scene = new Scene(bp, 500, 400);
			mainStage.setScene(scene);
			mainStage.show();
		});

		deleteB.setPrefWidth(200);
		deleteB.setPrefHeight(60);
		deleteB.setFont(new Font(20));
		deleteM.setPrefWidth(200);
		deleteM.setPrefHeight(60);
		deleteM.setFont(new Font(20));
		VBox v = new VBox(10);
		v.getChildren().addAll(deleteB, deleteM);
		v.setAlignment(Pos.CENTER);
		HBox hp1 = new HBox(10);
		hp1.setAlignment(Pos.TOP_LEFT);
		hp1.getChildren().add(esc);
		StackPane topStackPane1 = new StackPane();
		topStackPane1.getChildren().addAll(top3, hp1);
		StackPane.setAlignment(hp1, Pos.TOP_LEFT);
		StackPane.setAlignment(top3, Pos.TOP_CENTER);
		esc.setOnAction(e12 -> showCarsScreen());
		BorderPane bp1 = new BorderPane();
		bp1.getChildren().add(imageView);
		bp1.setTop(topStackPane1);
		bp1.setCenter(v);
		scene = new Scene(bp1, 500, 400);
		mainStage.setScene(scene);
		mainStage.show();
	}

	/*
	 * The adminMainScreen() method sets up the main interface for the admin section
	 * of the car agency application. It displays a background image and several
	 * buttons, including "Choose a file to add cars,"
	 * "Choose a file to add orders," "Report," "Orders," "Save to a file," and
	 * "Available cars."
	 * 
	 * The method also includes event handlers for the buttons. The
	 * "Choose a file to add cars" button prompts the admin to select a file
	 * containing car data, which is then read and processed. The other buttons are
	 * not provided with their respective event handlers in the given code snippet.
	 * 
	 * Overall, this method establishes the admin's main screen, allowing them to
	 * perform various actions related to car management and data processing.
	 */
	public void adminMainScreen() {
		Image image2 = new Image("back.png");
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(50);
		imageView2.setFitHeight(50);
		Button esc = new Button();
		esc.setGraphic(imageView2);
		esc.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		esc.setOnAction(e -> mainScreen());
		Image image = new Image("a.jpg");
		ImageView imageView = new ImageView(image);
		Button carFile = new Button("Choose a file \nto add cars");
		orderFile.setText("Choose a file\nto add orders");
		report.setText("Report");
		orderB.setText("Orders");
		saveB.setText("Save to a file");
		searchB.setText("Available cars");
		// carsB = new Button();
		Text carsLabel = new Text("Cars");

		carFile.setStyle("-fx-background-radius: 50;");
		orderFile.setStyle("-fx-background-radius: 50;");
		orderB.setStyle("-fx-background-radius: 50;");
		saveB.setStyle("-fx-background-radius: 50;");
		searchB.setStyle("-fx-background-radius: 50;");
		carsB.setStyle("-fx-background-radius: 50;");
		report.setStyle("-fx-background-radius: 50;");
		carFile.setOnAction(e -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose a file");
			FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(txtFilter);
			File selectedFile = fileChooser.showOpenDialog(mainStage);

			if (selectedFile != null) {
				orderFile.setDisable(false);
				carsB.setDisable(false);
				orderB.setDisable(false);
				saveB.setDisable(false);
				searchB.setDisable(false);
				report.setDisable(false);
				customer.setDisable(false);
				customer.setText("Customer");
				customer.setFont(new Font(20));
				try {
					Scanner scanner = new Scanner(selectedFile);
					queue = new Queue();
					stack = new Stack();
					carInventory = new brandLinkedList();
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						String[] data = line.split(",");

						if (data.length != 5) {
							if (data.length == 0 || line.trim().isEmpty()) {
								continue;
							} else {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("Invalid line format");
								alert.setContentText("The line does not have the expected number of fields.");
								alert.showAndWait();
								break;
							}
						}

						if (data.length == 5) {
							String brand = data[0].trim();
							String model = data.length > 1 ? data[1].trim() : "";
							String year = data.length > 2 ? data[2].trim() : "";
							String color = data.length > 3 ? data[3].trim() : "";
							String price = data.length > 4 ? data[4].trim() : "";
							joker.setText("The choosen file is: " + selectedFile);
							a = carInventory.getFront();
							boolean brandExists = false;

							while (a != null) {
								if (a.getBrand().getBrand().equalsIgnoreCase(brand)) {
									brandExists = true;
									break;
								}
								a = a.getNext();
							}

							if (!brandExists) {
								a = new brandNode(new Brand(brand));
								carInventory.insert(a);
							}

							try {
								Car car = new Car(model, Integer.parseInt(year), color, price);
								a.getBrand().getCars().insert(new carNode(car));

							} catch (NumberFormatException e1) {

							}
						}
					}

					scanner.close();

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("File Read Successful");
					alert.setHeaderText("File Read Successfully");
					alert.setContentText("The file \"" + selectedFile.getName() + "\" has been read successfully.");
					alert.showAndWait();
				} catch (FileNotFoundException e1) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("File not found");
					alert.setContentText("The selected file was not found.");
					alert.showAndWait();
				}
			} else {
				orderFile.setDisable(true);
				carsB.setDisable(true);
				orderB.setDisable(true);
				saveB.setDisable(true);
				searchB.setDisable(true);
				report.setDisable(true);
				customer.setDisable(true);
				joker.setText(null);
			}
		});
		orderB.setOnAction(e -> {
			if (!queue.isEmpty()) {
				Label top = new Label("Orders");
				top.setFont(new Font(20));
				Label orderLeft = new Label();
				Label cusInfo = new Label("Customer Info");
				Label carInfo = new Label("Car Info");
				Label nameC = new Label("Customer Name: ");
				Label numC = new Label("Customer Number: ");
				Label dateO = new Label("Date of Order");
				Label brandO = new Label("Car Brand");
				Label modelO = new Label("Car Model");
				Label yearO = new Label("Year of made");
				Label colorO = new Label("Car Color");
				Label priceO = new Label("Car Price");
				cusInfo.setFont(new Font(17));
				carInfo.setFont(new Font(17));
				nameC.setFont(new Font(17));
				numC.setFont(new Font(17));
				dateO.setFont(new Font(17));
				brandO.setFont(new Font(17));
				modelO.setFont(new Font(17));
				yearO.setFont(new Font(17));
				colorO.setFont(new Font(17));
				priceO.setFont(new Font(17));
				orderLeft.setFont(new Font(17));
				TextField nameF = new TextField();
				nameF.setEditable(false);
				TextField numF = new TextField();
				numF.setEditable(false);
				TextField brandF = new TextField();
				brandF.setEditable(false);
				TextField modelF = new TextField();
				modelF.setEditable(false);
				TextField yearF = new TextField();
				yearF.setEditable(false);
				TextField colorF = new TextField();
				colorF.setEditable(false);
				TextField priceF = new TextField();
				priceF.setEditable(false);
				TextField dateF = new TextField();
				dateF.setEditable(false);
				Button cancle = new Button("Cancle");
				Button accept = new Button("Accept");
				Button delay = new Button("Delay");
				cancle.setStyle("-fx-background-radius: 50;");
				accept.setStyle("-fx-background-radius: 50;");
				delay.setStyle("-fx-background-radius: 50;");
				firstOrder = queue.front();
				orderLeft.setText(queue.getSize() + " Order/s left");
				if (firstOrder != null) {
					// Set the order information in the text fields
					nameF.setText(firstOrder.getName());
					numF.setText(firstOrder.getNumber() + " ");
					brandF.setText(firstOrder.getBrand());
					modelF.setText(firstOrder.getModel());
					colorF.setText(firstOrder.getColor());
					priceF.setText(String.valueOf(firstOrder.getPrice()));
					yearF.setText(String.valueOf(firstOrder.getYear()));
					dateF.setText(firstOrder.getDate().toString());
				}
				if (queue.isEmpty()) {
					accept.setDisable(true);
					cancle.setDisable(true);
					delay.setDisable(true);
				}
				accept.setOnAction(event -> {
					if (queue.isEmpty()) {
						accept.setDisable(true);
						cancle.setDisable(true);
						delay.setDisable(true);
						nameF.clear();
						numF.clear();
						brandF.clear();
						modelF.clear();
						colorF.clear();
						priceF.clear();
						yearF.clear();
						dateF.clear();

					} else if (firstOrder != null) {
						// Remove the first order from the queue
						queue.deQueue();
						// Add the order to the stack

						Car car = new Car(firstOrder.getModel(), firstOrder.getYear(), firstOrder.getColor(),
								firstOrder.getPrice());
						brandNode b = carInventory.search(firstOrder.getBrand());

						if (b != null && b.getBrand().getCars() != null) {
							b.getBrand().getCars().delete(car);
						}
						stack.push(firstOrder);
						// Clear the text fields
						nameF.clear();
						numF.clear();
						brandF.clear();
						modelF.clear();
						colorF.clear();
						priceF.clear();
						yearF.clear();
						dateF.clear();
						if (queue.front != null) {
							firstOrder = queue.front();
						} else {
							accept.setDisable(true);
							cancle.setDisable(true);
							delay.setDisable(true);
							nameF.clear();
							numF.clear();
							brandF.clear();
							modelF.clear();
							colorF.clear();
							priceF.clear();
							yearF.clear();
							dateF.clear();
						}

						if (firstOrder != null) {
							// Set the order information in the text fields
							nameF.setText(firstOrder.getName());
							numF.setText(firstOrder.getNumber() + " ");
							brandF.setText(firstOrder.getBrand());
							modelF.setText(firstOrder.getModel());
							colorF.setText(firstOrder.getColor());
							priceF.setText(String.valueOf(firstOrder.getPrice()));
							yearF.setText(String.valueOf(firstOrder.getYear()));
							dateF.setText(firstOrder.getDate().toString());
							orderLeft.setText(queue.getSize() + " Order/s left");
						}
					}

				});

				// Cancel button action
				cancle.setOnAction(event -> {
					if (queue.isEmpty()) {
						accept.setDisable(true);
						cancle.setDisable(true);
						delay.setDisable(true);
						nameF.clear();
						numF.clear();
						brandF.clear();
						modelF.clear();
						colorF.clear();
						priceF.clear();
						yearF.clear();
						dateF.clear();
					} else if (firstOrder != null) {
						// Remove the first order from the queue
						queue.deQueue();

						// Clear the text fields
						nameF.clear();
						numF.clear();
						brandF.clear();
						modelF.clear();
						colorF.clear();
						priceF.clear();
						yearF.clear();
						dateF.clear();
						if (queue.front != null) {
							firstOrder = queue.front();
						} else {
							accept.setDisable(true);
							cancle.setDisable(true);
							delay.setDisable(true);
							nameF.clear();
							numF.clear();
							brandF.clear();
							modelF.clear();
							colorF.clear();
							priceF.clear();
							yearF.clear();
							dateF.clear();
						}

						if (firstOrder != null) {
							// Set the order information in the text fields
							nameF.setText(firstOrder.getName());
							numF.setText(firstOrder.getNumber() + "");
							brandF.setText(firstOrder.getBrand());
							modelF.setText(firstOrder.getModel());
							colorF.setText(firstOrder.getColor());
							priceF.setText(String.valueOf(firstOrder.getPrice()));
							yearF.setText(String.valueOf(firstOrder.getYear()));
							dateF.setText(firstOrder.getDate().toString());
							orderLeft.setText(queue.getSize() + " Order/s left");
						}
					}
				});
				delay.setOnAction(event -> {

					if (queue.isEmpty()) {
						accept.setDisable(true);
						cancle.setDisable(true);
						delay.setDisable(true);
						nameF.clear();
						numF.clear();
						brandF.clear();
						modelF.clear();
						colorF.clear();
						priceF.clear();
						yearF.clear();
						dateF.clear();

					} else if (firstOrder != null) {

						queue.deQueue();
						// Add the order to the stack
						queue.enQueue(firstOrder);

						// Clear the text fields
						nameF.clear();
						numF.clear();
						brandF.clear();
						modelF.clear();
						colorF.clear();
						priceF.clear();
						yearF.clear();
						dateF.clear();
						if (queue.front != null) {
							firstOrder = queue.front();
						} else {
							accept.setDisable(true);
							cancle.setDisable(true);
							delay.setDisable(true);
							nameF.clear();
							numF.clear();
							brandF.clear();
							modelF.clear();
							colorF.clear();
							priceF.clear();
							yearF.clear();
							dateF.clear();
						}

						if (firstOrder != null) {
							// Set the order information in the text fields
							nameF.setText(firstOrder.getName());
							numF.setText(firstOrder.getNumber() + " ");
							brandF.setText(firstOrder.getBrand());
							modelF.setText(firstOrder.getModel());
							colorF.setText(firstOrder.getColor());
							priceF.setText(String.valueOf(firstOrder.getPrice()));
							yearF.setText(String.valueOf(firstOrder.getYear()));
							dateF.setText(firstOrder.getDate().toString());
							orderLeft.setText(queue.getSize() + " Order/s left");
						}
					}
				});

				HBox h3 = new HBox(10);
				h3.setAlignment(Pos.CENTER);
				h3.getChildren().addAll(accept, cancle, delay);
				HBox h1 = new HBox(10);
				h1.setAlignment(Pos.CENTER);
				h1.getChildren().addAll(cusInfo, carInfo);
				h1.setPadding(new Insets(20));
				GridPane gp1 = new GridPane();
				gp1.add(nameC, 0, 0);
				gp1.add(nameF, 1, 0);
				gp1.add(numC, 0, 1);
				gp1.add(numF, 1, 1);
				gp1.add(dateO, 0, 2);
				gp1.add(dateF, 1, 2);
				gp1.setAlignment(Pos.CENTER);
				gp1.setVgap(10);
				gp1.setHgap(10);
				GridPane gp2 = new GridPane();
				gp2.add(brandO, 0, 0);
				gp2.add(brandF, 1, 0);
				gp2.add(modelO, 0, 1);
				gp2.add(modelF, 1, 1);
				gp2.add(colorO, 0, 2);
				gp2.add(colorF, 1, 2);
				gp2.add(priceO, 0, 3);
				gp2.add(priceF, 1, 3);
				gp2.add(yearO, 0, 4);
				gp2.add(yearF, 1, 4);
				gp2.setAlignment(Pos.CENTER);
				gp2.setVgap(10);
				gp2.setHgap(10);
				HBox h2 = new HBox(10);
				h2.getChildren().addAll(gp1, gp2);
				h2.setAlignment(Pos.CENTER);
				VBox v1 = new VBox(10);
				v1.getChildren().addAll(orderLeft, h1, h2, h3);
				v1.setAlignment(Pos.CENTER);

				HBox hp1 = new HBox(10);
				hp1.setAlignment(Pos.TOP_LEFT);
				hp1.getChildren().add(esc);
				StackPane topStackPane1 = new StackPane();
				topStackPane1.getChildren().addAll(top, hp1);
				StackPane.setAlignment(hp1, Pos.TOP_LEFT);
				StackPane.setAlignment(top, Pos.TOP_CENTER);
				esc.setOnAction(e12 -> adminMainScreen());
				BorderPane bp = new BorderPane();
				bp.getChildren().add(imageView);
				bp.setTop(topStackPane1);
				bp.setCenter(v1);
				scene = new Scene(bp, 600, 420);
				mainStage.setScene(scene);
				mainStage.show();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Empty Queue");
				alert.setHeaderText(null);
				alert.setContentText("There is No orders");

				alert.showAndWait();
			}

		});

		orderFile.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose a file");
			FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(txtFilter);
			File selectedFile = fileChooser.showOpenDialog(mainStage);

			if (selectedFile != null) {
				try {
					Scanner scanner = new Scanner(selectedFile);
					StringBuilder unavailableCars = new StringBuilder();
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						String[] data = line.split(",");

						if (data.length != 9) {
							if (data.length == 0 || line.trim().isEmpty()) {
								continue;
							} else {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("Invalid line format");
								alert.setContentText("The line does not have the expected number of fields.");
								alert.showAndWait();
								break;
							}
						}

						try {
							String customerName = data[0].trim();
							String customerMobile = data[1].trim();
							String brand = data[2].trim();
							String model = data[3].trim();
							int year = Integer.parseInt(data[4].trim());
							String color = data[5].trim();
							String price = data[6].trim();
							LocalDate orderDate = LocalDate.parse(data[7].trim(),
									DateTimeFormatter.ofPattern("dd/M/yyyy"));
							String orderStatus = data[8].trim();

							try {
								// Check if the car is available in the linked list
								brandNode brandNode = carInventory.search(brand);
								if (brandNode != null) {
									carNode carNode = brandNode.getBrand().getCars()
											.search(new Order(customerName, Integer.parseInt(customerMobile), brand,
													model, year, color, price, orderDate));
									if (carNode != null) {
										Order order = new Order(customerName, Integer.parseInt(customerMobile), brand,
												model, year, color, price, orderDate);
										if (queue.contains(order)) {
											// Remove the existing order if it's newer
											queue.checkAndRemove(order);
										}
										if (orderStatus.equalsIgnoreCase("Finished")) {
											stack.push(order);
											brandNode.getBrand().getCars().delete(carNode.getCar());
										} else if (orderStatus.equalsIgnoreCase("Inprocess")) {
											queue.enQueue(order);
										} else {
											// Handle invalid order status
											Alert alert = new Alert(Alert.AlertType.ERROR);
											alert.setTitle("Error");
											alert.setHeaderText("Invalid order status");
											alert.setContentText("Invalid order status: " + orderStatus);
											alert.showAndWait();
										}
									} else {

										unavailableCars.append("- ").append(brand).append(" ").append(model)
												.append("\n");
									}
								} else {

									unavailableCars.append("- ").append(brand).append(" ").append(model).append("\n");
								}
							} catch (NumberFormatException e1) {
								// Handle invalid number format
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("Invalid number format");
								alert.setContentText("Invalid number format in line: " + line);
								alert.showAndWait();
							}
						} catch (DateTimeParseException e2) {
							// Handle invalid date format
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("Invalid date format");
							alert.setContentText("Invalid date format in line: " + line);
							alert.showAndWait();
						}
					}

					scanner.close();
					if (unavailableCars.length() > 0) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Unavailable Cars");
						alert.setHeaderText("The following cars are not available:");
						alert.setContentText(unavailableCars.toString());
						alert.showAndWait();
					} else {
					}
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("File Read Successful");
					alert.setHeaderText("File Read Successfully");
					alert.setContentText("The file \"" + selectedFile.getName() + "\" has been read successfully.");
					alert.showAndWait();
				} catch (FileNotFoundException e4) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("File not found");
					alert.setContentText("The selected file was not found.");
					alert.showAndWait();
				}
			}
		});

		saveB.setOnAction(e -> {
			Label top = new Label("Select Data to Save");
			top.setFont(new Font(20));

			Button carsButton = new Button("Cars");
			carsButton.setOnAction(e1 -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Data");
				FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
				fileChooser.getExtensionFilters().add(txtFilter);
				File file = fileChooser.showSaveDialog(mainStage);

				if (file != null) {
					try (PrintWriter writer = new PrintWriter(file)) {
						brandNode currentBrandNode = carInventory.getFront();

						while (currentBrandNode != null) {
							Brand brand = currentBrandNode.getBrand();
							carNode currentCarNode = brand.getCars().getFront();
							if (currentCarNode == null) {
								// Write the brand without models
								String line = currentBrandNode.getBrand().getBrand() + ", , , , ";
								writer.println(line);
							} else {

								while (currentCarNode != null) {
									Car car = currentCarNode.getCar();
									String line = currentBrandNode.getBrand().getBrand() + ", " + car.getModel() + ", "
											+ car.getYear() + ", " + car.getColor() + ", " + car.getPrice();
									writer.println(line);
									currentCarNode = currentCarNode.getNext();
								}

								currentBrandNode = currentBrandNode.getNext();
							}
						}

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Data Saved");
						alert.setHeaderText("Data Saved Successfully");
						alert.setContentText("The data has been saved to the file: " + file.getName());
						alert.showAndWait();
					} catch (FileNotFoundException e4) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("File Not Found");
						alert.setContentText("The selected file was not found.");
						alert.showAndWait();
					}
				}
			});
			Button ordersButton = new Button("Orders");
			ordersButton.setOnAction(e1 -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Orders Data");
				FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
				fileChooser.getExtensionFilters().add(txtFilter);
				File file = fileChooser.showSaveDialog(mainStage);

				if (file != null) {
					try (PrintWriter writer = new PrintWriter(file)) {
						Queue queueCopy = new Queue();
						queueCopy.setFront(queue.getFront());
						queueCopy.setRear(queue.getRear());
						Stack stackCopy = new Stack();
						stackCopy.setTop(stack.getTop());

						while (!queueCopy.isEmpty()) {
							Order order = queueCopy.deQueue();
							String customerName = order.getName();
							String customerMobile = Integer.toString(order.getNumber());
							String brand = order.getBrand();
							String model = order.getModel();
							int year = order.getYear();
							String color = order.getColor();
							String price = order.getPrice();
							String orderDate = order.getDate().format(DateTimeFormatter.ofPattern("dd/M/yyyy"));
							String orderStatus = "Inprocess";

							writer.println(customerName + "," + customerMobile + "," + brand + "," + model + "," + year
									+ "," + color + "," + price + "," + orderDate + "," + orderStatus);
						}

						while (!stackCopy.isEmpty()) {
							Order order = stackCopy.pop();
							String customerName = order.getName();
							String customerMobile = Integer.toString(order.getNumber());
							String brand = order.getBrand();
							String model = order.getModel();
							int year = order.getYear();
							String color = order.getColor();
							String price = order.getPrice();
							String orderDate = order.getDate().format(DateTimeFormatter.ofPattern("dd/M/yyyy"));
							String orderStatus = "Finished";

							writer.println(customerName + "," + customerMobile + "," + brand + "," + model + "," + year
									+ "," + color + "," + price + "," + orderDate + "," + orderStatus);
						}

						writer.close();

						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setTitle("Success");
						successAlert.setHeaderText(null);
						successAlert.setContentText("Order data has been saved successfully!");
						successAlert.showAndWait();
					} catch (IOException ex) {
						Alert errorAlert = new Alert(Alert.AlertType.ERROR);
						errorAlert.setTitle("Error");
						errorAlert.setHeaderText(null);
						errorAlert.setContentText("An error occurred while saving order data.");
						errorAlert.showAndWait();
					}
				}
			});
			HBox vb = new HBox(10);
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(carsButton, ordersButton);
			carsButton.setStyle("-fx-background-radius: 50;");
			ordersButton.setStyle("-fx-background-radius: 50;");
			carsButton.setPrefWidth(150);
			carsButton.setPrefHeight(40);
			carsButton.setFont(new Font(17));
			ordersButton.setPrefWidth(150);
			ordersButton.setPrefHeight(40);
			ordersButton.setFont(new Font(17));
			HBox hp1 = new HBox(10);
			hp1.setAlignment(Pos.TOP_LEFT);
			hp1.getChildren().add(esc);

			StackPane topStackPane1 = new StackPane();
			topStackPane1.getChildren().addAll(top, hp1);
			StackPane.setAlignment(hp1, Pos.TOP_LEFT);
			StackPane.setAlignment(top, Pos.TOP_CENTER);

			esc.setOnAction(e12 -> adminMainScreen());

			BorderPane bp = new BorderPane();
			bp.getChildren().add(imageView);
			bp.setTop(topStackPane1);
			bp.setCenter(vb);

			scene = new Scene(bp, 500, 400);
			mainStage.setScene(scene);
			mainStage.show();
		});

		carsB.setOnAction(e -> showCarsScreen());

		report.setOnAction(e -> {
			Label top = new Label("Last 10 sold cars");
			TableView<Order> tableView = new TableView<>();

			TableColumn<Order, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Order, String> numberColumn = new TableColumn<>("Number");
			numberColumn.setCellValueFactory(new PropertyValueFactory<>("Number"));

			TableColumn<Order, String> brandColumn = new TableColumn<>("Brand");
			brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

			TableColumn<Order, String> modelColumn = new TableColumn<>("Model");
			modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

			TableColumn<Order, Integer> yearColumn = new TableColumn<>("Year");
			yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

			TableColumn<Order, String> colorColumn = new TableColumn<>("Color");
			colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

			TableColumn<Order, String> priceColumn = new TableColumn<>("Price");
			priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

			TableColumn<Order, LocalDate> dateColumn = new TableColumn<>("Date");
			dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
			nameColumn.setPrefWidth(120);
			numberColumn.setPrefWidth(80);
			brandColumn.setPrefWidth(100);
			modelColumn.setPrefWidth(90);
			yearColumn.setPrefWidth(80);
			colorColumn.setPrefWidth(100);
			priceColumn.setPrefWidth(80);
			dateColumn.setPrefWidth(100);
			tableView.getColumns().addAll(nameColumn, numberColumn, brandColumn, modelColumn, yearColumn, colorColumn,
					priceColumn, dateColumn);
			tableView.getColumns().removeAll(tableView.getColumns()
					.filtered(column -> !(column.equals(nameColumn) || column.equals(numberColumn)
							|| column.equals(brandColumn) || column.equals(modelColumn) || column.equals(yearColumn)
							|| column.equals(colorColumn) || column.equals(priceColumn) || column.equals(dateColumn))));

			ObservableList<Order> carData = FXCollections.observableArrayList();

			stack.reportLastSoldCars(carData);

			FilteredList<Order> filteredData = new FilteredList<>(carData);
			filteredData.setPredicate(order -> order.getNumber() > 0);
			// filteredData = filteredData.sorted();
			int maxRows = 10;
			if (filteredData.size() > maxRows) {
				List<Order> limitedList = filteredData.subList(0, maxRows);
				filteredData = new FilteredList<>(FXCollections.observableArrayList(limitedList));
			}
			tableView.setItems(filteredData);
			HBox hp = new HBox(10);
			hp.setAlignment(Pos.TOP_LEFT);
			hp.getChildren().add(esc);
			StackPane topStackPane = new StackPane();
			topStackPane.getChildren().addAll(top, hp);
			StackPane.setAlignment(hp, Pos.TOP_LEFT);
			StackPane.setAlignment(top, Pos.TOP_CENTER);
			esc.setOnAction(e1 -> adminMainScreen());
			VBox v = new VBox(10);
			v.getChildren().addAll(topStackPane, tableView);
			// v.setPadding(new Insets(10));
			v.setAlignment(Pos.CENTER);
			top.setFont(new Font(15));

			Scene scene = new Scene(v, 700, 328);
			mainStage.setScene(scene);
			mainStage.show();
		});
		searchB.setOnAction(e -> {
			Label top = new Label("Available Cars");
			top.setFont(new Font(20));
			ComboBox<String> brandC = new ComboBox<>();
			brandC.setPromptText("Choose a car Brand");
			a = carInventory.getFront();
			while (a != null) {
				brandC.getItems().add(a.getBrand().getBrand());
				a = a.getNext();
			}
			TableView<Car> tableView = new TableView<>();

			TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
			modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

			TableColumn<Car, Integer> yearColumn = new TableColumn<>("Year");
			yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

			TableColumn<Car, String> colorColumn = new TableColumn<>("Color");
			colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

			TableColumn<Car, String> priceColumn = new TableColumn<>("Price");
			priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

			modelColumn.setPrefWidth(90);
			yearColumn.setPrefWidth(80);
			colorColumn.setPrefWidth(100);
			priceColumn.setPrefWidth(80);

			tableView.getColumns().addAll(modelColumn, yearColumn, colorColumn, priceColumn);

			VBox v = new VBox(10);
			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(brandC, tableView);
			HBox hp = new HBox(10);
			hp.setAlignment(Pos.TOP_LEFT);
			hp.getChildren().add(esc);
			esc.setOnAction(e2 -> adminMainScreen());
			StackPane topStackPane = new StackPane();
			topStackPane.getChildren().addAll(top, hp);
			StackPane.setAlignment(hp, Pos.TOP_LEFT);
			StackPane.setAlignment(top, Pos.TOP_CENTER);
			BorderPane bp = new BorderPane();
			bp.setTop(topStackPane);
			bp.setCenter(v);
			scene = new Scene(bp, 650, 400);
			mainStage.setScene(scene);
			mainStage.show();

			brandC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				tableView.getItems().clear();
				if (newValue != null) {
					brandNode brandNode = carInventory.search(newValue);
					if (brandNode != null) {
						carNode curr = brandNode.getBrand().getCars().getFront();

						while (curr != null && curr.getCar() != null) {
							tableView.getItems().add(curr.getCar());
							curr = curr.getNext();
						}
					}
				}
			});

		});
		carsLabel.setFont(Font.font(20));

		Text detailsText = new Text("(insert, delete and update)");
		detailsText.setFont(Font.font(15));

		VBox VB = new VBox();
		VB.getChildren().addAll(carsLabel, detailsText);
		VB.setAlignment(Pos.CENTER);
		carsB.setGraphic(VB);
		orderFile.setPrefWidth(200);
		orderFile.setPrefHeight(60);
		orderFile.setFont(new Font(18));

		carFile.setPrefWidth(200);
		carFile.setPrefHeight(60);
		carFile.setFont(new Font(18));

		orderB.setPrefWidth(200);
		orderB.setPrefHeight(60);
		orderB.setFont(new Font(20));

		saveB.setPrefWidth(200);
		saveB.setPrefHeight(60);
		saveB.setFont(new Font(20));

		searchB.setPrefWidth(200);
		searchB.setPrefHeight(60);
		searchB.setFont(new Font(20));

		report.setPrefWidth(200);
		report.setPrefHeight(60);
		report.setFont(new Font(20));

		carsB.setPrefWidth(200);
		carsB.setPrefHeight(60);
		carsB.setFont(new Font(15));
		joker.setFont(new Font(20));
		Label Top = new Label("Admin permissions");
		Top.setFont(new Font(30));
		HBox h = new HBox(10);
		h.getChildren().addAll(carsB, searchB, orderB, report, saveB);
		h.setAlignment(Pos.CENTER);
		h.setPadding(new Insets(10));
		HBox h2 = new HBox(10);
		h2.getChildren().addAll(carFile, orderFile);
		h2.setAlignment(Pos.CENTER);
		h2.setPadding(new Insets(10));
		VBox v = new VBox(10);
		v.getChildren().addAll(h2, joker, h);
		v.setAlignment(Pos.CENTER);
		BorderPane bp = new BorderPane();
		bp.getChildren().add(imageView);
		HBox hp = new HBox(10);
		hp.setAlignment(Pos.TOP_LEFT);
		hp.getChildren().add(esc);
		StackPane topStackPane = new StackPane();
		topStackPane.getChildren().addAll(Top, hp);
		StackPane.setAlignment(hp, Pos.TOP_LEFT);
		StackPane.setAlignment(Top, Pos.TOP_CENTER);
		bp.setTop(topStackPane);
		bp.setCenter(v);
		bp.setBottom(h);
		scene = new Scene(bp, 1000, 600);
		imageView.fitHeightProperty().bind(scene.heightProperty());
		imageView.fitWidthProperty().bind(scene.widthProperty());
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.show();
	}

	/*
	 * The mainScreen() method creates the main interface of an application for a
	 * car agency. It displays a background image, a title, and two buttons: "Admin"
	 * and "Customer." The method also includes event handlers for the buttons,
	 * which lead to different screens.
	 */
	public void mainScreen() {
		Image image = new Image("a.jpg");
		ImageView imageView = new ImageView(image);
		Label top = new Label("Ata's car agency");
		top.setFont(new Font(30));
		Button admin = new Button("Admin");
		admin.setPrefWidth(200);
		admin.setPrefHeight(60);
		admin.setFont(new Font(20));
		admin.setStyle("-fx-background-radius: 50;");
		customer.setStyle("-fx-background-radius: 50;");
		admin.setOnMouseEntered(e -> admin.setEffect(new DropShadow(10, Color.WHITE)));
		admin.setOnMouseExited(e -> admin.setEffect(null));
		customer.setOnMouseEntered(e -> customer.setEffect(new DropShadow(10, Color.WHITE)));
		customer.setOnMouseExited(e -> customer.setEffect(null));
		customer.setPrefWidth(200);
		customer.setPrefHeight(60);
		customer.setFont(new Font(17.5));
		admin.setOnAction(e -> showPasswordScreen());
		customer.setOnAction(e -> customerScreen());
		VBox v = new VBox(20);
		v.getChildren().addAll(admin, customer);
		v.setAlignment(Pos.CENTER);
		VBox v2 = new VBox(20);
		v2.setPadding(new Insets(30));
		v2.getChildren().add(top);
		v2.setAlignment(Pos.CENTER);
		BorderPane bp = new BorderPane();
		bp.getChildren().add(imageView);
		bp.setCenter(v);
		bp.setTop(v2);
		scene = new Scene(bp, 400, 550);
		mainStage.setScene(scene);
		imageView.fitHeightProperty().bind(scene.heightProperty());
		imageView.fitWidthProperty().bind(scene.widthProperty());
		mainStage.setResizable(false);
		mainStage.show();
	}

	/*
	 * The showPasswordScreen() method sets up a password screen for the admin. It
	 * prompts the admin to enter a password and validates it. If the entered
	 * password is correct, it navigates to the admin's main screen; otherwise, it
	 * displays a warning message.
	 */
	private void showPasswordScreen() {
		Image image = new Image("back.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		Image image2 = new Image("a.jpg");
		ImageView iv = new ImageView(image2);
		Label passwordLabel = new Label("Enter the admin's password: ");
		passwordLabel.setStyle("-fx-text-fill: white;");
		passwordLabel.setFont(new Font(20));
		PasswordField passwordField = new PasswordField();
		Button submit = new Button("Login");
		Button esc = new Button();
		esc.setGraphic(imageView);
		esc.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		esc.setOnAction(e -> mainScreen());
		submit.setPrefWidth(100);
		submit.setPrefHeight(30);
		submit.setFont(new Font(20));
		submit.setStyle("-fx-background-radius: 50;");
		submit.setOnAction(event -> {
			String enteredPassword = passwordField.getText();
			if ("admin".equals(enteredPassword)) {
				adminMainScreen();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("Wrong password");
				alert.showAndWait();
				passwordField.clear();
			}

		});

		VBox v = new VBox(10);
		v.setPadding(new Insets(10));
		v.getChildren().addAll(passwordLabel, passwordField, submit);
		v.setAlignment(Pos.CENTER);
		BorderPane bp = new BorderPane();
		bp.getChildren().add(iv);
		bp.setTop(esc);
		bp.setCenter(v);

		scene = new Scene(bp, 400, 550);
		iv.fitHeightProperty().bind(scene.heightProperty());
		iv.fitWidthProperty().bind(scene.widthProperty());
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.show();
		;
	}

	/*
	 * The customerScreen() method sets up a customer screen interface for ordering
	 * cars. It creates a graphical user interface with various input fields and
	 * buttons for customers to enter their information, select a car, and place an
	 * order. The method displays labels, text fields, combo boxes, and buttons,
	 * allowing customers to input their name, phone number, select a car brand and
	 * model, choose an order date, and submit their order.
	 */
	public void customerScreen() {
		Image image = new Image("a.jpg");
		ImageView imageView = new ImageView(image);
		Image image2 = new Image("back.png");
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(50);
		imageView2.setFitHeight(50);
		Button esc = new Button();
		esc.setGraphic(imageView2);
		esc.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		esc.setOnAction(e -> mainScreen());
		Button order = new Button("Order");
		order.setPrefWidth(100);
		order.setPrefHeight(30);
		order.setFont(new Font(15));
		order.setStyle("-fx-background-radius: 50;");
		Label top = new Label("\nWelcome to Ata's car agency");
		Label top2 = new Label("\nMake yourself comfortable and order the car you want");
		top.setStyle("-fx-text-fill: white;");
		top2.setStyle("-fx-text-fill: white;");
		top.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
		top2.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
		Label name = new Label("Name: ");
		name.setFont(new Font(20));
		TextField nameF = new TextField();
		Label number = new Label("Phone Number: ");
		number.setFont(new Font(20));
		TextField numberF = new TextField();
		Label brand = new Label("Car Brand ");
		Label model = new Label("Car Model ");
		brand.setFont(new Font(20));
		model.setFont(new Font(20));
		ComboBox<String> brandC = new ComboBox<>();
		ComboBox<String> modelC = new ComboBox<>();
		ComboBox<Integer> yearComboBox = new ComboBox<>();
		ComboBox<String> monthComboBox = new ComboBox<>();
		ComboBox<Integer> dayComboBox = new ComboBox<>();
		HBox dateH = new HBox(10);
		dateH.getChildren().addAll(yearComboBox, monthComboBox, dayComboBox);
		dateH.setAlignment(Pos.CENTER);

		yearComboBox.getItems().add(2023);
		yearComboBox.getSelectionModel().select(0);

		List<String> monthNumbers = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			String monthNumber = String.format("%02d", month);
			monthNumbers.add(monthNumber);
		}
		monthComboBox.getItems().addAll(monthNumbers);

		monthComboBox.getSelectionModel().select(0);

		monthComboBox.setOnAction(e -> {
			String selectedMonth = monthComboBox.getValue();
			int daysInMonth = YearMonth.of(2023, Integer.parseInt(selectedMonth)).lengthOfMonth();
			dayComboBox.getItems().clear();
			for (int day = 1; day <= daysInMonth; day++) {
				String dayNumber = String.format("%02d", day);
				dayComboBox.getItems().add(Integer.parseInt(dayNumber));
			}
		});

		monthComboBox.fireEvent(new ActionEvent());

		yearComboBox.setOnAction(e -> {
			Integer selectedYear = yearComboBox.getValue();
		});

		dayComboBox.setOnAction(e -> {
			Integer selectedDay = dayComboBox.getValue();
		});

		try {
			a = carInventory.getFront();
			while (a != null) {
				brandC.getItems().add(a.getBrand().getBrand());
				a = a.getNext();
			}
			brandC.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
				modelC.getItems().clear();
				if (newvalue != null)
					a = carInventory.search(newvalue);
				if (a != null) {
					carNode curr = a.getBrand().getCars().getFront();
					while (curr != null) {
						modelC.getItems().add(curr.getCar().getModel() + " " + curr.getCar().getYear() + " "
								+ curr.getCar().getColor() + " " + curr.getCar().getPrice());
						curr = curr.getNext();
					}
				}
			});
		} catch (Exception e) {

		}

		order.setOnAction(e -> {
			if (nameF.getText() != null && numberF.getText() != null && brandC.getValue() != null
					&& modelC.getValue() != null && yearComboBox.getValue() != null && monthComboBox.getValue() != null
					&& dayComboBox.getValue() != null && !nameF.getText().isBlank() && !numberF.getText().isBlank()
					&& !brandC.getValue().isBlank() && !modelC.getValue().isBlank()) {
				String nameS = nameF.getText();
				String numberS = numberF.getText();
				String brandS = brandC.getValue();
				String Specs = modelC.getValue();
				String dateS = yearComboBox.getValue() + "-" + monthComboBox.getValue() + "-" + dayComboBox.getValue();
				LocalDate dateL = null;

				if (dateS != null) {
					try {
						dateL = LocalDate.parse(dateS, DateTimeFormatter.ofPattern("yyyy-MM-d"));
					} catch (DateTimeParseException e1) {
						e1.printStackTrace();
					}
				}
				String[] data = Specs.split(" ");
				String modelS = data[0];
				String year = data[1];
				String color = data[2];
				String price = data[3];
				if (nameS.matches("[a-zA-Z]+")) {
					try {

						Order curr = new Order(nameS, Integer.parseInt(numberS), brandS, modelS, Integer.parseInt(year),
								color, price, dateL);
						Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
						confirmation.setTitle("Confirmation");
						confirmation.setHeaderText("Confirm Order");
						confirmation.setContentText("Are you sure you want to place this order?");
						ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
						ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
						confirmation.getButtonTypes().setAll(confirmButton, cancelButton);
						Optional<ButtonType> result = confirmation.showAndWait();

						if (result.isPresent() && result.get() == confirmButton) {
							queue.enQueue(curr);
							nameF.setText(null);
							numberF.setText(null);
							modelC.setValue(null);
							brandC.setValue(null);
							dayComboBox.setValue(null);
						} else {

						}
					} catch (Exception e1) {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Error");
						alert.setHeaderText("Missing Information or Wrong Information");
						alert.setContentText("There is an empty Field");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Error");
					alert.setHeaderText("The name should be String");
					alert.setContentText("There is an empty Field");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Missing Information or Wrong Information");
				alert.setContentText("There is an empty Field");
				alert.showAndWait();
			}
		});
		Label date = new Label("Order Date: ");
		date.setFont(new Font(20));
		name.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		number.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		brand.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		model.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		date.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		GridPane gp = new GridPane();
		gp.add(name, 0, 0);
		gp.add(nameF, 1, 0);
		gp.add(number, 0, 1);
		gp.add(numberF, 1, 1);
		gp.add(brand, 0, 2);
		gp.add(brandC, 1, 2);
		gp.add(model, 0, 3);
		gp.add(modelC, 1, 3);
		gp.add(date, 0, 4);
		gp.add(dateH, 1, 4);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		VBox v2 = new VBox(10);
		v2.getChildren().addAll(gp, order);
		v2.setAlignment(Pos.CENTER);
		BorderPane bp = new BorderPane();
		VBox v = new VBox();
		v.getChildren().addAll(top, top2);
		v.setAlignment(Pos.CENTER);
		HBox hp = new HBox();
		hp.setAlignment(Pos.TOP_LEFT);
		hp.getChildren().add(esc);
		StackPane topStackPane = new StackPane();
		topStackPane.getChildren().addAll(v, hp);
		StackPane.setAlignment(hp, Pos.TOP_LEFT);
		StackPane.setAlignment(v, Pos.TOP_CENTER);
		bp.getChildren().add(imageView);
		bp.setTop(topStackPane);
		bp.setCenter(v2);
		scene = new Scene(bp, 650, 400);
		mainStage.setScene(scene);
		mainStage.show();
	}
}