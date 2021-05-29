package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Algorithms.CycleCancelling;
import Algorithms.MinimumMeanCycleCancelling;
import Graph.Graph;
import Graph.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class GraphCanvasController implements Initializable{

	@FXML
	private Button playable,NodeButton,EdgeButton,SaveButton,ResetButton;
	@FXML
	private AnchorPane GraphPane;
	@FXML
	private ChoiceBox<Object> AlgorithmsBox;
	public Graph graph = new Graph();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ObservableList<Object> availableChoices = FXCollections.observableArrayList(new CycleCancelling(),new Separator(),new MinimumMeanCycleCancelling());
		AlgorithmsBox.setItems(availableChoices);
		graph.GraphPane = GraphPane;
	}
	// action click New Node
	// sang che do tao dinh
	@FXML
	public void CreateNode(ActionEvent e) {
		graph.node=true;
		graph.edge=false;
	}
	// action click New Edge
	// sang che do tao canh
	@FXML
	public void CreateEdge(ActionEvent e) {
		if (graph.Nodes.size() < 2) {
			 Alert alert = new Alert(AlertType.INFORMATION);
		     alert.setTitle("Warning!");
		     alert.setHeaderText(null);
		     alert.setContentText("Need more than 1 node to create edge!");
		     alert.showAndWait();
		     return;
		}
		graph.node = false;
		graph.edge = true;
	}
	// action click Save Graph
	// tat tao do thi __ Chon dinh dau,dinh cuoi,luong can thong qua
	@FXML
	public void SaveGraph(ActionEvent e) {
		graph.node= false;
		graph.edge = false;
		graph.unselect();
		graph.SelectStartDialog();
	}
	// click left mouse to create vertex
	@FXML
	public void handle(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY) return;
		if (graph.node) {
			if (graph.Nodes.size() == 15) {
				Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Warning!");
			    alert.setHeaderText(null);
			    alert.setContentText("Too much nodes!");
			    alert.showAndWait();
			    return;
			}
			Node newnode = new Node(graph.nnode+1,e.getX(),e.getY());
			graph.addNode(newnode);
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
		graph.clear();
		GraphPane.getChildren().clear();
	}
}
