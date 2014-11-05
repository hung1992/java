'use strict';
/**
 * @class userCtrl
 * @param $scope
 */

/**
 * Description : Controller Register
 * @param $scope
 * @param Users
 * @param $location
 * @param $rootScope
 */
var RegisterCtrl = function($scope, Users, $location,$rootScope) {

	if($rootScope.user.logged == true){
		var r = $rootScope.user.role;
		(r != 2)?$location.path('/home'):$location.path('/admin');
	}

	else{
		$scope.user = {}; // init scope.user
		$scope.acc = {};
		$scope.positions = ['Developer','Team Leader','Project Manager','DBA','Tester'];
		$scope.levels = ['Senior','Junior','InternShip'];
		$scope.user.gender = "";
		$scope.user.position = "";
		$scope.user.level = "";

		// Use the Users to register the user on submit().
		$scope.submit = function() {
			if ($scope.user.gender == ""  ||  $scope.user.position == ""  ||  $scope.user.level == "")
				alertify.error("Gender, Position, Level field must not be blank.");
			else if($scope.user.phoneNumber.localeCompare("0" + parseInt($scope.user.phoneNumber).toString()) != 0)
				alertify.error("Phone number format is wrong.");
			else if ($scope.password.localeCompare($scope.confirmpassword) != 0){
				alertify.error("The passwords do not match. Try again!")
			}
			else if ($scope.password.length < 6){
				alertify.error("Password must be more than five characters. Try again!")
			}
			else {
				$scope.acc.password = CryptoJS.MD5($scope.password).toString();
				// refactor request data with multi parameters
				Users.register(JSON.stringify({ candidate: $scope.user, account: $scope.acc}), function(){

					alert("Congratulations! You have already register successfully. You can login to start the test.")

					// after register success, user can login
					$location.path("#/login");
				});
			}
		};
	}

};

/**
 * Description : Controller Modify Profile
 * @param $scope
 * @param Users
 * @param $location
 * @param $rootScope
 */
var ModifyProfileCtrl = function($scope, Users, $location, $rootScope) {
	$scope.positions = ['Developer','Team Leader','Project Manager','DBA','Tester'];
	$scope.levels = ['Senior','Junior','InternShip'];
	
	if($rootScope.logged == false){
		$rootScope.$broadcast('event:loginRequired');
		
	}
	else if($rootScope.user.role != 1){
		$location.path('/admin');
	}

	else{
		$scope.user = {}; // init user profile	 
	
		// Use the Users to get user profile.
		$scope.user = Users.get({id: $rootScope.user.email});
		
		// Use the Users to modify profile on modifyProfile().
		$scope.submit = function() {
			if($scope.user.phoneNumber.localeCompare("0" + parseInt($scope.user.phoneNumber).toString()) != 0){
				alertify.error("Phone number format is wrong.");
			}
			else {
				Users.modifyProfile($scope.user, function(){
		            alertify.success("Success!");
					$location.path("/home");
				});
			}
		};
	}
};

/**
 * Description : Controller Change Password
 * @param $scope
 * @param Users
 * @param $location
 * @param $rootScope
 */
var ChangePassCtrl = function($scope, Users, $location, $rootScope) {
	if($rootScope.logged == false){
		$rootScope.$broadcast('event:loginRequired');
		
	}else{
		$scope.user = {}; // init user account	 
		$scope.user.email = $rootScope.user.email;
		$scope.submit = function() {
			if ($scope.newPass.length < 6)
				alertify.error("Password must be more than five characters. Try again!")
			else {
				$scope.user.oldPass = CryptoJS.MD5($scope.oldPass).toString();
				$scope.user.newPass = CryptoJS.MD5($scope.newPass).toString();
				$scope.user.confirmNewPass = CryptoJS.MD5($scope.confirmNewPass).toString();
				Users.changePass($scope.user, function(){
					alertify.success("Success!");
					$location.path("/home");
				}, function(response){
					alertify.error(response.data);
				});
			}
				
			
		};
	}
};


