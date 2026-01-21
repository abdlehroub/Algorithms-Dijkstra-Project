package algorithms;

public class Edge implements Comparable<Edge> {
	private double distance;
	private double time;
	private Vertex destination;

	public Edge(double distance, double time, Vertex destination) {
		super();
		this.distance = distance;
		this.destination = destination;
		this.time = time;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public int compareTo(Edge o) {
		return Double.compare(distance, o.distance);
	}

}
