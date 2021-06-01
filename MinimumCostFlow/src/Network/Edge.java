package Network;

import java.util.Optional;

import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Edge {
	public Node source, target;
    public Line line;
    public Arrow arrow;
    public double cost;
    public int capacity;
    public int flow=0;
    public Label EdgeLabel;
    public Edge ResidualEdge;
    public Shape getline() {
    	return line;
    }
    public Edge(Node Source,Node Target,Line line) {
    	super();
    	this.source = Source;
    	this.target = Target;
    	this.line = line;
    }
    public Edge(Node Source,Node Target,Line line,Arrow arrow) {
    	super();
    	this.source = Source;
    	this.target = Target;
    	this.line = line;
    	this.arrow = arrow;
    }
    
    public Edge(Node Source, Node Target, Line line, Arrow arrow, double cost, int capacity, Label edgeLabel,Edge ReEdge) {
		super();
		this.source = Source;
		this.target = Target;
		this.line = line;
		this.arrow = arrow;
		this.cost = cost;
		this.capacity = capacity;
		EdgeLabel = edgeLabel;
		this.ResidualEdge = ReEdge;
	}
	public Edge(Node Source,Node Target,double cost,Line line,Label EdgeLabel) {
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
		newline.setStrokeWidth(0.5);
		newline.startXProperty().bind(Source.circle.layoutXProperty().add(Source.circle.translateXProperty()).add(Source.circle.widthProperty().divide(2)));
	    newline.startYProperty().bind(Source.circle.layoutYProperty().add(Source.circle.translateYProperty()).add(Source.circle.heightProperty().divide(2)));
	    newline.endXProperty().bind(Target.circle.layoutXProperty().add(Target.circle.translateXProperty()).add(Target.circle.widthProperty().divide(2)));
	    newline.endYProperty().bind(Target.circle.layoutYProperty().add(Target.circle.translateYProperty()).add(Target.circle.heightProperty().divide(2)));
	    //newline.setEndY(newline.getEndY()+3);
	    Arrow newarrow = Arrow.getArrow(newline,3,3);
	    double cost = getCost();
		int capacity = getCapacity();
		this.arrow=newarrow;
		this.capacity=capacity;
		this.cost=cost;
		this.line=newline;
		this.EdgeLabel=ChangeLabel();
		this.source=Source;
		this.target=Target;
		newline = new Line();
		newline.setStrokeWidth(0.5);
		newline.endXProperty().bind(Source.circle.layoutXProperty().add(Source.circle.translateXProperty()).add(Source.circle.widthProperty().divide(2)));
	    newline.endYProperty().bind(Source.circle.layoutYProperty().add(Source.circle.translateYProperty()).add(Source.circle.heightProperty().divide(2)));
	    newline.startXProperty().bind(Target.circle.layoutXProperty().add(Target.circle.translateXProperty()).add(Target.circle.widthProperty().divide(2)));
	    newline.startYProperty().bind(Target.circle.layoutYProperty().add(Target.circle.translateYProperty()).add(Target.circle.heightProperty().divide(2)));
	    ResidualEdge = new Edge(Target,Source,newline,Arrow.getArrow(newline, -3, -3),-cost,0,new Label(),this);
	    ResidualEdge.EdgeLabel = ResidualEdge.EdgeLabel();
		Target.addInEdge(this);
		Target.addOutEdge(ResidualEdge);
		Source.addOutEdge(this);
		Source.addInEdge(ResidualEdge);
	}
	public Edge() {
		super();
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
	int getCapacity() {
		TextInputDialog InCapacity = new TextInputDialog();
		InCapacity.setTitle("Edge Information");
		InCapacity.setHeaderText("Enter the capacity of the new Edge:");
		InCapacity.setContentText(null);
		Optional<String> result = InCapacity.showAndWait();
		if (result.isPresent()) {
			if (result.get().isBlank()) return 0;
			else return Integer.parseInt(result.get());
		}
		else return 0;
	}
	// Show cost and capacity on edge
	Label EdgeLabel() {
		Label newLabel = new Label();
		newLabel.setLayoutX((line.getStartX()+line.getEndX())/2);
		newLabel.setLayoutY((line.getStartY()+line.getEndY())/2);
		newLabel.setTranslateX(-20);
		newLabel.setTranslateY(-20);
		newLabel.setText(this.ResidualText());
		double angle=0;
		double length = (line.getEndY()-line.getStartY())*(line.getEndY()-line.getStartY()) + (line.getEndX()-line.getStartX())*(line.getEndX()-line.getStartX());
		length = Math.sqrt(length);
		angle = Math.acos((line.getEndX()-line.getStartX())/length);
		angle = Math.toDegrees(-angle);
		//newLabel.setRotate(angle);
		return newLabel;
	}
	Label ChangeLabel() {
		Label newLabel = new Label();
		newLabel.setLayoutX((line.getStartX()+line.getEndX())/2);
		newLabel.setLayoutY((line.getStartY()+line.getEndY())/2);
		newLabel.setTranslateX(20);
		newLabel.setTranslateY(20);
		newLabel.setText(String.valueOf(flow)+"/"+String.valueOf(capacity)+", "+String.valueOf(cost)+"$");
		double angle =0;
		double length = (line.getEndY()-line.getStartY())*(line.getEndY()-line.getStartY()) + (line.getEndX()-line.getStartX())*(line.getEndX()-line.getStartX());
		length = Math.sqrt(length);
		angle = Math.asin((line.getEndY()-line.getStartY())/length);
		angle = Math.toDegrees(angle);
		newLabel.setRotate(angle);
		return newLabel;
	}
	String ResidualText() {
		return String.valueOf(capacity-flow)+" / "+String.valueOf(cost) +"$";
	}
	public void changeFlow(int increase) {
		if (cost<0) {
			capacity -= increase;
			ResidualEdge.flow -= increase;
		}
		else {
			flow += increase;
			ResidualEdge.capacity +=increase;
		}
	}
	public int isExist() {
		return (capacity-flow);
	}
	public Edge cloneforview(){
		Edge ed = new Edge();
		ed.line = line;
		ed.arrow = arrow;
		ed.cost = cost;
		ed.flow = flow;
		ed.capacity = capacity;
		ed.EdgeLabel = EdgeLabel();
		return ed;
	}
}
