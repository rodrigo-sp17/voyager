package com.nauta.voyager.people;

import java.time.*;


public class Person {
    // Default values for CrewMembers
    final LocalDate DEFAULT_EXP_DATE = LocalDate.of(2030,01,01);
    final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1980,01,01);
    final String DEFAULT_NATIONALITY = "BRASILEIRA";
    
    // Counter to keep track of number of instances, used as default ID
    //private static int numMembers
    
    // Fixed crew member data
    private int id;
    private String name;    
    private Function function;
    private String company;
    private String nationality;
    private String cir;
    private LocalDate cirExpDate;
    private String sispat;
    private LocalDate birthDate;
    
    // Mutable crew member data
    private String crew;
    private boolean boarded;
    private LocalDate boardingDate;
    private String boardingPlace;
    private LocalDate arrivalDate;
    private String arrivalPlace;
    private String cabin;
    private String shift;    
    
    // Constructors
    // Basic info constructor
    public Person(String name, String company, Function function) {
        this.id = 0;
        this.name = name;
        this.function = function;
        this.company = company;
        this.nationality = DEFAULT_NATIONALITY;
        this.cir = "";
        this.cirExpDate = DEFAULT_EXP_DATE;
        this.sispat = "";
        this.birthDate = DEFAULT_BIRTH_DATE;
        
        this.crew = "";
        this.boarded = false;
        this.boardingDate = LocalDate.now();
        this.boardingPlace = "";
        this.arrivalDate = LocalDate.now();
        this.arrivalPlace = "";
        this.cabin = "";
        this.shift = "";     
    }
    
    // Instantiates a default Crew Member
    public Person() {
        this.id = 0;
        this.name = "";  
        this.function = null;
        this.company = "";
        this.nationality = DEFAULT_NATIONALITY;
        this.cir = "";
        this.cirExpDate = DEFAULT_EXP_DATE;
        this.sispat = "";
        this.birthDate = DEFAULT_BIRTH_DATE;
        
        this.crew = "";
        this.boarded = false;
        this.boardingDate = LocalDate.now();
        this.boardingPlace = "";
        this.arrivalDate = LocalDate.now();
        this.arrivalPlace = "";
        this.cabin = "";
        this.shift = "";        
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCir() {
        return cir;
    }

    public void setCir(String cir) {
        this.cir = cir;
    }

    public LocalDate getCirExpDate() {
        return cirExpDate;
    }

    public void setCirExpDate(LocalDate cirExpDate) {
        this.cirExpDate = cirExpDate;
    }

    public String getSispat() {
        return sispat;
    }

    public void setSispat(String sispat) {
        this.sispat = sispat;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}