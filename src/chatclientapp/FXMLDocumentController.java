/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import message.ChatMessage;
import message.Participants;

/**
 *
 * @author Ohjelmistokehitys
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    TextField chatMessage;
    
    @FXML
    TextArea chatMessageArea;
    
    @FXML
    ColorPicker colorPickerSelection;
    
    @FXML 
    TextField nickNameField;
    
    @FXML
    ListView listOfUsers;    
    
    @FXML
    ComboBox fontSelector;
    
    @FXML
    ColorPicker colorSelector;
    
    ClientBackEnd backEnd;
    Thread backThread;


    
    @FXML
    public void sendChatMessage(ActionEvent ae){
        
        ChatMessage cm = new ChatMessage();
        cm.setUserName(nickNameField.getText());
        cm.setChatMessage(chatMessage.getText());

        backEnd.sendMessage(cm);
    }    

    @FXML
    public void setNickname(ActionEvent ae){
        
        if(nickNameField.getText().length()!=0){
            Participants p = new Participants();
            chatMessage.setDisable(false);
            nickNameField.setDisable(true);
            p.setParticipants(nickNameField.getText());
            backEnd.sendParticipants(p);


        }
    }


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backEnd = new ClientBackEnd(this);
        backThread = new Thread(backEnd);
        backThread.setDaemon(true);
        backThread.start(); //suorittaa backendin run-funktion
        chatMessage.setDisable(true);        
        
    }    
    public void updateTextArea(String m){
        //Text text1 = new Text(m);
        //text1.setFill(Color.RED);
        //TextFlow textFlow = new TextFlow(text1);
        chatMessageArea.appendText(m + "\n");
    }
    
    @FXML
    public void setlistOfUsers(ObservableList<String> users){
        //listOfUsers.getItems().removeAll(listOfUsers.getItems());
        //listOfUsers.setItems(users);
    }
}
