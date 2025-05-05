import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.SortedMap;

public class Exercise4 {


    public void loadLocationGraph(String fileName){
        ListGraph graph = new ListGraph();

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
                for (Node node : graph.getNodes()) {
                    if (node.getName().equals(edgeData[0])){
                        from = node;
                        break;
                    }
                }
                for (Node node : graph.getNodes()) {
                    if (node.getName().equals(edgeData[1])){
                        to = node;
                        break;
                    }
                }

                String name = edgeData[2];
                int weight = Integer.parseInt(edgeData[3]);

                graph.connect(from, to, name, weight);
            }
            System.out.println(graph);

            fileReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.printf("%s not found%n", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRecommendationGraph(String fileName) {
        ListGraph graph = new ListGraph();

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
                for (Node node : graph.getNodes()) {
                    if (node.getName().equals(personName)){
                        person = node;
                        break;
                    }
                }
                if (person == null){
                    person = new Person(personName);
                    graph.add(person);
                }
                Node record = null;
                for (Node node : graph.getNodes()) {
                    if (node.getName().equals(recordName)){
                        record = node;
                        break;
                    }
                }
                if (record == null){
                    record = new Record(recordName, recordArtist);
                    graph.add(record);
                }
                graph.connect(person, record, "", 0);
            }

            System.out.println(graph);

            fileReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.printf("%s not found%n", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record record) {
       return null;
    }

    public int getPopularity(Record record) {
       return -1;
    }

    public SortedMap<Integer, Set<Record>> getTop5() {
       return null;
    }



}
