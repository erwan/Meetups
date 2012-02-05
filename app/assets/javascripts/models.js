
var Meetup = Backbone.Model.extend({
});

var MeetupCollection = Backbone.Collection.extend({

    model: Meetup,

    url: "/meetups"

});

// Only objects declared below are exposed
exports.Meetup = Meetup;
exports.MeetupCollection = MeetupCollection;

