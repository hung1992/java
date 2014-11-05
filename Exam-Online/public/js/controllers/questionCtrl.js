'use strict';

/**
 * QuestionCtrl
 * @author khacvu
 * @param Question
 */

var QuestionCtrl = (function($scope, $rootScope, $location, Question) {
	//List all question
	$scope.allquestions = [];
	//List all question of current page
	$scope.questions = [];
	//A new question
	$scope.newQuestion = {};
	$scope.newQuestion.id = undefined;
	$scope.question = {};
	//List of quizzes
	$scope.quizzes = [];
	//List of types
	$scope.types = [{name: 'One Choice', type: 1, checkbox: false, radio: true, text: false},
	                {name: 'MultiChoice', type: 2, checkbox: true, radio: false, text: false},
	                {name: 'Text', type: 3, checkbox: false, radio: false, text: true}];
	//Type of question
	$scope.newQuestion.type = $scope.types[0];
	//Message
	$scope.messageOption = "Please add content for option";
	$scope.messageErrorLevel = "Please select level for question";
	$scope.messageErrorLanguage = "Please select language for question";
	$scope.messageErrorContent = "Please fill content for question";
	$scope.messageSuccessCreateQuestion = "Create a new question successful!";
	$scope.messageConfirmEditQuestion = "Are you sure to edit this question?";
	$scope.messageEditSuccess = "Edit success";
	$scope.messageEditUnsuccess = "Edit unsuccessful";
	$scope.messageAlertDelete = "You cann't delete question, when you don't have it!";
	$scope.messageConfirmDeleteQuestion = "Are you sure to delete this question?";
	$scope.messageDeleteSuccess = "Delete success!";
	$scope.messageDeleteUnsucces = "Delete unsuccess!";
	//Value of answer
	$scope.newQuestion.answerValue = 0;
	//Value for sort by ID or Name(Language)
	$scope.sortBy = {};
	$scope.sortBy.sort = 'ID';
	$scope.sortBy.ID = true;
	$scope.sortBy.language = false;
	$scope.sortBy.content = false;
	$scope.sortBy.level = false;
	//Value for search
	$scope.search = {};
	$scope.search.language = '';
	$scope.search.type = '';
	$scope.search.level ='';
	//Index of current page
	$scope.indexOfPage = 0;
	$scope.end = undefined;
	//A question for update
	$scope.question = {};
	//Value of button add option, it can be visible or disable.
	$scope.addOption = "";
	//Value of content modal adding question.
	$scope.headingModal = "";

	if($rootScope.logged == false){
		$rootScope.$broadcast('event:loginRequired');

	}
    else if($rootScope.user.role == 1){
		$location.path('/home');
	}

	/**
	 * Sort table by column.
	 */
	$scope.sortByWith = function(name){
		$scope.sortBy.ID = false;
		$scope.sortBy.language = false;
		$scope.sortBy.content = false;
		$scope.sortBy.level = false;
		switch(name){
			case 0:
				$scope.sortBy.sort = 'ID';
				$scope.sortBy.ID = true;
				break;
			case 1:
				$scope.sortBy.sort = 'language';
				$scope.sortBy.language = true;
				break;
			case 2:
				$scope.sortBy.sort = 'content';
				$scope.sortBy.content = true;
				break;
			case 3:
				$scope.sortBy.sort = 'level';
				$scope.sortBy.level = true;
				break;
			default:
				$scope.sortBy.sort = 'ID';
				break;
		}
	}
	/**
	 * Add a new quiz for a new question
	 */
	$scope.addQuiz = function(){
		$scope.quizzes.push({id: -1, content: "", sequence: $scope.quizzes.length, value: false});
	};

	/**
	 * Add a new quiz with content for a new question
	 */
	$scope.addQuizOld = function( Qid, Qcontent, Qsequence, Qvalue){
		$scope.quizzes.push({id: Qid, content: Qcontent, sequence: Qsequence, value:Qvalue});
	};
	/**
	 * Check error question and calculate answer value.
	 */
	$scope.submit = function(){
		//Check error undefined level
		if($scope.newQuestion.level == undefined){
			alertify.error($scope.messageErrorLevel);
			return false;
		}

		//Check error undefined language
		if($scope.newQuestion.language == undefined){
			alertify.error($scope.messageErrorLanguage);
			return false;
		}

		//Check error undefined content
		if($scope.newQuestion.content == undefined){
			alertify.error($scope.messageErrorContent);
			return false;
		}

		//Reset answerValue after convert.
		$scope.newQuestion.answerValue = 0;
		var i;
		//Calculate answer value for One Choice and MultiChoice question
		if($scope.newQuestion.type.radio){
			var check;
			for(i = 0; i < $scope.quizzes.length; i++){

				//With Once choice question, check each radio by element id.
				check = document.getElementById("radio-" + $scope.quizzes[i].sequence).checked;
				if(check){
					$scope.newQuestion.answerValue = Math.pow(2,i);
				}
			}
		}else if($scope.newQuestion.type.checkbox){
			for(i = 0; i < $scope.quizzes.length; i++){
				if($scope.quizzes[i].value){
					$scope.newQuestion.answerValue += Math.pow(2,i);
				}
			}
		}
		//
		$scope.newQuestion.quizzes = $scope.quizzes;
		////////////////////////////////////////////
		if($scope.newQuestion.id == undefined){
			$scope.add();
		}else{
			alertify.confirm($scope.messageConfirmEditQuestion, function (e) {
			    if (e) {
			    	alertify.success($scope.messageEditSuccess);
			    	$scope.updateQuestion($scope.newQuestion.id);
			    } else {
			    	alertify.error($scope.messageEditUnsuccess);
			    }
			});
		}
	};

	/**
	 * send a new question from input to database.
	 */
	$scope.add = function(){
		Question.createQuestion( $scope.newQuestion, function(){
			alertify.success($scope.messageSuccessCreateQuestion);
		})
	};

	/**
	 * get all question from database
	 */
	$scope.getAll = function(){
		Question.all({}, function(data){
				$scope.allquestions = data;
			}, function(error){
				console.log(error)
		});
	};

	/**
	 * Increase index of page, and show list of question in this page.
	 */
	$scope.nextPage = function(){
		if(($scope.end - $scope.indexOfPage*10) > 9){
			$scope.indexOfPage += 1;
		}
	};

	/**
	 * Decrease index of page, and show list of question in this page.
	 */
	$scope.previousPage = function(){
		if($scope.indexOfPage > 0){
			$scope.indexOfPage -= 1;
		}
	}

	/**
	 * Check this page is last page.
	 */
	$scope.endPage = function(end, index){
		if(end){
			$scope.end = index;
		}
	}

	/**
	 * Clean all attribute of question.
	 */
	$scope.resetQuestion = function(){
		$scope.headingModal = "Add new your question";
		reset();
	}

	/**
	 * Reset all attribute of question is undefined.
	 */
	function reset(){
		$scope.newQuestion.id = undefined;
		$scope.newQuestion.language = undefined;
		$scope.newQuestion.level = undefined;
		$scope.newQuestion.type = undefined;
		$scope.newQuestion.content = undefined;
		$scope.newQuestion.answerValue = undefined;
		$scope.quizzes = [];
		$scope.radioValue = undefined;
	}
	/**
	 * Description: Load a choose question from table.
	 * @param question
	 * @returns The view of a choose question.
	 */
	$scope.loadQuestion = function(question){
		$scope.headingModal = "Edit your question";

		//Delete old value from scope.
		reset();

		//Load new question.
		$scope.newQuestion.id = question.ID;
		$scope.newQuestion.language = question.language;
		$scope.newQuestion.level = question.level;
		$scope.newQuestion.type = $scope.types[question.type-1];
		$scope.newQuestion.content = question.content;
		$scope.newQuestion.answerValue = question.answerValue;
		console.log($scope.newQuestion);

		//Convert answerValue from decimal to binary and assign it to each quiz value
		if($scope.newQuestion.type.type != 3){

			//variable quotient is result of divide answerValue for 2
			var quotient = question.answerValue;
			var value = false;
			console.log(quotient);
			for(var i = 0; i < question.quizzes.length; i++){
				if(quotient > 0){
					if(quotient%2 == 0){
						value = false;
					}else{

						//radio correct will be stick with only once.
						$scope.radioValue = question.quizzes[i].sequence;
						value = true;
					}
					quotient = Math.floor(quotient/2);
				}else{
					value = false;
				}
				$scope.addQuizOld(question.quizzes[i].ID ,question.quizzes[i].content, question.quizzes[i].sequence, value);
			}
		}
	}

	/**
	 * Handing event click button Submit when edit modal is using.
	 * @param id
	 * @returns the view of search question.
	 */
	$scope.updateQuestion = function(id){
		Question.updateQuestion( {id: id},$scope.newQuestion, function(){
		});
	};
	/**
	 * Check error and delete question to database.
	 * @return the view of search.
	 */
	$scope.deleteQuestion = function(){
		if($scope.newQuestion.id == undefined){
			alertify.alert( $scope.messageAlertDelete);
		}else{
			alertify.confirm($scope.messageConfirmDeleteQuestion, function (e) {
			    if (e) {
			    	Question.deleteQuestion( {id: $scope.newQuestion.id}, function(){});
			    	alertify.success($scope.messageDeleteSuccess);
			    } else {
			    	alertify.error($scope.messageDeleteUnsucces);
			    }
			});
		}
	};
	$scope.link = function(link){
		console.log(link);
		$location.path(link);
	}
});