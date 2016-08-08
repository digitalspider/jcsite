package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.log4j.Logger;

import au.com.jcloud.context.JCActionBeanContext;
import au.com.jcloud.service.EmailService;

/**
 * Created by david.vittor on 3/08/16.
 */
@UrlBinding("/contact.action")
public class ContactActionBean implements ActionBean {
    private static final Logger LOG = Logger.getLogger(ContactActionBean.class);

    private JCActionBeanContext context;
    private String name;
    private String email;
    private String subject;
    private String message;

    @DefaultHandler
    public Resolution contact() {
        try {
            EmailService emailService = new EmailService();
            emailService.sendEmail(name, email, subject, message);
        } catch (Exception e) {
            LOG.error(e,e);
        }
        return new ForwardResolution("contact.jsp");
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = (JCActionBeanContext)context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
