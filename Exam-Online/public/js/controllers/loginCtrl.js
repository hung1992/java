'use strict';
/**
 * Authenticate controller
 *
 * @class LoginCtrl
 * @param $scope
 * @param $rootScope
 * @param LoginService
 */
var LoginCtrl = (function($scope, LoginService, $rootScope, $location) {
	$scope.errMessage = "Please login";

	if($rootScope.logged == true){
		var r = $rootScope.user.role;
		(r != 1)?$location.path('/admin'):$location.path('/home');
	}
	else{
		$scope.auth = {}; // init scope.auth

		// Use the LoginService to log in the user on submit().
		$scope.submit = function() {

			$scope.auth.password = CryptoJS.MD5($scope.auth.password).toString();
			LoginService.login($scope.auth, function(data) {

				$rootScope.user = data;
				$rootScope.requests401 = []; // everything has been re-fired
				$rootScope.$broadcast('event:login'); // broadcast login success
			}, function(response) {
				$rootScope.errorMessage = response.data;

			});
		}

	}

});
