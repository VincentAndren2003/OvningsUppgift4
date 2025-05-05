import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Record;
import java.util.*;

public class Exercise4 {
    private final ListGraph locationGraph = new ListGraph();
    private final ListGraph recommendationGraph  = new ListGraph();

    public void loadLocationGraph(String fileName){
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String nodesLine = reader.readLine();
            String[] tokens =  nodesLine.split(";");
            for (int i = 0; i < tokens.length; i+=3){

                locationGraph.add(new Location(tokens[i], Double.parseDouble(tokens[i+1]), Double.parseDouble(tokens[i+2])));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] edgeData = line.split(";");

                Node from = null;
                Node to = null;
                for (Node node : locationGraph.getNodes()) {
                    if (node.getName().equals(edgeData[0])){
                        from = node;
                        break;
                    }
                }
                for (Node node : locationGraph.getNodes()) {
                    if (node.getName().equals(edgeData[1])){
                        to = node;
                        break;
                    }
                }

                String name = edgeData[2];
                int weight = Integer.parseInt(edgeData[3]);

                locationGraph.connect(from, to, name, weight);
            }
            System.out.println(locationGraph);

            fileReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.printf("%s not found%n", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRecommendationGraph(String fileName) {
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");

                String personName = tokens[0];
                String recordName = tokens[1];
                String recordArtist = tokens[2];
                if (tokens.length > 3){
                    recordName = tokens[1] + ";" + tokens[2];
                    recordArtist = tokens[3];
                }

                //Check if person and record exists
                Node person = null;
                for (Node node : recommendationGraph.getNodes()) {
                    if (node.getName().equals(personName)){
                        person = node;
                        break;
                    }
                }
                if (person == null){
                    person = new Person(personName);
                    recommendationGraph.add(person);
                }

                Node record = null;
                for (Node node : recommendationGraph.getNodes()) {
                    if (node.getName().equals(recordName)){
                        record = node;
                        break;
                    }
                }
                if (record == null){
                    record = new Record(recordName, recordArtist);
                    recommendationGraph.add(record);
                }

                recommendationGraph.connect(person, record, "", 0);
            }

            System.out.println(recommendationGraph);

            fileReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.printf("%s not found%n", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record record) {
        //Get list of all people owning record
        List<Node> people = new ArrayList<>();
        for (Edge edge : recommendationGraph.getEdgesFrom((Node) record)) {
            people.add(edge.getDestination());
        }

        //Get all records from all people owing record
        Set<Record> allRecords = new TreeSet<>();
        for (Node node : people) {
            recommendationGraph.getEdgesFrom(node).forEach(edge -> allRecords.add((Record) edge.getDestination()));
        }
        SortedMap<Integer, SortedSet<Record>> sortedMap = new TreeMap<>(Comparator.reverseOrder());
        //Loop throiugh reciords, if popularity is unique, make new sorted set and add, if not find sorted set and add
        for (Record r : allRecords) {
            if (sortedMap.containsKey(getPopularity(r))){
                //Popularity is not unique
                sortedMap.get(getPopularity(r)).add(r);
            }
            else{
                SortedSet<Record> recSet  = new TreeSet<>();
                recSet.add(r);
                sortedMap.put(getPopularity(r), recSet);
            }
        }
        return sortedMap;
    }

    public int getPopularity(Record record) {
        return recommendationGraph.getEdgesFrom((Node) record).size();
    }

    public SortedMap<Integer, Set<Record>> getTop5() {
        //Get list of all people owning record
        Set<Record> allRecords = new TreeSet<>();
        for (Node node: recommendationGraph.getNodes()){
            if (node instanceof Record){
                allRecords.add((Record) node);
            }
        }

        SortedMap<Integer, Set<Record>> sortedMap = new TreeMap<>(Comparator.reverseOrder());
        //Loop throiugh reciords, if popularity is unique, make new sorted set and add, if not find sorted set and add
        for (Record r : allRecords) {
            if (sortedMap.containsKey(getPopularity(r))){
                //Popularity is not unique
                sortedMap.get(getPopularity(r)).add(r);
            }
            else{
                SortedSet<Record> recSet  = new TreeSet<>();
                recSet.add(r);
                sortedMap.put(getPopularity(r), recSet);
            }
        }

        Integer[] popularity = sortedMap.keySet().toArray(new Integer[sortedMap.size()]);

        SortedMap<Integer, Set<Record>> top5 = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 0; i < sortedMap.size(); i++){
            if (i > 5){
                break;
            }
            top5.put(popularity[i], sortedMap.get(popularity[i]));
        }
        return top5;
    }
}
