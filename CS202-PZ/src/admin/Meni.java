/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package admin;

import entities.Jelo;
import entities.Pice;
import enums.TipJela;
import enums.TipPica;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Meni extends Application {

    //levi panel
    Button btnJela = new Button("Jela");
    Button btnPica = new Button("Pića");
    Button btnNazad = new Button("Nazad");
    Button dezertiBtn = new Button("Dezerti");

    BorderPane root = new BorderPane();

    //centralni panel za jela
    TextField tfIdJela = new TextField();
    TextField tfNazivJela = new TextField();
    ComboBox<TipJela> comboJela = new ComboBox<>(FXCollections.observableArrayList(TipJela.values()));
    TextField tfCenaJela = new TextField();

    Button btnAddPica = new Button("Dodaj piće");
    Button btnEditPica = new Button("Izmeni piće");
    Button btnDeletePica = new Button("Obriši piće");
    Button btnDeleteAllPica = new Button("Obriši sve");

    Button btnAddJela = new Button("Dodaj jelo");
    Button btnEditJela = new Button("Izmeni jelo");
    Button btnDeleteJela = new Button("Obriši jelo");
    Button btnDeleteAllJela = new Button("Obriši sve");

    //centralni panel za pica
    TextField tfIdPica = new TextField();
    TextField tfNazivPica = new TextField();
    ComboBox<TipPica> comboPica = new ComboBox<>(FXCollections.observableArrayList(TipPica.values()));
    TextField tfCenaPica = new TextField();

    TableView<Pice> tablePice = new TableView<>();
    TableView<Jelo> tableJelo = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        initializeLeftPanel();
        initializeTableViewPica();
        initializeTableViewJela();
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 800, 600);

        btnNazad.setOnAction(e -> {
            primaryStage.close();
            new scenes.AdminScene().start(primaryStage);
        });

        root.setStyle("-fx-background-color: #DFD3BD; -fx-font-size: 15px");
        
        dezertiBtn.setOnAction(e -> {
            primaryStage.close();
            new webScraping.DezertScraping().start(primaryStage);
        });

        primaryStage.setTitle("Meni");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void initializePicaScene() {
        initializeCenterPanelPica();
        initializeRightPanelPica();

        btnAddPica.setOnAction(e -> dodajPice());
        btnEditPica.setOnAction(e -> izmeniPice());
        btnDeletePica.setOnAction(e -> obrisiPice());
        btnDeleteAllPica.setOnAction(e -> obrisiSvaPica());
    }

    private void initializeLeftPanel() {
        VBox leftPanel = new VBox(btnJela, btnPica, dezertiBtn, btnNazad);
        leftPanel.setSpacing(10);
        leftPanel.setPadding(new Insets(10, 10, 10, 10));

        root.setLeft(leftPanel);
        BorderPane.setMargin(leftPanel, new Insets(10, 20, 10, 20));

        btnPica.setOnAction(e -> initializePicaScene());
        btnJela.setOnAction(e -> initializeJelaScene());

    }

    private void initializeCenterPanelPica() {
        comboPica = new ComboBox<>(FXCollections.observableArrayList(TipPica.values()));
        comboPica.getSelectionModel().select(TipPica.TOPLO_PICE);

        VBox centerPanel = new VBox(
                new Label("Id pića:"), tfIdPica,
                new Label("Naziv pića:"), tfNazivPica,
                new Label("Tip pića:"), comboPica,
                new Label("Cena pića:"), tfCenaPica,
                btnAddPica, btnEditPica, btnDeletePica, btnDeleteAllPica
        );
        centerPanel.setSpacing(10);
        // centerPanel.setPadding(new Insets(10));
        root.setCenter(centerPanel);
        BorderPane.setMargin(centerPanel, new Insets(15));
    }

    private void initializeRightPanelPica() {
        VBox rightPanel = new VBox(tablePice);
        rightPanel.setPadding(new Insets(10, 0, 10, 0));
        root.setRight(rightPanel);
        BorderPane.setMargin(rightPanel, new Insets(10, 20, 10, 20));
    }

    private void initializeTableViewPica() {
        TableColumn<Pice, Integer> idCol = new TableColumn<>("Id pića:");
        TableColumn<Pice, String> nazivCol = new TableColumn<>("Naziv pića: ");
        TableColumn<Pice, TipJela> tipCol = new TableColumn<>("Tip pića:");
        TableColumn<Pice, Integer> cenaCol = new TableColumn<>("Cena pića:");

        idCol.setCellValueFactory(new PropertyValueFactory<>("idPica"));
        nazivCol.setCellValueFactory(new PropertyValueFactory<>("nazivPica"));
        tipCol.setCellValueFactory(new PropertyValueFactory<>("tipPica"));
        cenaCol.setCellValueFactory(new PropertyValueFactory<>("cenaPica"));

        tablePice.getColumns().addAll(idCol, nazivCol, tipCol, cenaCol);
    }

    private void dodajPice() {
        try {
            int id = Integer.parseInt(tfIdPica.getText());
            String naziv = tfNazivPica.getText();
            int cena = Integer.parseInt(tfCenaPica.getText());
            TipPica tip = comboPica.getValue();

            Pice novoPice = new Pice(id, naziv, cena, tip);

            tablePice.getItems().addAll(novoPice);
            ocistiPoljaPica();
        } catch (NumberFormatException e) {
            System.out.println("Neispravan unos. Pokusajte ponovo");
        }
    }

    private void ocistiPoljaPica() {
        tfIdPica.clear();
        tfNazivPica.clear();
        tfCenaPica.clear();
        comboPica.getSelectionModel().selectFirst();
    }

    private void izmeniPice() {
        Pice odabranoPice = tablePice.getSelectionModel().getSelectedItem();

        if (odabranoPice != null) {
            try {
                odabranoPice.setIdPica(Integer.parseInt(tfIdPica.getText()));
                odabranoPice.setNazivPica(tfNazivPica.getText());
                odabranoPice.setCenaPica(Integer.parseInt(tfCenaPica.getText()));
                odabranoPice.setTipPica(comboPica.getValue());

                tablePice.refresh();
                ocistiPoljaPica();
            } catch (NumberFormatException e) {
                System.out.println("Neispravan unos. Pokusajte ponovo");
            }
        }
    }

    private void obrisiPice() {
        Pice odabranoPice = tablePice.getSelectionModel().getSelectedItem();

        if (odabranoPice != null) {
            tablePice.getItems().remove(odabranoPice);

            ocistiPoljaPica();
        } else {
            System.out.println("Nije odabrano pice za brisanje");
        }
    }

    private void obrisiSvaPica() {
        tablePice.getItems().clear();
        ocistiPoljaPica();
    }

    private void initializeJelaScene() {
        initializeCenterPanelJela();
        initializeRightPanelJela();

        btnAddJela.setOnAction(e -> dodajJelo());
        btnEditJela.setOnAction(e -> izmeniJelo());
        btnDeleteJela.setOnAction(e -> obrisiJelo());
        btnDeleteAllJela.setOnAction(e -> obrisiSvaJela());
    }

    private void initializeCenterPanelJela() {
        comboJela = new ComboBox<>(FXCollections.observableArrayList(TipJela.values()));
        comboJela.getSelectionModel().select(TipJela.DEZERT);

        VBox centerPanel = new VBox(
                new Label("Id jela:"), tfIdJela,
                new Label("Naziv jela:"), tfNazivJela,
                new Label("Tip jela:"), comboJela,
                new Label("Cena jela:"), tfCenaJela,
                btnAddJela, btnEditJela, btnDeleteJela, btnDeleteAllJela
        );
        centerPanel.setSpacing(10);
        centerPanel.setPadding(new Insets(5));
        root.setCenter(centerPanel);
        BorderPane.setMargin(centerPanel, new Insets(10, 10, 10, 10));
    }

    private void initializeRightPanelJela() {
        VBox rightPanel = new VBox(tableJelo);
        rightPanel.setPadding(new Insets(10, 0, 10, 0));
        root.setRight(rightPanel);
        BorderPane.setMargin(rightPanel, new Insets(10, 20, 10, 20));
    }

    private void initializeTableViewJela() {
        TableColumn<Jelo, Integer> idCol = new TableColumn<>("Id jela:");
        TableColumn<Jelo, String> nazivCol = new TableColumn<>("Naziv jela: ");
        TableColumn<Jelo, TipJela> tipCol = new TableColumn<>("Tip jela:");
        TableColumn<Jelo, Integer> cenaCol = new TableColumn<>("Cena jela:");

        idCol.setCellValueFactory(new PropertyValueFactory<>("idJela"));
        nazivCol.setCellValueFactory(new PropertyValueFactory<>("nazivJela"));
        tipCol.setCellValueFactory(new PropertyValueFactory<>("tipJela"));
        cenaCol.setCellValueFactory(new PropertyValueFactory<>("cenaJela"));

        tableJelo.getColumns().addAll(idCol, nazivCol, tipCol, cenaCol);
    }

    private void dodajJelo() {
        try {
            int id = Integer.parseInt(tfIdJela.getText());
            String naziv = tfNazivJela.getText();
            int cena = Integer.parseInt(tfCenaJela.getText());
            TipJela tip = comboJela.getValue();

            Jelo novoJelo = new Jelo(id, naziv, cena, tip);

            tableJelo.getItems().addAll(novoJelo);
            ocistiPoljaJela();
        } catch (NumberFormatException e) {
            System.out.println("Neispravan unos. Pokušajte ponovo.");
        }
    }

    private void ocistiPoljaJela() {
        tfIdJela.clear();
        tfNazivJela.clear();
        tfCenaJela.clear();
        comboJela.getSelectionModel().selectFirst();
    }

    private void izmeniJelo() {
        Jelo odabranoJelo = tableJelo.getSelectionModel().getSelectedItem();

        if (odabranoJelo != null) {
            try {
                odabranoJelo.setIdJela(Integer.parseInt(tfIdJela.getText()));
                odabranoJelo.setNazivJela(tfNazivJela.getText());
                odabranoJelo.setCenaJela(Integer.parseInt(tfCenaJela.getText()));
                odabranoJelo.setTipJela(comboJela.getValue());

                tableJelo.refresh();
                ocistiPoljaJela();
            } catch (NumberFormatException e) {
                System.out.println("Neispravan unos. Pokusajte ponovo");
            }

        }
    }

    private void obrisiJelo() {
        Jelo odabranoJelo = tableJelo.getSelectionModel().getSelectedItem();

        if (odabranoJelo != null) {
            tableJelo.getItems().remove(odabranoJelo);

            ocistiPoljaJela();
        } else {
            System.out.println("Nije odabrano jelo za brisanje");
        }
    }

    private void obrisiSvaJela() {
        tableJelo.getItems().clear();
        ocistiPoljaJela();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
