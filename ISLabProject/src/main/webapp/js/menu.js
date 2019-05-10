class Common {
    static roles = Object.freeze({
        Admin         : 'Admin',
        ViewManager   : 'ViewManager',
        ImportManager : 'ImportManager',
        ExportManager : 'ExportManager',
    });
    static EntityArray = Object.freeze([
        'provider',
        'customer',
        'goods',
        'storage',
        'import document',
        'export document',
        'import goods',
        'export goods',
        'import move document',
        'export move document',
        'import summary',
        'export summary',
        'available goods',
    ]);
    static EntityMap = Object.freeze({
        Provider           : 'provider',
        Customer           : 'customer',
        Goods              : 'goods',
        Storage            : 'storage',
        ImportDocument     : 'import document',
        ExportDocument     : 'export document',
        ImportGoods        : 'import goods',
        ExportGoods        : 'export goods',
        ImportMoveDocument : 'import move document',
        ExportMoveDocument : 'export move document',
        ImportSummary      : 'import summary',
        ExportSummary      : 'export summary',
        AvailableGoods     : 'available goods',
    });
    // Temporary
    static role;

    static ProviderList;
    static CustomerList;
    static GoodsList;
    static ImportsList;
    static ExportsList;
    static ImportGoodsList;
    static ExportGoodsList;
    static ImportMoveDocumentList;
    static ExportMoveDocumentList;
    static AvailableList;
    static StorageList;
    static ImportSummaryList;
    static ExportSummaryList;

    static capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }
    static initialize() {
        Common.ProviderList           = new ListPage('ProviderList', 'dynamic_panel',          Common.EntityMap.Provider,           true, true, false, false, true);
        Common.CustomerList           = new ListPage('CustomerList', 'dynamic_panel',          Common.EntityMap.Customer,           true, true, false, false, true);
        Common.GoodsList              = new ListPage('GoodsList', 'dynamic_panel',             Common.EntityMap.Goods,              true, true, true, false, false);
        Common.ImportsList            = new ListPage('ImportsList', 'dynamic_panel',           Common.EntityMap.ImportDocument,     false, false, false, true, false);
        Common.ExportsList            = new ListPage('ExportsList', 'dynamic_panel',           Common.EntityMap.ExportDocument,     false, false, false, true, false);
        Common.ImportGoodsList        = new ListPage('ImportGoodsList', 'dynamic_panel',       Common.EntityMap.ImportGoods,        false, false, false, true, false);
        Common.ExportGoodsList        = new ListPage('ExportGoodsList','dynamic_panel',        Common.EntityMap.ExportGoods,        false, false, false, true, false);
        Common.ImportMoveDocumentList = new ListPage('ImportMoveDocumentList', 'dynamic_panel',Common.EntityMap.ImportMoveDocument, false, false, false, false, false);
        Common.ExportMoveDocumentList = new ListPage('ExportMoveDocumentList', 'dynamic_panel',Common.EntityMap.ExportMoveDocument, false, false, false, false, false);
        Common.AvailableList          = new ListPage('AvailableList', 'dynamic_panel',         Common.EntityMap.AvailableGoods,     false, false, true, false, false);
        Common.StorageList            = new ListPage('StorageList',   'dynamic_panel',         Common.EntityMap.Storage       ,     false, false, false, false, false);
        Common.ImportSummaryList      = new ListPage('ImportSummaryList','dynamic_panel',      Common.EntityMap.ImportSummary,      false, false, false, true, false);
        Common.ExportSummaryList      = new ListPage('ExportSummaryList','dynamic_panel',      Common.EntityMap.ExportSummary,      false, false, false, true, false);

        Common.StorageList.filterable = false;
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
        return "entity_query\n" +
               "" + entity + "\n" +
               "get_entity_list\n";
    }
    static AddEntity(entity) {
        return "entity_query\n" +
               "" + entity + "\n" +
               "add_entity\n";
    }
    static DeleteEntity(entity) {
        return "entity_query\n" +
               "" + entity + "\n" +
               "delete_entity\n";
    }
    static EditEntity(entity) {
        return "entity_query\n" +
               "" + entity + "\n" +
               "edit_entity\n";
    }
}
class EntityFilters {
    static undefined_value = Object.freeze('____undefined____');

