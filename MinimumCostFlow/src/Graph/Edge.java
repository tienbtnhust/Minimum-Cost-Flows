package Graph;

import java.util.Optional;

import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Edge {
	public Node source, target;
    public Shape line;
    public Arrow arrow;
    public double cost;
    public double capacity;
    public double flow;
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
    
    public Edge(Node Source, Node Target, Shape line, Arrow arrow, double cost, double capacity, Label edgeLabel) {
		super();
		this.source = Source;
		this.target = Target;
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
	public Edge(Node Source,Node Target) {
		super();
		Line newline = new Line();
		newline.setStrokeWidth(2);
		newline.startXProperty().bind(Source.circle.layoutXProperty().add(Source.circle.translateXProperty()).add(Source.circle.widthProperty().divide(2)));
	    newline.startYProperty().bind(Source.circle.layoutYProperty().add(Source.circle.translateYProperty()).add(Source.circle.heightProperty().divide(2)));
	    newline.endXProperty().bind(Target.circle.layoutXProperty().add(Target.circle.translateXProperty()).add(Target.circle.widthProperty().divide(2)));
	    newline.endYProperty().bind(Target.circle.layoutYProperty().add(Target.circle.translateYProperty()).add(Target.circle.heightProperty().divide(2)));
	    Arrow newarrow = Arrow.getArrow(newline);
	    double cost = getCost();
		double capacity = getCapacity();
		Label newLabel = getEdgeLabel(newline,cost,capacity);
		this.arrow=newarrow;
		this.capacity=capacity;
		this.cost=cost;
		this.EdgeLabel=newLabel;
		this.line=newline;
		this.source=Source;
		this.target=Target;
		Target.addInEdge(this);
		Source.addOutEdge(this);
	}
	// Create dialog to input cost of edge
    double getCost() {
		TextInputDialog InCost = new TextInputDialog();
		InCost.setTitle("Edge Information");
		InCost.setHeaderText("Enter the cost of the new Edge:");
		InCost.setContentText(null);
		Optional<String> result = InCost.showAndWait();
		if (result.isPresent()) {
			if (result.get().isBlank()) return 0;
			else return Double.parseDouble(result.get());
		}
		else return 0;
	}
	// Create dialog to input capacity of edge
	double getCapacity() {
		TextInputDialog InCapacity = new TextInputDialog();
		InCapacity.setTitle("Edge Information");
		InCapacity.setHeaderText("Enter the capacity of the new Edge:");
		InCapacity.setContentText(null);
		Optional<String> result = InCapacity.showAndWait();
		if (result.isPresent()) {
			if (result.get().isBlank()) return 0;
			else return Double.parseDouble(result.get());
		}
		else return 0;
	}
	// Show cost and capacity on edge
	Label getEdgeLabel(Line line,double cost,double capacity) {
		Label newLabel = new Label();
		newLabel.setLayoutX((line.getStartX()+line.getEndX())/2);
		newLabel.setLayoutY((line.getStartY()+line.getEndY())/2);
		newLabel.setText(String.valueOf(cost)+'/'+String.valueOf(capacity));
		return newLabel;
	}
}
