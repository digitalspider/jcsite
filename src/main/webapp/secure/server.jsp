<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/jsp/include/taglibs.jsp"%>

<!DOCTYPE html>

<html>
    <head>
        <title>jcloud Server</title>
        <jsp:include page="/jsp/include/htmlhead.jsp"/>
    </head>

    <body>
        <jsp:include page="/jsp/include/header.jsp"/>

        <!--
        <div class="tm-blog-img-container">
        </div>
        -->

<%
String username = request.getRemoteUser();
%>
<span>Hello <%= username %>. This is a secure resource</span>

<s:form action="/server.action" method="post" class="tm-contact-form">
	<s:errors/>
	<div class="form-group">
		<input type="name" id="name" name="name" class="form-control" placeholder="Name" required="true"/>
		<s:errors field="name"/>
	</div>
	<div class="form-group">
		<input type="email" id="email" name="email" class="form-control" placeholder="Email" required="true"/>
		<s:errors field="email"/>
	</div>
	<div class="form-group">
		<input type="text" id="subject" name="subject" class="form-control" placeholder="Subject" required="true"/>
		<s:errors field="subject"/>
	</div>
	<div class="form-group">
		<textarea id="message" name="message" class="form-control" rows="6" placeholder="Message" required="true"></textarea>
		<s:errors field="message"/>
	</div>

	<s:submit name="contact" class="tm-btn">Submit</s:submit>
