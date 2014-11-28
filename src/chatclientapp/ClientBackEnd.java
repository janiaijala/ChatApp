/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import message.ChatMessage;
import message.Participants;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ClientBackEnd implements Runnable{

    private Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private FXMLDocumentController controller;
    
    //tämä ajetaan UI säikeessä, EDT säikeessä!! MIelellään kaikki toteutus siis run funktioon
    public ClientBackEnd(FXMLDocumentController controller){
        
        try {
            clientSocket = new Socket("localhost",3010); //localhost on sama kuin 127.0.0.1
            this.controller=controller;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //Tämä on sitten omassa säikeessä!!! 
    //Run funktiota kutsutaan vain kerran
    @Override
    public void run() {
        try {
            //kun sama objekti kyseessä, näiden järjestyksellä on väliä, ensin kirjoitus, sitten luku?
            output=new ObjectOutputStream(clientSocket.getOutputStream());
            input=new ObjectInputStream(clientSocket.getInputStream());

            
            //read and write from socket until user closes the app
            while(true){
            
                final ChatMessage m = (ChatMessage)input.readObject();

                //kun toissijaisessa säikeessä ollaan, runlater komento siirtää alla olevan komennon EDT säikeeseen
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        controller.updateTextArea(m.getUserName() + ": " + m.getChatMessage());
                    }
                });
                
            }    
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }        
    }
    
    public void sendParticipants(Participants p){
        try {
            output.writeObject(p);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void sendMessage(ChatMessage cm){
        
        try {
            output.writeObject(cm);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
