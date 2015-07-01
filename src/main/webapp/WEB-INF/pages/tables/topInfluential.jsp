<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Top Influential Users</title>
    <style>
        .main { padding-top: 30px; }
    </style>
</head>
<body>
    <table id="influentialTable" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>User Name</th>
                <th>Network</th>
                <th>No. Followers</th>
            </tr>
        </thead>
        <tfoot>
            <tr>
                <th>User Name</th>
                <th>Network</th>
                <th>No. Followers</th>
            </tr>
        </tfoot>
    </table>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#influentialTable').dataTable({
                "ajax": "${pageContext.request.contextPath}/data/dataTable_test/influential_data.json",
                "pagingType": "full_numbers",
                "order": [[ 2, "desc" ]]
            });
        });
    </script>
</body>
</html>