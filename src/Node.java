package com.mxgraph.examples.swing;

public class Node {

    int order;
    int distance;
    boolean leaf;

    public Node(int order, int distance, boolean leaf){
        this.order=order;
        this.distance = distance;
        this.leaf = leaf;
    }
}
