/**
* @author: Shruthi s3613612
*/
package ozlympicgames;

abstract class Person
{
	private String id;
        private String type;
	private String name;
	private int age;
	private String state;

	/* Constructor */
	Person(String id, String type, String name, int age, String state)
	{	
		this.id = id;
                this.type = type;
		this.name = name;
                this.age = age;
		this.state = state;	
	}
        
        public String toString()
        {
            return this.id+" "+this.type+" "+this.name;	
        }
        
        public int compareTo(Person that)
        {
            if(this.id.equals(that.id) && this.name.equals(that.name))
                return 0;
            else
                return 1;
        }
                
	
	/* Accessors and mutators */
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return this.id;
	}
        public void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return this.type;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setAge(int age)
        {
                this.age = age;
        }
        
        public int getAge()
        {
                return this.age;
        }
	public void setState(String state)
	{
		this.state = state;	
	}
	public String getState()
	{
		return this.state;
	}
}
