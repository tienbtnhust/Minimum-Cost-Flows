package Algorithms;

import java.util.ArrayList;
import java.util.LinkedList;

import Network.Network;
import Network.Node;
import Network.Step;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class CycleAlgorithm implements Algorithm{
	String name;
	ArrayList<Step> ListSteps = new ArrayList<Step>();
	public boolean pausable = false;
	Timeline timeline;
	Network usage;
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public void RunAlgorithm(Network input) {
		usage = input;
		ListSteps.clear();
		FindFeasibleFlow(input);
		ListSteps.add(new Step(input.cloneforview(),false));
		ListSteps.add(new Step(input.cloneforview(),true));
		while (FindNegativeCycle(input)!=null) {
			Network cycle = FindNegativeCycle(input);
			ListSteps.add(new Step(cycle.cloneforview(),true));
			UpdateFlow(cycle);
			ListSteps.add(new Step(input.cloneforview(),false));
			ListSteps.add(new Step(input.cloneforview(),true));
		}
		ListSteps.add(new Step(input.cloneforview(),false));
		timeline = new Timeline();
		for (int i=0;i<ListSteps.size();++i) {
			Duration duration = Duration.seconds(2*i);
			Step node = ListSteps.get(i);
			KeyFrame keyFrame = new KeyFrame(duration,evt-> {
                node.display();
            });
            timeline.getKeyFrames().add(keyFrame);
		}
	}	
	public void display() {
		timeline.play();
		pausable = false;
	}
	public void pause() {
		timeline.pause();
		pausable = true;
	}
	public void reset() {
		timeline.stop();
		usage.reset();
		usage.view();
	}
	public void CreateResidualNetwork(Network input) {
		input.viewResidual();
		//for (int i=0;i<residual.Edges.size();++i) residual.Edges.get(i).create
	}
	public void FindFeasibleFlow(Network input) {
		LinkedList<Node> queue = new LinkedList<Node>();
		int initflow = input.flow;
		while (initflow > 0) {
			queue.clear();
			for (int i=0;i<input.Nodes.size();++i) {
				input.Nodes.get(i).preEdge = null;
				input.Nodes.get(i).visited = false;
				input.Nodes.get(i).flow    = 0;
			}
			input.start.visited = true;
			input.start.flow    = initflow;
			queue.add(input.start);
			while (!queue.isEmpty()) {
				Node u = queue.poll();
				for (int i=0;i<u.OutEdges.size();++i)
					if (!u.OutEdges.get(i).target.visited && u.OutEdges.get(i).capacity > u.OutEdges.get(i).flow) {
						Node v =  u.OutEdges.get(i).target;
						v.visited = true;
						v.flow    = Math.min(u.flow,u.OutEdges.get(i).capacity-u.OutEdges.get(i).flow);
						v.preEdge  = u.OutEdges.get(i);
						if (input.end.visited) break;
						queue.add(v);
					}
			    if (input.end.visited) break;
			}
			if (!input.end.visited) break;
			else {
				initflow -= input.end.flow;
				Node tmp = input.end;
				while (tmp.preEdge != null) {
					tmp.preEdge.changeFlow(input.end.flow);
					tmp = tmp.preEdge.source;
				}
			}
		}
		if (initflow > 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Notification");
	        alert.setHeaderText("Results:");
	        alert.setContentText("Can not pass throw the init flow!");
	        alert.showAndWait();
		} else input.viewResidual();
		//output.view();
	}
	public Network FindNegativeCycle(Network input) {
		Network cycle =new Network();
		Node ok = null;
		for (int i=0;i<input.Nodes.size();++i) {
			input.Nodes.get(i).cost  = 0;
			input.Nodes.get(i).preEdge = null;
		}
		for (int i = 0; i < input.Nodes.size(); ++i) {
	        ok = null;
	        for (int j =0; j < input.Edges.size();++j) 
	        if (input.Edges.get(j).isExist()>0){
	        	Node u  = input.Edges.get(j).source;
	        	Node v  = input.Edges.get(j).target;
	        	//System.out.println(u.name+" "+v.name);
	            if (u.cost + input.Edges.get(j).cost < v.cost) {
	                v.cost = u.cost + input.Edges.get(j).cost;
	                v.preEdge = input.Edges.get(j);
	                ok = v;
	            }
	        }
	    }
		if (ok == null) return null;
		else {
			for (int i=0;i<input.Nodes.size();++i)
				ok  = ok.preEdge.source;
			for  (Node v = ok;;v=v.preEdge.source) {
				if (v!=ok || cycle.Nodes.size()==0) {
					cycle.Nodes.add(v);
					cycle.Edges.add(v.preEdge);
				}
				if (v==ok && cycle.Nodes.size()>=2) break;
			}
			cycle.GraphPane = input.GraphPane;
			cycle.viewResidual();
			return cycle;
		}
	}
	public void UpdateFlow(Network cycle) {
		int minflow = Integer.MAX_VALUE;
		for (int i=0;i<cycle.Edges.size();++i)
			minflow = Math.min(minflow, cycle.Edges.get(i).isExist());
		for (int i=0;i<cycle.Edges.size();++i)
			cycle.Edges.get(i).changeFlow(minflow);
	}
}
