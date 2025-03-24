package DRCTC.entities;

import java.util.List;

// package ek box hai jiske andr bht saari chije h jese User, Train, Ticket ,etc. AB unhe upr jese package likha h ese use kr skte h Dusri koi entity file kholke dekho pta lgega ki import nhi kia but still use kr rhe h

public class User {
    private String userId;
    private String name;
    private String password;
    private String hashedPassword;

    private List<Ticket> ticketBooked;

    public User(String name, String password, String hashedPassword, List<Ticket> ticketBooked, String userId){
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketBooked = ticketBooked;
        this.userId = userId;
    }

    public User(){}

    // getters
    public  String getName(){
        return name;
    }
    public  String getPassword(){
        return password;
    }
    public  String getHashedPassword(){
        return hashedPassword;
    }
    public  String getUserId(){
        return userId;
    }
    public List<Ticket> getTicketBooked(){
        return ticketBooked;
    }

    public void printTickets(){
        for(int i=0; i<ticketBooked.size();i++){
            System.out.println(ticketBooked.get(i).getTicketInfo());
        }
    }

    // setters
    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setHashedPassword(String hashedPassword){
        this.hashedPassword = hashedPassword;
    }
    public void setTicketBooked(List<Ticket> ticketBooked){
        this.ticketBooked = ticketBooked;
    }
}
