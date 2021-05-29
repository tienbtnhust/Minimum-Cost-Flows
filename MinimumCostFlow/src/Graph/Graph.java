package Graph;

import java.util.ArrayList;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Graph{
	public ArrayList<Node> Nodes = new ArrayList<Node>();
	public ArrayList<Edge> Edges = new ArrayList<Edge>();
	public StackPane selectNode;
	public AnchorPane GraphPane;
	public int nnode=0;
	public boolean node=false,edge=false,selectstart=false,selectend=false;
	public Node start,end;
	public double flow;
	Node getNodeFromShape(StackPane Dot) {
		for (int i=0;i<Nodes.size();++i)
			if (Nodes.get(i).circle.equals(Dot))
				return Nodes.get(i);
		return null;
	}
	// action delete vertex and create edge
	// create right mouse to delete vertex
	// create left mouse to 2 vertexes to create edge
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
						   Edge newEdge = new Edge(getNodeFromShape(selectNode),getNodeFromShape(target)); 
						   addEdge(newEdge);
						   Circle nodeCircle =(Circle) selectNode.getChildren().get(0);
						   nodeCircle.setFill(Color.CORAL);
						   selectNode = null;
					   }
			   }
			   if (!node && !edge && e.getButton() == MouseButton.PRIMARY) {
				   selectNode = (StackPane) e.getSource();
				   Circle NodeCircle = (Circle) selectNode.getChildren().get(0);
				   if (selectstart) {
					   NodeCircle.setFill(Color.ORCHID);
					   start = getNodeFromShape(selectNode);
					   SelectEndDialog();
					   selectstart = false;
				   } else
				   if (selectend) {
					   NodeCircle.setFill(Color.FORESTGREEN);
					   end = getNodeFromShape(selectNode);
					   EnterFlowDialog();
					   selectend = false;
				   }
				   selectNode = null;
			   }
		}
	};
	public Graph(AnchorPane graphPane) {
		super();
		this.GraphPane = graphPane;
	}
	public Graph() {
		super();
	}
	public void addNode(Node newnode) {
		newnode.circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
		GraphPane.getChildren().add(newnode.circle);
		Nodes.add(newnode);
		nnode++;
	}
	public void addEdge(Edge newEdge) {
		GraphPane.getChildren().removeAll(newEdge.source.circle,newEdge.target.circle);
		GraphPane.getChildren().addAll(newEdge.line,newEdge.source.circle,newEdge.target.circle,newEdge.arrow,newEdge.EdgeLabel);
		Edges.add(newEdge);
	}
	public void clear() {
		Nodes.clear();
		Edges.clear();
		nnode=0;
		node=false;
		edge=false;
	}
	public void remove() {
		//
	}
	public void view() {
		GraphPane.getChildren().clear();
		for (int i=0;i<Edges.size();++i) GraphPane.getChildren().addAll(Edges.get(i).arrow,Edges.get(i).line,Edges.get(i).EdgeLabel);
		for (int i=0;i<Nodes.size();++i) GraphPane.getChildren().add(Nodes.get(i).circle);
	}
	public void unselect() {
		if (selectNode == null) return;
		Circle SelectCircle =(Circle) selectNode.getChildren().get(0);
		SelectCircle.setFill(Color.CORAL);
		selectNode= null;
	}
	public void SelectStartDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Notification");
		alert.setContentText("Please choose the beginning vertex");
		alert.showAndWait();
		selectstart = true;
	}
	public void SelectEndDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Notification");
		alert.setContentText("Please choose the ending vertex");
		alert.showAndWait();
		selectend=true;
	}
	public void EnterFlowDialog() {
		TextInputDialog InFlow = new TextInputDialog();
		InFlow.setTitle("Edge Information");
		InFlow.setHeaderText("Enter the init flow:");
		InFlow.setContentText(null);
		Optional<String> result = InFlow.showAndWait();
		if (result.isPresent()) {
			if (result.get().isBlank()) flow =0;
			else flow = Double.parseDouble(result.get());
		}
		else flow = 0;
	}
	@SuppressWarnings("unchecked")
	public Graph clone() {
		Graph copy = new Graph();
		copy.Nodes = (ArrayList<Node>) this.Nodes.clone();
		copy.Edges = (ArrayList<Edge>) this.Edges.clone();
		copy.start = this.start;
		copy.end   = this.end;
		copy.flow  = this.flow;
		return copy;
	}
	
}
