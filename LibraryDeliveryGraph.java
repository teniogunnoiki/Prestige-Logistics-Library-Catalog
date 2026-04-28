import java.util.*;
public class LibraryDeliveryGraph {

    static class Edge {
        String destination;
        int minutes;

        Edge(String destination, int minutes) {
            this.destination = destination;
            this.minutes = minutes;
        }

        @Override
        public String toString() {
            return destination + " (" + minutes + " min)";
        }
    }

    private final Map<String, List<Edge>> adjList = new LinkedHashMap<>();

    public void addVertex(String name) {
        adjList.putIfAbsent(name, new ArrayList<>());
    }

    public void addEdge(String from, String to, int minutes) {
        adjList.get(from).add(new Edge(to, minutes));
        adjList.get(to).add(new Edge(from, minutes));
    }

    public List<Edge> getNeighbors(String name) {
        return adjList.getOrDefault(name, Collections.emptyList());
    }

    public void printGraph() {
        System.out.println("\n=== Delivery Network (Adjacency List) ===");
        System.out.println("Edge weight = delivery time in minutes\n");
        for (Map.Entry<String, List<Edge>> entry : adjList.entrySet()) {
            System.out.printf("  %-35s -> %s%n", entry.getKey(), entry.getValue());
        }
    }

    public void dijkstra(String source) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev  = new HashMap<>();
        PriorityQueue<String> pq  = new PriorityQueue<>(
                Comparator.comparingInt(v -> dist.getOrDefault(v, Integer.MAX_VALUE)));

        for (String v : adjList.keySet()) dist.put(v, Integer.MAX_VALUE);
        dist.put(source, 0);
        pq.add(source);

        while (!pq.isEmpty()) {
            String cur = pq.poll();
            for (Edge e : getNeighbors(cur)) {
                int newDist = dist.get(cur) + e.minutes;
                if (newDist < dist.get(e.destination)) {
                    dist.put(e.destination, newDist);
                    prev.put(e.destination, cur);
                    pq.add(e.destination);
                }
            }
        }

        System.out.println("\n=== Fastest Delivery Times from: " + source + " ===");
        for (Map.Entry<String, Integer> entry : dist.entrySet()) {
            if (!entry.getKey().equals(source)) {
                System.out.printf("  To %-35s : %3d min  | Route: %s%n",
                        entry.getKey(), entry.getValue(), buildPath(prev, source, entry.getKey()));
            }
        }
    }

    public void findFastestRoute(String from, String to) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev  = new HashMap<>();
        PriorityQueue<String> pq  = new PriorityQueue<>(
                Comparator.comparingInt(v -> dist.getOrDefault(v, Integer.MAX_VALUE)));

        for (String v : adjList.keySet()) dist.put(v, Integer.MAX_VALUE);
        dist.put(from, 0);
        pq.add(from);

        while (!pq.isEmpty()) {
            String cur = pq.poll();
            if (cur.equals(to)) break;
            for (Edge e : getNeighbors(cur)) {
                int newDist = dist.get(cur) + e.minutes;
                if (newDist < dist.get(e.destination)) {
                    dist.put(e.destination, newDist);
                    prev.put(e.destination, cur);
                    pq.add(e.destination);
                }
            }
        }

        System.out.println("\n--- Fastest Route: " + from + " -> " + to + " ---");
        System.out.println("  Time  : " + dist.get(to) + " minutes");
        System.out.println("  Route : " + buildPath(prev, from, to));
    }

    public void bfs(String start) {
        System.out.println("\n=== BFS from: " + start + " ===");
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            System.out.println("  Visiting: " + cur);
            for (Edge e : getNeighbors(cur))
                if (!visited.contains(e.destination)) { visited.add(e.destination); queue.add(e.destination); }
        }
    }

    public void dfs(String start) {
        System.out.println("\n=== DFS from: " + start + " ===");
        dfsHelper(start, new LinkedHashSet<>());
    }

    private void dfsHelper(String v, Set<String> visited) {
        visited.add(v);
        System.out.println("  Visiting: " + v);
        for (Edge e : getNeighbors(v))
            if (!visited.contains(e.destination)) dfsHelper(e.destination, visited);
    }

    private String buildPath(Map<String, String> prev, String source, String target) {
        LinkedList<String> path = new LinkedList<>();
        for (String cur = target; cur != null; cur = prev.get(cur)) path.addFirst(cur);
        if (path.isEmpty() || !path.getFirst().equals(source)) return "No path found";
        return String.join(" -> ", path);
    }
}