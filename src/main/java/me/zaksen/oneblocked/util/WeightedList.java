package me.zaksen.oneblocked.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WeightedList<T> {

    public class Entry {
        int accumulatedWeight;
        T object;

        public T getObject() {
            return object;
        }

        public int getAccumulatedWeight() {
            return accumulatedWeight;
        }
    }

    private final List<Entry> entries = new ArrayList<>();
    private int accumulatedWeight;

    public void addEntry(T object, int weight) {
        accumulatedWeight += weight;
        Entry e = new Entry();
        e.object = object;
        e.accumulatedWeight = accumulatedWeight;
        entries.add(e);
    }

    public T get(int value) {
        for (Entry entry : entries) {
            if (entry.accumulatedWeight >= value) {
                return entry.object;
            }
        }
        return entries.get(entries.size() - 1).object;
    }

    public int getObjectWeight(T entryCheck) {
        for (Entry entry : entries) {
            if (entry.getObject() == entryCheck) {
                return entry.accumulatedWeight;
            }
        }
        return 0;
    }

    public void clear() {
        entries.clear();
        accumulatedWeight = 0;
    }

    public void forEach(Consumer<Entry> function) {
        entries.forEach(function);
    }

    public int size() {
        return entries.size();
    }

    public T getIndex(int index) {
        return entries.get(index).object;
    }

    public Entry getEntryIndex(int index) {
        return entries.get(index);
    }
}
