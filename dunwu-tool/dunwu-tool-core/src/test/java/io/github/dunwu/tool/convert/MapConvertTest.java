package io.github.dunwu.tool.convert;

import io.github.dunwu.tool.map.MapBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map转换单元测试
 *
 * @author looly
 */
public class MapConvertTest {

    @Test
    public void beanToMapTest() {
        User user = new User();
        user.setName("AAA");
        user.setAge(45);

        HashMap<?, ?> map = Convert.convert(HashMap.class, user);
        Assertions.assertEquals("AAA", map.get("name"));
        Assertions.assertEquals(45, map.get("age"));
    }

    @Test
    public void mapToMapTest() {
        Map<String, Object> srcMap =
            MapBuilder.create(new HashMap<String, Object>()).put("name", "AAA").put("age", 45).map();

        LinkedHashMap<?, ?> map = Convert.convert(LinkedHashMap.class, srcMap);
        Assertions.assertEquals("AAA", map.get("name"));
        Assertions.assertEquals(45, map.get("age"));
    }

    public static class User {

        private String name;

        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }

}
