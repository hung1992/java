'use strict';

/**
 * CreateExamCtrl
 * @author khacvu
 * @param Exam
 */

var CreateExamCtrl = (function($scope, $rootScope, $location, Exam, Question) {
	//A new exam
	$scope.newExam = {};
	$scope.newExam.id = undefined;
	$scope.newExam.order = [];
	//List of all exam
	$scope.exams = [];
	//List of question
	$scope.allQuestions = [];
	//Attribute in exam
	$scope.newExam.questions = [];
	$scope.newExam.numberQuestion = 0;
	$scope.newExam.numberOfQuestion = 10;
	$scope.newExam.duration = 60;
	$scope.newExam.language = "Java";
	$scope.newExam.autoCountQuestion = false;
	//Tab index
	$scope.tab = {};
	$scope.tab.search = true;
	$scope.tab.edit = false;
	$scope.tab.view = false;
	$scope.tab.searchPill = "active";
	$scope.tab.editPill = "";
	$scope.tab.viewPill = "";
	//Value for sort by ID or Name(Language)
	$scope.sortBy = {};
	$scope.sortBy.sort = 'ID';
	$scope.sortBy.ID = true;
	$scope.sortBy.name = false;
	$scope.sortBy.language = false;
	$scope.sortBy.duration = false;
	$scope.sortBy.amountOfQuestions = false;
	//Value for search exam
	$scope.search = {};
	$scope.search.language = '';
	$scope.search.question = {};
	$scope.search.question.language = '';
	$scope.search.question.level = '';
	$scope.search.question.type = '';
	//Message
	$scope.messageSuccessCreateExam = "Create exam successful!";
	$scope.messageErrorName = "Please add name for exam";
	//Index of current page
	$scope.indexOfPage = 0;
	$scope.end = undefined;

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
		$scope.sortBy.name = false;
		$scope.sortBy.amountOfQuestions = false;
		$scope.sortBy.duration = false;
		switch(name){
			case 0:
				$scope.sortBy.sort = 'ID';
				$scope.sortBy.ID = true;
				break;
			case 1:
				$scope.sortBy.sort = 'name';
				$scope.sortBy.name = true;
				break;
			case 2:
				$scope.sortBy.sort = 'language';
				$scope.sortBy.language = true;
				break;
			case 3:
				$scope.sortBy.sort = 'duration';
				$scope.sortBy.duration = true;
				break;
			case 4:
				$scope.sortBy.sort = 'numberOfQuestion';
				$scope.sortBy.amountOfQuestions = true;
				break;
			default:
				$scope.sortBy.sort = 'ID';
				$scope.sortBy.ID = true;
				break;
		}
	}

	function closeAllTab(){
		$scope.tab.search = false;
		$scope.tab.edit = false;
		$scope.tab.view = false;
		$scope.tab.searchPill = "";
		$scope.tab.editPill = "";
		$scope.tab.viewPill = "";
	};

	/**
	 * Active, show selected tab and visible other.
	 */
	$scope.tab.change = function(number){
		closeAllTab();
		if(number == 1){
			$scope.tab.search = true;
			$scope.tab.searchPill = "active";
		}else if(number == 2){
			$scope.tab.edit = true;
			$scope.tab.editPill = "active";
		}else{
			$scope.tab.view = true;
			$scope.tab.viewPill = "active";
		}
	};

	/**
	 * Get all exam.
	 * @returns list of exam.
	 */
	$scope.getAll = function(){
		Exam.all({}, function(data){
			$scope.exams = data;
		}, function(error){
			console.log(error);
		});
	};

	/**
	 * Get all question.
	 * @returns list of questions.
	 */
	$scope.getAllQuestion = function(){
		var i, j;
		Question.all({}, function(questions){
				$scope.allQuestions = questions;
				for(i = 0; i < $scope.newExam.questions.length; i++){
					for(j = 0; j < $scope.allQuestions.length; j++){
						if($scope.newExam.questions[i].ID == $scope.allQuestions[j].ID){
							$scope.allQuestions[j].choose = true;
						}
					}
				}
			}, function(error){
				console.log(error);
		});
	};

	/**
	 * Remove all question in exam.
	 */
	function removeAllQuestion(){
		$scope.newExam.questions = [];
		$scope.newExam.numberQuestion = 0;
	}

	/**
	 * Add questions is chosen into exam.
	 * @returns The view exam.
	 */
	$scope.submitQuestion = function(){
		var i = 0;
		//Remove all question in exam.
		removeAllQuestion();

		for(i = 0; i < $scope.allQuestions.length; i++){
			if($scope.allQuestions[i].choose){
				$scope.newExam.questions.push($scope.allQuestions[i]);
				$scope.newExam.numberQuestion++;
			}
		}
		//check amount of question.
		if($scope.newExam.numberQuestion > $scope.newExam.numberOfQuestion && !$scope.newExam.autoCountQuestion){
			alertify.alert("There are too many questions.\n When you set exam with " + $scope.newExam.numberOfQuestion + " questions.");
			removeAllQuestion();
			return false;
		}else{
			alertify.success("Edit question is successful.");
		}
	};

	/**
	 * Swap two question in list, if direction is true, this question is move up and false is move down.
	 * @param direction, index
	 * @returns the view of exam
	 */
	$scope.move = function(direction, index){
		if(direction && index > 0){
			var temp = $scope.newExam.questions[index];
			$scope.newExam.questions[index] = $scope.newExam.questions[index-1];
			$scope.newExam.questions[index-1] = temp;
		}else if(!direction && index < $scope.newExam.questions.length-1){
			var temp = $scope.newExam.questions[index];
			$scope.newExam.questions[index] = $scope.newExam.questions[index+1];
			$scope.newExam.questions[index+1] = temp;
		}
	};


	/**
	 * Check name, amount of question and confirm exam to edit or create.
	 */
	$scope.submit = function(){
		if($scope.newExam.name == undefined){
			alertify.alert($scope.messageErrorName);
			return false;
		}

		//check amount of question
		if(!$scope.newExam.autoCountQuestion && $scope.newExam.numberQuestion != $scope.newExam.numberOfQuestion){
			alertify.confirm("Choice questions are not enough, do you want continue?", function (e){
				if(e){
					$scope.newExam.autoCountQuestion = true;
				}else{
					alertify.error("Create unsuccessful!");
				}
			});
			return false;
		}
		if($scope.newExam.ID == undefined){
			$scope.createExam();
		}else{
			alertify.confirm("Are you sure to edit this exam?", function (e) {
			    if (e) {
			    	$scope.updateExam($scope.newExam.ID);
			    	alertify.success("Edit success");

			    } else {
			    	alertify.error("Edit unsuccessful");
			    }
			});
		}
	}

	/**
	 * Edit exam and save it to database
	 */
	$scope.updateExam = function(id){
		console.log($scope.newExam);
		Exam.updateExam( {id: id},$scope.newExam, function(){
		});
	}


	/**
	 * Create exam and save it to database.
	 */
	$scope.createExam = function(){
		Exam.createExam( $scope.newExam, function(){
			console.log($scope.newExam);
			alertify.success($scope.messageSuccessCreateExam);
		});
	}

	$scope.resetExam = function(){
		resetExam();
	};

	function resetExam(){
		$scope.newExam.ID = undefined;
		$scope.newExam.name = undefined;
		$scope.newExam.questions = [];
		$scope.newExam.numberQuestion = 0;
		$scope.newExam.numberOfQuestion = 10;
		$scope.newExam.duration = 60;
		$scope.newExam.language = "Java";
		$scope.newExam.autoCountQuestion = false;
	}

	/**
	 * read exam from database and show it in tab edit.
	 * @returns The view of exam
	 */
	$scope.loadExam = function(exam){
		var i, j;
		resetExam();
		console.log(exam);
		$scope.newExam = exam;
		$scope.newExam.numberQuestion = exam.numberOfQuestion;
		$scope.newExam.autoCountQuestion = true;

		//load order question
		for(var i = 0; i < exam.questions.length; i++){
			$scope.newExam.questions[i] = exam.questions[i].question;
		}

		//Swap tab
		$scope.tab.change(2);
	};

	/**
	 * delete exam.
	 */
	$scope.deleteExam = function(id){

		if(id == undefined){
			alertify.alert("You can't delete exam when you don't create it.");
			return false;
		}
		alertify.confirm("Are you sure to delete this exam?", function(e){
			if(e){
				Exam.deleteExam({id: id}, function(){});
		    	alertify.success("Edit success");
			}
			else{
		    	alertify.error("Edit unsuccess");
			}
		});
	};

	/**
	 * Link to another page.
	 */
	$scope.link = function(link){
		$location.path(link);
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
});