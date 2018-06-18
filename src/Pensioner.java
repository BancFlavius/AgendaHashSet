class Pensioner extends Person {
    private double pension;

    public double getPension() {
        return pension;
    }

    public void setPension(double pension) {
        this.pension = pension;
    }

    Pensioner(String name, String phone, double pension){
        super(name, phone);
        this.pension = pension;
    }
}