    static getEmptyFilter(entity) {
        switch (entity) {
            case Common.EntityMap.Provider :
            case Common.EntityMap.Customer :
                return {
                    id          : this.undefined_value,
                    name        : this.undefined_value,
                    country     : this.undefined_value,
                    description : this.undefined_value,
                };
            case Common.EntityMap.Goods :
                return {
                    id            : this.undefined_value,
                    name          : this.undefined_value,
                    average_price : this.undefined_value,
                    description   : this.undefined_value,
                };
            case Common.EntityMap.Storage :
                return {
                    id          : this.undefined_value,
                    name        : this.undefined_value,
                    description : this.undefined_value,
                };
            case Common.EntityMap.ImportDocument :
                return {
                    id          : this.undefined_value,
                    provider_id : this.undefined_value,
                    import_date : this.undefined_value,
                    description : this.undefined_value,
                };
            case Common.EntityMap.ExportDocument :
                return {
                    id          : this.undefined_value,
                    customer_id : this.undefined_value,
                    export_date : this.undefined_value,
                    description : this.undefined_value,
                };
            case Common.EntityMap.ImportGoods :
            case Common.EntityMap.ExportGoods :
                return {
                    id          : this.undefined_value,
                    document_id : this.undefined_value,
                    goods_id    : this.undefined_value,
                    goods_count : this.undefined_value,
                    goods_price : this.undefined_value,
                };
            case Common.EntityMap.ImportMoveDocument :
                return {
                    id             : this.undefined_value,
                    importGoods_id : this.undefined_value,
                    storage_id     : this.undefined_value,
                };
            case Common.EntityMap.ExportMoveDocument :
                return {
                    id             : this.undefined_value,
                    exportGoods_id : this.undefined_value,
                    storage_id     : this.undefined_value,
                };
            case Common.EntityMap.ImportSummary :
                return {
                    id             : this.undefined_value,
                    start_date     : this.undefined_value,
                    end_date       : this.undefined_value,
                    imports_count  : this.undefined_value,
                    imports_amount : this.undefined_value,
                    max_price      : this.undefined_value,
                    min_price      : this.undefined_value,
                };
            case Common.EntityMap.ExportSummary :
                return {
                    id             : this.undefined_value,
                    start_date     : this.undefined_value,
                    end_date       : this.undefined_value,
                    exports_count  : this.undefined_value,
                    exports_amount : this.undefined_value,
                    max_price      : this.undefined_value,
                    min_price      : this.undefined_value,
                };
            case Common.EntityMap.AvailableGoods :
                return {
                    id            : this.undefined_value,
                    goods_id      : this.undefined_value,
                    goods_count   : this.undefined_value,
                    provider_id   : this.undefined_value,
                    storage_id    : this.undefined_value,
                    current       : true,
                    snapshot_date : this.undefined_value,
                };

            default : {
                alert("Unknown entity \'" + entity + "\' at getEmptyFilter");
                return undefined;
            }
        }
    }
    static getChildConnectedFilter(entity, id) {
        switch (entity) {
            case Common.EntityMap.ImportDocument :
                return {
                    id          : this.undefined_value,
                    provider_id : id,
                    import_date : this.undefined_value,
                    description : this.undefined_value,
                };
            case Common.EntityMap.ExportDocument :
                return {
                    id          : this.undefined_value,
                    customer_id : id,
                    export_date : this.undefined_value,
                    description : this.undefined_value,
                };
            case Common.EntityMap.ImportGoods :
            case Common.EntityMap.ExportGoods :
                return {
                    id          : this.undefined_value,
                    document_id : id,
                    goods_id    : this.undefined_value,
                    goods_count : this.undefined_value,
                    goods_price : this.undefined_value,
                };
            case Common.EntityMap.ImportMoveDocument :
                return {
                    id             : this.undefined_value,
                    importGoods_id : id,
                    storage_id     : this.undefined_value,
                };
            case Common.EntityMap.ExportMoveDocument :
                return {
                    id             : this.undefined_value,
                    exportGoods_id : id,
                    storage_id     : this.undefined_value,
                };
            default: return this.undefined_value;
        }
    }
    static getChildEntity(entity) {
        switch (entity) {
            case Common.EntityMap.ImportDocument :
                return Common.EntityMap.ImportGoods;
            case Common.EntityMap.ExportDocument :
                return Common.EntityMap.ExportGoods;
            case Common.EntityMap.ImportGoods :
                return Common.EntityMap.ImportMoveDocument;
            case Common.EntityMap.ExportGoods :
                return Common.EntityMap.ExportMoveDocument;
            default: return this.undefined_value;
        }
    }
    static getListEntity(entity) {
        switch (entity) {
            case Common.EntityMap.Provider :
                return Common.ProviderList;
            case Common.EntityMap.Customer :
                return Common.CustomerList;
            case Common.EntityMap.Goods :
                return Common.GoodsList;
            case Common.EntityMap.Storage :
                return;
            case Common.EntityMap.ImportDocument :
                return Common.ImportsList;
            case Common.EntityMap.ExportDocument :
                return Common.ExportsList;
            case Common.EntityMap.ImportGoods :
                return Common.ImportGoodsList;
            case Common.EntityMap.ExportGoods :
                return Common.ExportGoodsList;
            case Common.EntityMap.ImportMoveDocument :
                return Common.ImportMoveDocumentList;
            case Common.EntityMap.ExportMoveDocument :
                return Common.ExportMoveDocumentList;
            case Common.EntityMap.ImportSummary :
                return Common.ImportSummaryList;
            case Common.EntityMap.ExportSummary :
                return Common.ExportSummaryList;
            case Common.EntityMap.AvailableGoods :
                return Common.AvailableList;
        }
    }

