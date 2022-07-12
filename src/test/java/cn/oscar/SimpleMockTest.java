package cn.oscar;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SimpleMockTest {

    @Mock
    public List mockedList1;

    @BeforeClass
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSimple() {

        //创建mock对象，参数可以是类，也可以是接口
        List<String> list = mock(List.class);

        //设置方法的预期返回值
        when(list.get(0)).thenReturn("hello mock1");

        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        verify(list).get(0);

        //测试
        Assert.assertEquals("hello mock", result);
    }

    @Test
    public void testMockStubbing() {
        // You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);


        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

//following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

//exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

//verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

//verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
//        verify(mockedList, atLeast(2)).add("five times");
//        verify(mockedList, atMost(5)).add("three times");

        // stubbing
        when(mockedList.get(0)).thenReturn("first");

        when(mockedList.get(1)).thenThrow(new RuntimeException());

        // 连续stubbing
        when(mockedList.get(0)).thenReturn("first").thenReturn("second").thenReturn("Third");

        // following prints "first"
        System.out.println(mockedList.get(0));

        // following throws runtime exception
        //System.out.println(mockedList.get(1));

        // following prints "null" because get(999) was not stubbed
        // 默认情况下，对于所有有返回值且没有stub过的方法，mockito会返回相应的默认值。
        // 对于内置类型会返回默认值，如int会返回0，布尔值返回false。对于其他type会返回null。
        System.out.println(mockedList.get(999));

        // Although it is possible to verify a stubbed invocation, usually it's just redundant
        // If your code cares what get(0) returns, then something else breaks (often even before verify() gets
        // executed).
        // If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);
////        verify(mockedList).get(1);
//
//        RuntimeException exception= (RuntimeException) mockedList.get(1);
//        Assert.assertEquals(exception,new RuntimeException());


        String result = (String) mockedList.get(0);
//        Assert.assertEquals(result,"second");
        // 重复stub两次,则以第二次为准。如下将返回"second"：
        when(mockedList.get(0)).thenReturn("first");

        when(mockedList.get(0)).thenReturn("second");
        when(mockedList.get(0)).thenReturn("Third");
        result = (String) mockedList.get(0);
        Assert.assertEquals(result, "Third1");

        // 如果是下面这种形式，则表示第一次调用时返回“first”，第二次调用时返回“second”。可以写n多个.
        when(mockedList.get(0)).thenReturn("first").thenReturn("second");
        // 但是，如果实际调用的次数超过了stub过的次数，则会一直返回最后一次stub的值。
        // 如上例，第三次调用get(0)时，则会返回"second".
    }

    @Test
    public void mockList() {
        List mockedList = mock(List.class);
        when(mockedList.get(anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                System.out.println("哈哈哈，被我逮到了吧");
                Object[] arguments = invocationOnMock.getArguments();
                System.out.println("参数为:" + Arrays.toString(arguments));
                Method method = invocationOnMock.getMethod();
                System.out.println("方法名为:" + method.getName());

                return "结果由我决定";
            }
        });

        System.out.println(mockedList.get(0));
    }


    @Test
    public void mockList1() {
        mockedList1.add("one");
        verify(mockedList1).add("one");
    }


    @Test
    public void spyList() {
        //申请了一个真实的对象
        List list = new LinkedList();
        List spy = spy(list);

        //可以选择存根某些函数
        when(spy.size()).thenReturn(100);

        //调用真实的方法
        spy.add("one");
        spy.add("two");

        //打印第一个元素
        System.out.println(spy.get(0));

        //获取list的大小
        System.out.println(spy.size());

        //验证
        verify(spy).add("one");
        verify(spy).add("two");
    }


}
