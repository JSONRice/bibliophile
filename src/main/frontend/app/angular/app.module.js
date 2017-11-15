import angular from 'angular';
import uiRouter from '@uirouter/angularjs';

import routes from './routes';

const appModule = angular.module('app', [uiRouter]);

appModule.config(routes);
