package node_data;
import java.util.ArrayList;

public class NGonTable {

	private VertexSet vertices;
	public NGonTable(VertexSet v){
		this.vertices  = v;
	}
	

	public VertexSet getVertices(){
		return this.vertices;
	}
	public String toString(){
		return this.vertices.toString();
	}
	
}
