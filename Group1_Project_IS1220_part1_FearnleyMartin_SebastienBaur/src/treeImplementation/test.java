package treeImplementation;

import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.NotAnExistingFileException;
import dataTypes.VirtualDisk;

public class test {

	public static void main(String[] args) throws NotInTreeException, NoAvailableSpaceException, NotADirectoryException, ParentException, NotAnExistingFileException {
		// TODO Auto-generated method stub

		VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
		System.out.println(vd.getTree());
		vd.importFileStructure("eval/Host", "Home/level 1");
		System.out.println(vd.getTree());
		vd.exportDirectory("eval/HBOU", "Home");

	}
}