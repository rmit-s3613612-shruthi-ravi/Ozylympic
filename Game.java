/**
* @author: Neha
*/
package ozlympicgames;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.Statement; 
import java.sql.ResultSet;
import javax.swing.JOptionPane;

class Game
{
	private static int countGames = 0;
        private int countPlayers;
        
	private String id;
        private String gameType;
        private String time; 
	
	Official official;
	Athlete[] athletesArr; 
        
        Connection con = null; 
        Statement stmt = null; 
        //ResultSet output = null; 
        //int result = 0;
        
        
                
           
	
	public enum WinnerPoints
	{
		FIRST(5),
		SECOND(2),
		THIRD(1);
		private int value;
		private WinnerPoints(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	}
	
        /* Constructor */
	
        Game(String gameType, int countPlayers, Official official, Athlete[] athletesArr)
	{
            //Date date = new Date();
            //time = new SimpleDateFormat("YYYY-MM-dd h:mm:ss a").toString();
            //System.out.println(time);
            
            this.gameType = gameType;
            this.countGames++;
            this.countPlayers = countPlayers;
            this.official = official;
            this.athletesArr = athletesArr;
            
            if(gameType.equalsIgnoreCase("swimming"))
                    this.id = "S0"+this.countGames;
            else if(gameType.equalsIgnoreCase("running"))
                    this.id = "R0"+this.countGames;
            else
                this.id = "C0"+this.countGames;
		
             
           
	}
        
        /** 
	* @param: none
	* @return: none
	* @function: to start the game when user selects option 3
	*/
	public void startGame()
	{
		for(int i=0; i < countPlayers; i++)
		{
			athletesArr[i].compete();
		}
		sort(athletesArr);
		athletesArr[0].updatePoints(WinnerPoints.FIRST.getValue());
		athletesArr[1].updatePoints(WinnerPoints.SECOND.getValue());
		athletesArr[2].updatePoints(WinnerPoints.THIRD.getValue());
                System.out.println(countPlayers);   
	}
        
        /** 
	* @param: none
	* @return: none
	* @function: to sort athlete array according to finish time
	*/
	void sort(Athlete[] arr)
	{
		int j;
		Athlete temp;
                Athlete temp1;
		for(int i=1; i<countPlayers; i++)
		{
			j = i-1;
			temp = arr[i];	
			while(j >= 0 && temp.getFinishTime() < arr[j].getFinishTime())
			{
                                temp1 = arr[j];
				arr[j+1] = temp1;
				j--;
			}
			arr[j+1] = temp;
		}
	}
        
        
        @Override
        public String toString()
        {
            String s = id+","+official.getId()+","+time+"\n";
            for(int i=0; i < countPlayers; i++)
            {
			s += athletesArr[i].getId()+","+athletesArr[i].getFinishTime()+","
                                +athletesArr[i].getPoints()+"\n";
            }
            return s;
            
        }
        
        public String returnResult()
	{
            String s = "Game Type: "+gameType+"\n"+"Game ID: "+id+"\n"+
                    "Referee: "+ official.getName()+"\n"+
                    "First: " + athletesArr[0].getName()+"\n"+
                    "Second: " + athletesArr[1].getName()+"\n"+
                    "Third: " + athletesArr[2].getName()+"\n";
            writeResults();
            return s;
        }

        
	/** accessors and mutator */
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return id;
	}
	public static void incrCountGames()
	{
		countGames++;	
	}
	public static int getCountGames()
	{
		return countGames;
	}
	public void setGameType(String gameType)
	{
		this.gameType = gameType;
	}
	public String getGameType()
	{
		return gameType;
	}
	public void setCountPlayers(int count)
	{
		countPlayers = count;
	}
	public int getCountPlayers()
	{
		return countPlayers;
	}
	public void printAthletes()
	{
		int j;
		for(int i=0; i < athletesArr.length; i++)
		{
			j = i+1;	
			System.out.println(j + ". " + athletesArr[i].getName());
		}
	}
	
	public void writeResults()
        {
            String line;
            try
            {
                FileWriter fw = new FileWriter("results.txt", true) ;
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                line=id+gameType;
                out.println(line);
                Athlete a;
                for(int i=0;i<countPlayers;i++)
                {
                    a=athletesArr[i];
                    line=a.getId()+a.getName();
                    out.println(line);
                }
                
                
                //more code
                out.println("more text");
                //more code
            }
            catch (IOException e) 
            {
            //exception handling left as an exercise for the reader
            }
        }
        
       public void writeResultsDB()
       {
           try 
           { 
               Class.forName("org.hsqldb.jdbc.JDBCDriver");
               con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/OzLympics/OzLympics", "results", "");
               String id,officialId, athleteId1, athleteId2, athleteId3;
               int score1, score2, score3, result1, result2, result3;
               
               id=this.getId();
               officialId=this.official.getId();
               
               athleteId1=athletesArr[0].getId();
               athleteId2=athletesArr[1].getId();
               athleteId3=athletesArr[2].getId();
               
               score1=athletesArr[0].getPoints();
               score2=athletesArr[1].getPoints();
               score3=athletesArr[2].getPoints();
               
               result1=5;
               result2=2;
               result3=1;
               
                              
               stmt = con.createStatement(); 
               stmt.executeQuery("INSERT INTO RESULTS VALUES(Id,officialId, athleteId1, result1, score1");
               stmt.executeQuery("INSERT INTO RESULTS VALUES(Id,officialId, athleteId2, result2, score2");
               stmt.executeQuery("INSERT INTO RESULTS VALUES(Id,officialId, athleteId3, result3, score3");
               
       
           }
           catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "DB connection could not be established for writing");       
            }
           
       }
	
}
