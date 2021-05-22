package Graph;

import java.util.ArrayList;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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
	//private StackPane selectNode;
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
	public void setNode(String name,StackPane circle) {
		//super();
		this.name = name;
		this.circle=circle;
		this.visited=false;
		this.mincost = Double.POSITIVE_INFINITY;
		this.flow = 0;
	}
	public Node(int index,double vtX,double vtY) {
		super();
		Circle NodeCircle = new Circle();
		NodeCircle.setRadius(12);
		NodeCircle.setFill(Color.CORAL);
		NodeCircle.setVisible(true);
		Text NodeText = new Text();
		NodeText.setText(String.valueOf(index));
		StackPane newNodePane = new StackPane();
		newNodePane.getChildren().addAll(NodeCircle,NodeText);
		newNodePane.setTranslateX(vtX);
		newNodePane.setTranslateY(vtY);
		//newNodePane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
		setNode(String.valueOf(index),newNodePane);
	}
	public void addInEdge(Edge inedge) {
		this.InEdges.add(inedge);
	}
	public void addOutEdge(Edge outedge) {
		this.OutEdges.add(outedge);
	}
}
