Ext.define('App.view.Main', {
    extend : 'Ext.Container',
    xtype  : 'main',
    //requires : [ 'App.store.Contacts', 'App.view.ContactsList', 'App.view.ContactDetails' ],

    config : {
        fullscreen : true,

        items : {
            docked : 'top',
            xtype  : 'toolbar',
            title  : 'Contacts'
        }
    },

    setTitle : function(title) {
        this.down('toolbar').setTitle(title);
    }
});