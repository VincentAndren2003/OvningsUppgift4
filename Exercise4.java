import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Exercise4 {
    private Graph<Node> graph = new ListGraph<>();

    public void loadLocationGraph(String fileName){
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String nodesLine = reader.readLine();
            String[] tokens =  nodesLine.split(";");
            for (int i = 0; i < tokens.length; i+=3){

                graph.add(new Location(tokens[i], Double.parseDouble(tokens[i+1]), Double.parseDouble(tokens[i+2])));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] edgeData = line.split(";");

                Node from = null;
                Node to = null;
                for (Object obj : graph.getNodes()) {
                    Node node = (Node) obj;
                    if (node.getName().equals(edgeData[0])){
                        from = node;
                        break;
                    }
                }
                for (Object obj : graph.getNodes()) {
                    Node node = (Node) obj;
                    if (node.getName().equals(edgeData[1])){
                        to = node;
                        break;
                    }
                }

                String name = edgeData[2];
                int weight = Integer.parseInt(edgeData[3]);

                graph.connect(from, to, name, weight);
            }
            //System.out.println(graph);

            fileReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            //System.out.printf("%s not found%n", fileName);
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

                Node person = findOrCreatePerson(personName);
                Node record = findOrCreateRecord(recordName, recordArtist);

                graph.connect(person, record, "", 0);
            }

            //System.out.println(graph);

            fileReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            //System.out.printf("%s not found%n", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node findOrCreatePerson(String name) {
        for (Object obj : graph.getNodes()) {
            Node node = (Node) obj;
            if (node.getName().equals(name)) return node;
        }
        Node person = new Person(name);
        graph.add(person);
        return person;
    }
    private Node findOrCreateRecord(String name, String artist) {
        for (Object obj : graph.getNodes()) {
            Node node = (Node) obj;
            if (node.getName().equals(name)) return node;
        }
        Node record = new Record(name, artist);
        graph.add(record);
        return record;
    }

    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record record) {
        //Get list of all people owning record
        List<Node> people = new ArrayList<>();
        for (Object obj : graph.getEdgesFrom((Node) record)) {
            Edge edge = (Edge) obj;
            people.add((Node)edge.getDestination());
        }

        //Get all records from all people owing record
        Set<Record> allRecords = new TreeSet<>(Comparator.comparing(Node::getName));

        for (Node node : people) {
            graph.getEdgesFrom(node).forEach(obj -> {
                Edge edge = (Edge) obj;
                allRecords.add((Record) edge.getDestination());
            });
        }
        SortedMap<Integer, SortedSet<Record>> sortedMap = new TreeMap<>(Comparator.reverseOrder());
        //Loop throiugh reciords, if popularity is unique, make new sorted set and add, if not find sorted set and add
        for (Record r : allRecords) {
            if (sortedMap.containsKey(getPopularity(r))){
                //Popularity is not unique
                sortedMap.get(getPopularity(r)).add(r);
            }
            else{
                SortedSet<Record> recSet = new TreeSet<>(Comparator.comparing(Node::getName));
                recSet.add(r);
                sortedMap.put(getPopularity(r), recSet);
            }
        }
        return sortedMap;
    }

    public int getPopularity(Record record) {
        return graph.getEdgesFrom((Node) record).size();
    }

    public SortedMap<Integer, Set<Record>> getTop5() {
        //Get list of all people owning record
        Set<Record> allRecords = new TreeSet<>(Comparator.comparing(Node::getName));
        for (Object obj: graph.getNodes()){
            Node node = (Node) obj;
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
                SortedSet<Record> recSet = new TreeSet<>(Comparator.comparing(Node::getName));
                recSet.add(r);
                sortedMap.put(getPopularity(r), recSet);
            }
        }

        Integer[] popularity = sortedMap.keySet().toArray(new Integer[sortedMap.size()]);

        SortedMap<Integer, Set<Record>> top5 = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 0; i < sortedMap.size(); i++){
            if (i > 4){
                break;
            }
            top5.put(popularity[i], sortedMap.get(popularity[i]));
        }
        return top5;
    }
}
