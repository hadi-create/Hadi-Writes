import java.util.*;

class Challan {
    private String challanId;
    private String vehicleNumber;
    private double fineAmount;
    private boolean isPaid;

    public Challan(String challanId, String vehicleNumber, double fineAmount) {
        this.challanId = challanId;
        this.vehicleNumber = vehicleNumber;
        this.fineAmount = fineAmount;
        this.isPaid = false;
    }

    public String getChallanId() {
        return challanId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void payChallan() {
        this.isPaid = true;
    }

    public void undoPayment() {
        this.isPaid = false;
    }

    @Override
    public String toString() {
        return "Challan ID: " + challanId + ", Vehicle Number: " + vehicleNumber +
                ", Fine Amount: " + fineAmount + ", Status: " + (isPaid ? "Paid" : "Unpaid");
    }
}

class LinkedListChallan {
    class Node {
        Challan data;
        Node next;

        Node(Challan data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    public LinkedListChallan() {
        this.head = null;
    }

    public void addChallan(Challan challan) {
        Node newNode = new Node(challan);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void viewAllChallans() {
        if (head == null) {
            System.out.println("No challans available.");
            return;
        }
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    public Challan getChallanById(String challanId) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.getChallanId().equals(challanId)) {
                return temp.data;
            }
            temp = temp.next;
        }
        return null;
    }

    public void sortChallansByFineAmount() {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            Node temp = head;
            while (temp != null && temp.next != null) {
                if (temp.data.getFineAmount() > temp.next.data.getFineAmount()) {
                    // Swap data
                    Challan tempData = temp.data;
                    temp.data = temp.next.data;
                    temp.next.data = tempData;
                    swapped = true;
                }
                temp = temp.next;
            }
        } while (swapped);
    }
}

class EChallanSystem {
    private static LinkedListChallan challanList = new LinkedListChallan();
    private static Stack<Challan> paymentStack = new Stack<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- E-Challan System ---");
            System.out.println("1. Add Challan");
            System.out.println("2. View All Challans");
            System.out.println("3. Pay Challan");
            System.out.println("4. Undo Last Payment");
            System.out.println("5. Sort Challans by Fine Amount");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addChallan(scanner);
                    break;
                case 2:
                    viewAllChallans();
                    break;
                case 3:
                    payChallan(scanner);
                    break;
                case 4:
                    undoLastPayment();
                    break;
                case 5:
                    sortChallans();
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addChallan(Scanner scanner) {
        System.out.print("Enter Challan ID: ");
        String challanId = scanner.nextLine();
        System.out.print("Enter Vehicle Number: ");
        String vehicleNumber = scanner.nextLine();
        System.out.print("Enter Fine Amount: ");

        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input! Please enter a valid fine amount.");
            scanner.next();
        }
        double fineAmount = scanner.nextDouble();
        scanner.nextLine();

        Challan newChallan = new Challan(challanId, vehicleNumber, fineAmount);
        challanList.addChallan(newChallan);
        System.out.println("Challan added successfully.");
    }

    private static void viewAllChallans() {
        System.out.println("\n--- List of Challans ---");
        challanList.viewAllChallans();
    }

    private static void payChallan(Scanner scanner) {
        System.out.print("Enter Challan ID to pay: ");
        String challanId = scanner.nextLine();

        Challan challan = challanList.getChallanById(challanId);
        if (challan != null) {
            if (!challan.isPaid()) {
                challan.payChallan();
                paymentStack.push(challan);
                System.out.println("Challan paid successfully.");
            } else {
                System.out.println("This challan is already paid.");
            }
        } else {
            System.out.println("Challan ID not found.");
        }
    }

    private static void undoLastPayment() {
        if (paymentStack.isEmpty()) {
            System.out.println("No payments to undo.");
            return;
        }

        Challan lastPaidChallan = paymentStack.pop();
        lastPaidChallan.undoPayment();
        System.out.println("Last payment has been undone.");
    }

    private static void sortChallans() {
        System.out.println("Sorting challans by fine amount...");
        challanList.sortChallansByFineAmount();
        viewAllChallans();
    }
}

