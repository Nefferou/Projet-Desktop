package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.mail.internet.InternetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    private ChangeListener listener;
    @FXML
    private TreeView listingEmails;
    @FXML
    private WebView viewver;
    @FXML
    private Label statusLabel;

    @FXML
    public void refreshAction() {
        System.out.println("Refresh");
        listingEmails.getSelectionModel().selectedItemProperty().removeListener(listener);

        listMails = EmailManager.getEmails();
        listMails.forEach(mail ->
                mail.persist()
        );

        initialize();
        String dateTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy à HH:mm"));
        statusLabel.setText("Boîte de réception synchronisée le "+dateTimeStr);
    }
    @FXML
    private void showAboutDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("A propos");

        // Ajoutez un bouton "OK" à la fenêtre modale
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        // Définir le contenu de la fenêtre
        VBox content = new VBox();
        content.getChildren().add(new Label("Projet Dev-Desktop"));
        content.getChildren().add(new Label("Bonjour, je m'appelle Julien Fertilati, je vous présente aujourd'hui ma boîte mail."));
        content.getChildren().add(new Label("Elle a été réaliser dans le cadre d'un projet Dev-Desktop en Java."));
        content.getChildren().add(new Label("Je tiens à remercier mon professeur Térence FERUT, les documentations et ChatGPT pour l'aide précieuse qu'ils m'ont apporté."));
        content.getChildren().add(new Label("Je tiens aussi à ne pas remercier Hugo GONCALVES pour m'avoir déconcentrer durant le cours et m'avoir donner plus de travaille pour chez moi"));
        content.getChildren().add(new Label("Merci à tous"));
        dialog.getDialogPane().setContent(content);

        dialog.showAndWait();
    }

    private List<Email> listMails = new ArrayList<>();

    public void initialize() {
        //Lecture mails
        listMails = Email.fetchAll();

        //Affichage du sujet de chaque mail dans la TreeView
        TreeItem<Object> root = new TreeItem<>("Boîte de réception");
        listMails.forEach(mail ->
                root.getChildren().add(new TreeItem<>(mail))
                );
        listingEmails.setRoot(root);
        root.setExpanded(true);

        //Affichage des infos du mail selectionné dans la webView
        SelectionModel sm = listingEmails.getSelectionModel();
        listener = (observableValue, oldValue, newValue) -> {
            TreeItem<Email> selectedItem = (TreeItem<Email>) newValue;
            if(selectedItem.getValue() instanceof Email) {
                Email selectedEmail = ((TreeItem<Email>) newValue).getValue();
                // Affichage infos mail dans la webview
                WebEngine webEngine = viewver.getEngine();
                DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy à HH:mm");
                String dateStr = dateFormat.format(selectedEmail.getDate());

                String webContent = "De : " + selectedEmail.getSender() + "<br>" +
                "Envoyé le : " + dateStr +
                "<br>---------------------------------------" +
                selectedEmail.getBody().replaceAll("\n", "<br>")+
                "---------------------------------------";
                webEngine.loadContent(webContent);
            }
        };
        sm.selectedItemProperty().addListener(listener);


    }
}