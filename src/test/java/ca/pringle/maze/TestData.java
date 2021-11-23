package ca.pringle.maze;

import ca.pringle.maze.logic.MazeConfig;
import ca.pringle.maze.logic.Path;
import ca.pringle.maze.logic.SpecializedGraph;

public enum TestData {

    MAZE_2X2_S0(new MazeConfig(2, 2, 0),
            maze2x2s0(),
            new Path(3, 1),
            new int[]{1, 0, 2, 3}
    ),
    MAZE_3X4_S0(
            new MazeConfig(3, 4, 0),
            maze3x4s0(),
            new Path(2, 0),
            new int[]{0, 4, 8, 9, 10, 6, 5, 1, 2}
    ),
    MAZE_6X6_S0(
            new MazeConfig(6, 6, 0),
            maze6x6s0(),
            new Path(29, 22),
            new int[]{22, 23, 17, 16, 10, 9, 8, 14, 20, 26, 27, 28, 34, 35, 29}
    ),
    MAZE_6X6_S1(
            new MazeConfig(6, 6, 1),
            maze6x6s1(),
            new Path(18, 11),
            new int[]{11, 5, 4, 10, 9, 8, 14, 15, 16, 22, 28, 34, 33, 32, 31, 30, 24, 18}
    );

    private final MazeConfig mazeConfig;
    private final SpecializedGraph graph;
    private final Path longestPath;
    private final int[] solution;

    TestData(final MazeConfig mazeConfig,
             final SpecializedGraph graph,
             final Path longestPath,
             final int[] solution) {

        this.mazeConfig = mazeConfig;
        this.graph = graph;
        this.longestPath = longestPath;
        this.solution = solution;
    }

    public MazeConfig config() {
        return mazeConfig;
    }

    public SpecializedGraph graph() {
        return graph;
    }

    public Path longestPath() {
        return longestPath;
    }

    public int[] solution() {
        return solution;
    }

    private static SpecializedGraph maze2x2s0() {

        final SpecializedGraph graph = new SpecializedGraph(4);
        graph.put(0, 2, 1);
        graph.put(1, 0);
        graph.put(2, 0, 3);
        graph.put(3, 2);

        return graph;
    }

    private static SpecializedGraph maze3x4s0() {

        final SpecializedGraph graph = new SpecializedGraph(12);
        graph.put(0, 4);
        graph.put(1, 2, 5);
        graph.put(2, 1);
        graph.put(3, 7);
        graph.put(4, 0, 8);
        graph.put(5, 1, 6);
        graph.put(6, 5, 10, 7);
        graph.put(7, 3, 6);
        graph.put(8, 4, 9);
        graph.put(9, 8, 10);
        graph.put(10, 11, 9, 6);
        graph.put(11, 10);

        return graph;
    }

    private static SpecializedGraph maze6x6s0() {

        final SpecializedGraph graph = new SpecializedGraph(36);
        graph.put(0, 1);
        graph.put(1, 0, 7);
        graph.put(2, 8);
        graph.put(3, 4, 9);
        graph.put(4, 3);
        graph.put(5, 11);
        graph.put(6, 7);
        graph.put(7, 1, 6, 8);
        graph.put(8, 2, 7, 9, 14);
        graph.put(9, 3, 8, 10);
        graph.put(10, 11, 9, 16);
        graph.put(11, 10, 5);
        graph.put(12, 18);
        graph.put(13, 14, 19);
        graph.put(14, 13, 20, 8);
        graph.put(15, 21);
        graph.put(16, 17, 10);
        graph.put(17, 16, 23);
        graph.put(18, 12, 24);
        graph.put(19, 13, 25);
        graph.put(20, 14, 26);
        graph.put(21, 15, 27);
        graph.put(22, 23);
        graph.put(23, 17, 22);
        graph.put(24, 18, 25);
        graph.put(25, 31, 24, 19);
        graph.put(26, 32, 20, 27);
        graph.put(27, 28, 26, 21);
        graph.put(28, 27, 34);
        graph.put(29, 35);
        graph.put(30, 31);
        graph.put(31, 25, 30);
        graph.put(32, 26);
        graph.put(33, 34);
        graph.put(34, 28, 33, 35);
        graph.put(35, 29, 34);

        return graph;
    }

    private static SpecializedGraph maze6x6s1() {

        final SpecializedGraph graph = new SpecializedGraph(36);

        graph.put(0, 1);
        graph.put(1, 0, 7);
        graph.put(2, 8, 3);
        graph.put(3, 2);
        graph.put(4, 10, 5);
        graph.put(5, 11, 4);
        graph.put(6, 12, 7);
        graph.put(7, 1, 6);
        graph.put(8, 2, 14, 9);
        graph.put(9, 8, 10);
        graph.put(10, 4, 9);
        graph.put(11, 5);
        graph.put(12, 6, 13);
        graph.put(13, 12, 14);
        graph.put(14, 8, 13, 15, 20);
        graph.put(15, 14, 16);
        graph.put(16, 15, 22, 17);
        graph.put(17, 16);
        graph.put(18, 24);
        graph.put(19, 25);
        graph.put(20, 14);
        graph.put(21, 22, 27);
        graph.put(22, 23, 28, 16, 21);
        graph.put(23, 22);
        graph.put(24, 18, 30);
        graph.put(25, 26, 19, 31);
        graph.put(26, 25);
        graph.put(27, 21);
        graph.put(28, 34, 22, 29);
        graph.put(29, 28);
        graph.put(30, 24, 31);
        graph.put(31, 25, 30, 32);
        graph.put(32, 33, 31);
        graph.put(33, 32, 34);
        graph.put(34, 28, 35, 33);
        graph.put(35, 34);

        return graph;
    }
}
