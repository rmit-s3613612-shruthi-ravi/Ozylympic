/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ozlympicgames;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.collections.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.*; 
import javafx.event.EventType;
import javafx.stage.Modality;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet; 
import java.sql.Statement; 


/**
 *
 * @author Shruthi s3613612
 */

public class OzLympic extends Application {
    
    private ObservableList<ObservableList> data = FXCollections.observableArrayList();
    
    Connection con = null;//for hssql db connection
    Statement stmt = null;//sql statement 
    ResultSet result = null;//result set
    int dbError=0;//db error code
    
    //dialog box
    final JOptionPane pane = new JOptionPane("This player is already added to game.");
    final JDialog d = pane.createDialog((JFrame)null, "Title");
    
    //max and min players    
    final int MAX_PLAYERS=8;
    final int MIN_PLAYERS=4;
    
    //buttons for gui
    final Button btnNewGame = new Button ("New Game");
    final Button btnRunGame = new Button ("Run this Game");
    final Button btnClose = new Button ("Close Application");
    
    //text area and combo box for gui
    TextArea text = new TextArea ("");
    final ComboBox gameChoice = new ComboBox();
    final ComboBox athleteChoice = new ComboBox();
    final ComboBox officialChoice = new ComboBox();
    
    //text file name to read        
    final String fileName="participants.txt";
    
    //values for game type
    final ObservableList<String> gameType = FXCollections.observableArrayList(
        "Swimming",
        "Cycling",
        "Running"
        );
    
    //exceptions
    class GameFullException extends Exception { };
    class TooFewAthleteException extends Exception { };
    class NoRefereeException extends Exception { };
    
    //list of game athletes
    ObservableList<String> gameAthletes;
    ObservableList<String> gameOfficials = FXCollections.observableArrayList();   
        
    ArrayList<String> idList = new ArrayList<String>();
    
    ArrayList<Sprinter> spr = new ArrayList<Sprinter>();  
    ArrayList<Swimmer> swr = new ArrayList<Swimmer>();
    ArrayList<Cyclist> cyc = new ArrayList<Cyclist>();
    ArrayList<SuperAthlete> sup = new ArrayList<SuperAthlete>(); 
    ArrayList<Official> off = new ArrayList<Official>();
    
