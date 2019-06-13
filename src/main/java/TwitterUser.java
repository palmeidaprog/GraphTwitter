import twitter4j.IDs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TwitterUser implements Serializable {
    private String name;
    private String screeName;
    private long id;
    private List<Long> followers;

    public TwitterUser(String name, String screeName, long id) {
        this.name = name;
        this.screeName = screeName;
        this.id = id;
        this.followers = new ArrayList<>();
    }

    public TwitterUser(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreeName() {
        return screeName;
    }

    public void setScreeName(String screeName) {
        this.screeName = screeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getFollowers() {
        return followers;
    }

    public void addFollower(Long id) {
        this.followers.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwitterUser)) return false;
        TwitterUser that = (TwitterUser) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(screeName, that.screeName) &&
                Objects.equals(followers, that.followers);
    }

    @Override
    public int hashCode() {
        int soma = 0;
        for(Long l : followers) {
            soma += (long) l;
        }
        return name.hashCode() + screeName.hashCode() + ((int) id) + soma;
    }

    @Override
    public String toString() {
        return "TwitterUser{" +
                "name='" + name + '\'' +
                ", screeName='" + screeName + '\'' +
                ", id=" + id +
                ", followers=" + followers +
                '}';
    }
}
