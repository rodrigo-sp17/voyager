package com.nauta.voyager.entity;

import com.nauta.voyager.entity.Cabin;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

@Embeddable
public class BoardingData {
        
    @Column(length = 3, nullable = false)    
    private String crew;
    
    @ColumnDefault(value = "false")
    private boolean boarded;
    
    @Column(nullable = false)
    private LocalDate boardingDate;
    
    @ColumnDefault(value = "''")
    private String boardingPlace;
    
    @Column(nullable = false)
    private LocalDate arrivalDate;
    
    @ColumnDefault(value = "''")
    private String arrivalPlace;
    
    @Embedded
    private Cabin cabin;
    
    @ColumnDefault(value = "''")
    private String shift;
        
    public BoardingData() {
        crew = "";
        boardingDate = LocalDate.now();
        arrivalDate = ChronoUnit.DAYS.addTo(LocalDate.now(), 28);
    }
    
    
    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public boolean isBoarded() {
        return boarded;
    }

    public void setBoarded(boolean boarded) {
        this.boarded = boarded;
    }

    public LocalDate getBoardingDate() {
        return boardingDate;
    }

    public void setBoardingDate(LocalDate boardingDate) {
        this.boardingDate = boardingDate;
    }

    public String getBoardingPlace() {
        return boardingPlace;
    }

    public void setBoardingPlace(String boardingPlace) {
        this.boardingPlace = boardingPlace;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }    
    
    
}
