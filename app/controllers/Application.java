package controllers;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Application extends Controller {

    private static List<Participant> participants = new LinkedList<Participant>();

    public static void index() {
        participants = Participant.find("order by percentage asc").fetch();
        render(participants);
    }

    public static void participants() {
        if(participants != null && (Participant.count() != participants.size())){
            participants = Participant.find("order by percentage asc").fetch();
        }
        if(participants != null){
            for(Participant p : participants){
                p.calculatePercentage();
            }
        }
        Collections.sort(participants);
        renderJSON(participants);
    }

    public static void resetBoard(){
    	Participant.deleteAll();
    }

    public static void addParticipant(String name, Integer height, Integer weight){
		Participant participant = new Participant(name,height, weight);
        if(Participant.count("byName",participant.name) == 0){
            System.out.println("Printing");
            participant.save();            
        }
    }
    
    public static void addBeer(Long participantId){
    	Participant participant = Participant.findById(participantId);
        participant.addBeer();
        participant.save();
        participants = Participant.find("order by percentage asc").fetch();
    }


}