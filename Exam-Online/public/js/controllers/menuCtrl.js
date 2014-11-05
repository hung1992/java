'use strict';
var MenuCtrl = (function($scope, $rootScope, LogoutService){
	
	/**
	 * Logout user
	 */
	$scope.logout = function(){
		console.log("out");
		LogoutService.logout({}, function(res){
			$rootScope.errorMessage = "You've been logged out.";
			$rootScope.$broadcast('event:logout');
		});
	};
	

});