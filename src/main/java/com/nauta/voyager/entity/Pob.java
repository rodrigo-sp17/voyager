package com.nauta.voyager.entity;

import com.nauta.voyager.entity.Person;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Pob {
    
    @Id    
    private Long pobId;
    
    @ElementCollection
    private List<Person> people;
    
    //private Integer numMembers;
    
    @Column(nullable = false)
    private LocalDate dateIssued;
    
    //@ColumnDefault(value = "")
    @Column(nullable = false)
    private String boardingPlace;
    
    @Column(nullable = false)
    private LocalDate boardingDate;
    
    //@ColumnDefault(value = "")    
    @Column(nullable = false)
    private String arrivalPlace;
    
    @Column(nullable = false)
    private LocalDate arrivalDate;
    
    //@ColumnDefault(value = "A")
    private String crew;

    public Pob() {
        pobId = 1L;
        dateIssued = LocalDate.now();
        boardingDate = LocalDate.now();
        arrivalDate = ChronoUnit.DAYS.addTo(LocalDate.now(), 28);
    }

    public Pob(LocalDate dateIssued,
            String boardingPlace, LocalDate boardingDate, 
            String arrivalPlace, LocalDate arrivalDate, 
            String crew) {
        this.pobId = 1L;
        this.people = Collections.EMPTY_LIST;
        this.dateIssued = dateIssued;
        this.boardingPlace = boardingPlace;
        this.boardingDate = boardingDate;
        this.arrivalPlace = arrivalPlace;
        this.arrivalDate = arrivalDate;
        this.crew = crew;
    }
    
    

    public Long getPobId() {
        return pobId;
    }

    public void setPobId(Long pobId) {
        this.pobId = pobId;
    }      

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getBoardingPlace() {
        return boardingPlace;
    }

    public void setBoardingPlace(String boardingPlace) {
        this.boardingPlace = boardingPlace;
    }

    public LocalDate getBoardingDate() {
        return boardingDate;
    }

    public void setBoardingDate(LocalDate boardingDate) {
        this.boardingDate = boardingDate;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }    
   
}
