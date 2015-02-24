var exec = require('cordova/exec');

var hockeyapp = {
    start:function(success, failure, token) {
        exec(success, failure, "HockeyApp", "start", [ token ]);
    },
    feedback:function(success, failure) {
        exec(success, failure, "HockeyApp", "feedback", []);
    },
    testCrash: function(success, failure) {
		exec(success, failure, "HockeyAppPlugin", "forcecrash", []);
	}
};

module.exports = hockeyapp;
