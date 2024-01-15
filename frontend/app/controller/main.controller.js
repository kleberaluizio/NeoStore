app.controller('MainController', ['$scope','supplierService',function ($scope,supplierService) {

    var self = this;
    self.isRegisterEnable = false;


    // 
    self.createSupplier = function (form) {
        form.$submitted = true;
        if (!form.$valid) { return; }
        supplierService.createSupplier(self.tempSupplier).then(function (response) {
            swal("Fornecedor cadastrado com sucesso!", "", "success");
            executeAfterHttpRequest();
            cleanFormMessages(form);
        })
    }

    /* PAGINACAO */
    // Exemplo de lógica de controle de paginação no controlador
    
    self.pages = [];
    self.currentPage = 1;
    self.itemsPerPage = 5;
    self.totalItems = 0;
    self.totalPages = 0;

    self.getAllSuppliers = function () {
        self.getPageSuppliers();
        /*
        supplierService.getAllSuppliers()
            .then(function (response) {self.suppliers = response.data;}, function () {
            swal("Não foi possível coletar os dados dos fornecedores, verifique sua conexão!", "", "warning");
        })
        */
    };

    self.getPageSuppliers = function () {
        supplierService.getPageSuppliers(self.itemsPerPage, self.currentPage)
            .then(function (response) {
                self.suppliers = response.data.data;
                self.totalItems = response.data.totalItems;
                self.totalPages = Math.ceil(self.totalItems / self.itemsPerPage); 
            }, function () {
                swal("Não foi possível coletar os dados dos fornecedores, verifique sua conexão!", "", "warning");
            })
    };


    // Função para carregar uma página específica
    self.loadPage = function (page) {
        // Atualizar a página atual
        self.currentPage = page;

        // Chamar a função existente para obter os fornecedores da página atual
        self.getPageSuppliers(self.itemsPerPage, self.currentPage);
    };

    // Função para atualizar a lista de páginas
    function updatePages() {
        self.pages = [];
        for (let i = 1; i <= self.totalPages; i++) {
            self.pages.push(i);
        }
    }
    $scope.$watch('mainCtrl.suppliers', function (newValue) {
        if (newValue) {
            // Calcular o número total de páginas com base no total de itens e itens por página
            self.totalPages = Math.ceil(self.totalItems / self.itemsPerPage);

            // Atualizar a lista de páginas
            updatePages();
        }
    });

        /* PAGINACAO */

    self.updateSupplier = function (form) {
        form.$submitted = true;
        if (!form.$valid) { return; }
        supplierService.updateSupplier(self.tempSupplier, self.tempSupplier.id).then(function (response) {
            swal("Fornecedor atualizado com sucesso!", "", "success");
            executeAfterHttpRequest();
        })
    }

    self.prepareToUpdate = function (supplier) {
        self.isRegisterEnable = true;
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
        self.updateSupplier(form);
        cleanFormMessages(form);
        self.cleanData();
        self.isSupplierToBeUpdated = false;
    }


    self.executeWhenCancelButtonClicked = function (form) {
        cleanFormMessages(form);
        self.cleanData();
        self.isSupplierToBeUpdated = false;
        self.isRegisterEnable = false;
    }

    self.executeWhenCadastrarButtonClicked = function () {
        self.isRegisterEnable = true;
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

    function cleanFormMessages(form){
        form.$submitted = false;
        form.$setUntouched();
    }


    $scope.init = function () {
        self.isSupplierToBeUpdated = false;
        self.tempSupplier = {};
        self.getAllSuppliers();
    };
    $scope.init();

    
    angular.element(document).ready(function () {
        var cleave = new Cleave('.cnpj', {
            delimiters: ['.', '.', '/', '-'],
            blocks: [2, 3, 3, 4, 2],
            numericOnly: true
        });
    });
    
}]);