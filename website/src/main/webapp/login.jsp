<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Javacloud Login</title>
<!--
Classic Template
http://www.templatemo.com/tm-488-classic
-->
    <!-- load stylesheets -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400">  <!-- Google web font "Open Sans" -->
    <link rel="stylesheet" href="css/bootstrap.min.css">                                      <!-- Bootstrap style -->
    <link rel="stylesheet" href="css/templatemo-style.css">                                   <!-- Templatemo style -->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
          <![endif]-->
</head>

    <body>
       
        <div class="tm-header">
            <div class="container-fluid">
                <div class="tm-header-inner">
                    <a href="index.jsp" class="navbar-brand tm-site-name">JavaCloud</a>
                    
                    <!-- navbar -->
                    <nav class="navbar tm-main-nav">

                        <button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#tmNavbar">
                            &#9776;
                        </button>
                        
                        <div class="collapse navbar-toggleable-sm" id="tmNavbar">
                            <ul class="nav navbar-nav">
                                <li class="nav-item">
                                    <a href="about.jsp" class="nav-link">Services</a>
                                </li>
                                <li class="nav-item">
                                    <a href="blog.jsp" class="nav-link">Blog</a>
                                </li>
                                <li class="nav-item">
                                    <a href="contact.jsp" class="nav-link">Contact</a>
                                </li>
                                <li class="nav-item">
                                    <a href="login.jsp" class="nav-link">Login</a>
                                </li>
                            </ul>                        
                        </div>
                        
                    </nav>  

                </div>                                  
            </div>            
        </div>

        <section class="tm-section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">

                        <form method="post" action="login.jsp"  class="tm-login-form">
                            <div class="h4">Login</div>
                            <div class="form-group">
                                <input type="text" id="login_username" name="login_username" class="form-control" placeholder="Username"  required/>
                            </div>
                            <div class="form-group">
                                <input type="password" id="login_password" name="login_password" class="form-control" placeholder="Password"  required/>
                            </div>
                            <input type="submit" value="Login" class="tm-btn text-uppercase" />
                        </form>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">
                        <form method="post" action="login.jsp" class="tm-login-form">
                            <div class="h4">Register</div>
                            <div class="form-group">
                                <input type="text" id="login_firstname" name="login_firstname" class="form-control" placeholder="First Name"  required/>
                            </div>
                            <div class="form-group">
                                <input type="text" id="login_lastname" name="login_lastname" class="form-control" placeholder="Last Name"  required/>
                            </div>
                            <div class="form-group">
                                <input type="email" id="login_email" name="login_email" class="form-control" placeholder="Email"  required/>
                            </div>
                            <div class="form-group">
                                <input type="username" id="login_username" name="login_username" class="form-control" placeholder="Username"  required/>
                            </div>
                            <div class="form-group">
                                <input type="password" id="login_password" name="login_password" class="form-control" placeholder="Password"  required/>
                            </div>
                            <input type="submit" value="Register" class="tm-btn text-uppercase"/>
                        </form>
                    </div>

                </div> <!-- row -->

            </div>
        </section>

        <!-- load JS files -->
        <script src="js/jquery-1.11.3.min.js"></script>             <!-- jQuery (https://jquery.com/download/) -->
        <script src="https://www.atlasestateagents.co.uk/javascript/tether.min.js"></script> <!-- Tether for Bootstrap, http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h --> 
        <script src="js/bootstrap.min.js"></script>                 <!-- Bootstrap (http://v4-alpha.getbootstrap.com/) -->
       
</body>
</html>
