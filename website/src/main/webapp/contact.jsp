<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
    <head>
        <title>Javacloud Contact Page</title>
        <jsp:include page="jsp/include/htmlhead.jsp"/>
    </head>

    <body>

        <jsp:include page="jsp/include/header.jsp"/>

        <!--
        <div class="tm-contact-img-container">
        </div>
        -->

        <section class="tm-section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-xl-6">

                        <section>
                            <h3 class="tm-gold-text tm-form-title">Contact us for more information</h3>
                            <p class="tm-form-description">
                            If you would like to know more information about hosting, want to change your plan,
                            or are having a support issue, please contact us. We respect our customers and want
                            our customers to be sucessful, so please let us know how we can help.
                            </p>


                            <form action="index.jsp" method="post" class="tm-contact-form">
                                <div class="form-group">
                                    <input type="text" id="contact_name" name="contact_name" class="form-control" placeholder="Name"  required/>
                                </div>
                                <div class="form-group">
                                    <input type="email" id="contact_email" name="contact_email" class="form-control" placeholder="Email"  required/>
                                </div>
                                <div class="form-group">
                                    <input type="text" id="contact_subject" name="contact_subject" class="form-control" placeholder="Subject"  required/>
                                </div>
                                <div class="form-group">
                                    <textarea id="contact_message" name="contact_message" class="form-control" rows="6" placeholder="Message" required></textarea>
                                </div>
                            
                                <button type="submit" class="tm-btn">Submit</button>                          
                            </form>   
                        </section>
                        
                        <section class="tm-margin-t-mid tm-map-section">
                            <h3 class="tm-gold-text tm-form-title">Our location</h3>

                            <div id="google-map"></div>

                            <p class="tm-form-description">We are located in Greater Western Sydney, Australia,
                            and aim to cater specifically for Australian based businesses and individuals</p>

                            <p class="m-b-0">If you are interested in partnering with us please let us know.</p>

                        </section>
                 

                    </div>

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-xl-6 tm-contact-right">
                        
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
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
                                        </ul>
                                    </nav>      
                                </div>
                                 
                            </div>

                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
                                
                                <div class="tm-content-box tm-content-box-contact">
                                    <img src="img/tm-img-310x180-1.jpg" alt="Image" class="tm-margin-b-20 img-fluid">
                                    <h4 class="tm-margin-b-20 tm-gold-text">Lorem ipsum dolor #1</h4>
                                    <p class="tm-margin-b-20 tm-p-small">Aenean cursus tellus mauris, quis
                                    consequat mauris dapibus id. Donec
                                    scelerisque porttitor pharetra</p>
                                    <a href="#" class="tm-btn text-uppercase">Detail</a>    
                                </div> 

                                <div class="tm-content-box tm-margin-t-mid tm-content-box-contact">
                                    <img src="img/tm-img-310x180-2.jpg" alt="Image" class="tm-margin-b-20 img-fluid">
                                    <h4 class="tm-margin-b-20 tm-gold-text">Lorem ipsum dolor #2</h4>
                                    <p class="tm-margin-b-20 tm-p-small">Aenean cursus tellus mauris, quis
                                    consequat mauris dapibus id. Donec
                                    scelerisque porttitor pharetra</p>
                                    <a href="#" class="tm-btn text-uppercase">Read More</a>    
                                </div>  

                            </div>
                        </div>
                        <hr class="tm-margin-t-mid">
                        <div class="row tm-contact-row-related-posts">
                            <div class="col-xs-12">
                                
                                <div class="tm-contact-related-posts-container">
                                    <h3 class="tm-gold-text tm-title tm-margin-b-30">Related Posts</h3>
                                    <div class="media tm-related-post">
                                      <div class="media-left media-middle">
                                        <a href="#">
                                          <img class="media-object" src="img/tm-img-240x120-1.jpg" alt="Generic placeholder image">
                                        </a>
                                      </div>
                                      <div class="media-body">
                                        <a href="#"><h4 class="media-heading tm-gold-text tm-margin-b-15">Lorem ipsum dolor</h4></a>
                                        <p class="tm-small-font tm-media-description">Aenean cursus tellus mauris, quis consequat mauris dapibus id. Donec scelerisque porttitor pharetra.</p>
                                      </div>
                                    </div>
                                    <div class="media tm-related-post">
                                      <div class="media-left media-middle">
                                        <a href="#">
                                          <img class="media-object" src="img/tm-img-240x120-2.jpg" alt="Generic placeholder image">
                                        </a>
                                      </div>
                                      <div class="media-body">
                                        <a href="#"><h4 class="media-heading tm-gold-text tm-margin-b-15">Lorem ipsum dolor</h4></a>
                                        <p class="tm-small-font tm-media-description">Aenean cursus tellus mauris, quis consequat mauris dapibus id. Donec scelerisque porttitor pharetra.</p>
                                      </div>
                                    </div>
                                    <div class="media tm-related-post">
                                      <div class="media-left media-middle">
                                        <a href="#">
                                          <img class="media-object" src="img/tm-img-240x120-3.jpg" alt="Generic placeholder image">
                                        </a>
                                      </div>
                                      <div class="media-body">
                                        <a href="#"><h4 class="media-heading tm-gold-text tm-margin-b-15">Lorem ipsum dolor</h4></a>
                                        <p class="tm-small-font tm-media-description">Aenean cursus tellus mauris, quis consequat mauris dapibus id. Donec scelerisque porttitor pharetra.</p>
                                      </div>
                                    </div>
                                </div>                                

                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </section>
        

        <jsp:include page="jsp/include/footer.jsp"/>

        <script>     
       
            /* Google map
            ------------------------------------------------*/
            var map = '';
            var center;

            function initialize() {
                var mapOptions = {
                    zoom: 16,
                    center: new google.maps.LatLng(-33.768955, 150.907624),
                    scrollwheel: false
                };
            
                map = new google.maps.Map(document.getElementById('google-map'),  mapOptions);

                google.maps.event.addDomListener(map, 'idle', function() {
                  calculateCenter();
                });
            
                google.maps.event.addDomListener(window, 'resize', function() {
                  map.setCenter(center);
                });
            }

            function calculateCenter() {
                center = map.getCenter();
            }

            function loadGoogleMap(){
                var script = document.createElement('script');
                script.type = 'text/javascript';
                script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&' + 'callback=initialize';
                document.body.appendChild(script);
            }
        
            // DOM is ready
            $(function() {

                // Google Map
                loadGoogleMap();
            });

        </script>             

</body>
</html>
