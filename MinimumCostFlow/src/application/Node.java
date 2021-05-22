package application;

import java.util.ArrayList;

import javafx.scene.layout.StackPane;

public class Node {
	public String name;
	// Canh di vao Node
	public ArrayList<Edge> InEdges = new ArrayList<Edge>();
	// Canh di ra tu Node
	public ArrayList<Edge> OutEdges= new ArrayList<Edge>();
	public Node preNode;
	public StackPane circle; 
	public boolean visited;
	public double mincost;
	public double flow;
	public Node(String name) {
		super();
		this.name = name;
		this.visited=false;
		this.mincost = Double.POSITIVE_INFINITY;
		this.flow = 0;
	}
	public Node(String name,StackPane circle) {
		super();
		this.name = name;
		this.circle=circle;
		this.visited=false;
		this.mincost = Double.POSITIVE_INFINITY;
		this.flow = 0;
	}
}
