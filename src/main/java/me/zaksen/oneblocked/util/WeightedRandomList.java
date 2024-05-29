package me.zaksen.oneblocked.util;

import java.util.ArrayList;
import java.util.List;

public class WeightedRandomList<T> {

    public class Entry {
        double weight;
        double accumulatedWeight;
        T object;

        public T getObject() {
            return object;
        }

        public double getWeight() {
            return weight;
        }
    }

    private final List<Entry> entries = new ArrayList<>();
    private double accumulatedWeight;

    public void addEntry(T object, double weight) {
        accumulatedWeight += weight;
        Entry e = new Entry();
        e.object = object;
        e.accumulatedWeight = accumulatedWeight;
        e.weight = weight;
        entries.add(e);
    }

    public T getRandom() {
        double r = Math.random() * accumulatedWeight;

        for (Entry entry : entries) {
            if (entry.accumulatedWeight >= r) {
                return entry.object;
            }
        }

        return null;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public double getAccumulatedWeight() {
        return accumulatedWeight;
    }
}
