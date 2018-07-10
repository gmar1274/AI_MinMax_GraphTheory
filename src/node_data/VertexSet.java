package node_data;

import java.util.Arrays;

/**
 * 
 * @author gabe
 *Vertices array[] = 1,2,3,4,5... represent a  1 - 2 -3 - 4 -5. a linkedlist
 */
public class VertexSet {
	private Node[] nodes;
	
	public VertexSet(char[] arr){
		nodes = new Node[arr.length];
		for (int i =0; i < nodes.length; ++i){
			nodes[i] = new Node(i,arr[i]);
		}
	}
	/*public VertexSet(Node[] nodes){
		this.nodes =nodes;
	}*/
	public Node[] getNodes(){
		return this.nodes;
	}

	public String toString(){
		String s = "";
		for(Node n : nodes){
			s += n.getValue()+", ";//"key: "+n.getName()+" "+n.getValue()+", ";
		}
		if(s.length()>2)s = s.substring(0, s.length()-2);
		return s;// Arrays.toString(nodes);
	}
	public int size(){
		return this.nodes.length;
	}
}
