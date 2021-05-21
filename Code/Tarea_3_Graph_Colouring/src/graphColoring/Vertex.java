package graphColoring;

public class Vertex {
	private static int nextId=0;
	private int id;
//	private int group;

	public Vertex() {
		this.id = nextId;
//		this.group = -1;
		nextId++;
	}


	public int getId() {
		return id;
	}

//	public int getGroup() {	return group; }

//	public void setGroup(int group) {
//		this.group = group;
//	}

	public static void resetNextId(){ nextId=0;}
}
