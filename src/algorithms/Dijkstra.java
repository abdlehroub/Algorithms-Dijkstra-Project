package algorithms;

public class Dijkstra {
	public Dijkstra() {

	}

	private void solve(Graph g, Vertex src, Vertex dest, boolean isTime) {
		g.getVertices().forEach(e -> {
			e.setDv(Double.MAX_VALUE);
			e.setPv(null);
			e.setKnown(false);
		});
		MinHeap<Vertex> heap = new MinHeap<Vertex>(g.getVertices().size());
		src.setDv(0);
		heap.insert(src);
		while (!heap.isEmpty()) {
			Vertex curr = heap.extractMin();
			if (curr.isKnown())
				continue;
			curr.setKnown(true);
			curr.getAdjacents().forEach(e -> {
				Vertex adj = e.getDestination();
				if (!adj.isKnown()) {
					double w = isTime ? e.getTime() : e.getDistance();
					if (curr.getDv() + w < adj.getDv()) {
						adj.setDv(curr.getDv() + w);
						adj.setPv(curr);
						heap.insert(adj);
					}
				}
			});
			if (curr == dest)
				break;
		}
	}

	public MyList<Vertex> getShortestPath(Graph g, Vertex src, Vertex dest, boolean isTime) {
		solve(g, src, dest, isTime);
		MyList<Vertex> path = new MyList<Vertex>();
		Vertex curr = dest;
		if (curr.getPv() == null && curr != src)
			return path;
		while (curr != null) {
			path.add(curr);
			curr = curr.getPv();
		}
		return path;
	}

}
