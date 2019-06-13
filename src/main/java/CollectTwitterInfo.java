import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.sql.SQLOutput;

public class CollectTwitterInfo {
    private Twitter twitter;
    private TwitterUserController users;
    private String usuarioInicial;



    public CollectTwitterInfo(String usuarioInicial) {
        this.usuarioInicial = usuarioInicial;
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey("qxVvf5GgQrpCtejaIGnPfAzUq")
            .setOAuthConsumerSecret("HE6I2qWeqTwTD0xay6MgV9ShrCHaHPRKKy139MZjxEbh3OjtY8")
            .setOAuthAccessToken("230500949-4fv2N0n1G3MKWhZiTM9vI66w3YwdbdteHioMcBYf")
            .setOAuthAccessTokenSecret("2fqSwtxMbmmhtFPVjyI7koaIwLu1oapJGJGh6U9GhM03d");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        try {
            users = new TwitterUserController();
        } catch(IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public TwitterUserController getUsers() {
        return users;
    }

    public String getUsuarioInicial() {
        return usuarioInicial;
    }

    private void getUserInfo(Long id) throws TwitterException, IOException {
        // if user exists do nothing
        if(users.getUser(id) == null) {
            User user = twitter.showUser(id);
            TwitterUser twitterUser = new TwitterUser(user.getName(),
                    user.getScreenName(), user.getId());
            long ids[] = twitter.getFollowersIDs(user.getId(), -1)
                    .getIDs();
            for (int i = 0; i < 5 && i < ids.length; i++) {
                twitterUser.addFollower(ids[i]);
            }
            users.addUser(twitterUser);
        }
    }

    public void run() {
        TwitterUser twitterUser;

        try { // add first user to the graph
            if(users.getNextToQuery() == null) {
                User user = twitter.showUser(usuarioInicial);
                getUserInfo(user.getId());
                users.collectionDone();
            }
        } catch(TwitterException e) {
            System.out.println("Exception:" + e.getMessage());
            return ;
        } catch(IOException ioe) {
            System.out.println("Exception: " + ioe.getMessage());
            return;
        }

        while(true) { // fill graph
            Long next;
            System.out.println("Restarting queries...");
            try {
                while((next = users.getNextToQuery()) != null) {
                    getUserInfo(next);
                    users.collectionDone();
                }
            } catch(TwitterException e) {
                System.out.println("Waiting 15 minutes... [" + e.getMessage()
                        + "]");
                try {
                    Thread.sleep(15*60*1000);
                } catch(InterruptedException threadExc) {
                    // do nothing
                }
            } catch(IOException ioe) {
                System.out.println("Exception: " + ioe.getMessage());
            }
        }
    }
}
