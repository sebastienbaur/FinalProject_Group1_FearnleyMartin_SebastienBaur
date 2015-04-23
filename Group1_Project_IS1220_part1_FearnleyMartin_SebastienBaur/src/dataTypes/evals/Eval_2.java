package dataTypes.evals;
import java.io.*;
import java.util.*;

import treeImplementation.*;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.NotAnExistingFileException;
import dataTypes.VirtualDisk;


public class Eval_2 {

	/**
	 * @param args
	 * @throws NotInTreeException 
	 * @throws NoAvailableSpaceException 
	 * @throws NotADirectoryException 
	 * @throws ParentException 
	 * @throws ImpossibleDisplacementException 
	 * @throws NotAnExistingFileException 
	 * @throws AlreadyExistingComputerFileException 
	 */
	public static void main(String[] args) throws NotInTreeException, NoAvailableSpaceException, NotADirectoryException, ParentException, ImpossibleDisplacementException, NotAnExistingFileException {
		//--------------------------------------------------------------------------------
		// CREATION OF A VIRTUAL DISK VD1, DISPLAY FREE SPACE AND INITIAL NODES
		//--------------------------------------------------------------------------------

		VirtualDisk vd = VirtualDisk.createVirtualDisk("vd2", "virtual disks/vd2.ser",1000);
		System.out.println("Free space: " +vd.queryFreeSpace());
		System.out.println("List of nodes: " +vd.getTree().getNodeList());



		//--------------------------------------------------------------------------------
		// IMPORT THE FILE STRUCTURE OF LEVEL 1 (THAT LIES IN THE SAME FOLDER AS THE ONE WHERE YOU STORED SRC ON YOUR COMPUTER)
		//--------------------------------------------------------------------------------
		// importing the files
		try {
			vd.importFileStructure("eval/Host/level 1", "Home");
		} catch (NoAvailableSpaceException e) {
			e.printStackTrace();
		}

		//checking if import has worked by checking the file and folders in graph as well as the available space
		System.out.println("List of nodes: " + vd.getTree().getNodeList());
		System.out.println("List of edges: " + vd.getTree().getEdgeList());
		System.out.println("Free space: " +vd.queryFreeSpace());

		//test "getListOfPredecessors" and "contains" and "getPath"
		System.out.println("Predecessors of test text: " +vd.getTree().getListOfPredecessors(vd.getNodeFromPath("Home/level 1/level 2/test text.txt")));
		System.out.println("vd contains test text: " + vd.getTree().contains(vd.getNodeFromPath("Home/level 1/level 2/test text.txt")));
		System.out.println("filepath of test text: " + vd.getPath(vd.getNodeFromPath("Home/level 1/level 2/test text.txt")));



		//--------------------------------------------------------------------------------
		// TEST OF MOVE FUNCTION
		//--------------------------------------------------------------------------------
		//moves yahoo.txt from level 2 bis to level 2
		try{
			vd.move("Home/level 1/level 2 bis/yahoo.txt", "Home/level 1/level 2");
		} catch (NotADirectoryException e) {
			e.printStackTrace();
		}



		//--------------------------------------------------------------------------------
		// TEST OF RENAME()
		//--------------------------------------------------------------------------------
		vd.rename("Home/level 1/level 2/test text.txt", "test text renamed.txt");



		//--------------------------------------------------------------------------------
		// TEST SEARCH, GETALLSUCCESSORS, SEARCH INSIDE SPECIFIC DIRECTORY
		//--------------------------------------------------------------------------------
		System.out.println("result of search(level 1) : " + vd.search("level 1")); 
		System.out.println("result of getAllSuccessors(Home/level1) :" + vd.getAllSuccessors("Home/level 1"));
		System.out.println("result of search(test text renamed.txt)" + vd.search("test text renamed.txt","Home/level 1"));



		//--------------------------------------------------------------------------------
		// TEST OF EXPORT
		//--------------------------------------------------------------------------------
		//allows us to check if move function has worked, moving test should contain the hierarchy with yahoo.txt in level 2 bis

		vd.exportDirectory("eval/Host/moving test", "Home/level 1");
		vd.saveVirtualDisk();



		//--------------------------------------------------------------------------------
		// TEST OF DELETEALL()
		//--------------------------------------------------------------------------------
		System.out.println("Content before removing : " + vd.getTree().getNodeList());
		vd.deleteAll("Home/level 1/level 2");
		System.out.println("Content after removing : " + vd.getTree().getNodeList());


	}
}


