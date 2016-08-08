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

    private static EBeanServerService eBeanServerService = new EBeanServerService();

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
        List<User> users = Ebean.find(User.class).where().eq("name", username).findList();
        return users;
    }

    public List<User> getByEmail(String username) {
        List<User> users = Ebean.find(User.class).where().eq("email", username).findList();
        return users;
    }

    public User getUserByAuth(String username, String password) {
        User user = Ebean.find(User.class).select("id, name, email, status, firstName, lastName").where()
                .eq("name", username).eq("password",password).eq("status", StatusService.Status.ENABLED.name())
                .findUnique();
        return user;
    }

    public static EBeanServerService geteBeanServerService() {
        return eBeanServerService;
    }

    public static void seteBeanServerService(EBeanServerService eBeanServerService) {
        UserService.eBeanServerService = eBeanServerService;
    }

    public static void main(String[] args) {
        try {
            UserService us = new UserService();
            LOG.info("us=" + us);
            User user = us.createUser("test","first","last","email","pass");
//            user.setStatus(StatusService.Status.DISABLED.name());
//            Ebean.save(user);
            LOG.info("user=" + user);
            LOG.info("test="+us.getByUsername("test"));
            LOG.info("te%="+us.getByUsername("te%"));
            LOG.info("auth="+us.getUserByAuth("test","pass"));
        } catch (Exception e) {
            LOG.error(e,e);
        }
    }
}
