package dataTypes.evals;
import java.io.*;
import java.util.*;

import treeImplementation.*;
import dataTypes.Directory;
import dataTypes.Fichier;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.NotAnExistingFileException;
import dataTypes.VirtualDisk;


public class Eval_3 {

	/**
	 * @param args
	 * @throws NotInTreeException 
	 * @throws NoAvailableSpaceException 
	 * @throws NotADirectoryException 
	 * @throws ParentException 
	 * @throws ImpossibleDeplacementException 
	 * @throws NotAnExistingFileException 
	 */
	public static void main(String[] args) throws NotInTreeException, NoAvailableSpaceException, NotADirectoryException, ParentException, ImpossibleDeplacementException, NotAnExistingFileException {


		//--------------------------------------------------------------------------------
		// CREATION OF A VIRTUAL DISK VD1, DISPLAY FREE SPACE AND INITIAL NODES
		//--------------------------------------------------------------------------------


		VirtualDisk vd = VirtualDisk.createVirtualDisk("vd3", "vd3.ser",1000);
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
		// TEST OF EXPORT
		//--------------------------------------------------------------------------------
		//allows us to check if move function has worked, moving test should contain the hierarchy with yahoo.txt in level 2 bis
		vd.exportDirectory("eval/Host/moving test", "Home/level 1");

		//--------------------------------------------------------------------------------
		// FURTHER TESTS OF IMPORT/EXPORT
		//--------------------------------------------------------------------------------

		// adding of a directory level 3 in directory level 2
		Directory d = new Directory("level 3");
		vd.addDirectory("Home/level 1/level 2", d);
		System.out.println(vd.getTree().getNodeList());
		vd.exportDirectory("eval/Host/moving test/level 1/level 2", "Home/level 1/level 2/level 3");

		// importing a file from the directory and adding it to another place in the directory (level 3)
		Fichier f = new Fichier("test text.txt");
		f.importFile("eval/Host/level 1/level 2/test text.txt");
		vd.addFile("Home/level 1/level 2/level 3", f);
		System.out.println(vd.getTree().getNodeList());

		vd.exportFile("eval/Host/moving test/level 1/test text.txt", "Home/level 1/level 2/level 3/test text.txt");
	}





}