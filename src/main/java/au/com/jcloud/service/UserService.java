package au.com.jcloud.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;

import org.apache.log4j.Logger;

import java.util.List;

import au.com.jcloud.emodel.User;

/**
 * Created by david on 5/08/16.
 */
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);

    public UserService() {
        EBeanServerService.init();
    }

    public User createUser(String username, String firstName, String lastName, String email, String password) throws Exception {
        User user = new User();
        user.setName(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setStatus(StatusService.Status.ENABLED.name());
        LOG.info("creating new user: "+user);

        Ebean.save(user);
        // EBeanServerService.getEBeanServer().save(user);
        return user;
    }

    public User get(int id) {
        User user = Ebean.find(User.class, id);
        return user;
    }

    public List<User> getByUsername(String username) {
        List<User> users = Ebean.find(User.class).where().ilike("name", username).findList();
        return users;
    }

    public User getUserByAuth(String username, String password) {
        User user = Ebean.find(User.class).fetch("id").where().eq("name", username).eq("password",password).findUnique();
        return user;
    }

    public static void main(String[] args) {
        try {
            UserService us = new UserService();
            LOG.info("us=" + us);
            EbeanServer e2 = EBeanServerService.getEBeanServer();
            User user = us.createUser("test","first","last","email","pass");
            LOG.info("user=" + user);
            LOG.info("test="+us.getByUsername("test"));
            LOG.info("te="+us.getByUsername("te"));
        } catch (Exception e) {
            LOG.error(e,e);
        }
    }
}
