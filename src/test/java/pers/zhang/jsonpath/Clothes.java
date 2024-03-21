package pers.zhang.jsonpath;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/27 10:57 下午
 * @Version 1.0
 */
public class Clothes {

    private String name;
    private Double price;
    private Object sizes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Object getSizes() {
        return sizes;
    }

    public void setSizes(Object sizes) {
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", sizes=" + sizes +
                '}';
    }
}
