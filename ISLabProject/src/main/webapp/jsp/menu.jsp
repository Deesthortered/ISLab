<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="cache-control" content="no-cache, must-revalidate, post-check=0, pre-check=0" />
    <meta http-equiv="cache-control" content="max-age=0" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
    <meta http-equiv="pragma" content="no-cache" />

    <title>Title</title>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/mdb.min.css" rel="stylesheet">
    <link href="css/addons/datatables.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Oswald" rel="stylesheet">
    <link href="css/menu.css" rel="stylesheet">
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
                    <ul id="menu_ul" ></ul>
                </nav>
            </div>

            <div id="dynamic_panel" class="dynamic_panel"></div>

        </div>
        <footer></footer>
    </div>

    <!-- There are scripts -->
    <script id="username_template" type="text/template" >
        <p class="username_corner"><b>User:</b> <u>{{ username }}</u></p>
        <button class="logout" onclick="InterfaceHashHandler.Logout()">Logout</button>
    </script>

    <!-- Menu for each role -->
    <script id="admin_menu_template" type="text/template">
        <li><a href="#">Start Page</a></li>
        <li><a href="#provider">Provider</a>
            <ul>
                <li><a href="#provider_list">List of Providers (10 by step)</a></li>
                <li><a href="#provider_add">Add new Provider</a></li>
            </ul>
        </li>
        <li><a href="#customer">Customer</a>
            <ul>
                <li><a href="#customer_list">List of Customers (10 by step)</a></li>
                <li><a href="#customer_add">Add new Customer</a></li>
            </ul>
        </li>
        <li><a href="#goods">Goods</a>
            <ul>
                <li><a href="#goods_list">List of Goods (10 by step)</a></li>
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
                <li><a href="#import_list">Import Documents list (Filter/ID)</a></li>
            </ul>
        </li>
        <li><a href="#exports">Exports</a>
            <ul>
                <li><a href="#export_action">Export goods</a></li>
                <li><a href="#export_list">Exports Document list (Filter/ID)</a></li>
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

    </script>
    <script id="view_menu_template" type="text/template">
        <li><a href="#">Start Page</a></li>
        <li><a href="#provider">Provider</a>
            <ul>
                <li><a href="#provider_list">List of Providers (10 by step)</a></li>
            </ul>
        </li>
        <li><a href="#customer">Customer</a>
            <ul>
                <li><a href="#customer_list">List of Customers (10 by step)</a></li>
            </ul>
        </li>
        <li><a href="#goods">Goods</a>
            <ul>
                <li><a href="#goods_list">List of Goods (10 by step)</a></li>
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
                <li><a href="#import_list">Import Documents list (Filter/ID)</a></li>
            </ul>
        </li>
        <li><a href="#exports">Exports</a>
            <ul>
                <li><a href="#export_list">Exports Document list (Filter/ID)</a></li>
            </ul>
        </li>
        <li><a href="#reports">Reports</a>
            <ul>
                <li><a href="#report_last">Show last report</a></li>
                <li><a href="#report_list">Show reports list (10 by step)</a></li>
                <li><a href="#report_make">Make report</a></li>
            </ul>
        </li>
    </script>
    <script id="import_menu_template" type="text/template">
        <li><a href="#">Start Page</a></li>
        <li><a href="#provider">Provider</a>
            <ul>
                <li><a href="#provider_list">List of Providers (10 by step)</a></li>
                <li><a href="#provider_add">Add new Provider</a></li>
            </ul>
        </li>
        <li><a href="#goods">Goods</a>
            <ul>
                <li><a href="#goods_list">List of Goods (10 by step)</a></li>
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
                <li><a href="#import_list">Import Document list (Filter/ID)</a></li>
            </ul>
        </li>
        <li><a href="#reports">Reports</a>
            <ul>
                <li><a href="#report_last">Show last report</a></li>
                <li><a href="#report_list">Show reports list (10 by step)</a></li>
                <li><a href="#report_make">Make report</a></li>
            </ul>
        </li>
    </script>
    <script id="export_menu_template" type="text/template">
        <li><a href="#">Start Page</a></li>
        <li><a href="#customer">Customer</a>
            <ul>
                <li><a href="#customer_list">List of Customers (10 by step)</a></li>
                <li><a href="#customer_add">Add new Customer</a></li>
            </ul>
        </li>
        <li><a href="#goods">Goods</a>
            <ul>
                <li><a href="#goods_list">List of Goods (10 by step)</a></li>
            </ul>
        </li>
        <li><a href="#storage">Storage</a>
            <ul>
                <li><a href="#storage_available">Show available goods</a></li>
                <li><a href="#storage_info">Show storage info</a></li>
            </ul>
        </li>
        <li><a href="#exports">Exports</a>
            <ul>
                <li><a href="#export_action">Export goods</a></li>
                <li><a href="#export_list">Exports Document list (Filter/ID)</a></li>
            </ul>
        </li>
        <li><a href="#reports">Reports</a>
            <ul>
                <li><a href="#report_last">Show last report</a></li>
                <li><a href="#report_list">Show reports list (10 by step)</a></li>
                <li><a href="#report_make">Make report</a></li>
            </ul>
        </li>
    </script>

    <script id="startpage_template" type="text/template">
        <h1> Hello, {{ user_name }}, Welcome to the ISLabStorage!</h1>
        <p> Yours role is {{ user_role }} and you have relevant permissions: {{ user_permissions }}. </p>
        <br>
        <h3> Short list of latest info: </h3>
        <ul class="content_ul">
            <li>Last visit: {{ last_visit }}.</li>
        </ul>
    </script>

    <!-- Provider section scripts -->
    <script id="provider_template" type="text/template">
        <h1>Provider menu</h1>
        <p> In the menu you can review provider's info and control data.</p>
        <br>
        <h3><i>General info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
            <li>Something2</li>
            <li>Something3</li>
        </ul>
        <br>
        <h3><i>Available actions:</i></h3>
        <ul class="content_ul">
            <li><a href="#provider_list"><u>List of Providers</u></a> - show first 10 providers, you can expand list by each 10 next providers.</li>
            <li><a href="#provider_add"><u>Add new Provider</u></a> - you can add new provider if you need to import some goods, but provider is not exist in the database.</li>
        </ul>
    </script>

    <script id="provider_list_template" type="text/template">
        <h1>Provider list</h1>
        <p>There you can review list of providers and edit it.</p>
        <div id="provider_filter">
            <h3>Filter.</h3>
            <ul>
                <li>ID: <input type="text" name="filter_id"></li>
                <li>Name: <input type="text" name="filter_name"></li>
                <li>Country: <input type="text" name="filter_country"></li>
                <li>Description: <input type="text" name="filter_description"></li>
            </ul>
            <button onclick="ListPage.TableSetFilter()">Send request</button>
        </div>
        <br>
        <div id="provider_table_place"></div>
        <br>
    </script>
    <script id="provider_list_table" type="text/template">
        <table id="dt_providerTable" class="table table-striped table-bordered table-sm" cellspacing="0" width="1200px">
            <thead>
            <tr>
                <th class="th-sm">ID </th>
                <th class="th-sm">Name </th>
                <th class="th-sm">Country </th>
                <th class="th-sm">Description </th>
                <th class="th-sm">Actions </th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr>
                <th>ID </th>
                <th>Name </th>
                <th>Country </th>
                <th>Description </th>
                <th>Actions </th>
            </tr>
            </tfoot>
        </table>
        <button onclick="ListPage.TableExtendList(true)">Load more (+5)</button>
        <button onclick="ListPage.TableExtendList(false)">Load all</button>
        <button onclick="ListPage.TableRefresh()">Refresh</button>
    </script>
    <script id="provider_datatable_row" type="text/template">
        <tr>
            <td>{{ id }}</td>
            <td>{{ name }}</td>
            <td>{{ country }}</td>
            <td>{{ description }}</td>
            <td>
                <button class="table_action" onclick="ListPage.TableEditRowMenu( {{ id }} );">Edit</button>
                <button class="table_action" onclick="ListPage.TableDeleteRow( {{ id }} );">Delete</button>
            </td>
        </tr>
    </script>

    <script id="provider_add_template" type="text/template">
        <h1>Add provider</h1>
        <p> There you can add new provider to base.</p>
        <div class = "add_provider_panel">
            <p><b>*</b> Name:</p>
                <input type="text" name="input_name">
            <p><b>*</b> Country:</p>
                <input type="text" name="input_country">
            <p>Description:</p>
                <input type="text" name="input_description">
            <p><button onclick="ListPage.TableAddSend()">Send data</button></p>
        </div>
    </script>
    <script id="provider_edit_template" type="text/template">
        <h1>Edit provider</h1>
        <p> There you can edit provider in the base.</p>
        <p> Edit field which you want to change.</p>
        <div class = "edit_provider_panel">
            <input type="hidden" name="input_id" value="{{ id }}" />
            <p> Name:</p>
            <input type="text" name="input_name" value="{{ name }}">
            <p> Country:</p>
            <input type="text" name="input_country" value="{{ country }}">
            <p> Description:</p>
            <input type="text" name="input_description"  value="{{ description }}">
            <p><button onclick="ListPage.TableEditSend()">Send data</button></p>
        </div>
    </script>
    <!--  -->

    <!-- Customer section scripts -->
    <script id="customer_template" type="text/template">
        <h1>Customer menu</h1>
        <p> In the menu you can review customer's info and control data.</p>
        <br>
        <h3><i>General info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
            <li>Something2</li>
            <li>Something3</li>
        </ul>
        <br>
        <h3><i>Available actions:</i></h3>
        <ul class="content_ul">
            <li><a href="#customer_list"><u>List of Customers</u></a> - show first 10 customers, you can expand list by each 10 next customers.</li>
            <li><a href="#customer_add"><u>Add new Customer</u></a> - you can add new customer if you need to export some goods, but customer is not exist in the database.</li>
        </ul>
    </script>

    <script id="customer_list_template" type="text/template">
        <h1>Customer list</h1>
        <p>There you can review list of providers and edit it.</p>
        <div id="customer_filter">
            <h3>Filter.</h3>
            <ul>
                <li>ID: <input type="text" name="filter_id"></li>
                <li>Name: <input type="text" name="filter_name"></li>
                <li>Country: <input type="text" name="filter_country"></li>
                <li>Description: <input type="text" name="filter_description"></li>
            </ul>
            <button onclick="ListPage.TableSetFilter()">Send request</button>
        </div>
        <br>
        <div id="customer_table_place"></div>
        <br>
    </script>
    <script id="customer_list_table" type="text/template">
        <table id="dt_customerTable" class="table table-striped table-bordered table-sm" cellspacing="0" width="1200px">
            <thead>
            <tr>
                <th class="th-sm">ID </th>
                <th class="th-sm">Name </th>
                <th class="th-sm">Country </th>
                <th class="th-sm">Description </th>
                <th class="th-sm">Actions </th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr>
                <th>ID </th>
                <th>Name </th>
                <th>Country </th>
                <th>Description </th>
                <th>Actions </th>
            </tr>
            </tfoot>
        </table>
        <button onclick="ListPage.TableExtendList(true)">Load more (+5)</button>
        <button onclick="ListPage.TableExtendList(false)">Load all</button>
        <button onclick="ListPage.TableRefresh()">Refresh</button>
    </script>
    <script id="customer_datatable_row" type="text/template">
        <tr>
            <td>{{ id }}</td>
            <td>{{ name }}</td>
            <td>{{ country }}</td>
            <td>{{ description }}</td>
            <td>
                <button class="table_action" onclick="ListPage.TableEditRowMenu( {{ id }} );">Edit</button>
                <button class="table_action" onclick="ListPage.TableDeleteRow( {{ id }} );">Delete</button>
            </td>
        </tr>
    </script>

    <script id="customer_add_template" type="text/template">
        <h1>Add customer</h1>
        <p> There you can add new customer to base.</p>
        <div class = "add_customer_panel">
            <p><b>*</b> Name:</p>
            <input type="text" name="input_name">
            <p><b>*</b> Country:</p>
            <input type="text" name="input_country">
            <p>Description:</p>
            <input type="text" name="input_description">
            <p><button onclick="ListPage.TableAddSend()">Send data</button></p>
        </div>
    </script>
    <script id="customer_edit_template" type="text/template">
        <h1>Edit customer</h1>
        <p> There you can edit customer in the base.</p>
        <p> Edit field which you want to change.</p>
        <div class = "edit_customer_panel">
            <input type="hidden" name="input_id" value="{{ id }}" />
            <p> Name:</p>
            <input type="text" name="input_name" value="{{ name }}">
            <p> Country:</p>
            <input type="text" name="input_country" value="{{ country }}">
            <p> Description:</p>
            <input type="text" name="input_description"  value="{{ description }}">
            <p><button onclick="ListPage.TableEditSend()">Send data</button></p>
        </div>
    </script>
    <!--  -->

    <!-- Goods section scripts -->
    <script id="goods_template" type="text/template">
        <h1>Goods menu</h1>
        <p> In the menu you can review goods info and control data.</p>
        <br>
        <h3><i>General info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
            <li>Something2</li>
            <li>Something3</li>
        </ul>
        <br>
        <h3><i>Available actions:</i></h3>
        <ul class="content_ul">
            <li><a href="#goods_list"><u>List of Goods</u></a> - show first 10 goods, you can expand list by each 10 next goods.</li>
            <li><a href="#goods_add"><u>Add new Goods</u></a> - you can add new one if you need to export some goods, but it is not exist in the database.</li>
        </ul>
    </script>

    <script id="goods_list_template" type="text/template">
        <h1>Goods list</h1>
        <p>There you can review list of goods and edit it.</p>
        <div id="goods_filter">
            <h3>Filter.</h3>
            <ul>
                <li>ID: <input type="text" name="filter_id"></li>
                <li>Name: <input type="text" name="filter_name"></li>
                <li>Average Price: <input type="text" name="filter_average_price"></li>
                <li>Description: <input type="text" name="filter_description"></li>
            </ul>
            <button onclick="ListPage.TableSetFilter()">Send request</button>
        </div>
        <br>
        <div id="goods_table_place"></div>
        <br>
    </script>
    <script id="goods_list_table" type="text/template">
        <table id="dt_goodsTable" class="table table-striped table-bordered table-sm" cellspacing="0" width="1200px">
            <thead>
            <tr>
                <th class="th-sm">ID </th>
                <th class="th-sm">Name </th>
                <th class="th-sm">Average Price </th>
                <th class="th-sm">Description </th>
                <th class="th-sm">Actions </th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr>
                <th>ID </th>
                <th>Name </th>
                <th>Average Price </th>
                <th>Description </th>
                <th>Actions </th>
            </tr>
            </tfoot>
        </table>
        <button onclick="ListPage.TableExtendList(true)">Load more (+5)</button>
        <button onclick="ListPage.TableExtendList(false)">Load all</button>
        <button onclick="ListPage.TableRefresh()">Refresh</button>
    </script>
    <script id="goods_datatable_row" type="text/template">
        <tr>
            <td>{{ id }}</td>
            <td>{{ name }}</td>
            <td>{{ average_price }}</td>
            <td>{{ description }}</td>
            <td>
                <button class="table_action" onclick="ListPage.TableEditRowMenu( {{ id }} );">Edit</button>
                <button class="table_action" onclick="ListPage.TableDeleteRow( {{ id }} );">Delete</button>
            </td>
        </tr>
    </script>

    <script id="goods_add_template" type="text/template">
        <h1>Add goods</h1>
        <p> There you can add new goods to base.</p>
        <div class = "add_goods_panel">
            <p><b>*</b> Name:</p>
            <input type="text" name="input_name">
            <p><b>*</b> Average Price:</p>
            <input type="text" name="input_average_price">
            <p>Description:</p>
            <input type="text" name="input_description">
            <p><button onclick="ListPage.TableAddSend()">Send data</button></p>
        </div>
    </script>
    <script id="goods_edit_template" type="text/template">
        <h1>Edit goods</h1>
        <p> There you can edit goods in the base.</p>
        <p> Edit field which you want to change.</p>
        <div class = "edit_goods_panel">
            <input type="hidden" name="input_id" value="{{ id }}" />
            <p> Name:</p>
            <input type="text" name="input_name" value="{{ name }}">
            <p> Average Price:</p>
            <input type="text" name="input_average_price" value="{{ average_price }}">
            <p> Description:</p>
            <input type="text" name="input_description"  value="{{ description }}">
            <p><button onclick="ListPage.TableEditSend()">Send data</button></p>
        </div>
    </script>
    <!--  -->

    <script id="storage_template" type="text/template">
        <h1>Storage menu</h1>
        <p> In the menu you can review available goods and storage info.</p>
        <br>
        <h3><i>General info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
            <li>Something2</li>
            <li>Something3</li>
        </ul>
        <br>
        <h3><i>Available actions:</i></h3>
        <ul class="content_ul">
            <li><a href="#storage_available"><u>List of available Goods</u></a> - show first 10 goods in one of storages, you can expand list by each 10 next goods.</li>
            <li><a href="#storage_info"><u>Review storage info</u></a> - you can choose one storage and review all necessary info (choose point above to review goods).</li>
        </ul>
    </script>
    <script id="storage_available_template" type="text/template"> </script>
    <script id="storage_info_template" type="text/template"> </script>

    <script id="imports_template" type="text/template">
        <h1>Import menu</h1>
        <p> In the menu you can review all imports, check imports info and make new one.</p>
        <br>
        <h3><i>General info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
            <li>Something2</li>
            <li>Something3</li>
        </ul>
        <br>
        <h3><i>Available actions:</i></h3>
        <ul class="content_ul">
            <li><a href="#import_action"><u>Make new Import</u></a> - Choose the point if you are going to import some new goods.
                <b>!Be sure goods and provider already exist in the database!</b>.</li>
            <li><a href="#import_find"><u>Find Import Document</u></a> - you can find one (by using ID) or more document by using filter of fields.</li>
        </ul>
    </script>
    <script id="import_action_template" type="text/template"> </script>
    <script id="import_list_template" type="text/template"> </script>

    <script id="exports_template" type="text/template">
        <h1>Export menu</h1>
        <p> In the menu you can review all exports, check exports info and make new one.</p>
        <br>
        <h3><i>General info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
            <li>Something2</li>
            <li>Something3</li>
        </ul>
        <br>
        <h3><i>Available actions:</i></h3>
        <ul class="content_ul">
            <li><a href="#import_action"><u>Make new Export</u></a> - Choose the point if you are going to export some goods.
                <b>!Be sure customer already exist in the database!</b>.</li>
            <li><a href="#import_find"><u>Find Export Document</u></a> - you can find one (by using ID) or more document by using filter of fields.</li>
        </ul>
    </script>
    <script id="export_action_template" type="text/template"> </script>
    <script id="export_list_template" type="text/template"> </script>

    <script id="reports_template" type="text/template">
        <h1>Report menu</h1>
        <p> In the menu you can review reports and create new one.</p>
        <br>
        <h3><i>General info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
            <li>Something2</li>
            <li>Something3</li>
        </ul>
        <br>
        <h3><i>Available actions:</i></h3>
        <ul class="content_ul">
            <li><a href="#report_last"><u>Show last Report</u></a> - show last made Report by any user.</li>
            <li><a href="#report_list"><u>Show Report list</u></a> - show first 10 goods, you can expand list by each 10 next goods.</li>
            <li><a href="#report_make"><u>Make new Report</u></a> - you can make new Report for custom date period.</li>
        </ul>
    </script>
    <script id="report_last_template" type="text/template"> </script>
    <script id="report_list_template" type="text/template"> </script>
    <script id="report_make_template" type="text/template"> </script>

    <script id="system_template" type="text/template">
        <h1>System settings</h1>
        <p><b>Be careful!</b> There are important settings which can break system.</p>
        <h3><i>System info:</i></h3>
        <ul class="content_ul">
            <li>Something1</li>
        </ul>
        <br>
        <h3>System action</h3>
        <ul class="content_ul">
            <li><a><u>Rebuild base of available goods</u></a> - available goods data writes in base relatively import nd export documents
                                                                If available goods data are damaged, choose the point to rebuild the base.</li>
            <li><a href="#"><u>Show users last visits</u></a> - you can see the date of last visit of every user registered in the system.</li>
        </ul>
    </script>
    <script id="forbidden_template" type="text/template">
        <h1>Forbidden!!!</h1>
        <p>You don't have permissions for access to this section.</p>
    </script>


    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="js/addons/datatables.min.js"></script>
    <script type="text/javascript" src="js/popper.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/mdb.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.5/handlebars.min.js"></script>
    <script type="text/javascript" src="js/menu.js" ></script>
    <!--  -->
</body>
</html>