package models;

import play.db.jpa.*;
import javax.persistence.*;
import java.util.*;
import play.data.validation.*;
import play.*;
import play.mvc.*;
import play.mvc.Scope.*;


@Entity
public class Participant extends Model implements Comparable<Participant> {

	public Integer beerCount = 0;
	public String name = "";
	public Integer height = 0;
	public Integer weight = 0;
	public Boolean man = true;
	public Double percentage;
	public Date startTime;

	public Participant(String name, Integer height, Integer weight){
		name = name;
		height = height;
		weight = weight;
	}

	// At the first beer grabbed, percentage should be zero.
	public void addBeer(){
		if(beerCount!=0){
			calculatePercentage();
		}
		if(startTime == null){
			startTime = new Date();
		}
		beerCount += 1;
	}

	public void calculatePercentage(){
		percentage = 0D;  
		Double variation = man ? 0.7D : 0.6D;
		percentage = 18*beerCount/((weight*variation)-(0.15D*hoursSoFar()));
	}

	private Long hoursSoFar(){
		if(startTime!=null){
			Long diff = new Date().getTime() - startTime.getTime();
			Long diffHours = diff / (60 * 60 * 1000);     
			System.out.println(diffHours);                 
			return diffHours;
		}
		return 0L;
	}

	public int compareTo(Participant otherParticipant){
		if(otherParticipant.percentage.equals(percentage)){
			System.out.println(otherParticipant.name + " :" + otherParticipant.percentage);
			return 0;
		} else if(otherParticipant.percentage > percentage){
			System.out.println(otherParticipant.name + " :" + otherParticipant.percentage);
			return 1;
		} else {
			System.out.println(otherParticipant.name + " :" + otherParticipant.percentage);
			return -1;
		}
	}
}