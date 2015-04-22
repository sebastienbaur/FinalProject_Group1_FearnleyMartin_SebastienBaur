package dataTypes.evals;
import java.io.*;
import java.util.*;

import treeImplementation.*;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.NotAnExistingFileException;
import dataTypes.VirtualDisk;


public class Eval_1 {

	/**
	 * @param args
	 * @throws NotInTreeException 
	 * @throws NoAvailableSpaceException 
	 * @throws NotADirectoryException 
	 * @throws ParentException 
	 * @throws NotAnExistingFileException 
	 * @throws AlreadyExistingComputerFileException 
	 */
	public static void main(String[] args) throws NotInTreeException, NoAvailableSpaceException, NotADirectoryException, ParentException, NotAnExistingFileException {


		//--------------------------------------------------------------------------------
		// CREATION OF A VIRTUAL DISK VD1, DISPLAY FREE SPACE AND INITIAL NODES
		//--------------------------------------------------------------------------------
		VirtualDisk vd = VirtualDisk.createVirtualDisk("vd1", "vd1.ser",10000000);
		System.out.println("Free space: " +vd.queryFreeSpace());
		System.out.println("List of nodes: " +vd.getTree().getNodeList());



		//--------------------------------------------------------------------------------
		// IMPORT THE FILE STRUCTURE OF test1 files (contained in eval/Host)
		//--------------------------------------------------------------------------------
		// imports the files into Home directory of the virtual disk
		try {
			vd.importFileStructure("eval/Host/test1 files", "Home");
		} catch (NoAvailableSpaceException e) {
			e.printStackTrace();
		}

		//checking if import has worked by checking the file and folders in graph as well as the available space
		System.out.println("List of nodes: " + vd.getTree().getNodeList());
		System.out.println("List of edges: " + vd.getTree().getEdgeList());
		System.out.println("Free space: " +vd.queryFreeSpace());



		//--------------------------------------------------------------------------------
		// TEST SEARCH
		//--------------------------------------------------------------------------------
		try{
			System.out.println("result of search(test folder) : " + vd.search("test folder")); 
		}catch(NotInTreeException e){
			System.out.println("file not found");
		}
		try{
			System.out.println("result of search(gibberish) : " + vd.search("gibberish")); 
		}catch (NotInTreeException e){
			System.out.println("file not found");
		}




	}
}

