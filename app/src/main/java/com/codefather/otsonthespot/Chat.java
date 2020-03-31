package com.codefather.otsonthespot;

import java.util.ArrayList;

public class Chat
{
    private String chatID;
    private String firstParticipantID;
    private String secondParticipantID;
    private String firstName;
    private String secondName;
    private ArrayList<Message> messages;

    public Chat(){}

    public Chat(String chatID, String firstParticipantID, String secondParticipantID, String firstName, String secondName, ArrayList<Message> messages)
    {
        this.chatID = chatID;
        this.firstParticipantID = firstParticipantID;
        this.secondParticipantID = secondParticipantID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.messages = messages;
    }

    public String getChatID()
    {
        return chatID;
    }

    public void setChatID(String chatID)
    {
        this.chatID = chatID;
    }

    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages)
    {
        this.messages = messages;
    }

    public String getFirstParticipantID()
    {
        return firstParticipantID;
    }

    public void setFirstParticipantID(String firstParticipantID)
    {
        this.firstParticipantID = firstParticipantID;
    }

    public String getSecondParticipantID()
    {
        return secondParticipantID;
    }

    public void setSecondParticipantID(String secondParticipantID)
    {
        this.secondParticipantID = secondParticipantID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSecondName()
    {
        return secondName;
    }

    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
    }

    @Override
    public String toString()
    {
        return "Chat{" +
                "chatID='" + chatID + '\'' +
                ", firstParticipantID='" + firstParticipantID + '\'' +
                ", secondParticipantID='" + secondParticipantID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", messages=" + messages +
                '}';
    }
}
