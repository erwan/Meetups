(function(){

var models = require("models");
var views = require("views");

var meetupView;

var Workspace = Backbone.Router.extend({

  routes: {
    "":                 "list",
    "meetup/:id":       "meetup"
  },

  list: function() {
  },

  meetup: function(id) {
  }

});

// Main
$(function(){
    window.workspace = new Workspace();
    var meetups = new models.MeetupCollection();
    meetupsView = new views.MeetupsView({
        collection: meetups
    });
    meetups.fetch();
    Backbone.history.start();
});

})();

