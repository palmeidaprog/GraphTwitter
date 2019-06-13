import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import twitter4j.Twitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TwitterUserController {
    private TwitterUserDAO dao;
    private Graph<TwitterUser, DefaultEdge> graph;

    public TwitterUserController() throws IOException {
        dao = new TwitterUserDAO("usuarios.dat", "fila.dat");
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        //dao.addToQueue(new Long(331129823));
    }

    public TwitterUserController(String usersFile, String queueFile) throws
            IOException {
        dao = new TwitterUserDAO(usersFile, queueFile);
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public void addUser(TwitterUser twitterUser) throws IOException {
        dao.addUser(twitterUser);
        dao.addToQueue(twitterUser.getFollowers());
    }

    public TwitterUser getUser(Long id) {
        return dao.getUser(id);
    }

    public Long getNextToQuery() {
        return dao.getNextInQueue();
    }

    public void collectionDone() throws IOException {
        dao.lastDone();
    }

    public Map<Long, TwitterUser> getGraphCopy() {
        return dao.getHashCopy();
    }

    public Graph<TwitterUser, DefaultEdge> getGraph() {
        Map<Long, TwitterUser> hash = dao.getHashCopy();
        for(TwitterUser t : hash.values()) {
            graph.addVertex(t);
        }

        for(TwitterUser t : hash.values()) {
            for(Long id : t.getFollowers()) {
                TwitterUser checking = dao.getUser(id);
                if(checking != null) {
                    graph.addEdge(t, checking);
                }
            }
        }
        return graph;
    }

    public void debug() {
        dao.debug();
    }
}
