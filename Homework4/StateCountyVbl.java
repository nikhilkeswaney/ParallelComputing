//******************************************************************************
//
// File:    DivIndex.java
// This program is the vbl class for the value variable in the combiner
//******************************************************************************


import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;
import edu.rit.pj2.Vbl;

import java.io.IOException;

/**
 * This class is the vbl class for the value variable in int combiner
 * @author  Nikhil Keswaney
 * @version 12-Dec-2018
 */
public class StateCountyVbl extends Tuple implements Vbl {
    /**
     * Setter for withe population
     * @param whitePopulation white population amount.
     */
    public void setWhitePopulation(int whitePopulation) {
        this.whitePopulation = whitePopulation;
    }

    /**
     * Setter for black population
     * @param blackPopulation black population amount
     */
    public void setBlackPopulation(int blackPopulation) {
        this.blackPopulation = blackPopulation;
    }

    /**
     * Setter for american indian population
     * @param americanIndianPopulation american indian population amount
     */
    public void setAmericanIndianPopulation(int americanIndianPopulation) {
        this.americanIndianPopulation = americanIndianPopulation;
    }

    /**
     * Setter for asian population
     * @param asianPopulation new asian population
     */
    public void setAsianPopulation(int asianPopulation) {
        this.asianPopulation = asianPopulation;
    }

    /**
     * Setter for hawaiian population
     * @param nativeHawaiian new hawaiian population
     */
    public void setNativeHawaiian(int nativeHawaiian) {
        this.nativeHawaiian = nativeHawaiian;
    }

    /**
     * Setter for two or more population
     * @param twoOrMore new two or more population anumber
     */
    public void setTwoOrMore(int twoOrMore) {
        this.twoOrMore = twoOrMore;
    }

    private int whitePopulation, blackPopulation, americanIndianPopulation, asianPopulation, nativeHawaiian, twoOrMore;
    private double densityIndex;
    private boolean flagDensity = false;

    /**
     * Constructor for state county VBL
     * @param whitePopulation White population amount
     * @param blackPopulation black population amount
     * @param americanIndianPopulation American indian population amount
     * @param asianPopulation Asian population amount
     * @param nativeHawaiian Native hawaiian population amount
     * @param twoOrMore two or more population amount
     */
    public StateCountyVbl(int whitePopulation, int blackPopulation, int americanIndianPopulation,
                          int asianPopulation, int nativeHawaiian, int twoOrMore) {
        this.whitePopulation = whitePopulation;
        this.blackPopulation = blackPopulation;
        this.americanIndianPopulation = americanIndianPopulation;
        this.asianPopulation = asianPopulation;
        this.nativeHawaiian = nativeHawaiian;
        this.twoOrMore = twoOrMore;

    }

    /**
     * Construct a new StateCountyVbl object.
     */
    public StateCountyVbl() {
    }

    /**
     * This method clones the object.
     *
     * @return The new cloned object.
     */
    @Override
    public StateCountyVbl clone() {
        return new StateCountyVbl.Sum(this.whitePopulation, this.blackPopulation, this.americanIndianPopulation,
        this.asianPopulation, this.nativeHawaiian, this.twoOrMore);
    }


    /**
     * This method is used to writeOut data from the node
     * @param outStream Outstream for the node
     * @throws IOException inputOutput exception
     */
    @Override
    public void writeOut(OutStream outStream) throws IOException {
        outStream.writeInt(this.getWhitePopulation());
        outStream.writeInt(this.getBlackPopulation());
        outStream.writeInt(this.getAmericanIndianPopulation());
        outStream.writeInt(this.getAsianPopulation());
        outStream.writeInt(this.getNativeHawaiian());
        outStream.writeInt(this.getTwoOrMore());
    }

    /**
     * This method is used to writeOut data from the node
     * @param inStream The instream for the node
     * @throws IOException inputOutput exception
     */
    @Override
    public void readIn(InStream inStream) throws IOException {
        this.whitePopulation = inStream.readInt();
        this.blackPopulation = inStream.readInt();
        this.americanIndianPopulation = inStream.readInt();
        this.asianPopulation = inStream.readInt();
        this.nativeHawaiian = inStream.readInt();
        this.twoOrMore = inStream.readInt();
    }

    /**
     * Setter for object values
     * @param vbl VBL object
     */
    @Override
    public void set(Vbl vbl) {
        this.whitePopulation = ((StateCountyVbl) vbl).getWhitePopulation();
        this.blackPopulation = ((StateCountyVbl) vbl).getBlackPopulation();
        this.americanIndianPopulation = ((StateCountyVbl) vbl).getAmericanIndianPopulation();
        this.asianPopulation = ((StateCountyVbl) vbl).getAsianPopulation();
        this.nativeHawaiian = ((StateCountyVbl) vbl).getNativeHawaiian();
        this.twoOrMore = ((StateCountyVbl) vbl).getTwoOrMore();
    }

    /**
     * Method to get AmericanIndian Pouplation  value
     * @return american Indian population .
     */
    public int getAmericanIndianPopulation() {
        return americanIndianPopulation;
    }

