// Name: Ken Whittaker, Matt Kanoc, Brandon Lewis
// Course: CSC 470, Section 1
// Semester: Fall 2012
// Instructor: Dr. Pulimood
// Collaborative Project: Poll-Friend
// Description: Driver class to test poll functionality, sorting, and user
//              data to view aggregated results.
// Filename: PollFriend.java
// Last modified on: 12/6/2012

import java.util.*;
import java.io.*;

public class PollFriend
{
    public static void main (String[] args) throws IOException
    {
        boolean admin = false; //checks if the user is admin
        Scanner sc = new Scanner(System.in); 
        String select; //this will record the user-input name of the current poll
        int pollNo; //polls will be stored in an array, so pollNo gives location.
	
	// Create a user.
        System.out.print("User Name: ");
        String person = sc.next();
	user csuser;

        Poll[] allPolls; //stores all polls
        Poll currentPoll = null; //when a poll is pulled from the allPolls array, it can be run as currentPoll

	//For the purpose of our project and its scope, we will start with three polls.
	allPolls = new Poll[3];
	allPolls[0] = new Poll(new File("sportsData.txt"), "Sports", "Which is more fun?");
	allPolls[1] = new Poll(new File("fruitData.txt"), "Fruit", "Which tastes better?");
	allPolls[2] = new Poll(new File("socialNetworkData.txt"), "Web", "Which wastes more time?");

	//We need to check if the user exists, because there are two ways of creating a user.
	File f = new File(person + ".txt");
	if (f.exists())
		csuser = new user(person);
	else
		csuser = new user(); 
		
	
	//reset admin if the user is an admin.
        if (person.equals("admin"))
            admin = true;
	
        while (!admin)//We run through users until an admin logs in.
        {
	    //First we need to let the user know what polls are available.
            System.out.println("Which poll would you like to take?");
            for (int i = 0; i < allPolls.length; i++)
                System.out.println("\t" + allPolls[i].toString());
	    
            select = sc.next();// user inputs poll name.
            pollNo = -1;// initial value.
            
	    // Finds the poll if there is a match and sets pollNo to the index of select.
            for (int j = 0; j < allPolls.length; j++)
            {
                if (select.equals(allPolls[j].toString()))
                    pollNo = j;
            }
	    
            if (pollNo == -1)//if the user input select does not match any poll in allPolls
            {
                System.out.println("That is not the name of a poll currently in our database.");
                System.out.println("Please e-mail us and complain.");
            }
            else if (csuser.hasPolled(select))//check if the user has already taken a poll. They can only be taken once.
            {
                System.out.println("You have already taken this poll");
            }
            else //if the user is eligible to take the poll they selected.
            {
                currentPoll = allPolls[pollNo];
                csuser.tookPoll(select);//Marks the poll as taken.
                currentPoll.run();//This instruction takes the poll.
                System.out.println("Thank you for taking " + currentPoll.toString());
                System.out.println();
            }
            csuser.store();//closes the user file.

	    //Set up the new user for the next time through the loop. Entering admin will exit.
            System.out.print("User Name: ");
            person = sc.next();
	    
            f = new File(person + ".txt");
            if (f.exists())
                csuser = new user(person);
            else
                csuser = new user();
            if (person.equals("admin"))
                admin = true;
        }//while(!admin)
	
        System.out.println();
        System.out.println("Which poll would you like to view?");
        for (int l = 0; l < allPolls.length; l++)
        {
            System.out.println(allPolls[l].toString());
        }
	//The poll which is input will be aggregated and viewed.
        select = sc.next();
        pollNo = -1;
        
        for (int k = 0; k < allPolls.length; k++)//Check to find the poll.
        {
            if (select.equals(allPolls[k].toString()))
                pollNo = k;
        }

        if(pollNo == -1)// Exits if the poll does not exist.
            System.out.println("Good-bye");
        else // aggregates if the poll exists. aggregate() includes a print call.
            allPolls[pollNo].aggregate();
    }
}
