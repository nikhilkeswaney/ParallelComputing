//******************************************************************************
//
// File:    DivIndex.java
// This program calculates the diversity index of the given data set. Based
// On the year and the states.
//
//******************************************************************************


import edu.rit.pjmr.Combiner;
import edu.rit.pjmr.Customizer;
import edu.rit.pjmr.Mapper;
import edu.rit.pjmr.PjmrJob;
import edu.rit.pjmr.Reducer;
import edu.rit.pjmr.TextFileSource;
import edu.rit.pjmr.TextId;

import java.util.HashSet;

/**
 * This class makes the diversity index for the state specifies and the year.
 * @author  Nikhil Keswaney
 * @version 12-Dec-2018
 */
public class DivIndex
        extends PjmrJob<TextId,String,StateCounty,StateCountyVbl> {

    /**
     * main program.
     *
     * @param args Command line arguments.
     */
    public void main(String[] args) {
        if (args.length < 3) usage();
        int NT = Math.max(threads(), 1);
        String[] nodes = args[0].split(",");
        String file = args[1];
        String[] argsMapper;
        String year = args[2];
        int n = -1;
        try {
            n = Integer.parseInt(year);
        } catch (Exception e) {
            usage();
        }
        if (n <= 0) {
            usage();
        }
        if (args.length > 3) {
            argsMapper = new String[args.length - 2];
            argsMapper[0] = year;
            HashSet<String> ar = new HashSet<>();
            for(int i = 3; i < args.length; i++){
                if(!ar.contains(args[i])){
                    argsMapper[i - 2] = args[i];
                    ar.add(args[i]);
                } else {
                    usage();
                }
            }
        } else {
            argsMapper = new String[]{year, null};
        }
        for (String node : nodes)
            mapperTask(node)
                    .source(new TextFileSource(file))
                    .mapper(NT, MyMapper.class, argsMapper  );

        // Configure reducer task.
        reducerTask()
                .customizer(MyCustomizer.class)
                .reducer(MyReducer.class);

        startJob();
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println("java pj2 jar=<jar> threads=<NT> DivIndex <nodes> <file> <year> [ \"<state>\" ... ]\n" +
                "<jar> is the name of the JAR file containing all of the program's Java class files.\n" +
                "<NT> is the number of mapper threads per mapper task; if omitted, the default is 1.\n" +
                "<nodes> is a comma-separated list of cluster node names on which to run the analysis. One or more node names must be specified.\n" +
                "<file> is the name of the file on each node's local hard disk containing the census data to be analyzed.\n" +
                "<year> is the year to be analyzed. It must be an integer from 1 to 10.\n" +
                "<state> is a state name to be analyzed. There can be zero or more state names. A particular state name must not appear more than once.]");
        terminate(1);
    }

    /**
     * Mapper class.
     */
    private static class MyMapper extends Mapper<TextId, String, StateCounty, StateCountyVbl> {

        HashSet<String> states;
        String year;
        boolean allFlag = false;

        /**
         * The start method to confugre parameters for the mapper
         * @param args arguments passed by the main program
         * @param combiner Combiner used for the mapper
         */
        public void start(String[] args, Combiner<StateCounty, StateCountyVbl> combiner)
        {
            states = new HashSet<>();
            year = args[0];
            if (args[1] != null) {
                for (int i = 1; i < args.length; i++) {
                    states.add(args[i]);
                }
            }
            else {
                allFlag = true;
            }
        }

        /**
         * This is used for to process diffrent text data in diffrent fil
         * @param id Id of the text in map
         * @param contents The contents of the text
         * @param combiner The combiner in which the words are stored
         */
        public void map(TextId id, String contents, Combiner<StateCounty, StateCountyVbl> combiner) {
            String data[];
            data = contents.split(",");
            if(data[6].equals("0") && data[5].equals(year) && (allFlag || states.contains(data[3]))) {
                StateCountyVbl.Sum temp = new StateCountyVbl.Sum(Integer.parseInt(data[10]) + Integer.parseInt(data[11]),
                        Integer.parseInt(data[12]) + Integer.parseInt(data[13]),
                        Integer.parseInt(data[14]) + Integer.parseInt(data[15]),
                        Integer.parseInt(data[16]) + Integer.parseInt(data[17]),
                        Integer.parseInt(data[18]) + Integer.parseInt(data[19]),
                        Integer.parseInt(data[20]) + Integer.parseInt(data[21]));
                combiner.add(new StateCounty(data[3], data[4]), temp);
                combiner.add(new StateCounty(data[3], " "), temp);
            }
        }
    }
    /**
     * Reducer task customizer class.
     */
    private static class MyCustomizer
            extends Customizer<StateCounty, StateCountyVbl> {
        /**
         * Comes before is a method used to sort the data
         * @param key_1 Key1 to be compared
         * @param value_1 value1 to be coompared
         * @param key_2 Key2 to be compared
         * @param value_2 value2 to be compared
         * @return true, false
         */
        public boolean comesBefore(StateCounty key_1, StateCountyVbl value_1,
                                   StateCounty key_2, StateCountyVbl value_2) {
                if (key_1.getState().compareTo(key_2.getState()) < 0) {
                    return true;
                } else if (key_1.getState().compareTo(key_2.getState()) > 0) {
                    return false;
                } else {
                    if (key_1.getCounty().equals(" ")) {
                        return true;
                    }
                    else if (key_2.getCounty().equals(" ")){
                        return false;
                    }
                    else if (value_1.getDensityIndex() < value_2.getDensityIndex()) {
                        return false;
                    } else {
                        return true;
                    }
                }
        }
    }

    /**
     * Reducer class.
     */
    private static class MyReducer
            extends Reducer<StateCounty, StateCountyVbl> {

        /**
         * This class is used for printing all the values
         * @param key key i.e the state name
         * @param value value i.e diversity index of the key
         */
        public void reduce
                (StateCounty key,
                 StateCountyVbl value)
        {
            if(key.getCounty().equals(" "))
                System.out.printf ("%s\t\t%.5g%n", key.getState(), value.getDensityIndex());
            else
                System.out.printf ("\t%s\t%.5g%n", key.getCounty(), value.getDensityIndex());
            System.out.flush();
        }
    }
}

