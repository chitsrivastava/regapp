package com.stackhack.regapp.model;

import javax.persistence.*;

@Entity
@Table(
        name = "user"
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "re_id"
    )
    private long registrationId;
    @Column(
            name = "user_name"
    )
    private String userName;
    @Column(
            name = "user_contact"
    )
    private String contactNumber;
    @Column(
            name = "user_email"
    )
    private String email;

    @Lob
    @Column(name="user_image")
    private byte[] image;

    @Column(
            name = "user_registration_type"
    )
    private String registrationType;
    @Column(
            name = "user_no_of_tickets"
    )
    private int numberOfTickets;
    @Column(
            name = "user_is_approved"
    )
    private boolean isApproved;

    public User() {
    }

    public User(String userName, String contactNumber, String email, byte[] image, String registrationType, int numberOfTickets, boolean isApproved) {
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.image = image;
        this.registrationType = registrationType;
        this.numberOfTickets = numberOfTickets;
        this.isApproved = isApproved;
    }

    public long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(long registrationId) {
        this.registrationId = registrationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "User{" +
                "registrationId=" + registrationId +
                ", userName='" + userName + '\'' +
                ", contactNumber=" + contactNumber +
                ", email='" + email + '\'' +
                ", image=" + image +
                ", registrationType='" + registrationType + '\'' +
                ", numberOfTickets=" + numberOfTickets +
                ", isApproved=" + isApproved +
                '}';
    }
}
