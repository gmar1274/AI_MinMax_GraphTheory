import java.util.ArrayList;

public  class OneFactor<Node>{
	private Edge<Node> edge;
	public OneFactor(Node one, Node two){
		this.edge = new Edge<Node>(one,two);
	}
	public Node getNodeOne(){
		return this.edge.getNodeOne();
	}
	public Node getNodeTwo(){
		return this.edge.getNodeTwo();
	}
}
