(function() {
    'use strict';

    angular
        .module('dryhomecrmApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Customer'];

    function CustomerDetailController($scope, $rootScope, $stateParams, entity, Customer) {
        var vm = this;

        vm.customer = entity;

        var unsubscribe = $rootScope.$on('dryhomecrmApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
