package Graph;

import javafx.scene.layout.AnchorPane;

public class Step {
	public Graph graphstep;
	public String AlgorithmLabel;
	public Step(Graph graphstep, String algorithmLabel) {
		super();
		this.graphstep = graphstep;
		AlgorithmLabel = algorithmLabel;
	}
	public Step() {
		super();
	}
	public void display(AnchorPane display) {
		graphstep.view();
	}
}
