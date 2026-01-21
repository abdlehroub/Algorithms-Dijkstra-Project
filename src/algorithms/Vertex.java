package algorithms;

public class Vertex implements Comparable<Vertex> {
	private String id;
	private LinkedList<Edge> adjacents;

	private double dv = Double.MAX_VALUE;
	private Vertex pv;
	private boolean isKnown;

	public Vertex(String id) {
		this.id = id;
		adjacents = new LinkedList<Edge>();

	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setAdjacents(LinkedList<Edge> adjacents) {
		this.adjacents = adjacents;
	}

	public LinkedList<Edge> getAdjacents() {
		return this.adjacents;
	}

	public void addEdge(Vertex to, double distance, double time) {
		adjacents.addFirst(new Edge(distance, time, to));
	}

	public double getDv() {
		return dv;
	}

	public void setDv(double dv) {
		this.dv = dv;
	}

	public Vertex getPv() {
		return pv;
	}

	public void setPv(Vertex pv) {
		this.pv = pv;
	}

	public boolean isKnown() {
		return isKnown;
	}

	public void setKnown(boolean isKnown) {
		this.isKnown = isKnown;
	}

	public int compareTo(Vertex v) {
		return Double.compare(this.dv, v.dv);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vertex)
			return ((Vertex) obj).id.equals(id);
		return super.equals(obj);
	}
}
