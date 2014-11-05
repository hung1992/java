'use strict';

/**
 * This module contains the ETest application configuration.
 * @module eTest
 */

// Convenient literal to store Http Status code,
var httpCode = {
		unauthorized : 401,
	OK : 200
};

var etest = angular.module('ETest', [ 'eTest.services', 'ui.directives', 'timer']);

/**
 * ETest routes.
 */
etest.config([ '$routeProvider', function($routeProvider, $rootScope) {
	$routeProvider.when('/home', {
		templateUrl : '/partials/home.html',
		controller : HomeCtrl
	});
	$routeProvider.when('/login', {
		templateUrl : '/partials/login.html',
		controller : LoginCtrl
	});
	$routeProvider.when('/register', {
		templateUrl : '/partials/register.html',
		controller : RegisterCtrl
	});
	$routeProvider.when('/modify-profile', {
		templateUrl : '/partials/modify-profile.html',
		controller : ModifyProfileCtrl
	});
	$routeProvider.when('/change-pass', {
		templateUrl : '/partials/change-pass.html',
		controller : ChangePassCtrl
	});
	$routeProvider.when('/question', {
		templateUrl : '/partials/question.html',
		controller : QuestionCtrl
	});
	$routeProvider.when('/about', {
		templateUrl : '/partials/about.html'
	});
	$routeProvider.when('/exam', {
		templateUrl : '/partials/exam.html',
		controller : ExamCtrl
	});
    $routeProvider.when('/admin', {
        templateUrl : '/partials/admin-page.html',
        controller : loadAllUserCtr

    });

    $routeProvider.when('/scoring', {
        templateUrl : '/partials/scoring.html',
        controller : AdminCtrl
    });
    $routeProvider.when('/create-exam',{
    	templateUrl : '/partials/create-exam.html',
    	controller : CreateExamCtrl
    });
	$routeProvider.otherwise({
		redirectTo : ('/home')
	});
} ]);



etest.config(['$httpProvider', function($httpProvider) {
	var interceptor = [ '$rootScope', '$q', '$location', function(scope, $q, $location) {
		function success(response) {
			return response;
		}
		function error(response) {
			var status = response.status;
			if (status == httpCode.unauthorized) { // we have done an unauthorized request
				var defer = $q.defer();
				var req = {
					config : response.config,
					deferred : defer
				}

				scope.requests401.push(req); // cache the request who failed
				// Broadcast login required event
				scope.$broadcast('event:loginRequired');
				return defer.promise;
			}
			// otherwise
			return $q.reject(response);
		}

		return function(promise) {
			return promise.then(success, error);
			}
		}
	];
	$httpProvider.responseInterceptors.push(interceptor);
	}
]);

/**
 * Tear up method, initialization of the app.
 * @method run
 */
etest.run(function($rootScope, $location, $http) {
	$rootScope.requests401 = [];
	$rootScope.logged = false;
	$rootScope.currentExam = undefined;

	// load all boards after login successful
	$rootScope.$on('event:login', function() {
		$rootScope.logged = true;
		if($rootScope.user.role == 1){

			$location.path("/home");

		}else{
			$location.path("/admin");
		}
	});


	// do some cleanup on logout
	$rootScope.$on('event:logout', function() {
		$rootScope.logged = false;
		$location.path("/login");

	});

	// Listen to some login related event.
	$rootScope.$on('event:loginRequired', function() {
		$rootScope.errorMessage = "Please authenticate.";
		$location.path("/login"); // redirect to login
		$rootScope.logged = false;
	});


	// ping
	$http.get("/ping").success(function(pUser) {

		$rootScope.logged = true;
		$rootScope.user = pUser;
		if($rootScope.user.role == 1){
			$location.path("/home");

		}else{
			$location.path("/admin");
		}
	}).error(function(){
		$rootScope.logged = false;
		$rootScope.$broadcast('event:loginRequired');
	});

    
});


/**
 * Group by key
 */
etest.filter('group', function() {
    return function(input, key) {
        var unique = {};
        var uniqueList = [];
        for(var i = 0; i < input.length; i++){
            if(typeof unique[input[i][key]] == "undefined"){
                unique[input[i][key]] = "";
                uniqueList.push(input[i]);
            }
        }
        return uniqueList;
    };
});

etest.directive('confirmOnExit', function() {
    return {
        link: function($scope, elem, attrs) {
        	
        	var startTime;
        	
        	// close tab, browser or change url on browser
            window.onbeforeunload = function(event){
            	$scope.$broadcast('timer-pause');
            	if($scope.isSubmit == undefined){
            		return ;
            	}
            	setTimeout(resumeTimer,0);
                return "The test can be taken only one.";
                
            }
            
            var resumeTimer = function(){
            	$scope.$broadcast('timer-resume');
            }
           
           

//            window.onload = function(event){
//            	if(history.go(-1))
//            	    history.go(0);
//            }
              
                        
            
            window.onpopstate = function(event) {

            	if($scope.isSubmit == undefined || $scope.isSubmit == true){
            		$(window).unbind( "onbeforeunload" );

        	    	return;
        	    }
            	alert("The test can be taken only one. So your test was canceled.");
            	$scope.isSubmit = undefined;
//            	
//            	window.history.go(1);
//            	$scope.$broadcast('timer-pause');
//            	event.stopPropagation();
//            	event.preventDefault();
//            	event.stopImmediatePropagation();
//            	if(!confirm("The test can be taken only one.\n Are you sure you want to leave this page?")) {
////            		$scope.$broadcast('timer-resume');
//            		window.history.go(1);
////                    
//                }else{
//                	$scope.isSubmit = undefined;
//                }

            }
                        
        }
    };
});

