package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import java.util.List;

public abstract class SeatClass {
    private long id;
    private float price;
    private List<Benefit> listOfBenefits;

    public SeatClass(long id, float price, List<Benefit> listOfBenefits) {
        this.id = id;
        this.price = price;
        this.listOfBenefits = listOfBenefits;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Benefit> getListOfBenefits() {
        return listOfBenefits;
    }

    public void setListOfBenefits(List<Benefit> listOfBenefits) {
        this.listOfBenefits = listOfBenefits;
    }
}
