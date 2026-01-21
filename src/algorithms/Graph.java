package algorithms;

public class Graph {
	private MyList<Vertex> vertices;

	
	public Graph(int size) {
		vertices = new MyList<Vertex>(size);
	}

	public MyList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(MyList<Vertex> vertices) {
		this.vertices = vertices;
	}

	
	
	
	
	
}
