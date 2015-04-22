package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import treeImplementation.ImpossibleDeplacementException;
import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import treeImplementation.ParentException;
import dataTypes.Directory;
import dataTypes.Fichier;
import dataTypes.InvalidVirtualDiskException;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.NotAnExistingFileException;
import dataTypes.VirtualDisk;

public class VirtualDiskTest {
	//specifies the vd the testing is done with
	//when VirtualDisk is changed, the serialization changes which break the juint testing, therefore regenerate the vd with virtualdiskgenerator and update the file path here
	VirtualDisk vdtest = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSaveVirtualDisk()  {
		VirtualDisk vd =  new VirtualDisk("vd", "vd.ser", 100);
		vd.saveVirtualDisk();
		File f = new File("vd.ser");
		assertTrue(f.exists());
	}
	//test à revoir
	@Test
	public void testLoadVirtualDisk() throws NotInTreeException, NotADirectoryException, ParentException, NotAnExistingFileException {
		
//		Recreation of eval/vd1.ser
		VirtualDisk vd = new VirtualDisk(vdtest.getName(), vdtest.getPath(),vdtest.getCapacity());
		vd.getTree().addNode(new Directory("Home"));
		try {
			vd.importFileStructure("eval/Host/level 1", "Home");
		} catch (NoAvailableSpaceException e) {
			e.printStackTrace();
		}
		
//		import of vd1.ser
		VirtualDisk vd2 = vdtest;

//		to test this function, we import vd1 from disk, and we create the exact same virtual disk from java, we can then test their equality.
		assertEquals(vd,vd2);
	}

	@Test
    public void testDeleteAll() throws NotInTreeException, NotADirectoryException, NoAvailableSpaceException, ParentException {
          VirtualDisk vd1 = vdtest;
          vd1.deleteAll("Home/level 1/level 2");
          VirtualDisk vd2 =  new VirtualDisk(vdtest.getName(), vdtest.getPath(),vdtest.getCapacity());
          vd2.getTree().addNode(new Directory("Home"));
          Directory l1 = new Directory("level 1");
          Directory l2bis = new Directory("level 2 bis");
          Fichier f2 = new Fichier("yahoo.txt");
          f2.importFile("eval/Host/level 1/level 2 bis/yahoo.txt");
          vd2.addDirectory("Home", l1);
          vd2.addDirectory("Home/level 1", l2bis);
          vd2.addFile("Home/level 1/level 2 bis", f2);
          assertTrue(vd1.equals(vd2));
    }

	@Test
    public void testAddDirectoryANDAddFile() throws NotADirectoryException, NoAvailableSpaceException, ParentException {
          VirtualDisk vd1 = vdtest;
          Directory d1 = new Directory("hello");
          Fichier f = new Fichier("i'm a file");
          f.importFile("eval/Host/level 1/level 2/test text.txt");
          
          vd1.addDirectory("Home/level 1/level 2", d1);
          vd1.addFile("Home/level 1/level 2/hello", f);
          
          VirtualDisk vd2 = new VirtualDisk(vdtest.getName(), vdtest.getPath(),vdtest.getCapacity());
          vd2.getTree().addNode(new Directory("Home"));
          
          Directory l1 = new Directory("level 1");
          Directory l2 = new Directory("level 2");
          Directory l2bis = new Directory("level 2 bis");
          Fichier f1 = new Fichier("test text.txt");
          Fichier f2 = new Fichier("yahoo.txt");
          f1.importFile("eval/Host/level 1/level 2/test text.txt");
          f2.importFile("eval/Host/level 1/level 2 bis/yahoo.txt");
          vd2.addDirectory("Home", l1);
          vd2.addDirectory("Home/level 1", l2bis);
          vd2.addFile("Home/level 1/level 2 bis", f2);
          vd2.addDirectory("Home/level 1", l2);
          vd2.addFile("Home/level 1/level 2", f1);
          vd2.addDirectory("Home/level 1/level 2", d1);
          vd2.addFile("Home/level 1/level 2/hello", f);
          assertEquals(vd1,vd2);
    }

	@Test
	public void testCopy() throws NotADirectoryException, NotInTreeException, NoAvailableSpaceException, ParentException {
		VirtualDisk vd1 = vdtest;
		VirtualDisk vd2 = vdtest;
		Fichier f = new Fichier("test");
		vd1.addFile("Home/level 1/level 2", f);
		vd2.addFile("Home/level 1/level 2", f);
		vd2.addFile("Home/level 1/level 2 bis", f);
		vd1.copy("Home/level 1/level 2/test", "Home/level 1/level 2 bis");
		assertEquals(vd1,vd2);
	}

