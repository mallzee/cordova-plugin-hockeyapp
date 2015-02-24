var exec = require('cordova/exec');

var hockeyapp = {
    start:function(success, failure, token) {
        exec(success, failure, "HockeyAppPlugin", "start", [ token ]);
    },
    feedback:function(success, failure) {
        exec(success, failure, "HockeyAppPlugin", "feedback", []);
    },
    testCrash: function(success, failure) {
		exec(success, failure, "HockeyAppPlugin", "forcecrash", []);
	}
};

module.exports = hockeyapp;
