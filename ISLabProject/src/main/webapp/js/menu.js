class Common {
    static roles = {
        Admin         : 'Admin',
        ViewManager   : 'ViewManager',
        ImportManager : 'ImportManager',
        ExportManager : 'ExportManager',
    };
    static list_size = 5;

    // Temporary
    static role;
    static list_begin_ind;
    static table_data;
    static filter;

    static ClearTemporary() {
        this.list_begin_ind = 0;
        this.table_data = [];
    }
}
class Router {
    static initialize(){
        addEventListener('hashchange', Router.handleHash);
        Router.handleHash();
    }
    static handleHash() {
        Common.ClearTemporary();
        switch (location.hash) {
            case ''                   : { InterfaceHashHandler.StartPage();        } break;

            case '#provider'          : { InterfaceHashHandler.Provider();         } break;
            case '#provider_list'     : { InterfaceHashHandler.ProviderList();     } break;
            case '#provider_add'      : { InterfaceHashHandler.ProviderAdd();      } break;

            case '#customer'          : { InterfaceHashHandler.Customer();         } break;
            case '#customer_list'     : { InterfaceHashHandler.CustomerList();     } break;
            case '#customer_add'      : { InterfaceHashHandler.CustomerAdd();      } break;

            case '#goods'             : { InterfaceHashHandler.Goods();            } break;
            case '#goods_list'        : { InterfaceHashHandler.GoodsList();        } break;
            case '#goods_find'        : { InterfaceHashHandler.GoodsFind();        } break;
            case '#goods_add'         : { InterfaceHashHandler.GoodsAdd();         } break;

            case '#storage'           : { InterfaceHashHandler.Storage();          } break;
            case '#storage_available' : { InterfaceHashHandler.StorageAvailable(); } break;
            case '#storage_info'      : { InterfaceHashHandler.StorageInfo();      } break;

            case '#imports'           : { InterfaceHashHandler.Imports();          } break;
            case '#import_action'     : { InterfaceHashHandler.ImportsAction();    } break;
            case '#import_find'       : { InterfaceHashHandler.ImportsFind();      } break;

            case '#exports'           : { InterfaceHashHandler.Exports();          } break;
            case '#export_action'     : { InterfaceHashHandler.ExportsAction();    } break;
            case '#export_find'       : { InterfaceHashHandler.ExportsFind();      } break;

            case '#reports'           : { InterfaceHashHandler.Reports();          } break;
            case '#report_last'       : { InterfaceHashHandler.ReportLast();       } break;
            case '#report_list'       : { InterfaceHashHandler.ReportList();       } break;
            case '#report_make'       : { InterfaceHashHandler.ReportMake();       } break;

            case '#system'            : { InterfaceHashHandler.System();           } break;
            case '#forbidden'         : { InterfaceHashHandler.ForbiddenPage();    } break;

            default: alert("Unknown hash-tag!");
        }
    }
}
class TemplateHandler {
    static Render(templateName, data = {}) {
        const templateElement = document.getElementById(templateName);
        const templateSource = templateElement.innerHTML;
        const renderFn = Handlebars.compile(templateSource);
        return renderFn(data);
    }
}

class InterfaceHashHandler {

    static DefineUser() {
        document.location.hash = '';

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, false);
        http.onreadystatechange = function () {
            Common.role = http.responseText;
        };
        http.send('get_role');

        const username_field = document.getElementById('username_field');
        username_field.innerHTML = TemplateHandler.Render('username_template', { username: Common.role});

