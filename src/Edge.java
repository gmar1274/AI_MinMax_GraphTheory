

public  class Edge<Node>{
	private Node one,two;
	
	public Edge(Node one2, Node two2){
		this.one = one2;
		this.two=two2;
	}
	public Edge() {
		// TODO Auto-generated constructor stub
	}
	public Node getNodeOne(){
		return this.one;
	}
	public Node getNodeTwo(){
		return this.two;
	}
	@Override 
	public int hashCode(){
		return (this.one.toString()+this.two.toString()).toString().hashCode();
	}
	public String toString(){
		return this.one.toString() +"---"+this.two.toString();
	}
}