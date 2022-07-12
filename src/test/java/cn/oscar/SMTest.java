package cn.oscar;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

//@ExtendWith(MockitoExtension.class)
public class SMTest {


    @BeforeClass
    public void test() {
        MockitoAnnotations.initMocks(this);
    }


    @Mock
    private ArrayList mockedList;

    private List aa;

    @Test
    public void mockList() {
        aa =new ArrayList();
        aa.add("add test");
        System.out.println(aa.get(0));
//        mockedList.add("one");
        when(mockedList.get(0)).thenReturn("one");
//        verify(mockedList).add("one");
        System.out.println(mockedList.get(0));
        Assert.assertEquals(mockedList.get(0),"one");

    }

    @Test
    public void stubMultipleResult() {
        when(mockedList.get(anyInt())).thenReturn("first get").thenReturn("more than once get");

        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(0));
    }

    private int anyInt() {
        return 0;
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
