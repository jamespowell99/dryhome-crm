(function () {
    'use strict';

    angular
        .module('dryhomecrmApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
