package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class GraphCanvasController implements Initializable{

	@FXML
	private Button playable,NodeButton,EdgeButton,SaveButton,ResetButton;
	@FXML
	private AnchorPane GraphPane;
	private boolean node=false,edge = false;
	public ArrayList<Node> Nodes = new ArrayList<Node>();
	public ArrayList<Edge> Edges = new ArrayList<Edge>();
	public int nnode=0;
	public StackPane selectNode=null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	// action click New Node
	// sang che do tao dinh
	@FXML
	public void CreateNode(ActionEvent e) {
		node = true;
		edge = false;
	}
	// action click New Edge
	// sang che do tao canh
	@FXML
	public void CreateEdge(ActionEvent e) {
		if (Nodes.size() < 2) {
			 Alert alert = new Alert(AlertType.INFORMATION);
		     alert.setTitle("Warning!");
		     alert.setHeaderText(null);
		     alert.setContentText("Need more than 1 node to create edge!");
		     alert.showAndWait();
		     return;
		}
		node = false;
		edge = true;
	}
	// action click Save Graph
	// tat tao do thi
	@FXML
	public void SaveGraph(ActionEvent e) {
		node= false;
		edge = false;
	}
	Node getNodeFromShape(StackPane Dot) {
		for (int i=0;i<Nodes.size();++i)
			if (Nodes.get(i).circle.equals(Dot))
				return Nodes.get(i);
		return null;
	}
	Arrow getArrow(Line line) {
		Arrow arrow;
		double length = Math.pow(line.getStartX()-line.getEndX(),2)+Math.pow(line.getStartY()-line.getEndY(),2);
		length  = Math.sqrt(length);
		double eX = line.getEndX() + (line.getStartX()-line.getEndX())/length *12;
		double eY = line.getEndY() + (line.getStartY()-line.getEndY())/length *12;
		double sX = line.getStartX() - (line.getStartX()-line.getEndX())/length *12;
		double sY =line.getStartY() - (line.getStartY()-line.getEndY())/length *12;
		arrow = new Arrow(sX,sY,eX,eY);
		return arrow;
	}
	// Create dialog to input cost of edge
	double getCost() {
		TextInputDialog InCost = new TextInputDialog();
		InCost.setTitle("Edge Information");
		InCost.setHeaderText("Enter the cost of the new Edge:");
		InCost.setContentText(null);
		Optional<String> result = InCost.showAndWait();
		if (result.isPresent()) return Double.parseDouble(result.get());
		else return 0;
	}
	// Create dialog to input capacity of edge
	double getCapacity() {
		TextInputDialog InCapacity = new TextInputDialog();
		InCapacity.setTitle("Edge Information");
		InCapacity.setHeaderText("Enter the capacity of the new Edge:");
		InCapacity.setContentText(null);
		Optional<String> result = InCapacity.showAndWait();
		if (result.isPresent()) return Double.parseDouble(result.get());
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
	// click right mouse to delete vertex
	// click left mouse on 2 vertex to create edge
	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
		   @Override 
		   public void handle(MouseEvent e) { 
			   if (e.getButton() == MouseButton.SECONDARY) {
					selectNode = (StackPane) e.getSource();
					Node deleteNode = getNodeFromShape(selectNode);
					for (int i=0;i<deleteNode.InEdges.size();++i) {
						deleteNode.InEdges.get(i).source.OutEdges.remove(deleteNode.InEdges.get(i));
						Edges.remove(deleteNode.InEdges.get(i));
						GraphPane.getChildren().remove((Line)deleteNode.InEdges.get(i).line);
						GraphPane.getChildren().remove(deleteNode.InEdges.get(i).arrow);
						GraphPane.getChildren().remove(deleteNode.InEdges.get(i).EdgeLabel);
					}
					for (int i=0;i<deleteNode.OutEdges.size();++i) {
						deleteNode.OutEdges.get(i).source.InEdges.remove(deleteNode.OutEdges.get(i));
						Edges.remove(deleteNode.OutEdges.get(i));
						GraphPane.getChildren().remove((Line)deleteNode.OutEdges.get(i).line);
						GraphPane.getChildren().remove(deleteNode.OutEdges.get(i).arrow);
						GraphPane.getChildren().remove(deleteNode.OutEdges.get(i).EdgeLabel);
					}
					GraphPane.getChildren().remove(selectNode);
					for (int i=0;i<Nodes.size();++i)
						if (Nodes.get(i).circle.equals(selectNode)) {
							Nodes.remove(i);
						}
					selectNode = null;
				}
			   if (edge) {
			   if (e.getButton() == MouseButton.PRIMARY && selectNode == null && e.getClickCount()==1) {
				   selectNode = (StackPane) e.getSource();
				   Circle NodeCircle = (Circle) selectNode.getChildren().get(0);
				   NodeCircle.setFill(Color.GOLD);
			   }
			   else
			   if (e.getButton() == MouseButton.PRIMARY && e.getClickCount()==1) {
				   StackPane target = (StackPane) e.getSource();
				   Line newline = new Line();
				   newline.setStrokeWidth(2);
				   newline.startXProperty().bind(selectNode.layoutXProperty().add(selectNode.translateXProperty()).add(selectNode.widthProperty().divide(2)));
			       newline.startYProperty().bind(selectNode.layoutYProperty().add(selectNode.translateYProperty()).add(selectNode.heightProperty().divide(2)));
			       newline.endXProperty().bind(target.layoutXProperty().add(target.translateXProperty()).add(target.widthProperty().divide(2)));
			       newline.endYProperty().bind(target.layoutYProperty().add(target.translateYProperty()).add(target.heightProperty().divide(2)));
			       GraphPane.getChildren().remove(target);
			       GraphPane.getChildren().remove(selectNode);
			       Arrow newarrow = getArrow(newline);
			       GraphPane.getChildren().addAll(newline,target,selectNode,newarrow);
			       Circle NodeCircle = (Circle) selectNode.getChildren().get(0);
				   NodeCircle.setFill(Color.CORAL);
				   double cost = getCost();
				   double capacity = getCapacity();
				   Label newLabel = getEdgeLabel(newline,cost,capacity);
				   Edge newEdge = new Edge(getNodeFromShape(selectNode),getNodeFromShape(target),newline,newarrow,cost,capacity,newLabel);
				   Edges.add(newEdge);
				   GraphPane.getChildren().add(newLabel);
				   getNodeFromShape(selectNode).OutEdges.add(newEdge);
				   getNodeFromShape(target).InEdges.add(newEdge);
			       selectNode = null;
			   }
		   }  
		}
	};
	// click left mouse to create vertex
	@FXML
	public void handle(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY) return;
		//if (e.getClickCount() == 1) return;
		if (node) {
			if (Nodes.size() == 15) {
				Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Warning!");
			    alert.setHeaderText(null);
			    alert.setContentText("Too much nodes!");
			    alert.showAndWait();
			    return;
			}
			Circle NodeCircle = new Circle();
			NodeCircle.setRadius(12);
			NodeCircle.setFill(Color.CORAL);
			NodeCircle.setVisible(true);
			Text NodeText = new Text();
			NodeText.setText(String.valueOf(nnode+1));
			StackPane newNodePane = new StackPane();
			newNodePane.getChildren().addAll(NodeCircle,NodeText);
			newNodePane.setTranslateX(e.getX());
			newNodePane.setTranslateY(e.getY());
			newNodePane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
			Node newNode = new Node(String.valueOf(nnode+1),newNodePane);
			Nodes.add(newNode);
			//GraphPane.getChildren().add(NodeCircle);
			GraphPane.getChildren().add(newNodePane);
			nnode++;
		}
		if (edge) {
			return;
		}
	}
	// action click Play
	@FXML
	public void Playable(ActionEvent e) {
		//
	}
	//reset graph
	@FXML
	public void ResetGraph(ActionEvent e) {
		Nodes.clear();
		Edges.clear();
		GraphPane.getChildren().clear();
	}
}
