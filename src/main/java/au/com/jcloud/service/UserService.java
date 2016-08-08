package au.com.jcloud.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

import au.com.jcloud.emodel.User;

/**
 * Created by david on 5/08/16.
 */
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);

    private static EBeanServerService eBeanServerService = new EBeanServerService();
    private EncryptService encryptService = new EncryptService();

    public User createUser(String username, String firstName, String lastName, String email, String password) throws Exception {
        List<User> exists = getByUsername(username);
        if (exists.size()>0) {
            throw new Exception("Username already exists! Please select a different one");
        }
        exists = getByEmail(email);
        if (exists.size()>0) {
            throw new Exception("This email is already registered! Please select a different one");
        }
        User user = new User();
        user.setName(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        String passValue = encryptService!=null ? encryptService.md5(password) : password;
        user.setPassword(passValue);
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
        String passValue = encryptService!=null ? encryptService.md5(password) : password;
        User user = null;
        if (StringUtils.isNotBlank(username)) {
            user = Ebean.find(User.class).select("id, name, email, status, firstName, lastName").where()
                    .or(Expr.eq("name", username), Expr.eq("email", username)).eq("password", passValue).eq("status", StatusService.Status.ENABLED.name())
                    .findUnique();
        }
        return user;
    }

    public static EBeanServerService geteBeanServerService() {
        return eBeanServerService;
    }

    public static void seteBeanServerService(EBeanServerService eBeanServerService) {
        UserService.eBeanServerService = eBeanServerService;
    }

    protected EncryptService getEncryptService() {
        return encryptService;
    }

    public void setEncryptService(EncryptService encryptService) {
        this.encryptService = encryptService;
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
