import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Agenda {
    private static final int MAX = 5;
    private static final int EXIT = 6;
    private static Set<Person> users = new HashSet<>();

    public static void main(String[] args) throws NullPointerException, ConcurrentModificationException {

        Person u1 = new Person("user1", "09231231");
        Person u2 = new Student("Mihai", "019231231", 2);
        Person u3 = new Pensioner("user3", "parola123", 123153.213);

        users.add(u1);
        users.add(u2);
        users.add(u3);
        Scanner input = new Scanner(System.in);
        int optiune;

        menu();
        System.out.print("Choose a menu option by typing the appropriate index: ");
        optiune = input.nextInt();

        while (optiune != EXIT) {
            System.out.println("You chose: " + optiune);

            switch (optiune) {

                case 1:
                    display();
                    break;
                case 2:
                    search();
                    break;
                case 3:
                    add();
                    break;
                case 4:
                    modify();
                    break;
                case 5:
                    delete();
                    break;
                default:
                    System.out.println(optiune + " does not exist in the menu. Try typing the corresponding number of the option you want to access.");
                    break;
            }

            menu();
            System.out.print("Choose a menu option by typing the appropriate index: ");
            optiune = input.nextInt();
        }

    }

    static void menu() {
        System.out.println("--------------------------------------------------");
        System.out.println("1. Display");
        System.out.println("2. Search");
        System.out.println("3. Add");
        System.out.println("4. Modify");
        System.out.println("5. Delete");
        System.out.println("6. EXIT");
    }

    static void display() {
        System.out.println("--------------------------------------------------");

        for (Person u : users) {
            if (u != null) {
                if (u instanceof Person && !(u instanceof Student) && !(u instanceof Pensioner)) {
                    System.out.println(u.getName() + " " + u.getPhone() + " ");
                }
                if (u instanceof Student) {
                    System.out.println(u.getName() + " " + u.getPhone() + " " + ((Student) u).getYear());
                }
                if (u instanceof Pensioner) {
                    System.out.println(u.getName() + " " + u.getPhone() + " " + ((Pensioner) u).getPension());
                }
            }
        }
    }

    static void search() {
        Scanner input = new Scanner(System.in);
        System.out.print("Type the phone number or name that you want to find: ");
        String searchFor = input.nextLine().toLowerCase();
        boolean found = false;
        int i = -1;

        for (Person u : users) {
            i++;
            if (u == null) {
                continue;
            } else if (u.getName().toLowerCase().contains(searchFor)) {
                System.out.println("The contact name has been fount at the index: " + i + ". " + u.getName());
                found = true;
            } else if (u.getPhone().contains(searchFor)) {
                System.out.println("The phone number has been found at the index: " + i + ". " + u.getPhone());
            }
        }
        if (!found) {
            System.out.println("The name or phone number was not found on the agenda.");
        }
    }

    static void add() {
        Scanner input = new Scanner(System.in);
        System.out.print("What type of contact would you like to add? " +
                "1.Person - " +
                "2.Student - " +
                "3.Pensioner: ");
        int nr = input.nextInt();

        try {
            System.out.print("Type the name for the new contact: ");
            String name = input.next();
            System.out.print("Type the phone number for the new contact: ");
            String phone = input.next();

            int yearM = 0;
            double pensionM = 0;
            boolean full = false;
            int counter = 0;
            Person u = null;

            for (int i = 0; i <= MAX; i++) {
                for (Person k : users) {
                    counter++;
                }
                if (counter < MAX) {
                    switch (nr) {
                        case 1:
                            u = new Person(name, phone);
                            break;
                        case 2:
                            u = new Student(name, phone, yearM);
                            break;
                        case 3:
                            u = new Pensioner(name, phone, pensionM);
                            break;
                        default:
                            System.out.println("Index out of bounds.");
                            break;
                    }
                    if (nr == 2) {
                        System.out.print("Type the year of the student: ");
                        yearM = input.nextInt();
                        ((Student) u).setYear(yearM);
                    } else if (nr == 3) {
                        System.out.print("Type the pension of the pensioner: ");
                        pensionM = input.nextDouble();
                        ((Pensioner) u).setPension(pensionM);

                    }
                    users.add(u);
                    full = true;
                    break;
                }
            }

            if (!full) System.out.println("I'm sorry, the agenda is already full..");
        } catch (NullPointerException e) {
            System.out.println("There was an error. Retrying...");
            add();
        }
    }

    static void modify() {
        Scanner input = new Scanner(System.in);
        System.out.print("Type the appropriate index of the name or phone number you want to modify. Or type '-1' to go back to the menu. ");
        int index = input.nextInt();
        int temp = -1;

        if (index >= MAX) {
            System.out.println("You have exceeded the size of the agenda.");
            modify();
        } else if (index >= 0 && index < MAX) {
            for (Person u : users) {
                temp++;
                if (temp == index) {

                    System.out.print("Type the new name of the contact: ");
                    String name = input.next();
                    u.setName(name);
                    System.out.print("Type the new phone number of the contact: ");
                    String phone = input.next();
                    u.setPhone(phone);
                    if (u instanceof Student) {
                        System.out.print("Type the new year of the student: ");
                        int yearM = input.nextInt();
                        ((Student) u).setYear(yearM);
                    } else if (u instanceof Pensioner) {
                        System.out.print("Type the new pension of the student: ");
                        double pensionM = input.nextDouble();
                        ((Pensioner) u).setPension(pensionM);
                    }
                    System.out.println("The contact has been modified.");
                }
            }
        } else if (index == -1) {
        }
    }

    static void delete() throws ConcurrentModificationException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the appropriate index of the contact you want to delete. If you want to go back to the menu, type '-1'. ");
        int index = input.nextInt();
        int temp = 0;
        if (index == -1) {

        } else {
            try {
                for (Person u : users) {
                    temp++;
                    if (temp == index) {
                        if (u == null) {
                            System.out.println("Index value selected already has the value 'null'.");
                        } else {
                            users.remove(u);
                            System.out.println("Contact deleted.");
                        }
                    }
                }
            } catch(ConcurrentModificationException e){

            }
        }
    }
}



