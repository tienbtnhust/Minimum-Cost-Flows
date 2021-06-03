package Algorithms;

import Network.Edge;
import Network.Network;
import Network.Node;

public class MinimumMeanCycleCancelling extends CycleAlgorithm{

	public MinimumMeanCycleCancelling() {
		super();
		this.name="Minimum Mean Cycle Cancelling";
	}
	public Network FindNegativeCycle(Network input) {
		for (int i=0;i<input.Nodes.size();++i) input.Nodes.get(i).index = i;
		double[][] d = new double[50][50];
		Edge[][] p = new Edge[50][50];
		for (int i=0;i<=input.Nodes.size();++i)
			for (int j=0;j<input.Nodes.size();++j) {
				d[i][j]=Double.POSITIVE_INFINITY;
				p[i][j]=null;
			}
		int n = input.Nodes.size();
		d[0][input.start.index] =0;
		for (int i=1;i<=n;++i) {
			for (int j=0;j<input.Edges.size();++j) {
				Edge e = input.Edges.get(j);
				Node u = e.source;
				Node v = e.target;
				if (e.isExist()>0) {
					if (d[i-1][u.index] != Double .POSITIVE_INFINITY) {
						if (d[i][v.index] > d[i-1][u.index]+e.cost) {
							d[i][v.index] = d[i-1][u.index]+e.cost;
							//System.out.println(i+" "+v.index+" "+u.index+" "+v.index);
							p[i][v.index] = e;
						}
					}
				}
			}
		}
		int index = 0,loop=n;
		double mmc = Double.POSITIVE_INFINITY;
		for (int i=0;i<input.Nodes.size();++i) {
			double tmp = Double.NEGATIVE_INFINITY;
			int looptmp = 0;
			for (int k = 0; k<input.Nodes.size();++k)
				if (d[n][i]!= Double.POSITIVE_INFINITY && d[k][i]!=Double.POSITIVE_INFINITY)
				if (tmp < (d[n][i]-d[k][i])/(n-k)) {
					tmp = (d[n][i]-d[k][i])/(n-k);
					looptmp = k;
				}
			if (mmc>tmp && tmp!=Double.NEGATIVE_INFINITY) {
				mmc = tmp;
				index = i;
				loop = looptmp;
			}
		}
		//System.out.println(index+" "+loop+" "+mmc);
		if (mmc>=0) return null;
		else {
			Network cycle = new Network();
			int u = index;
			for (int i = n; i>loop;--i) {
				//System.out.println(i+" "+u);
				cycle.Edges.add(p[i][u]);
				u = p[i][u].source.index;
				cycle.Nodes.add(input.Nodes.get(u));
			}
			cycle.GraphPane = input.GraphPane;
			//cycle.viewResidual();
			return cycle;
		}
	}
	/*public MinimumMeanCycleCancelling(Graph input) {
		super();
		this.name="Minimum Mean Cycle Cancelling";
		this.InGraph = input;
	}*/
}
