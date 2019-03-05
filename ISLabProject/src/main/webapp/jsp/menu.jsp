<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="cache-control" content="no-cache, must-revalidate, post-check=0, pre-check=0" />
    <meta http-equiv="cache-control" content="max-age=0" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
    <meta http-equiv="pragma" content="no-cache" />

    <title>Title</title>

    <style><%@include file="../css/menu.css"%></style>
    <link href="https://fonts.googleapis.com/css?family=Oswald" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.5/handlebars.min.js"></script>
</head>
<body>
    <div class="wrap">
        <header>
            <div class="main_title">
                <h1 class="title">Welcome to The System!)</h1>
            </div>
            <div id="username_field" class="logout_corner"></div>
        </header>
        <div class="content">

            <div class="menu">
                <nav class="dws-menu">
                    <ul>
                        <li><a href="#">Start Page</a></li>
                        <li><a href="#provider">Provider</a>
                            <ul>
                                <li><a href="#provider_list">List of Providers (10 by step)</a></li>
                                <li><a href="#provider_find">Find Provider(s) (Filter/ID)</a></li>
                                <li><a href="#provider_add">Add new Provider</a></li>
                            </ul>
                        </li>
                        <li><a href="#customer">Customer</a>
                            <ul>
                                <li><a href="#customer_list">List of Customers (10 by step)</a></li>
                                <li><a href="#customer_find">Find Customer(s) (Filter/ID)</a></li>
                                <li><a href="#customer_add">Add new Customer</a></li>
                            </ul>
                        </li>
                        <li><a href="#goods">Goods</a>
                            <ul>
                                <li><a href="#goods_list">List of Goods (10 by step)</a></li>
                                <li><a href="#goods_find">Find Goods (Filter/ID)</a></li>
                                <li><a href="#goods_add">Add new Goods</a></li>
                            </ul>
                        </li>
                        <li><a href="#storage">Storage</a>
                            <ul>
                                <li><a href="#storage_available">Show available goods</a></li>
                                <li><a href="#storage_info">Show storage info</a></li>
                            </ul>
                        </li>
                        <li><a href="#imports">Imports</a>
                            <ul>
                                <li><a href="#import_action">Import goods</a></li>
                                <li><a href="#import_find">Find Import Document (Filter/ID)</a></li>
                            </ul>
                        </li>
                        <li><a href="#exports">Exports</a>
                            <ul>
                                <li><a href="#export_action">Export goods</a></li>
                                <li><a href="#export_find">Find Exports Document (Filter/ID)</a></li>
                            </ul>
                        </li>
                        <li><a href="#reports">Reports</a>
                            <ul>
                                <li><a href="#report_last">Show last report</a></li>
                                <li><a href="#report_list">Show reports list (10 by step)</a></li>
                                <li><a href="#report_make">Make report</a></li>
                            </ul>
                        </li>
                        <li><a href="#system">System</a></li>
                    </ul>
                </nav>
            </div>

            <div class="dynamic_panel"></div>

        </div>
        <footer></footer>
    </div>

    <!-- There are scripts -->
    <script id="username_template" type="text/template" >
        <p class="username_corner"><b>User:</b> <u>{{ username }}</u></p>
        <button class="logout" onclick="Logout()">Logout</button>
    </script>

    <script><%@include file="../js/menu.js"%></script>
    <!--  -->

</body>
</html>