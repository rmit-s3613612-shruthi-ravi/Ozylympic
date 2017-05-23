/**
* @author: Shruthi s3613612
*/

package ozlympicgames;

class Athlete extends Person
{
	private int finishTime;
	private int points;
	private boolean played;
	/* Constructor */
	Athlete(String id, String type, String name, int age, String state)
	{
		super(id, type, name, age, state);
		this.points = 0;	
		this.played = false;
	}
        
        public String toString()
        {
            return super.toString();
        }
        
        public int compareTo(Athlete that)
        {
            return super.compareTo(that);
        }
        
        public String getId()
	{
		return super.getId();
	}
        
        /* Method to compute finish time */
	public void compete()
	{
		played = true;
     	}
        
	public void displayPoints()
	{
		System.out.println(super.getName() + " " + points);
	}
        
	/*  Accessors and Mutators */
	public void setFinishTime(int finishTime)
        {
                this.finishTime = finishTime;
        }
        public int getFinishTime()
        {
                return this.finishTime;
        }
	public void updatePoints(int points)
        {
                this.points = this.points + points;
        }
        public int getPoints()
        {
                return this.points;
        }
	public boolean hasPlayed()
	{
		return played;
	}
	
}
