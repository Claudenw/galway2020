<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Top Active Users</title>
    <style>
        .main { padding-top: 30px; }
    </style>
</head>
<body>
    <table id="activistsTable" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>User Name</th>
                <th>Network</th>
                <th>No. Retweets</th>
            </tr>
        </thead>
        <tfoot>
            <tr>
                <th>User Name</th>
                <th>Network</th>
                <th>No. Retweets</th>
            </tr>
        </tfoot>
    </table>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#activistsTable').dataTable({
                "ajax": "${pageContext.request.contextPath}/data/dataTable_test/activists_data.json",
                "pagingType": "full_numbers",
                "order": [[ 2, "desc" ]]
            });
        });
    </script>
</body>
</html>