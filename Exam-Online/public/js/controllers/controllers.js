'use strict';

var QuestionCtrl = (function($scope, $location, Question) {
	console.log("question");
	Question.all({}, function(data){
		console.log(data);
		$scope.data = data;
	}, function(error){
		console.log(error)
	});
});