        let menu_startpage = document.getElementById('menu_ul');
        switch (Common.role) {
            case Common.roles.Admin: {
                menu_startpage.innerHTML = TemplateHandler.Render('admin_menu_template')
            } break;
            case Common.roles.ViewManager: {
                menu_startpage.innerHTML = TemplateHandler.Render('view_menu_template')
            } break;
            case Common.roles.ImportManager: {
                menu_startpage.innerHTML = TemplateHandler.Render('import_menu_template')
            } break;
            case Common.roles.ExportManager: {
                menu_startpage.innerHTML = TemplateHandler.Render('export_menu_template')
            } break;
            default: alert("Unknown role: " + Common.role);
        }
    }
    static CheckPermission(privileged_users) {
        for (let i = 0; i < privileged_users.length; i++) {
            if (privileged_users[i] === Common.role)
                return false;
        }
        alert('forbidden');
        document.location.hash = 'forbidden';
        return true;
    }

    static StartPage() {
        let data = InterfaceActionHandler.StartPage_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('startpage_template', data);
    }

    static Provider() {
        if (this.CheckPermission( [Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager] )) return;
        let data = InterfaceActionHandler.Provider_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_template', data);
    }
    static ProviderList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager])) return;

        Common.filter = {
            id : -1,
            name : '',
            country : '',
            description : ''
        };

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_list_template', {});
        InterfaceActionHandler.ProviderTable_Load(function(data) {
            InterfaceActionHandler.ProviderTable_Fill(data);
        });
    }
    static ProviderAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_add_template', {});
    }
    static ProviderEdit(id) {
        InterfaceActionHandler.Provider_LoadOne(id, function (data) {
            document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_edit_template', data);
        });
    }

    static Customer() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        const data = InterfaceActionHandler.Customer_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_template', data);
    }
    static CustomerList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;

        Common.filter = {
            id : -1,
            name : '',
            country : '',
            description : ''
        };

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_list_template', {});
        InterfaceActionHandler.CustomerTable_Load(function(data) {
            InterfaceActionHandler.CustomerTable_Fill(data);
        });
    }
    static CustomerAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ExportManager])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_add_template', {});
    }
    static CustomerEdit(id) {
        InterfaceActionHandler.Customer_LoadOne(id, function (data) {
            document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_edit_template', data);
        });
    }

    static Goods() {
        const data = InterfaceActionHandler.Goods_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('goods_template', data);
    }
    static GoodsList() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('goods_list_template', data);
    }
    static GoodsFind() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('goods_find_template', data);
    }
    static GoodsAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('goods_add_template', data);
    }

    static Storage() {
        const data = InterfaceActionHandler.Storage_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('storage_template', data);
    }
    static StorageAvailable() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('storage_available_template', data);
    }
    static StorageInfo() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('storage_info_template', data);
    }

    static Imports() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager])) return;
        const data = InterfaceActionHandler.Import_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('imports_template', data);
    }
    static ImportsAction() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('import_action_template', data);
    }
    static ImportsFind() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('import_find_template', data);
    }

    static Exports() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        const data = InterfaceActionHandler.Export_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('exports_template', data);
    }
    static ExportsAction() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('export_action_template', data);
    }
    static ExportsFind() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('export_find_template', data);
    }

    static Reports() {
        const data = InterfaceActionHandler.Report_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('reports_template', data);
    }
    static ReportLast() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('report_last_template', data);
    }
    static ReportList() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('report_list_template', data);
    }
    static ReportMake() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('report_make_template', data);
    }

    static System() {
        if (this.CheckPermission( [Common.roles.Admin])) return;
        const data = InterfaceActionHandler.System_Load();
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('system_template', data);
    }
    static ForbiddenPage() {
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('forbidden_template', {});
    }
}
class InterfaceActionHandler {

    static StartPage_Load() {
        return {
            user_name: 'user_name',
            user_role: 'role',
            user_permissions: 'user_permissions',
            last_visit: 'last_visit'
        };
    }

