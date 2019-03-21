package com.sdl.selenium.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Version {
    private static List<Integer> versions = Arrays.asList(602, 660, 670);

    public static String greaterThen(String version) {
        String v = version.replaceAll("\\.", "");
        int anInt = Integer.parseInt(v);
        int result = 0;
        for (int i = 0; i < versions.size(); i++) {
            if (versions.get(i) == anInt) {
                result = versions.get(i + 1);
                break;
            }
            if (versions.get(i) > anInt) {
                result = versions.get(i);
                break;
            }
        }
        return returnVersion(result);
    }

    private static String returnVersion(int result) {
        if (result == 602) {
            return "6.0.2";
        } else if (result == 660) {
            return "6.6.0";
        } else if (result == 670) {
            return "6.7.0";
        } else {
            return null;
        }
    }

    public static String lessThan(String version) {
        String v = version.replaceAll("\\.", "");
        int anInt = Integer.parseInt(v);
        int result = 0;
        for (int i = versions.size() - 1; i > -1; i--) {
            if (versions.get(i) == anInt) {
                result = versions.get(i - 1);
                break;
            }
            if (versions.get(i) < anInt) {
                result = versions.get(i);
                break;
            }
        }
        return returnVersion(result);
    }

    public static void main(String[] args) {
        String v = Version.greaterThen("6.0.2");
        log.debug("{}", v);

        String v2 = Version.greaterThen("6.2.0");
        log.debug("{}", v2);

        String v1 = Version.lessThan("6.6.0");
        log.debug("{}", v1);

        String v3 = Version.lessThan("6.7.0");
        log.debug("{}", v3);
    }
}
