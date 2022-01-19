/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
import javafx.stage.Stage;

/**
 *
 * @author Omar Samir
 */
public class ChatRoomApp extends Application {

    BorderPane myPanel = new BorderPane();
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    TextArea textarea;
    TextField msgarea;
    FlowPane botpanel;
    Label msglabel;
    Button sendbtn;

    @Override
    public void init() {
        try {

            textarea = new TextArea();
            textarea.setText("Chat Messages :");
            textarea.setEditable(false);
            textarea.positionCaret(textarea.getText().length());

            msglabel = new Label("Enter your Message");
            msgarea = new TextField();
            msgarea.setPromptText("Enter a message");
            msgarea.setPrefWidth(350);
            msgarea.setPrefHeight(2);
            sendbtn = new Button("Send");
            sendbtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ps.println(msgarea.getText());
                    msgarea.setText("");
                }
            });

            botpanel = new FlowPane();
            botpanel.setOrientation(Orientation.HORIZONTAL);
            botpanel.getChildren().addAll(msglabel, msgarea, sendbtn);

            myPanel.setCenter(textarea);
            myPanel.setBottom(botpanel);

            mySocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String replyMsg;
                        try {
                            replyMsg = dis.readLine();
                            textarea.appendText("\n" + replyMsg);
                        } catch (IOException e) {
                            System.out.println("Exception from Reader");
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
        }
    }

    @Override
    public void start(Stage stg) {

        Scene mymainscene = new Scene(myPanel, 500, 500);
        stg.setTitle("Omar-Yahoo");
        stg.setScene(mymainscene);
        stg.show();
    }
}
