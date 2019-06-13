import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.JGraph;
//import org.jgraph.JGraph;
//import org.jgrapht.DirectedGraph;
//import org.jgrapht.ext.JGraphModelAdapter;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String [] args) {
        CollectTwitterInfo collect = new
                CollectTwitterInfo("tuliogadelha");

        TwitterUserController users = collect.getUsers();

        collect.run();
        //users.debug();

        //Graph<TwitterUser, DefaultEdge> graph = users.getGraph();
//        m_jgAdapter = new JGraphModelAdapter( g );
//
//        JGraph jgraph = new JGraph( m_jgAdapter );
//
//        adjustDisplaySettings( jgraph );
//        getContentPane(  ).add( jgraph );
//        resize( DEFAULT_SIZE );
//

//        JGraphModelAdapter<String, DefaultEdge> graphModel =
//                new JGraphModelAdapter<String, DefaultEdge>(graph);
//        JGraph jgraph = new JGraph (graphModel);
//        BufferedImage img = jgraph.getImage(Color.WHITE, 5);
    }
}

