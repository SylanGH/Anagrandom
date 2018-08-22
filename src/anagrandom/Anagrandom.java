package anagrandom;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Lexie
 */
public class Anagrandom extends Application 
{

    @Override
    public void start(Stage stage) 
    {        
        ArrayList<String> strings = new ArrayList<>();

        Label wordLabel = new Label("Word to Scramble:");
        TextField wordField = new TextField();
        HBox wordPane = new HBox(20);
        wordPane.setAlignment(Pos.CENTER);
        wordPane.getChildren().addAll(wordLabel, wordField);
        
        HBox buttonPane = new HBox(20);
        buttonPane.setAlignment(Pos.CENTER);
        Button generate = new Button("Generate Anagrams");
        Button clear = new Button("Clear List");
        buttonPane.getChildren().addAll(generate, clear);
        
        TextArea wordDisplay = new TextArea();
        wordDisplay.setEditable(false);
        
        EventHandler<ActionEvent> handler = 
                event->
                {
                    final String wordString = wordField.getText();  
                    StringBuilder displayStringBuilder = new StringBuilder("");
                                        
                    int limiter = 0;
                    
                    for(int i = 0; i < 10; i++)
                    {
                        if(limiter >= 100)
                        {
                            JOptionPane.showMessageDialog(null, "No new combinations found after 100 tries, no longer generating new combinations");
                            break;
                        }
                        
                        Random rng = ThreadLocalRandom.current();
                        StringBuilder newCombo = new StringBuilder(wordString);
                        
                        for(int j = wordString.length() - 1; j > 0; j--)
                        {
                            int index = rng.nextInt(j+1);
                            
                            char a = newCombo.charAt(index);
                            newCombo.setCharAt(index, newCombo.charAt(j));
                            newCombo.setCharAt(j, a);
                            
                        }
                        
                        if(!strings.contains(newCombo.toString()))
                        {
                            strings.add(newCombo.toString());
                        }else
                        {
                            limiter++;
                            i--;
                        }
                    }
                    
                    int numCases = 0;
                    
                    for(int i = 0; i < strings.size(); i++)
                    {
                        if(numCases % 10 == 0 && numCases != 0)
                        {
                            displayStringBuilder.append("\n");
                        }
                        numCases++;

                        displayStringBuilder.append(strings.get(i));
                        displayStringBuilder.append("\n");
                    }
                    
                    wordDisplay.setText(displayStringBuilder.toString());
                };
        
        EventHandler<ActionEvent> clearAction = 
                event->
                {
                    strings.clear();
                    wordDisplay.setText("");
                };
        
        generate.setOnAction(handler);
        wordField.setOnAction(handler);
        clear.setOnAction(clearAction);
        
        VBox mainPane = new VBox(20);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().addAll(wordPane, buttonPane, wordDisplay);
        
        Scene scene = new Scene(mainPane, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Anagrandom");
        stage.show();
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    public static void alterString(String toChange, String newString)
    {
        toChange = newString;
    }
}
