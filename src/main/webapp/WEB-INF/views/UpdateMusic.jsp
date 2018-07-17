<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Music</title>
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
</head>
<body>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4>Hello ${login}</h4>
            <ul class="nav nav-pills nav-stacked">
                <li><a href="${pageContext.request.contextPath}/moderator?id=${user.id}">Users List</a></li>
                <li><a href="${pageContext.request.contextPath}/createmusic">Add new Music type</a></li>
                <li><a href="${pageContext.request.contextPath}/editmusictype">Edit music type</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Exit</a></li>

            </ul>
            <br>
        </div>

        <div class="col-sm-9">
            <h2>"${user.login} Music list"</h2>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Music type</th>
                    <th>Description</th>
                </tr>
                <c:forEach items="${user.musicType}" var="music">
                    <tr style="cursor: pointer">
                        <td><c:out value="${music.id}"></c:out>
                        </td>
                        <td><c:out value="${music.type}"></c:out>
                        </td>
                        <td><c:out value="${music.description}"></c:out>
                        </td>
                        <td>
                            <button type="submit" class="btn btn-success"
                                    name="id"
                                    value="">Update
                            </button>
                            <button type="submit" class="btn btn-warning"
                                    name="id"
                                    formaction="${pageContext.request.contextPath}/editmusictype"
                                    formmethod="post"
                                    value="${music.id}">Remove
                            </button>
                        </td>

                    </tr>
                </c:forEach>
            </table>
            <br><br>
        </div>
    </div>
</div>

<footer class="container-fluid">
    <%--<p>Footer Text</p>--%>
</footer>

</body>
</html>
