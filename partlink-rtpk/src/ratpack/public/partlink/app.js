Ext.application({
    name: 'partlink',

//    requires: [
//        'Ext.MessageBox'
//    ],
//
//    views: [
//        'Main'
//    ],
//
//    icon: {
//        '57': 'resources/icons/Icon.png',
//        '72': 'resources/icons/Icon~ipad.png',
//        '114': 'resources/icons/Icon@2x.png',
//        '144': 'resources/icons/Icon~ipad@2x.png'
//    },
//
//    isIconPrecomposed: true,
//
//    startupImage: {
//        '320x460': 'resources/startup/320x460.jpg',
//        '640x920': 'resources/startup/640x920.png',
//        '768x1004': 'resources/startup/768x1004.png',
//        '748x1024': 'resources/startup/748x1024.png',
//        '1536x2008': 'resources/startup/1536x2008.png',
//        '1496x2048': 'resources/startup/1496x2048.png'
//    },

    // launch: function() {
    //     // Destroy the #appLoadingIndicator element
    //     Ext.fly('appLoadingIndicator').destroy();

    //     // Initialize the main view
    //     Ext.Viewport.add(Ext.create('partlink.view.Main'));
    // },
    
    launch: function () {
        Ext.define('ListItem', {
            extend: 'Ext.data.Model',
            config: {
                fields: ['text']
            }
        });

        var treeStore = Ext.create('Ext.data.TreeStore', {
            autoLoad: true,
            model: 'ListItem',
           defaultRootProperty: 'items',
            proxy: {
                type: 'ajax',
                //url: '/lookup/client/016033650,015127231,010077987,015303893,015208009,001186249,010127380,010767912,015118286,012746352',
                url: '/lookup/client/016033650',
                //url: '/mock1',
                reader: {
                    type: 'json'
                } 
            }
        });

        Ext.create('Ext.NestedList', {
            fullscreen: true,
            store: treeStore
        });
    },

    onUpdated: function() {
        Ext.Msg.confirm(
            "Application Update",
            "This application has just successfully been updated to the latest version. Reload now?",
            function(buttonId) {
                if (buttonId === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
