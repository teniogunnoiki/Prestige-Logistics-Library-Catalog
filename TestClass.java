import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;

public class TestClass {

    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        // Controls main program loop
        boolean session = true;

        // Create library catalog in admin mode
        LibraryCatalog<LibraryItem> library = new LibraryCatalog<>("Admin Mode");
        Stack<Action> undoStack = new Stack<>();
        Warehouse<Shipment> warehouse = new Warehouse<>();
        AVLTree<LibraryItem> libraryItemAVLTree = new AVLTree<>();

        // Add initial book items to catalog
        library.addItem(new Book(1001, "Five Dialogues", "Plato", "2002", "Physical"));
        library.addItem(new Book(1005, "Introduction to Programming", "Daniel Liang", "1998", "Digital"));
        library.addItem(new Book(1003, "Modern Operating Systems", "Andrew S. Tanenbaum", "1992", "Digital"));
        library.addItem(new Book(1407, "The Essential Bible Companion", "John H Walton", "2006", "Digital"));
        library.addItem(new Book(1009, "Nicomachean Ethics", "Aristotle", "2009", "Physical"));

        // Add initial video items to catalog
        library.addItem(new Video(1004, "Harriet", 125));
        library.addItem(new Video(1011, "Microsoft Excel Training 2017", 55));
        library.addItem(new Video(1006, "Hidden Figures", 127));
        library.addItem(new Video(1308, "Charlotte's Web", 97));
        library.addItem(new Video(1010, "Harry Potter and the Sorcerer's Stone", 152));

        //add initial items to AVL Tree

        for (LibraryItem item : library.getAllItems()) {
            libraryItemAVLTree.insert(item);
        }

