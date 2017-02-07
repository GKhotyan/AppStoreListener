'use strict';

angular.module('myApp').controller('AppController', ['$scope', 'AppService', function($scope, AppService) {
    var self = this;
    self.app={id:null,name:'',url:'', maxVersion:''};
    self.apps=[];
    self.submit = submit;
    self.edit = edit;
    self.remove = remove;

    fetchAllApps();

    function fetchAllApps(){
        AppService.fetchAllApps()
            .then(
                function(d) {
                    self.apps = d;
                },
                function(errResponse){
                    console.error('Error while fetching Apps');
                }
            );
    }

    function createApp(app){
        AppService.createApp(app)
            .then(
                fetchAllApps,
                function(errResponse){
                    console.error('Error while creating App');
                }
            );
    }

    function updateApp(app, id){
        AppService.updateApp(app, id)
            .then(
                fetchAllApps,
                function(errResponse){
                    console.error('Error while updating App');
                }
            );
    }

    function deleteApp(id){
        AppService.deleteApp(id)
            .then(
                fetchAllApps,
                function(errResponse){
                    console.error('Error while deleting App');
                }
            );
    }

    function submit() {
        if(self.app.id===null){
            console.log('Saving New App', self.app);
            createApp(self.app);
        }else{
            updateApp(self.app, self.app.id);
            console.log('App updated with id ', self.app.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.apps.length; i++){
            if(self.apps[i].id === id) {
                self.app = angular.copy(self.apps[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.app.id === id) {//clean form if the app to be deleted is shown there.
            reset();
        }
        deleteApp(id);
    }

}]);