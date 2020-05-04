package ca.pringle.maze.ui;

import ca.pringle.maze.Preconditions;
import ca.pringle.maze.logic.Edge;
import ca.pringle.maze.util.Pair;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

final class MazePanel extends JPanel {

    private Set<Edge> edges;
    private Pair<Integer, Integer> startAndEndNodes;
    private PanelDimensions panelDimensions;

    public void update(final Set<Edge> edges,
                       final Pair<Integer, Integer> startAndEndNodes,
                       final PanelDimensions panelDimensions) {

        this.edges = edges;
        this.startAndEndNodes = startAndEndNodes;
        this.panelDimensions = panelDimensions;

        Preconditions.checkNotNull(this.edges);
        Preconditions.checkNotNull(this.startAndEndNodes);
        Preconditions.checkNotNull(this.panelDimensions);
    }

    @Override
    public void paint(final Graphics graphics) {

        // nothing to paint yet
        if (edges == null) {
            return;
        }

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics.setColor(Color.black);
        drawFullGrid(graphics);
        graphics.setColor(Color.white);

        for (final Edge edge : edges) {
            eraseEdge(graphics, edge);
        }

        graphics.setColor(Color.red);
        addNode(graphics, startAndEndNodes.left);
        addNode(graphics, startAndEndNodes.right);
        graphics.setColor(Color.black);
    }

    private void addNode(final Graphics graphics,
                         final Integer node) {

        graphics.fillRect(
                panelDimensions.mazeDimensions.xMin + (node % panelDimensions.columns) * panelDimensions.pathWidth + panelDimensions.pathWidth / 5,
                panelDimensions.mazeDimensions.yMin + (node / panelDimensions.columns) * panelDimensions.pathWidth + panelDimensions.pathWidth / 5,
                3 * panelDimensions.pathWidth / 5,
                3 * panelDimensions.pathWidth / 5);
    }

    private void drawFullGrid(final Graphics graphics) {

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

    private void eraseEdge(final Graphics graphics,
                           final Edge edge) {

        if (edge == null) {
            return;
        }

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
        } else {
            System.out.println("Bad point " + edge);
        }
    }
}
