package com.nauta.voyager;

import java.util.*;


public class CrewMember {
    // Fixed crew member data
    private static int numMembers = 0;
    private int id;
    private String name;    
    private String company;
    private String function;
    private String sispat;
    private String cir;
    private Date cirExpDate;
    private Date birthDate;
    private String nationality;
    
    // Mutable crew member data
    private String shift;
    private Date boardingDate;
    private String cabin;
    
    // Constructors
    CrewMember(String name, String company, String function, String sispat, 
        Date birthDate, String shift, Date boardingDate, String cabin) {
            this.id = numMembers++;
            this.name = name;
            this.company = company;
            this.function = function;
            this.sispat = sispat;
            this.birthDate = birthDate;
            this.shift = shift;
            this.boardingDate = boardingDate;
            this.cabin = cabin;            
    }
    
    // Shortened constructor for prototyping purposes
    CrewMember(String name, String company, String function) {
        this.id = numMembers++;
        this.name = name;
        this.company = company;
        this.function = function;
        this.sispat = "11111111";
        this.birthDate = new GregorianCalendar(2020, 5, 15).getTime();
        this.shift = "someshift";
        this.boardingDate = new GregorianCalendar(2020, 5, 15).getTime();
        this.cabin = "cabine X";
        this.nationality = "BRASILEIRA";
        this.cir = "801P2222222";
        this.cirExpDate = new GregorianCalendar(2020, 5, 17).getTime();
    }
    // Empty constructor
    CrewMember() {
        this.id = numMembers++;
    }    
       
    
    
    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }    
    public String getCompany() {
         return company;
    }
    public String getFunction() {
        return function;
    }
    public String getNationality() {
        return nationality;
    }
    public String getCir() {
        return cir;
    }
    public Date getCirExpDate() {
        return cirExpDate;
    }
    public String getSispat() {
        return sispat;
    }    
    public Date getBirthDate() {
        return birthDate;
    }    
    public String getShift() {
        return shift;
    }
    public Date getBoardingDate() {
        return boardingDate;
    }
    public String getCabin() {
        return cabin;
    }
    public static int getNumMembers() {
        return numMembers;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setFunction(String function) {
        this.function = function;
    }
    public void setNationality(String nation) {
        this.nationality = nation;
    }
    public void setCir(String cir) {
        this.cir = cir;
    }
    public void setCirExpDate(Date date) {
        this.cirExpDate = date;
    }
    public void setSispat(String sispat) {
        this.sispat = sispat;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public void setShift(String shift) {
        this.shift = shift;
    }
    public void setBoardingDate(Date boardingDate) {
        this.boardingDate = boardingDate;
    }
    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

}