'use strict';

var ExamCtrl = (function($scope, Users, $rootScope, $location, $timeout) {

	if($rootScope.currentExam == undefined || $rootScope.user.active == false){
		$location.path("#/home");
	}else{
		$scope.remainSeconds = $rootScope.currentExam.duration*60;
			

		// the timer-stop event will show a confirm dialog.
		// the candidate should confirm to submit answer or cancel.
		// After that candidate can not do again.
		$scope.$on('timer-stopped', function (event, data){
			$('#answerModal').modal('show');
		});
	    
		// deactive candidate status to mark that candidate did the test.
		$scope.deactive = function(){
			// update status for this account
			Users.deactive({id: $rootScope.user.email}, function(data){
				$rootScope.user = data;
			});    	
		};
		
		// start timer
	    var timer = function() {
	    	if($scope.remainSeconds === 0){
	    		$timeout.cancel();
	    		$scope.$broadcast('timer-stopped');
	    		return;
	    	}
	        $timeout(timer, 1000);
	        $scope.remainSeconds--;
	    }
	    
	    $timeout(timer, 1000);
        
		// the first, mark to user did the test.
		$scope.deactive();
		
		// pause timer
		$scope.$on('timer-pause', function(event){
			$scope.startTime = new Date();
		});
		
		// resume timer
		$scope.$on('timer-resume', function(event){	
			var now = new Date()
			var delay = now.getTime() - $scope.startTime.getTime();
			$scope.remainSeconds -= Math.round(delay/1000);
		});
		
		
		// detect event when user goes to another controller/page
		$scope.$on('$locationChangeStart', function (event, newUrl, oldUrl) {
    	    // Allow navigation if our old url wasn't where we prevented navigation from
    	    if($scope.isSubmit == undefined || $scope.isSubmit == true){
    	    	$(window).unbind( "onbeforeunload" );
    	    	return;
    	    }
    	    
    	    $scope.$broadcast('timer-pause');
    	    if (!confirm("The test can be taken only one.\n Are you sure you want to leave this page?")) {
    	      event.preventDefault();
    	      $scope.$broadcast('timer-resume');
    	      
    	    }else{
    	    	if(!angular.equals($location.url(),"/exam")){
    	    		$(window).unbind( "onbeforeunload" );
    	    	}
    	    	$scope.isSubmit =undefined;
    	    }
    	    
    	});

        $scope.$on('$destroy', function(event){
        	$(window).unbind( "onbeforeunload" );
        	$(window).unbind( "unload" );
        });
		
		$scope.isSubmit = false;
		

	    $scope.answer = [];	//init answer array to sent to AnswerManager controller

	    //create tmpAnswer array, it copied from question of current exam
	    $scope.tmpAnswer = [];
	    $scope.tmpAnswer = angular.copy($rootScope.currentExam.questions);

	    console.log($scope.tmpAnswer);
	    //With multiple choice questions, add feild "value" into quiz object to save candidate's selection
	    //With text question, reset answerValue to no-placeholder
		var i = 0;
	    for (i = 0; i < $scope.currentExam.questions.length; i++){
	    	if ($scope.tmpAnswer[i].type == 2){
	    		var j = 0;
	    		for (j = 0; j < $scope.tmpAnswer[i].quizzes.length; j++){
	    			$scope.tmpAnswer[i].quizzes[j].value = false;
	    		}
	    	}
	    	else {
	    		$scope.tmpAnswer[i].answerValue = "";
	    	}
	    }

	    // submit the anwsers.
	    // after that deactive link to test
	    $scope.submit = function(){
	    	//remind user about unfinished question.

	    	if ($scope.isSubmit){
	    		$scope.deactive();
	    	}
	    	else {
	    		$scope.done();
	    	}
	    }
	    $scope.done = function(){
	        for (i = 0; i < $scope.tmpAnswer.length; i++){
	        	if ($scope.tmpAnswer[i].type == 1){
	        		$scope.tmpAnswer[i].answerValue = $scope.answerFromRadio($scope.tmpAnswer[i].answerValue);
	        	}
	        	else if ($scope.tmpAnswer[i].type == 2){
	        		$scope.tmpAnswer[i].answerValue = $scope.answerFromCheckbox($scope.tmpAnswer[i].quizzes);
	        	}
	        	$scope.answerObj = {};;
	        	$scope.answerObj.questionID = $scope.tmpAnswer[i].ID;
	        	$scope.answerObj.answerValue = $scope.tmpAnswer[i].answerValue;
	        	$scope.answer.push($scope.answerObj);
	        };
	    	Users.sendExam({id:$rootScope.user.email},angular.toJson($scope.answer), function(){
	    		alertify.success("You have success the exam!");
	    		$scope.isSubmit = true;
	    		$scope.deactive();
	    	});
	    };

	    /**
	     * Description: convert from order selection to binary code.
	     * 				Ex: 1 -> 1000, 3 -> 0010,...
	     * @param value value of the order selection. Ex: 1 or 2 or...or size
	     * @param size size of quizzes array
	     * @returns str Ex: 0010, 10000, 01000,...
	     */
		$scope.answerFromRadio = function(value){
			if (value == 0)
				return "0";
			var str = Math.pow(2, value - 1);
			return str;
		};

		/**
		 * Description: convert from array with true/false values to binary code
		 * @param array array contain true/false values, true -> 1; false -> 0
		 * @returns str Ex: 0010, 10000, 01000,...
		 */
		$scope.answerFromCheckbox = function(array){
			var str = "";
			var h = 0;
			for (h = 0; h < array.length; h++){
				(array[h].value) ? str="1"+str : str="0"+str;
			}
			return parseInt(str, 2).toString();;
		};
	}

});