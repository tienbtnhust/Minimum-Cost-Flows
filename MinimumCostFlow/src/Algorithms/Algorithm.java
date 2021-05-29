package Algorithms;

import java.util.ArrayList;

import Graph.Graph;
import Graph.Step;

public class Algorithm implements Runnable{
	String name;
	ArrayList<Step> ListSteps = new ArrayList<Step>();
	//Graph InGraph;
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public void RunAlgorithm(Graph input) {
		// TODO Auto-generated method stub
		
	}	
	public void CreateResidualNetwork(Graph input) {
		Graph residual = input.clone();
		//for (int i=0;i<residual.Edges.size();++i) residual.Edges.get(i).create
	}
	/*public void reset() {
		InGraph.view();
	}*/
}
