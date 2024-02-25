package tqs.lab2;

public class Stock {

    private String label;
    private Integer quantity;

    public String getLabel() {
        return label;
    }

    public Stock(String label, Integer quantity) {
        this.label = label;
        this.quantity = quantity;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
