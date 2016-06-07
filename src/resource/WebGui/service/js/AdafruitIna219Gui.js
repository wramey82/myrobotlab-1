angular.module('mrlapp.service.AdafruitIna219Gui', [])
.controller('AdafruitIna219GuiCtrl', ['$log', '$scope', 'mrl', function($log, $scope, mrl) {
    $log.info('AdafruitIna219GuiCtrl');
    var _self = this;
    var msg = this.msg;
    
    // init
    //$scope.controller = '';
    $scope.controllerName = '';
    $scope.controllers = [];    
    
    // GOOD TEMPLATE TO FOLLOW
    this.updateState = function(service) {
        $scope.service = service;
        $scope.controllerName = service.controllerName;
        $scope.isAttached = service.isAttached;
        $scope.controllers = service.controllers;
        $scope.busVoltage = service.busVoltage;
        $scope.shuntVoltage = service.shuntVoltage;
        $scope.current = service.current;
        $scope.power = service.power;
    }
    ;
    
    _self.updateState($scope.service);
    
    this.onMsg = function(inMsg) {
        var data = inMsg.data[0];
        switch (inMsg.method) {
        case 'onState':
            _self.updateState(data);
            $scope.$apply();
            break;
        // servo event in the past 
        // meant feedback from MRLComm.c
        // but perhaps its come to mean
        // feedback from the service.moveTo
        case 'onStatus':
            $scope.status = data;
            $scope.$apply();
            break;
        case 'addListener':
            // wtf?
            $log.info("Add listener called");
            $scope.status = data;
            $scope.$apply();
            break;
            
        default:
            $log.info("ERROR - unhandled method " + $scope.name + " Method " + inMsg.method);
            break;
        }
        ;
    
    }
    ;
    
    $scope.setControllerName = function(name) {
        $scope.controllerName = name;
    }
    
    // regrettably the onMethodMap dynamic
    // generation of methods failed on this overloaded
    // sweep method - there are several overloads in the
    // Java service - although msg.sweep() was tried for ng-click
    // for some reason Js resolved msg.sweep(null, null, null, null) :P

    msg.subscribe(this);
}
]);