	@Test
    public void testimportFileStructure() throws NoAvailableSpaceException, NotInTreeException, NotADirectoryException, ParentException, NotAnExistingFileException {
          VirtualDisk vd1 = vdtest;
          VirtualDisk vd2 = new VirtualDisk(vdtest.getName(), vdtest.getPath(),vdtest.getCapacity());
          vd2.getTree().addNode(new Directory("Home"));
          vd2.importFileStructure("eval/Host/level 1", "Home");
          assertEquals(vd1,vd2);
    }

    @Test
 public void testExportFile() {
       VirtualDisk vd1 = vdtest;
       vd1.exportFile("eval/Host/moving test/test text.txt", "Home/level 1/level 2/test text.txt");
       File f1 = new File("eval/Host/moving test/test text.txt");
       File f2 = new File("eval/Host/level 1/level 2/test text.txt");
       assertTrue(f1.exists());
 }


    @Test
    public void testExportDirectory() throws NotInTreeException {
          VirtualDisk vd1 = vdtest;
          vd1.exportDirectory("eval/Host/moving test", "Home/level 1/level 2/test text.txt");
          File f1 = new File("eval/Host/moving test");
          assertTrue(f1.exists() );
    }


	@Test
	public void testMove() throws NotADirectoryException, NotInTreeException, NoAvailableSpaceException, ParentException, ImpossibleDeplacementException {
		VirtualDisk vd1 = vdtest;
		VirtualDisk vd2 = vdtest;
		Fichier f = new Fichier("test");
		Fichier f2 = new Fichier("testm");
		vd1.addFile("Home/level 1/level 2", f);
		vd2.addFile("Home/level 1/level 2 bis", f2);
		vd1.move("Home/level 1/level 2/test", "Home/level 1/level 2 bis");
		assertEquals(vd1,vd2);
	}

	@Test
	public void testRename() throws NotADirectoryException, NotInTreeException, NoAvailableSpaceException, ParentException {
		VirtualDisk vd2 = vdtest;
		Fichier f = new Fichier("test");
		vd2.addFile("Home/level 1/level 2", f);
		vd2.rename("Home/level 1/level 2/test", "test rename");
		assertEquals("test rename", f.getName());
		
	}

	@Test
	public void testGetPathNode() throws NotADirectoryException, NotInTreeException, NoAvailableSpaceException, ParentException {
		VirtualDisk vd2 = vdtest;
		Fichier f = new Fichier("test");
		vd2.addFile("Home/level 1/level 2", f);
		String path = new String("Home/level 1/level 2/test");
		assertEquals(path,vd2.getPath(f));

	}

	@Test
	public void testGetNodeFromPath() throws NotADirectoryException, NotInTreeException, NoAvailableSpaceException, ParentException {
		VirtualDisk vd2 = vdtest;
		Fichier f = new Fichier("test");
		vd2.addFile("Home/level 1/level 2", f);
		Fichier f2 = (Fichier) vd2.getNodeFromPath("Home/level 1/level 2/test");
		assertEquals(f,f2);
	}

	@Test
	public void testSearchString() throws NotInTreeException {
		List<Node> list= new ArrayList<Node>();
		list.add(new Directory ("level 1"));
		VirtualDisk vd2 = vdtest;
		assertEquals(list, vd2.search("level 1"));
	}

	@Test
	public void testGetAllSuccessors() throws NotInTreeException {
		VirtualDisk vd2 = vdtest;
		List<Node> list= new ArrayList<Node>();
		list.add(new Directory ("level 2"));
		list.add(new Directory ("level 2 bis"));
		list.add(new Fichier ("yahoo.txt"));
		list.add(new Fichier ("test text.txt"));
		list.add(new Directory("level 1"));
		HashSet<Node> h1 = new HashSet<Node>(list);
        HashSet<Node> h2 = new HashSet<Node>(vd2.getAllSuccessors("Home/level 1"));
		assertEquals(h1,h2);
	}

	@Test
	public void testSearchStringString() throws NotInTreeException {
		List<Node> list= new ArrayList<Node>();
		list.add(new Directory ("level 2"));
		VirtualDisk vd2 = vdtest;
		assertEquals(list, vd2.search("level 2","Home/level 1"));
	}

}
