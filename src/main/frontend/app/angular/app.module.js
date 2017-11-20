import angular from 'angular';
import uiRouter from 'angular-ui-router';

import routes from './routes';

const appModule = angular.module('app', [uiRouter]);

appModule.config(routes);
