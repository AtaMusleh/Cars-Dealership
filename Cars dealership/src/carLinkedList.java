public class carLinkedList {
	private carNode Front, Back;
	private int size;

	public carLinkedList() {
		Front = Back = null;
		size = 0;
	}

	/**
	 * Returns the front node of the linked list.
	 */
	public carNode getFront() {
		return Front;
	}

	/**
	 * Sets the front node of the linked list.
	 */
	public void setFront(carNode front) {
		Front = front;
	}

	/**
	 * Returns the back node of the linked list.
	 */
	public carNode getBack() {
		return Back;
	}

	/**
	 * Sets the back node of the linked list.
	 */
	public void setBack(carNode back) {
		Back = back;
	}

	/**
	 * Inserts a new node into the linked list in a sorted manner based on the car
	 * information.
	 */
	public void insert(carNode newNode) {
		if (Front == null) {
			Front = newNode;
			Back = newNode;
		} else {
			if (newNode.getCar().compareTo(Front.getCar()) <= 0) {
				newNode.setNext(Front);
				Front = newNode;
			} else if (newNode.getCar().compareTo(Back.getCar()) >= 0) {
				Back.setNext(newNode);
				Back = newNode;
			} else {
				carNode current = Front;
				while (current.getNext() != null && newNode.getCar().compareTo(current.getNext().getCar()) > 0) {
					current = current.getNext();
				}
				newNode.setNext(current.getNext());
				current.setNext(newNode);
			}
		}
		size++;
	}

	/**
	 * Deletes a node from the linked list based on the car information.
	 */
//	public void delete(Car car) {
//	    carNode current = Front;
//	    carNode previous = null;
//
//	    // Traverse the list to find the node to be deleted and its previous node
//	    while (current != null) {
//	        if (current.getCar().equals(car)) {
//	            break;
//	        }
//	        previous = current;
//	        current = current.getNext();
//	    }
//
//	    if (current == null) {
//	        // Node not found, do nothing
//	        return;
//	    }
//
//	    if (previous == null) {
//	        // Node to be deleted is the first node
//	        Front = current.getNext();
//	    } else {
//	        // Node is somewhere in the middle of the list
//	        previous.setNext(current.getNext());
//	    }
//
//	    if (current == Back) {
//	        // If the node to be deleted is the last node, update the 'Back' reference
//	        Back = previous;
//	    }
//
//	    size--;
//	}
//	public void delete(Car name) {
//		carNode current = search(name);
//		if (current == null) {
//
//		} else if (current == Front && current == Back) {
//			Front = null;
//			Back = null;
//		} else if (current == Front) {
//			Front = current.getNext();
//			// Front.setPre(null);
//		} else if (current == Back) {
//			// Back = current.getPre();
//			Back.setNext(null);
//		} else {
////			current.getPre().setNext(current.getNext());
////			current.getNext().setPre(current.getPre());
//		}
//		size--;
//	}
	public void delete(Car name) {
		carNode current = search(name);
		if (current == null) {
			// Car not found, do nothing
			return;
		}

		if (current == Front) {
			// Car is the first node
			Front = current.getNext();
		} else {
			// Car is somewhere in the middle or at the end
			carNode previous = getPreviousNode(current);
			previous.setNext(current.getNext());
		}

		size--;
	}

	private carNode getPreviousNode(carNode node) {
		carNode current = Front;
		while (current != null && current.getNext() != node) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Searches for a car in the linked list based on the order information. Returns
	 * the corresponding node if found, null otherwise.
	 */
	public carNode search(Order car) {
		carNode current = Front;
		while (current != null) {
			if (current.getCar().getModel().trim().equalsIgnoreCase(car.getModel().trim())
					&& current.getCar().getYear() == (car.getYear())
					&& current.getCar().getColor().trim().equalsIgnoreCase(car.getColor().trim())
					&& current.getCar().getPrice().trim().equalsIgnoreCase(car.getPrice().trim())) {
				return current; // Found the car
			}
			current = current.getNext();
		}
		return null; // Car not found
	}

	/**
	 * Searches for a car in the linked list based on the car information. Returns
	 * the corresponding node if found, null otherwise.
	 */
	public carNode search(Car car) {
		carNode current = Front;
		while (current != null) {
			if (current.getCar().getModel().trim().equalsIgnoreCase(car.getModel().trim())
					&& current.getCar().getYear() == (car.getYear())
					&& current.getCar().getColor().trim().equalsIgnoreCase(car.getColor().trim())
					&& current.getCar().getPrice().trim().equalsIgnoreCase(car.getPrice().trim())) {
				return current; // Found the car
			}
			current = current.getNext();
		}
		return null; // Car not found
	}

}
