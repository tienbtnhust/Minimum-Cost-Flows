package application;

import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

public class Edge {
	public Node source, target;
    public Shape line;
    public Arrow arrow;
    public double cost;
    public double capacity;
    public Label EdgeLabel;
    public Shape getline() {
    	return line;
    }
    public Edge(Node Source,Node Target,Shape line) {
    	super();
    	this.source = Source;
    	this.target = Target;
    	this.line = line;
    }
    public Edge(Node Source,Node Target,Shape line,Arrow arrow) {
    	super();
    	this.source = Source;
    	this.target = Target;
    	this.line = line;
    	this.arrow = arrow;
    }
    
    public Edge(Node source, Node target, Shape line, Arrow arrow, double cost, double capacity, Label edgeLabel) {
		super();
		this.source = source;
		this.target = target;
		this.line = line;
		this.arrow = arrow;
		this.cost = cost;
		this.capacity = capacity;
		EdgeLabel = edgeLabel;
	}
	public Edge(Node Source,Node Target,double cost,Shape line,Label EdgeLabel) {
    	super();
    	this.source = Source;
    	this.target = Target;
    	this.cost = cost;
    	this.line = line;
    	this.EdgeLabel = EdgeLabel;
    }
}
