
import java.io.Serializable;
import java.util.Date;

public class WarehouseItem implements Serializable {

    private String productCode;
    private double price; // Giá sản phẩm
    private int quantity;
    private Date manufacturingDate; // Ngày sản xuất
    private Date expirationDate; // Ngày hết hạn

    public WarehouseItem(String productCode, double price, int quantity, Date manufacturingDate, Date expirationDate) {
        this.productCode = productCode;
        this.price = price;
        this.quantity = quantity;
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
    }

    public WarehouseItem(String productCode) {
        this.productCode = productCode;
        this.quantity = 0;
        this.price = 0.0;
        this.manufacturingDate = null;
        this.expirationDate = null;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void displayInfo() {
        System.out.println("Product Code: " + productCode);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + quantity);
        System.out.println("Manufacturing Date: " + manufacturingDate);
        System.out.println("Expiration Date: " + expirationDate);
    }
}
