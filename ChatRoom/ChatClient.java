package clientapp.clientapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClient extends Application {
    FlowPane buttonPane;
    BorderPane borderPane;
    Label label;
    Button send;
    Button save;
    Button open;
    TextField textField;
    TextArea textArea;

    Socket mySoket;
    DataInputStream dis;
    PrintStream ps;

    @Override
    public void init() throws Exception {
        textArea = new TextArea();
        textArea.setEditable(false);
        textField = new TextField();
        send = new Button("Send");
        send.setDefaultButton(true);
        save = new Button("Save");
        open = new Button("Open");
        label = new Label("Enter Your Message");


        buttonPane = new FlowPane(label, textField, send, save, open);
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        borderPane = new BorderPane();
        borderPane.setCenter(textArea);
        borderPane.setBottom(buttonPane);

        mySoket = new Socket("127.0.0.1", 7777);
        dis = new DataInputStream(mySoket.getInputStream());
        ps = new PrintStream(mySoket.getOutputStream());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String replyMsg;
                    try {
                        replyMsg = dis.readLine();
                        textArea.appendText("\n" + replyMsg);
                    } catch (IOException e) {
                        System.out.println("Client side Thread Error ");

                        try {
                            ps.close();
                            dis.close();
                            mySoket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        }).start();

    }

    @Override
    public void start(Stage stage) throws IOException {

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ps.println(textField.getText());
                textField.setText("");
            }
        });
        open.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt files", "*.txt"));
                    File openfile = fc.showOpenDialog(stage);
                    DataInputStream data = new DataInputStream(new FileInputStream(openfile.getPath()));
                    while(data.available()>0){
                        String c = data.readLine();
                        System.out.println(c);
                        textArea.appendText(c+"\n");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        save.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt files", "*.txt"));
                    File savefile = fc.showSaveDialog(null);
                    FileWriter fw = new FileWriter(savefile);
                    fw.write(textArea.getText());
                    fw.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }


            }

        });

        Scene scene = new Scene(borderPane, 400, 400);
        stage.setTitle("Chat Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}