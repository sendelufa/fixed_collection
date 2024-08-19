package ru.sendel;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedSizeCollection<E> implements Collection<E> {

    private final E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public FixedSizeCollection(int size) {
        elements = (E[]) new Object[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        for (E e : this) {
            if (e.equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                if (i == elements.length) return false;
                if (elements[i] == null) {
                    i++;
                    return hasNext();
                }
                return true;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return elements[i++];
            }

            @Override
            public void remove() {
                if (elements[i - 1] != null) {
                    size--;
                }
                elements[i - 1] = null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        var array = new Object[size];

        var iterator = iterator();
        for (int i = 0; i < array.length; i++) {
            array[i] = iterator.next();
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        T[] array = (T[]) java.lang.reflect.Array
                .newInstance(a.getClass().componentType(), size);
        int i = 0;
        for (E e : this) {
            array[i++] = (T) e;
        }
        return array;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            return false;
        }

        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                elements[i] = e;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        var iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        var isAddedAtLeastOnce = false;
        for (E e : c) {
            isAddedAtLeastOnce = add(e) || isAddedAtLeastOnce;
        }
        return isAddedAtLeastOnce;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var isRemoveAtLeastOnce = false;
        var iterator = iterator();
        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                isRemoveAtLeastOnce = true;
            }
        }

        return isRemoveAtLeastOnce;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        var iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

    }
}
