Ext.application({
    name: 'partlink',

    
    launch: function () {
        Ext.define('ListItem', {
            extend: 'Ext.data.Model',
            config: {
                fields: ['text','url']
            }
        });

        var treeStore = Ext.create('Ext.data.TreeStore', {
           autoLoad: true,
           model: 'ListItem',
           defaultRootProperty: 'items',
            proxy: {
                type: 'ajax',
                //url: '/lookup/client/016033650,015127231,010077987,015303893,015208009,001186249,010127380,010767912,015118286,012746352',
                url: '/gold',
                reader: {
                    type: 'json'
                } 
            }
        });
//        var stProxy = Ext.StoreMgr.get("myStore").getProxy();
//        stProxy.setUrl("http://the.new.url");
//        Ext.StoreMgr.set("myStore").setProxy(stProxy);
        Ext.create('Ext.NestedList', {
            fullscreen: true,
            store: treeStore,
            //
            listeners: {
            	itemtap: function(me, list, selections, eOpts) {
            		//var rec = view.getStore().getAt(index);
            		if(me.getStore().getAt(selections).get('url')){
                    console.log(list, selections );
            		var rec = me.getStore().getAt(selections);
            		console.log(rec.getData(), rec.get('url'));
                    console.log(me.getStore().getProxy().getUrl());// = rec.get('url')
                    me.getStore().getProxy().setUrl(rec.get('url'));
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
