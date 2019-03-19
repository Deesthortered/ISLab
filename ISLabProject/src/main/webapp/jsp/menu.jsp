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

    <!-- Table template scripts -->
    <script id="list_template" type="text/template">
        <h1> {{ entity_uppercase }} list</h1>
        <p>There you can review list of {{ entity_lowercase }} and edit it.</p>
        <div id="filter">
            <h3>Filter.</h3>
            <ul>

            </ul>
            <button onclick="ListPage.TableSetFilter()">Send request</button>
        </div>
        <br>
        <div id="table_place"></div>
        <br>
    </script>
    <script id="filter_input" type="text/template">
        <li>{{ property_uppercase }}: <input type="text" name="filter_{{ property_lowercase }}"></li>
    </script>
    <script id="list_table" type="text/template">
        <table id="dt_Table" class="table table-striped table-bordered table-sm" cellspacing="0" width="1200px">
            <thead>
            <tr>

            </tr>
            </thead>
            <tbody>

            </tbody>
            <tfoot>
            <tr>

            </tr>
            </tfoot>
        </table>
        <button onclick="ListPage.TableExtendList(true)">Load more (+{{ extend_size }})</button>
        <button onclick="ListPage.TableExtendList(false)">Load all</button>
        <button onclick="ListPage.TableRefresh()">Refresh</button>
    </script>
    <script id="list_header_title" type="text/template">
        <th class="th-sm">{{ property_uppercase }} </th>
    </script>
    <script id="list_footer_title" type="text/template">
        <th>{{ property_uppercase }} </th>
    </script>
    <script id="datatable_row_field" type="text/template">
        <td>{{ value }}</td>
    </script>
    <script id="datatable_row_buttons" type="text/template">
        <td>
            <button class="table_action" onclick="ListPage.TableEditRowMenu( {{ id }} );">Edit</button>
            <button class="table_action" onclick="ListPage.TableDeleteRow( {{ id }} );">Delete</button>
        </td>
    </script>

    <script id="add_template" type="text/template">
        <h1>Add {{ entity_lowercase }}</h1>
        <p> There you can add new {{ entity_lowercase }} to base.</p>
        <div class = "add_panel"></div>
    </script>
    <script id="add_template_field" type="text/template">
        <p>{{ property_uppercase }}:</p>
        <input type="text" name="input_{{ property_lowercase }}">
    </script>
    <script id="add_template_button" type="text/template">
        <p><button onclick="ListPage.TableAddSend()">Send data</button></p>
    </script>

    <script id="edit_template" type="text/template">
        <h1>Edit {{ entity_lowercase }}</h1>
        <p> There you can edit {{ entity_lowercase }} in the base.</p>
        <p> Edit field which you want to change.</p>
        <div class = "edit_panel"></div>
    </script>
    <script id="edit_template_field" type="text/template">
        <p>{{ property_uppercase }}:</p>
        <input type="text" name="input_{{ property_lowercase }}" value="{{ property_value }}">
    </script>
    <script id="edit_template_button" type="text/template">
        <input type="hidden" name="input_id" value="{{ id }}" />
        <p><button onclick="ListPage.TableEditSend()">Send data</button></p>
    </script>
    <!--  -->

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