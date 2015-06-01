Ext.application({
    name: 'partlink',
    
    launch: function () {
        Ext.define('ListItem', {
            extend: 'Ext.data.Model',
            config: {
                fields: ['text','idx','url','niins','destination']
            }
        });

        var treeStore = Ext.create('Ext.data.TreeStore', {
           autoLoad: true,
           storeId: 'treeStore',
           model: 'ListItem',
           defaultRootProperty: 'items',
           remoteFilter: false,
           proxy: {
                type: 'ajax',
                url: '/work/assemblages',
                reader: {
                    type: 'json'
                } 
            }
        });
        var nestedList = Ext.create('Ext.NestedList', {
        	id: 'nestedlistId',
        	xtype : 'nestedlist',
            fullscreen: true,
            store: treeStore,
            emptyText: "<div>No notes found.</div>",
            deferEmptyText: false,
            toolbar: {
            	items: [{xtype: 'spacer'},
            	        {iconCls: 'home',
            	        	iconMask: true,
            	        	id: 'homeBtn',
            	        	handler:function(btn){
            	        		var store = btn.up('nestedlist').getStore()
            	        		store.getProxy().setUrl('/work/assemblages');
            	        		store.load()}
            	        }]
            },
            title: "<font size='3' color='white'>MissionLink</font>",
            loadingText: 'Loading <b>Partlink</b> data...',
            useTitleAsBackText: false,
            updateTitleText : false,
            backText: 'Back',
            listeners: {
            	itemtap: function(me, list, selections, eOpts) {
            		if(me.getStore().getAt(selections).get('url')){
	            		var rec = me.getStore().getAt(selections);
	            		var url = rec.get('url') + rec.get('destination') + '/' + rec.get('niins')
	                    me.getStore().getProxy().setUrl(url);
	                    me.getStore().load()
            		}
                }               	
            }
        });
        
        Ext.define('Sencha.view.SearchBar', {
            extend: 'Ext.Toolbar',
            xtype : 'searchbar',
            requires: ['Ext.field.Text', 'Ext.field.Search'],

            config: {
                ui: 'searchbar',
                layout: 'vbox',

                items: [{
                        xtype: 'searchfield',
                        placeHolder: 'Search...',
                    }]
              },
              initialize: function() {
                  this.on({
                      scope: this,
                      delegate: 'searchfield',
                      keyup: 'onSearchFieldKeyUp'
                  });
              },
//              onSearchFieldKeyUp : function(searchField){
//            	  var val = searchField.getValue();
//            	  console.log( val.length)
//            	  if(val.length > 2){
//	            	  var ts = Ext.getStore('treeStore')
//	            	  console.log(ts)
//	            	  var activeStore = ts.getActiveItem();
//	            	  activeStore.store.filter('text',val);
//	            	  ts.update();
//            	  }
//              }
//              onSearchFieldKeyUp : function(searchField){  
//            	  var val = searchField.getValue();
//            	  console.log( val.length)
//            	  if(val.length > 2){
//	            	  console.log(val);
//	            	  var ts = Ext.getStore('treeStore')
//	            	  ts.clearFilter(); 
//	            	  ts.filterBy( function(it){
//		            	  var text =  it.get('idx')
//	            		  if (typeof(text) != 'undefined'){
//		            		  console.log(val +'~~~~~~~'+text+'======'+text.indexOf(val)+"----------"+(text.indexOf(val)!=-1));
//		            		  return (text.indexOf(val)!=-1) 
//	            		  }else{return false} 
//	            	  });
//            	  }
//            	}
              onSearchFieldKeyUp : function(searchField){  
            	  var val = searchField.getValue();
            	  console.log( val.length)
            	  if(val.length > 2){
            		console.log(Ext.getStore('treeStore').findRecord('text', val))
            		//console.log(Ext.getCmp('nestedlistId').goToNode( Ext.getStore('treeStore').findRecord('text', searchField.getValue())));
            		 // Ext.getCmp('yourNestedListID').goToNode( Ext.getStore('NestedListStore').findRecord('text', field.getValue())); 

//            		  console.log(val);
//            		  var ts = Ext.getStore('treeStore')
//            		  ts.clearFilter(); 
//            		  ts.filterBy( function(it){
//            			  var text =  it.get('idx')
//            			  if (typeof(text) != 'undefined'){
//            				  console.log(val +'~~~~~~~'+text+'======'+text.indexOf(val)+"----------"+(text.indexOf(val)!=-1));
//            				  return (text.indexOf(val)!=-1) 
//            			  }else{return false} 
//            		  });
            	  }
              }
//              onSearchFieldKeyUp: function(field) {
//            	  var val = field.getValue()
//                  console.log(val);
//                  //Ext.getStore('storeId').filter("title", "ABC3");
//                  var ts = Ext.getStore('treeStore');
//                  console.log(ts);
//                  ts.clearFilter();
//                  ts.filterBy(function(record, val) {
//                	  console.log( record.get('text')+'     '+ val)	
//                	    return(record.get('text').indexOf('BRUSH') !=-1)
//                	}, this);                 
 //                 ts.filter( Ext.create('Ext.util.Filter', {property: "text", value: /\.BRUSH$/, root: 'items'}));
 //                 ts.filter(new Ext.util.Filter({property:"text", value:val, exactMatch:true}));
//                 ts.filterBy(function(record, field){
//                	   var text = record.get('text');
//                	   //console.log('!  ' + field.getValue());
//
//                	   if(text.indexOf("BRUSH") !=-1){
//                    	   console.log(text)
//                	      return record;
//                	   }
//                   })
 //             }
        });
        
        
//        nestedList.add({
//            docked: 'top',
//            xtype: 'searchbar'
//        }); 

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
