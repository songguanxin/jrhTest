package mockito;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * desc:
 * Created by jiarh on 17/2/28 14:45.
 */

public class Demo1 {

    @Mock
    private List mockList;

    public Demo1() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMock(){

        List mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();


    }

    @Test
    public void test2(){

        List mockedList = mock(List.class);

        int a = anyInt();
        when(mockedList.get(a)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        System.out.println(mockedList.get(a));

        System.out.println(mockedList.get(1));

        System.out.println(mockedList.get(999));

        verify(mockedList).get(a);

    }




    @Test
    public void test3(){

        Iterator iterator = mock(Iterator.class);
        when(iterator.next()).thenReturn("hello").thenReturn("world");

        String result = iterator.next()+" "+iterator.next()+" "+iterator.next();
        System.out.println(result);
    }

    @Test
    public void testArguments(){

        Comparable compareable = mock(Comparable.class);
        when(compareable.compareTo("a")).thenReturn(1);
        when(compareable.compareTo("b")).thenReturn(2);

//        Assert.assertEquals(1,compareable.compareTo("a"));
//        Assert.assertEquals(2,compareable.compareTo("b"));
        assertEquals(0,compareable.compareTo("b"));

    }

    @Test
    public void testTimes(){

        List list = mock(List.class);
        list.add(1);
        list.add(12);
        list.add(12);
        list.add(13);
        list.add(13);
        list.add(13);
        verify(list).add(1);
        /**验证调用次数 */
        verify(list,times(1)).add(1);
        verify(list,times(2)).add(12);
        verify(list,times(3)).add(13);
        /**从未被调用过 */
        verify(list,never()).add(4);
        /** 至少调用过一次*/
        verify(list,atLeastOnce()).add(1);

        verify(list,atLeast(2)).add(12);

        verify(list,atMost(3)).add(13);
    }


    @Test
    public void testThowException(){

        List list = mock(List.class);
        doThrow(new RuntimeException()).when(list).add(1);

        list.add(1);
    }

    @Test
    public void testOrder(){

        List list1 = mock(List.class);
        List list2 = mock(List.class);

        list1.add(1);
        list2.add("hello");
        list1.add(2);
        list2.add("world");

        InOrder order = inOrder(list1,list2);
        order.verify(list1).add(1);
        order.verify(list2).add("hello");
        order.verify(list1).add(2);
        order.verify(list2).add("world");

    }

    @Test
    public void testZero(){

        List list1 = mock(List.class);
        List list2 = mock(List.class);
        List list3 = mock(List.class);

        list1.add(1);

        verify(list1).add(1);
        verify(list1,never()).add(11);

        verifyZeroInteractions(list2,list3);
    }

    @Test
    public void testRedunant(){

        List list  =mock(List.class);
        list.add(1);
        list.add(2);
        verify(list,times(2)).add(anyInt());
        /** 检查行为是否都被验证过了*/
        verifyNoMoreInteractions(list);

        List list2 = mock(List.class);
        list2.add(2);
        list2.add(1);
        verify(list2).add(1);
        verify(list2).add(2);

        verifyNoMoreInteractions(list2);
    }

    @Test
    public void testAnnotation(){

        mockList.add(1);
        verify(mockList).add(1);
    }

    @Test
    public void testAnswer(){

        when(mockList.get(anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();

                return "hello world:"+args[0];
            }
        });

        assertEquals("hello world:0",mockList.get(0));
        assertEquals("hello world:999",mockList.get(0));

    }

    @Test
    public void testReal(){



    }

    @Test
    public void testStubbed(){

        List mock = mock(List.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return 999;
            }
        });

        assertEquals(999,mock.get(1));

        assertEquals(999,mock.size());

    }

    @Test
    public void testSpy(){

        List list = spy(new ArrayList());

        assertEquals(0,list.size());

        A a = mock(A.class);
        when(a.doSomething(anyInt())).thenCallRealMethod();
        assertEquals(999,a.doSomething(999));

    }

    class A{
        public int doSomething(int i){
            return i;
        }
    }

    @Test
    public void testReset(){

        List list = mock(List.class);
        when(list.size()).thenReturn(10);
        list.add(1);
        assertEquals(10,list.size());
        reset(list);
        assertEquals(0,list.size());

    }
}
