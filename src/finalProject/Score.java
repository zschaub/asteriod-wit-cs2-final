package finalProject;

public class Score {
	private static int total=0;
	
	
	public static int getScore(){
		return total;
	}
	public static void addScore(int add) {
		total+=add;
	}
	public static void setScore(int t) {
		total = t;
	}
}