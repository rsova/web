Ext.define('App.store.Contacts', {
    extend : 'Ext.data.Store',
    alias : 'store.contacts',
    requires : ['App.model.Contact','Ext.data.proxy.Ajax' , 'Ext.data.reader.Json'],

    config : {
        autoLoad: true,
        model : 'App.model.Contact',
        proxy: {
            type: 'ajax',
            url: '/contacts',
            reader: {
                type: 'json'
                //rootProperty: 'data'
            },
            //  writer: {
            //      type: 'json'
            // }
        }
    }
});