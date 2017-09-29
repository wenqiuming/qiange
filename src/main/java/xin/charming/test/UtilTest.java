package xin.charming.test;

import org.junit.Test;
import xin.charming.utils.ColorUtils;

public class UtilTest {

    @Test
    public void ColorTest(){
        for (int i=0;i<1000;i++){
            System.out.println(new ColorUtils().getRandom());
        }
    }
}
