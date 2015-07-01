angular.module('app', ['ui.bootstrap'])

.controller('main', function($scope, $http, $log) {
 
    $http.get('http://localhost:8080/generate/2.4.1/ADT_A01').
    success(function(data) {
        $scope.payload = data;
    });

  // BUTTONS ======================
  
  // COLLAPSE =====================
  $scope.isCollapsed = true;
  //DROP DOWN
  $scope.status = { isopen: false };  
});

//angular.module('app', [ 'ngRoute', 'ui.bootstrap' ])
//  .config(function($routeProvider, $httpProvider) {
//
//	$routeProvider.when('/', {
//		templateUrl : 'home.html',
//		controller : 'home'
//	}).when('/login', {
//		templateUrl : 'edit.html',
//		controller : 'navigation'
//	}).otherwise('/');
//
//    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
//
//  })
//  .controller('home', function($scope, $http) {
//    $http.get('/resource/')
//    .success(function(data) {
//      $scope.payload = data;
//    })
//  })
//  .controller('navigation', function() {})
//  .controller('main', function($scope, $http) { 
//    $http.get('http://localhost:8080/generate/2.4.1/ADT_A01')
//    .success(function(data) {
//        $scope.payload = data;
//    });
//  });   
   



