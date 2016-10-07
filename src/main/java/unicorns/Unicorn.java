package unicorns;

import java.sql.Timestamp;

/**
 * A simple class representing a unicorn.
 * 
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class Unicorn {
	public int id = 0;
	public String name = "";
	public String description = "";
	public String reportedBy = "";
	public Location spottedWhere = new Location();
	//public Timestamp spottedWhen = new Timestamp(0);
	public String image = "";
	
	public Unicorn() {
		
	}
	
	public String toString(){
		String str = "";
		str = str + id + "\n";
		str = str + name + "\n";
		str = str + description + "\n";
		str = str + reportedBy + "\n";
		str = str + spottedWhere.name + "\n";
		str = str + spottedWhere.lat + "\n";
		str = str + spottedWhere.lon + "\n";
		str = str + image;

		return str;
	}
}
