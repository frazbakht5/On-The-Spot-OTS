package com.codefather.otsonthespot;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
    private String userID;
    private String name;
    private String institution;
    private String email;
    private ArrayList<String> chatsID;

    public User(){}

    public User(String userID, String name, String institution, String email)
    {
        super();
        this.userID = userID;
        this.name = name;
        this.institution = institution;
        this.email = email;
        this.chatsID = new ArrayList<>();
    }


    public ArrayList<String> getChatsID()
    {
        return chatsID;
    }

    public void setChatsID(ArrayList<String> chatsID)
    {
        this.chatsID = chatsID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getInstitution()
    {
        return institution;
    }

    public void setInstitution(String institution)
    {
        this.institution = institution;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }


    @Override
    public String toString()
    {
        return "User [userID=" + userID + ", name=" + name + ", institution=" + institution + ", email=" + email
                + ", chatsID=" + chatsID + "]";
    }


}