    Game g;
    Athlete[] athletesArr; 
    Official refree;
    int playerCount;
    String type;
    int errorCode;
    
         
    @Override
    public void start(Stage stage) throws Exception {
        
        
        d.setLocation(600,550);
        text.setEditable(false);
        type="Swimming";
             
        //JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
               
        buildData();
         
        if(dbError>0)
            loadDataText();
               
        newGame(type);
       
        
        gameChoice.setPromptText("Select Game Type:");
        gameChoice.setItems(gameType);
        gameChoice.setVisibleRowCount(3);
        gameChoice.setEditable(false); 
        
        
        athleteChoice.setPromptText("Select Athletes for Game:");
        athleteChoice.setItems(gameAthletes);
        athleteChoice.setVisibleRowCount(8);
        athleteChoice.setEditable(false); 
        
        
        officialChoice.setPromptText("Select a Game Official:");
        officialChoice.setItems(gameOfficials);
        officialChoice.setVisibleRowCount(4);
        officialChoice.setEditable(false); 
        
                        
        stage.setTitle("OzLympic Game");
        Scene scene = new Scene(new Group(), 480, 320);
        
        gameChoice.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                type=t1;
                newGame(type);
                
                           }    
        });
        
        officialChoice.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                String[] s=t1.split(": ");
                String id=s[0];
                Iterator itr = off.iterator();
                while(itr.hasNext())
                {
                    Official o = (Official) itr.next();
                    if(o.getId().equalsIgnoreCase(id))
                    {
                        refree=o;
                        break;
                    }
                }
                //newGame(type);
                
                //JOptionPane.showMessageDialog(null, type);
            }    
        });
        
        athleteChoice.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                String[] s = t1.split(": ");
                String id=s[0];
                String type=s[1];
                Iterator itr;
                Athlete a;
                boolean found=false;
                
                athleteChoice.setValue(t1);
                
                if(playerCount+1==MAX_PLAYERS)
                    JOptionPane.showMessageDialog(null, "Game is full. More players can't be added to this game");
                else
                {
                    
                    for(int i=0;i<playerCount;i++)
                    {
                        if(athletesArr[i].getId().equals(id))
                        {
                            found=true;
                            d.setVisible(true);
                            //break;
                        }
                    }
                    
                    //if(found==true)
                    //{
                        //d.setVisible(true);
                    //}
                        
                    if(!found)
                    {
                        if(type.equalsIgnoreCase("Swimmer"))
                            itr = swr.iterator();
                        else if(type.equalsIgnoreCase("Sprinter"))
                            itr = spr.iterator();
                        else if(type.equalsIgnoreCase("Cyclist"))
                            itr = cyc.iterator();
                        else
                            itr = sup.iterator();

                        while(itr.hasNext())
                        {
                            a=(Athlete)itr.next();
                            if(a.getId().equalsIgnoreCase(id))
                            {
                                athletesArr[playerCount]=a;
                                text.setText(text.getText()+a.toString()+"\n");
                                playerCount++;
                                //break;
                            }
                        }
                    }
                }    
                
            }    
        });
        
         btnNewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newGame(type);
         
            }
        });
        
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent f) {
                stage.close();
            } 
        });
        
        btnRunGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                    errorCode=0;
                    if(refree==null)
                        errorCode=1;
                    //else if(playerCount+1==MAX_PLAYERS)
                        //errorCode=2;
                    else if(playerCount<MIN_PLAYERS)
                        errorCode=2;
                                         
                    //if((playerCount+1)>=MIN_PLAYERS)
                        runGame(errorCode);
                }
                catch(Exception f) { }
                
            } 
        });
        
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        grid.add(new Label("Game: "), 0, 0);
        grid.add(gameChoice, 1, 0);
        grid.add(new Label("Official: "), 0, 1);
        grid.add(officialChoice, 1, 1);
        grid.add(new Label("Athletes: "), 0, 2);
        grid.add(athleteChoice, 1, 2);
        grid.add(new Label("Athletes added to Game: "), 0, 3);
        grid.add(text, 0, 4, 3, 1);
        grid.add(btnNewGame, 0, 5);
        grid.add(btnRunGame, 1,5);
        grid.add(btnClose, 2,5);
        
        
        gameChoice.setValue(type);
        
               
        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();

               
        
    }
    
    public void newGame(String type)
    {
        athletesArr = null;
        athletesArr = new Athlete[8];
        playerCount=0;
        errorCode=0;
        loadAthletes(type);
        athleteChoice.setItems(gameAthletes);
        
        text.clear();
        
    }
    
       
    private void runGame(int code) throws Exception {
        
        if(errorCode>0)
        {
            try {
                try {
                    if(code==1)
                        throw new NoRefereeException();
                }
                catch (Exception NoRefereeException) {
                    JOptionPane.showMessageDialog(null, "Error: No Official selected");
                }
                try {
                    if(code==2)
                    throw new TooFewAthleteException();
                }
                catch (Exception TooFewAthleteException) {
                    JOptionPane.showMessageDialog(null, "Error: Too few athletes selected");
                }
                
            }
            catch (Exception e){    
            
            }
        }
        else
        
        g = new Game(type, playerCount, refree, athletesArr);
        g.startGame();
        String result = g.returnResult();
        JOptionPane.showMessageDialog(null, result);
        g.writeResults();
        g.writeResultsDB();
        
        
    }
     
    private int checkDuplicate(String id, ArrayList<String> idList)
    {
        String listId;
        Iterator itr = idList.iterator();
        while(itr.hasNext())
        {
            listId = itr.next().toString();
            if(id.equals(listId))
                return 0;
        }
        return 1;
    }
    
    private void display(ObservableList list)
    {
        Iterator itr = list.iterator();
        while(itr.hasNext())
            System.out.println(itr.next().toString());
    }
    
    private void loadDataText()
    {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
                        
            String str;
            while ((str = in.readLine()) != null) 
            {
                String[] ar=str.split(",");
                String id, type, name, age, state;
                int intAge;
                
                id=ar[0];
                type=ar[1];
                name=ar[2];
                age=ar[3];
                state=ar[4];
                
                createObject(id,type,name,age,state);
             
            }
            in.close();
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, "Text File Read Error");
            System.exit(1);
        }
        
    }
    
    private void createObject(String id, String type, String name, String age, String state)
    {
        if(!id.equals("") && !type.equals("") && !name.equals("") && !age.equals("") && !state.equals(""))
        {
            if(checkDuplicate(id, idList)>0)
            {
                idList.add(id);
                
                if(type.equalsIgnoreCase("sprinter"))
                    spr.add(new Sprinter(id,type,name,Integer.parseInt(age),state));
                else if(type.equalsIgnoreCase("swimmer"))
                    swr.add(new Swimmer(id,type,name,Integer.parseInt(age),state));
                else if(type.equalsIgnoreCase("cyclist"))
                    cyc.add(new Cyclist(id,type,name,Integer.parseInt(age),state));
                else if(type.equalsIgnoreCase("super"))
                    sup.add(new SuperAthlete(id,type,name,Integer.parseInt(age),state));
                else
                {
                    gameOfficials.add(id+": "+type+": "+name);
                    off.add(new Official(id,type,name,Integer.parseInt(age),state));
                }
                    
            }
        }
    }


    private void loadAthletes(String gameType)
    {
        gameAthletes = FXCollections.observableArrayList();
        String id, type, name;
        Athlete athlete;
        Iterator itr;
        
        if(gameType.equalsIgnoreCase("swimming"))
            itr = swr.iterator();
        else if(gameType.equalsIgnoreCase("running"))
            itr = spr.iterator();
        else
            itr = cyc.iterator();
        
        while(itr.hasNext())
        {
            athlete = (Athlete) itr.next();
            gameAthletes.add(athlete.getId()+": "+athlete.getType()+": "+athlete.getName());
        }
        
        itr = sup.iterator();
        while(itr.hasNext())
        {
            athlete = (Athlete) itr.next();
            gameAthletes.add(athlete.getId()+": "+athlete.getType()+": "+athlete.getName());
        }
    }
            
     public void buildData(){
             
        try
        {
               //Registering the HSQLDB JDBC driver 
                Class.forName("org.hsqldb.jdbc.JDBCDriver");

                //Creating the connection with HSQLDB
                con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/OzLympics/participants", "", "");

                if (con!= null)
                {
                    //System.out.println("Connection created successfully");
                    stmt = con.createStatement(); 
                    result = stmt.executeQuery( "SELECT * FROM participants");
                    String id, type, name, age, state;
                   
                    while(result.next())
                    { 
                        id=result.getString("id");
                        type=result.getString("type");
                        name=result.getString("name");
                        age=result.getString("age");
                        state=result.getString("state");
                        
                        createObject(id,type,name,age,state);
                        
                    }
                }
        }
        catch(Exception e)
        {
            dbError=1;
            JOptionPane.showMessageDialog(null, "DB connection could not be established for reading");          
        }
     }
     public static void main(String[] args) {
         Application.launch(args);
      }

}
