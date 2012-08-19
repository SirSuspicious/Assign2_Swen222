package main;

import gameObjects.*;

public class Suggestion {
	
	private Weapon weapon;
	private Person character;
	private RoomType room;
	public Suggestion(RoomType room, Person character, Weapon weapon) {

		this.weapon = weapon;
		this.character = character;
		this.room = room;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public Person getCharacter() {
		return character;
	}
	public RoomType getRoom() {
		return room;
	}
	
	public String toString(){
		return "Room: "+room+"   Character: "+character+"   Weapon: "+weapon;
	}
	
	public boolean contains(Card card){
		if(weapon.equals(card.getName()))
			return true;
		if(character.equals(card.getName()))
			return true;
		if(room.equals(card.getName()))
			return true;
		return false;
	}

	
	
}
