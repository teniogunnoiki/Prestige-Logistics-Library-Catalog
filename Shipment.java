import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
public class Shipment {
    private int shipmentID;
    private Date shipmentDate;
    private String status;
    private ArrayList<LibraryItem> items;
    private String destination;

    public static final String DEFAULT_STATUS = "Pending";


    public Shipment(){
        this.shipmentID = 0;
        this.status = "Pending";
        this.shipmentDate = new Date();
        this.items = new ArrayList<>();
        this.destination = "Unknown";
    }
    public Shipment(int shipmentID, String status, String destination) {
        this.shipmentID = shipmentID;
        this.shipmentDate = new Date();
        this.status = status;
        this.items = new ArrayList<>();
        this.destination = "Unknown";
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }
    public void deliverShipment() {
        this.status = "Delivered";
    }

    public int getShipmentID() {
        return shipmentID;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public String getStatus() {
        return status;
    }
    public void updateStatus(String status){
        this.status = status;
    }
    public String toString() {
        return "Shipment ID: " + shipmentID +
                ", Date: " + shipmentDate +
                ", Status: " + status +
                ", Items in Shipment: " + items.size();
    }
    public ArrayList<LibraryItem> getItems() {
        return items;
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items in this shipment.");
            return;
        }
        System.out.println("Items in Shipment " + shipmentID + ":");
        for (LibraryItem item : items) {
            System.out.println(item.toString());
        }
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public static Comparator<Shipment> compareByDate = (a, b) -> a.getShipmentDate().compareTo(b.getShipmentDate());
    public static Comparator<Shipment> compareByID = (a, b) -> Integer.compare(a.getShipmentID(), b.getShipmentID());

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Shipment other = (Shipment) obj;
        return this.shipmentID == other.shipmentID;
    }

}
