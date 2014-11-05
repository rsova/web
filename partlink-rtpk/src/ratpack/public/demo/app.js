Ext.application({
    name: 'partlink',

    
    launch: function () {
        Ext.define('ListItem', {
            extend: 'Ext.data.Model',
            config: {
                fields: ['text','url','niins','destination']
            }
        });

        var treeStore = Ext.create('Ext.data.TreeStore', {
           autoLoad: true,
           model: 'ListItem',
           defaultRootProperty: 'items',
            proxy: {
                type: 'ajax',
                url: '/work/assemblages',
                reader: {
                    type: 'json'
                } 
            }
        });
        Ext.create('Ext.NestedList', {
            fullscreen: true,
            store: treeStore,
            useTitleAsBackText: false,
            backText: 'Back',
            listeners: {
            	itemtap: function(me, list, selections, eOpts) {
            		//var rec = view.getStore().getAt(index);
            		if(me.getStore().getAt(selections).get('url')){
                    console.log(list, selections );
            		var rec = me.getStore().getAt(selections);
            		var url = rec.get('url') + rec.get('destination') + '/' + rec.get('niins')
                    me.getStore().getProxy().setUrl(url);
                    console.log(me.getStore().getProxy().getUrl());
                    me.getStore().load()
            		}
                }               	
            }
//                   
            //
//            updateRecord: function(record) {
//                var me = this;
//                me.down('#textCmp').setHtml(record.get('question'));
//                me.callParent(arguments);
//            } 
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
