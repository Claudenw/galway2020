<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><decorator:title/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/bootstrap/3.3.4/css/bootstrap.min.css" media="all"/>
    <!-- Custom styles for this template -->
    <link href="css/dashboard.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <decorator:head/>
</head>

<body>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!--<a class="navbar-brand" href="#">Galway2020 Dashboard</a>-->
                <a title="Galway 2020 Dashboard" href="${pageContext.request.contextPath}" style="max-width:100px; max-height:100px;">
                    <svg style="vertical-align:middle;" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="110" height="66" x="0px" y="0px" viewBox="60 76.2 120.7 53" enable-background="new 60 76.2 120.7 53" xml:space="preserve">
                    <g>
                        <g>
                            <g>
                                <path fill="#78787A" d="M118.5,93.4c-0.9,0-1.7-0.1-2.4-0.5s-1.3-0.8-1.9-1.3c-0.5-0.6-0.9-1.2-1.2-2c-0.3-0.7-0.4-1.5-0.4-2.3
                                    c0-0.8,0.1-1.5,0.4-2.3c0.3-0.7,0.6-1.3,1.1-1.9c0.5-0.6,1.1-1,1.9-1.3s1.6-0.5,2.5-0.5c1.1,0,1.9,0.2,2.8,0.6
                                    c0.8,0.4,1.4,1.1,1.9,1.9l-2.2,1.7c0-0.1-0.1-0.3-0.3-0.5c-0.1-0.2-0.3-0.3-0.5-0.6c-0.2-0.2-0.5-0.3-0.8-0.5s-0.6-0.2-1-0.2
                                    c-0.5,0-0.9,0.1-1.3,0.3c-0.3,0.2-0.6,0.5-0.9,0.9c-0.2,0.3-0.4,0.7-0.6,1.1s-0.2,0.9-0.2,1.3s0,0.9,0.1,1.3
                                    c0.1,0.4,0.3,0.9,0.5,1.1c0.2,0.3,0.6,0.6,0.9,0.9c0.3,0.2,0.8,0.3,1.3,0.3c0.3,0,0.6,0,0.9-0.1c0.3,0,0.6-0.2,0.8-0.3
                                    c0.2-0.1,0.4-0.3,0.6-0.6c0.1-0.2,0.3-0.4,0.3-0.6h-2.3v-2.5h5.4c0,0.3,0,0.5,0,0.6s0,0.3,0,0.6c0,0.7-0.1,1.4-0.4,2.1
                                    c-0.3,0.6-0.6,1.2-1.1,1.7c-0.5,0.5-1.1,0.9-1.7,1.1C120,93.2,119.3,93.4,118.5,93.4z"></path>
                                <path fill="#78787A" d="M128.5,93.5c-0.6,0-1.2-0.1-1.7-0.3c-0.5-0.2-1-0.6-1.3-1c-0.3-0.4-0.6-0.9-0.9-1.5
                                    c-0.2-0.6-0.3-1.1-0.3-1.9c0-0.6,0-1.3,0.3-1.9c0.2-0.6,0.5-1.1,0.9-1.5c0.4-0.4,0.8-0.7,1.3-1c0.5-0.3,1-0.3,1.6-0.3
                                    c0.6,0,1.2,0.1,1.7,0.4c0.5,0.3,0.9,0.7,1.1,1.2v-1.4h2.7v8.8h-2.7v-1.3c-0.3,0.5-0.6,0.9-1.1,1.1
                                    C129.6,93.4,129.1,93.5,128.5,93.5z M131.1,88.1c-0.2-0.5-0.4-0.9-0.9-1.1c-0.4-0.3-0.8-0.4-1.2-0.4c-0.3,0-0.6,0-0.9,0.2
                                    c-0.2,0.1-0.5,0.3-0.6,0.5c-0.2,0.2-0.3,0.5-0.4,0.8c-0.1,0.3-0.1,0.6-0.1,0.9c0,0.3,0,0.6,0.1,0.9c0,0.3,0.3,0.5,0.4,0.7
                                    c0.2,0.2,0.4,0.3,0.6,0.5c0.2,0.1,0.6,0.2,0.9,0.2c0.4,0,0.8-0.1,1.1-0.3c0.3-0.2,0.6-0.5,0.8-0.9L131.1,88.1L131.1,88.1z"></path>
                                <path fill="#78787A" d="M135.2,83.3v-2.5h2.7v2.5H135.2z M135.2,93.3v-9h2.7v9H135.2z"></path>
                                <path fill="#78787A" d="M139.2,80.8h2.7v12.5h-2.7V80.8z"></path>
                                <path fill="#78787A" d="M143.3,80.8h2.7v12.5h-2.7V80.8z"></path>
                                <path fill="#78787A" d="M147.4,83.3v-2.5h2.7v2.5H147.4z M147.4,93.3v-9h2.7v9H147.4z"></path>
                                <path fill="#78787A" d="M165.6,93.3h-2.7v-5c0-0.6-0.1-1-0.3-1.3c-0.2-0.3-0.5-0.4-0.9-0.4s-0.7,0.1-1.1,0.4s-0.6,0.6-0.8,1.1
                                    v5.1h-2.7v-5c0-0.6-0.1-1-0.3-1.3c-0.2-0.3-0.5-0.4-0.9-0.4c-0.3,0-0.7,0.1-1.1,0.4s-0.6,0.6-0.8,1.1v5.1h-2.7v-8.9h2.5v1.5
                                    c0.3-0.6,0.8-1,1.3-1.3c0.6-0.3,1.2-0.4,2-0.4c0.3,0,0.7,0,1,0.1c0.3,0,0.5,0.2,0.7,0.3c0.2,0.1,0.3,0.3,0.5,0.6
                                    c0.1,0.2,0.2,0.4,0.3,0.6c0.3-0.6,0.8-1,1.3-1.3s1.2-0.4,1.9-0.4c0.6,0,1,0.1,1.3,0.3c0.3,0.2,0.6,0.5,0.9,0.9
                                    c0.2,0.4,0.3,0.7,0.3,1.1s0,0.8,0,1.1L165.6,93.3L165.6,93.3L165.6,93.3z"></path>
                                <path fill="#78787A" d="M175.5,93.3h-2.7v-5c0-0.6-0.1-1-0.3-1.3s-0.6-0.4-0.9-0.4c-0.1,0-0.3,0-0.5,0.1c-0.2,0-0.3,0.2-0.6,0.3
                                    c-0.1,0.1-0.3,0.3-0.5,0.5c-0.1,0.2-0.3,0.4-0.3,0.6v5.1H167V80.8h2.7v5c0.3-0.6,0.8-0.9,1.3-1.2c0.6-0.3,1.1-0.4,1.8-0.4
                                    c0.6,0,1.1,0.1,1.4,0.3c0.3,0.1,0.6,0.5,0.9,0.8c0.2,0.3,0.3,0.7,0.4,1.1c0,0.3,0.1,0.8,0.1,1.2L175.5,93.3L175.5,93.3z"></path>
                            </g>
                            <g>
                                <path fill="#78787A" d="M118.5,122.2c-0.9,0-1.7-0.1-2.4-0.5c-0.7-0.3-1.3-0.8-1.9-1.3c-0.5-0.6-0.9-1.2-1.2-2
                                    c-0.3-0.7-0.4-1.5-0.4-2.3c0-0.8,0.1-1.5,0.4-2.3c0.3-0.7,0.6-1.3,1.1-1.9c0.5-0.6,1.1-1,1.9-1.3s1.6-0.5,2.5-0.5
                                    c1.1,0,1.9,0.2,2.8,0.6c0.8,0.4,1.4,1.1,1.9,1.9l-2.2,1.7c0-0.1-0.1-0.3-0.3-0.5s-0.3-0.3-0.5-0.6c-0.2-0.2-0.5-0.3-0.8-0.5
                                    c-0.3-0.1-0.6-0.2-1-0.2c-0.5,0-0.9,0.1-1.3,0.3c-0.3,0.1-0.6,0.5-0.9,0.9c-0.2,0.3-0.4,0.7-0.6,1.1s-0.2,0.9-0.2,1.3
                                    s0,0.9,0.1,1.3c0.1,0.4,0.3,0.9,0.5,1.1c0.2,0.3,0.6,0.6,0.9,0.9c0.3,0.2,0.8,0.3,1.3,0.3c0.3,0,0.6,0,0.9-0.1
                                    c0.3,0,0.6-0.2,0.8-0.3c0.2-0.1,0.4-0.3,0.6-0.6c0.1-0.2,0.3-0.4,0.3-0.6h-2.3v-2.6h5.4c0,0.3,0,0.5,0,0.6s0,0.3,0,0.6
                                    c0,0.7-0.1,1.4-0.4,2.1s-0.6,1.2-1.1,1.7c-0.5,0.5-1.1,0.9-1.7,1.1C120,122.1,119.3,122.2,118.5,122.2z"></path>
                                <path fill="#78787A" d="M128.5,122.3c-0.6,0-1.2-0.1-1.7-0.3c-0.5-0.2-1-0.6-1.3-1c-0.3-0.4-0.6-0.9-0.9-1.5
                                    c-0.2-0.6-0.3-1.1-0.3-1.9c0-0.6,0-1.3,0.3-1.9c0.2-0.6,0.5-1.1,0.9-1.5c0.3-0.4,0.8-0.7,1.3-1c0.5-0.3,1-0.3,1.6-0.3
                                    c0.6,0,1.2,0.1,1.7,0.4c0.5,0.3,0.9,0.7,1.1,1.2v-1.3h2.7v8.8h-2.7v-1.3c-0.3,0.5-0.6,0.9-1.1,1.1
                                    C129.6,122.2,129.1,122.3,128.5,122.3z M131.1,116.9c-0.2-0.5-0.4-0.9-0.9-1.1c-0.4-0.3-0.8-0.4-1.2-0.4c-0.3,0-0.6,0-0.9,0.2
                                    c-0.2,0.1-0.5,0.3-0.6,0.5c-0.2,0.2-0.3,0.5-0.4,0.8c-0.1,0.3-0.1,0.6-0.1,0.9s0,0.6,0.1,0.9c0,0.3,0.3,0.5,0.4,0.7
                                    c0.1,0.2,0.4,0.3,0.6,0.5c0.2,0.1,0.6,0.2,0.9,0.2c0.4,0,0.8-0.1,1.1-0.3c0.3-0.2,0.6-0.5,0.8-0.9L131.1,116.9L131.1,116.9z"></path>
                                <path fill="#78787A" d="M135.2,109.7h2.7v12.5h-2.7V109.7z"></path>
                                <path fill="#78787A" d="M150.4,113.2h2.6l-3.7,9h-2.2l-1.3-3.7l-1.3,3.7h-2.2l-3.7-9h2.6l2.4,6.4l0.9-2.4l-1.6-4h2.2l0.9,2.7
                                    l0.9-2.7h2.2l-1.6,4l0.9,2.4L150.4,113.2z"></path>
                                <path fill="#78787A" d="M156.7,122.3c-0.6,0-1.2-0.1-1.7-0.3c-0.5-0.2-1-0.6-1.3-1s-0.6-0.9-0.9-1.5c-0.2-0.6-0.3-1.1-0.3-1.9
                                    c0-0.6,0-1.3,0.3-1.9c0.2-0.6,0.5-1.1,0.9-1.5c0.3-0.4,0.8-0.7,1.3-1c0.5-0.2,1-0.3,1.6-0.3c0.6,0,1.2,0.1,1.7,0.4
                                    c0.5,0.3,0.9,0.7,1.1,1.2v-1.3h2.7v8.8h-2.7v-1.3c-0.3,0.5-0.6,0.9-1.1,1.1C157.8,122.2,157.3,122.3,156.7,122.3z M159.3,116.9
                                    c-0.2-0.5-0.4-0.9-0.9-1.1s-0.8-0.4-1.2-0.4c-0.3,0-0.6,0-0.9,0.2c-0.2,0.1-0.5,0.3-0.6,0.5c-0.2,0.2-0.3,0.5-0.4,0.8
                                    s-0.1,0.6-0.1,0.9s0,0.6,0.1,0.9c0.1,0.3,0.3,0.5,0.4,0.7c0.1,0.2,0.4,0.3,0.6,0.5c0.3,0.1,0.6,0.2,0.9,0.2
                                    c0.4,0,0.8-0.1,1.1-0.3c0.3-0.2,0.6-0.5,0.8-0.9L159.3,116.9L159.3,116.9z"></path>
                                <path fill="#78787A" d="M171.7,113.2l-4.1,12.6h-3.2c0.1-0.3,0.2-0.6,0.3-0.9c0-0.2,0.2-0.5,0.3-0.8c0-0.3,0.2-0.6,0.3-0.9
                                    c0-0.3,0.2-0.5,0.3-0.7c0-0.2,0.1-0.3,0.2-0.5l-3.3-8.9h2.9l2.1,6.6l1.8-6.6L171.7,113.2L171.7,113.2z"></path>
                            </g>
                            <path fill="#78787A" d="M88.3,104.5c0,0,0-3.7,0-7.6c0-5.1,1.2-7.6,4.2-7.6c4.1,0,5.8,6.8,5.8,15.4c0,3.2,0,9.8,0,9.8l7.6,7.6
                                v-15.8c0-17.3-5.9-24.5-13.7-24.5c-7.3,0-10.9,4.5-10.9,14.4v1.3L88.3,104.5z"></path>
                        </g>
                        <g>
                            <g>
                                <g>
                                    <path fill="#A6A8AB" d="M127.6,107.9c-0.9,0-1.7-0.2-2.3-0.6c-0.6-0.3-1.2-0.9-1.7-1.5c-0.4-0.6-0.8-1.3-1-2
                                        c-0.2-0.7-0.3-1.4-0.3-2.2s0.1-1.4,0.3-2.2c0.2-0.7,0.6-1.4,1.1-2c0.5-0.6,1-1.1,1.7-1.4c0.7-0.3,1.4-0.6,2.3-0.6
                                        c0.9,0,1.6,0.2,2.3,0.6c0.6,0.3,1.2,0.9,1.7,1.5c0.4,0.6,0.8,1.3,1,2c0.2,0.7,0.3,1.4,0.3,2.1c0,0.7-0.1,1.5-0.3,2.2
                                        s-0.6,1.4-1.1,2c-0.4,0.6-1,1.1-1.7,1.4C129.2,107.7,128.5,107.9,127.6,107.9z M125.1,101.8c0,0.4,0,0.9,0.1,1.3
                                        c0.1,0.4,0.3,0.9,0.5,1.1s0.5,0.6,0.8,0.9c0.3,0.2,0.7,0.3,1.1,0.3c0.4,0,0.9-0.1,1.1-0.3c0.3-0.2,0.6-0.6,0.8-0.9
                                        c0.2-0.3,0.3-0.7,0.4-1.1c0-0.4,0.1-0.9,0.1-1.2c0-0.4,0-0.9-0.1-1.3c-0.1-0.4-0.3-0.9-0.5-1.1c-0.2-0.3-0.5-0.6-0.8-0.9
                                        c-0.3-0.2-0.7-0.3-1.1-0.3c-0.4,0-0.9,0.1-1.1,0.3c-0.3,0.2-0.6,0.6-0.8,0.9c-0.2,0.3-0.3,0.7-0.4,1.1
                                        C125.1,100.9,125.1,101.3,125.1,101.8z"></path>
                                </g>
                            </g>
                            <g>
                                <g>
                                    <path fill="#A6A8AB" d="M148.4,107.9c-0.9,0-1.7-0.2-2.3-0.6c-0.6-0.3-1.2-0.9-1.7-1.5c-0.4-0.6-0.8-1.3-1-2
                                        c-0.2-0.7-0.3-1.4-0.3-2.2s0.1-1.4,0.3-2.2c0.2-0.7,0.6-1.4,1.1-2c0.5-0.6,1-1.1,1.7-1.4c0.7-0.3,1.4-0.6,2.3-0.6
                                        c0.9,0,1.6,0.2,2.3,0.6c0.7,0.3,1.2,0.9,1.7,1.5c0.4,0.6,0.8,1.3,1,2s0.3,1.4,0.3,2.1c0,0.7-0.1,1.5-0.3,2.2s-0.6,1.4-1.1,2
                                        c-0.4,0.6-1,1.1-1.7,1.4C149.9,107.7,149.2,107.9,148.4,107.9z M145.8,101.8c0,0.4,0,0.9,0.1,1.3c0.1,0.4,0.3,0.9,0.5,1.1
                                        s0.5,0.6,0.8,0.9c0.3,0.2,0.7,0.3,1.1,0.3c0.4,0,0.9-0.1,1.1-0.3c0.3-0.2,0.6-0.6,0.8-0.9c0.2-0.3,0.3-0.7,0.4-1.1
                                        c0-0.4,0.1-0.9,0.1-1.2c0-0.4,0-0.9-0.1-1.3c-0.1-0.4-0.3-0.9-0.5-1.1c-0.2-0.3-0.5-0.6-0.8-0.9c-0.3-0.2-0.7-0.3-1.1-0.3
                                        s-0.9,0.1-1.1,0.3c-0.3,0.2-0.6,0.6-0.8,0.9c-0.2,0.3-0.3,0.7-0.4,1.1C145.9,100.9,145.8,101.3,145.8,101.8z"></path>
                                </g>
                            </g>
                            <path fill="#A6A8AB" d="M120.5,101.8c0,0,0.2-0.2,0.3-0.3c0.2-0.3,0.4-0.6,0.6-1c0.1-0.3,0.2-0.8,0.2-1.3c0-1.1-0.3-2.2-1.1-2.8
                                c-0.7-0.6-1.8-1-3.2-1c-0.6,0-1.1,0-1.6,0.2c-0.5,0.1-0.9,0.3-1.3,0.5c-0.4,0.2-0.8,0.4-1.1,0.6c-0.3,0.2-0.6,0.4-0.7,0.6l1.7,1.8
                                c0.1-0.1,0.3-0.3,0.5-0.5c0.2-0.1,0.4-0.3,0.6-0.4c0.2-0.1,0.5-0.2,0.7-0.3c0.3,0,0.5-0.1,0.8-0.1c0.4,0,0.8,0.1,1.1,0.3
                                c0.3,0.2,0.4,0.6,0.4,1.1c0,0.3,0,0.6-0.2,0.9c-0.1,0.3-0.3,0.5-0.6,0.7l-4.8,4.7v2.2h8.8v-2.5h-4.8L120.5,101.8z"></path>
                            <path fill="#A6A8AB" d="M141.2,101.8c0,0,0.2-0.2,0.3-0.3c0.2-0.3,0.4-0.6,0.6-1c0.1-0.3,0.2-0.8,0.2-1.3c0-1.1-0.3-2.2-1.1-2.8
                                c-0.7-0.6-1.8-1-3.2-1c-0.6,0-1.1,0-1.6,0.2c-0.5,0.1-0.9,0.3-1.3,0.5c-0.4,0.2-0.8,0.4-1.1,0.6c-0.3,0.2-0.6,0.4-0.7,0.6l1.7,1.8
                                c0.1-0.1,0.3-0.3,0.5-0.5c0.2-0.1,0.4-0.3,0.6-0.4c0.2-0.1,0.5-0.2,0.7-0.3c0.3,0,0.5-0.1,0.8-0.1c0.4,0,0.8,0.1,1.1,0.3
                                c0.3,0.2,0.4,0.6,0.4,1.1c0,0.3,0,0.6-0.2,0.9s-0.4,0.5-0.6,0.7l-4.8,4.7v2.2h8.8v-2.5h-4.8L141.2,101.8z"></path>
                            <path fill="#A6A8AB" d="M81.4,97.6h-1.3c-9.9,0-14.4,3.6-14.4,10.9c0,7.7,7.2,13.7,24.5,13.7H106l-7.7-7.6c0,0-6.6,0-9.8,0
                                c-8.6,0-15.4-1.8-15.4-5.8c0-3,2.5-4.2,7.6-4.2c3.8,0,7.6,0,7.6,0L81.4,97.6z"></path>
                        </g>
                    </g>
                    </svg>
                    Dashboard
                </a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${pageContext.request.contextPath}">Dashboard</a></li>
                    <!--<li><a href="#">Settings</a></li>
                    <li><a href="#">Profile</a></li>-->
                    <li><a href="#">Help</a></li>
                </ul>
                <!--<form class="navbar-form navbar-right">
                    <input type="text" class="form-control" placeholder="Search...">
                </form>-->
            </div><!--/.nav-collapse -->
        </div>
    </nav><!-- navbar -->

    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-2 sidebar">
                <!--<ul class="nav nav-sidebar">
                    <li class="${pageContext.request.requestURI eq '${pageContext.request.contextPath}' ? ' active' : ''}"><a href="${pageContext.request.contextPath}">Overview <span class="sr-only">(current)</span></a></li>
                    <li><a href="#">Reports</a></li>
                    <li><a href="#">Analytics</a></li>
                </ul>-->
                <ul class="nav nav-sidebar">
                    <li><a href="${pageContext.request.contextPath}/followers">Followers vs. Time</a></li>
                    <li><a href="${pageContext.request.contextPath}/mentions">Mentions vs. Time</a></li>
                    <li><a href="${pageContext.request.contextPath}/engagement">Engagement vs. Time</a></li>
                    <li><a href="${pageContext.request.contextPath}/reach">Reach vs. Time</a></li>
                    <li><a href="${pageContext.request.contextPath}/heatMap">Galway2020 Heat Map</a></li>
                    <li><a href="${pageContext.request.contextPath}/hashTags">Hash Tags Graph</a></li>
                </ul>
                <ul class="nav nav-sidebar">
                    <li><a href="${pageContext.request.contextPath}/topEngaged">Top Engaged Users</a></li>
                    <li><a href="${pageContext.request.contextPath}/topInfluential">Top Influential Users</a></li>
                    <li><a href="${pageContext.request.contextPath}/topActivists">Top Active Users</a></li>
                </ul>
            </div><!-- sidebar -->
            
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <c:if test="${page_error != null }">
                    <div class="alert alert-error">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <h4>Error!</h4>
                        ${page_error}
                    </div>
                </c:if>
                
                <decorator:body/>
            </div><!-- main -->
        </div><!-- row -->
    </div><!-- container-fluid -->
    <footer>
    </footer>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/3.3.4/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function() {
            // Get current url
            // Select an a element that has the matching href and apply a class of 'active'. Also prepend a - to the content of the link
            var url = window.location.pathname;
            $('.nav li a[href="'+url+'"]').parent().addClass('active');
        });
    </script>
</body>
</html>
