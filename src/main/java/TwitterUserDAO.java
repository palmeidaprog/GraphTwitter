import java.io.*;
import java.util.*;

public class TwitterUserDAO {
    private String usersFile, queueFile;
    private Map<Long, TwitterUser> users;
    private Queue<Long> toQuery;
    private File f, q;

    public TwitterUserDAO(String usersFile, String queueFile) throws
            IOException {
        this.usersFile = usersFile;
        this.queueFile = queueFile;
        f = new File(usersFile);
        q = new File(queueFile);
        checkFile();
    }

    private void checkFile() throws IOException {
        if(!f.exists() || !q.exists()) {
            users = new HashMap<>();
            toQuery = new ArrayDeque<>();
            save();
        } else {
            load();
        }
    }

    public void addUser(TwitterUser user) throws IOException {
        this.users.put(user.getId(), user);
        save();
    }

    public void addToQueue(Long id) throws IOException {
        toQuery.offer(id);
        save();
    }

    public void addToQueue(List<Long> ids) throws IOException {
        for(Long id : ids) {
            toQuery.offer(id);
        }
        save();
    }

    public Long getNextInQueue() {
        return toQuery.peek();
    }

    public void lastDone() throws IOException {
        Long l = toQuery.poll(); // removes
        save();
    }

    public TwitterUser getUser(Long id) {
        return users.get(id);
    }

    @SuppressWarnings("unchecked")
    public void load() throws IOException {
        if(!f.exists()) {
            throw new IOException("File " + usersFile + " doesn't exist");
        } else if(!q.exists()) {
            throw new IOException("File " + queueFile + " doesn't exist");
        }

        //toQuery = (ArrayDeque<Long>) obj.readObject();
        try(ObjectInputStream obj = new ObjectInputStream(
                new FileInputStream(f))) {
            users = (HashMap<Long, TwitterUser>) obj.readObject();
        } catch (ClassNotFoundException c) {
            throw new IOException("File " + usersFile +
                    " doesn't contain TwitterUsers");
        } catch (IOException e) {
            throw new IOException("File " + usersFile +
                    " couldn't be read");
        }

        try(ObjectInputStream obj = new ObjectInputStream(
                new FileInputStream(q))) {
            toQuery = (ArrayDeque<Long>) obj.readObject();
        } catch (ClassNotFoundException c) {
            throw new IOException("File " + queueFile +
                    " doesn't contain TwitterUsers");
        } catch (IOException e) {
            throw new IOException("File " + queueFile +
                    " couldn't be read");
        }
    }

    public void save() throws IOException {
        try(ObjectOutputStream obj = new ObjectOutputStream(new
                FileOutputStream(f))) {
            obj.writeObject(users);
        } catch (IOException e) {
            throw new IOException("File " + usersFile + " couldn't be saved");
        }

        try(ObjectOutputStream obj = new ObjectOutputStream(new
                FileOutputStream(q))) {
            obj.writeObject(toQuery);
        } catch (IOException e) {
            throw new IOException("File " + queueFile + " couldn't be saved");
        }
    }

    public Map<Long, TwitterUser> getHashCopy() {
        return new HashMap<>(users);
    }

    public void debug() {
        System.out.println("Users:");
        for(TwitterUser u : users.values()) {
            System.out.println(u);
        }
        System.out.println("\nQueue:");
        for(Long l : toQuery) {
            System.out.println(l);
        }
    }
}
