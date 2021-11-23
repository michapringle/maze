package ca.pringle.maze.ui;

import ca.pringle.maze.logic.Edge;
import ca.pringle.maze.logic.Path;
import ca.pringle.maze.logic.SpecializedGraph;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

import static ca.pringle.maze.util.Checks.check;

final class MazePanel extends JPanel {

    private SpecializedGraph dag;
    private Path startAndEndNodes;
    private int[] solution;
    private PanelDimensions panelDimensions;

    public void update(final SpecializedGraph dag,
                       final Path startAndEndNodes,
                       final PanelDimensions panelDimensions) {

        this.dag = check(dag).isNotNull();
        this.startAndEndNodes = check(startAndEndNodes).isNotNull();
        this.panelDimensions = check(panelDimensions).isNotNull();
    }

    public void addSolution(final int[] solution) {
        this.solution = check(solution).isNotNull();
    }

    public boolean hasSolution() {
        return solution.length != 0;
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

        if (solution.length == 0) {
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

        graphics.fillRect(calculateLeft(node), calculateTop(node), calculateWidth(), calculateHeight());
    }

    private void addSolutionNode(final Graphics graphics,
                                 final int lastNode,
                                 final int currentNode) {

        if (lastNode - currentNode == -1) {
            graphics.fillRect(
                    calculateLeft(lastNode) + 1, calculateTop(lastNode) + 1,
                    3 * (calculateWidth() - 2), calculateHeight() - 2
            );
        } else if (lastNode - currentNode == 1) {
            graphics.fillRect(
                    calculateLeft(currentNode) + 1, calculateTop(currentNode) + 1,
                    3 * (calculateWidth() - 2), calculateHeight() - 2
            );
        } else if (lastNode < currentNode) {
            graphics.fillRect(
                    calculateLeft(lastNode) + 1, calculateTop(lastNode) + 1,
                    calculateWidth() - 2, 3 * (calculateHeight() - 2)
            );
        } else {
            graphics.fillRect(
                    calculateLeft(currentNode) + 1, calculateTop(currentNode) + 1,
                    calculateWidth() - 2, 3 * (calculateHeight() - 2)
            );
        }
    }

    private int calculateLeft(final int node) {
        return panelDimensions.mazeDimensions.xMin +
                (node % panelDimensions.columns) * panelDimensions.pathWidth +
                panelDimensions.pathWidth / 5;
    }

    private int calculateTop(final int node) {
        return panelDimensions.mazeDimensions.yMin +
                (node / panelDimensions.columns) * panelDimensions.pathWidth +
                panelDimensions.pathWidth / 5;
    }

    private int calculateWidth() {
        return 3 * panelDimensions.pathWidth / 5;
    }

    private int calculateHeight() {
        return 3 * panelDimensions.pathWidth / 5;
    }

    private void paintFullGrid(final Graphics graphics) {

        //the two drawLines above cover the x = 0 and x = d.cols case
        for (int x = 0; x <= panelDimensions.columns; x++) {
            graphics.drawLine(
                    x * panelDimensions.pathWidth + panelDimensions.mazeDimensions.xMin,
                    panelDimensions.mazeDimensions.yMin,
                    x * panelDimensions.pathWidth + panelDimensions.mazeDimensions.xMin,
                    panelDimensions.mazeDimensions.yMax
            );
        }

        for (int y = 0; y <= panelDimensions.rows; y++) {
            graphics.drawLine(
                    panelDimensions.mazeDimensions.xMin,
                    y * panelDimensions.pathWidth + panelDimensions.mazeDimensions.yMin,
                    panelDimensions.mazeDimensions.xMax,
                    y * panelDimensions.pathWidth + panelDimensions.mazeDimensions.yMin
            );
        }
    }

    private void paintErasedEdge(final Graphics graphics,
                                 final Edge edge) {

        final int cellXMin = panelDimensions.mazeDimensions.xMin + (edge.node1 % panelDimensions.columns) * panelDimensions.pathWidth;
        final int cellYMin = panelDimensions.mazeDimensions.yMin + (edge.node1 / panelDimensions.columns) * panelDimensions.pathWidth;
        final int cellXMax = cellXMin + panelDimensions.pathWidth;
        final int cellYMax = cellYMin + panelDimensions.pathWidth;

        final boolean isEdgeGoingRight = edge.node1 == (edge.node2 - 1);
        final boolean isEdgeGoingDown = edge.node1 == (edge.node2 - panelDimensions.columns);

        if (isEdgeGoingRight) {
            graphics.drawLine(cellXMax, cellYMin + 1, cellXMax, cellYMax - 1);
        } else if (isEdgeGoingDown) {
            graphics.drawLine(cellXMin + 1, cellYMax, cellXMax - 1, cellYMax);
        }
    }
}
