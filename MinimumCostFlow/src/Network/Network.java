package Network;

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

public class Network{
	public ArrayList<Node> Nodes = new ArrayList<Node>();
	public ArrayList<Edge> Edges = new ArrayList<Edge>();
	public StackPane selectNode;
	public AnchorPane GraphPane;
	public int nnode=0;
	public boolean node=false,edge=false,selectstart=false,selectend=false,save=false;
	public Node start,end;
	public int flow;
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
						   createaddEdge(newEdge);
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
	public Network(AnchorPane graphPane) {
		super();
		this.GraphPane = graphPane;
	}
	public Network() {
		super();
	}
	public void createaddNode(Node newnode) {
		newnode.circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
		GraphPane.getChildren().add(newnode.circle);
		Nodes.add(newnode);
		nnode++;
	}
	public void createaddEdge(Edge newEdge) {
		GraphPane.getChildren().removeAll(newEdge.source.circle,newEdge.target.circle);
		GraphPane.getChildren().addAll(newEdge.source.circle,newEdge.target.circle,newEdge.arrow,newEdge.EdgeLabel);
		Edges.add(newEdge);
		Edges.add(newEdge.ResidualEdge);
	}
	public void addNode(Node newnode) {
		Nodes.add(newnode);
	}
	public void addEdge(Edge newedge) {
		Edges.add(newedge);
	}
	public void clear() {
		Nodes.clear();
		Edges.clear();
		selectNode =null;
		nnode=0;
		node=false;
		edge=false;
	}
	public void remove() {
		//
	}
	public void view() {
		GraphPane.getChildren().clear();
		for (int i=0;i<Edges.size();++i) 
		  if (Edges.get(i).cost >= 0) {
			 Edge tmp = Edges.get(i);
			 tmp.EdgeLabel = tmp.ChangeLabel();
			GraphPane.getChildren().addAll(Edges.get(i).arrow,Edges.get(i).EdgeLabel);
		  }
		for (int i=0;i<Nodes.size();++i) GraphPane.getChildren().add(Nodes.get(i).circle);
	}
	public void viewResidual() {
		GraphPane.getChildren().clear();
		for (int i=0;i<Edges.size();++i) 
		if (Edges.get(i).isExist()>0) {
			//GraphPane.getChildren().add(Edges.get(i).line);
     		GraphPane.getChildren().add(Edges.get(i).arrow);
     		Edges.get(i).EdgeLabel.setText(Edges.get(i).ResidualText());
     		Edges.get(i).EdgeLabel.setTextFill(Color.RED);
			GraphPane.getChildren().add(Edges.get(i).EdgeLabel);
		}
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
			else flow = Integer.parseInt(result.get());
		}
		else flow = 0;
	}
	public Network cloneforview() {
		Network copy = new Network();
		copy.Nodes = new ArrayList<Node>();
		for (int i=0;i<Nodes.size();++i) copy.Nodes.add(Nodes.get(i).cloneforview());
		copy.Edges = new ArrayList<Edge>();
		for (int i=0;i<Edges.size();++i) copy.Edges.add(Edges.get(i).cloneforview());
		//copy.start = start;
		//copy.end   = end;
		//copy.flow  = flow;
		copy.GraphPane = GraphPane;
		return copy;
	}
	
}
