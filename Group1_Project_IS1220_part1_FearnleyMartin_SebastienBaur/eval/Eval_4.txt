package dataTypes.evals;
import java.io.*;
import java.util.*;

import treeImplementation.*;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.VirtualDisk;


public class Eval_4 {

	/**
	 * @param args
	 * @throws NotInTreeException 
	 * @throws NoAvailableSpaceException 
	 * @throws NotADirectoryException 
	 */
	public static void main(String[] args) throws NotInTreeException, NoAvailableSpaceException, NotADirectoryException {

		//--------------------------------------------------------------------------------
		// CREATION OF A VIRTUAL DISK VD, DISPLAY FREE SPACE AND INITIAL NODES AND EDGES, TEST OF SUBGRAPH
		//--------------------------------------------------------------------------------
		VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
		System.out.println("Free space: " +vd.queryFreeSpace());
		System.out.println("List of nodes: " +vd.getTree().getNodeList());
		System.out.println("List of edges: " +vd.getTree().getEdgeList());
		System.out.println(vd.getSubTree("Home/level 1"));	
	}

}
