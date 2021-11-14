package test.basic;

public class Ecar {

    private String brand;
    private int price;
    private int mile;

    public Ecar() {

    }

    public Ecar(String brand, int price, int mile) {
        this.brand = brand;
        this.price = price;
        this.mile = mile;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMile() {
        return mile;
    }

    public void setMile(int mile) {
        this.mile = mile;
    }
}