</s:form>

        <section class="tm-section">
            <div class="container-fluid">
                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 col-xl-9">
                        <div class="tm-blog-post">
                            <h3 class="tm-gold-text">Server Page</h3>
                            <p>This is the page for creating or editing a server</p>
                            
                            <p class="domain" id="domain">
                                <span>Domain/Hostname/ServerName:<span>
                                <input type="text" name="domain" placeholder="server" />
                                <span>.jcloud.com.au<span>
                            </p>
                        </div>
                        
                        <div class="row tm-margin-t-small">
                            <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-xl-3">
                                <div class="tm-content-box">
                                    <h4 class="tm-margin-b-20 tm-gold-text">Hardware</h4>
                                    <p class="tm-margin-b-20">
                                        Memory
                                        <select>
                                            <option>128 MB</option>
                                            <option>0.5 GB</option>
                                            <option>1 GB</option>
                                            <option>2 GB</option>
                                            <option>4 GB</option>
                                        </select><br/>
                                        SSD Disk
                                        <select>
                                            <option>0.5 GB</option>
                                            <option>10 GB</option>
                                            <option>20 GB</option>
                                            <option>40 GB</option>
                                            <option>80 GB</option>
                                        </select><br/>
                                        Backups
                                        <select>
                                            <option>None</option>
                                            <option>Weekly</option>
                                            <option>Daily</option>
                                        </select><br/>
                                        Cores
                                        <select>
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                        </select><br/>
                                    </p>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-12 col-md-5 col-lg-5 col-xl-5">
                                <div class="tm-content-box">
                                    <h4 class="tm-margin-b-20 tm-gold-text">System Setup</h4>
                                    <p class="tm-margin-b-20">
                                    <p class="tm-margin-b-20">
                                        Operating System
                                        <select>
                                            <option>Alpine edge</option>
                                            <option>Alpine 3.3</option>
                                            <option>Alpine 3.4</option>
                                            <option>Ubuntu 16.04 - xenial</option>
                                            <option>Ubuntu 14.04 - wily</option>
                                            <option>Ubuntu 12.04 - precise</option>
                                            <option>Centos 6</option>
                                            <option>Centos 7</option>
                                            <option>Debian wheezy</option>
                                            <option>OpenSUSE 13.2</option>
                                            <option>Fedora 22</option>
                                            <option>Fedora 23</option>
                                            <option>Fedora 24</option>
                                        </select><br/>
                                        Database Server
                                        <select>
                                            <option>None</option>
                                            <option>MySQL 5.7</option>
                                            <option>Postgres</option>
                                        </select><br/>
                                        Application Server
                                        <select>
                                            <option>None</option>
                                            <option>Jetty</option>
                                            <option>Tomcat 6.x</option>
                                            <option>Tomcat 7.x</option>
                                            <option>Tomcat 8.x</option>
                                            <option>Tomcat 9.x</option>
                                            <option>JBossAS 5.x</option>
                                            <option>JBossAS 7.x</option>
                                            <option>Wildfly 8.x</option>
                                            <option>Wildfly 9.x</option>
                                            <option>Glassfish 3.x</option>
                                            <option>Glassfish 4.x</option>
                                        </select><br/>
                                        WebServer
                                        <select>
                                            <option>None</option>
                                            <option>Apache 2.x</option>
                                            <option>Nginx</option>
                                        </select><br/>
                                    </p>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 col-xl-4">
                                <div class="tm-content-box">
                                    <h4 class="tm-margin-b-20 tm-gold-text">Application</h4>
                                    <p class="tm-margin-b-20">
                                        Application Bundle
                                        <select>
                                            <option>None / Custom</option>
                                            <option>JSPWiki</option>
                                            <option>Wordpress</option>
                                            <option>JIRA</option>
                                            <option>Confluence</option>
                                            <option>Jenkins</option>
                                            <option>JForum</option>
                                            <option>exoPlatform</option>
                                            <option>SugarCRM</option>
                                            <option>Broadleaf Commerce</option>
                                            <option>Wikipedia</option>
                                            <option>Drupal</option>
                                            <option>Prestashop</option>
                                            <option>Magento</option>
                                            <option>OneHippo</option>
                                            <option>OwnCloud</option>
                                        </select><br/>
                                    </p>
                                </div>  

                            </div>    
                        </div>
                        

                        <div class="row tm-margin-t-small">
                            <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-xl-3">
                                <div class="tm-content-box">
                                    <h4 class="tm-margin-b-20 tm-gold-text">Support</h4>
                                    <p class="tm-margin-b-20">
                                        Support
                                        <select>
                                            <option>None / Self managed</option>
                                            <option>Basic Email</option>
                                            <option>Business Hours</option>
                                            <option>Enterprise 24x7</option>
                                        </select><br/>
                                    </p>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
                                <div class="tm-content-box">
                                    <h4 class="tm-margin-b-20 tm-gold-text">Payment</h4>
                                    <p class="tm-margin-b-20">
                                        Monthly Price&nbsp;<span id="price" name="price" class="price">$12.95</span><br/>
                                        Credit card&nbsp;
                                        <input type="text" placeholder="Credit card number" size="16" maxlength="16" />
                                        <br/>
                                        Expiry&nbsp;
                                        <select>
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                            <option>6</option>
                                            <option>7</option>
                                            <option>8</option>
                                            <option>9</option>
                                            <option>10</option>
                                            <option>11</option>
                                            <option>12</option>
                                        </select>&nbsp;/&nbsp;
                                        <select>
                                            <option>2016</option>
                                            <option>2017</option>
                                            <option>2018</option>
                                            <option>2019</option>
                                            <option>2020</option>
                                        </select>
                                        <br/>
                                        CVC&nbsp;
                                        <input type="text" placeholder="XXX" size="3" maxlength="3" />

                                    </p>
                                </div>
                            </div>
                        </div>

                        Selecting this checkbox binds you to our <a href="#">Terms and Conditions</a>&nbsp;<input type="checkbox" id="terms" name="terms" class="terms"/>
                        <br/>
                        <a href="#" class="tm-btn text-uppercase">Create Server</a>
                    </div>

                    <aside class="col-xs-12 col-sm-12 col-md-4 col-lg-3 col-xl-3 tm-aside-r">

                        <div class="tm-aside-container">
                            <h3 class="tm-gold-text tm-title">
                                Categories
                            </h3>
                            <nav>
                                <ul class="nav">
                                    <li><a href="#" class="tm-text-link">Java development</a></li>
                                    <li><a href="#" class="tm-text-link">Hosting</a></li>
                                    <li><a href="#" class="tm-text-link">Technology news</a></li>
                                    <li><a href="#" class="tm-text-link">Business news</a></li>
                                    <li><a href="#" class="tm-text-link">Other</a></li>
                                </ul>
                            </nav>
                            <hr class="tm-margin-t-small">   
                            <h3 class="tm-gold-text tm-title tm-margin-t-small">
                                Useful Links
                            </h3>
                            <nav>   
                                <ul class="nav">
                                    <li><a href="http://www.java.com" class="tm-text-link">Oracle Java</a></li>
                                    <li><a href="http://www.se-radio.net" class="tm-text-link">SE Radio</a></li>
                                    <li><a href="http://www.mkyong.com/" class="tm-text-link">Mykong</a></li>
                                    <li><a href="http://build-podcast.com" class="tm-text-link">Build Podcast</a></li>
                                    <li><a href="http://www.theserverside.com" class="tm-text-link">The Server Side</a></li>
                                    <li><a href="#" class="tm-text-link">Other..</a></li>
                                </ul>
                            </nav>
                        </div>
                        
                        
                    </aside>

                </div>
                
            </div>
        </section>
        
        <footer class="tm-footer">
            <div class="container-fluid">
                <div class="row">
                    
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 col-xl-3">
                        
                        <div class="tm-footer-content-box">
                            <h3 class="tm-gold-text tm-title tm-footer-content-box-title">Quote</h3>
                            <div class="tm-gray-bg">
                                <p>Making web apps simple!</p>
                                <p><strong>The Java Cloud Team</strong></p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 col-xl-3">
                        <div class="tm-footer-content-box tm-footer-links-container">

                            <h3 class="tm-gold-text tm-title tm-footer-content-box-title">Links</h3>
                            <nav>
                                <ul class="nav">
                                    <li><a href="blog.jsp" class="tm-footer-link">Our Blogs</a></li>
                                    <li><a href="contact.jsp" class="tm-footer-link">Contact Us</a></li>
                                    <li><a href="#" class="tm-footer-link">Privacy Policy</a></li>
                                    <li><a href="#" class="tm-footer-link">Terms and Conditions</a></li>
                                    <li><a href="#" class="tm-footer-link">JIRA Support Portal</a></li>
                                </ul>
                            </nav>

                        </div>

                    </div>

                    <!-- Add the extra clearfix for only the required viewport
                        http://stackoverflow.com/questions/24590222/bootstrap-3-grid-with-different-height-in-each-item-is-it-solvable-using-only
                    -->
                    <div class="clearfix hidden-lg-up"></div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 col-xl-3">

                        <div class="tm-footer-content-box">

                            <h3 class="tm-gold-text tm-title tm-footer-content-box-title">Support</h3>
                            <p class="tm-margin-b-30">If your container is not responding, please use the contact us page and submit a request. We will address this ASAP!</p><hr class="tm-margin-b-30">
                            <p class="tm-margin-b-30">If your container is working, but your application is failing, please look at our support contracts.</p>
                            <a href="about.jsp" class="tm-btn tm-btn-gray text-uppercase">Support</a>

                        </div>

                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 col-xl-3">

                        <div class="tm-footer-content-box">
                            <h3 class="tm-gold-text tm-title tm-footer-content-box-title">Partners</h3>
                            <p class="tm-margin-b-20">If you would like to partner with us, please contact us through the contact page</p>
                            <a href="contact.jsp" class="tm-btn tm-btn-gray text-uppercase">Contact</a>
                        </div>
                        
                    </div>


                </div>

                <div class="row">
                    <div class="col-xs-12 tm-copyright-col">
                        <p class="tm-copyright-text">Copyright 2016 Java Cloud</p>
                    </div>
                </div>
            </div>
        </footer>

        <!-- load JS files -->
        <script src="${ctx}/js/jquery-1.11.3.min.js"></script>             <!-- jQuery (https://jquery.com/download/) -->
        <script src="https://www.atlasestateagents.co.uk/javascript/tether.min.js"></script> <!-- Tether for Bootstrap, http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h --> 
        <script src="${ctx}/js/bootstrap.min.js"></script>                 <!-- Bootstrap (http://v4-alpha.getbootstrap.com/) -->
       


</html>