var loadAllUserCtr = function($scope, Users, $location, $rootScope,$window,LogoutService){
	if($rootScope.logged == false){
		$rootScope.$broadcast('event:loginRequired');

	}

	else if($rootScope.user.role != 2){
		$location.path('/home');
	}
	else{
		$scope.candidate = [];
		/**
		 * get all candidates from data
		 */
		$scope.aCandidate = {};


		//scope variable for searching candidate information
		$scope.searchLevel = "";
		$scope.searchText = "";
		$scope.searchPosition = "";
		$scope.searchLanguage = "";

		//language searcher
		$scope.btnJava = function(){
			$scope.searchLanguage = "Java" ;
			$scope.searchText = "Java";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		$scope.btnPHP = function(){
			$scope.searchLanguage = "PHP" ;
			$scope.searchText = "PHP";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		$scope.btnCSharp = function(){
			$scope.searchLanguage = "C#" ;
			$scope.searchText = "C#";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		$scope.btnCplus = function(){
			$scope.searchLanguage = "C++" ;
			$scope.searchText = "C++";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		$scope.btnHTMLJavaScript = function(){
			$scope.searchLanguage = "HTML" ;
			$scope.searchText = "HTML";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		$scope.btnRuby = function(){
			$scope.searchLanguage = "Ruby" ;
			$scope.searchText = "Ruby";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		$scope.btnPython = function(){
			$scope.searchLanguage = "Python" ;
			$scope.searchText = "Python";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		$scope.btnAll = function () {
			$scope.searchLanguage = "";
			$scope.searchText =  "";
			$scope.searchPosition = "";
			$scope.searchLevel = "";
		};

		//position searcher
		$scope.btnDeveloper = function(){
			$scope.searchPosition = "Developer";
			$scope.searchText = "Developer";

			$scope.searchLanguage = "";
			$scope.searchLevel = "";
		};

		$scope.btnTester = function(){
			$scope.searchPosition = "Tester";
			$scope.searchText = "Tester";

			$scope.searchLanguage = "";
			$scope.searchLevel = "";
		};

		$scope.btnProjectManager = function(){
			$scope.searchPosition = "Project Manager";
			$scope.searchText = "Project Manager";

			$scope.searchLanguage = "";
			$scope.searchLevel = "";
		};

		$scope.btnTeamLeader = function(){
			$scope.searchPosition = "Team leader";
			$scope.searchText = "Team leader";

			$scope.searchLanguage = "";
			$scope.searchLevel = "";
		};

		$scope.btnDBA = function(){
			$scope.searchPosition = "DBA";
			$scope.searchText = "DBA";

			$scope.searchLanguage = "";
			$scope.searchLevel = "";
		};

		//level searcher
		$scope.btnJunior = function(){
			$scope.searchPosition = "Junior";
			$scope.searchText = "Junior";

			$scope.searchPosition = "";
			$scope.searchLanguage = "";
		};

		$scope.btnSenior = function(){
			$scope.searchPosition = "Senior";
			$scope.searchText = "Senior";

			$scope.searchPosition = "";
			$scope.searchLanguage = "";
		};

		$scope.btnIntern = function(){
			$scope.searchPosition = "Internship";
			$scope.searchText = "Internship";

			$scope.searchPosition = "";
			$scope.searchLanguage = "";
		};

		// remove a candidate
		$scope.deleteCandidate = function(email){
			Users.deleteUser({id: email}, function(){
				alert("Delete "+email+"  OK");
				$window.location.reload();
			});

		};


		$scope.MyCandidate = [];
		Users.all({},function(data){
			console.log(data);
			$scope.MyCandidate = data;

		}, function(error){
			console.log(error);
		});

		//resource for todo list
		$scope.noteContent = "";
		$scope.noteURL = "";
		$scope.note = [];
		$scope.addNote = function(){
			alert($scope.note.content + "   " + $scope.note.url);

		};


		//button edit
		$rootScope.userMe = {};
		$rootScope.userData = [];
		$scope.btnEdit = function(c){
			console.log(c.exam);
			if(c.exam == null){
				alert(c.fullName + "   CHUA LAM BAI TEST");
			}
			else{
				$rootScope.userMe = c;
				$location.path('/scoring');
			}
		};

			//button logout
		$scope.logout = function(){
			console.log("out");
			LogoutService.logout({}, function(res){
				$rootScope.errorMessage = "You've been logged out.";
				$rootScope.$broadcast('event:logout');
			});
		};


	}

};