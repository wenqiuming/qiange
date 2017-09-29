package xin.charming.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ColorUtils {

    private String darkerGreen = "#928B8B";
    private String lightGreen = "#DADBC0";
    private String orange = "#FFBC2C";
    private String blue = "#A393EB";
    private String pink = "#E8D5B7";
    private String red = "#F38181";
    private String lightBlue = "#6ACAFC";
    private String dakePurple = "#2D5C7F";
    private String deepRed = "#970747";
    private String yellow = "#EFE94B";
    private String gray = "#99F0CA";
    private String dakeGray="#574E6D";
    private String deepBlue="#280B45";
    List<String> colors = new ArrayList<String>();

    {
        colors.add(darkerGreen);
        colors.add(lightBlue);
        colors.add(orange);
        colors.add(blue);
        colors.add(pink);
        colors.add(red);
        colors.add(lightBlue);
        colors.add(dakePurple);
        colors.add(deepRed);
        colors.add(yellow);
        colors.add(gray);
        colors.add(dakeGray);
        colors.add(deepBlue);
    }

    public String getRandom() {
        int size = colors.size();
        int random = (int) (Math.random() * size);
        return colors.get(random);
    }
}
