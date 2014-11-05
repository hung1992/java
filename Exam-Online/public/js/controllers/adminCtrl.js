'use strict';

var AdminCtrl = (function($scope, $rootScope, Users,$location) {

		$scope.user = $rootScope.userMe;

		$scope.totalScore = 0;
		$scope.userData = [];
		//send and get data from server
		//Note : this method is depreciated
		$scope.sendToServer = function(){

			/* alert("CALL GET DATA");*/
			var userEmail = $scope.user.account.email;

			Users.getData({id : userEmail,arg: 1}, function(dataReceived){

				$scope.userData.questionList = angular.copy(dataReceived);
			});

			Users.getData({id : userEmail,arg: 2}, function(dataReceived1){

				$scope.userData.answerList = angular.copy(dataReceived1);
			});

		}



		// invoke data from back-end
		$scope.allData = [];
		$scope.sendAndGetData = function(){
			var userEmail = $scope.user.account.email;
			Users.getData1({id: userEmail},function(data){
				console.log(data);
				$scope.allData = data;
			});
		};

		//calculating total score
		$scope.calScore = function(){
			var total = 0;
			for(var i=0; i<$scope.allData.length; i++){
				//data receive from server is parsed to string, so i need to re-parse it to Number type
				var aScore = Number($scope.allData[i].score);
				total += aScore;
			}
			$scope.totalScore = total;
			return total;
		};

		$scope.saveUserScore = function(){
			var userEmail = $scope.user.account.email;
			var userScore = $scope.totalScore.toString();
			Users.saveScore({id: userEmail, arg: userScore},function(){
				$location.path('/admin');
			});
		}


	//}

});