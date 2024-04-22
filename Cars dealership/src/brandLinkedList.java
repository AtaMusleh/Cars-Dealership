public class brandLinkedList {
	private brandNode Front, Back;
	private int size;

	public brandLinkedList() {
		Front = Back = null;
		size = 0;
	}

	/**
	 * Returns the front node of the linked list.
	 */
	public brandNode getFront() {
		return Front;
	}

	/**
	 * Sets the front node of the linked list.
	 */
	public void setFront(brandNode front) {
		Front = front;
	}

	/**
	 * Returns the back node of the linked list.
	 */
	public brandNode getBack() {
		return Back;
	}

	/**
	 * Sets the back node of the linked list.
	 */
	public void setBack(brandNode back) {
		Back = back;
	}

	/**
	 * Returns the size of the linked list.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Inserts a new node into the linked list in a sorted manner based on the brand name.
	 * If the brand already exists, it does not insert a duplicate.
	 */
	public void insert(brandNode newNode) {
		if (Front == null) {
			Front = Back = newNode;
		} else {
			brandNode current = Front;
			while (current != null && current.getBrand().compareTo(newNode.getBrand()) < 0) {
				current = current.getNext();
			}
			if (current != null && current.getBrand().compareTo(newNode.getBrand()) == 0) {
				return; // Brand already exists, no need to insert
			}
			if (current == null) {
				Back.setNext(newNode);
				newNode.setPre(Back);
				Back = newNode;
			} else if (current == Front) {
				newNode.setNext(Front);
				Front.setPre(newNode);
				Front = newNode;
			} else {
				newNode.setNext(current);
				newNode.setPre(current.getPre());
				current.getPre().setNext(newNode);
				current.setPre(newNode);
			}
		}
		size++;
	}

	/**
	 * Deletes a node from the linked list.
	 */
	public void delete(brandNode deleteNode) {
		if (Front == null) {
			return; // List is empty, nothing to delete
		}

		if (Front == deleteNode) {
			Front = Front.getNext();
			if (Front != null) {
				Front.setPre(null);
			} else {
				Back = null; // The list became empty after deletion
			}
		} else if (Back == deleteNode) {
			Back = Back.getPre();
			Back.setNext(null);
		} else {
			brandNode current = Front.getNext();
			while (current != null && current != deleteNode) {
				current = current.getNext();
			}

			if (current == null) {
				return; // Node not found in the list
			}

			brandNode prevNode = current.getPre();
			brandNode nextNode = current.getNext();
			prevNode.setNext(nextNode);
			if (nextNode != null) {
				nextNode.setPre(prevNode);
			}
		}

		size--;
	}

	/**
	 * Updates the brand name of a node in the linked list.
	 */
	public void update(String oldBrandName, String newBrandName) {
		brandNode current = Front;
		while (current != null) {
			if (current.getBrand().getBrand().equalsIgnoreCase(oldBrandName)) {
				current.getBrand().setBrand(newBrandName);
				return; // Found and updated the brand, exit the method
			}
			current = current.getNext();
		}
	}

	/**
	 * Searches for a brand in the linked list and returns the corresponding node.
	 * Returns null if the brand is not found.
	 */
	public brandNode search(String brand) {
		brandNode current = Front;
		while (current != null) {
			if (current.getBrand().getBrand().trim().equalsIgnoreCase(brand.trim())) {
				return current; // Found the brand
			}
			current = current.getNext();
		}
		return null; // Brand not found
	}
}
