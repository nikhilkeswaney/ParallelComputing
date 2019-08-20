//******************************************************************************
// This is used as a key to be used for the combiner
//
// File:    StateCounty.java
//******************************************************************************

import java.io.Serializable;

/**
 * Class StateCounty is used as a key for combiner in t
 *
 * @author  Nikhil Keswaney
 * @version 12-Dec-2018
 */
public class StateCounty implements Comparable<StateCounty>, Serializable

{

    private String state = "", county = "";

    /**
     * Constructor for StateCounty.
     * @param state state name of the object to create
     * @param county county name of the object to create
     */
    public StateCounty
    (String state, String county)
    {
        this.state = state;
        this.county = county;
    }

    /**
     * This method teturns if 2 objects are equal.
     * @param obj to check if this object is same as this
     * @return true or false depending upon the equality
     */
    public boolean equals
    (Object obj)
    {
        return (obj instanceof StateCounty) && ((this.state + this.county).equals(((StateCounty)obj).state + ((StateCounty)obj).county));
    }

    /**
     * Returns hashcode of the word.
     * @return hash code of the word
     */
    public int hashCode()
    {
        return (this.state + this.county).hashCode();
    }

    /**
     * State of the object type
     * @return State name
     */
    public String getState() {
        return state;
    }

    /**
     * County name of the object
     * @return county name
     */
    public String getCounty() {
        return county;
    }

    /**
     * Compareto function to perform some kind of sorting
     * @param o object of type StateCounty
     * @return 0, 1, -1
     */
    @Override
    public int compareTo(StateCounty o) {
        return 0;
    }
}