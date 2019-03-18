class Common {
    static roles = Object.freeze({
        Admin         : 'Admin',
        ViewManager   : 'ViewManager',
        ImportManager : 'ImportManager',
        ExportManager : 'ExportManager',
    });
    static Entity = Object.freeze({
        Provider : 'provider',
        Customer : 'customer',
        Goods    : 'goods'
    });
    static list_size = Object.freeze(5);

    // Temporary
    static role;
    static list_begin_ind;
    static table_data;
    static limited;
    static filter;

    static ClearTemporary() {
        this.list_begin_ind = 0;
        this.table_data = [];
        this.limited = true;
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
            case '#goods_add'         : { InterfaceHashHandler.GoodsAdd();         } break;

            case '#storage'           : { InterfaceHashHandler.Storage();          } break;
            case '#storage_available' : { InterfaceHashHandler.StorageAvailable(); } break;
            case '#storage_info'      : { InterfaceHashHandler.StorageInfo();      } break;

            case '#imports'           : { InterfaceHashHandler.Imports();          } break;
            case '#import_action'     : { InterfaceHashHandler.ImportsAction();    } break;
            case '#import_list'       : { InterfaceHashHandler.ImportsList();      } break;

            case '#exports'           : { InterfaceHashHandler.Exports();          } break;
            case '#export_action'     : { InterfaceHashHandler.ExportsAction();    } break;
            case '#export_list'       : { InterfaceHashHandler.ExportsList();      } break;

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

class QueryMaker {
    static GetEntityList(entity) {
        return "get_" + entity + "_list\n";
    }
    static DeleteEntity(entity) {
        return "delete_" + entity + "\n";
    }
    static AddEntity(entity) {
        return "add_" + entity + "\n";
    }
    static EditEntity(entity) {
        return "edit_" + entity + "\n";
    }
}
class EntityFilters {
    static undefined_value = Object.freeze('____undefined____');

    static getEmptyFilter(entity) {
        switch (entity) {
            case Common.Entity.Provider :
            case Common.Entity.Customer :
                return {
                    id: this.undefined_value,
                    name: this.undefined_value,
                    country: this.undefined_value,
                    description: this.undefined_value
                };
            case Common.Entity.Goods :
                return {
                    id: this.undefined_value,
                    name: this.undefined_value,
                    average_price: this.undefined_value,
                    description: this.undefined_value
                };

            default : {
                alert("Unknown entity \'" + entity + "\' at getEmptyFilter");
                return undefined;
            }
        }
    }
    static getQueryFilterRepresentation(entity, data) {
        switch (entity) {
            case Common.Entity.Provider :
            case Common.Entity.Customer :
                return "" +
                    String(data.id === undefined ? this.undefined_value : data.id) + "\n" +
                    String(data.name === undefined ? this.undefined_value : data.name) + "\n" +
                    String(data.country === undefined ? this.undefined_value : data.country) + "\n" +
                    String(data.description === undefined ? this.undefined_value : data.description) + "\n";
            case Common.Entity.Goods :
                return "" +
                    String(data.id === undefined ? this.undefined_value : data.id) + "\n" +
                    String(data.name === undefined ? this.undefined_value : data.name) + "\n" +
                    String(data.average_price === undefined ? this.undefined_value : data.average_price) + "\n" +
                    String(data.description === undefined ? this.undefined_value : data.description) + "\n";

            default : {
                alert("Unknown entity \'" + entity + "\' at getQueryFilterRepresentation");
                return undefined;
            }
        }
    }
}
class ListPage {
    static current_entity;

    static TableLoad(callback) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, false);
        http.onreadystatechange = function() {
            let new_data = JSON.parse(http.responseText);
            Common.table_data = Common.table_data.concat(new_data);
            callback(Common.table_data);
        };
        let query_body =
            QueryMaker.GetEntityList(this.current_entity) +
            EntityFilters.getQueryFilterRepresentation(this.current_entity, Common.filter) +
            String(Common.limited) + "\n" +
            String(Common.list_begin_ind) + "\n" +
            String(Common.list_size) + "\n";
        http.send(query_body);
    }
    static TableFill(data) {
        const table_place = document.getElementById(this.current_entity + '_table_place');
        table_place.innerHTML = TemplateHandler.Render(this.current_entity + '_list_table', {});
        let table_body = table_place.getElementsByTagName('tbody')[0];

        for (let i = 0; i < data.length; i++)
            table_body.insertAdjacentHTML('beforeend', TemplateHandler.Render(this.current_entity + '_datatable_row', data[i]));

        $('#dt_'+ this.current_entity + 'Table').DataTable({
            "retrieve": true,
            "scrollY": "50vh",
            "scrollCollapse": true,
        });
        $('.dataTables_length').addClass('bs-select');
    }
    static TableClear() {
        Common.table_data = [];
        let table_body = document.getElementById(this.current_entity + '_table_place').getElementsByTagName('tbody')[0];
        while (table_body.firstChild) {
            table_body.removeChild(table_body.firstChild);
        }
    }
    static TableRefresh() {
        let prev_ind = Common.list_begin_ind;
        let prev_size = Common.list_size;

        Common.list_size += Common.list_begin_ind;
        Common.list_begin_ind = 0;

        this.TableClear();

        this.TableLoad(function(data) {
            ListPage.TableFill(data);

            Common.list_begin_ind = prev_ind;
            Common.list_size = prev_size;
        });
    }
    static TableEditRowMenu(id) {
        let sure = confirm("Are you sure want to edit the record with ID = " + id +"?");
        if (!sure) return;

        Common.ClearTemporary();
        Common.filter = EntityFilters.getEmptyFilter(this.current_entity);
        Common.filter.id = id;

        this.TableLoad(function (data) {
            document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render(ListPage.current_entity + '_edit_template', data[0]);
        });
    }
    static TableDeleteRow(id) {
        let sure = confirm("Are you sure want to delete the record with ID = " + id +"?");
        if (sure) {
            let http = new XMLHttpRequest();
            http.open('POST', window.location.href, true);
            http.onreadystatechange = function () {
                if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                    if (http.responseText === "ok") {
                        alert("The " + ListPage.current_entity + " are deleted successfully");
                        ListPage.TableRefresh();
                    }
                    else
                        alert("The " + ListPage.current_entity + " was not deleted, some trouble happened on the server side.");
                } else if (http.readyState === XMLHttpRequest.DONE) {
                    alert("The " + ListPage.current_entity + " was not deleted, some trouble happened with the request.");
                }
            };
            let query_body =
                QueryMaker.DeleteEntity(ListPage.current_entity) +
                id + "\n";
            http.send(query_body);
        }
    }
    static TableExtendList(limited) {
        Common.limited = limited;
        Common.list_begin_ind += Common.list_size;
        ListPage.TableLoad(function(data) {
            if (!limited) {
                ListPage.TableClear();
                Common.list_begin_ind = data.length;
            }
            ListPage.TableFill(data);
        });
    }
    static TableSetFilter() {
        Common.ClearTemporary();

        Common.filter = EntityFilters.getEmptyFilter(this.current_entity);
        let properties = Object.keys(Common.filter);
        let panel = $('#' + this.current_entity + '_filter');
        for (let i = 0; i < properties.length; i++) {
            let input = panel.find('input[name=\'input_' + properties[i] + '\']').val();
            Common.filter[properties[i]] = (input === '' ? EntityFilters.undefined_value : input);
        }

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render(this.current_entity + '_list_template', {});
        this.TableLoad(function(data) {
            ListPage.TableFill(data);
        });
    }
    static TableAddSend() {
        let new_entry = EntityFilters.getEmptyFilter(this.current_entity);
        let panel = $('.add_' + this.current_entity + '_panel');

        let ok = true;
        let properties = Object.keys(new_entry);
        for (let i = 0; i < properties.length; i++) {
            let input = panel.find('input[name=\'input_' + properties[i] + '\']');
            if (!(properties[i] === 'description') && input.val() === '') {
                alert("Fill all required fields!");
                ok = false;
            }
            new_entry[properties[i]] = (input.val() === '' ? EntityFilters.undefined_value : input.val());
            input.val('');
        }
        if (!ok) return;

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("The " + ListPage.current_entity + " is added successfully");
                }
                else
                    alert("The " + ListPage.current_entity + " was not added, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The " + ListPage.current_entity + " was not added, some trouble happened with the request.");
            }
        };
        let query_body =
            QueryMaker.AddEntity(this.current_entity) +
            EntityFilters.getQueryFilterRepresentation(this.current_entity, new_entry);
        http.send(query_body);
    }
    static TableEditSend() {
        let new_entry = EntityFilters.getEmptyFilter(this.current_entity);
        let panel = $('.edit_' + this.current_entity + '_panel');

        let ok = false;
        let properties = Object.keys(new_entry);
        for (let i = 0; i < properties.length; i++) {
            let input = panel.find('input[name=\'input_' + properties[i] + '\']');
            if (properties[i] === 'description' || !(input.val() === '')) {
                ok = true;
            }
            new_entry[properties[i]] = (input.val() === '' ? EntityFilters.undefined_value : input.val());
            input.val('');
        }
        if (!ok) {
            alert("Fill at least one field!");
            return;
        }

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("The " + ListPage.current_entity + " is edited successfully");
                }
                else
                    alert("The " + ListPage.current_entity + " was not edited, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The " + ListPage.current_entity + " was not edited, some trouble happened with the request.");
            }
        };
        let query_body =
            QueryMaker.EditEntity(this.current_entity) +
            EntityFilters.getQueryFilterRepresentation(this.current_entity, new_entry);
        http.send(query_body);
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

    static StartPage() {
        let data = {
            user_name: 'user_name',
            user_role: 'role',
            user_permissions: 'user_permissions',
            last_visit: 'last_visit'
        };
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('startpage_template', data);
    }

    static Provider() {
        if (this.CheckPermission( [Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager] )) return;
        ListPage.current_entity = Common.Entity.Provider;

        ListPage.TableLoad( function (data) {
            document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_template', data);
        });
    }
    static ProviderList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager])) return;
        ListPage.current_entity = Common.Entity.Provider;

        Common.filter = EntityFilters.getEmptyFilter(ListPage.current_entity);
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_list_template', {});
        ListPage.TableLoad(function(data) {
            ListPage.TableFill(data);
        });
    }
    static ProviderAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        ListPage.current_entity = Common.Entity.Provider;

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_add_template', {});
    }

    static Customer() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        ListPage.current_entity = Common.Entity.Customer;

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_template', {});
    }
    static CustomerList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        ListPage.current_entity = Common.Entity.Customer;

        Common.filter = EntityFilters.getEmptyFilter(ListPage.current_entity);
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_list_template', {});
        ListPage.TableLoad(function(data) {
            ListPage.TableFill(data);
        });
    }
    static CustomerAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ExportManager])) return;
        ListPage.current_entity = Common.Entity.Customer;

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_add_template', {});
    }

    static Goods() {
        ListPage.current_entity = Common.Entity.Goods;

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('goods_template', {});
    }
    static GoodsList() {
        ListPage.current_entity = Common.Entity.Goods;

        Common.filter = EntityFilters.getEmptyFilter(ListPage.current_entity);
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('goods_list_template', {});
        ListPage.TableLoad(function(data) {
            ListPage.TableFill(data);
        });
    }
    static GoodsAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        ListPage.current_entity = Common.Entity.Goods;

        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('goods_add_template', {});
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
    static ImportsList() {
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
    static ExportsList() {
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

(async () => {
    InterfaceHashHandler.DefineUser();
    Router.initialize();
})();
