package cn.oscar;

import cn.commom.Utils;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

@PrepareForTest(Utils.class) //PowerMock表态方法
public class PowerMockSampleTest extends PowerMockTestCase {

//    @Mock
//    public Utils utils;

    @BeforeClass
    public void test() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testPrintUUID() throws Exception {
        PowerMockito.mockStatic(Utils.class);
        List<String> list = mock(List.class);

//        Utils utils = mock(Utils.class);
        PowerMockito.when(Utils.generateNewUUId()).thenReturn("FAKE UUID");

        Assert.assertEquals(Utils.generateNewUUId(), "FAKE UUID");

    }

}