    static getEntityLogicProperties(entity) {
        switch (entity) {
            case Common.EntityMap.Provider :
            case Common.EntityMap.Customer :
                return ['id', 'name', 'country', 'description'];
            case Common.EntityMap.Goods :
                return ['id', 'name', 'average_price', 'description'];
            case Common.EntityMap.Storage :
                return ['id', 'name', 'description'];
            case Common.EntityMap.ImportDocument :
                return ['id', 'provider_id', 'import_date', 'description'];
            case Common.EntityMap.ExportDocument :
                return ['id', 'customer_id', 'export_date', 'description'];
            case Common.EntityMap.ImportGoods :
            case Common.EntityMap.ExportGoods :
                return ['id', 'document_id', 'goods_id', 'goods_count', 'goods_price'];
            case Common.EntityMap.ImportMoveDocument :
                return ['id', 'importGoods_id', 'storage_id'];
            case Common.EntityMap.ExportMoveDocument :
                return ['id', 'exportGoods_id', 'storage_id'];
            case Common.EntityMap.ImportSummary :
                return ['id', 'start_date', 'end_date', 'imports_count', 'imports_amount', 'max_price', 'min_price'];
            case Common.EntityMap.ExportSummary :
                return ['id', 'start_date', 'end_date', 'exports_count', 'exports_amount', 'max_price', 'min_price'];
            case Common.EntityMap.AvailableGoods :
                return ['id', 'goods_id', 'goods_count', 'provider_id', 'storage_id'];
            default: return Object.keys(EntityFilters.getEmptyFilter(entity));
        }
    }
    static getEntityViewProperties(entity) {
        switch (entity) {
            case Common.EntityMap.Provider :
            case Common.EntityMap.Customer :
                return ['ID', 'Name', 'Country', 'Description'];
            case Common.EntityMap.Goods :
                return ['ID', 'Name', 'Average price', 'Description'];
            case Common.EntityMap.Storage :
                return ['ID', 'Name', 'Description'];
            case Common.EntityMap.ImportDocument :
                return ['ID', 'Provider ID', 'Import Date', 'Description'];
            case Common.EntityMap.ExportDocument :
                return ['ID', 'Customer ID', 'Export Date', 'Description'];
            case Common.EntityMap.ImportGoods :
            case Common.EntityMap.ExportGoods :
                return ['ID', 'Document ID', 'Goods ID', 'Goods count', 'Goods price'];
            case Common.EntityMap.ImportMoveDocument :
                return ['ID', 'Import Goods ID', 'Storage ID'];
            case Common.EntityMap.ExportMoveDocument :
                return ['ID', 'Export Goods ID', 'Storage ID'];
            case Common.EntityMap.ImportSummary :
                return ['ID', 'Start Date', 'End Date', 'Imports count', 'Imports amount', 'Max price', 'Min price'];
            case Common.EntityMap.ExportSummary :
                return ['ID', 'Start Date', 'End Date', 'Exports count', 'Exports amount', 'Max price', 'Min price'];
            case Common.EntityMap.AvailableGoods :
                return ['ID', 'Goods ID', 'Goods Count', 'Provider ID', 'Storage ID'];
            default: return Object.keys(EntityFilters.getEmptyFilter(entity));
        }
    }
    static getRepresentDataField(entity) {
        switch (entity) {
            case Common.EntityMap.Provider :
            case Common.EntityMap.Customer :
            case Common.EntityMap.Goods :
            case Common.EntityMap.Storage :
                return 'name';
            default: return 'id';
        }
    }
}
class ListPage {
    static base_list_size = Object.freeze(5);
    static list_size = Object.freeze(5);
    listpage_name;
    dynamic_panel_name;
    show_title;

    current_entity;
    list_begin_ind;
    table_data;
    limited;

    filterable;
    filter;

    in_pocket;
    pocket = [];

    choosed_item = null;

    actionable;
    editable;
    deletable;
    pocketable;
    getinfoable;
    choosable;

    frozen = false;
    is_import = false;
    is_export = false;

    constructor(listpage_name, dynamic_table, entity, editable, deletable, pocketable, getinfoable, choosable) {
        if (!Common.EntityArray.includes(entity)) {
            alert("Entity  \'" + entity + "\' is not defined.");
            return;
        }
        this.listpage_name = listpage_name;
        this.dynamic_panel_name = dynamic_table;
        this.show_title = true;

        this.current_entity = entity;
        this.list_begin_ind = 0;
        this.table_data = [];
        this.limited = true;

        this.filterable = true;
        this.filter = EntityFilters.getEmptyFilter(entity);

        this.in_pocket = false;
        this.pocket = [];

        this.actionable = editable || deletable || pocketable || getinfoable || choosable;
        this.editable = editable;
        this.deletable = deletable;
        this.pocketable = pocketable;
        this.getinfoable = getinfoable;
        this.choosable = choosable;
    }