    /**
     * Method to get White Pouplation value
     * @return White population .
     */
    public int getWhitePopulation() {
        return whitePopulation;
    }

    /**
     * Method to get Black Pouplation  value
     * @return Black population .
     */
    public int getBlackPopulation() {
        return blackPopulation;
    }

    /**
     * Method to get Asisan Pouplation  value
     * @return Asian population .
     */
    public int getAsianPopulation() {
        return asianPopulation;
    }

    /**
     * Method to get Two or more Pouplation  value
     * @return Tow or more population .
     */
    public int getTwoOrMore() {
        return twoOrMore;
    }

    /**
     * Method to get Native Hawaiian Pouplation  value
     * @return Native Hawaiian  population .
     */
    public int getNativeHawaiian() {
        return nativeHawaiian;
    }

    /**
     * Reduce method with exception if tehre is any
     * @param whitePopulation White population amount
     * @param blackPopulation black population amount
     * @param americanIndianPopulation American indian population amount
     * @param asianPopulation Asian population amount
     * @param nativeHawaiian Native hawaiian population amount
     * @param twoOrMore two or more population amount
     */
    public void reduce(int whitePopulation, int blackPopulation, int americanIndianPopulation,
                       int asianPopulation, int nativeHawaiian, int twoOrMore) {
        throw new UnsupportedOperationException("reduce() not defined in base class; use a subclass");
    }

    /**
     * Reduce to perform sum reduction
     * @param var1 object of vbl to do reduction with
     */
    public void reduce(Vbl var1) {
        this.reduce(((StateCountyVbl) var1).getWhitePopulation(),
                ((StateCountyVbl) var1).getBlackPopulation(),
                ((StateCountyVbl) var1).getAmericanIndianPopulation(),
                ((StateCountyVbl) var1).getAsianPopulation(),
                ((StateCountyVbl) var1).getNativeHawaiian(),
                ((StateCountyVbl) var1).getTwoOrMore());
    }

    /**
     * For making the density index.
     */
    public void makeDensityIndex(){
        int total = this.whitePopulation + this.blackPopulation + this.americanIndianPopulation +
                this.asianPopulation + this.nativeHawaiian + this.twoOrMore;
        this.densityIndex = (
                        (this.whitePopulation / (double)(total)) * (total - this.whitePopulation) +
                        (this.blackPopulation / (double)(total)) * (total - this.blackPopulation) +
                        (this.americanIndianPopulation / (double)(total)) * (total - this.americanIndianPopulation) +
                        (this.asianPopulation / (double)(total)) * (total - this.asianPopulation) +
                        (this.nativeHawaiian / (double)(total)) * (total - this.nativeHawaiian) +
                        (this.twoOrMore / (double)(total)) * (total - this.twoOrMore)
        );
        this.densityIndex = this.densityIndex/total;
    }

    /**
     * Getting the density index
     */
    public double getDensityIndex() {
        if(!flagDensity){
            this.makeDensityIndex();
            flagDensity = true;
        }
        return densityIndex;
    }

    /**
     * Class StateCountyVbl.Max provides Does reduction based on the values.
     *
     * @author Nikhil Haresh Keswaney
     * @version 12-Dec-2018
     */
    public static class Sum extends StateCountyVbl {
        /**
         * Default constructor for  StateCountyVBL.sum class
         */
        public Sum() {
        }

        /**
         * Constructor for StateCountyVBL.sum class
         * @param whitePopulation White population amount
         * @param blackPopulation black population amount
         * @param americanIndianPopulation American indian population amount
         * @param asianPopulation Asian population amount
         * @param nativeHawaiian Native hawaiian population amount
         * @param twoOrMore two or more population amount
         */
        public Sum(int whitePopulation, int blackPopulation, int americanIndianPopulation,
                   int asianPopulation, int nativeHawaiian, int twoOrMore) {
            super(whitePopulation, blackPopulation, americanIndianPopulation,
             asianPopulation, nativeHawaiian, twoOrMore);
        }

        /**
         * reduce method for StateCountyVbl.sum.
         * @param whitePopulation White population amount
         * @param blackPopulation black population amount
         * @param americanIndianPopulation American indian population amount
         * @param asianPopulation Asian population amount
         * @param nativeHawaiian Native hawaiian population amount
         * @param twoOrMore two or more population amount
         **/
        public void reduce(int whitePopulation, int blackPopulation, int americanIndianPopulation,
                           int asianPopulation, int nativeHawaiian, int twoOrMore) {
            this.setWhitePopulation(this.getWhitePopulation() + whitePopulation);
            this.setBlackPopulation(this.getBlackPopulation()+ blackPopulation);
            this.setAmericanIndianPopulation(this.getAmericanIndianPopulation() + americanIndianPopulation);
            this.setAsianPopulation(this.getAsianPopulation() + asianPopulation);
            this.setNativeHawaiian(this.getNativeHawaiian() + nativeHawaiian);
            this.setTwoOrMore(this.getTwoOrMore() + twoOrMore);
        }
    }
}
