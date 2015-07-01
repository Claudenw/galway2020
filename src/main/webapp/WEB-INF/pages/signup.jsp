<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Galway2020 Sign-up Page</title>
</head>

<body>
    <h1>Sign Up Here!</h1>
    <form class="form-signin">
        <h2 class="form-signin-heading">Please sign up</h2>
        <legend>Enter Your Information</legend>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="">
        <label for="inputFirstName" class="sr-only">First Name</label>
        <input type="firstName" id="inputFirstName" class="form-control" placeholder="First Name" required="">
        <label for="inputLastName" class="sr-only">Last Name</label>
        <input type="lastName" id="inputLastName" class="form-control" placeholder="Last Name" required="">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="">
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
        <button class="btn btn-lg btn-warning btn-block" type="button">Cancel</button>
    </form>
</body>
</html>

