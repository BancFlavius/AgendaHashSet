public class Student extends Person {
    private int year;
    Student(String name, String phone, int year){
        super(name, phone);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
