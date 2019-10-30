package org.anonymous.set;

import org.anonymous.bytecodeAnnotations.LogEntry;

import java.util.Objects;

/**
 * @author child
 * 2019/6/19 8:31
 * an item with a description and a part number.
 */
public class Item {

    private String description;
    private int partNumber;

    /**
     * constructs an item.
     *
     * @param description the item's description
     * @param partNumber  the item's part number
     */
    public Item(String description, int partNumber) {
        this.description = description;
        this.partNumber = partNumber;
    }

    /**
     * gets the description of this item
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", partNumber=" + partNumber +
                '}';
    }

    @LogEntry(logger = "org.anonymous.demo")
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (null == other) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        Item item = (Item) other;

        return Objects.equals(description, item.description) && partNumber == item.partNumber;
    }

    @LogEntry(logger = "org.anonymous.demo")
    @Override
    public int hashCode() {
        return Objects.hash(description, partNumber);
    }
}
