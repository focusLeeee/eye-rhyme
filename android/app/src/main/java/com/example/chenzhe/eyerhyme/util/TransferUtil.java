package com.example.chenzhe.eyerhyme.util;

/**
 * Created by chenzhe on 2016/5/10.
 */
public class TransferUtil {
    public static String transferFilmType(int type) {

        switch (type) {
            case 0:
                return "动作";
            case 1:
                return "喜剧";
            case 2:
                return "爱情";
            case 3:
                return "科幻";
            case 4:
                return "奇幻";
            case 5:
                return "灾难";
            case 6:
                return "恐怖";
            case 7:
                return "纪录";
            case 8:
                return "犯罪";
            case 9:
                return "战争";
            case 10:
                return "冒险";
            case 11:
                return "动画";
            case 12:
                return "剧情";
            case 13:
                return "其它";
        }
        return "";
    }

    public static String transferProductType(int type) {
        switch (type) {
            case 0:
                return "英文/2D";
            case 1:
                return "英文/3D";
            case 2:
                return "英文/IMAX2D";
            case 3:
                return "英文/IMAX3D";
            case 4:
                return "中文/2D";
            case 5:
                return "中文/3D";
            case 6:
                return "中文/IMAX2D";
            case 7:
                return "中文/IMAX3D";

        }
        return "";
    }

    public static String transferRound(int round) {
        switch (round) {
            case 1:
                return "10:00";
            case 2:
                return "13:00";
            case 3:
                return "16:00";
            case 4:
                return "19:00";
            case 5:
                return "22:00";
        }
        return "";
    }


}
