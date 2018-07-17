<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update User</title>
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
            if (document.f_update.name.value == "") {
                valid = false;
            }
            if (document.f_update.login.value == "") {
                valid = false;
            }
            if (document.f_update.email.value == "") {
                valid = false;
            }
            if (document.f_update.password.value == "") {
                valid = false;
            }
            if (document.f_update.country.value == "") {
                valid = false;
            }
            if (document.f_update.sity.value == "") {
                valid = false;
            }
            if (document.f_update.street.value == "") {
                valid = false;
            }

            if (!valid) {
                alert("Please enter correct data")
            }

            return valid;
        }
    </script>
    <script type="text/javascript">
        $(function () {
            $('#country').on('change', function (event) {
                console.log($(this).val());
                $("select[name='sity']").empty();

                $.ajax({
                    url: '/signin/sityjson',
                    type: 'GET',
                    dataType: 'html',
                    data: {country: $(this).val()},
                })
                    .done(function (sities) {
                        console.log("success");
                        sities = JSON.parse(sities);
                        $("select[name='sity']").append($("<option value=\"0\">- select sity -</option>"))
                        for (var id in sities) {
                            $("select[name='sity']").append($("<option value='" + sities[id] + "'>" + sities[id] + "</option>"))
                        }
                    })
                    .fail(function () {
                        console.log("error");
                    });
            });
        });
    </script>
</head>
<body>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4>Hello ${login}</h4>

            <ul class="nav nav-pills nav-stacked">
                <li><a href="${pageContext.request.contextPath}/user?id=${user.id}">My music</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/updateMe?id=${user.id}">Update me</a></li>
                <li><a href="${pageContext.request.contextPath}/addUserMusic?id=${user.id}">Add music</a> </li>
                <li><a href="${pageContext.request.contextPath}/logout">Exit</a></li>
            </ul>
            <br>
        </div>

        <div class="col-sm-9">
            <h2>Update</h2>
            <form action="${pageContext.request.contextPath}/updateMe" method="post"
                  name="f_update" onsubmit="return validate_form()">
                Name : <br>
                <input type="text" name="name" value="${user.name}"><br>
                Login :<br>
                <input type="text" name="login" value="${user.login}"><br>
                E-mail :<br>
                <input type="text" name="email" value="${user.email}"><br>
                Password :<br>
                <input type="text" name="password" value="${user.password}"><br>
                <br>
                Country :
                <select name="country" id="country">
                    <option value="${user.address.country}">${user.address.country}</option>
                    <c:forEach items="${country}" var="country">
                        <option value="${country}">${country}</option>
                    </c:forEach>
                </select>
                <br>
                <br>

                Sity :
                <select name="sity" id="sity">
                    <option value="${user.address.sity}">${user.address.sity}</option>
                </select>
                <br>
                <br>

                Street :
                <input type="text" name="street" value="${user.address.street}"><br>
                <br>
                <br>
                <button type="submit" class="btn btn-success"
                        name="id"
                        value="${user.id}">Update
                </button>
            </form>
        </div>

    </div>
</div>

<footer class="container-fluid">
    <%--<p>Footer Text</p>--%>
</footer>
</body>
</html>
