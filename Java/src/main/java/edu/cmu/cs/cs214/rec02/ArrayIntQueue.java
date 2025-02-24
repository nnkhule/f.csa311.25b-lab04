package edu.cmu.cs.cs214.rec02;

/**
 * A resizable-array implementation of the {@link IntQueue} interface. The head of
 * the queue starts out at the head of the array, allowing the queue to grow and
 * shrink in constant time.
 * 
 * @autho Alex Lockwood
 * @autho Ye Lu
 */
public class ArrayIntQueue implements IntQueue {

    private int[] elementData;
    private int head;
    private int size;
    private static final int INITIAL_SIZE = 10;

    public ArrayIntQueue() {
        elementData = new int[INITIAL_SIZE];
        head = 0;
        size = 0;
    }

    @Override
    public void clear() {
        elementData = new int[INITIAL_SIZE];
        size = 0;
        head = 0;
    }

    @Override
    public Integer dequeue() {
        if (isEmpty()) return null;
    
        Integer value = elementData[head];
        elementData[head] = 0;
        head = (head + 1) % elementData.length;
        size--;
    
        if (size == 0) clear();  // Сүүлчийн элемент гарсны дараа дарааллыг цэвэрлэнэ.
        return value;
    }
    
    @Override
    public boolean enqueue(Integer value) {
        ensureCapacity();           
        int tail = (head + size) % elementData.length;
        elementData[tail] = value;
        size++;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return elementData[head];
    }

    @Override
    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elementData.length) {
            int newCapacity = elementData.length * 2 + 1;
            int[] newData = new int[newCapacity];
            for (int i = 0; i < size; i++) {
                newData[i] = elementData[(head + i) % elementData.length];
            }
            elementData = newData;
            head = 0;
        }
    }
}