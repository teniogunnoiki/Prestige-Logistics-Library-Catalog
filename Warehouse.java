import java.util.ArrayList;
import java.util.List;

public class Warehouse<T> {

    private int warehouseID;
    private String location;
    private List<T> items;

    public Warehouse() {
        this.warehouseID = 0;
        this.location = "Unknown";
        this.items = new ArrayList<>();
    }

    public Warehouse(int warehouseID, String location) {
        this.warehouseID = warehouseID;
        this.location = location;
        this.items = new ArrayList<>();
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public String getLocation() {
        return location;
    }

    public void addShipment(T item) {
        items.add(item);
    }

    public T findShipment(int index) {
        return items.get(index);
    }

    public List<T> getShipments() {
        return items;
    }
}