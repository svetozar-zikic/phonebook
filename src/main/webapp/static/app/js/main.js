var phonebookApp = angular.module('phonebookApp', ['ngRoute']);

phonebookApp.service('pagingSvc', function($location){
	
	this.back = function(search_url, page){
		page = page - 1;
		return $location.path(search_url + page);
	};
	
	this.forward = function(search_url, page){
		page = page + 1;
		return $location.path(search_url + page);
	};
	
	this.first = function(search_url){
		return $location.path(search_url + 0);
	};
	
	this.last = function(search_url, pages){
		lastPage = pages - 1;
		return $location.path(search_url + lastPage);
	};
});

phonebookApp.controller('contactsCtrl', function($scope, $http, $location, $routeParams, pagingSvc){
	
	$scope.base_url = "/api/contacts";
	$scope.contacts = [];
	
	$scope.page = Number($routeParams.page);
	$scope.num_pages = 0;
		
	var getContacts = function () {
		$http.get($scope.base_url, { params: {'page': $scope.page}})
			.then(function success(data) {
				$scope.contacts = data.data;
				$scope.num_pages = data.headers("pages");
			}, function error(data) {
				console.log(data);
			})
		};
		
	getContacts();
	
	$scope.addContact = function() {
		$location.path("/contacts/add");
	};
	
	$scope.editContact = function(id) {
		$location.path("/contacts/edit/" + id);
	};
	
	$scope.deleteContact = function(id) {
		$http.delete($scope.base_url + "/" + id)
			.then(function success(data){
				getContacts();
			}, function error(data){
				console.log(data);
			})
	};
	
	$scope.findContact = function() {
		$location.path("/contacts/find");
	};
	
	$scope.viewContact = function(id) {
		$location.path("/contacts/view/" + id);
	};
	
	$scope.search_url = '/contacts/page/';
	
	$scope.back = function(){
		pagingSvc.back($scope.search_url, $scope.page);
	};
	
	$scope.forward = function(){
		pagingSvc.forward($scope.search_url, $scope.page);
	};
	
	$scope.first = function(){
		pagingSvc.first($scope.search_url);
	};
	
	$scope.last = function(){
		pagingSvc.last($scope.search_url, $scope.num_pages);
	}
	
	
	
});

//==========================================================================================

phonebookApp.controller('addContactCtrl', function($scope, $http, $location){
	
	$scope.contact = {};
	$scope.base_url = "/api/contacts";
	
	$scope.add = function(){
		$http.post($scope.base_url, $scope.contact)
		.then(function success(data){
			$location.path("/contacts");
		}, function error(data){
			console.log(data);
		});
		
	};
	
});

//==========================================================================================

phonebookApp.controller('viewContactCtrl', function($scope, $http, $routeParams){
	
	$scope.contact = {};
	$scope.base_url = "/api/contacts";
	
	var getContact = function(id){
		$http.get($scope.base_url + "/" + $routeParams.id)
		.then(function success(data){
			$scope.contact = data.data;
			console.log(data);
		}, function error(data){
			console.log(data);
		});
		
	};
	
	getContact();
	
});

//==========================================================================================

phonebookApp.controller('editContactCtrl', function($scope, $http, $location, $routeParams){
	
	$scope.contact = {};
	$scope.base_url = "/api/contacts/";
	
	var getContact = function(id){
		$http.get($scope.base_url + "/" + $routeParams.id)
		.then(function success(data){
			$scope.contact = data.data;
			console.log(data);
		}, function error(data){
			console.log(data);
		});
		
	};
	
	getContact();
	
	$scope.edit = function(){
		$http.put($scope.base_url + $routeParams.id, $scope.contact)
			.then(function success(data){
				$location.path("/contacts");
			}, function error(data){
				console.log(data);
			})
		
	};
	
});

//==========================================================================================

phonebookApp.controller('findContactCtrl', function($scope, $http, $routeParams, $location){
	
	$scope.contactSearch = {};
	$scope.contacts = [];
	$scope.indikator = true;
	
	$scope.find = function(){
		getLekovi();
	};
	
	var getLekovi = function(){ 
		
		var config = {params: {}};
		
		config.params.page = $scope.page;
		
		if ($scope.contactSearch.naziv != "") {
			config.params.naziv = $scope.contactSearch.naziv;
		}
		if ($scope.contactSearch.minC != "") {
			config.params.minC = $scope.contactSearch.minC;
		}
		if ($scope.contactSearch.maxC != "") {
			config.params.maxC = $scope.contactSearch.maxC;
		}
		
		$http.get("/api/contacts", config)
			.then(function success(data){
				if (data.data != ""){
					$scope.indikator = false;
				} else {
					$scope.indikator = true;
				}
				$scope.contacts = data.data;
				$scope.num_pages = data.headers("pages");
			}, function error(data){
				console.log(data);
			});
		
	};
	
});

//==========================================================================================

phonebookApp.config(['$routeProvider', function($routeProvider) {
	
	$routeProvider
	
		.when('/', {
			redirectTo: '/contacts/page/0' 
		})
		
		.when('/contacts/page/:page', {
			templateUrl : '/static/app/html/partial/contacts.html'
		})
		
		.when('/contacts/add', {
			templateUrl : '/static/app/html/partial/add-contact.html'
		})
		
		.when('/contacts/edit/:id', {
			templateUrl : '/static/app/html/partial/edit-contact.html'
		})
		
		.when('/contacts/find', {
			templateUrl : '/static/app/html/partial/find-contact.html'
		})
		
		.when('/contacts/view/:id', {
			templateUrl : '/static/app/html/partial/view-contact.html'
		})
		
		.otherwise({
			redirectTo: '/'
		});

}]);