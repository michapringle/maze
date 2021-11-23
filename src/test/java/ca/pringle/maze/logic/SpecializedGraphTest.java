package ca.pringle.maze.logic;

import ca.pringle.maze.TestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SpecializedGraphTest {

    @Test
    void constructorShouldValidateInput() {
        assertThrows(NegativeArraySizeException.class, () -> new SpecializedGraph(-1));
        assertDoesNotThrow(() -> new SpecializedGraph(0));
    }

    @Test
    void copyShouldShallowCopyKeysAndListData() {

        assertEquals(TestData.MAZE_2X2_S0.graph(), SpecializedGraph.copy(TestData.MAZE_2X2_S0.graph()));
        assertEquals(TestData.MAZE_3X4_S0.graph(), SpecializedGraph.copy(TestData.MAZE_3X4_S0.graph()));
        assertEquals(TestData.MAZE_6X6_S0.graph(), SpecializedGraph.copy(TestData.MAZE_6X6_S0.graph()));
        assertEquals(TestData.MAZE_6X6_S1.graph(), SpecializedGraph.copy(TestData.MAZE_6X6_S1.graph()));
    }

    @Test
    void putShouldRejectInvalidInputs() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new SpecializedGraph(1).put(-1, 0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new SpecializedGraph(1).put(1, 0));
        assertThrows(NullPointerException.class, () -> new SpecializedGraph(1).put(0, (Integer) null));
    }

    @Test
    void putShouldAcceptValidInputs() {

        {
            final SpecializedGraph sut = new SpecializedGraph(1);
            sut.put(-1);
            assertEquals(1, sut.numberOfNodes());
            assertNull(sut.get(0));
        }

        {
            final SpecializedGraph sut = new SpecializedGraph(1);
            sut.put(0, -1);
            assertEquals(1, sut.numberOfNodes());
            assertEquals(1, sut.get(0).size());
            assertEquals(-1, sut.get(0).get(0));
        }

        {
            final SpecializedGraph sut = new SpecializedGraph(1);
            sut.put(0, 3, 4);
            assertEquals(1, sut.numberOfNodes());
            assertEquals(2, sut.get(0).size());
            assertEquals(3, sut.get(0).get(0));
            assertEquals(4, sut.get(0).get(1));
        }
    }

    @Test
    void removeShouldThrowExceptionIfInvalidInput() {
        final SpecializedGraph sut = new SpecializedGraph(2);
        sut.put(0, 4, 5, 6);
        sut.put(1, 4, 5, 6);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> sut.remove(-1, 0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> sut.remove(2, 0));
    }

    @Test
    void removeShouldWork() {
        final SpecializedGraph sut = new SpecializedGraph(2);
        sut.put(0, 4, 5, 6);
        sut.put(1, 4, 5, 6);

        sut.remove(0, 4);
        assertEquals(2, sut.get(0).size());
        assertEquals(5, sut.get(0).get(0));
        assertEquals(6, sut.get(0).get(1));
        assertEquals(3, sut.get(1).size());
        assertEquals(4, sut.get(1).get(0));
        assertEquals(5, sut.get(1).get(1));
        assertEquals(6, sut.get(1).get(2));

        assertDoesNotThrow(() -> sut.remove(0, 1));
        assertEquals(2, sut.get(0).size());
        assertEquals(3, sut.get(1).size());
    }

    @Test
    void equalsAndHashcodeShouldObeyContract() {

        EqualsVerifier
                .forClass(SpecializedGraph.class)
                .withNonnullFields("nodes")
                .verify();

        // special case not covered by EqualsVerifier
        assertNotEquals(new SpecializedGraph(1), new SpecializedGraph(2));
    }

    @Test
    void toStringShouldHaveCustomImplementation() {

        final SpecializedGraph sut = new SpecializedGraph(1);
        assertFalse(sut.toString().contains(Integer.toHexString(sut.hashCode())));
    }

    @Test
    void specializedListConstructorWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();

        assertEquals(0, sut.size());
        assertEquals(0, sut.toArray().length);
    }

    @Test
    void specializedListAppendWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();

        sut.append(1);

        assertEquals(1, sut.size());
        assertEquals(1, sut.toArray().length);

        sut.append(2);
        assertEquals(2, sut.size());
        assertEquals(2, sut.toArray().length);
    }

    @Test
    void specializedListGetWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();

        sut.append(1);
        assertEquals(1, sut.get(0));

        sut.append(2);
        assertEquals(1, sut.get(0));
        assertEquals(2, sut.get(1));

        sut.removeValue(2);
        assertEquals(1, sut.get(0));
    }

    @Test
    void specializedListSizeWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();
        assertEquals(0, sut.size());

        sut.append(1);
        assertEquals(1, sut.size());

        sut.append(2);
        assertEquals(2, sut.size());

        sut.removeValue(1);
        assertEquals(1, sut.size());

        sut.removeValue(2);
        assertEquals(0, sut.size());
    }

    @Test
    void specializedListIsEmptyWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();
        assertTrue(sut.isEmpty());

        sut.append(1);
        assertFalse(sut.isEmpty());

        sut.append(2);
        assertFalse(sut.isEmpty());

        sut.removeValue(1);
        assertFalse(sut.isEmpty());

        sut.removeValue(2);
        assertTrue(sut.isEmpty());
    }

    @Test
    void specializedListContainsWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();
        assertFalse(sut.contains(1));
        assertFalse(sut.contains(2));

        sut.append(1);
        assertTrue(sut.contains(1));
        assertFalse(sut.contains(2));

        sut.append(2);
        assertTrue(sut.contains(1));
        assertTrue(sut.contains(2));

        sut.removeValue(1);
        assertFalse(sut.contains(1));
        assertTrue(sut.contains(2));

        sut.removeValue(2);
        assertFalse(sut.contains(1));
        assertFalse(sut.contains(2));
    }

    @Test
    void specializedListToArrayWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();

        assertArrayEquals(new int[0], sut.toArray());

        sut.append(1);
        assertArrayEquals(new int[]{1}, sut.toArray());

        sut.append(2);
        assertArrayEquals(new int[]{1, 2}, sut.toArray());

        sut.removeValue(1);
        assertArrayEquals(new int[]{2}, sut.toArray());

        sut.removeValue(2);
        assertArrayEquals(new int[0], sut.toArray());
    }

    @Test
    void specializedListRemoveValueWorks() {

        SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();

        sut.removeValue(1);
        assertArrayEquals(new int[0], sut.toArray());

        sut.append(1);
        sut.append(2);
        sut.append(3);
        sut.append(4);

        sut.removeValue(4);
        assertArrayEquals(new int[]{1, 2, 3}, sut.toArray());

        sut.removeValue(3);
        assertArrayEquals(new int[]{1, 2}, sut.toArray());

        sut.removeValue(2);
        assertArrayEquals(new int[]{1}, sut.toArray());

        sut.removeValue(1);
        assertArrayEquals(new int[0], sut.toArray());
    }

    @Test
    void SpecializedListEqualsAndHashcodeShouldObeyContract() {
        EqualsVerifier
                .forClass(SpecializedGraph.SpecializedList.class)
                .withNonnullFields("list")
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        // special case that equals verifier does not cover
        final SpecializedGraph.SpecializedList list1 = new SpecializedGraph.SpecializedList();
        list1.append(2);
        list1.append(5);
        list1.removeValue(5);

        final SpecializedGraph.SpecializedList list2 = new SpecializedGraph.SpecializedList();
        list2.append(2);
        list2.append(8);
        list2.removeValue(8);

        assertEquals(list1, list2);
    }

    @Test
    void SpecializedListToStringShouldHaveCustomImplementation() {

        final SpecializedGraph.SpecializedList sut = new SpecializedGraph.SpecializedList();
        assertFalse(sut.toString().contains(Integer.toHexString(sut.hashCode())));
    }
}
