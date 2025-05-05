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
