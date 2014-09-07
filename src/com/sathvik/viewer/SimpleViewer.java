package com.sathvik.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import y.base.Edge;
import y.base.Node;
import y.layout.Layouter;
import y.layout.PartitionLayouter;
import y.layout.PortConstraintKeys;
import y.layout.circular.CircularLayouter;
import y.util.DataProviderAdapter;
import y.view.Graph2D;
import y.view.Graph2DLayoutExecutor;
import y.view.Graph2DView;

import com.google.common.collect.HashMultimap;
import com.sathvik.models.Resource;
import com.sathvik.utils.Utils;

/**
 * @author sathvik, sathvikmail@gmail.com
 * 
 *         Using YFiles to graph the parent child relationship in network.
 *
 */

public class SimpleViewer {
	JFrame frame;
	/** The yFiles view component that displays (and holds) the graph. */
	Graph2DView view;
	/** The yFiles graph type. */
	Graph2D graph;

	public SimpleViewer(Dimension size, String title) {
		view = createGraph2DView();
		graph = view.getGraph2D();
		frame = createApplicationFrame(size, title, view);
	}

	public SimpleViewer() {
		this(new Dimension(400, 300), "");
		frame.setTitle(getClass().getName());
	}

	private Graph2DView createGraph2DView() {
		Graph2DView view = new Graph2DView();
		view.setAntialiasedPainting(true);
		return view;
	}

	/** Creates a JFrame that will show the demo graph. */
	private JFrame createApplicationFrame(Dimension size, String title,
			JComponent view) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(size);
		// Add the given view to the panel.
		panel.add(view, BorderLayout.CENTER);
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getRootPane().setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		return frame;
	}

	private Layouter createLayouter() {
		
		 graph.addDataProvider(PortConstraintKeys.SOURCE_GROUPID_KEY, new DataProviderAdapter() {
		      public Object get(Object edge) {
		        return ((Edge)edge).source();
		      }
		    });
		 
		CircularLayouter cl = new CircularLayouter();
		cl.setLayoutStyle(CircularLayouter.BCC_ISOLATED);
		cl.setPartitionLayoutStyle(CircularLayouter.PARTITION_LAYOUTSTYLE_CYCLIC);
		cl.setLayoutOrientation(CircularLayouter.PARTITION_LAYOUTSTYLE_DISK);
		//cl.setOrientationLayouter();
		return cl;
		
		
	}

	/** Creates a simple graph structure. */
	public void populateGraph(Graph2D graph,
			HashMultimap<Integer, Resource> nodemap) {

		new Graph2DLayoutExecutor().doLayout(graph, createLayouter());

		
		Utils.print("NODEMAP SIZE::" + nodemap.size());
		// ArrayList<Node> parents = new ArrayList<Node>();
		// ArrayList<Node> children = new ArrayList<Node>();
		// Iterate the map
		int parent_max = 400;
		int parent_min = 300;
		int child_max = 500;
		int child_min = 10;
		for (int parentId : nodemap.keys()) {
			graph.getDefaultNodeRealizer().setFillColor(Color.RED);
			int parent_x = (int) (Math.random() * (parent_max - parent_min) + parent_min);
			int parent_y = (int) (Math.random() * (parent_max - parent_min) + parent_min);
			//Node parent = graph
				//	.createNode(parent_x, parent_y, 50, 30, "PARENT");
			Node parent = graph.createNode();

			Collection<Resource> collections = nodemap.get(parentId);
			for (Resource r : collections) {
				graph.getDefaultNodeRealizer().setFillColor(Color.GREEN);
				int child_x = (int) (Math.random() * (child_max - child_min) + child_min);
				int child_y = (int) (Math.random() * (child_max - child_min) + child_min);

				//Node child = graph.createNode(child_x, child_y,
					//	"" + r.getPostId());
				Node child = graph.createNode();
				graph.createEdge(parent, child);
			}

		}
		
		getView().fitContent();
	    graph.updateViews();
		
	}

	public void show() {
		frame.setVisible(true);
	}

	public Graph2DView getView() {
		return view;
	}

	public Graph2D getGraph() {
		return graph;
	}

	
}
