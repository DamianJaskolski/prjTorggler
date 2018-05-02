package torggler.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import torggler.UserSingleton;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML //GLÓWNA RAMA
    BorderPane mainBorderPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public TextField tf_info;

    @FXML
    public TextField tf_infoNameSurname;

    @FXML
    public TextField tf_infoDepartment;

    @FXML
    public TreeView<String> mainTreeView;

    Image folder64x64 = new Image(getClass().getResourceAsStream("/img/folder64x64.png"));
    Image goods64x64 = new Image(getClass().getResourceAsStream("/img/goods64x64.png"));
    Image excel64x64 = new Image(getClass().getResourceAsStream("/img/excel64x64.png"));
    Image bucket64x64 = new Image(getClass().getResourceAsStream("/img/bucket64x64.png"));
    Image grout64x64 = new Image(getClass().getResourceAsStream("/img/grout64x64.png"));



    public void initialize(URL location, ResourceBundle resources) {

        setCenter("/fxml/login.fxml");



        //zablokowanie menu na starcie
       // mainTreeView.disableProperty().bind(UserSingleton.getInstance().isLoggedProperty().not());

        //menu okna głównego po lewej stronie
        TreeItem<String> root = new TreeItem<String>("Torggler");
        TreeItem<String> dry = new TreeItem<String>("Sucha", new ImageView(folder64x64));
        TreeItem<String> wet = new TreeItem<String>("Mokra", new ImageView(folder64x64));
        TreeItem<String> grout = new TreeItem<String>("Fugi", new ImageView(folder64x64));
        root.getChildren().addAll(dry, wet, grout);

        TreeItem<String> dryproduct = new TreeItem<String>("Produkty sucha", new ImageView(goods64x64));
        TreeItem<String> dryraport = new TreeItem<String>("Raport sucha", new ImageView(excel64x64));
        dry.getChildren().addAll(dryproduct, dryraport);

        TreeItem<String> wetproduct = new TreeItem<String>("Produkty mokra", new ImageView(bucket64x64));
        TreeItem<String> wetraport = new TreeItem<String>("Raport mokra", new ImageView(excel64x64));
        wet.getChildren().addAll(wetproduct, wetraport);

        TreeItem<String> groutproduct = new TreeItem<String>("Fugi", new ImageView(grout64x64));
        TreeItem<String> groutraport = new TreeItem<String>("Raport", new ImageView(excel64x64));
        grout.getChildren().addAll(groutproduct, groutraport);

        root.setExpanded(true);
        mainTreeView.setRoot(root);

        mainTreeView.disableProperty().bind(UserSingleton.getInstance().isLoggedProperty().not ());

    }


    public void setCenter(String fxmlPath) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

        AnchorPane mainAnchorPane = null;
        try {
            mainAnchorPane = loader.load();
            if (loader.getController () instanceof LoginController) {
                LoginController loginController = loader.getController ( );
                loginController.setMainController (this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return
        mainBorderPane.setCenter(mainAnchorPane);
    }

    public void mainOnMouseClickedTreeView(MouseEvent mouseEvent) {

        TreeItem<String> item = mainTreeView.getSelectionModel().getSelectedItem();

       // if (mouseEvent.getClickCount() == 2) {

                if (item.getValue() == "Produkty mokra") {

                    setCenter("/fxml/wetBase.fxml");
                    System.out.println("Base");



                /*//Stary kod podnieniający okno
                 AnchorPane anchorPane = null;
                try {
                    anchorPane = FXMLLoader.load(getClass().getResource("/fxml/wetBase.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

                }

                if (item.getValue() == "Raport mokra") {

                    setCenter("/fxml/wetReport.fxml");
                    System.out.printf("wet report");
                }

                //-------------------

        if (item.getValue() == "Produkty sucha") {

            setCenter("/fxml/login.fxml");
            System.out.println("Login");


        }


    }
}
