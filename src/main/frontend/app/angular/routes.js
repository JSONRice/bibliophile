import Home from './home';

export default function routerConfig($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('home', {
            url: '/home',
            template: Home.template,
            controller: Home.controller,
            controllerAs: 'vm'
        });
}
