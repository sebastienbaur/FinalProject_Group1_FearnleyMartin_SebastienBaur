package treeImplementation;

import dataTypes.*;


public class TreeEval {
	// for testing the graph type has been correctly implemented
	public static void main(String[] args) throws NotInTreeException, ParentException {

		//		VirtualDisk vd = new VirtualDisk("vd","test_path",100000);
		//		
		//		
		//		Fichier file1 = new Fichier("test1","txt",500);
		//		Fichier file2 = new Fichier("test2","txt",400);
		//		Directory dir1 = new Directory("dir1");
		//		Edge e1 = new Edge(dir1,file1);
		//		Edge e2 = new Edge(dir1,file2);
		//		
		//		vd.getGraph().addNode(file1);
		//		vd.getGraph().addNode(file2);
		//		vd.getGraph().addNode(dir1);
		//		vd.getGraph().addEdge(e1);
		//		vd.getGraph().addEdge(e2);
		//		
		//		System.out.println(vd.getGraph().getNodeList());
		//		System.out.println(vd.getGraph().getEdgeList());
		//		System.out.println(vd.getGraph().listContentsOfDirectory(dir1));


		Tree g = new Tree();
		Directory d1 = new Directory("level1");
		Directory d2 = new Directory("level21");
		Directory d3 = new Directory("level22");
		Directory d4 = new Directory("level31");
		Directory d5 = new Directory("level32");
		Directory d6 = new Directory("level33");
		Directory d7 = new Directory("level41");
		Directory d8 = new Directory("level42");
		g.addNode(d1);
		g.addNode(d2);
		g.addNode(d3);
		g.addNode(d4);
		g.addNode(d5);
		g.addNode(d6);
		g.addNode(d7);
		g.addNode(d8);
		g.addEdge(new Edge(d1,d2));
		g.addEdge(new Edge(d1,d3));
		g.addEdge(new Edge(d2,d4));
		g.addEdge(new Edge(d2,d5));
		g.addEdge(new Edge(d5,d7));
		g.addEdge(new Edge(d5,d8));
		g.addEdge(new Edge(d3,d6));
		g.deleteAll(d2);
		System.out.println(g.getNodeList());
		System.out.println(g.getEdgeList());

	}

}