    static Provider_Load() {
        return {};
    }
    static ProviderTable_Load(callback) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, false);
        http.onreadystatechange = function() {
            let new_data = JSON.parse(http.responseText);
            Common.table_data = Common.table_data.concat(new_data);
            callback(Common.table_data);
        };
        let query_body =
            "get_provider_list\n" +
            String(Common.filter.id) + "\n" +
            String(Common.filter.name) + "\n" +
            String(Common.filter.country) + "\n" +
            String(Common.filter.description) + "\n" +
            String(Common.list_begin_ind) + "\n" +
            String(Common.list_size) + "\n";
        http.send(query_body);
    }
    static ProviderTable_Fill(data) {
        const table_place = document.getElementById('provider_table_place');
        table_place.innerHTML = TemplateHandler.Render('provider_list_table', {});
        let table_body = table_place.getElementsByTagName('tbody')[0];

        for (let i = 0; i < data.length; i++)
            table_body.insertAdjacentHTML('beforeend', TemplateHandler.Render('provider_datatable_row', data[i]));

        $('#dtProviderTable').DataTable({
            "retrieve": true,
            "scrollY": "50vh",
            "scrollCollapse": true,
        });
        $('.dataTables_length').addClass('bs-select');
    }
    static ProviderTable_EditRow(id) {
        let sure = confirm("Are you sure want to edit the record with ID = " + id +"?");
        if (sure)
            InterfaceHashHandler.ProviderEdit(id);
    }
    static ProviderTable_DeleteRow(id) {
        let sure = confirm("Are you sure want to delete the record with ID = " + id +"?");
        if (sure) {
            let http = new XMLHttpRequest();
            http.open('POST', window.location.href, true);
            http.onreadystatechange = function () {
                if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                    if (http.responseText === "ok") {
                        alert("Provider are deleted successfully");
                        InterfaceActionHandler.ProviderTable_Refresh();
                    }
                    else
                        alert("Provider was not deleted, some trouble happened on the server side.");
                } else if (http.readyState === XMLHttpRequest.DONE) {
                    alert("Provider was not deleted, some trouble happened with the request.");
                }
            };
            let query_body =
                "delete_provider" + "\n" +
                id + "\n";
            http.send(query_body);
        }
    }
    static ProviderTable_ExtendList() {
        Common.list_begin_ind += Common.list_size;
        InterfaceActionHandler.ProviderTable_Load(function(data) {
            InterfaceActionHandler.ProviderTable_Fill(data);
        });
    }
    static ProviderTable_Refresh() {

        let prev_ind = Common.list_begin_ind;
        let prev_size = Common.list_size;

        Common.list_size += Common.list_begin_ind;
        Common.list_begin_ind = 0;

        Common.table_data = [];
        let table_body = document.getElementById('provider_table_place').getElementsByTagName('tbody')[0];
        while (table_body.firstChild) {
            table_body.removeChild(table_body.firstChild);
        }

        this.ProviderTable_Load(function(data) {
            InterfaceActionHandler.ProviderTable_Fill(data);
        });

        $('#dtProviderTable').DataTable({
            "retrieve": true,
            "scrollY": "50vh",
            "scrollCollapse": true,
        });
        $('.dataTables_length').addClass('bs-select');

        Common.list_begin_ind = prev_ind;
        Common.list_size = prev_size;
    }
    static Provider_LoadOne(id, callback) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, false);
        http.onreadystatechange = function() {
            callback(JSON.parse(http.responseText));
        };
        let query_body =
            "get_one_provider\n" +
            String(id) + "\n";
        http.send(query_body);
    }
    static ProviderAdd_Send() {
        let panel = $('.add_provider_panel');
        let name = panel.find('input[name=\'input_name\']');
        let country = panel.find('input[name=\'input_country\']');
        let description = panel.find('input[name=\'input_description\']');

        if (name.val() === '' || country.val() === '') {
            alert("Fill all required fields!");
            return;
        }

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("Provider are added successfully");
                    name.val('');
                    country.val('');
                    description.val('');
                }
                else
                    alert("Provider was not added, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("Provider was not added, some trouble happened with the request.");
            }
        };
        let query_body =
            "add_provider" + "\n" +
            String(name.val()) + "\n" +
            String(country.val()) + "\n" +
            String(description.val()) + "\n";
        http.send(query_body);
    }
    static ProviderEdit_Send() {
        let panel = $('.edit_provider_panel');
        let id = panel.find('input[name=\'input_id\']');
        let name = panel.find('input[name=\'input_name\']');
        let country = panel.find('input[name=\'input_country\']');
        let description = panel.find('input[name=\'input_description\']');

        if (name.val() === '' && country.val() === '' && description.val() === '') {
            alert("Fill at least one field!");
            return;
        }

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("Provider are edited successfully");
                    InterfaceHashHandler.ProviderList();
                }
                else
                    alert("Provider was not edited, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("Provider was not edited, some trouble happened with the request.");
            }
        };
        let query_body =
            "edit_provider" + "\n" +
            String(id.val()) + "\n" +
            String(name.val()) + "\n" +
            String(country.val()) + "\n" +
            String(description.val()) + "\n";
        http.send(query_body);
    }
    static ProviderTable_SetFilter() {
        Common.ClearTemporary();

        let panel = $('#provider_filter');
        let id = panel.find('input[name=\'filter_id\']');
        let name = panel.find('input[name=\'filter_name\']');
        let country = panel.find('input[name=\'filter_country\']');
        let description = panel.find('input[name=\'filter_description\']');

        Common.filter = {
            id : String(id.val()) === '' ? -1 : Number(id.val()),
            name : String(name.val()),
            country : String(country.val()),
            description : String(description.val())
        };

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_list_template', {});
        InterfaceActionHandler.ProviderTable_Load(function(data) {
            InterfaceActionHandler.ProviderTable_Fill(data);
        });
    }

    static Customer_Load() {
        return {};
    }
    static CustomerTable_Load(callback) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, false);
        http.onreadystatechange = function() {
            let new_data = JSON.parse(http.responseText);
            Common.table_data = Common.table_data.concat(new_data);
            callback(Common.table_data);
        };
        let query_body =
            "get_customer_list\n" +
            String(Common.filter.id) + "\n" +
            String(Common.filter.name) + "\n" +
            String(Common.filter.country) + "\n" +
            String(Common.filter.description) + "\n" +
            String(Common.list_begin_ind) + "\n" +
            String(Common.list_size) + "\n";
        http.send(query_body);
    }
    static CustomerTable_Fill(data) {
        const table_place = document.getElementById('customer_table_place');
        table_place.innerHTML = TemplateHandler.Render('customer_list_table', {});
        let table_body = table_place.getElementsByTagName('tbody')[0];

        for (let i = 0; i < data.length; i++)
            table_body.insertAdjacentHTML('beforeend', TemplateHandler.Render('customer_datatable_row', data[i]));

        $('#dtCustomerTable').DataTable({
            "retrieve": true,
            "scrollY": "50vh",
            "scrollCollapse": true,
        });
        $('.dataTables_length').addClass('bs-select');
    }
    static CustomerTable_EditRow(id) {
        let sure = confirm("Are you sure want to edit the record with ID = " + id +"?");
        if (sure)
            InterfaceHashHandler.CustomerEdit(id);
    }
    static CustomerTable_DeleteRow(id) {
        let sure = confirm("Are you sure want to delete the record with ID = " + id +"?");
        if (sure) {
            let http = new XMLHttpRequest();
            http.open('POST', window.location.href, true);
            http.onreadystatechange = function () {
                if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                    if (http.responseText === "ok") {
                        alert("Customer are deleted successfully");
                        InterfaceActionHandler.CustomerTable_Refresh();
                    }
                    else
                        alert("Customer was not deleted, some trouble happened on the server side.");
                } else if (http.readyState === XMLHttpRequest.DONE) {
                    alert("Customer was not deleted, some trouble happened with the request.");
                }
            };
            let query_body =
                "delete_customer" + "\n" +
                id + "\n";
            http.send(query_body);
        }
    }
    static CustomerTable_ExtendList() {
        Common.list_begin_ind += Common.list_size;
        InterfaceActionHandler.CustomerTable_Load(function(data) {
            InterfaceActionHandler.CustomerTable_Fill(data);
        });
    }
    static CustomerTable_Refresh() {

        let prev_ind = Common.list_begin_ind;
        let prev_size = Common.list_size;

        Common.list_size += Common.list_begin_ind;
        Common.list_begin_ind = 0;

        Common.table_data = [];
        let table_body = document.getElementById('customer_table_place').getElementsByTagName('tbody')[0];
        while (table_body.firstChild) {
            table_body.removeChild(table_body.firstChild);
        }

        this.CustomerTable_Load(function(data) {
            InterfaceActionHandler.CustomerTable_Fill(data);
        });

        $('#dtCustomerTable').DataTable({
            "retrieve": true,
            "scrollY": "50vh",
            "scrollCollapse": true,
        });
        $('.dataTables_length').addClass('bs-select');

        Common.list_begin_ind = prev_ind;
        Common.list_size = prev_size;
    }
    static Customer_LoadOne(id, callback) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, false);
        http.onreadystatechange = function() {
            callback(JSON.parse(http.responseText));
        };
        let query_body =
            "get_one_customer\n" +
            String(id) + "\n";
        http.send(query_body);
    }
    static CustomerAdd_Send() {
        let panel = $('.add_customer_panel');
        let name = panel.find('input[name=\'input_name\']');
        let country = panel.find('input[name=\'input_country\']');
        let description = panel.find('input[name=\'input_description\']');

        if (name.val() === '' || country.val() === '') {
            alert("Fill all required fields!");
            return;
        }

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("Customer are added successfully");
                    name.val('');
                    country.val('');
                    description.val('');
                }
                else
                    alert("Customer was not added, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("Customer was not added, some trouble happened with the request.");
            }
        };
        let query_body =
            "add_customer" + "\n" +
            String(name.val()) + "\n" +
            String(country.val()) + "\n" +
            String(description.val()) + "\n";
        http.send(query_body);
    }
    static CustomerEdit_Send() {
        let panel = $('.edit_customer_panel');
        let id = panel.find('input[name=\'input_id\']');
        let name = panel.find('input[name=\'input_name\']');
        let country = panel.find('input[name=\'input_country\']');
        let description = panel.find('input[name=\'input_description\']');

        if (name.val() === '' && country.val() === '' && description.val() === '') {
            alert("Fill at least one field!");
            return;
        }

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("Customer are edited successfully");
                    InterfaceHashHandler.CustomerList();
                }
                else
                    alert("Customer was not edited, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("Customer was not edited, some trouble happened with the request.");
            }
        };
        let query_body =
            "edit_customer" + "\n" +
            String(id.val()) + "\n" +
            String(name.val()) + "\n" +
            String(country.val()) + "\n" +
            String(description.val()) + "\n";
        http.send(query_body);
    }
    static CustomerTable_SetFilter() {
        Common.ClearTemporary();

        let panel = $('#customer_filter');
        let id = panel.find('input[name=\'filter_id\']');
        let name = panel.find('input[name=\'filter_name\']');
        let country = panel.find('input[name=\'filter_country\']');
        let description = panel.find('input[name=\'filter_description\']');

        Common.filter = {
            id : String(id.val()) === '' ? -1 : Number(id.val()),
            name : String(name.val()),
            country : String(country.val()),
            description : String(description.val())
        };

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_list_template', {});
        InterfaceActionHandler.CustomerTable_Load(function(data) {
            InterfaceActionHandler.CustomerTable_Fill(data);
        });
    }

    static Goods_Load() {
        return {};
    }
    static Storage_Load() {
        return {};
    }
    static Import_Load() {
        return {};
    }
    static Export_Load() {
        return {};
    }
    static Report_Load() {
        return {};
    }
    static System_Load() {
        return {};
    }

    static Logout() {
        document.location.hash = '';
        let Http = new XMLHttpRequest();
        let url = window.location.href.split('#')[0];
        let param = "logout=true";
        Http.open("GET", url+"?"+param, true);
        Http.send(param);
        alert("You are logged out.");
        document.location.reload(true);
    }
}

(async () => {
    InterfaceHashHandler.DefineUser();
    Router.initialize();
})();
