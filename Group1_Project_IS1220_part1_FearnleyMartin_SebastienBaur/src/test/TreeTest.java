package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import treeImplementation.*;
import dataTypes.Directory;
import dataTypes.Fichier;



public class TreeTest {

       Tree t1 = new Tree();
       Tree t2 = new Tree();
       Directory d1 = new Directory("d1");
       Directory d2 = new Directory("d2");
       Directory d3 = new Directory("d3");
       Directory d4 = new Directory("d4");
       Directory d5 = new Directory("d5");
       Directory d6 = new Directory("d6");
       Fichier f1 = new Fichier("f1");
       Fichier f2 = new Fichier("f2");
       Fichier f3 = new Fichier("f3");
       Fichier f4 = new Fichier("f4");
       Fichier f5 = new Fichier("f5");
       Fichier f6 = new Fichier("f6");
       Edge e1 = new Edge(d1,d2);
       Edge e2 = new Edge(d1,f1);
       Edge e3 = new Edge(d1,d3);
       Edge e4 = new Edge(d3,d5);
       Edge e5 = new Edge(d5,f3);
       Edge e6 = new Edge(d5,f4);
       Edge e7 = new Edge(d2,f2);
       Edge e8 = new Edge(d2,d4);
       Edge e9 = new Edge(d4,d6);
       Edge e10 = new Edge(d4,f6);
       Edge e11 = new Edge(d4,f5);

       
       
       
       @Before
       public void setUp() throws Exception {
       }

       @After
       public void tearDown() throws Exception {
       }


             
             

       @Test
       public void testDeleteAll() throws ParentException {     
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             
             t2.addNode(d1); t2.addNode(f1);   t2.addNode(d3);     t2.addNode(d5);       t2.addNode(f3);     t2.addNode(f4);            
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);       t1.addEdge(e10);t1.addEdge(e11);               
                    
                    t2.addEdge(e2);     t2.addEdge(e3);     t2.addEdge(e4);     t2.addEdge(e5);       t2.addEdge(e6);            
                    
                    t1.deleteAll(d2);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertEquals(t1,t2);       
       }

       
       @Test
       public void testDeleteLeaf() throws ParentException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             
             t2.addNode(d1);     t2.addNode(d2);     t2.addNode(d3);     t2.addNode(d4);       t2.addNode(d5);     t2.addNode(d6);     t2.addNode(f1);
             t2.addNode(f2);     t2.addNode(f3);     t2.addNode(f4);     t2.addNode(f5);
             
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
                    
                    t2.addEdge(e1);     t2.addEdge(e2);     t2.addEdge(e3);     t2.addEdge(e4);       t2.addEdge(e5);     t2.addEdge(e6);
                    t2.addEdge(e7);     t2.addEdge(e8);     t2.addEdge(e9);     t2.addEdge(e11);
                    
                    t1.deleteLeaf(f6);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertEquals(t1,t2);
       }

       
       @Test
       public void testGetSuccessors() throws ParentException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
                    
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             List<Node> nodes = new ArrayList<Node>();
             nodes.add(f6); nodes.add(f5); nodes.add(d6);
             // the order is unimportant so that we transform the lists into sets to compare the two
             HashSet<Node> nodesSet = new HashSet<Node>(nodes);
             HashSet<Node> expected = new HashSet<Node>(t1.getSuccessors(d4));
             assertEquals(expected,nodesSet);
       }
       

       @Test
       public void testGetPredecessor() throws NotInTreeException, ParentException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             Directory d = new Directory("d4");
             ((Node) d).id = t1.getPredecessor(f6).get(0).id;
             assertEquals(d,t1.getPredecessor(f6).get(0));
       }

       
       @Test
       public void testGetListOfPredecessors() throws ParentException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             List<Node> nodes = new ArrayList<Node>();
             nodes.add(d4);
             nodes.add(d2);
             nodes.add(d1);
             assertEquals(nodes, t1.getListOfPredecessors(f6));
       }

       
       @Test
       public void testContains() throws ParentException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             // remark : it doesn't work for d1 since it has no predecessor
             // this problem is avoided in the virtual disk since every graph has a base folder called "Home"
             // that way, the graph of the VD doesn't contain the Home folder the way we defined it, but it
             // contains all its sons
             assertTrue(t1.contains(d2));
       }

       
       @Test
       public void testGetEdgeFromNodes() throws ParentException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             Edge e = new Edge(d3,d5);
             e.setId(e4.getId());
             assertEquals(e, e4);
       }

       
       @Test
       public void testContainsEdge() throws ParentException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertTrue(t1.containsEdge(d1,d2));
       }

       
       @Test
       public void testMove() throws ParentException, ImpossibleDeplacementException {
             t1.addNode(d1);     t1.addNode(d2);     t1.addNode(d3);     t1.addNode(d4);       t1.addNode(d5);     t1.addNode(d6);     t1.addNode(f1);
             t1.addNode(f2);     t1.addNode(f3);     t1.addNode(f4);     t1.addNode(f5);       t1.addNode(f6);
             
             t2.addNode(d1);     t2.addNode(d2);     t2.addNode(d3);     t2.addNode(d4);       t2.addNode(d5);     t2.addNode(d6);     t2.addNode(f1);
             t2.addNode(f2);     t2.addNode(f3);     t2.addNode(f4);     t2.addNode(f5);       t2.addNode(f6); t2.addNode(d4); t2.addNode(d6);
             t2.addNode(f6);     t2.addNode(f5);
             try {
                    t1.addEdge(e1);     t1.addEdge(e2);     t1.addEdge(e3);     t1.addEdge(e4);       t1.addEdge(e5);     t1.addEdge(e6);
                    t1.addEdge(e7);     t1.addEdge(e8);     t1.addEdge(e9);     t1.addEdge(e10); t1.addEdge(e11);
                    
                    t2.addEdge(e1);     t2.addEdge(e2);     t2.addEdge(e3);     t2.addEdge(e4);       t2.addEdge(e5);     t2.addEdge(e6);
                    t2.addEdge(e7);     t2.addEdge(e9);     t2.addEdge(e10); t2.addEdge(e11);
                    t2.addEdge(new Edge(d5,d4)); 
                    
                    t1.move(d4, d5);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertEquals(t1,t2);
       }

}

