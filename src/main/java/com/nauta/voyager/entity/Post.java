package com.nauta.voyager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Post implements RaftKey {
       
    @Id
    @GeneratedValue
    private Long postId;
    
    @Column(length = 3)
    private String postPrefix;
        
    private String postDescription;
    
    @ColumnDefault(value = "0")
    private Integer postVariation;
    
    private boolean crewMember;
    

    public Post() {
    }  
    
    
    public Long getPostId() {
        return postId;
    }
    
    public String getPostPrefix() {
        return postPrefix;                            
    }
    
    public String getPostDescription() {
        return postDescription;
    }

    public Integer getPostVariation() {
        return postVariation;
    }

    public boolean isCrewMember() {
        return crewMember;
    }   

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setPostPrefix(String postPrefix) {
        this.postPrefix = postPrefix;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public void setPostVariation(Integer postVariation) {
        this.postVariation = postVariation;
    }

    public void setCrewMember(boolean crewMember) {
        this.crewMember = crewMember;
    } 
    

    @Override
    public String toString() {
        if (postVariation == 0) {
            return String.format("%s - %s",
                    postPrefix,
                    postDescription);
        } else {
            return String.format("%s - %d - %s",
                    postPrefix,
                    postVariation,
                    postDescription);
        }
    } 

    @Override
    public String asKey() {
        return "function" + postId;
    }

    @Override
    public RaftKeyType getType() {
        return RaftKeyType.FUNCTION;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Post) {
            Post that = (Post) obj;
            return this.postId.equals(that.postId);
        }
        return false;
    }   
}
