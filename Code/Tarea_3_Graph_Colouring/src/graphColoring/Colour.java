package graphColoring;

import java.util.ArrayList;

public class Colour {
	private ArrayList<Vertex> elements;

	public Colour(ArrayList<Vertex> elements) {
		this.elements = elements;
	}

	public Colour() {
		this.elements=new ArrayList<Vertex>();
	}

	public ArrayList<Vertex> getElements() {
		return elements;
	}

	public void setElements(ArrayList<Vertex> elements) {
		this.elements = elements;
	}

	public void printId(){
		for (int i=0;i< elements.size();i++)
			System.out.println(elements.get(i).getId());
	}
}
