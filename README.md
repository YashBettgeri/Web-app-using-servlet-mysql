# Web-app-using-servlet-mysql
Simple Bookstore web application using servlets, jsp, mysql database to understand basic web application workflow

Reference site - https://www.codejava.net/coding/jsp-servlet-jdbc-mysql-create-read-update-delete-crud-example

Tech Stack
1. Java EE
2. MySQL
3. Tomcat server
   

Concepts used - 
1. Web MVC
2. Servlets
3. JSP

Web MVC - It is a design pattern which standardizes the structure of web application and organizes the frontend, backend and the connection between the two. The Model includes the database connection, java objects which will be used throughout the program, business logic of the program. The View includes the frontend or UI. It has HTML, javascript, CSS, JSP files which creates the View of the we application. The controller is the middleman between Model and View and works on connecting the UI and business logic. The requests from UI is routed to the desired business logic, response is processed and sent back to UI via Controller.

Tomcat - It is the web server which contains the Servlet (Control) and JSP (View) and manages the workflow of requests and responses. Incoming requests from client are analyzed and routed to the appropriate servlet by tomcat. Once servlet processes the input and creates response, it sends it tomcat which then routes it to the correct URL to client.

Servlet - Servlet is an API provided by Java EE to handle and process HTTP requests. It acts as the controller of MVC pattern. These are java classes provided by Java EE to process incoming HTTP request, and provide desired HTTP response to client. The lifecycle of these classes is managed by tomcat server which acts as the web server and container for Java Servlet Pages (JSP) and serlvets. So tomcat creates servlets (instantiates servlets using init() method), routes request to desired serlvet, sends responses from serlvets to client, destroys servlets (calls destroy() method when no longer needed)

JSP - Java Server Pages are a type of file which is like HTML but with additional capabilities for handling java code as well. These files act as View of MVC pattern. So these are files where we write the HTML along with java code. They can display the data passed by the servlet by embedding java code inside HTML using jsp tags. However modern practices emphasize use of JSTL

*JSP tags examples - *
1. <%= ... %> -> Output the value of a Java expression directly to the response.
2. <% ... %> -> Write arbitrary Java code inside the HTML page. eg: <% count++; out.println("Count incremented!"); %>

*JSTL (Java Standard Tag Library) -*
1. Core	- c :	Logic, iteration, variable support, etc.
2. Formatting	- fmt :	Formatting numbers, dates, and messages.
3. SQL - sql : Database interaction (discouraged in production).
4. XML - x	: XML manipulation.
5. Functions - fn :	String functions, such as substring, length.
