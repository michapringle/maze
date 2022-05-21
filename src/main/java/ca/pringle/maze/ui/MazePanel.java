package ca.pringle.maze.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import ca.pringle.maze.logic.Edge;
import ca.pringle.maze.logic.MazeConfig;
import ca.pringle.maze.logic.Path;
import ca.pringle.maze.logic.SpecializedGraph;

import static ca.pringle.maze.ui.PanelDimensions.NODE_INSET;
import static ca.pringle.maze.ui.PanelDimensions.NODE_WIDTH;
import static ca.pringle.maze.util.Checks.check;

final class MazePanel extends JPanel {

    private SpecializedGraph dag;
    private Path startAndEndNodes;
    private int[] solution;
    private PanelDimensions panelDimensions;
    private MazeConfig mazeConfig;

    public void update(final SpecializedGraph dag,
                       final Path startAndEndNodes,
                       final PanelDimensions panelDimensions,
                       final MazeConfig mazeConfig) {

        this.dag = check(dag).isNotNull();
        this.startAndEndNodes = check(startAndEndNodes).isNotNull();
        this.panelDimensions = check(panelDimensions).isNotNull();
        this.mazeConfig = check(mazeConfig).isNotNull();
    }

    public void addSolution(final int[] solution) {
        this.solution = check(solution).isNotNull();
    }

    public boolean hasSolution() {
        return solution != null && solution.length != 0;
    }

    public PanelDimensions getPanelDimensions() {
        return panelDimensions;
    }

    @Override
    public void paint(final Graphics graphics) {

        // nothing to paint yet
        if (dag == null) {
            return;
        }

        paintMaze(graphics);
        paintSolution(graphics);
        paintStartAndEndNodes(graphics);
    }

    private void paintMaze(final Graphics graphics) {

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        graphics.setColor(Color.black);
        paintFullGrid(graphics);

        graphics.setColor(Color.white);
        for (int fromNode = 0; fromNode < dag.numberOfNodes(); fromNode++) {
            for (final int toNode : dag.get(fromNode).toArray()) {
                final Edge edge = new Edge(fromNode, toNode);
                paintErasedEdge(graphics, edge);
            }
        }
    }

    private void paintSolution(final Graphics graphics) {

        if (!hasSolution()) {
            return;
        }

        graphics.setColor(Color.pink);
        int lastNode = solution[0];

        for (int i = 1; i < solution.length; i++) {

            final int currentNode = solution[i];
            addSolutionNode(graphics, lastNode, currentNode);
            lastNode = currentNode;
        }
    }

    private void paintStartAndEndNodes(final Graphics graphics) {

        graphics.setColor(Color.red);
        addSquareNode(graphics, startAndEndNodes.fromNode);
        addSquareNode(graphics, startAndEndNodes.toNode);
        graphics.setColor(Color.black);
    }

    private void addSquareNode(final Graphics graphics,
                               final int node) {

        graphics.fillRect(
                panelDimensions.nodeLeft(node, mazeConfig.getColumns()) + NODE_INSET,
                panelDimensions.nodeTop(node, mazeConfig.getColumns()) + NODE_INSET,
                NODE_WIDTH - 2 * NODE_INSET,
                NODE_WIDTH - 2 * NODE_INSET
        );
    }

    private void addSolutionNode(final Graphics graphics,
                                 final int lastNode,
                                 final int currentNode) {

        final int shortDimension = NODE_WIDTH - 2 * (NODE_INSET + 1);
        final int longDimension = 2 * (NODE_WIDTH - NODE_INSET - 1);

        if (isHorizontalEdge(currentNode, lastNode)) {
            graphics.fillRect(
                    panelDimensions.nodeLeft(lastNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    panelDimensions.nodeTop(lastNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    longDimension,
                    shortDimension
            );

        } else if (isHorizontalEdge(lastNode, currentNode)) {
            graphics.fillRect(
                    panelDimensions.nodeLeft(currentNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    panelDimensions.nodeTop(currentNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    longDimension,
                    shortDimension
            );

        } else if (isVerticalEdge(currentNode, lastNode)) {
            graphics.fillRect(
                    panelDimensions.nodeLeft(lastNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    panelDimensions.nodeTop(lastNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    shortDimension,
                    longDimension
            );

        } else {
            graphics.fillRect(
                    panelDimensions.nodeLeft(currentNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    panelDimensions.nodeTop(currentNode, mazeConfig.getColumns()) + NODE_INSET + 1,
                    shortDimension,
                    longDimension
            );
        }
    }

    private void paintFullGrid(final Graphics graphics) {

        for (int x = 0; x <= mazeConfig.getColumns(); x++) {
            graphics.drawLine(
                    panelDimensions.getMazeXMin() + x * NODE_WIDTH,
                    panelDimensions.getMazeYMin(),
                    panelDimensions.getMazeXMin() + x * NODE_WIDTH,
                    panelDimensions.getMazeYMax()
            );
        }

        for (int y = 0; y <= mazeConfig.getRows(); y++) {
            graphics.drawLine(
                    panelDimensions.getMazeXMin(),
                    panelDimensions.getMazeYMin() + y * NODE_WIDTH,
                    panelDimensions.getMazeXMax(),
                    panelDimensions.getMazeYMin() + y * NODE_WIDTH
            );
        }
    }

    private void paintErasedEdge(final Graphics graphics,
                                 final Edge edge) {

        final int nodeXMin = panelDimensions.nodeLeft(edge.node1, mazeConfig.getColumns());
        final int nodeYMin = panelDimensions.nodeTop(edge.node1, mazeConfig.getColumns());
        final int nodeXMax = nodeXMin + NODE_WIDTH;
        final int nodeYMax = nodeYMin + NODE_WIDTH;

        if (isHorizontalEdge(edge.node2, edge.node1)) {
            graphics.drawLine(nodeXMax, nodeYMin + 1, nodeXMax, nodeYMax - 1);
        } else if (isVerticalEdge(edge.node2, edge.node1)) {
            graphics.drawLine(nodeXMin + 1, nodeYMax, nodeXMax - 1, nodeYMax);
        }
    }

    private boolean isHorizontalEdge(final int node1,
                                     final int node2) {

        return node1 - node2 == 1;
    }

    private boolean isVerticalEdge(final int node1,
                                   final int node2) {

        return node1 - node2 == mazeConfig.getColumns();
    }
}
