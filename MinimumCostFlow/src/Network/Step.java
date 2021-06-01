package Network;

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

	public Step() {
		super();
	}
	public void display() {
		if (!isResidual) graphstep.view();
		else graphstep.viewResidual();
	}
}
