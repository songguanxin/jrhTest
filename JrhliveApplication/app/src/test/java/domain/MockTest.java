package domain;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * desc:
 * Created by jiarh on 16/12/5 15:13.
 */


public class MockTest {

    List mockedList = mock(List.class);

    @Before
    public void setUp(){

    }

    @Test
    public void testMock(){
        List mockedList = mock(List.class);
        mockedList.add("one");
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.size()+"");
        mockedList.clear();

        verify(mockedList).add("one");
        when(mockedList.get(0)).thenReturn("one");
        System.out.println(mockedList.get(0));
        verify(mockedList).clear();


    }

    @Test
    public void testMockSubbing(){

        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenReturn(new RuntimeException());
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(1));
        verify(mockedList).get(0);


    }

    @Test
    public void testArgumentMatcher(){

        verify(mockedList);
        when(mockedList.get(anyInt())).thenReturn("element");

        verify(mockedList).get(anyInt());
        System.out.println(mockedList.get(1));
        System.out.println(mockedList.get(2));
    }

}
