package pers.zhang.fastjson;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

/**
 * @Author: acton_zhang
 * @Date: 2024/2/3 11:42 下午
 * @Version 1.0
 */
public class Demo2 {


    class Address {
        String street;
        String city;
        Integer zip;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getZip() {
            return zip;
        }

        public void setZip(Integer zip) {
            this.zip = zip;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", city='" + city + '\'' +
                    ", zip=" + zip +
                    '}';
        }
    }

    @Test
    public void test() {
        String jsonStr = "{\"name\":\"John\",\"age\":30,\"address\":{\"street\":\"123 Walnut St\",\"city\":\"New York\",\"zip\":10001}}";
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Type type = new TypeReference<Address>() {}.getType();
        Address address = jsonObject.getObject("address", type);
        System.out.println(address);
    }
}
