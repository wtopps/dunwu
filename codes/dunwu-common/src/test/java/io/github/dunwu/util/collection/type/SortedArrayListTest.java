package io.github.dunwu.util.collection.type;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import io.github.dunwu.util.collection.ListUtil;
import io.github.dunwu.util.collection.MoreLists;

import com.google.common.collect.Ordering;

public class SortedArrayListTest {

    @Test
    public void sortedArrayList() {
        SortedArrayList<String> list = MoreLists.createSortedArrayList();
        list.add("9");
        list.add("1");
        list.add("6");
        list.add("9");
        list.add("3");

        assertThat(list).containsExactly("1", "3", "6", "9", "9");

        list.remove(2);
        assertThat(list).containsExactly("1", "3", "9", "9");

        assertThat(list.contains("3")).isTrue();
        assertThat(list.contains("2")).isFalse();

        try {
            list.add(1, "2");
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(UnsupportedOperationException.class);
        }

        try {
            list.set(1, "2");
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(UnsupportedOperationException.class);
        }

        SortedArrayList<String> list2 = MoreLists.createSortedArrayList(Ordering.natural());
        list2.addAll(ListUtil.newArrayList("3", "1", "2"));
        assertThat(list2).containsExactly("1", "2", "3");
    }
}