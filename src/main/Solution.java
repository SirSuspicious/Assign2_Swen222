package main;

import gameObjects.*;

public class Solution {
	
	private final WeaponCard weapon;
	private final RoomCard room;
	private final CharacterCard character;
	
	/**
	 * contains the cards that compose the solution to the murder mystery.
	 * @param room
	 * @param character
	 * @param weapon
	 */
	public Solution(RoomCard room,
			CharacterCard character, WeaponCard weapon) {

		this.weapon = weapon;
		this.room = room;
		this.character = character;
	}

	
	public WeaponCard getWeapon() {
		return weapon;
	}

	public RoomCard getRoom() {
		return room;
	}

	public CharacterCard getCharacter() {
		return character;
	}
	
	

	@Override
	public String toString() {
		return "Combination [weapon=" + weapon + ", room=" + room
				+ ", character=" + character + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solution other = (Solution) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}


	public boolean contains(Card card) {
		if(weapon.equals(card))
			return true;
		if(character.equals(card))
			return true;
		if(room.equals(card))
			return true;
		return false;
	}
	
	/**
	 * compares a suggestion to this solution,
	 * return true if it is a match, false otherwise.
	 * @param s
	 * @return
	 */
	public boolean is(Suggestion s){
		if(weapon.getName() == s.getWeapon() && 
				character.getName() == s.getCharacter() && 
				room.getName() == s.getRoom()){
			return true;
		}
		return false;
	}
	
	
}