        // Main menu loop
        while (session) {

            // Display menu options
            System.out.println("");
            System.out.println("Welcome to Prestige University Library Catalog!");
            System.out.println("Main Menu: ");
            System.out.println("1. Display current library");
            System.out.println("2. Add item");
            System.out.println("3. Remove item");
            System.out.println("4. Create or add to a Shipment");
            System.out.println("5. Remove Shipment");
            System.out.println("6. View Shipments");
            System.out.println("7. Undo Last Action");
            System.out.println("8. Search for an Item or Shipment");
            System.out.println("9. View Performance");
            System.out.println("10. AVL Tree for Library");
            System.out.println("11. HashMap for Shipment");
            System.out.println("0. Exit");
            System.out.print("Enter menu option: ");

            int choice = s1.nextInt();
            s1.nextLine();

            // Option 1: Display and sort library
            if (choice == 1) {
                System.out.println("Displaying all Items as entered: \n");
                library.displayLibrary();

                System.out.println("\nDisplaying by type: ");
                library.sortLibrary();
                for (LibraryItem item : library.getAllItems()) {
                    if (item instanceof Book) {
                        System.out.println(item.getDetails());
                    }
                    if (item instanceof Video) {
                        System.out.println(item.getDetails());
                    }
                }
            }
            // Option 2: Add new item (Book or Video)
            else if (choice == 2) {
                System.out.println("");
                System.out.println("1. Book");
                System.out.println("2. Video");

                int choice1 = s1.nextInt();
                s1.nextLine(); // Clear buffer

                // Add Book
                if (choice1 == 1) {
                    int lastIndex = 1000 + library.size() + 1; // Generate new ID

                    System.out.println("Title of Book: ");
                    String title = s1.nextLine();

                    System.out.println("Name of Author: ");
                    String author = s1.nextLine();

                    System.out.println("Publish Date of Book: ");
                    String publishDate = s1.nextLine();

                    System.out.println("Digital or Physical: ");
                    String category = s1.nextLine();

                    Book newBook = new Book(lastIndex++, title, author, publishDate, category);
                    library.addItem(newBook);
                    libraryItemAVLTree.insert(newBook); // add to AVL

                    undoStack.push(new Action("ADD_ITEM", lastIndex - 1));
                    System.out.println("\nBook Added!");
                }

                // Add Video
                else if (choice1 == 2) {
                    int lastIndex = 1000 + library.size() - 1; // Generate new ID

                    System.out.println("Title of Video: ");
                    String title = s1.nextLine();

                    System.out.println("Duration of Video: ");
                    double duration = s1.nextDouble();

                    Video newVid = new Video(lastIndex++, title, duration);
                    library.addItem(newVid);
                    libraryItemAVLTree.insert(newVid);

                    undoStack.push(new Action("ADD_ITEM", lastIndex - 1));
                    System.out.println("\nVideo Added!");
                }
            }
            // Option 3: Remove item by ID
            else if (choice == 3) {
                System.out.println("");
                library.displayLibrary(); //displays library to see items

                System.out.println("\nWhich Item would you like to remove by ID: ");
                int item = s1.nextInt();
                s1.nextLine(); // Clear buffer
                //remove from library Catalog
                library.findAndRemoveItem(item);

                undoStack.push(new Action("REMOVE_ITEM", item));// Remove selected item

            }
            // Option 4: Create shipment and add item to it
            else if (choice == 4) {
                System.out.println("Create Shipment or Add item to existing Shipment");
                System.out.println("1. Create Shipment");
                System.out.println("2. Add Item to Existing Shipment");
                System.out.println("0. Exit");
                int choice1 = s1.nextInt();

                System.out.print("");
                if (choice1 == 1) {


                    // Get shipment details
                    System.out.print("Enter new Shipment ID: ");
                    int newShipmentID = s1.nextInt();
                    s1.nextLine();

                    System.out.print("Enter destination: ");
                    String destination = s1.nextLine();

                    Shipment newShipment = new Shipment(newShipmentID, Shipment.DEFAULT_STATUS, destination);
                    library.createShipment(newShipment);
                    System.out.println("Shipment created successfully!");
                    // Add item to shipment
                    library.displayLibrary();
                    System.out.print("Enter Item ID to add: ");
                    int itemID = s1.nextInt();
                    s1.nextLine();
                    library.addItemToShipment(itemID, newShipmentID);
                    warehouse.addShipment(newShipment);
                    undoStack.push(new Action("ADD_TO_SHIPMENT", itemID));

                } else if (choice1 == 2) {

                    library.displayShipments();
                    System.out.println("Enter Shipment ID - ");
                    int shipmentID = s1.nextInt();
                    library.displayLibrary();
                    System.out.println("Enter Item ID to add to shipment - ");
                    int itemID = s1.nextInt();
                    library.addItemToShipment(itemID, shipmentID);
                    System.out.println("Item added to Shipment:" + shipmentID);
                    undoStack.push(new Action("ADD_TO_SHIPMENT", itemID));

                } else if (choice1 == 0) {
                    session = false;

                }
            }
            //Option 5: Delete shipment
            else if (choice == 5) {
                System.out.print("Enter Shipment ID to remove: ");
                int shipID = s1.nextInt();
                s1.nextLine();
                Shipment removed = library.removeShipment(shipID);
                if(removed != null){
                    undoStack.push(new Action("REMOVE_SHIPMENT", removed));
                }

            }
            // Option 6: View all shipments
            else if (choice == 6) {

                System.out.println("What do you want to view? ");
                System.out.println("1. View Library");
                System.out.println("2. View Shipments");
                System.out.println("3. View Items in Shipments");
                int choice2 = s1.nextInt();

                if (choice2 == 1)
                    library.displayLibrary();

                else if (choice2 == 2)
                    library.displayShipments();

                else if (choice2 == 3) {
                    library.displayShipments(); // show them the list first
                    System.out.print("Enter Shipment ID to view items: ");
                    int shipmentID = s1.nextInt();
                    library.displayItemsInShipment(shipmentID);
                }


            }
            //Option 7: undo item to shipment or catalog
            else if (choice == 7) {
                if (undoStack.isEmpty()) {
                    System.out.println("Nothing to undo.");
                    return;
                }
                Action lastAction = undoStack.pop();
                switch (lastAction.getType()) {
                    case "ADD_ITEM":
                        int idToRemove = (int) lastAction.getData();
                        library.findAndRemoveItem(idToRemove);
                        System.out.println("Undo: Item addition reversed.");
                        break;
                    case "REMOVE_ITEM":
                        LibraryItem item = (LibraryItem) lastAction.getData();
                        library.addItem(item);
                        System.out.println("Undo: Item removal reversed.");
                        break;

                    case "ADD_TO_SHIPMENT":
                        int itemID = (int) lastAction.getData();
                        library.findAndRemoveItem(itemID);
                        System.out.println("Undo: Removed item from shipment.");
                        break;
                    case "REMOVE_SHIPMENT":
                        Shipment shipID = (Shipment) lastAction.getData();
                        library.createShipment(shipID);
                        System.out.println("Undo: Shipment restored.");
                        break;

                    default:
                        System.out.println("Unknown action.");
                }
            }
            //option 8: searching and sorting
            else if (choice == 8) {
                System.out.println("Displaying all Items as entered:");
                library.displayLibrary();

                System.out.println("\nSorting Alphabetically using merge sort");
                List<LibraryItem> sortedItems = SortUtils.mergeSort(library.getAllItems(), SortUtils.compareByTitle);
                for (LibraryItem item : sortedItems) {
                    System.out.println(item.getDetails());
                }

                System.out.println("\nSorting by ID using quick sort");
                SortUtils.quickSort(library.getAllItems(), 0, library.getAllItems().size() - 1, SortUtils.compareByID);
                for (LibraryItem item : library.getAllItems()) {
                    System.out.println(item.getDetails());
                }


                SortUtils.quickSort(library.getAllItems(), 0, library.getAllItems().size() - 1, SortUtils.compareByID);
                System.out.print("Are you searching for (1) book or (2)video? Enter an integer: ");
                int ans = s1.nextInt();
                s1.nextLine();
                ArrayList<LibraryItem> filter = new ArrayList<>();
                if (ans == 1) {
                    for (LibraryItem item : library.getAllItems()) {
                        if (item instanceof Book) {
                            filter.add(item);
                        }
                    }
                } else if (ans == 2) {
                    for (LibraryItem item : library.getAllItems()) {
                        if (item instanceof Video) {
                            filter.add(item);
                        }
                    }
                }
                System.out.println("Enter the ID of the Item: ");
                ans = s1.nextInt();
                s1.nextLine();

                int index = SortUtils.binarySearch(filter, ans);
                if (index != -1) {
                    System.out.println("Found: " + filter.get(index));
                } else {
                    System.out.println("Item not found.");
                }
            }

            //Option 9: performance testing
            else if (choice == 9) {
                //generates large dataset of book objects
                ArrayList<LibraryItem> test = new ArrayList<>();
                for (int i = 0; i < 10100; i++) {
                    test.add(new Book(i, "Title" + i, "Author", "2026", "Digital"));
                }
                //measuring custom merge sort
                long start = System.nanoTime();
                ArrayList<LibraryItem> sorted = SortUtils.mergeSort(test, SortUtils.compareByID);
                long end = System.nanoTime();
                System.out.println("Merge Sort Time: " + ((end - start)/1000000.0) + " ms");

                //measuring custom quick sort
                long start2 = System.nanoTime();
                SortUtils.quickSort(library.getAllItems(), 0, library.getAllItems().size() - 1, SortUtils.compareByID);
                long end2 = System.nanoTime();
                System.out.println("Quick Sort Time: " + ((end2 - start2)/1000000.0) + " ms");

                //measuring java's built-in sort
                long start3 =  System.nanoTime();
                test.sort(SortUtils.compareByID);
                long end3 =  System.nanoTime();
                System.out.println("Java Sort Time: " + ((end3- start3)/1000000.0) + " ms");

            }
            else if (choice == 10) {
                System.out.println("AVL Tree In-order traversal Sorted by Type, then ID: ");
                libraryItemAVLTree.inorder();
                System.out.print("Enter Item ID to search: ");
                int id = s1.nextInt();
                s1.nextLine();

                // Create dummy object (only ID matters)
                LibraryItem dummy = new Book(id, "", "", "", "");

                boolean found = libraryItemAVLTree.search(dummy);

                if (found) {
                    System.out.println("Found: " + found);

                } else {
                    System.out.println("Item not found.");
                }

            }

            // Option 0: Exit program
            else if (choice == 0) {
                session = false;
            }
        }
    }
}
