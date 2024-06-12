package Models;

public class Node {
    int row, col, cost, heuristic;

    Node(int row, int col, int cost, int heuristic) {
        this.row = row;
        this.col = col;
        this.cost = cost;
        this.heuristic = heuristic;
    }
}