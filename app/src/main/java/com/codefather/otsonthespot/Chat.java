package com.codefather.otsonthespot;

import java.util.ArrayList;

public class Chat
{
    private String chatID;
    private String firstParticipantID;
    private String secondParticipantID;
    private ArrayList<Message> messages;

    public Chat(String chatID, String firstParticipantID, String secondParticipantID, ArrayList<Message> messages)
    {
        super();
        this.chatID = chatID;
        this.firstParticipantID = firstParticipantID;
        this.secondParticipantID = secondParticipantID;
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

    @Override
    public String toString()
    {
        return "Chat [chatID=" + chatID + ", firstParticipantID=" + firstParticipantID + ", secondParticipantID="
                + secondParticipantID + ", messages=" + messages + "]";
    }

}
