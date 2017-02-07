'use strict';

angular.module('myApp').factory('AppService', ['$http', '$q', function($http, $q){

    // var REST_SERVICE_URI = 'http://localhost:8080/application/';
    var REST_SERVICE_URI = 'application/';

    var factory = {
        fetchAllApps: fetchAllApps,
        getApp: getApp,
        createApp: createApp,
        deleteApp:deleteApp
    };

    return factory;
    
    function getApp() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while getting App');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function fetchAllApps() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Apps');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function createApp(app) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, app)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while creating App');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteApp(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while deleting App');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);
