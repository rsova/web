
angular.module('app', [ 'ngRoute', 'ui.bootstrap' ])
  .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'generate.html',
		controller : 'main'
	}).when('/edit', {
		templateUrl : 'edit.html',
		controller : 'navigation'
	}).when('/validate', {
		templateUrl : 'validate.html',
		controller : 'validate'
	}).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  })
  .controller('home', function($scope, $http) {
    $http.get('/resource/')
    .success(function(data) {
      $scope.payload = data;
    })
  })
  .controller('navigation', function($scope) {
	  $scope.isCollapsed = true;
	  $scope.isCollapsed1 = true;
	  $scope.isCollapsed2 = true;})
  .controller('validate', function($scope) {
	  $scope.visible = false;
	  $scope.toggle = function() {
	    $scope.visible = !$scope.visible;
	  }
  })
  .controller('main', function($scope, $http) { 
    $http.get('http://localhost:8080/generate/2.4.1/ADT_A01')
    .success(function(data) {
        $scope.payload = data;
    });
  });   
   



