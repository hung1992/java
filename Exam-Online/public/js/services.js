'use strict';

/**
 * This module contains the ETest project convenient services. such as:
 * LoginService, and Admin service.
 *
 * @module kb.services
 */
var eTestServices = angular.module('eTest.services', [ 'ngResource']);

eTestServices.factory('LoginService', function($resource, $rootScope) {

	/**
	 * Login the user with the given credentials (auth) on the server.
	 *
	 * @method login
	 * @param auth,
	 *            the user credentials (e.g { user : "toto", password: "secret" }
	 * @return a promise.
	 */

	return $resource('/login', {}, {
		login : {
			method : 'POST',
			isArray : false
		}

	});

});

eTestServices.factory('LogoutService', function($resource, $rootScope) {

	/**
	 * Logout the logging user.
	 *
	 * @method logout
	 */

	return $resource('/logout', {}, {
		logout : {
			method : 'POST',
			isArray : false
		}
	});

});

/**
 * Users service
 */
eTestServices.factory('Users', function($resource, $rootScope) {
	return $resource('/users/:id/:action/:arg', {
		id: "@id",
		action: "@action",
		arg: "@arg"
	}, {
		all : {
			method : 'GET',
			isArray : true
		},

        deleteUser : {
        method : 'GET',
            params:{
            action: "delete-user"
        }
    },

    getData : {
        method: 'GET',
        isArray : true,
            params:{
            action: "get-data"
        }
    },

    getData1 : {
        method: 'GET',
        isArray : true,
        params:{
            action: "get-data1"
        }
    },

    exportData : {
	    method : 'GET',
	    params:{
		    action: "export-data"
	    }
    },
        saveScore : {
            method: 'GET',
            params: {
                action : "save-score"
            }
        },

		changePass : {
			method: 'POST',
			params : {
				action : "change-pass"
			}
		},
		modifyProfile : {
			method: 'POST',
			params : {
				action : "modify-profile"
			}
		},
		forgotPass : {
			method: 'POST',
			params : {
				action : "forgot-pass"
			}
		},
		sendMail : {
			method: 'POST',
			params : {
				action : "sendMail"
			}
		},
		sendExam : {
			method: 'POST',
			params: {
				action: "send-exam"
			}
		},
		register : {
			method: 'POST',
			params: {
				action: "register"
			}
		},
		deactive : {
			method: 'POST',
			params:{
				action: "deactive"
			}
		},
		setExam: {
			method: 'GET',
			params:{
				action: "set-exam"
			}
		}
	});
});
/**
 * Question service
 */
eTestServices.factory('Question', function($resource) {
	return $resource('/question/:id',{
		id: "@id"
			},{
			all: {
				method: 'GET',
				isArray: true
			},
			createQuestion: {
				method: 'POST'
			},
			getQuestionByID: {
				method: 'GET'
			},
			updateQuestion: {
				method: 'POST'
			},
			deleteQuestion: {
				method: 'DELETE'
			}
		});
});
/**
 * Exam service
 */
eTestServices.factory('Exam', function($resource){
	return $resource('/exam/:id/:action' ,{
		id: "@id",
		action: "@action"
		},{
			createExam: {
				method: 'POST',
				isArray: false
			},
		    all: {
				  method: 'GET',
				  isArray: true
			},
			updateExam: {
				method: 'POST',
				isArray: false
			},
			deleteExam: {
				method: 'DELETE',
				isArray: false
			},
			getExamById: {
				method: 'GET',
				isArray: false
			},
			getOrderQuestion: {
				method: 'GET',
				isArray: true,
				params:{
					action: "getOrder"
				}
			}
		});
});
