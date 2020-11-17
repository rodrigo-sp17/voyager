package com.nauta.voyager.entity;

import java.time.*;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Person {
    // Default values for CrewMembers
    final LocalDate DEFAULT_EXP_DATE = LocalDate.of(2030,01,01);
    final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1980,01,01);
    final String DEFAULT_NATIONALITY = "BRASILEIRA";
       
    @Id
    @GeneratedValue
    private Long personId;
    
    @Column(nullable = false)
    private String name;    
    
    @Column(nullable = false)
    private String company;
    
    @ManyToOne
    @JoinColumn(name = "post_id",
		foreignKey = @ForeignKey(name = "POSTID_FK"))
    private Post function;
        
    @ColumnDefault(value = "'BRASILEIRA'")
    @Column(nullable = false)
    private String nationality;
    
    @Column(length = 14)
    private String cir;
    
    @Column(nullable = false)
    private LocalDate cirExpDate;
    
    @Column(length = 8)
    private String sispat;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    //@Column(nullable = false)
    @Embedded
    private BoardingData boardingData;

    public Person() {
        boardingData = new BoardingData();
        cirExpDate = LocalDate.MIN;
        birthDate = LocalDate.MIN;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Post getFunction() {
        return function;
    }

    public void setFunction(Post function) {
        this.function = function;
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

    public BoardingData getBoardingData() {
        return boardingData;
    }

    public void setBoardingData(BoardingData boardingData) {
        this.boardingData = boardingData;
    }

    
    
}