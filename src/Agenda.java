import java.io.*;
import java.util.*;

public class Agenda {
    private static final int EXIT = 6;
    private static Set<Person> users = new LinkedHashSet<>();

    public static void main(String[] args) throws NullPointerException, ConcurrentModificationException, IOException, FileNotFoundException {

//        Person u1 = new Person("user1", "09231231");
//        Person u2 = new Student("Mihai", "019231231", 2);
//        Person u3 = new Pensioner("user3", "parola123", 123153.213);
//
//        users.add(u1);
//        users.add(u2);
//        users.add(u3);
        try {
            File f = new File("database.txt");
            updateFromDatabase(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    static void display() throws IOException {
        File f = new File("database.txt");
        updateFromDatabase(f);
        System.out.println("--------------------------------------------------");
            read(f);

            //debug feature
        System.out.println("users in database.txt ^^^");
        System.out.println("separator");
        System.out.println("users inside LinkedHashSet vvv");
        for(Person u: users){
            System.out.println(u.toString());
        }
    }

    static void search() throws IOException{
        File f = new File("database.txt");
            updateFromDatabase(f);
        Scanner input = new Scanner(System.in);
        System.out.print("Type the phone number or name that you want to find: ");
        String searchFor = input.nextLine().toLowerCase();
        boolean found = false;
        int temp = 0;

        for (Person u : users) {
            temp++;
            if (u == null) {
                continue;
            } else if (u.getName().toLowerCase().contains(searchFor)) {
                System.out.println("The contact name has been fount at the index: " + temp + ". " + u.getName());
                found = true;
            } else if (u.getPhone().contains(searchFor)) {
                System.out.println("The phone number has been found at the index: " + temp + ". " + u.getPhone());
                found = true;
            }
        }
        if (!found) {
            System.out.println("The name or phone number was not found on the agenda.");
        }
    }

    static void add() throws IOException{
        File f = new File("database.txt");
            updateFromDatabase(f);
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
            Person u = null;


            switch (nr) {
                        case 1:
                            u = new Person(name, phone);
                            break;
                        case 2:
                            u = new Student(name, phone, yearM);
                            System.out.print("Type the year of the student: ");
                            yearM = input.nextInt();
                            ((Student) u).setYear(yearM);
                            break;
                        case 3:
                            u = new Pensioner(name, phone, pensionM);
                            System.out.print("Type the pension of the pensioner: ");
                            pensionM = input.nextDouble();
                            ((Pensioner) u).setPension(pensionM);
                            break;
                        default:
                            System.out.println("Index out of bounds.");
                            break;
            }
            users.add(u);
            try{
                BufferedReader br = new BufferedReader(new FileReader(f));
                        write(u.toString(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            System.err.println("There was an error. Retrying...");
            add();
        }
    }

    static void modify() throws IOException {
        File f = new File("database.txt");
            updateFromDatabase(f);

        Scanner input = new Scanner(System.in);
        System.out.print("Type the appropriate index of the name or phone number you want to modify. Or type '-1' to go back to the menu. ");
        int index = input.nextInt();
        int temp = 0;

        if (index == -1) {
        } else {
            for (Person u : users) {
                temp++;
                if (temp == index) {
                    String lineToModify = u.toString();
                    System.out.println("user to modify "+u.toString());
                    System.out.print("Type the new name of the contact or type -1 to leave it as it is: ");
                    String name = input.next();
                    if (!name.contains("-1")) {
                        u.setName(name);
                    }

                    System.out.print("Type the new phone number of the contact or type -1 to leave it as it is: ");
                    String phone = input.next();
                    if (!phone.contains("-1")) {
                        u.setPhone(phone);
                    }

                    if (u instanceof Student) {
                        System.out.print("Type the new year of the student or type -1 to leave it as it is: ");
                        int yearM = input.nextInt();
                        if (yearM != -1) {
                            ((Student) u).setYear(yearM);
                        }
                    } else if (u instanceof Pensioner) {
                        System.out.print("Type the new pension of the pensioner or type -1 to leave it as it is: ");
                        double pensionM = input.nextDouble();
                        if (pensionM != -1) {
                            ((Pensioner) u).setPension(pensionM);
                        }
                    }
                    File inputFile = new File("database.txt");
                    File tempfile = new File("database_tmp.txt");
                    BufferedReader br = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(tempfile));
                    String line;
                    while((line = br.readLine()) != null){
                        if(!line.contains(lineToModify)){ bw.write(line+"\r\n"); }
                        else { bw.write(u.toString()+"\r\n"); }
                    }

                    bw.close();
                    br.close();

                    boolean delete = inputFile.delete();
                    boolean success = tempfile.renameTo(inputFile);
                    if(delete && success){
                        System.out.println("The contact has been modified.");
                        break;
                    } else {
                        System.out.println("Operation failed.");
                    }

                }
            }
        }
    }

    static void delete() throws ConcurrentModificationException, IOException {
        File f = new File("database.txt");
        updateFromDatabase(f);
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
                            File inputFile = new File("database.txt");
                            File tempfile = new File("database_tmp.txt");
                            BufferedReader br = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter bw = new BufferedWriter(new FileWriter(tempfile));
                            String line;
                            String lineToRemove = u.toString();
                            while((line = br.readLine()) != null){
                                if(!line.contains(lineToRemove)){ bw.write(line+"\r\n"); }
                                else { continue; }
                            }

                            bw.close();
                            br.close();

                            boolean delete = inputFile.delete();
                            boolean success = tempfile.renameTo(inputFile);
                            if(delete && success){
                                users.remove(u);
                                System.out.println("Contact deleted.");
                            } else {
                                System.out.println("Operation failed.");
                            }


                        }
                    }
                }
            } catch(ConcurrentModificationException e){

            }
        }
    }

    private static void updateFromDatabase(File f) throws IOException, NullPointerException{

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String str;
        String name;
        String phone="0";
        String year="0";
        String pension="0";
        int temp=1;

        while((str = br.readLine()) != null){

            String[] part1 = str.split(":");
            name = part1[1].substring(0, part1[1].indexOf("|")).trim();
            phone = part1[2].substring(0, part1[2].lastIndexOf("|")).trim();

            if(str.contains("Year")){
                temp = 2;
                year = part1[3].substring(0, part1[3].lastIndexOf("|")).trim();
            } else if(str.contains("Pension")){
                temp = 3;
                pension = part1[3].substring(0, part1[3].lastIndexOf("|")).trim();
            } else{
                temp = 1;
            }

            Person u = null;
            switch (temp) {
                case 1:
                    u = new Person(name, phone);
                    break;
                case 2:
                    u = new Student(name, phone, Integer.parseInt(year));
                    break;
                case 3:
                    u = new Pensioner(name, phone, Double.parseDouble(pension));
                    break;
                default:
                    break;
            }
            users.add(u);
        }
        br.close();
    }

    static void write(String s, File f) throws IOException{
        FileWriter fw = new FileWriter(f, true);
        fw.write(s);
        fw.close();
    }

    static void read(File f) throws IOException{
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String str;
            while((str = br.readLine()) != null){
                System.out.println(str );
            }
            br.close();

    }
}



