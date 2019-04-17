package sample.Domain;

public class Invoice extends Entity {

    private double sum;
    private String description;
    private String date;

    public Invoice(String id, double sum, String description, String date) {
        super(id);
        this.sum = sum;
        this.description = description;
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "sum=" + sum +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
