'use strict';
/**
 * @class HomeCtrl
 * @param $scope
 */
var HomeCtrl = (function($scope, $rootScope, Exam, $location, Users) {
	if($rootScope.logged == false){
		$rootScope.$broadcast('event:loginRequired');

	}
    else if($rootScope.user.role == 2){
		$location.path('/admin');
	}
	else{
			// load all test topics
			$rootScope.exams = Exam.all(function(data){
				console.log(data);
		});
	}

	$scope.setExam = function(e){
		$scope.exam = angular.copy(e);
		//load order question of exam
		Exam.getOrderQuestion({id: $scope.exam.ID}, function(data){
			$scope.exam.orderQuestion = data;
		});
	}


	$scope.doTest = function(exam){

		Users.setExam({id: $rootScope.user.email, arg: $scope.exam.ID}, function(data){

		});

		$rootScope.currentExam = $scope.exam;
		/*var questions = [];
		//set order question of exam
		for(var i = 0; i < $scope.exam.orderQuestion.length; i++){
			for(var j = 0; j < $scope.exam.questions.length; j++){
				//swap position
				if($scope.exam.orderQuestion[i].question_id == $scope.exam.questions[j].ID){
					questions.push($scope.exam.questions[j]);
					break;
				}
			}
		}*/
		//$rootScope.currentExam.questions = questions;
		$location.path("/exam");

	}
});