    BuildList() {
        this.TableBuildFacad();
        this.filter = EntityFilters.getEmptyFilter(this.current_entity);
        this.TableLoadAndBuildByFilter();
    }
    TableBuildFacad() {
        document.getElementById(this.dynamic_panel_name).innerHTML = TemplateHandler.Render('list_template', {});
        let data = {
            entity_uppercase: Common.capitalizeFirstLetter(this.current_entity),
            entity_lowercase: this.current_entity,
        };
        if (this.show_title)
            document.getElementById('list_title').innerHTML = TemplateHandler.Render('list_title_template', data);

        if (this.filterable) {
            document.getElementById('filter').innerHTML = TemplateHandler.Render('filter_template', { listpage : this.listpage_name });
            let ul = document.getElementById(this.dynamic_panel_name).getElementsByTagName('ul')[0];
            let properties = EntityFilters.getEntityLogicProperties(this.current_entity);
            let properties_view = EntityFilters.getEntityViewProperties(this.current_entity);
            for (let i = 0; i < properties.length; i++) {
                let sub_data = {
                    property_uppercase : properties_view[i],
                    property_lowercase : properties[i],
                };
                ul.insertAdjacentHTML('beforeend', TemplateHandler.Render('filter_input', sub_data));
            }
        }
        if (this.choosable) {
            this.choosed_item = null;
            document.getElementById('choosed').innerHTML = TemplateHandler.Render('choosed_template', { id : 'none' });
        }
        if (this.is_import){
            document.getElementById('import_bottom').innerHTML = TemplateHandler.Render('import_action_bottom', {});
        }
        if (this.is_export){
            document.getElementById('export_bottom').innerHTML = TemplateHandler.Render('export_action_bottom', {});
        }
    }
    TableLoadAndBuildByFilter() {
        this.TableLoad(function(data, obj) {
            obj.table_data = obj.table_data.concat(data);
            obj.TableFill(obj.table_data, true);
            obj.TableRefresh();
        });
    }

