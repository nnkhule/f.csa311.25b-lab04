package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO:
 * 1. {@link LinkedIntQueue}-ийг тодорхойлолтын тестээр шалгана. 
 *    mQueue = new LinkedIntQueue(); ашиглан IntQueue интерфейстэй нийцэж буйг батална.
 *
 * 2. Дараа нь "mQueue = new LinkedIntQueue();"-ийг comment хийж, "mQueue = new ArrayIntQueue();"-г тайлна.
 *    1-р хэсгийн тестүүдийг ашиглаж ArrayIntQueue-г шалгаж, алдаануудыг олно.
 *    Мөн бүтцийн тестийг ашиглаж 100% мөрийн хамралтад хүрнэ.
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Тест бүрийн өмнө дуудагдана.
     */
    @Before
    public void setUp() {
        // Uncomment the following line to test LinkedIntQueue
        // mQueue = new LinkedIntQueue();
        
        // Comment the following line when testing LinkedIntQueue
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        assertTrue("Queue хоосон байх ёстой", mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(42);
        assertFalse("Queue хоосон биш байх ёстой", mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull("Хоосон queue дээр peek нь null өгөх ёстой", mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(99);
        assertEquals("Peek нь хамгийн түрүүнд оруулсан элементийг харуулах ёстой", Integer.valueOf(99), mQueue.peek());
        assertEquals("Peek хийсний дараа хэмжээ өөрчлөгдөх ёсгүй", 1, mQueue.size());
    }

    @Test
    public void testEnqueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals("Queue-ийн эхний элемент" , testList.get(0), mQueue.peek());
        }
    }

    @Test
    public void testDequeue() {
        for (Integer val : testList) {
            mQueue.enqueue(val);
        }

        for (int i = 0; i < testList.size(); i++) {
            assertEquals("Dequeue хийхэд зөв элемент гарах ёстой", testList.get(i), mQueue.dequeue());
            assertEquals("Dequeue хийсний дараах хэмжээ", testList.size() - i - 1, mQueue.size());
        }

        System.out.println("Queue size before final dequeue: " + mQueue.size());
        assertNull("Хоосон queue-ээс dequeue хийхэд null гарах ёстой", mQueue.dequeue());
    }

    @Test
    public void testEnsureCapacity() {
        mQueue = new ArrayIntQueue(); // Capacity нэмэгдэхийг шалгах тул ArrayIntQueue ашиглана.
        for (int i = 0; i < 15; i++) {
            mQueue.enqueue(i);
        }
        assertEquals("Queue-ийн хэмжээ 15 байх ёстой", 15, mQueue.size());
        assertEquals("Peek нь 0 байх ёстой", Integer.valueOf(0), mQueue.peek());
    }

    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                mQueue.enqueue(input);
            }

            for (Integer expected : correctResult) {
                assertEquals("Dequeued элемент" , expected, mQueue.dequeue());
            }
        }
    }
}
