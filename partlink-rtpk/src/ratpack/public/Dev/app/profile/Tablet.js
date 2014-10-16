Ext.define('App.profile.Tablet', {
    extend: 'Ext.app.Profile',
    requires: ['App.controller.tablet.Main','App.view.tablet.Main'],
    config: {
        name: 'Tablet',
        controllers:['Main'],
        views : ['Main']
    },

    isActive: function() {
//        return false;  // remove

        return Ext.os.is('Tablet') || Ext.os.is('Desktop');
    },

    launch: function() {
        Ext.create('App.view.tablet.Main');
    }
});