package com.chan.util;

import java.io.FileInputStream;
import java.lang.management.OperatingSystemMXBean;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @author zsljava 2017年12月13日 下午2:16:49
 * @since 1.0.0
 */
public class YmlUtil {
    private static Map<String, Object> ymlMap = null;

    static {
        ymlMap = new HashMap<>();
        getApplicationYml();
    }

    /**
     *
     */
    public YmlUtil() {
        super();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getApplicationYml() {
        try {
            Yaml yaml = new Yaml();
            URL url = ClassLoader.getSystemResource("application.yml");
            if (url != null) {
                Map<String, Object> map = yaml.loadAs(new FileInputStream(url.getFile()), Map.class);
                switchToMap(null, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ymlMap;
    }

    @SuppressWarnings("unchecked")
    private static void switchToMap(String myKey, Map<String, Object> map) {
        Iterator<String> it = map.keySet().iterator();
        myKey = myKey == null ? "" : myKey;
        String tmpkey = myKey;
        while (it.hasNext()) {
            String key = it.next();
            myKey = tmpkey + key;
            Object value = map.get(key);
            if (value instanceof Map) {
                switchToMap(myKey.concat("."), (Map<String, Object>) value);
            } else {
                if (null != value) {
                    ymlMap.put(myKey, value);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) ymlMap.get(key);
    }

    public static String getStr(String key) {
        return String.valueOf(ymlMap.get(key));
    }

}