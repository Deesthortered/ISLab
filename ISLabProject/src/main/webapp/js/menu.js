class Common {

}
class Router {
    static initialize(){
        addEventListener('hashchange', Router.handleHash);
        Router.handleHash();
    }
    static handleHash() {
        switch (location.hash) {
            case ''                   : { InterfaceHashHandler.StartPage();        } break;

            case '#provider'          : { InterfaceHashHandler.Provider();         } break;
            case '#provider_list'     : { InterfaceHashHandler.ProviderList();     } break;
            case '#provider_find'     : { InterfaceHashHandler.ProviderFind();     } break;
            case '#provider_add'      : { InterfaceHashHandler.ProviderAdd();      } break;

            case '#customer'          : { InterfaceHashHandler.Customer();         } break;
            case '#customer_list'     : { InterfaceHashHandler.CustomerList();     } break;
            case '#customer_find'     : { InterfaceHashHandler.CustomerFind();     } break;
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
    static role;
    static roles = { Admin : 'Admin', ViewManager : 'ViewManager', ImportManager : 'ImportManager', ExportManager : 'ExportManager' };

    static DefineUser() {
        document.location.hash = '';

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, false);
        http.onreadystatechange = function () {
            InterfaceHashHandler.role = http.responseText;
        };
        http.send('get_role\n');

        const username_field = document.getElementById('username_field');
        username_field.innerHTML = TemplateHandler.Render('username_template', { username: InterfaceHashHandler.role});

        let menu_startpage = document.getElementById('menu_ul');
        switch (InterfaceHashHandler.role) {
            case this.roles.Admin: {
                menu_startpage.innerHTML = TemplateHandler.Render('admin_menu_template')
            } break;
            case this.roles.ViewManager: {
                menu_startpage.innerHTML = TemplateHandler.Render('view_menu_template')
            } break;
            case this.roles.ImportManager: {
                menu_startpage.innerHTML = TemplateHandler.Render('import_menu_template')
            } break;
            case this.roles.ExportManager: {
                menu_startpage.innerHTML = TemplateHandler.Render('export_menu_template')
            } break;
            default: alert("Unknown role: " + InterfaceHashHandler.role);
        }
    }
    static CheckPermission(privileged_users) {
        for (let i = 0; i < privileged_users.length; i++) {
            if (privileged_users[i] === this.role)
                return false;
        }
        alert('forbidden');
        document.location.hash = 'forbidden';
        return true;
    }

    static StartPage() {
        const data = {
            user_name : 'user_name',
            user_role : '${role}',
            user_permissions : 'user_permissions',
            last_visit : 'last_visit'
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('startpage_template', data);
    }

    static Provider() {
        if (this.CheckPermission( [this.roles.Admin, this.roles.ViewManager, this.roles.ImportManager] )) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('provider_template', data);
    }
    static ProviderList() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('provider_list_template', data);
        $('#dtDynamicVerticalScrollExample').DataTable({
            "scrollY": "50vh",
            "scrollCollapse": true,
        });
        $('.dataTables_length').addClass('bs-select');
    }
    static ProviderFind() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('provider_find_template', data);
    }
    static ProviderAdd() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('provider_add_template', data);
    }

    static Customer() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('customer_template', data);
    }
    static CustomerList() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('customer_list_template', data);
    }
    static CustomerFind() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('customer_find_template', data);
    }
    static CustomerAdd() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('customer_add_template', data);
    }

    static Goods() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('goods_template', data);
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
        if (this.CheckPermission([this.roles.Admin, this.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('goods_add_template', data);
    }

    static Storage() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('storage_template', data);
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
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('imports_template', data);
    }
    static ImportsAction() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('import_action_template', data);
    }
    static ImportsFind() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ImportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('import_find_template', data);
    }

    static Exports() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('exports_template', data);
    }
    static ExportsAction() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('export_action_template', data);
    }
    static ExportsFind() {
        if (this.CheckPermission([this.roles.Admin, this.roles.ViewManager, this.roles.ExportManager])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('export_find_template', data);
    }

    static Reports() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('reports_template', data);
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
        if (this.CheckPermission( [this.roles.Admin])) return;
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('system_template', data);
    }
    static ForbiddenPage() {
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('forbidden_template', {});
    }
}
class InterfaceActionHandler {


    static RebuildAvailableGoodsData() {

    }
}

function Logout() {
    document.location.hash = '';
    let Http = new XMLHttpRequest();
    let url = window.location.href.split('#')[0];
    let param = "logout=true";
    Http.open("GET", url+"?"+param, true);
    Http.send(param);
    alert("You are logged out.");
    document.location.reload(true);
}

(async () => {
    InterfaceHashHandler.DefineUser();
    Router.initialize();
})();
