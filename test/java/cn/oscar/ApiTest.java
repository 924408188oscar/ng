package cn.oscar;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Test
public class ApiTest {
    Ttt ttt=new Ttt();

    @Test
    public void testAdd(){
        System.out.println("开始测试");

        Reporter.log("正在执行测试add",true);
        int result=ttt.add(1,2);

        Assert.assertEquals(result,3);
    }


    @Test(dataProvider = "datas")
    public void testsubtract(int a,int b,int expected){
        Reporter.log("正在执行测试subtract",true);
        int result=ttt.subtract(a,b);
        Assert.assertEquals(result,expected);

    }


    @Parameters({"db"})
    @Test
    public void testDemo(@Optional("mysql") String db) {
        System.out.println("使用的数据库是：" + db);
    }

    @DataProvider(name = "datas")
    public Object[][] params() {
        return new Object[][]{
                //可以写多个值，没有限制
                {1, 23,-22},
                {123456,123456,0},
                {-134354,3452345,-24534554}
        };
    }

}
