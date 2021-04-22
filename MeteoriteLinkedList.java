package project2;

/** 
 * This class creates a LinkedList for {@link Meteorite} objects. Creating an object of this class
 * creates a linked list that holds Meteorite objects. The list is sorted at all times from 
 * least to greatest based on {@link Meteorite#compareTo(Meteorite o)}.
 * 
 * @author Jonason Wu
 * @version 10/5/2020
 */
class MeteoriteLinkedList {
	private Node head = null;
	private Node tail = null;
	private Node current = null;
	
	/**
	 * Instantiate a empty MeteoriteLinkedList.
	 */
	public MeteoriteLinkedList () {
	}
	
	/** 
	 * This constructor will create a sorted linked list given a {@link MeteoriteList}. It 
	 * converts an MeteoriteList into a MeteoriteLinkedList
	 * 
	 * @param list MeteoriteList to create a linked list out of; should have at least 1 element
	 * @throws IllegalArgumentException if the list is empty
	 */
	public MeteoriteLinkedList(MeteoriteList list) throws IllegalArgumentException {
		if (list == null) {
			throw new IllegalArgumentException("The list is empty.");
		}
		for (Meteorite meteor : list) {
			add(meteor);
		}
	}
	
	/**
	 * Adds a {@link Meteorite} object into the linked list. Meteorite object is automatically 
	 * sorted in the process by name 
	 * in alphabetical order disregarding upper or lower case. If names are equivalent, then id
	 * values would be compared for the sorting process. Meteorites are sorted from "lowest" 
	 * to "greatest" 
	 * based on the {@link Meteorite#compareTo(Node n)} method.
	 * If Meteorite object is already in the {@link MeteoriteLinkedList}, then the Meteorite object
	 * would not be added to the list.
	 *  
	 * @param meteor Meteorite object to add to the linked list.
	 * @return true if the MeteoriteLinkedList has changed, false if there is no change.
	 * @throws IllegalArgumentException if Meteorite object is null.
	 */
	public boolean add(Meteorite meteor) throws IllegalArgumentException {
		if (meteor == null) {
			throw new IllegalArgumentException ("null cannot be added to list");
		}
		Node newNode = new Node (meteor);
		//head has nothing
		if (head == null) {
			head = newNode;
			tail = newNode;
			return true;
		}
		
		//head has something
		int relation = newNode.compareTo(head);
		if (relation == 0) {
			//Nothing is added.
			return false;
		}
		else if (relation < 0) {
			//The added value should be the first one in the linked list
			newNode.next = head;
			head = newNode;
			return true;
		}
		else {
			//Continue down the list
			current = head;
			//Get out of loop when current = tail
			while (current.next != null) {
				relation = newNode.compareTo(current.next);
				if (relation == 0) {
					//Nothing is added
					return false;
				}
				else if (relation < 0) {
					//The added value should be at the current.next location.
					newNode.next = current.next;
					current.next = newNode;
					return true;
				}
				current = current.next;
			}
			//the value is greater than all values in the list including tail
			tail.next = newNode;
			tail = newNode;
			return true;
		}
	}
	
	/**
	 * This method removes the {@link Meteorite} object that contains the exact same 
	 * {@code name} and {@code id} in the linked list.
	 * 
	 * @param name the name of the meteorite.
	 * @param id the id of the meteorite.
	 * @return null if nothing was removed. Returns the Meteorite object that was removed 
	 * if a Meteorite is removed in the {@link MeteoriteLinkedList}.
	 */
	public Meteorite remove (String name, int id) {

		if (head == null) {
			return null;
		}
		//The node that is removed
		Node remove;
		
		//Test if first element is the one to remove
		if (head.data.getName().equals(name) && head.data.getId() == id) {
			remove = head;
			//The list only has one Node
			if (head.next == null) {
				head = null;
				tail = null;
				current = null;
			}
			//The list has more than 1 node
			else {
				head = head.next;
				current = head;
			}
			return remove.data;
		}
		
		//The list has more than 1 node
		current = head;
		Meteorite toremove = new Meteorite(name, id);
		
		//Loop while current does not go over all the values and the relation is positive or 0
		//	meaning that the value to remove is greater or equal to the next value in the list
		//	Since linked list is sorted, once the relation is negative, there would not be any
		//	matches.
		int relation;
		while (current.next != null && ((relation = toremove.compareTo(current.next.data)) >= 0)) {
			
			if (relation == 0){
				remove = current.next;
				current.next = current.next.next;
				//If the tail node is the one that is removed
				if (current.next == null) {
					tail = current;
				}
				return remove.data;
			}
			current = current.next;			
		}
		return null;
	}
	
	/**
	 * Returns a single String that contains all the values stored in the linked list.
	 * Each {@link Meteorite} will be separated by a "\n" so when printed, it will return a 
	 * neat list of all the aspects of a Meteorite in each row. Look at 
	 * {@link Meteorite#toString()} for details on how the Meteorite aspects are printed.
	 */
	public String toString() {
		String allNodes = "";
		if (head == null) {
			return allNodes;
		}
		allNodes += head.toString();
		
		current = head;
		while (current.next != null) {
			current = current.next;
			allNodes += "\n" + current.toString();
		}
		return allNodes;
	}
	
	/**
	 * This class creates a {@link Meteorite} object to store a Meteorite and a Node object 
	 * to store the reference to the next node. Used to create singly linked lists. 
	 * Copied implementation from project 2 page.
	 * 
	 * @author unknown
	 */
	private class Node implements Comparable<Node> {
		Meteorite data;
		Node next;
		
		/**
		 * Sets a data value. {@code next} will be null.
		 * 
		 * @param data the Meteorite object to store in the Node.
		 */
		Node (Meteorite data) {
			this.data = data;
		}

		/**
		 * Calls the {@link Meteorite#toString()}.
		 * 
		 * @return a formatted string with aspects of the Meteorite
		 */
		public String toString () {
			return data.toString();
		}

		/**
		 * Evaluates whether the Node that calls this method and the {@code o} are equal.
		 * Calls the {@link Meteorite#equals(Object obj)} to determine whether it is equal.
		 * 
		 * @return true if equal. false if {@code o} is not an instance of Node or they are not 
		 * equal.
		 */
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Node)) {
				return false;
			}
			Node other = (Node) o;
			if (!this.data.equals(other.data)) {
				return false;
			}
			return true;
		}

		/**
		 * Evaluates whether the Node that calls this method and the {@code o} are equal.
		 * Calls the {@link Meteorite#compareTo(Meteorite o)} to determine whether it is equal.
		 * 
		 * @return -1 if the method calling this method is considered "less" than {@code n}.
		 * Returns 0 if they are equal. Returns 1 if the method calling this method is considered
		 * "greater" than {@code n}.
		 */
		public int compareTo (Node n) {
			return data.compareTo(n.data);
		}
	}
}
