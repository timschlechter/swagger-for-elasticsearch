package org.elasticsearch.routes.util;

import org.elasticsearch.common.base.Function;

import java.util.Comparator;
import java.util.List;

public class CollectionUtil {

    /**
     * Sorts the specified list according to the order induced by comparing the keys extracted by the given
     * {keyExtractor}
     *
     * @param items        List of items to sort
     * @param keyExtractor The function which produces the value to compare
     */
    public static <T, K extends Comparable> void sort(List<T> items, final Function<T, K> keyExtractor) {
        if (items == null) {
            return;
        }

        java.util.Collections.sort(
            items,
            new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    K key1 = o1 != null ? keyExtractor.apply(o1) : null;
                    K key2 = o2 != null ? keyExtractor.apply(o2) : null;

                    if (key1 == key2) {
                        return 0;
                    }

                    if (key1 == null) {
                        return -1;
                    }

                    if (key2 == null) {
                        return 1;
                    }

                    return key1.compareTo(key2);
                }
            }
        );
    }
}
