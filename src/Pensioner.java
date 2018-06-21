class Pensioner extends Person {
    private double pension;

    public double getPension() {
        return pension;
    }

    public void setPension(double pension) {
        this.pension = pension;
    }

    @Override
    public String toString() {
        return "Name: "+getName()+" | Phone: "+getPhone()+" | Pension: "+getPension();
    }

    Pensioner(String name, String phone, double pension){
        super(name, phone);
        this.pension = pension;
    }
}