    TableLoad(callback) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = this.TableLoadCallback(callback, http, this);
        let query_body =
            QueryMaker.GetEntityList(this.current_entity) +
            JSON.stringify(this.filter) + '\n' +
            String(this.limited) + "\n" +
            String(this.list_begin_ind) + "\n" +
            String(ListPage.list_size) + "\n";
        http.send(query_body);
    }
    TableLoadCallback(callback, http, obj) {
        return function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                let new_data = JSON.parse(http.responseText);
                callback(new_data, obj);
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The Load request finished not successful, some trouble happened with the request.");
            }
        }
    }

    TableFill(data, external) {
        let table_place =  document.getElementById('table_place');
        table_place.innerHTML = TemplateHandler.Render('list_table', {});
        if (external) {
            table_place.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_table_extend_buttons', { extend_size : ListPage.list_size, listpage : this.listpage_name }));
            if (this.pocketable)
                table_place.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_table_show_pocket', { listpage : this.listpage_name }));
        } else {
            if (!this.frozen)
                table_place.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_table_hide_pocket', { listpage : this.listpage_name }));
        }

        let list_header = table_place.getElementsByTagName('tr')[0];
        let list_footer = table_place.getElementsByTagName('tr')[1];
        let properties = EntityFilters.getEntityLogicProperties(this.current_entity);
        let properties_view = EntityFilters.getEntityViewProperties(this.current_entity);
        for (let i = 0; i < properties_view.length; i++) {
            let sub_data = {
                property_uppercase : Common.capitalizeFirstLetter(properties_view[i]),
            };
            list_header.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_header_title', sub_data));
            list_footer.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_footer_title', sub_data));
        }
        if (this.actionable) {
            let sub_data = {
                property_uppercase : 'Action',
            };
            list_header.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_header_title', sub_data));
            list_footer.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_footer_title', sub_data));
        }
        if (this.frozen) {
            let sub_data = {
                property_uppercase : 'Count of items',
            };
            list_header.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_header_title', sub_data));
            list_footer.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_footer_title', sub_data));
        }
        if (this.is_import) {
            let sub_data = {
                property_uppercase : 'Goods Price',
            };
            list_header.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_header_title', sub_data));
            list_footer.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_footer_title', sub_data));
            let sub_data1 = {
                property_uppercase : 'Storage ID',
            };
            list_header.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_header_title', sub_data1));
            list_footer.insertAdjacentHTML('beforeend', TemplateHandler.Render('list_footer_title', sub_data1));
        }

        let table_body = table_place.getElementsByTagName('tbody')[0];

        for (let i = 0; i < data.length; i++) {
            let record = document.createElement('tr');
            for (let j = 0; j < properties.length; j++) {
                let row_data = {
                    value : (data[i][properties[j]] === EntityFilters.undefined_value ? '' : data[i][properties[j]] ),
                };
                record.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_field', row_data));
            }
            if (this.actionable) {
                let action_buttons = document.createElement('td');

                if (this.editable)
                    action_buttons.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_button_edit',{ id : data[i].id, listpage : this.listpage_name} ));
                if (this.deletable)
                    action_buttons.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_button_delete',{ id : data[i].id, listpage : this.listpage_name} ));
                if (this.pocketable)
                    action_buttons.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_button_pocket',{ id : data[i].id, listpage : this.listpage_name} ));
                if (this.getinfoable)
                    action_buttons.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_button_get_info',{ id : data[i].id, listpage : this.listpage_name} ));
                if (this.choosable)
                    action_buttons.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_button_choose',{ id : data[i].id, listpage : this.listpage_name} ));

                record.insertAdjacentElement('beforeend', action_buttons);
            }
            if (this.frozen) {
                if (this.is_import) {
                    let action_buttons = document.createElement('td');
                    let action_buttons2 = document.createElement('td');
                    let action_buttons3 = document.createElement('td');
                    action_buttons.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_count_input_import',{ id : data[i].id} ));
                    action_buttons2.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_storage_id',{ id : data[i].id} ));
                    action_buttons3.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_price_input_import',{ id : data[i].id} ));
                    record.insertAdjacentElement('beforeend', action_buttons);
                    record.insertAdjacentElement('beforeend', action_buttons2);
                    record.insertAdjacentElement('beforeend', action_buttons3);
                }
                if (this.is_export) {
                    let action_buttons = document.createElement('td');
                    action_buttons.insertAdjacentHTML('beforeend', TemplateHandler.Render('datatable_row_count_input_export',{ id : data[i].id, max_value : data[i]['goods_count']} ));
                    record.insertAdjacentElement('beforeend', action_buttons);
                }
            }
            table_body.insertAdjacentElement('beforeend', record);
        }

        $('#dt_Table').DataTable({
            "retrieve": true,
            "scrollY": "50vh",
            "scrollCollapse": true,
        });
        $('.dataTables_length').addClass('bs-select');
    }
    TableClear() {
        this.table_data = [];
        let table_body = document.getElementById('table_place').getElementsByTagName('tbody')[0];
        while (table_body.firstChild) {
            table_body.removeChild(table_body.firstChild);
        }
    }
    TableRefresh() {
        let prev_ind = this.list_begin_ind;

        ListPage.list_size += this.list_begin_ind;
        this.list_begin_ind = 0;

        this.TableClear();

        this.TableLoad(function(data, obj) {
            obj.list_begin_ind = prev_ind;
            ListPage.list_size = ListPage.base_list_size;
            obj.table_data = obj.table_data.concat(data);
            obj.TableFill(obj.table_data, true);
        });
    }
    TableAddRowMenu() {
        let data = {
            entity_lowercase : this.current_entity,
        };
        document.getElementById(this.dynamic_panel_name).innerHTML = TemplateHandler.Render('add_template', data);
        let panel = document.getElementsByClassName('add_panel')[0];
        let properties = EntityFilters.getEntityLogicProperties(this.current_entity);
        let properties_view = EntityFilters.getEntityViewProperties(this.current_entity);
        for (let i = 0; i < properties.length; i++) {
            if (properties[i] === 'id') continue;
            let sub_data = {
                property_uppercase : properties_view[i],
                property_lowercase : properties[i],
            };
            panel.insertAdjacentHTML('beforeend', TemplateHandler.Render('add_template_field', sub_data));
        }
        panel.insertAdjacentHTML('beforeend', TemplateHandler.Render('add_template_button', { listpage : this.listpage_name }));
    }

    TableEditRowMenu(id) {
        if (!this.editable) {
            alert("The field is not editable");
            return;
        }
        let sure = confirm("Are you sure want to edit the record with ID = " + id +"?");
        if (!sure) return;

        this.filter = EntityFilters.getEmptyFilter(this.current_entity);
        this.filter.id = id;

        this.TableLoad(this.TableEditRowMenuCallback(this.current_entity, this.listpage_name));
    }
    TableEditRowMenuCallback(cur_entity, listpage_name) {
        return function (loaded_data, obj) {
            let data = {
                entity_lowercase : cur_entity,
            };
            document.getElementById(obj.dynamic_panel_name).innerHTML = TemplateHandler.Render('edit_template', data);
            let panel = document.getElementsByClassName('edit_panel')[0];
            let properties = EntityFilters.getEntityLogicProperties(obj.current_entity);
            let properties_view = EntityFilters.getEntityViewProperties(obj.current_entity);
            for (let i = 0; i < properties.length; i++) {
                if (properties[i] === 'id') continue;
                let sub_data = {
                    property_uppercase : properties_view[i],
                    property_lowercase : properties[i],
                    property_value : (loaded_data[0][properties[i]] === EntityFilters.undefined_value ? '' : loaded_data[0][properties[i]] ),
                };
                panel.insertAdjacentHTML('beforeend', TemplateHandler.Render('edit_template_field', sub_data));
            }
            panel.insertAdjacentHTML('beforeend', TemplateHandler.Render('edit_template_button', { id : loaded_data[0].id, listpage : listpage_name }));
        }
    }

    TableDeleteRow(id) {
        if (!this.editable) {
            alert("The field is not editable");
            return;
        }
        let sure = confirm("Are you sure want to delete the record with ID = " + id +"?");
        if (sure) {
            let http = new XMLHttpRequest();
            http.open('POST', window.location.href, true);
            http.onreadystatechange = this.TableDeleteRowCallback(http, this);
            let query_body =
                QueryMaker.DeleteEntity(this.current_entity) +
                id + "\n";
            http.send(query_body);
        }
    }
    TableDeleteRowCallback(http, obj) {
        return function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok") {
                    alert("The " + obj.current_entity + " are deleted successfully");
                    obj.TableRefresh();
                }
                else
                    alert("The " + obj.current_entity + " was not deleted, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The " + obj.current_entity + " was not deleted, some trouble happened with the request.");
            }
        }
    }

    TableExtendList(limited) {
        this.limited = limited;
        this.list_begin_ind += ListPage.list_size;
        this.TableLoad(function(data, obj) {
            if (!limited) {
                obj.TableClear();
                obj.list_begin_ind = data.length;
            }
            obj.table_data = obj.table_data.concat(data);
            obj.TableFill(obj.table_data, true);
            obj.TableRefresh();
        });
    }
    TableSetFilter() {
        this.filter = EntityFilters.getEmptyFilter(this.current_entity);
        let properties = EntityFilters.getEntityLogicProperties(this.current_entity);
        let panel = $('#filter');
        for (let i = 0; i < properties.length; i++) {
            let input = panel.find('input[name=\'filter_' + properties[i] + '\']').val();
            this.filter[properties[i]] = (input === '' ? EntityFilters.undefined_value : input);
        }
        this.list_begin_ind = 0;
        this.TableLoadAndBuildByFilter();
    }

    TableAddSend() {
        let new_entry = EntityFilters.getEmptyFilter(this.current_entity);
        let panel = $('.add_panel');

        let ok = true;
        let properties = EntityFilters.getEntityLogicProperties(this.current_entity);
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
        http.onreadystatechange = this.TableAddCallback(http, this);
        let query_body =
            QueryMaker.AddEntity(this.current_entity) +
            JSON.stringify(new_entry) + '\n';
        http.send(query_body);
    }
    TableAddCallback(http, obj) {
        return function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("The " + obj.current_entity + " is added successfully");
                }
                else
                    alert("The " + obj.current_entity + " was not added, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The " + obj.current_entity + " was not added, some trouble happened with the request.");
            }
        }
    }

    TableEditSend() {
        let new_entry = EntityFilters.getEmptyFilter(this.current_entity);
        let panel = $('.edit_panel');

        let ok = false;
        let properties = EntityFilters.getEntityLogicProperties(this.current_entity);
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
        http.onreadystatechange = this.TableEditCallback(http, this);
        let query_body =
            QueryMaker.EditEntity(this.current_entity) +
            JSON.stringify(new_entry) + '\n';
        http.send(query_body);
    }
    TableEditCallback(http, obj) {
        return function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("The " + obj.current_entity + " is edited successfully");
                }
                else
                    alert("The " + obj.current_entity + " was not edited, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The " + obj.current_entity + " was not edited, some trouble happened with the request.");
            }
        }
    }

    TableAddToPocket(id) {
        let item = this.table_data.find(x => x['id'] === id);
        let item_in_pocket = this.pocket.find(x => x['id'] === id);
        if (item_in_pocket === undefined)
            this.pocket[this.pocket.length] = item;
        else {
            for (let i = 0; i < this.pocket.length; i++){
                if (this.pocket[i]['id'] === id) {
                    this.pocket.splice(i, 1);
                    break;
                }
            }
        }
        if (this.in_pocket)
            this.ShowPocket();
    }
    ShowPocket() {
        this.in_pocket = true;
        this.TableFill(this.pocket, false);
    }
    HidePocket() {
        this.in_pocket = false;
        this.TableFill(this.table_data, true);
        this.TableRefresh();
    }

    TableGetElementInfo(id) {
        if (this.current_entity === Common.EntityMap.ImportSummary) {
            this.ImportSummaryGetInfo(id);
        } else if (this.current_entity === Common.EntityMap.ExportSummary) {
            this.ExportSummaryGetInfo(id);
        } else {
            let child_entity = EntityFilters.getChildEntity(this.current_entity);
            if (child_entity === EntityFilters.undefined_value) return;
            let child_list = EntityFilters.getListEntity(child_entity);
            child_list.TableBuildFacad();
            child_list.filter = EntityFilters.getChildConnectedFilter(child_entity, id);
            child_list.TableLoadAndBuildByFilter();
        }
    }

    FreezeTable() {
        this.frozen = true;
        this.filterable = false;
        this.pocketable = false;
        this.actionable = false;
    }
    UnfreezeTable() {
        this.frozen = false;
        this.filterable = true;
        this.pocketable = true;
        this.actionable = true;
    }

    TableChooseItem(id) {
        this.choosed_item = this.table_data.find(x => x['id'] === id);
        document.getElementById('choosed').innerHTML = TemplateHandler.Render('choosed_template',
            { id : this.choosed_item[EntityFilters.getRepresentDataField(this.current_entity)] });
    }

    //Special
    ImportSummaryGetInfo(id) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = this.ImportSummaryGetInfoCallback(http, this);
        let query_body =
            "get_report_available\n" +
            "get_import_report_available\n" +
            id + "\n";
        http.send(query_body);
    }
    ImportSummaryGetInfoCallback(http, obj) {
        return function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                alert("ok");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The " + obj.current_entity + " is not loaded, some trouble happened with the request.");
            }
        }
    }
    ExportSummaryGetInfo(id) {
        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = this.ExportSummaryGetInfoCallback(http, this);
        let query_body =
            "get_report_available\n" +
            "get_export_report_available\n" +
            id + "\n";
        http.send(query_body);
    }
    ExportSummaryGetInfoCallback(http, obj) {
        return function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                alert("ok");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The " + obj.current_entity + " is not loaded, some trouble happened with the request.");
            }
        }
    }
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
            case '#report_list_import': { InterfaceHashHandler.ImportReportList(); } break;
            case '#report_list_export': { InterfaceHashHandler.ExportReportList(); } break;
            case '#report_make'       : { InterfaceHashHandler.ReportMake();       } break;

            case '#system'            : { InterfaceHashHandler.System();           } break;
            case '#rebuild_available' : { InterfaceHashHandler.RebuildAvailable(); } break;
            case '#rebuild_reports'   : { InterfaceHashHandler.RebuildReports();   } break;
            case '#forbidden'         : { InterfaceHashHandler.ForbiddenPage();    } break;

            default: alert("Unknown hash-tag!");
        }
    }
}
class InterfaceHashHandler {

