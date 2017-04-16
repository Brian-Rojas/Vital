var app = angular.module('app', []);
app.controller('scheduleController', function($scope, $http) {
    $http.get('/schedule/1').then(function(response) {
        $scope.schedule = response.data;
    });
});