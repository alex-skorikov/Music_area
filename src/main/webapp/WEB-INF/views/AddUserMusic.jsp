<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add user musictype</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
        .row.content {
            height: 1500px
        }

        /* Set gray background color and 100% height */
        .sidenav {
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }

            .row.content {
                height: auto;
            }
        }

        table {
            font-family: Arial, sans-serif;
            border-collapse: collapse;
            width: 100%;

        }

        td, th, table, form, h2 {
            border: 1px solid #dddddd;
            text-align: center;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>

    <script type="text/javascript">
        function validate_form() {
            valid = true;
            if (document.f_update.music.value == "") {
                valid = false;
            }

            if (!valid) {
                alert("Please enter correct data")
            }

            return valid;
        }
    </script>
</head>
<body>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4>Hello ${login}</h4>
            <ul class="nav nav-pills nav-stacked">
                <li><a href="${pageContext.request.contextPath}/user?id=${user.id}">My music</a></li>
                <li><a href="${pageContext.request.contextPath}/updateMe?id=${user.id}">Update me</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/addUserMusic?id=${user.id}">Add music</a> </li>
                <li><a href="${pageContext.request.contextPath}/logout">Exit</a></li>
            </ul>
            <br>
        </div>

        <div class="col-sm-9">
            <h2>Add me new musictype</h2>

            <form action="${pageContext.request.contextPath}/addUserMusic" method="post"
                  name="f_update" onsubmit="return validate_form();">

                Music type :
                <select name="music">
                    <option value="0">- select music type -</option>
                    <c:forEach items="${music}" var="music">
                        <option value="${music.type}">${music.type}</option>
                    </c:forEach>
                </select>
                <br>
                <br>
                <button type="submit" class="btn btn-success"
                        name="id"
                        value="${user.id}">Add musictype
                </button>
            </form>
        </div>

    </div>
</div>

</body>
</html>
