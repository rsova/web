Ext.define('App.controller.Main', {
    extend: 'Ext.app.Controller',
    requires: ['App.store.Contacts','App.view.ContactsList', 'App.view.ContactDetails'],

    config : {
        refs : {
            mainView   : 'main',
            contacts   : 'contacts',
            details    : 'contactdetails'
        },

        control : {
            contacts : {
                select : 'onContactSelect'
                //initialize: 'onContactsInit'
            },
            main : {
                back : 'onMainBackButton'
            }
        },

        views : [
            'ContactsList',
            'ContactDetails'
        ],

        models: ['App.model.Contact'],
        stores: ['App.store.Contacts']
    },

    onContactSelect: function(list, record) {
        this.showContactDetails(record);
    },

    persistContact : function() {
        var details = this.getDetails(),
            record  = details.getRecord();

        if (! record) {
            return;
        }
        record.beginEdit();
        record.set(details.getValues());
        record.commit();
    },

    /**
     * Override for each profile
     */
    showContactDetails  : Ext.emptyFn,
    onMainBackButton    : Ext.emptyFn,

   //  init: function() {

   //      // Ext.Viewport.mask({
   //      //     xtype: 'loadmask',
   //      //     message: 'loading...'
   //      // });

   //      Ext.getStore('Contacts').load();
   //      Ext.getStore('Contacts').addListener('load',this.onContactsStoreLoad, this);

   //      console.log("On init app found "+ Ext.ComponentQuery.query('main').length+ " mainviews: ", Ext.ComponentQuery.query('main'));
   //      console.log("On init app found the reference: ",this.getMainView());
   //  },

   //  launch: function(app) {
   //     // console.log("On launch app found " + Ext.ComponentQuery.query('main').length + " mainviews: ", Ext.ComponentQuery.query('main'));
   //     // console.log("On launch app found the reference: ", this.getMainView());
   //     console.log("On launch app found " + Ext.ComponentQuery.query('contacts').length + " contacts views: ", Ext.ComponentQuery.query('contacts'));
   //     console.log("On launch app found the reference: ", this.getContacts());
   // },


   //  onContactsStoreLoad: function(records, success, operation) {
   //      console.log(records.getData());

   //      //Ext.Viewport.unmask();
   //  }

});