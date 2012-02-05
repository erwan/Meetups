
exports.MeetupView = Backbone.View.extend({

    tagName: "li",

    render: function() {

    }

});

exports.MeetupsView = Backbone.View.extend({

    el: "#meetups",

    initialize: function() {
        _.bindAll(this, 'render');
        this.collection.bind('add', this.render);
        this.collection.bind('reset', this.render);
        this.collection.bind('change', this.render);
    },

    render: function() {
        $(this.el).html(this.collection.map(function(meetup){
            return "<li>" + meetup.get("name") + "</li>";
        }).join(""))
    }

});

