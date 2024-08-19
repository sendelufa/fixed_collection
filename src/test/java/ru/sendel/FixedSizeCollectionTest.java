package ru.sendel;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FixedSizeCollectionTest {

    @Test
    void whenCollectionIsCreated_thenSizeZero() {
        var collection = new FixedSizeCollection<String>(3);
        assertThat(collection).hasSize(0);
    }

    @Test
    void whenAddElement_thenSizeOneAndElementExistInCollection() {
        var collection = new FixedSizeCollection<Integer>(3);
        collection.add(5);

        assertThat(collection).hasSize(1);
        assertThat(collection).containsExactlyInAnyOrderElementsOf(
                List.of(5));
    }

    @Test
    void whenAddElementsSuccessful_thenReturnTrue() {
        var collection = new FixedSizeCollection<Integer>(1);
        assertThat(collection.add(6)).isTrue();
        assertThat(collection.add(7)).isFalse();
    }

    @Test
    void whenAddNull_NothingChanges() {
        var collection = new FixedSizeCollection<String>(6);
        assertThat(collection.add(null)).isFalse();
        assertThat(collection).hasSize(0);
    }

    @Test
    void whenSearchInCollectionExistentElement_thenFoundIt() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");

        assertThat(collection.contains("f")).isTrue();
        assertThat(collection.contains("b")).isFalse();
        assertThat(collection.contains(null)).isFalse();
    }

    @Test
    void whenSearchInCollectionExistentElements_thenFoundThem() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");
        collection.add("h");
        collection.add("r");

        var otherCollection = new FixedSizeCollection<String>(2);
        otherCollection.add("f");
        otherCollection.add("h");

        assertThat(collection.containsAll(otherCollection))
                .isTrue();
    }

    @Test
    void whenSearchInCollectionNotExistentElements_thenNotFoundThem() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");
        collection.add("h");
        collection.add("r");

        var otherCollection = new FixedSizeCollection<String>(2);
        otherCollection.add("z");
        otherCollection.add("Я");

        assertThat(collection.containsAll(otherCollection))
                .isFalse();
    }

    @Test
    void whenAddOtherCollection_thenAllElementsAdded() {
        var collection = new FixedSizeCollection<Integer>(8);
        collection.add(6);
        collection.add(7);

        var otherCollection = new FixedSizeCollection<Integer>(2);
        otherCollection.add(9);
        otherCollection.add(7);

        assertThat(collection.addAll(otherCollection)).isTrue();
        assertThat(collection).containsExactlyInAnyOrder(6, 7, 7, 9);
    }

    @Test
    void whenRemoveExistentElement_thenReturnTrueAndRemoveIt() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");

        assertThat(collection.remove("f")).isTrue();
        assertThat(collection.remove("h")).isFalse();
        assertThat(collection)
                .containsExactlyInAnyOrder("a")
                .hasSize(1);
    }

    @Test
    void whenRemoveElementAfterRemoveNext_thenCollectionChanged() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");
        collection.add("b");
        collection.add("c");

        collection.remove("b");
        collection.remove("a");

        assertThat(collection).containsExactlyInAnyOrder("f", "c");
    }

    @Test
    void whenRemoveByAnotherCollection_thenThoseElementsNotFound() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");
        collection.add("a");
        collection.add("b");
        collection.add("c");
        collection.add("e");

        var otherCollection = new FixedSizeCollection<String>(6);
        otherCollection.add("e");
        otherCollection.add("a");

        assertThat(collection.removeAll(otherCollection)).isTrue();
        assertThat(collection)
                .containsExactlyInAnyOrder("f", "b", "c")
                .hasSize(3);
    }

    @Test
    void whenClearCollection_thenSizeZeroAndNoElements() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");
        collection.add("a");

        collection.clear();

        assertThat(collection).hasSize(0)
                .containsExactlyInAnyOrderElementsOf(List.of());
    }

    @Test
    void whenToArray_thenAlwaysNewArray() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");
        collection.add("a");

        Object[] actual = collection.toArray();

        assertThat(actual).isEqualTo(new Object[]{"f", "a", "a"});
    }

    @Test
    void whenToArrayWithType_thenAlwaysNewArray() {
        var collection = new FixedSizeCollection<String>(6);
        collection.add("f");
        collection.add("a");
        collection.add("a");

        String[] actual = collection.toArray(new String[0]);

        assertThat(actual).isEqualTo(new String[]{"f", "a", "a"});
    }

    @Test
    void whenCallCollectionIsEmpty_thenTrueIfNoElementsInCollection() {
        var collection = new FixedSizeCollection<String>(6);

        assertThat(collection).isEmpty();

        collection.add("f");
        collection.add("a");

        assertThat(collection).isNotEmpty();

        collection.clear();

        assertThat(collection).isEmpty();
    }


}