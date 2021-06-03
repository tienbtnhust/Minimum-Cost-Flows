package Network;

import javafx.scene.control.TextArea;

public class Step {
	public Network graphstep;
	public String AlgorithmLabel;
	public boolean isResidual = false;
	public Step(Network graphstep, String algorithmLabel) {
		super();
		this.graphstep = graphstep;
		AlgorithmLabel = algorithmLabel;
	}
	public Step(Network graphstep,boolean isResidual) {
		super();
		this.graphstep = graphstep;
		this.isResidual=isResidual;
	}

	public Step(Network graphstep, String algorithmLabel, boolean isResidual) {
		super();
		this.graphstep = graphstep;
		AlgorithmLabel = algorithmLabel;
		this.isResidual = isResidual;
	}
	public Step() {
		super();
	}
	public void display() {
		if (!isResidual) graphstep.view();
		else graphstep.viewResidual();
		
	}
	public void display(TextArea textarea) {
		if (!isResidual) graphstep.view();
		else graphstep.viewResidual();
		textarea.appendText(AlgorithmLabel);
	}
}
