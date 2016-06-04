# JavaCloud Framework

The JavaCloud Framework (jcframe) makes building Java web CRUD applications really simple.

The process is:
* Create a new database table schema, or get the connection properties to existing database
* Use a tool to generate a series of *beans* against the database schema
 * e.g. [oracle jpa page](http://www.oracle.com/technetwork/developer-tools/eclipse/jpatutorial-2-092215.html) or [eclipse jpa page](http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jpt.doc.user%2Ftasks021.htm)
 * A *bean* is an object representing a table in the database
* Import this JCFrame jar file into your maven
 * Download the source code using <code> git clone https://github.com/digitalspider/jcsite.git </code>
 * Run <code>maven clean install</code>
* Create a new WebApplication project and include the maven depenedency in *pom.xml*
```xml
    <!-- JavaCloud Framework -->
    <dependency>
      <groupId>au.com.javacloud</groupId>
      <artifactId>jcframe</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependenc>y
```
* Create a new controller for each *bean*, e.g. the below is created for the *Page* bean:
```
import java.security.Principal;
import javax.servlet.annotation.WebServlet;
import au.com.javacloud.model.Page;

@WebServlet(urlPatterns = {"/page/*", "/page.json/*"})
public class PageController extends BaseControllerImpl<Page,Principal> {

    public PageController() {
		super(Page.class);
	}

}
```
* Create a new file for the database configuration in
 * src/main/resources/**db.properties*
```
# MySQL
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/TestDB
username=test
password=test

# SQLite
#driver=org.sqlite.JDBC
#url=jdbc:sqlite:${REALPATH}database.db
#username=test
#password=test
```
* Create a new file for the javacloud configuration in
 * src/main/resources/**jc.properties*
```
# JavaCloud configuration file

package.model.name=au.com.javacloud.model
#auth.class=au.com.javacloud.auth.BaseAuthServiceImpl
#ds.class=au.com.javacloud.dao.BaseDataSource
#ds.config.file=db.properties.sample
```
* Build your application
** <code>maven package</code>
* Deploy your application
** Place the war file into a tomcat "webapps" directory