package PresentationLayer;

import BusinessLayer.BookBO;
import DTOPkg.BookDTO;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;

public class JavaFXUI extends Application {

    private static BookBO bookBOInstance;

    private TextField txtId;
    private TextField txtTitle;
    private TextField txtCompiler;
    private TextField txtEra;

    private ObservableList<String> listItems;
    private ListView<String> bookList;
    private Label lblStatus;
    private BorderPane rootContainer;

    public static void launchApp(String[] args, BookBO initializedBookBO) {
        bookBOInstance = initializedBookBO;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        if (bookBOInstance == null) {
            System.err.println("Fatal: BookBO instance not initialized!");
            return;
        }

        txtId = new TextField();
        txtTitle = new TextField();
        txtCompiler = new TextField();
        txtEra = new TextField();
        txtId.setEditable(false);

        listItems = FXCollections.observableArrayList();
        bookList = new ListView<>(listItems);
        lblStatus = new Label(" ");
        rootContainer = new BorderPane();

        // 🌈 Modern Styling
        rootContainer.setPadding(new Insets(15));
        rootContainer.setBackground(new Background(new BackgroundFill(Color.web("#f4f7f6"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Create sections
        VBox inputPanel = createInputPanel();
        HBox buttonPanel = createButtonPanel();
        VBox listPanel = createListPanel();

        // ✨ Title
        Label title = new Label("📚 Classical Arabic Poetry System");
        title.setFont(Font.font("Segoe UI Semibold", 22));
        title.setTextFill(Color.web("#005b5c"));
        title.setAlignment(Pos.CENTER);
        title.setPadding(new Insets(10, 0, 15, 0));

        VBox topContainer = new VBox(title, inputPanel);
        rootContainer.setTop(topContainer);
        rootContainer.setCenter(buttonPanel);
        rootContainer.setRight(listPanel);
        rootContainer.setBottom(lblStatus);

        lblStatus.setMaxWidth(Double.MAX_VALUE);
        lblStatus.setTextFill(Color.web("#005b5c"));
        lblStatus.setPadding(new Insets(8));
        lblStatus.setAlignment(Pos.CENTER);
        lblStatus.setStyle("-fx-background-color:#d9f0ee; -fx-border-color:#b5e2e0;");

        // Scene setup
        Scene scene = new Scene(rootContainer, 1000, 600);
        addCSS(scene);
        primaryStage.setTitle("Book Module - Poetry Management");
        primaryStage.setScene(scene);
        primaryStage.show();

        refreshList();
    }

    private VBox createInputPanel() {
        GridPane grid= new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        Label lblId = new Label("Book ID:");
        Label lblTitle = new Label("Title:");
        Label lblCompiler = new Label("Compiler:");
        Label lblEra = new Label("Era:");

        grid.add(lblId, 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(lblTitle, 0, 1);
        grid.add(txtTitle, 1, 1);
        grid.add(lblCompiler, 0, 2);
        grid.add(txtCompiler, 1, 2);
        grid.add(lblEra, 0, 3);
        grid.add(txtEra, 1, 3);

        TitledPane titledPane = new TitledPane("Book Details", grid);
        titledPane.setCollapsible(false);
        titledPane.setEffect(new DropShadow(5, Color.GRAY));

        VBox vbox = new VBox(titledPane);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    private HBox createButtonPanel() {
        HBox hbox = new HBox(12);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER);

        Button btnCreate = new Button("Create");
        Button btnRead = new Button("Read");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");
        Button btnRefresh = new Button("Refresh");
        Button btnClear = new Button("Clear");

        Button[] allBtns = {btnCreate, btnRead, btnUpdate, btnDelete, btnRefresh, btnClear};
        for (Button b : allBtns) {
            b.setPrefWidth(100);
            b.setStyle("-fx-background-color:#009688; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:8;");
            b.setOnMouseEntered(e -> b.setStyle("-fx-background-color:#00796B; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:8;"));
            b.setOnMouseExited(e -> b.setStyle("-fx-background-color:#009688; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:8;"));
        }

        btnCreate.setOnAction(this::onCreate);
        btnRead.setOnAction(this::onRead);
        btnUpdate.setOnAction(this::onUpdate);
        btnDelete.setOnAction(this::onDelete);
        btnRefresh.setOnAction(e -> refreshList());
        btnClear.setOnAction(e -> clearForm());

        hbox.getChildren().addAll(allBtns);
        hbox.setEffect(new DropShadow(8, Color.GRAY));
        return hbox;
    }

    private VBox createListPanel() {
        bookList.setPrefWidth(380);
        bookList.setStyle("-fx-background-color:white; -fx-border-color:#b5e2e0;");
        bookList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        bookList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        try {
                            int id = parseInt(newValue.split(" - ", 2)[0].trim());
                            BookDTO dto = bookBOInstance.readBook(new BookDTO(id, null, null, 0));
                            if (dto != null) populateForm(dto);
                        } catch (Exception ex) {
                            showError("Invalid selection: " + ex.getMessage());
                        }
                    }
                }
        );

        TitledPane titledPane = new TitledPane("Book Records", bookList);
        titledPane.setCollapsible(false);
        titledPane.setEffect(new DropShadow(5, Color.GRAY));

        VBox vbox = new VBox(titledPane);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    // --- CRUD Buttons Logic ---
    private void onCreate(ActionEvent e) {
        try {
            String title = txtTitle.getText().trim();
            String compiler = txtCompiler.getText().trim();
            int era = parseInt(txtEra.getText());

            if (title.isEmpty()) { showError("Book title is required."); return; }

            BookDTO book = new BookDTO(0, title, compiler, era);
            bookBOInstance.createBook(book);

            showSuccess("Book created successfully!");
            clearForm();
            refreshList();
        } catch (Exception ex) { showError("Error creating book: " + ex.getMessage()); }
    }

    private void onRead(ActionEvent e) {
        try {
            String idText = txtId.getText().trim();
            if (idText.isEmpty()) { showError("Please enter Book ID."); return; }

            int id = parseInt(idText);
            BookDTO found = bookBOInstance.readBook(new BookDTO(id, null, null, 0));
            if (found == null) showError("Book not found.");
            else { populateForm(found); showSuccess("Book loaded."); }
        } catch (Exception ex) { showError("Error reading book: " + ex.getMessage()); }
    }

    private void onUpdate(ActionEvent e) {
        try {
            int id = parseInt(txtId.getText());
            String title = txtTitle.getText().trim();
            String compiler = txtCompiler.getText().trim();
            int era = parseInt(txtEra.getText());

            if (title.isEmpty()) { showError("Title is required."); return; }

            BookDTO book = new BookDTO(id, title, compiler, era);
            BookDTO updated = bookBOInstance.updateBook(book);
            if (updated == null) showError("Update failed.");
            else { showSuccess("Book updated successfully!"); refreshList(); }
        } catch (Exception ex) { showError("Error updating book: " + ex.getMessage()); }
    }

    private void onDelete(ActionEvent e) {
        try {
            int id = parseInt(txtId.getText());
            if (id == 0) { showError("No book selected."); return; }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText(null);
            alert.setContentText("Delete book ID " + id + "?");
            alert.initOwner(rootContainer.getScene().getWindow());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                bookBOInstance.deleteBook(new BookDTO(id, null, null, 0));
                showSuccess("Deleted successfully!");
                clearForm();
                refreshList();
            }
        } catch (Exception ex) { showError("Error deleting: " + ex.getMessage()); }
    }

    private void populateForm(BookDTO b) {
        txtId.setText(String.valueOf(b.getBookID()));
        txtTitle.setText(b.getTitle());
        txtCompiler.setText(b.getCompiler());
        txtEra.setText(String.valueOf(b.getEra()));
    }

    private void clearForm() {
        txtId.clear(); txtTitle.clear(); txtCompiler.clear(); txtEra.clear();
        lblStatus.setText("Form cleared.");
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }

    private void refreshList() {
        listItems.clear();
        lblStatus.setText("Loading...");
        lblStatus.setTextFill(Color.GRAY);

        Task<List<BookDTO>> task = new Task<List<BookDTO>>() {
            @Override
            protected List<BookDTO> call() throws Exception {
                return bookBOInstance.listAll();
            }

            @Override
            protected void succeeded() {
                List<BookDTO> books = getValue();
                for (BookDTO b : books) {
                    listItems.add(b.getBookID() + " - " + b.getTitle() + " (" + b.getCompiler() + ") [" + b.getEra() + "]");
                }
                lblStatus.setText("Loaded " + books.size() + " record(s).");
                lblStatus.setTextFill(Color.web("#00695C"));
            }

            @Override
            protected void failed() {
                showError("Failed to load books.");
            }
        };

        new Thread(task).start();
    }

    // 💬 Alerts
    private void showSuccess(String msg) {
        lblStatus.setTextFill(Color.web("#00695C"));
        lblStatus.setText(msg);
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    private void showError(String msg) {
        lblStatus.setTextFill(Color.RED);
        lblStatus.setText(msg);
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    // 🌸 CSS Styling for Fields
    private void addCSS(Scene scene) {
        scene.getRoot().setStyle(
                "-fx-font-family: 'Segoe UI';"
                        + "-fx-font-size: 14;"
                        + "-fx-text-fill: #333;"
        );

        String textFieldStyle = "-fx-background-color: #ffffff; -fx-border-radius: 5; -fx-background-radius: 5; "
                + "-fx-border-color: #b5e2e0; -fx-padding: 5;";
        txtId.setStyle(textFieldStyle);
        txtTitle.setStyle(textFieldStyle);
        txtCompiler.setStyle(textFieldStyle);
        txtEra.setStyle(textFieldStyle);
    }
}
