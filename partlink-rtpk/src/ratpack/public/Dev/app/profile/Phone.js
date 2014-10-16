Ext.define('App.profile.Phone', {
    extend: 'Ext.app.Profile',
    requires: ['App.controller.phone.Main','App.view.phone.Main'],


    config: {
        name: 'Phone',
        controllers:['Main'],
        views : ['Main']

    },

    isActive: function() {
        //return true;
        return Ext.os.is('Phone');
    },

    launch: function() {
        Ext.create('App.view.phone.Main');
    }
});