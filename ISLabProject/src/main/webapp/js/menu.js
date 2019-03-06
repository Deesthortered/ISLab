class Router {
    static initialize(){
        addEventListener('hashchange', Router.handleHash);
        Router.handleHash();
    }
    static handleHash() {
        switch (location.hash) {
            case ''                   : { InterfaceHandler.StartPage();        } break;

            case '#provider'          : { InterfaceHandler.Provider();         } break;
            case '#provider_list'     : { InterfaceHandler.ProviderList();     } break;
            case '#provider_find'     : { InterfaceHandler.ProviderFind();     } break;
            case '#provider_add'      : { InterfaceHandler.ProviderAdd();      } break;

            case '#customer'          : { InterfaceHandler.Customer();         } break;
            case '#customer_list'     : { InterfaceHandler.CustomerList();     } break;
            case '#customer_find'     : { InterfaceHandler.CustomerFind();     } break;
            case '#customer_add'      : { InterfaceHandler.CustomerAdd();      } break;

            case '#goods'             : { InterfaceHandler.Goods();            } break;
            case '#goods_list'        : { InterfaceHandler.GoodsList();        } break;
            case '#goods_find'        : { InterfaceHandler.GoodsFind();        } break;
            case '#goods_add'         : { InterfaceHandler.GoodsAdd();         } break;

            case '#storage'           : { InterfaceHandler.Storage();          } break;
            case '#storage_available' : { InterfaceHandler.StorageAvailable(); } break;
            case '#storage_info'      : { InterfaceHandler.StorageInfo();      } break;

            case '#imports'           : { InterfaceHandler.Imports();          } break;
            case '#import_action'     : { InterfaceHandler.ImportsAction();    } break;
            case '#import_find'       : { InterfaceHandler.ImportsFind();      } break;

            case '#exports'           : { InterfaceHandler.Exports();          } break;
            case '#export_action'     : { InterfaceHandler.ExportsAction();    } break;
            case '#export_find'       : { InterfaceHandler.ExportsFind();      } break;

            case '#reports'           : { InterfaceHandler.Reports();          } break;
            case '#report_last'       : { InterfaceHandler.ReportLast();       } break;
            case '#report_list'       : { InterfaceHandler.ReportList();       } break;
            case '#report_make'       : { InterfaceHandler.ReportMake();       } break;

            case '#system'            : { InterfaceHandler.System();           } break;

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
class InterfaceHandler {
    static role;
    static DefineUser() {
        document.location.hash = '';
        InterfaceHandler.role = '${role}';

        const username_field = document.getElementById('username_field');
        username_field.innerHTML = TemplateHandler.Render('username_template', { username: InterfaceHandler.role});

        let menu_startpage = document.getElementById('menu_ul');
        switch (InterfaceHandler.role) {
            case 'Admin': {
                menu_startpage.innerHTML = TemplateHandler.Render('admin_menu_template')
            } break;
            case 'ViewManager': {
                menu_startpage.innerHTML = TemplateHandler.Render('view_menu_template')
            } break;
            case 'ImportManager': {
                menu_startpage.innerHTML = TemplateHandler.Render('import_menu_template')
            } break;
            case 'ExportManager': {
                menu_startpage.innerHTML = TemplateHandler.Render('export_menu_template')
            } break;
            default: alert("Unknown role");
        }
    }

    static StartPage() {

    }

    static Provider() {

    }
    static ProviderList() {

    }
    static ProviderFind() {

    }
    static ProviderAdd() {

    }

    static Customer() {

    }
    static CustomerList() {

    }
    static CustomerFind() {

    }
    static CustomerAdd() {

    }

    static Goods() {

    }
    static GoodsList() {

    }
    static GoodsFind() {

    }
    static GoodsAdd() {

    }

    static Storage() {

    }
    static StorageAvailable() {

    }
    static StorageInfo() {

    }

    static Imports() {

    }
    static ImportsAction() {

    }
    static ImportsFind() {

    }

    static Exports() {

    }
    static ExportsAction() {

    }
    static ExportsFind() {

    }

    static Reports() {

    }
    static ReportLast() {

    }
    static ReportList() {

    }
    static ReportMake() {

    }

    static System() {

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
    InterfaceHandler.DefineUser();
    Router.initialize();
})();


