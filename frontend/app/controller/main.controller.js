app.controller('MainController', ['$scope','supplierService',function ($scope,supplierService) {

    var self = this;


    self.createSupplier = function (form) {
        form.$submitted = true;
        if (!form.$valid) { return; }
        console.log('supplier', self.tempSupplier)
        supplierService.createSupplier(self.tempSupplier).then(function (response) {
            swal("Fornecedor cadastrado com sucesso!", "", "success");
            executeAfterHttpRequest();
        })
    }

    self.getAllSuppliers = function () {
        supplierService.getAllSuppliers()
            .then(function (response) {self.suppliers = response.data;}, function () {
            swal("Não foi possível coletar os dados dos fornecedores, verifique sua conexão!", "", "warning");
        })
    };

    self.updateSupplier = function (form) {
        form.$submitted = true;
        if (!form.$valid) { return; }
        supplierService.updateSupplier(self.tempSupplier, self.tempSupplier.id).then(function (response) {
            swal("Fornecedor atualizado com sucesso!", "", "success");
            executeAfterHttpRequest();
        })
    }

    self.prepareToUpdate = function (supplier) {
        self.tempSupplier = angular.copy(supplier);
        self.isSupplierToBeUpdated = true;
    }


    self.deleteSupplier = function (id_fornecedor) {
        supplierService.deleteSupplier(id_fornecedor).then(function (response) {
            swal("Exclusão realizada com sucesso!", "", "success");
            executeAfterHttpRequest();
        })
    }

    self.executeWhenUpdateButtonClicked = function(form) {
        form.$submitted = false;
        form.name$touched = false;
        self.updateSupplier(form);
        self.executeWhenCancelButtonClicked();
    }

    function disableSupplierToBeUpdated() {
        self.isSupplierToBeUpdated = false;
    }

    self.enableRegisterTemplate = function () {
        self.tempSupplier = {};
    }

    self.cleanData = function () {
        self.tempSupplier = {};
    }

    function executeAfterHttpRequest() {
        self.cleanData();
        self.getAllSuppliers();
    }

    self.executeWhenCancelButtonClicked = function (form) {
        form.$submitted = false;
        self.cleanData();
        disableSupplierToBeUpdated();
    }


    $scope.init = function () {
        disableSupplierToBeUpdated();
        self.tempSupplier = {};
        self.getAllSuppliers();
    };
    $scope.init();
}]);