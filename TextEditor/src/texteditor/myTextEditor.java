/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package texteditor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class myTextEditor extends Application {

    BorderPane mymainpanel = new BorderPane();

    @Override
    public void init() {

    }

    @Override
    public void start(Stage myStage) {

        TextArea textarea = new TextArea();

        MenuBar bar = new MenuBar();
        Menu file = new Menu("File");
        Menu view = new Menu("View");
        Menu format = new Menu("Format");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        //Items of File
        MenuItem newitem = new MenuItem("New");
//        newitem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/new-document.png"))));
        newitem.setAccelerator(KeyCombination.keyCombination("Ctrl+y"));
        newitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textarea.selectAll();
                textarea.clear();
            }
        });

        MenuItem openitem = new MenuItem("Open...");
//        openitem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/open-file.png"))));
        openitem.setAccelerator(KeyCombination.keyCombination("Ctrl+y"));
        openitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ac) {
                FileChooser openChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

                openChooser.getExtensionFilters().addAll(extFilter,
                        new FileChooser.ExtensionFilter("All Text", "*.text*"),
                        new FileChooser.ExtensionFilter("Text", "*.Text"));

                File myFile = openChooser.showOpenDialog(myStage);
                if (myFile != null) {
                    try {
                        Files.lines(myFile.toPath()).forEach(System.out::println);
                        FileReader fr = new FileReader(myFile);
                        int size = (int) myFile.length();
                        char[] arr = new char[size];
                        fr.read(arr);
                        textarea.setText(new String(arr));
                    } catch (IOException ex) {
                    }
                }
            }
        });

        MenuItem saveitem = new MenuItem("Save");
//        saveitem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/save-icon.png"))));
        saveitem.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        saveitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ac) {
                FileChooser saveChooser = new FileChooser();
                saveChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"));
                File myfile = saveChooser.showSaveDialog(myStage);

                try {
                    FileWriter myWriter = new FileWriter(myfile);
                    char[] arr = textarea.getText().toCharArray();
                    myWriter.write(arr);
                    myWriter.close();
                } catch (IOException e) {
                }
            }
        });

        MenuItem exititem = new MenuItem("Exit");
        exititem.setAccelerator(KeyCombination.keyCombination("Ctrl+p"));
        exititem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
        SeparatorMenuItem exitsep = new SeparatorMenuItem();

        file.getItems().addAll(newitem, openitem, saveitem, exitsep, exititem);

        //Items of Edit
        MenuItem undoitem = new MenuItem("Undo");
        undoitem.setAccelerator(KeyCombination.keyCombination("Ctrl+h"));
        undoitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textarea.undo();
            }
        });

        SeparatorMenuItem undosep = new SeparatorMenuItem();

        MenuItem cutitem = new MenuItem("Cut");
        cutitem.setAccelerator(KeyCombination.keyCombination("Ctrl+j"));
        cutitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textarea.cut();
            }
        });

        MenuItem copyitem = new MenuItem("Copy");
        copyitem.setAccelerator(KeyCombination.keyCombination("Ctrl+k"));
        copyitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textarea.copy();
            }
        });

        MenuItem pasteitem = new MenuItem("Paste");
        pasteitem.setAccelerator(KeyCombination.keyCombination("Ctrl+l"));
        pasteitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textarea.paste();
            }
        });

        MenuItem deleteitem = new MenuItem("Delete");
        deleteitem.setAccelerator(KeyCombination.keyCombination("Ctrl+;"));
        deleteitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textarea.deleteText(textarea.getSelection());
            }
        });

        SeparatorMenuItem selectsep = new SeparatorMenuItem();

        MenuItem selectitem = new MenuItem("Selectall");
        selectitem.setAccelerator(KeyCombination.keyCombination("Ctrl+m"));
        selectitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textarea.selectAll();
            }
        });

        edit.getItems().addAll(undoitem, undosep, cutitem, copyitem, pasteitem, deleteitem, selectsep, selectitem);

        //Items of View
        Menu zoomitem = new Menu("Zoom");
        MenuItem zoominitem = new MenuItem("Zoom In");
        zoominitem.setAccelerator(KeyCombination.keyCombination("Plus"));

        MenuItem zoomoutitem = new MenuItem("Zoom Out");
        zoomoutitem.setAccelerator(KeyCombination.keyCombination("Minus"));

        zoomitem.getItems().addAll(zoomoutitem, zoominitem);

        CheckMenuItem statusbaritem = new CheckMenuItem("Status Bar");

        view.getItems().addAll(zoomitem, statusbaritem);
        //Items of About
        MenuItem aboutitem = new MenuItem("About...");
        aboutitem.setAccelerator(KeyCombination.keyCombination("F10"));
        Alert aboutalert = new Alert(AlertType.INFORMATION);
        aboutalert.setTitle("About");
        aboutalert.setHeaderText("My Notepad Information");
        aboutalert.setContentText("Welcome to My Notepad Application ver1.0 !!");
        aboutitem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                aboutalert.showAndWait();
            }
        });

        help.getItems().addAll(aboutitem);
        bar.getMenus().addAll(file, edit, view, help);

        mymainpanel.setTop(bar);
        mymainpanel.setCenter(textarea);
        Scene mymainscene = new Scene(mymainpanel, 1000, 700);
        myStage.setTitle("My-Notepad");
        myStage.setScene(mymainscene);
        myStage.show();
    }
}
