package dataTypes;

import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import treeImplementation.ParentException;

public class VirtualDiskGenerator {
	public static void main(String[] args) throws NotInTreeException, NoAvailableSpaceException, NotADirectoryException, InvalidVirtualDiskException, ParentException, NotAnExistingFileException {

		//makes vd, adds level 1 and saves
		VirtualDisk vd = VirtualDisk.createVirtualDisk("vdlevel1", "eval/vdlevel1.ser",100000000);

		try {
			vd.importFileStructure("eval/Host/level 1", "Home");
//			System.out.println(Node.uniqueId);
		} catch (NoAvailableSpaceException e) {
			e.printStackTrace();
		}
		
		vd.saveVirtualDisk();
	}
}