    static DefineUser() {
        document.location.hash = '';

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                Common.role = http.responseText;
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
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The role is not defined, some trouble happened with the request.");
            }
        };
        http.send('get_role');
    }
    static CheckPermission(privileged_users) {
        if (privileged_users.includes(Common.role))
            return false;
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
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('provider_template', {});
    }
    static ProviderList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager])) return;
        Common.ProviderList.BuildList();
    }
    static ProviderAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        Common.ProviderList.TableAddRowMenu();
    }

    static Customer() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('customer_template', {});
    }
    static CustomerList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        Common.CustomerList.BuildList();
    }
    static CustomerAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ExportManager])) return;
        Common.CustomerList.TableAddRowMenu();
    }

    static Goods() {
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('goods_template', {});
    }
    static GoodsList() {
        Common.GoodsList.is_import = false;
        Common.GoodsList.dynamic_panel_name = 'dynamic_panel';
        Common.GoodsList.show_title = true;
        Common.GoodsList.BuildList();
    }
    static GoodsAdd() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        Common.GoodsList.TableAddRowMenu();
    }

    static Storage() {
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('storage_template', {});
    }
    static StorageAvailable() {
        Common.AvailableList.is_export = false;
        Common.AvailableList.dynamic_panel_name = 'dynamic_panel';
        Common.AvailableList.show_title = true;
        Common.AvailableList.BuildList();
    }
    static StorageInfo() {
        Common.StorageList.BuildList();
    }

    static Imports() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('imports_template', {});
    }
    static ImportsAction() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ImportManager])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('import_action_template', {});
        if (Common.GoodsList.pocket.length === 0 || Common.ProviderList.choosed_item === null) {
            document.getElementById('import_content').innerHTML = TemplateHandler.Render('import_action_not_ready', {});
        } else {
            Common.GoodsList.is_import = true;
            Common.GoodsList.dynamic_panel_name = 'import_content';
            Common.GoodsList.show_title = false;
            Common.GoodsList.FreezeTable();
            Common.GoodsList.TableBuildFacad();
            Common.GoodsList.ShowPocket();
            document.getElementById('provider_title').innerHTML = TemplateHandler.Render('import_provider_template',
                { provider : Common.ProviderList.choosed_item[EntityFilters.getRepresentDataField(Common.EntityMap.Provider)]});
            Common.GoodsList.UnfreezeTable();
        }
    }
    static ImportsList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ImportManager])) return;
        Common.ImportsList.BuildList();
    }

    static Exports() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('exports_template', {});
    }
    static ExportsAction() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ExportManager])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('export_action_template', {});
        if (Common.AvailableList.pocket.length === 0 || Common.CustomerList.choosed_item === null) {
            document.getElementById('export_content').innerHTML = TemplateHandler.Render('export_action_not_ready', {});
        } else {
            Common.AvailableList.is_export = true;
            Common.AvailableList.dynamic_panel_name = 'export_content';
            Common.AvailableList.show_title = false;
            Common.AvailableList.FreezeTable();
            Common.AvailableList.TableBuildFacad();
            Common.AvailableList.ShowPocket();
            document.getElementById('customer_title').innerHTML = TemplateHandler.Render('export_costumer_template',
                { customer : Common.CustomerList.choosed_item[EntityFilters.getRepresentDataField(Common.EntityMap.Provider)]});
            Common.AvailableList.UnfreezeTable();
        }
    }
    static ExportsList() {
        if (this.CheckPermission([Common.roles.Admin, Common.roles.ViewManager, Common.roles.ExportManager])) return;
        Common.ExportsList.BuildList();
    }

    static Reports() {
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('reports_template', {});
    }
    static ImportReportList() {
        Common.ImportSummaryList.BuildList();
    }
    static ExportReportList() {
        Common.ExportSummaryList.BuildList();
    }
    static ReportMake() {
        const data = {
        };
        const dynamic_panel = document.getElementById('dynamic_panel');
        dynamic_panel.innerHTML = TemplateHandler.Render('report_make_template', data);
    }

    static System() {
        if (this.CheckPermission( [Common.roles.Admin])) return;
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('system_template', {});
    }
    static RebuildAvailable() {
        if (confirm("Are you sure? It can take a lot of time")) {
            let http = new XMLHttpRequest();
            http.open('POST', window.location.href, true);
            http.onreadystatechange = function() {
                if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                    alert("Done");
                } else if (http.readyState === XMLHttpRequest.DONE) {
                    alert("The Rebuild request finished not successful, some trouble happened with the request.");
                }
            };
            let query_body = "db_rebuild_database\n";
            http.send(query_body);
        }
        window.location.hash = 'system';
    }
    static RebuildReports() {
        if (confirm("Are you sure? It can take a lot of time")) {
            let http = new XMLHttpRequest();
            http.open('POST', window.location.href, true);
            http.onreadystatechange = function() {
                if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                    alert("Done");
                } else if (http.readyState === XMLHttpRequest.DONE) {
                    alert("The Rebuild request finished not successful, some trouble happened with the request.");
                }
            };
            let query_body = "db_rebuild_reports\n";
            http.send(query_body);
        }
        window.location.hash = 'system';
    }

    static ForbiddenPage() {
        document.getElementById('dynamic_panel').innerHTML = TemplateHandler.Render('forbidden_template', {});
    }

    static MakeImport() {
        let provider_id = Common.ProviderList.choosed_item['id'];
        let goods_ids = [];
        let goods_counts = [];
        let storage_ids = [];
        let goods_prices = [];
        let description = document.getElementById('import_description').value;
        for (let i = 0; i < Common.GoodsList.pocket.length; i++) {
            goods_ids[goods_ids.length] = Common.GoodsList.pocket[i]['id'];
            goods_counts[goods_counts.length] = document.getElementById('count_id_'+goods_ids[i] ).value;
            storage_ids[storage_ids.length] = document.getElementById('storage_id_'+goods_ids[i] ).value;
            goods_prices[goods_prices.length] = document.getElementById('price_id_'+goods_ids[i] ).value;
        }

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("The import is added successfully");
                    Common.GoodsList.pocket = [];
                    Common.ProviderList.choosed_item = null;
                    document.location.hash = '#';
                }
                else
                    alert("The import was not added, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The import was not added, some trouble happened with the request.");
            }
        };
        let query_body =
            "import\n" +
            provider_id + "\n" +
            goods_ids.length + "\n";
        for (let i = 0; i < goods_ids.length; i++) {
            query_body += goods_ids[i] + "\n";
            query_body += goods_counts[i] + "\n";
            query_body += storage_ids[i] + "\n";
            query_body += goods_prices[i] + "\n";
        }
        query_body += description + "\n";
        http.send(query_body);
    }
    static MakeExport() {
        let customer_id = Common.CustomerList.choosed_item['id'];
        let availble_ids = [];
        let goods_counts = [];
        let description = document.getElementById('export_description').value;
        for (let i = 0; i < Common.AvailableList.pocket.length; i++) {
            availble_ids[availble_ids.length] = Common.AvailableList.pocket[i]['id'];
            goods_counts[goods_counts.length] = document.getElementById('count_id_'+availble_ids[i] ).value;
        }

        let http = new XMLHttpRequest();
        http.open('POST', window.location.href, true);
        http.onreadystatechange = function () {
            if(http.readyState === XMLHttpRequest.DONE && http.status === 200) {
                if (http.responseText === "ok"){
                    alert("The export is added successfully");
                    Common.AvailableList.pocket = [];
                    Common.CustomerList.choosed_item = null;
                    document.location.hash = '#';
                }
                else
                    alert("The export was not added, some trouble happened on the server side.");
            } else if (http.readyState === XMLHttpRequest.DONE) {
                alert("The export was not added, some trouble happened with the request.");
            }
        };
        let query_body =
            "export\n" +
            customer_id + "\n" +
            availble_ids.length + "\n";
        for (let i = 0; i < availble_ids.length; i++) {
            query_body += availble_ids[i] + "\n";
            query_body += goods_counts[i] + "\n";
        }
        query_body += description + "\n";
        http.send(query_body);
    }
}

(() => {
    Common.initialize();
    Router.initialize();
    InterfaceHashHandler.DefineUser();
})();