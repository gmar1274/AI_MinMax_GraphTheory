package node_data;

public  class Node<K,V>{
	private K name;
	private V value;
	public Node(K name,V val){
		this.name = name;
		this.value = val;
	}
	@Override
	public int hashCode(){
		return name.toString().hashCode();
	}
	public V getValue(){
		return this.value;
	}
	public K getName(){
		return this.name;
	}
	public String toString(){
		return this.name.toString();
	}
}