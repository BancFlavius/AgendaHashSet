public class Student extends Person {
    private int year;
    Student(String name, String phone, int year){
        super(name, phone);
        this.year = year;
    }

    @Override
    public String toString() {
        return "Name: "+getName()+" | Phone: "+getPhone()+" | Year: "+getYear()+" |";
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
